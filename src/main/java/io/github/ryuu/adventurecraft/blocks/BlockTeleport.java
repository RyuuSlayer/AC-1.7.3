package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityTeleport;
import io.github.ryuu.adventurecraft.items.ItemCursor;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.client.Minecraft;
import net.minecraft.level.Level;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.maths.Box;

public class BlockTeleport extends TileWithEntity {
    protected BlockTeleport(int i, int j) {
        super(i, j, ln.a);
    }

    protected TileEntity a_() {
        return new TileEntityTeleport();
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
        TileEntityTeleport tileEnt = (TileEntityTeleport)world.b(i, j, k);
        int y;
        for (y = tileEnt.y; y < 128; y++) {
            if (world.f(tileEnt.x, y, tileEnt.z) == ln.a)
                break;
        }
        for (Object obj : world.d) {
            gs p = (gs)obj;
            p.e(tileEnt.x + 0.5D, y, tileEnt.z + 0.5D);
        }
    }

    public void onTriggerDeactivated(Level world, int i, int j, int k) {}

    public boolean v_() {
        return DebugMode.active;
    }

    public boolean a(Level world, int i, int j, int k, gs entityplayer) {
        if (DebugMode.active && entityplayer.G() != null && (entityplayer.G()).c == Items.cursor.bf) {
            TileEntityTeleport obj = (TileEntityTeleport)world.b(i, j, k);
            obj.x = ItemCursor.minX;
            obj.y = ItemCursor.minY;
            obj.z = ItemCursor.minZ;
            Minecraft.minecraftInstance.v.a(String.format("Setting Teleport (%d, %d, %d)", new Object[] { Integer.valueOf(obj.x), Integer.valueOf(obj.y), Integer.valueOf(obj.z) }));
            return true;
        }
        return false;
    }
}
