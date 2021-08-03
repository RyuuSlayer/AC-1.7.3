package io.github.ryuu.adventurecraft.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.util.io.CompoundTag;

public class TriggerManager {
    Level world;

    HashMap<CoordBlock, HashMap<Integer, TriggerArea>> triggerAreas;

    TriggerManager(Level w) {
        this.world = w;
        this.triggerAreas = new HashMap<>();
    }

    public void addArea(int x, int y, int z, TriggerArea newArea) {
        addArea(x, y, z, 0, newArea);
    }

    public void addArea(int x, int y, int z, int areaID, TriggerArea newArea) {
        CoordBlock coord = new CoordBlock(x, y, z);
        HashMap<Integer, TriggerArea> areas = this.triggerAreas.get(coord);
        if (areas == null) {
            areas = new HashMap<>();
            this.triggerAreas.put(coord, areas);
        }
        TriggerArea prevArea = areas.get(Integer.valueOf(areaID));
        ArrayList<CoordBlock> blocks = findBlocksToActivate(newArea);
        areas.put(Integer.valueOf(areaID), newArea);
        activateBlocks(blocks);
        if (prevArea != null)
            deactivateArea(prevArea);
    }

    public void removeArea(int x, int y, int z) {
        CoordBlock coord = new CoordBlock(x, y, z);
        HashMap<Integer, TriggerArea> areas = this.triggerAreas.get(coord);
        if (areas != null)
            for (Integer i : ((HashMap) areas.clone()).keySet())
                removeArea(coord, areas, i.intValue());
    }

    public void removeArea(int x, int y, int z, int areaID) {
        CoordBlock coord = new CoordBlock(x, y, z);
        HashMap<Integer, TriggerArea> areas = this.triggerAreas.get(coord);
        if (areas != null)
            removeArea(coord, areas, areaID);
    }

    private void removeArea(CoordBlock coord, HashMap<Integer, TriggerArea> areas, int areaID) {
        TriggerArea prevArea = areas.get(Integer.valueOf(areaID));
        if (prevArea != null) {
            areas.remove(Integer.valueOf(areaID));
            if (areas.isEmpty())
                this.triggerAreas.remove(coord);
            deactivateArea(prevArea);
        }
    }

    int getTriggerAmount(int x, int y, int z) {
        int count = 0;
        for (HashMap<Integer, TriggerArea> areas : this.triggerAreas.values()) {
            for (TriggerArea aabb : areas.values()) {
                if (aabb.isPointInside(x, y, z))
                    count++;
            }
        }
        return count;
    }

    boolean isActivated(int x, int y, int z) {
        for (HashMap<Integer, TriggerArea> areas : this.triggerAreas.values()) {
            for (TriggerArea aabb : areas.values()) {
                if (aabb.isPointInside(x, y, z))
                    return true;
            }
        }
        return false;
    }

    void outputTriggerSources(int x, int y, int z) {
        Minecraft.minecraftInstance.v.a(String.format("Outputting active triggerings for (%d, %d, %d)", new Object[]{Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z)}));
        for (Map.Entry<CoordBlock, HashMap<Integer, TriggerArea>> e : this.triggerAreas.entrySet()) {
            HashMap<Integer, TriggerArea> areas = e.getValue();
            for (TriggerArea aabb : areas.values()) {
                if (aabb.isPointInside(x, y, z)) {
                    CoordBlock c = e.getKey();
                    Minecraft.minecraftInstance.v.a(String.format("Triggered by (%d, %d, %d)", new Object[]{Integer.valueOf(c.x), Integer.valueOf(c.y), Integer.valueOf(c.z)}));
                }
            }
        }
    }

    private ArrayList<CoordBlock> findBlocksToActivate(TriggerArea area) {
        ArrayList<CoordBlock> blocksToActivate = new ArrayList<>();
        for (int x = area.minX; x <= area.maxX; x++) {
            for (int y = area.minY; y <= area.maxY; y++) {
                for (int z = area.minZ; z <= area.maxZ; z++) {
                    if (getTriggerAmount(x, y, z) == 0)
                        blocksToActivate.add(new CoordBlock(x, y, z));
                }
            }
        }
        return blocksToActivate;
    }

    private void activateBlocks(ArrayList<CoordBlock> blocksToActivate) {
        for (CoordBlock c : blocksToActivate) {
            int blockID = this.world.a(c.x, c.y, c.z);
            if (blockID != 0 && Tile.m[blockID].canBeTriggered())
                Tile.m[blockID].onTriggerActivated(this.world, c.x, c.y, c.z);
        }
    }

    private void deactivateArea(TriggerArea area) {
        for (int x = area.minX; x <= area.maxX; x++) {
            for (int y = area.minY; y <= area.maxY; y++) {
                for (int z = area.minZ; z <= area.maxZ; z++) {
                    if (getTriggerAmount(x, y, z) == 0) {
                        int blockID = this.world.a(x, y, z);
                        if (blockID != 0 && Tile.m[blockID].canBeTriggered())
                            Tile.m[blockID].onTriggerDeactivated(this.world, x, y, z);
                    }
                }
            }
        }
    }

    CompoundTag getTagCompound() {
        CompoundTag tag = new CompoundTag();
        int numCoords = 0;
        for (Map.Entry<CoordBlock, HashMap<Integer, TriggerArea>> e : this.triggerAreas.entrySet()) {
            CompoundTag coordTag = new CompoundTag();
            CoordBlock c = e.getKey();
            coordTag.a("x", c.x);
            coordTag.a("y", c.y);
            coordTag.a("z", c.z);
            int numAreas = 0;
            for (Map.Entry<Integer, TriggerArea> te : (Iterable<Map.Entry<Integer, TriggerArea>>) ((HashMap) e.getValue()).entrySet()) {
                CompoundTag areaTag = te.getValue().getTagCompound();
                areaTag.a("areaID", te.getKey().intValue());
                coordTag.a(String.format("area%d", new Object[]{Integer.valueOf(numAreas++)}), areaTag);
            }
            coordTag.a("numAreas", numAreas);
            tag.a(String.format("coord%d", new Object[]{Integer.valueOf(numCoords++)}), coordTag);
        }
        tag.a("numCoords", numCoords);
        return tag;
    }

    void loadFromTagCompound(CompoundTag tag) {
        this.triggerAreas.clear();
        int numCoords = tag.e("numCoords");
        for (int i = 0; i < numCoords; i++) {
            CompoundTag coordTag = tag.k(String.format("coord%d", new Object[]{Integer.valueOf(i)}));
            CoordBlock coord = new CoordBlock(coordTag.e("x"), coordTag.e("y"), coordTag.e("z"));
            HashMap<Integer, TriggerArea> areas = new HashMap<>();
            this.triggerAreas.put(coord, areas);
            int numAreas = coordTag.e("numAreas");
            for (int j = 0; j < numAreas; j++) {
                CompoundTag areaTag = coordTag.k(String.format("area%d", new Object[]{Integer.valueOf(j)}));
                TriggerArea area = TriggerArea.getFromTagCompound(areaTag);
                areas.put(Integer.valueOf(coordTag.e("areaID")), area);
            }
        }
    }
}
