package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.client.Minecraft;
import net.minecraft.level.TileView;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockTeleport extends TileWithEntity {

    protected BlockTeleport(int i, int j) {
        super(i, j, Material.AIR);
    }

    @Override
    protected MixinTileEntity createTileEntity() {
        return new TileEntityTeleport();
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
        int y;
        TileEntityTeleport tileEnt = (TileEntityTeleport) world.getTileEntity(i, j, k);
        for (y = tileEnt.y; y < 128 && world.getMaterial(tileEnt.x, y, tileEnt.z) != Material.AIR; ++y) {
        }
        for (Object obj : world.players) {
            MixinPlayer p = (MixinPlayer) obj;
            p.setPosition((double) tileEnt.x + 0.5, y, (double) tileEnt.z + 0.5);
        }
    }

    @Override
    public void onTriggerDeactivated(MixinLevel world, int i, int j, int k) {
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }

    @Override
    public boolean activate(MixinLevel level, int x, int y, int z, MixinPlayer player) {
        if (DebugMode.active && player.getHeldItem() != null && player.getHeldItem().itemId == Items.cursor.id) {
            TileEntityTeleport obj = (TileEntityTeleport) level.getTileEntity(x, y, z);
            obj.x = ItemCursor.minX;
            obj.y = ItemCursor.minY;
            obj.z = ItemCursor.minZ;
            Minecraft.minecraftInstance.overlay.addChatMessage(String.format("Setting Teleport (%d, %d, %d)", new Object[]{obj.x, obj.y, obj.z}));
            return true;
        }
        return false;
    }
}
