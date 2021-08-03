package io.github.ryuu.adventurecraft.blocks;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;

public class BlockRedstonePower extends Tile {
    protected BlockRedstonePower(int i, int j) {
        super(i, j, Material.STONE);
        a(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
        a(0.07F);
    }

    public boolean c() {
        return false;
    }

    public int a(xp iblockaccess, int i, int j, int k, int l) {
        if (l <= 1) {
            if (Minecraft.minecraftInstance.f.triggerManager.isActivated(i, j, k))
                return 185;
            return 186;
        }
        return 5;
    }

    public boolean d() {
        return false;
    }

    public int b() {
        return 31;
    }

    public boolean canBeTriggered() {
        return true;
    }

    public void onTriggerActivated(Level world, int i, int j, int k) {
        world.b(i, j, k, 0, 0);
        world.b(i, j, k, this.bn, 0);
        world.j(i, j, k);
        world.i(i, j, k, this.bn);
        world.i(i, j - 1, k, this.bn);
        world.i(i, j + 1, k, this.bn);
        world.i(i - 1, j, k, this.bn);
        world.i(i + 1, j, k, this.bn);
        world.i(i, j, k - 1, this.bn);
        world.i(i, j, k + 1, this.bn);
    }

    public void onTriggerDeactivated(Level world, int i, int j, int k) {
        world.b(i, j, k, 0, 0);
        world.b(i, j, k, this.bn, 0);
        world.j(i, j, k);
        world.i(i, j, k, this.bn);
        world.i(i, j - 1, k, this.bn);
        world.i(i, j + 1, k, this.bn);
        world.i(i - 1, j, k, this.bn);
        world.i(i + 1, j, k, this.bn);
        world.i(i, j, k - 1, this.bn);
        world.i(i, j, k + 1, this.bn);
    }

    public boolean f() {
        return true;
    }

    public boolean c(xp iblockaccess, int i, int j, int k, int l) {
        return Minecraft.minecraftInstance.f.triggerManager.isActivated(i, j, k);
    }

    public boolean d(Level world, int i, int j, int k, int l) {
        return world.triggerManager.isActivated(i, j, k);
    }

    public void b(Level world, int i, int j, int k, Random random) {
        boolean activated = world.triggerManager.isActivated(i, j, k);
        if (activated) {
            double d = (i + 0.5F) + (random.nextFloat() - 0.5F) * 0.2D;
            double d1 = (j + 0.95F) + (random.nextFloat() - 0.5F) * 0.2D;
            double d2 = (k + 0.5F) + (random.nextFloat() - 0.5F) * 0.2D;
            world.a("reddust", d, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }

    public int getBlockLightValue(xp iblockaccess, int i, int j, int k) {
        if (Minecraft.minecraftInstance.f.triggerManager.isActivated(i, j, k))
            return 14;
        return 0;
    }
}
