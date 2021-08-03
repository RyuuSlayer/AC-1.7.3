package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockLightBulb extends Tile {
    protected BlockLightBulb(int i, int j) {
        super(i, j, Material.AIR);
    }

    public boolean c() {
        return false;
    }

    public Box e(Level world, int i, int j, int k) {
        return null;
    }

    public boolean shouldRender(xp blockAccess, int i, int j, int k) {
        return DebugMode.active;
    }

    public boolean canBeTriggered() {
        return true;
    }

    public void onTriggerActivated(Level world, int i, int j, int k) {
        int m = world.e(i, j, k);
        world.b(i, j, k, 0, 0);
        world.b(i, j, k, this.bn, m);
    }

    public void onTriggerDeactivated(Level world, int i, int j, int k) {
        int m = world.e(i, j, k);
        world.b(i, j, k, 0, 0);
        world.b(i, j, k, this.bn, m);
    }

    public int getBlockLightValue(xp iblockaccess, int i, int j, int k) {
        if (!Minecraft.minecraftInstance.f.triggerManager.isActivated(i, j, k))
            return iblockaccess.e(i, j, k);
        return 0;
    }

    public void e(Level world, int i, int j, int k, int l) {
        world.d(i, j, k, 15);
    }

    public boolean v_() {
        return DebugMode.active;
    }

    public boolean d() {
        return false;
    }

    public int b() {
        return 1;
    }

    public boolean a(Level world, int i, int j, int k, Player entityplayer) {
        if (DebugMode.active)
            GuiLightBulb.showUI(world, i, j, k);
        return true;
    }
}