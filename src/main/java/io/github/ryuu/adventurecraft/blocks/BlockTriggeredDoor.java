package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.client.Minecraft;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.util.maths.Box;

public class BlockTriggeredDoor extends Tile {
    protected BlockTriggeredDoor(int i) {
        super(i, ln.d);
        this.bm = 208;
    }

    public boolean c() {
        return false;
    }

    public boolean v_() {
        return DebugMode.active;
    }

    public boolean shouldRender(xp blockAccess, int i, int j, int k) {
        return (DebugMode.active || Minecraft.minecraftInstance.f.triggerManager.isActivated(i, j, k));
    }

    public Box e(Level world, int i, int j, int k) {
        if (!world.triggerManager.isActivated(i, j, k) || DebugMode.active)
            return null;
        return super.e(world, i, j, k);
    }

    public boolean canBeTriggered() {
        return true;
    }

    public void onTriggerActivated(Level world, int i, int j, int k) {
        world.a(i + 0.5D, j + 0.5D, k + 0.5D, "random.door_open", 1.0F, world.r.nextFloat() * 0.1F + 0.9F);
        world.j(i, j, k);
    }

    public void onTriggerDeactivated(Level world, int i, int j, int k) {
        world.a(i + 0.5D, j + 0.5D, k + 0.5D, "random.door_close", 1.0F, world.r.nextFloat() * 0.1F + 0.9F);
        world.j(i, j, k);
    }
}
