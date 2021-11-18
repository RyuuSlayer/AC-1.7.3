package io.github.ryuu.adventurecraft.util;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.tile.Tile;

public class InventoryDebug implements lw {
    private final String inventoryTitle;
    private final int size;
    private final ItemInstance[] inventoryContents;
    public int firstItem;
    public int lastItem;
    public boolean atEnd;

    public InventoryDebug(String s, int i) {
        this.inventoryTitle = s;
        this.size = i;
        this.inventoryContents = new ItemInstance[i];
    }

    private int getID(int i) {
        int j;
        for (j = 0; j < 4; ++j) {
            if (i <= Tile.GRASS.id) continue;
            --i;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Tile.SAND.id) continue;
            --i;
        }
        if (i > Tile.LOG.id) {
            --i;
        }
        if (i > Tile.LOG.id) {
            --i;
        }
        for (j = 0; j < 2; ++j) {
            if (i <= Tile.TALLGRASS.id) continue;
            --i;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Tile.WOOL.id) continue;
            --i;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Tile.STONE_SLAB.id) continue;
            --i;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.pillarStone.id) continue;
            --i;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.pillarMetal.id) continue;
            --i;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.plant1.id) continue;
            --i;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.trees.id) continue;
            --i;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.glassBlocks.id) continue;
            --i;
        }
        for (j = 0; j < 9; ++j) {
            if (i <= Blocks.cageBlocks.id) continue;
            --i;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.stoneBlocks1.id) continue;
            --i;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.stoneBlocks2.id) continue;
            --i;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.stoneBlocks3.id) continue;
            --i;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.woodBlocks.id) continue;
            --i;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.halfSteps1.id) continue;
            --i;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.halfSteps2.id) continue;
            --i;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.halfSteps3.id) continue;
            --i;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.tableBlocks.id) continue;
            --i;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Blocks.chairBlocks1.id) continue;
            --i;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Blocks.chairBlocks2.id) continue;
            --i;
        }
        for (j = 0; j < 14; ++j) {
            if (i <= Blocks.ropes1.id) continue;
            --i;
        }
        for (j = 0; j < 14; ++j) {
            if (i <= Blocks.ropes2.id) continue;
            --i;
        }
        for (j = 0; j < 8; ++j) {
            if (i <= Blocks.chains.id) continue;
            --i;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Blocks.ladders1.id) continue;
            --i;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Blocks.ladders2.id) continue;
            --i;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Blocks.ladders3.id) continue;
            --i;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Blocks.ladders4.id) continue;
            --i;
        }
        for (j = 0; j < 13; ++j) {
            if (i <= Blocks.lights1.id) continue;
            --i;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.plant2.id) continue;
            --i;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.plant3.id) continue;
            --i;
        }
        for (j = 0; j < 6; ++j) {
            if (i <= Blocks.overlay1.id) continue;
            --i;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Blocks.stairs1.id) continue;
            --i;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Blocks.stairs2.id) continue;
            --i;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Blocks.stairs3.id) continue;
            --i;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Blocks.stairs4.id) continue;
            --i;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Blocks.slopes1.id) continue;
            --i;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Blocks.slopes2.id) continue;
            --i;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Blocks.slopes3.id) continue;
            --i;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Blocks.slopes4.id) continue;
            --i;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= ItemType.dyePowder.id) continue;
            --i;
        }
        return i;
    }

    private int getSubtype(int i) {
        int j;
        int subtype = 0;
        for (j = 0; j < 4; ++j) {
            if (i <= Tile.GRASS.id) continue;
            --i;
            ++subtype;
        }
        if (i > Tile.GRASS.id) {
            subtype = 0;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Tile.SAND.id) continue;
            --i;
            ++subtype;
        }
        if (i > Tile.SAND.id) {
            subtype = 0;
        }
        if (i > Tile.LOG.id) {
            --i;
            ++subtype;
        }
        if (i > Tile.LOG.id) {
            --i;
            ++subtype;
        }
        if (i > Tile.LOG.id) {
            subtype = 0;
        }
        for (j = 0; j < 2; ++j) {
            if (i <= Tile.TALLGRASS.id) continue;
            --i;
            ++subtype;
        }
        if (i > Tile.TALLGRASS.id) {
            subtype = 0;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Tile.WOOL.id) continue;
            --i;
            ++subtype;
        }
        if (i > Tile.WOOL.id) {
            subtype = 0;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Tile.STONE_SLAB.id) continue;
            --i;
            ++subtype;
        }
        if (i > Tile.STONE_SLAB.id) {
            subtype = 0;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.pillarStone.id) continue;
            --i;
            ++subtype;
        }
        if (i > Blocks.pillarStone.id) {
            subtype = 0;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.pillarMetal.id) continue;
            --i;
            ++subtype;
        }
        if (i > Blocks.pillarMetal.id) {
            subtype = 0;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.plant1.id) continue;
            --i;
            ++subtype;
        }
        if (i > Blocks.plant1.id) {
            subtype = 0;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.trees.id) continue;
            --i;
            ++subtype;
        }
        if (i > Blocks.trees.id) {
            subtype = 0;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.glassBlocks.id) continue;
            --i;
            ++subtype;
        }
        if (i > Blocks.glassBlocks.id) {
            subtype = 0;
        }
        for (j = 0; j < 9; ++j) {
            if (i <= Blocks.cageBlocks.id) continue;
            --i;
            ++subtype;
        }
        if (i > Blocks.cageBlocks.id) {
            subtype = 0;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.stoneBlocks1.id) continue;
            --i;
            ++subtype;
        }
        if (i > Blocks.stoneBlocks1.id) {
            subtype = 0;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.stoneBlocks2.id) continue;
            --i;
            ++subtype;
        }
        if (i > Blocks.stoneBlocks2.id) {
            subtype = 0;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.stoneBlocks3.id) continue;
            --i;
            ++subtype;
        }
        if (i > Blocks.stoneBlocks3.id) {
            subtype = 0;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.woodBlocks.id) continue;
            --i;
            ++subtype;
        }
        if (i > Blocks.woodBlocks.id) {
            subtype = 0;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.halfSteps1.id) continue;
            --i;
            ++subtype;
        }
        if (i > Blocks.halfSteps1.id) {
            subtype = 0;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.halfSteps2.id) continue;
            --i;
            ++subtype;
        }
        if (i > Blocks.halfSteps2.id) {
            subtype = 0;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.halfSteps3.id) continue;
            --i;
            ++subtype;
        }
        if (i > Blocks.halfSteps3.id) {
            subtype = 0;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.tableBlocks.id) continue;
            --i;
            ++subtype;
        }
        if (i > Blocks.tableBlocks.id) {
            subtype = 0;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Blocks.chairBlocks1.id) continue;
            --i;
            subtype += 4;
        }
        if (i > Blocks.chairBlocks1.id) {
            subtype = 0;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Blocks.chairBlocks2.id) continue;
            --i;
            subtype += 4;
        }
        if (i > Blocks.chairBlocks2.id) {
            subtype = 0;
        }
        for (j = 0; j < 14; ++j) {
            if (i <= Blocks.ropes1.id) continue;
            --i;
            ++subtype;
        }
        if (i > Blocks.ropes1.id) {
            subtype = 0;
        }
        for (j = 0; j < 14; ++j) {
            if (i <= Blocks.ropes2.id) continue;
            --i;
            ++subtype;
        }
        if (i > Blocks.ropes2.id) {
            subtype = 0;
        }
        for (j = 0; j < 8; ++j) {
            if (i <= Blocks.chains.id) continue;
            --i;
            ++subtype;
        }
        if (i > Blocks.chains.id) {
            subtype = 0;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Blocks.ladders1.id) continue;
            --i;
            subtype += 4;
        }
        if (i > Blocks.ladders1.id) {
            subtype = 0;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Blocks.ladders2.id) continue;
            --i;
            subtype += 4;
        }
        if (i > Blocks.ladders2.id) {
            subtype = 0;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Blocks.ladders3.id) continue;
            --i;
            subtype += 4;
        }
        if (i > Blocks.ladders3.id) {
            subtype = 0;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Blocks.ladders4.id) continue;
            --i;
            subtype += 4;
        }
        if (i > Blocks.ladders4.id) {
            subtype = 0;
        }
        for (j = 0; j < 13; ++j) {
            if (i <= Blocks.lights1.id) continue;
            --i;
            ++subtype;
        }
        if (i > Blocks.lights1.id) {
            subtype = 0;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.plant2.id) continue;
            --i;
            ++subtype;
        }
        if (i > Blocks.plant2.id) {
            subtype = 0;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= Blocks.plant3.id) continue;
            --i;
            ++subtype;
        }
        if (i > Blocks.plant3.id) {
            subtype = 0;
        }
        for (j = 0; j < 6; ++j) {
            if (i <= Blocks.overlay1.id) continue;
            --i;
            ++subtype;
        }
        if (i > Blocks.overlay1.id) {
            subtype = 0;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Blocks.stairs1.id) continue;
            --i;
            subtype += 4;
        }
        if (i > Blocks.stairs1.id) {
            subtype = 0;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Blocks.stairs2.id) continue;
            --i;
            subtype += 4;
        }
        if (i > Blocks.stairs2.id) {
            subtype = 0;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Blocks.stairs3.id) continue;
            --i;
            subtype += 4;
        }
        if (i > Blocks.stairs3.id) {
            subtype = 0;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Blocks.stairs4.id) continue;
            --i;
            subtype += 4;
        }
        if (i > Blocks.stairs4.id) {
            subtype = 0;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Blocks.slopes1.id) continue;
            --i;
            subtype += 4;
        }
        if (i > Blocks.slopes1.id) {
            subtype = 0;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Blocks.slopes2.id) continue;
            --i;
            subtype += 4;
        }
        if (i > Blocks.slopes2.id) {
            subtype = 0;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Blocks.slopes3.id) continue;
            --i;
            subtype += 4;
        }
        if (i > Blocks.slopes3.id) {
            subtype = 0;
        }
        for (j = 0; j < 3; ++j) {
            if (i <= Blocks.slopes4.id) continue;
            --i;
            subtype += 4;
        }
        if (i > Blocks.slopes4.id) {
            subtype = 0;
        }
        for (j = 0; j < 15; ++j) {
            if (i <= ItemType.dyePowder.id) continue;
            --i;
            ++subtype;
        }
        if (i > ItemType.dyePowder.id) {
            subtype = 0;
        }
        return subtype;
    }

    public void fillInventory(int offset) {
        boolean filledFirst = false;
        this.atEnd = false;
        for (int i = 0; i < this.size; i++) {
            int id = getID(i + offset);
            if (gm.c[id] != null) {
                this.inventoryContents[i] = new iz(gm.c[id], -64);
                this.inventoryContents[i].b(getSubtype(i + offset));
                this.lastItem = i + offset;
                if (!filledFirst) {
                    this.firstItem = i + offset;
                    filledFirst = true;
                }
            } else if (id < 31999) {
                i--;
                offset++;
            } else {
                this.atEnd = true;
                while (i < this.size) {
                    this.inventoryContents[i] = null;
                    i++;
                }
                return;
            }
        }
    }

    public void fillInventory(int offset) {
        boolean filledFirst = false;
        this.atEnd = false;
        for (int i = 0; i < this.size; ++i) {
            int id = this.getID(i + offset);
            if (ItemType.byId[id] != null) {
                this.inventoryContents[i] = new ItemInstance(ItemType.byId[id], -64);
                this.inventoryContents[i].setDamage(this.getSubtype(i + offset));
                this.lastItem = i + offset;
                if (filledFirst) continue;
                this.firstItem = i + offset;
                filledFirst = true;
                continue;
            }
            if (id < 31999) {
                --i;
                ++offset;
                continue;
            }
            this.atEnd = true;
            while (i < this.size) {
                this.inventoryContents[i] = null;
                ++i;
            }
            return;
        }
    }

    public void fillInventoryBackwards(int offset) {
        boolean filledFirst = false;
        this.atEnd = false;
        for (int i = 0; i < this.size; ++i) {
            int id = this.getID(offset - i);
            if (id > 0 && ItemType.byId[id] != null) {
                this.inventoryContents[this.size - i - 1] = new ItemInstance(ItemType.byId[id], -64);
                this.inventoryContents[this.size - i - 1].setDamage(this.getSubtype(offset - i));
                this.firstItem = offset - i;
                if (filledFirst) continue;
                this.lastItem = offset - i;
                filledFirst = true;
                continue;
            }
            if (offset - i > 1) {
                --i;
                --offset;
                continue;
            }
            while (i < this.size) {
                this.inventoryContents[this.size - i - 1] = null;
                ++i;
            }
            return;
        }
    }

    public ItemInstance getInvItem(int i) {
        return this.inventoryContents[i];
    }

    public ItemInstance takeInvItem(int index, int j) {
        if (this.inventoryContents[index] != null) {
            return this.inventoryContents[index].copy();
        }
        return null;
    }

    public void setInvItem(int i, ItemInstance itemstack) {
        if (this.inventoryContents[i] != null) {
            this.inventoryContents[i] = this.inventoryContents[i].copy();
        }
    }

    public int getInvSize() {
        return this.size;
    }

    public String getContainerName() {
        return this.inventoryTitle;
    }

    public int getMaxItemCount() {
        return 64;
    }

    public void markDirty() {
    }
    public boolean canPlayerUse(Player entityplayer) {
        return true;
    }
}