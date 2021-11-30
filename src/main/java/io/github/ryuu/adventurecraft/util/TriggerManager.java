package io.github.ryuu.adventurecraft.util;

import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.util.io.AbstractTag;
import net.minecraft.util.io.CompoundTag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TriggerManager {

    boolean resetActive;

    Level world;

    HashMap<CoordBlock, HashMap<Integer, TriggerArea>> triggerAreas;

    public TriggerManager(Level w) {
        this.world = w;
        this.triggerAreas = new HashMap<>();
    }

    public void addArea(int x, int y, int z, TriggerArea newArea) {
        this.addArea(x, y, z, 0, newArea);
    }

    public void addArea(int x, int y, int z, int areaID, TriggerArea newArea) {
        CoordBlock coord = new CoordBlock(x, y, z);
        HashMap<Integer, TriggerArea> areas = this.triggerAreas.get(coord);
        if (areas == null) {
            areas = new HashMap<>();
            this.triggerAreas.put(coord, areas);
        }
        TriggerArea prevArea = areas.get(areaID);
        ArrayList<CoordBlock> blocks = this.findBlocksToActivate(newArea);
        areas.put(areaID, newArea);
        this.activateBlocks(blocks);
        if (prevArea != null) {
            this.deactivateArea(prevArea);
        }
    }

    public void removeArea(int x, int y, int z) {
        CoordBlock coord = new CoordBlock(x, y, z);
        HashMap<Integer, TriggerArea> areas = this.triggerAreas.get(coord);
        if (areas != null) {
            for (Integer i : ((HashMap<Integer, TriggerArea>) areas.clone()).keySet()) {
                this.removeArea(coord, areas, i);
            }
        }
    }

    public void removeArea(int x, int y, int z, int areaID) {
        CoordBlock coord = new CoordBlock(x, y, z);
        HashMap<Integer, TriggerArea> areas = this.triggerAreas.get(coord);
        if (areas != null) {
            this.removeArea(coord, areas, areaID);
        }
    }

    private void removeArea(CoordBlock coord, HashMap<Integer, TriggerArea> areas, int areaID) {
        TriggerArea prevArea = areas.get(areaID);
        if (prevArea != null) {
            areas.remove(areaID);
            if (areas.isEmpty()) {
                this.triggerAreas.remove(coord);
            }
            this.deactivateArea(prevArea);
        }
    }

    public int getTriggerAmount(int x, int y, int z) {
        int count = 0;
        for (HashMap<Integer, TriggerArea> areas : this.triggerAreas.values()) {
            for (TriggerArea aabb : areas.values()) {
                if (!aabb.isPointInside(x, y, z)) continue;
                ++count;
            }
        }
        return count;
    }

    public boolean isActivated(int x, int y, int z) {
        for (HashMap<Integer, TriggerArea> areas : this.triggerAreas.values()) {
            for (TriggerArea aabb : areas.values()) {
                if (!aabb.isPointInside(x, y, z)) continue;
                return true;
            }
        }
        return false;
    }

    public void outputTriggerSources(int x, int y, int z) {
        AccessMinecraft.getInstance().overlay.addChatMessage(String.format("Outputting active triggerings for (%d, %d, %d)", x, y, z));
        for (Map.Entry<CoordBlock, HashMap<Integer, TriggerArea>> e : this.triggerAreas.entrySet()) {
            for (TriggerArea aabb : e.getValue().values()) {
                if (!aabb.isPointInside(x, y, z)) continue;
                CoordBlock c = e.getKey();
                AccessMinecraft.getInstance().overlay.addChatMessage(String.format("Triggered by (%d, %d, %d)", c.x, c.y, c.z));
            }
        }
    }

    private ArrayList<CoordBlock> findBlocksToActivate(TriggerArea area) {
        ArrayList<CoordBlock> blocksToActivate = new ArrayList<>();
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

    private void activateBlocks(ArrayList<CoordBlock> blocksToActivate) {
        for (CoordBlock c : blocksToActivate) {
            int blockID = this.world.getTileId(c.x, c.y, c.z);
            if (blockID != 0 && ((ExTile) Tile.BY_ID[blockID]).canBeTriggered()) {
                ((ExTile) Tile.BY_ID[blockID]).onTriggerActivated(this.world, c.x, c.y, c.z);
            }
        }
    }

    private void deactivateArea(TriggerArea area) {
        for (int x = area.minX; x <= area.maxX; ++x) {
            for (int y = area.minY; y <= area.maxY; ++y) {
                for (int z = area.minZ; z <= area.maxZ; ++z) {
                    int blockID;
                    if (this.getTriggerAmount(x, y, z) == 0 && (blockID = this.world.getTileId(x, y, z)) != 0 && ((ExTile) Tile.BY_ID[blockID]).canBeTriggered()) {
                        ((ExTile) Tile.BY_ID[blockID]).onTriggerDeactivated(this.world, x, y, z);
                    }
                }
            }
        }
    }

    public CompoundTag getTagCompound() {
        CompoundTag tag = new CompoundTag();
        int numCoords = 0;
        for (Map.Entry<CoordBlock, HashMap<Integer, TriggerArea>> e : this.triggerAreas.entrySet()) {
            CompoundTag coordTag = new CompoundTag();
            CoordBlock c = e.getKey();
            coordTag.put("x", c.x);
            coordTag.put("y", c.y);
            coordTag.put("z", c.z);
            int numAreas = 0;
            for (Map.Entry<Integer, TriggerArea> te : e.getValue().entrySet()) {
                CompoundTag areaTag = te.getValue().getTagCompound();
                areaTag.put("areaID", te.getKey());
                coordTag.put(String.format("area%d", numAreas++), (AbstractTag) areaTag);
            }
            coordTag.put("numAreas", numAreas);
            tag.put(String.format("coord%d", numCoords++), (AbstractTag) coordTag);
        }
        tag.put("numCoords", numCoords);
        return tag;
    }

    public void loadFromTagCompound(CompoundTag tag) {
        this.triggerAreas.clear();
        int numCoords = tag.getInt("numCoords");
        for (int i = 0; i < numCoords; ++i) {
            CompoundTag coordTag = tag.getCompoundTag(String.format("coord%d", i));
            CoordBlock coord = new CoordBlock(coordTag.getInt("x"), coordTag.getInt("y"), coordTag.getInt("z"));
            HashMap<Integer, TriggerArea> areas = new HashMap<>();
            this.triggerAreas.put(coord, areas);
            int numAreas = coordTag.getInt("numAreas");
            for (int j = 0; j < numAreas; ++j) {
                CompoundTag areaTag = coordTag.getCompoundTag(String.format("area%d", j));
                TriggerArea area = TriggerArea.getFromTagCompound(areaTag);
                areas.put(coordTag.getInt("areaID"), area);
            }
        }
    }

    public void resetArea(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        boolean oldResetActive = resetActive;
        resetActive = true;
        for (int x = minX; x <= maxX; ++x) {
            for (int y = minY; y <= maxY; ++y) {
                for (int z = minZ; z <= maxZ; ++z) {
                    int blockID = world.getTileId(x, y, z);
                    if (blockID == 0) continue;
                    ((ExTile) Tile.BY_ID[blockID]).reset(world, x, y, z, false);
                }
            }
        }
        resetActive = oldResetActive;
    }
}
