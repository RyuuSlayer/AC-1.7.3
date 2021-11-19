package io.github.ryuu.adventurecraft.blocks;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  java.util.Random
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.level.Level
 */
import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;

public class BlockRedstonePower extends Tile {

    protected BlockRedstonePower(int i, int j) {
        super(i, j, Material.STONE);
        this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 0.25f, 1.0f);
        this.luminance(0.07f);
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public int method_1626(TileView iblockaccess, int i, int j, int k, int l) {
        if (l <= 1) {
            if (Minecraft.minecraftInstance.level.triggerManager.isActivated(i, j, k)) {
                return 185;
            }
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

    @Override
    public boolean canBeTriggered() {
        return true;
    }

    @Override
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

    @Override
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

    @Override
    public void randomDisplayTick(Level level, int x, int y, int z, Random rand) {
        boolean activated = level.triggerManager.isActivated(x, y, z);
        if (activated) {
            double d = (double) ((float) x + 0.5f) + (double) (rand.nextFloat() - 0.5f) * 0.2;
            double d1 = (double) ((float) y + 0.95f) + (double) (rand.nextFloat() - 0.5f) * 0.2;
            double d2 = (double) ((float) z + 0.5f) + (double) (rand.nextFloat() - 0.5f) * 0.2;
            level.addParticle("reddust", d, d1, d2, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public int getBlockLightValue(TileView iblockaccess, int i, int j, int k) {
        if (Minecraft.minecraftInstance.level.triggerManager.isActivated(i, j, k)) {
            return 14;
        }
        return 0;
    }
}
