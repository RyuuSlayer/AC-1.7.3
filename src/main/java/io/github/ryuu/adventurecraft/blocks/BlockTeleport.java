package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityTeleport;
import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import io.github.ryuu.adventurecraft.items.ItemCursor;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockTeleport extends TileWithEntity implements AcTriggerTile, AcRenderConditionTile {

    protected BlockTeleport(int i, int j) {
        super(i, j, Material.AIR);
        this.hardness(5.0f);
        this.sounds(Tile.METAL_SOUNDS);
        ((ExTile) this).setTextureNum(2);
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
        TileEntityTeleport tileEnt = (TileEntityTeleport) world.getTileEntity(i, j, k);
        int y = tileEnt.y;
        while (y < 128 && world.getMaterial(tileEnt.x, y, tileEnt.z) != Material.AIR) {
            ++y;
        }
        for (Object obj : world.players) {
            Player p = (Player) obj;
            p.setPosition((double) tileEnt.x + 0.5, y, (double) tileEnt.z + 0.5);
        }
    }

    @Override
    public void onTriggerDeactivated(Level world, int i, int j, int k) {
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }

    @Override
    public boolean activate(Level level, int x, int y, int z, Player player) {
        if (DebugMode.active && player.getHeldItem() != null && player.getHeldItem().itemId == Items.cursor.id) {
            TileEntityTeleport obj = (TileEntityTeleport) level.getTileEntity(x, y, z);
            obj.x = ItemCursor.minX;
            obj.y = ItemCursor.minY;
            obj.z = ItemCursor.minZ;
            AccessMinecraft.getInstance().overlay.addChatMessage(String.format("Setting Teleport (%d, %d, %d)", obj.x, obj.y, obj.z));
            return true;
        }
        return false;
    }
}
