/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  java.util.Random
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package io.github.ryuu.adventurecraft.mixin.tile;

import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.FurnaceEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.FurnaceTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.MathsHelper;
import io.github.ryuu.adventurecraft.mixin.item.MixinPlayer;
import io.github.ryuu.adventurecraft.mixin.item.MixinLevel;
import io.github.ryuu.adventurecraft.mixin.item.MixinLivingEntity;
import io.github.ryuu.adventurecraft.mixin.item.MixinTileEntity;
import io.github.ryuu.adventurecraft.mixin.item.MixinItemInstance;
import io.github.ryuu.adventurecraft.mixin.item.MixinItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FurnaceTile.class)
public class MixinFurnaceTile extends TileWithEntity {

    @Shadow()
    private Random rand = new Random();

    private final boolean lit;

    private static boolean SETTING_TILE = false;

    protected MixinFurnaceTile(int i, boolean flag) {
        super(i, Material.STONE);
        this.lit = flag;
        this.tex = 45;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getDropId(int meta, Random rand) {
        return Tile.FURNACE.id;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_1611(MixinLevel level, int x, int y, int z) {
        super.method_1611(level, x, y, z);
        this.method_1404(level, x, y, z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_1404(MixinLevel world, int i, int j, int k) {
        if (world.isClient) {
            return;
        }
        int l = world.getTileId(i, j, k - 1);
        int i1 = world.getTileId(i, j, k + 1);
        int j1 = world.getTileId(i - 1, j, k);
        int k1 = world.getTileId(i + 1, j, k);
        int byte0 = 3;
        if (Tile.FULL_OPAQUE[l] && !Tile.FULL_OPAQUE[i1]) {
            byte0 = 3;
        }
        if (Tile.FULL_OPAQUE[i1] && !Tile.FULL_OPAQUE[l]) {
            byte0 = 2;
        }
        if (Tile.FULL_OPAQUE[j1] && !Tile.FULL_OPAQUE[k1]) {
            byte0 = 5;
        }
        if (Tile.FULL_OPAQUE[k1] && !Tile.FULL_OPAQUE[j1]) {
            byte0 = 4;
        }
        world.setTileMeta(i, j, k, byte0);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int method_1626(TileView iblockaccess, int i, int j, int k, int l) {
        if (l == 1) {
            return this.tex + 17;
        }
        if (l == 0) {
            return this.tex + 17;
        }
        int i1 = iblockaccess.getTileMeta(i, j, k);
        if (l != i1) {
            return this.tex;
        }
        if (this.lit) {
            return this.tex + 16;
        }
        return this.tex - 1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void randomDisplayTick(MixinLevel level, int x, int y, int z, Random rand) {
        if (!this.lit) {
            return;
        }
        int l = level.getTileMeta(x, y, z);
        float f = (float) x + 0.5f;
        float f1 = (float) y + 0.0f + rand.nextFloat() * 6.0f / 16.0f;
        float f2 = (float) z + 0.5f;
        float f3 = 0.52f;
        float f4 = rand.nextFloat() * 0.6f - 0.3f;
        if (l == 4) {
            level.addParticle("smoke", f - f3, f1, f2 + f4, 0.0, 0.0, 0.0);
            level.addParticle("flame", f - f3, f1, f2 + f4, 0.0, 0.0, 0.0);
        } else if (l == 5) {
            level.addParticle("smoke", f + f3, f1, f2 + f4, 0.0, 0.0, 0.0);
            level.addParticle("flame", f + f3, f1, f2 + f4, 0.0, 0.0, 0.0);
        } else if (l == 2) {
            level.addParticle("smoke", f + f4, f1, f2 - f3, 0.0, 0.0, 0.0);
            level.addParticle("flame", f + f4, f1, f2 - f3, 0.0, 0.0, 0.0);
        } else if (l == 3) {
            level.addParticle("smoke", f + f4, f1, f2 + f3, 0.0, 0.0, 0.0);
            level.addParticle("flame", f + f4, f1, f2 + f3, 0.0, 0.0, 0.0);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getTextureForSide(int side) {
        if (side == 1) {
            return this.tex + 17;
        }
        if (side == 0) {
            return this.tex + 17;
        }
        if (side == 3) {
            return this.tex - 1;
        }
        return this.tex;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean activate(MixinLevel level, int x, int y, int z, MixinPlayer player) {
        if (level.isClient) {
            return true;
        }
        FurnaceEntity tileentityfurnace = (FurnaceEntity) level.getTileEntity(x, y, z);
        player.openFurnaceScreen(tileentityfurnace);
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static void method_1403(boolean flag, MixinLevel world, int i, int j, int k) {
        int l = world.getTileMeta(i, j, k);
        MixinTileEntity tileentity = world.getTileEntity(i, j, k);
        SETTING_TILE = true;
        if (flag) {
            world.setTile(i, j, k, Tile.FURNACE_LIT.id);
        } else {
            world.setTile(i, j, k, Tile.FURNACE.id);
        }
        SETTING_TILE = false;
        world.setTileMeta(i, j, k, l);
        tileentity.validate();
        world.setTileEntity(i, j, k, tileentity);
        tileentity.validate();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected MixinTileEntity createTileEntity() {
        return new FurnaceEntity();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void afterPlaced(MixinLevel world, int i, int j, int k, MixinLivingEntity entityliving) {
        int l = MathsHelper.floor((double) (entityliving.yaw * 4.0f / 360.0f) + 0.5) & 3;
        if (l == 0) {
            world.setTileMeta(i, j, k, 2);
        }
        if (l == 1) {
            world.setTileMeta(i, j, k, 5);
        }
        if (l == 2) {
            world.setTileMeta(i, j, k, 3);
        }
        if (l == 3) {
            world.setTileMeta(i, j, k, 4);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void onTileRemoved(MixinLevel level, int x, int y, int z) {
        if (!SETTING_TILE) {
            FurnaceEntity tileentityfurnace = (FurnaceEntity) level.getTileEntity(x, y, z);
            for (int l = 0; l < tileentityfurnace.getInvSize(); ++l) {
                MixinItemInstance itemstack = tileentityfurnace.getInvItem(l);
                if (itemstack == null)
                    continue;
                float f = this.rand.nextFloat() * 0.8f + 0.1f;
                float f1 = this.rand.nextFloat() * 0.8f + 0.1f;
                float f2 = this.rand.nextFloat() * 0.8f + 0.1f;
                while (itemstack.count > 0) {
                    int i1 = this.rand.nextInt(21) + 10;
                    if (i1 > itemstack.count) {
                        i1 = itemstack.count;
                    }
                    itemstack.count -= i1;
                    MixinItemEntity entityitem = new MixinItemEntity(level, (float) x + f, (float) y + f1, (float) z + f2, new MixinItemInstance(itemstack.itemId, i1, itemstack.getDamage()));
                    float f3 = 0.05f;
                    entityitem.velocityX = (float) this.rand.nextGaussian() * f3;
                    entityitem.velocityY = (float) this.rand.nextGaussian() * f3 + 0.2f;
                    entityitem.velocityZ = (float) this.rand.nextGaussian() * f3;
                    level.spawnEntity(entityitem);
                }
            }
        }
        super.onTileRemoved(level, x, y, z);
    }
}
