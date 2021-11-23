package io.github.ryuu.adventurecraft.util;

import net.minecraft.client.Minecraft;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.util.io.AbstractTag;
import net.minecraft.util.io.CompoundTag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TriggerManager {

    Level world;

    HashMap<net.minecraft.src.CoordBlock, HashMap<Integer, TriggerArea>> triggerAreas;

    TriggerManager(Level w) {
        this.world = w;
        this.triggerAreas = new HashMap();
    }

    public void addArea(int x, int y, int z, TriggerArea newArea) {
        this.addArea(x, y, z, 0, newArea);
    }

    public void addArea(int x, int y, int z, int areaID, TriggerArea newArea) {
        CoordBlock coord = new CoordBlock(x, y, z);
        HashMap areas = this.triggerAreas.get((Object) coord);
        if (areas == null) {
            areas = new HashMap();
            this.triggerAreas.put((Object) coord, (Object) areas);
        }
        TriggerArea prevArea = (TriggerArea) areas.get(areaID);
        ArrayList blocks = this.findBlocksToActivate(newArea);
        areas.put(areaID, newArea);
        this.activateBlocks((ArrayList<net.minecraft.src.CoordBlock>) blocks);
        if (prevArea != null) {
            this.deactivateArea(prevArea);
        }
    }

    public void removeArea(int x, int y, int z) {
        CoordBlock coord = new CoordBlock(x, y, z);
        HashMap areas = this.triggerAreas.get((Object) coord);
        if (areas != null) {
            for (Integer i : ((HashMap) areas.clone()).keySet()) {
                this.removeArea(coord, areas, i);
            }
        }
    }

    public void removeArea(int x, int y, int z, int areaID) {
        CoordBlock coord = new CoordBlock(x, y, z);
        HashMap areas = this.triggerAreas.get((Object) coord);
        if (areas != null) {
            this.removeArea(coord, areas, areaID);
        }
    }

    private void removeArea(CoordBlock coord, HashMap areas, int areaID) {
        TriggerArea prevArea = (TriggerArea) areas.get(areaID);
        if (prevArea != null) {
            areas.remove(areaID);
            if (areas.isEmpty()) {
                this.triggerAreas.remove((Object) coord);
            }
            this.deactivateArea(prevArea);
        }
    }

    int getTriggerAmount(int x, int y, int z) {
        int count = 0;
        for (HashMap areas : this.triggerAreas.values()) {
            for (TriggerArea aabb : areas.values()) {
                if (!aabb.isPointInside(x, y, z)) continue;
                ++count;
            }
        }
        return count;
    }

    boolean isActivated(int x, int y, int z) {
        for (HashMap areas : this.triggerAreas.values()) {
            for (TriggerArea aabb : areas.values()) {
                if (!aabb.isPointInside(x, y, z)) continue;
                return true;
            }
        }
        return false;
    }

    void outputTriggerSources(int x, int y, int z) {
        Minecraft.minecraftInstance.overlay.addChatMessage(String.format("Outputting active triggerings for (%d, %d, %d)", new Object[]{x, y, z}));
        for (Map.Entry e : this.triggerAreas.entrySet()) {
            HashMap areas = (HashMap) e.getValue();
            for (TriggerArea aabb : areas.values()) {
                if (!aabb.isPointInside(x, y, z)) continue;
                CoordBlock c = (CoordBlock) e.getKey();
                Minecraft.minecraftInstance.overlay.addChatMessage(String.format("Triggered by (%d, %d, %d)", new Object[]{c.x, c.y, c.z}));
            }
        }
    }

    private ArrayList findBlocksToActivate(TriggerArea area) {
        ArrayList blocksToActivate = new ArrayList();
        for (int x = area.minX; x <= area.maxX; ++x) {
            for (int y = area.minY; y <= area.maxY; ++y) {
                for (int z = area.minZ; z <= area.maxZ; ++z) {
                    if (this.getTriggerAmount(x, y, z) != 0) continue;
                    blocksToActivate.add(new CoordBlock(x, y, z));
                }
            }
        }
        return blocksToActivate;
    }

    private void activateBlocks(ArrayList<net.minecraft.src.CoordBlock> blocksToActivate) {
        for (CoordBlock c : blocksToActivate) {
            int blockID = this.world.getTileId(c.x, c.y, c.z);
            if (blockID == 0 || !Tile.BY_ID[blockID].canBeTriggered()) continue;
            Tile.BY_ID[blockID].onTriggerActivated(this.world, c.x, c.y, c.z);
        }
    }

    private void deactivateArea(TriggerArea area) {
        for (int x = area.minX; x <= area.maxX; ++x) {
            for (int y = area.minY; y <= area.maxY; ++y) {
                for (int z = area.minZ; z <= area.maxZ; ++z) {
                    int blockID;
                    if (this.getTriggerAmount(x, y, z) != 0 || (blockID = this.world.getTileId(x, y, z)) == 0 || !Tile.BY_ID[blockID].canBeTriggered())
                        continue;
                    Tile.BY_ID[blockID].onTriggerDeactivated(this.world, x, y, z);
                }
            }
        }
    }

    CompoundTag getTagCompound() {
        CompoundTag tag = new CompoundTag();
        int numCoords = 0;
        for (Map.Entry e : this.triggerAreas.entrySet()) {
            CompoundTag coordTag = new CompoundTag();
            CoordBlock c = (CoordBlock) e.getKey();
            coordTag.put("x", c.x);
            coordTag.put("y", c.y);
            coordTag.put("z", c.z);
            int numAreas = 0;
            for (Map.Entry te : ((HashMap) e.getValue()).entrySet()) {
                CompoundTag areaTag = ((TriggerArea) te.getValue()).getTagCompound();
                areaTag.put("areaID", (Integer) te.getKey());
                coordTag.put(String.format("area%d", numAreas++), (AbstractTag) areaTag);
            }
            coordTag.put("numAreas", numAreas);
            tag.put(String.format("coord%d", numCoords++), (AbstractTag) coordTag);
        }
        tag.put("numCoords", numCoords);
        return tag;
    }

    void loadFromTagCompound(CompoundTag tag) {
        this.triggerAreas.clear();
        int numCoords = tag.getInt("numCoords");
        for (int i = 0; i < numCoords; ++i) {
            CompoundTag coordTag = tag.getCompoundTag(String.format("coord%d", i));
            CoordBlock coord = new CoordBlock(coordTag.getInt("x"), coordTag.getInt("y"), coordTag.getInt("z"));
            HashMap areas = new HashMap();
            this.triggerAreas.put((Object) coord, (Object) areas);
            int numAreas = coordTag.getInt("numAreas");
            for (int j = 0; j < numAreas; ++j) {
                CompoundTag areaTag = coordTag.getCompoundTag(String.format("area%d", j));
                TriggerArea area = TriggerArea.getFromTagCompound(areaTag);
                areas.put(coordTag.getInt("areaID"), area);
            }
        }
    }
}
