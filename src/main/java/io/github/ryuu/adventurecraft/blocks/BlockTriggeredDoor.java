package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.client.Minecraft;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockTriggeredDoor extends Tile {
    protected BlockTriggeredDoor(int i) {
        super(i, Material.WOOD);
        this.tex = 208;
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }

    public boolean shouldRender(TileView blockAccess, int i, int j, int k) {
        return (DebugMode.active || Minecraft.minecraftInstance.level.triggerManager.isActivated(i, j, k));
    }

    @Override
    public Box getCollisionShape(Level world, int i, int j, int k) {
        if (!world.triggerManager.isActivated(i, j, k) || DebugMode.active)
            return null;
        return super.getCollisionShape(world, i, j, k);
    }

    public boolean canBeTriggered() {
        return true;
    }

    public void onTriggerActivated(Level world, int i, int j, int k) {
        world.playSound(i + 0.5D, j + 0.5D, k + 0.5D, "random.door_open", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);
        world.method_243(i, j, k);
    }

    public void onTriggerDeactivated(Level world, int i, int j, int k) {
        world.playSound(i + 0.5D, j + 0.5D, k + 0.5D, "random.door_close", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);
        world.method_243(i, j, k);
    }
}
