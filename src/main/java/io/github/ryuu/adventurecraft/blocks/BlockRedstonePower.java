package io.github.ryuu.adventurecraft.blocks;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;

public class BlockRedstonePower extends Tile {
    protected BlockRedstonePower(int i, int j) {
        super(i, j, Material.STONE);
        setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
        luminance(0.07F);
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public int method_1626(TileView iblockaccess, int i, int j, int k, int l) {
        if (l <= 1) {
            if (Minecraft.minecraftInstance.level.triggerManager.isActivated(i, j, k))
                return 185;
            return 186;
        }
        return 5;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public int method_1621() {
        return 31;
    }

    public boolean canBeTriggered() {
        return true;
    }

    public void onTriggerActivated(Level world, int i, int j, int k) {
        world.method_201(i, j, k, 0, 0);
        world.method_201(i, j, k, this.id, 0);
        world.method_243(i, j, k);
        world.method_244(i, j, k, this.id);
        world.method_244(i, j - 1, k, this.id);
        world.method_244(i, j + 1, k, this.id);
        world.method_244(i - 1, j, k, this.id);
        world.method_244(i + 1, j, k, this.id);
        world.method_244(i, j, k - 1, this.id);
        world.method_244(i, j, k + 1, this.id);
    }

    public void onTriggerDeactivated(Level world, int i, int j, int k) {
        world.method_201(i, j, k, 0, 0);
        world.method_201(i, j, k, this.id, 0);
        world.method_243(i, j, k);
        world.method_244(i, j, k, this.id);
        world.method_244(i, j - 1, k, this.id);
        world.method_244(i, j + 1, k, this.id);
        world.method_244(i - 1, j, k, this.id);
        world.method_244(i + 1, j, k, this.id);
        world.method_244(i, j, k - 1, this.id);
        world.method_244(i, j, k + 1, this.id);
    }

    @Override
    public boolean emitsRedstonePower() {
        return true;
    }

    @Override
    public boolean method_1568(TileView iblockaccess, int i, int j, int k, int l) {
        return Minecraft.minecraftInstance.level.triggerManager.isActivated(i, j, k);
    }

    @Override
    public boolean method_1570(Level world, int i, int j, int k, int l) {
        return world.triggerManager.isActivated(i, j, k);
    }

    public void randomDisplayTick(Level world, int i, int j, int k, Random random) {
        boolean activated = world.triggerManager.isActivated(i, j, k);
        if (activated) {
            double d = (i + 0.5F) + (random.nextFloat() - 0.5F) * 0.2D;
            double d1 = (j + 0.95F) + (random.nextFloat() - 0.5F) * 0.2D;
            double d2 = (k + 0.5F) + (random.nextFloat() - 0.5F) * 0.2D;
            world.addParticle("reddust", d, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }

    public int getBlockLightValue(TileView iblockaccess, int i, int j, int k) {
        if (Minecraft.minecraftInstance.level.triggerManager.isActivated(i, j, k))
            return 14;
        return 0;
    }
}
