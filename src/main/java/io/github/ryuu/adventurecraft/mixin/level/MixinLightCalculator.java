package io.github.ryuu.adventurecraft.mixin.level;

import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import net.minecraft.level.Level;
import net.minecraft.level.LightCalculator;
import net.minecraft.level.LightType;
import net.minecraft.tile.Tile;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LightCalculator.class)
public abstract class MixinLightCalculator {

    @Final
    @Shadow
    public LightType type;

    @Shadow
    public int field_1674;

    @Shadow
    public int field_1675;

    @Shadow
    public int field_1676;

    @Shadow
    public int field_1677;

    @Shadow
    public int field_1678;

    @Shadow
    public int field_1679;

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
        for (int k1 = this.field_1674; k1 <= this.field_1677; ++k1) {
            for (int l1 = this.field_1676; l1 <= this.field_1679; ++l1) {
                boolean flag2 = world.isRegionLoaded(k1, 0, l1, 1);
                if (flag2 && world.getChunkFromCache(k1 >> 4, l1 >> 4).method_886()) {
                    flag2 = false;
                }
                if (flag2) {
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
                        if (this.type == LightType.SKY) {
                            if (world.isAboveGround(k1, k2, l1)) {
                                l3 = 15;
                            }
                        } else if (this.type == LightType.BLOCK) {
                            l3 = Tile.BY_ID[j3] != null ? ((ExTile) Tile.BY_ID[j3]).getBlockLightValue(world, k1, k2, l1) : Tile.LUMINANCES[j3];
                        }
                        if (k3 < 15 || l3 != 0) {
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
                        if (l2 == i3) continue;
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
                        if (l1 + 1 < this.field_1679) continue;
                        world.method_165(this.type, k1, k2, l1 + 1, j4);
                    }
                }
            }
        }
    }
}
