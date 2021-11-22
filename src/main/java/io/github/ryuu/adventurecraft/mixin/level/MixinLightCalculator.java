package io.github.ryuu.adventurecraft.mixin.level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LightCalculator.class)
public class MixinLightCalculator {

    @Shadow()
    public final LightType type;

    public int field_1674;

    public int field_1675;

    public int field_1676;

    public int field_1677;

    public int field_1678;

    public int field_1679;

    public MixinLightCalculator(LightType enumskyblock, int i, int j, int k, int l, int i1, int j1) {
        this.type = enumskyblock;
        this.field_1674 = i;
        this.field_1675 = j;
        this.field_1676 = k;
        this.field_1677 = l;
        this.field_1678 = i1;
        this.field_1679 = j1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void calculateLight(Level world) {
        int i = this.field_1677 - this.field_1674 + 1;
        int j = this.field_1678 - this.field_1675 + 1;
        int k = this.field_1679 - this.field_1676 + 1;
        int l = i * j * k;
        if (l > 32768) {
            System.out.println("Light too large, skipping!");
            return;
        }
        int i1 = 0;
        int j1 = 0;
        boolean flag = false;
        boolean flag1 = false;
        for (int k1 = this.field_1674; k1 <= this.field_1677; ++k1) {
            for (int l1 = this.field_1676; l1 <= this.field_1679; ++l1) {
                int i2 = k1 >> 4;
                int j2 = l1 >> 4;
                boolean flag2 = false;
                if (flag && i2 == i1 && j2 == j1) {
                    flag2 = flag1;
                } else {
                    Chunk chunk;
                    flag2 = world.isRegionLoaded(k1, 0, l1, 1);
                    if (flag2 && (chunk = world.getChunkFromCache(k1 >> 4, l1 >> 4)).method_886()) {
                        flag2 = false;
                    }
                    flag1 = flag2;
                    i1 = i2;
                    j1 = j2;
                }
                if (!flag2)
                    continue;
                if (this.field_1675 < 0) {
                    this.field_1675 = 0;
                }
                if (this.field_1678 >= 128) {
                    this.field_1678 = 127;
                }
                for (int k2 = this.field_1675; k2 <= this.field_1678; ++k2) {
                    int l2 = world.getLightLevel(this.type, k1, k2, l1);
                    int i3 = 0;
                    int j3 = world.getTileId(k1, k2, l1);
                    int k3 = Tile.field_1941[j3];
                    if (k3 == 0) {
                        k3 = 1;
                    }
                    int l3 = 0;
                    if (this.type == LightType.Sky) {
                        if (world.isAboveGround(k1, k2, l1)) {
                            l3 = 15;
                        }
                    } else if (this.type == LightType.Block) {
                        l3 = Tile.BY_ID[j3] != null ? Tile.BY_ID[j3].getBlockLightValue(world, k1, k2, l1) : Tile.LUMINANCES[j3];
                    }
                    if (k3 >= 15 && l3 == 0) {
                        i3 = 0;
                    } else {
                        int i4 = world.getLightLevel(this.type, k1 - 1, k2, l1);
                        int k4 = world.getLightLevel(this.type, k1 + 1, k2, l1);
                        int l4 = world.getLightLevel(this.type, k1, k2 - 1, l1);
                        int i5 = world.getLightLevel(this.type, k1, k2 + 1, l1);
                        int j5 = world.getLightLevel(this.type, k1, k2, l1 - 1);
                        int k5 = world.getLightLevel(this.type, k1, k2, l1 + 1);
                        i3 = i4;
                        if (k4 > i3) {
                            i3 = k4;
                        }
                        if (l4 > i3) {
                            i3 = l4;
                        }
                        if (i5 > i3) {
                            i3 = i5;
                        }
                        if (j5 > i3) {
                            i3 = j5;
                        }
                        if (k5 > i3) {
                            i3 = k5;
                        }
                        if ((i3 -= k3) < 0) {
                            i3 = 0;
                        }
                        if (l3 > i3) {
                            i3 = l3;
                        }
                    }
                    if (l2 == i3)
                        continue;
                    world.setLightLevel(this.type, k1, k2, l1, i3);
                    int j4 = i3 - 1;
                    if (j4 < 0) {
                        j4 = 0;
                    }
                    world.method_165(this.type, k1 - 1, k2, l1, j4);
                    world.method_165(this.type, k1, k2 - 1, l1, j4);
                    world.method_165(this.type, k1, k2, l1 - 1, j4);
                    if (k1 + 1 >= this.field_1677) {
                        world.method_165(this.type, k1 + 1, k2, l1, j4);
                    }
                    if (k2 + 1 >= this.field_1678) {
                        world.method_165(this.type, k1, k2 + 1, l1, j4);
                    }
                    if (l1 + 1 < this.field_1679)
                        continue;
                    world.method_165(this.type, k1, k2, l1 + 1, j4);
                }
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean propagateLight(int i, int j, int k, int l, int i1, int j1) {
        if (i >= this.field_1674 && j >= this.field_1675 && k >= this.field_1676 && l <= this.field_1677 && i1 <= this.field_1678 && j1 <= this.field_1679) {
            return true;
        }
        int k1 = 1;
        if (i >= this.field_1674 - k1 && j >= this.field_1675 - k1 && k >= this.field_1676 - k1 && l <= this.field_1677 + k1 && i1 <= this.field_1678 + k1 && j1 <= this.field_1679 + k1) {
            int j3;
            int i3;
            int l2;
            int k2;
            int k3;
            int l1 = this.field_1677 - this.field_1674;
            int i2 = this.field_1678 - this.field_1675;
            int j2 = this.field_1679 - this.field_1676;
            if (i > this.field_1674) {
                i = this.field_1674;
            }
            if (j > this.field_1675) {
                j = this.field_1675;
            }
            if (k > this.field_1676) {
                k = this.field_1676;
            }
            if (l < this.field_1677) {
                l = this.field_1677;
            }
            if (i1 < this.field_1678) {
                i1 = this.field_1678;
            }
            if (j1 < this.field_1679) {
                j1 = this.field_1679;
            }
            if ((k3 = (k2 = l - i) * (l2 = i1 - j) * (i3 = j1 - k)) - (j3 = l1 * i2 * j2) <= 2) {
                this.field_1674 = i;
                this.field_1675 = j;
                this.field_1676 = k;
                this.field_1677 = l;
                this.field_1678 = i1;
                this.field_1679 = j1;
                return true;
            }
        }
        return false;
    }
}
