package io.github.ryuu.adventurecraft.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import io.github.ryuu.adventurecraft.util.DebugMode;

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

    @Override
    public boolean shouldRender(TileView blockAccess, int i, int j, int k) {
        return DebugMode.active || Minecraft.minecraftInstance.level.triggerManager.isActivated(i, j, k);
    }

    @Override
    public Box getCollisionShape(Level level, int x, int y, int z) {
        if (!level.triggerManager.isActivated(x, y, z) || DebugMode.active) {
            return null;
        }
        return super.getCollisionShape(level, x, y, z);
    }

    @Override
    public boolean canBeTriggered() {
        return true;
    }

    @Override
    public void onTriggerActivated(Level world, int i, int j, int k) {
        world.playSound((double) i + 0.5, (double) j + 0.5, (double) k + 0.5, "random.door_open", 1.0f, world.rand.nextFloat() * 0.1f + 0.9f);
        world.method_243(i, j, k);
    }

    @Override
    public void onTriggerDeactivated(Level world, int i, int j, int k) {
        world.playSound((double) i + 0.5, (double) j + 0.5, (double) k + 0.5, "random.door_close", 1.0f, world.rand.nextFloat() * 0.1f + 0.9f);
        world.method_243(i, j, k);
    }
}
