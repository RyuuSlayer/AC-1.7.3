package io.github.ryuu.adventurecraft.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.gui.GuiLightBulb;

public class BlockLightBulb extends Tile {

    protected BlockLightBulb(int i, int j) {
        super(i, j, Material.AIR);
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public Box getCollisionShape(Level level, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean shouldRender(TileView blockAccess, int i, int j, int k) {
        return DebugMode.active;
    }

    @Override
    public boolean canBeTriggered() {
        return true;
    }

    @Override
    public void onTriggerActivated(Level world, int i, int j, int k) {
        int m = world.getTileMeta(i, j, k);
        world.method_201(i, j, k, 0, 0);
        world.method_201(i, j, k, this.id, m);
    }

    @Override
    public void onTriggerDeactivated(Level world, int i, int j, int k) {
        int m = world.getTileMeta(i, j, k);
        world.method_201(i, j, k, 0, 0);
        world.method_201(i, j, k, this.id, m);
    }

    @Override
    public int getBlockLightValue(TileView iblockaccess, int i, int j, int k) {
        if (!Minecraft.minecraftInstance.level.triggerManager.isActivated(i, j, k)) {
            return iblockaccess.getTileMeta(i, j, k);
        }
        return 0;
    }

    @Override
    public void onPlaced(Level level, int x, int y, int z, int facing) {
        level.setTileMeta(x, y, z, 15);
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public int method_1621() {
        return 1;
    }

    @Override
    public boolean activate(Level level, int x, int y, int z, Player player) {
        if (DebugMode.active) {
            GuiLightBulb.showUI(level, x, y, z);
        }
        return true;
    }
}
