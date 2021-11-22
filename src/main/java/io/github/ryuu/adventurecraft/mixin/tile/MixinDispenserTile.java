package io.github.ryuu.adventurecraft.mixin.tile;

import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.entities.EntityArrowBomb;

@Mixin(DispenserTile.class)
public class MixinDispenserTile extends TileWithEntity {

    @Shadow()
    private Random rand = new Random();

    protected MixinDispenserTile(int id) {
        super(id, Material.STONE);
        this.tex = 45;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_1611(Level level, int x, int y, int z) {
        super.method_1611(level, x, y, z);
        this.method_1775(level, x, y, z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_1775(Level world, int i, int j, int k) {
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
        return this.tex + 1;
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
            return this.tex + 1;
        }
        return this.tex;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean activate(Level level, int x, int y, int z, Player player) {
        if (!DebugMode.active) {
            return false;
        }
        if (level.isClient) {
            return true;
        }
        Dispenser tileentitydispenser = (Dispenser) level.getTileEntity(x, y, z);
        player.openDispenserScreen(tileentitydispenser);
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_1774(Level world, int i, int j, int k, Random random) {
        int l = world.getTileMeta(i, j, k);
        int i1 = 0;
        int j1 = 0;
        if (l == 3) {
            j1 = 1;
        } else if (l == 2) {
            j1 = -1;
        } else {
            i1 = l == 5 ? 1 : -1;
        }
        Dispenser tileentitydispenser = (Dispenser) world.getTileEntity(i, j, k);
        ItemInstance itemstack = tileentitydispenser.getItemToDispense();
        double d = (double) i + (double) i1 * 0.6 + 0.5;
        double d1 = (double) j + 0.5;
        double d2 = (double) k + (double) j1 * 0.6 + 0.5;
        if (itemstack == null) {
            world.playLevelEvent(1001, i, j, k, 0);
        } else {
            if (itemstack.itemId == ItemType.arrow.id) {
                Arrow entityarrow = new Arrow(world, d, d1, d2);
                entityarrow.method_1291(i1, 0.1f, j1, 1.1f, 6.0f);
                entityarrow.player = true;
                world.spawnEntity(entityarrow);
                world.playLevelEvent(1002, i, j, k, 0);
            } else if (itemstack.itemId == ItemType.egg.id) {
                ThrownEgg entityegg = new ThrownEgg(world, d, d1, d2);
                entityegg.method_1682(i1, 0.1f, j1, 1.1f, 6.0f);
                world.spawnEntity(entityegg);
                world.playLevelEvent(1002, i, j, k, 0);
            } else if (itemstack.itemId == ItemType.snowball.id) {
                ThrownSnowball entitysnowball = new ThrownSnowball(world, d, d1, d2);
                entitysnowball.method_1656(i1, 0.1f, j1, 1.1f, 6.0f);
                world.spawnEntity(entitysnowball);
                world.playLevelEvent(1002, i, j, k, 0);
            } else if (itemstack.itemId == Items.bombArow.id) {
                EntityArrowBomb entityarrow = new EntityArrowBomb(world, d, d1, d2);
                entityarrow.method_1291(i1, 0.1f, j1, 1.1f, 6.0f);
                world.spawnEntity(entityarrow);
                world.playLevelEvent(1002, i, j, k, 0);
            } else {
                ItemEntity entityitem = new ItemEntity(world, d, d1 - 0.3, d2, itemstack);
                double d3 = random.nextDouble() * 0.1 + 0.2;
                entityitem.velocityX = (double) i1 * d3;
                entityitem.velocityY = 0.2f;
                entityitem.velocityZ = (double) j1 * d3;
                entityitem.velocityX += random.nextGaussian() * (double) 0.0075f * 6.0;
                entityitem.velocityY += random.nextGaussian() * (double) 0.0075f * 6.0;
                entityitem.velocityZ += random.nextGaussian() * (double) 0.0075f * 6.0;
                world.spawnEntity(entityitem);
                world.playLevelEvent(1000, i, j, k, 0);
            }
            world.playLevelEvent(2000, i, j, k, i1 + 1 + (j1 + 1) * 3);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_1609(Level level, int x, int y, int z, int id) {
        if (id > 0 && Tile.BY_ID[id].emitsRedstonePower()) {
            boolean flag;
            boolean bl = flag = level.hasRedstonePower(x, y, z) || level.hasRedstonePower(x, y + 1, z);
            if (flag) {
                level.method_216(x, y, z, this.id, this.getTickrate());
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void onScheduledTick(Level level, int x, int y, int z, Random rand) {
        if (level.hasRedstonePower(x, y, z) || level.hasRedstonePower(x, y + 1, z)) {
            this.method_1774(level, x, y, z, rand);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void afterPlaced(Level world, int i, int j, int k, LivingEntity entityliving) {
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
    public void onTileRemoved(Level level, int x, int y, int z) {
        Dispenser tileentitydispenser = (Dispenser) level.getTileEntity(x, y, z);
        for (int l = 0; l < tileentitydispenser.getInvSize(); ++l) {
            ItemInstance itemstack = tileentitydispenser.getInvItem(l);
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
                ItemEntity entityitem = new ItemEntity(level, (float) x + f, (float) y + f1, (float) z + f2, new ItemInstance(itemstack.itemId, i1, itemstack.getDamage()));
                float f3 = 0.05f;
                entityitem.velocityX = (float) this.rand.nextGaussian() * f3;
                entityitem.velocityY = (float) this.rand.nextGaussian() * f3 + 0.2f;
                entityitem.velocityZ = (float) this.rand.nextGaussian() * f3;
                level.spawnEntity(entityitem);
            }
        }
        super.onTileRemoved(level, x, y, z);
    }
}
