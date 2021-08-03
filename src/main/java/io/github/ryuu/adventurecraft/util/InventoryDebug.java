package io.github.ryuu.adventurecraft.util;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import net.minecraft.entity.player.Player;
import net.minecraft.tile.Tile;

public class InventoryDebug implements lw {
    private String inventoryTitle;

    private int size;

    private iz[] inventoryContents;

    public int firstItem;

    public int lastItem;

    public boolean atEnd;

    public InventoryDebug(String s, int i) {
        this.inventoryTitle = s;
        this.size = i;
        this.inventoryContents = new iz[i];
    }

    private int getID(int i) {
        int j;
        for (j = 0; j < 4; j++) {
            if (i > Tile.v.bn)
                i--;
        }
        for (j = 0; j < 3; j++) {
            if (i > Tile.F.bn)
                i--;
        }
        if (i > Tile.K.bn)
            i--;
        if (i > Tile.K.bn)
            i--;
        for (j = 0; j < 2; j++) {
            if (i > Tile.Y.bn)
                i--;
        }
        for (j = 0; j < 15; j++) {
            if (i > Tile.ac.bn)
                i--;
        }
        for (j = 0; j < 3; j++) {
            if (i > Tile.al.bn)
                i--;
        }
        for (j = 0; j < 15; j++) {
            if (i > Blocks.pillarStone.bn)
                i--;
        }
        for (j = 0; j < 15; j++) {
            if (i > Blocks.pillarMetal.bn)
                i--;
        }
        for (j = 0; j < 15; j++) {
            if (i > Blocks.plant1.bn)
                i--;
        }
        for (j = 0; j < 15; j++) {
            if (i > Blocks.trees.bn)
                i--;
        }
        for (j = 0; j < 15; j++) {
            if (i > Blocks.glassBlocks.bn)
                i--;
        }
        for (j = 0; j < 9; j++) {
            if (i > Blocks.cageBlocks.bn)
                i--;
        }
        for (j = 0; j < 15; j++) {
            if (i > Blocks.stoneBlocks1.bn)
                i--;
        }
        for (j = 0; j < 15; j++) {
            if (i > Blocks.stoneBlocks2.bn)
                i--;
        }
        for (j = 0; j < 15; j++) {
            if (i > Blocks.stoneBlocks3.bn)
                i--;
        }
        for (j = 0; j < 15; j++) {
            if (i > Blocks.woodBlocks.bn)
                i--;
        }
        for (j = 0; j < 15; j++) {
            if (i > Blocks.halfSteps1.bn)
                i--;
        }
        for (j = 0; j < 15; j++) {
            if (i > Blocks.halfSteps2.bn)
                i--;
        }
        for (j = 0; j < 15; j++) {
            if (i > Blocks.halfSteps3.bn)
                i--;
        }
        for (j = 0; j < 15; j++) {
            if (i > Blocks.tableBlocks.bn)
                i--;
        }
        for (j = 0; j < 3; j++) {
            if (i > Blocks.chairBlocks1.bn)
                i--;
        }
        for (j = 0; j < 3; j++) {
            if (i > Blocks.chairBlocks2.bn)
                i--;
        }
        for (j = 0; j < 14; j++) {
            if (i > Blocks.ropes1.bn)
                i--;
        }
        for (j = 0; j < 14; j++) {
            if (i > Blocks.ropes2.bn)
                i--;
        }
        for (j = 0; j < 8; j++) {
            if (i > Blocks.chains.bn)
                i--;
        }
        for (j = 0; j < 3; j++) {
            if (i > Blocks.ladders1.bn)
                i--;
        }
        for (j = 0; j < 3; j++) {
            if (i > Blocks.ladders2.bn)
                i--;
        }
        for (j = 0; j < 3; j++) {
            if (i > Blocks.ladders3.bn)
                i--;
        }
        for (j = 0; j < 3; j++) {
            if (i > Blocks.ladders4.bn)
                i--;
        }
        for (j = 0; j < 13; j++) {
            if (i > Blocks.lights1.bn)
                i--;
        }
        for (j = 0; j < 15; j++) {
            if (i > Blocks.plant2.bn)
                i--;
        }
        for (j = 0; j < 15; j++) {
            if (i > Blocks.plant3.bn)
                i--;
        }
        for (j = 0; j < 6; j++) {
            if (i > Blocks.overlay1.bn)
                i--;
        }
        for (j = 0; j < 3; j++) {
            if (i > Blocks.stairs1.bn)
                i--;
        }
        for (j = 0; j < 3; j++) {
            if (i > Blocks.stairs2.bn)
                i--;
        }
        for (j = 0; j < 3; j++) {
            if (i > Blocks.stairs3.bn)
                i--;
        }
        for (j = 0; j < 3; j++) {
            if (i > Blocks.stairs4.bn)
                i--;
        }
        for (j = 0; j < 3; j++) {
            if (i > Blocks.slopes1.bn)
                i--;
        }
        for (j = 0; j < 3; j++) {
            if (i > Blocks.slopes2.bn)
                i--;
        }
        for (j = 0; j < 3; j++) {
            if (i > Blocks.slopes3.bn)
                i--;
        }
        for (j = 0; j < 3; j++) {
            if (i > Blocks.slopes4.bn)
                i--;
        }
        for (j = 0; j < 15; j++) {
            if (i > gm.aU.bf)
                i--;
        }
        return i;
    }

    private int getSubtype(int i) {
        int subtype = 0;
        int j;
        for (j = 0; j < 4; j++) {
            if (i > Tile.v.bn) {
                i--;
                subtype++;
            }
        }
        if (i > Tile.v.bn)
            subtype = 0;
        for (j = 0; j < 3; j++) {
            if (i > Tile.F.bn) {
                i--;
                subtype++;
            }
        }
        if (i > Tile.F.bn)
            subtype = 0;
        if (i > Tile.K.bn) {
            i--;
            subtype++;
        }
        if (i > Tile.K.bn) {
            i--;
            subtype++;
        }
        if (i > Tile.K.bn)
            subtype = 0;
        for (j = 0; j < 2; j++) {
            if (i > Tile.Y.bn) {
                i--;
                subtype++;
            }
        }
        if (i > Tile.Y.bn)
            subtype = 0;
        for (j = 0; j < 15; j++) {
            if (i > Tile.ac.bn) {
                i--;
                subtype++;
            }
        }
        if (i > Tile.ac.bn)
            subtype = 0;
        for (j = 0; j < 3; j++) {
            if (i > Tile.al.bn) {
                i--;
                subtype++;
            }
        }
        if (i > Tile.al.bn)
            subtype = 0;
        for (j = 0; j < 15; j++) {
            if (i > Blocks.pillarStone.bn) {
                i--;
                subtype++;
            }
        }
        if (i > Blocks.pillarStone.bn)
            subtype = 0;
        for (j = 0; j < 15; j++) {
            if (i > Blocks.pillarMetal.bn) {
                i--;
                subtype++;
            }
        }
        if (i > Blocks.pillarMetal.bn)
            subtype = 0;
        for (j = 0; j < 15; j++) {
            if (i > Blocks.plant1.bn) {
                i--;
                subtype++;
            }
        }
        if (i > Blocks.plant1.bn)
            subtype = 0;
        for (j = 0; j < 15; j++) {
            if (i > Blocks.trees.bn) {
                i--;
                subtype++;
            }
        }
        if (i > Blocks.trees.bn)
            subtype = 0;
        for (j = 0; j < 15; j++) {
            if (i > Blocks.glassBlocks.bn) {
                i--;
                subtype++;
            }
        }
        if (i > Blocks.glassBlocks.bn)
            subtype = 0;
        for (j = 0; j < 9; j++) {
            if (i > Blocks.cageBlocks.bn) {
                i--;
                subtype++;
            }
        }
        if (i > Blocks.cageBlocks.bn)
            subtype = 0;
        for (j = 0; j < 15; j++) {
            if (i > Blocks.stoneBlocks1.bn) {
                i--;
                subtype++;
            }
        }
        if (i > Blocks.stoneBlocks1.bn)
            subtype = 0;
        for (j = 0; j < 15; j++) {
            if (i > Blocks.stoneBlocks2.bn) {
                i--;
                subtype++;
            }
        }
        if (i > Blocks.stoneBlocks2.bn)
            subtype = 0;
        for (j = 0; j < 15; j++) {
            if (i > Blocks.stoneBlocks3.bn) {
                i--;
                subtype++;
            }
        }
        if (i > Blocks.stoneBlocks3.bn)
            subtype = 0;
        for (j = 0; j < 15; j++) {
            if (i > Blocks.woodBlocks.bn) {
                i--;
                subtype++;
            }
        }
        if (i > Blocks.woodBlocks.bn)
            subtype = 0;
        for (j = 0; j < 15; j++) {
            if (i > Blocks.halfSteps1.bn) {
                i--;
                subtype++;
            }
        }
        if (i > Blocks.halfSteps1.bn)
            subtype = 0;
        for (j = 0; j < 15; j++) {
            if (i > Blocks.halfSteps2.bn) {
                i--;
                subtype++;
            }
        }
        if (i > Blocks.halfSteps2.bn)
            subtype = 0;
        for (j = 0; j < 15; j++) {
            if (i > Blocks.halfSteps3.bn) {
                i--;
                subtype++;
            }
        }
        if (i > Blocks.halfSteps3.bn)
            subtype = 0;
        for (j = 0; j < 15; j++) {
            if (i > Blocks.tableBlocks.bn) {
                i--;
                subtype++;
            }
        }
        if (i > Blocks.tableBlocks.bn)
            subtype = 0;
        for (j = 0; j < 3; j++) {
            if (i > Blocks.chairBlocks1.bn) {
                i--;
                subtype += 4;
            }
        }
        if (i > Blocks.chairBlocks1.bn)
            subtype = 0;
        for (j = 0; j < 3; j++) {
            if (i > Blocks.chairBlocks2.bn) {
                i--;
                subtype += 4;
            }
        }
        if (i > Blocks.chairBlocks2.bn)
            subtype = 0;
        for (j = 0; j < 14; j++) {
            if (i > Blocks.ropes1.bn) {
                i--;
                subtype++;
            }
        }
        if (i > Blocks.ropes1.bn)
            subtype = 0;
        for (j = 0; j < 14; j++) {
            if (i > Blocks.ropes2.bn) {
                i--;
                subtype++;
            }
        }
        if (i > Blocks.ropes2.bn)
            subtype = 0;
        for (j = 0; j < 8; j++) {
            if (i > Blocks.chains.bn) {
                i--;
                subtype++;
            }
        }
        if (i > Blocks.chains.bn)
            subtype = 0;
        for (j = 0; j < 3; j++) {
            if (i > Blocks.ladders1.bn) {
                i--;
                subtype += 4;
            }
        }
        if (i > Blocks.ladders1.bn)
            subtype = 0;
        for (j = 0; j < 3; j++) {
            if (i > Blocks.ladders2.bn) {
                i--;
                subtype += 4;
            }
        }
        if (i > Blocks.ladders2.bn)
            subtype = 0;
        for (j = 0; j < 3; j++) {
            if (i > Blocks.ladders3.bn) {
                i--;
                subtype += 4;
            }
        }
        if (i > Blocks.ladders3.bn)
            subtype = 0;
        for (j = 0; j < 3; j++) {
            if (i > Blocks.ladders4.bn) {
                i--;
                subtype += 4;
            }
        }
        if (i > Blocks.ladders4.bn)
            subtype = 0;
        for (j = 0; j < 13; j++) {
            if (i > Blocks.lights1.bn) {
                i--;
                subtype++;
            }
        }
        if (i > Blocks.lights1.bn)
            subtype = 0;
        for (j = 0; j < 15; j++) {
            if (i > Blocks.plant2.bn) {
                i--;
                subtype++;
            }
        }
        if (i > Blocks.plant2.bn)
            subtype = 0;
        for (j = 0; j < 15; j++) {
            if (i > Blocks.plant3.bn) {
                i--;
                subtype++;
            }
        }
        if (i > Blocks.plant3.bn)
            subtype = 0;
        for (j = 0; j < 6; j++) {
            if (i > Blocks.overlay1.bn) {
                i--;
                subtype++;
            }
        }
        if (i > Blocks.overlay1.bn)
            subtype = 0;
        for (j = 0; j < 3; j++) {
            if (i > Blocks.stairs1.bn) {
                i--;
                subtype += 4;
            }
        }
        if (i > Blocks.stairs1.bn)
            subtype = 0;
        for (j = 0; j < 3; j++) {
            if (i > Blocks.stairs2.bn) {
                i--;
                subtype += 4;
            }
        }
        if (i > Blocks.stairs2.bn)
            subtype = 0;
        for (j = 0; j < 3; j++) {
            if (i > Blocks.stairs3.bn) {
                i--;
                subtype += 4;
            }
        }
        if (i > Blocks.stairs3.bn)
            subtype = 0;
        for (j = 0; j < 3; j++) {
            if (i > Blocks.stairs4.bn) {
                i--;
                subtype += 4;
            }
        }
        if (i > Blocks.stairs4.bn)
            subtype = 0;
        for (j = 0; j < 3; j++) {
            if (i > Blocks.slopes1.bn) {
                i--;
                subtype += 4;
            }
        }
        if (i > Blocks.slopes1.bn)
            subtype = 0;
        for (j = 0; j < 3; j++) {
            if (i > Blocks.slopes2.bn) {
                i--;
                subtype += 4;
            }
        }
        if (i > Blocks.slopes2.bn)
            subtype = 0;
        for (j = 0; j < 3; j++) {
            if (i > Blocks.slopes3.bn) {
                i--;
                subtype += 4;
            }
        }
        if (i > Blocks.slopes3.bn)
            subtype = 0;
        for (j = 0; j < 3; j++) {
            if (i > Blocks.slopes4.bn) {
                i--;
                subtype += 4;
            }
        }
        if (i > Blocks.slopes4.bn)
            subtype = 0;
        for (j = 0; j < 15; j++) {
            if (i > gm.aU.bf) {
                i--;
                subtype++;
            }
        }
        if (i > gm.aU.bf)
            subtype = 0;
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

    public void fillInventoryBackwards(int offset) {
        boolean filledFirst = false;
        this.atEnd = false;
        for (int i = 0; i < this.size; i++) {
            int id = getID(offset - i);
            if (id > 0 && gm.c[id] != null) {
                this.inventoryContents[this.size - i - 1] = new iz(gm.c[id], -64);
                this.inventoryContents[this.size - i - 1].b(getSubtype(offset - i));
                this.firstItem = offset - i;
                if (!filledFirst) {
                    this.lastItem = offset - i;
                    filledFirst = true;
                }
            } else if (offset - i > 1) {
                i--;
                offset--;
            } else {
                while (i < this.size) {
                    this.inventoryContents[this.size - i - 1] = null;
                    i++;
                }
                return;
            }
        }
    }

    public iz f_(int i) {
        return this.inventoryContents[i];
    }

    public iz a(int i, int j) {
        if (this.inventoryContents[i] != null)
            return this.inventoryContents[i].k();
        return null;
    }

    public void a(int i, iz itemstack) {
        if (this.inventoryContents[i] != null)
            this.inventoryContents[i] = this.inventoryContents[i].k();
    }

    public int a() {
        return this.size;
    }

    public String c() {
        return this.inventoryTitle;
    }

    public int d() {
        return 64;
    }

    public void y_() {}

    public boolean a_(Player entityplayer) {
        return true;
    }
}
