package io.github.ryuu.adventurecraft.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.gui.GuiUrlRequest;
import io.github.ryuu.adventurecraft.gui.GuiUrl;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityUrl;

public class BlockUrl extends TileWithEntity {

    protected BlockUrl(int i, int j) {
        super(i, j, Material.AIR);
    }

    @Override
    protected TileEntity createTileEntity() {
        return new TileEntityUrl();
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
        TileEntityUrl obj = (TileEntityUrl) world.getTileEntity(i, j, k);
        if (obj.url != null && !obj.url.equals((Object) "")) {
            GuiUrlRequest.showUI(obj.url);
        }
    }

    @Override
    public boolean activate(Level level, int x, int y, int z, Player player) {
        if (DebugMode.active) {
            TileEntityUrl obj = (TileEntityUrl) level.getTileEntity(x, y, z);
            GuiUrl.showUI(level, obj);
            return true;
        }
        return false;
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }
}
