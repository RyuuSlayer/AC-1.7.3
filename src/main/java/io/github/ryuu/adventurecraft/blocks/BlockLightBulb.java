package io.github.ryuu.adventurecraft.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockLightBulb extends MixinTile {

    protected BlockLightBulb(int i, int j) {
        super(i, j, Material.AIR);
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public Box getCollisionShape(MixinLevel level, int x, int y, int z) {
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
    public void onTriggerActivated(MixinLevel world, int i, int j, int k) {
        int m = world.getTileMeta(i, j, k);
        world.method_201(i, j, k, 0, 0);
        world.method_201(i, j, k, this.id, m);
    }

    @Override
    public void onTriggerDeactivated(MixinLevel world, int i, int j, int k) {
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
    public void onPlaced(MixinLevel level, int x, int y, int z, int facing) {
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
    public boolean activate(MixinLevel level, int x, int y, int z, MixinPlayer player) {
        if (DebugMode.active) {
            GuiLightBulb.showUI(level, x, y, z);
        }
        return true;
    }
}
