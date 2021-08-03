package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityUrl;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.level.Level;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.maths.Box;

public class BlockUrl extends TileWithEntity {
    protected BlockUrl(int i, int j) {
        super(i, j, ln.a);
    }

    protected TileEntity a_() {
        return new TileEntityUrl();
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
        TileEntityUrl obj = TileEntityUrl)world.b(i, j, k);
        if (obj.url != null && !obj.url.equals(""))
            GuiUrlRequest.showUI(obj.url);
    }

    public boolean a(Level world, int i, int j, int k, gs entityplayer) {
        if (DebugMode.active) {
            TileEntityUrl obj = (TileEntityUrl)world.b(i, j, k);
            GuiUrl.showUI(world, obj);
            return true;
        }
        return false;
    }

    public boolean v_() {
        return DebugMode.active;
    }
}
