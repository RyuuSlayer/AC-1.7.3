package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityTeleport;
import io.github.ryuu.adventurecraft.items.ItemCursor;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockTeleport extends TileWithEntity {
    protected BlockTeleport(int i, int j) {
        super(i, j, Material.AIR);
    }

    @Override
    protected TileEntity createTileEntity() {
        return new TileEntityTeleport();
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public Box getCollisionShape(Level world, int i, int j, int k) {
        return null;
    }

    public boolean shouldRender(TileView blockAccess, int i, int j, int k) {
        return DebugMode.active;
    }

    public boolean canBeTriggered() {
        return true;
    }

    public void onTriggerActivated(Level world, int i, int j, int k) {
        TileEntityTeleport tileEnt = (TileEntityTeleport) world.getTileEntity(i, j, k);
        int y;
        for (y = tileEnt.y; y < 128; y++) {
            if (world.getMaterial(tileEnt.x, y, tileEnt.z) == Material.AIR)
                break;
        }
        for (Object obj : world.players) {
            Player p = (Player) obj;
            p.setPosition(tileEnt.x + 0.5D, y, tileEnt.z + 0.5D);
        }
    }

    public void onTriggerDeactivated(Level world, int i, int j, int k) {
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }

    @Override
    public boolean activate(Level world, int i, int j, int k, Player entityplayer) {
        if (DebugMode.active && entityplayer.getHeldItem() != null && (entityplayer.getHeldItem()).itemId == Items.cursor.id) {
            TileEntityTeleport obj = (TileEntityTeleport) world.getTileEntity(i, j, k);
            obj.x = ItemCursor.minX;
            obj.y = ItemCursor.minY;
            obj.z = ItemCursor.minZ;
            Minecraft.minecraftInstance.overlay.addChatMessage(String.format("Setting Teleport (%d, %d, %d)", obj.x, obj.y, obj.z));
            return true;
        }
        return false;
    }
}
