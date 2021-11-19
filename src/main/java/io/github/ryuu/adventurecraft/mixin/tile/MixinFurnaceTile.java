package io.github.ryuu.adventurecraft.mixin.tile;

import net.minecraft.entity.FurnaceEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.MathsHelper;

import java.util.Random;

public class MixinFurnaceTile extends TileWithEntity {
    private Random rand = new Random();
    private final boolean lit;
    private static boolean SETTING_TILE = false;

    protected FurnaceTile(int i, boolean flag) {
        super(i, Material.STONE);
        this.lit = flag;
        this.tex = 45;
    }

    public int getDropId(int meta, Random rand) {
        return Tile.FURNACE.id;
    }

    public void method_1611(Level level, int x, int y, int z) {
        super.method_1611(level, x, y, z);
        this.method_1404(level, x, y, z);
    }

    private void method_1404(Level world, int i, int j, int k) {
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

    public void randomDisplayTick(Level level, int x, int y, int z, Random rand) {
        if (!this.lit) {
            return;
        }
        int l = level.getTileMeta(x, y, z);
        float f = (float)x + 0.5f;
        float f1 = (float)y + 0.0f + rand.nextFloat() * 6.0f / 16.0f;
        float f2 = (float)z + 0.5f;
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

    public boolean activate(Level level, int x, int y, int z, Player player) {
        if (level.isClient) {
            return true;
        }
        FurnaceEntity tileentityfurnace = (FurnaceEntity)level.getTileEntity(x, y, z);
        player.openFurnaceScreen(tileentityfurnace);
        return true;
    }

    public static void method_1403(boolean flag, Level world, int i, int j, int k) {
        int l = world.getTileMeta(i, j, k);
        TileEntity tileentity = world.getTileEntity(i, j, k);
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

    protected TileEntity createTileEntity() {
        return new FurnaceEntity();
    }

    public void afterPlaced(Level world, int i, int j, int k, LivingEntity entityliving) {
        int l = MathsHelper.floor((double)(entityliving.yaw * 4.0f / 360.0f) + 0.5) & 3;
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

    public void onTileRemoved(Level level, int x, int y, int z) {
        if (!SETTING_TILE) {
            FurnaceEntity tileentityfurnace = (FurnaceEntity)level.getTileEntity(x, y, z);
            for (int l = 0; l < tileentityfurnace.getInvSize(); ++l) {
                ItemInstance itemstack = tileentityfurnace.getInvItem(l);
                if (itemstack == null) continue;
                float f = this.rand.nextFloat() * 0.8f + 0.1f;
                float f1 = this.rand.nextFloat() * 0.8f + 0.1f;
                float f2 = this.rand.nextFloat() * 0.8f + 0.1f;
                while (itemstack.count > 0) {
                    int i1 = this.rand.nextInt(21) + 10;
                    if (i1 > itemstack.count) {
                        i1 = itemstack.count;
                    }
                    itemstack.count -= i1;
                    ItemEntity entityitem = new ItemEntity(level, (float)x + f, (float)y + f1, (float)z + f2, new ItemInstance(itemstack.itemId, i1, itemstack.getDamage()));
                    float f3 = 0.05f;
                    entityitem.velocityX = (float)this.rand.nextGaussian() * f3;
                    entityitem.velocityY = (float)this.rand.nextGaussian() * f3 + 0.2f;
                    entityitem.velocityZ = (float)this.rand.nextGaussian() * f3;
                    level.spawnEntity(entityitem);
                }
            }
        }
        super.onTileRemoved(level, x, y, z);
    }
}