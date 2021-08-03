package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityNpcPath;
import io.github.ryuu.adventurecraft.gui.GuiNpcPath;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.level.Level;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.maths.Box;

public class BlockNpcPath extends TileWithEntity {
    public BlockNpcPath(int i, int j) {
        super(i, j, ln.e);
    }

    protected TileEntity a_() {
        return new TileEntityNpcPath();
    }

    public Box e(Level world, int i, int j, int k) {
        return null;
    }

    public boolean c() {
        return false;
    }

    public boolean shouldRender(xp blockAccess, int i, int j, int k) {
        return DebugMode.active;
    }

    public boolean v_() {
        return DebugMode.active;
    }

    public boolean canBeTriggered() {
        return true;
    }

    public void onTriggerActivated(Level world, int i, int j, int k) {
        TileEntityNpcPath obj = (TileEntityNpcPath)world.b(i, j, k);
        if (obj != null)
            obj.pathEntity();
    }

    public boolean a(Level world, int i, int j, int k, gs entityplayer) {
        if (DebugMode.active && entityplayer.G() != null && (entityplayer.G()).c == Items.cursor.bf) {
            TileEntityNpcPath obj = (TileEntityNpcPath)world.b(i, j, k);
            if (obj != null)
                GuiNpcPath.showUI(obj);
            return true;
        }
        return false;
    }
}
