package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityTriggerInverter;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import io.github.ryuu.adventurecraft.gui.GuiTriggerInverter;
import io.github.ryuu.adventurecraft.items.ItemCursor;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.TriggerArea;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

import java.util.Random;

public class BlockTriggerInverter extends TileWithEntity implements AcTriggerTile, AcRenderConditionTile, AcResetTile {

    protected BlockTriggerInverter(int i, int j) {
        super(i, j, Material.AIR);
        this.hardness(5.0f);
        this.sounds(Tile.METAL_SOUNDS);
        ((ExTile) this).setTextureNum(2);
    }

    @Override
    protected TileEntity createTileEntity() {
        return new TileEntityTriggerInverter();
    }

    @Override
    public int getDropId(int meta, Random rand) {
        return 0;
    }

    @Override
    public int getDropCount(Random rand) {
        return 0;
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
    public int method_1626(TileView iblockaccess, int i, int j, int k, int l) {
        return super.method_1626(iblockaccess, i, j, k, l);
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }

    @Override
    public boolean canBeTriggered() {
        return true;
    }

    @Override
    public void onTriggerActivated(Level level, int i, int j, int k) {
        ((ExLevel) level).getTriggerManager().removeArea(i, j, k);
    }

    @Override
    public void onTriggerDeactivated(Level level, int i, int j, int k) {
        TileEntityTriggerInverter obj = (TileEntityTriggerInverter) level.getTileEntity(i, j, k);
        ((ExLevel) level).getTriggerManager().addArea(i, j, k, new TriggerArea(obj.minX, obj.minY, obj.minZ, obj.maxX, obj.maxY, obj.maxZ));
    }

    public void setTriggerToSelection(Level level, int i, int j, int k) {
        TileEntityTriggerInverter obj = (TileEntityTriggerInverter) level.getTileEntity(i, j, k);
        if (obj.minX == ItemCursor.minX && obj.minY == ItemCursor.minY && obj.minZ == ItemCursor.minZ && obj.maxX == ItemCursor.maxX && obj.maxY == ItemCursor.maxY && obj.maxZ == ItemCursor.maxZ) {
            return;
        }
        obj.set(ItemCursor.minX, ItemCursor.minY, ItemCursor.minZ, ItemCursor.maxX, ItemCursor.maxY, ItemCursor.maxZ);
    }

    @Override
    public boolean activate(Level level, int x, int y, int z, Player player) {
        if (DebugMode.active && player.getHeldItem() != null && player.getHeldItem().itemId == Items.cursor.id) {
            TileEntityTriggerInverter obj = (TileEntityTriggerInverter) level.getTileEntity(x, y, z);
            GuiTriggerInverter.showUI(level, x, y, z, obj);
            return true;
        }
        return false;
    }

    @Override
    public void reset(Level level, int i, int j, int k, boolean death) {
        if (!((ExLevel) level).getTriggerManager().isActivated(i, j, k)) {
            this.onTriggerDeactivated(level, i, j, k);
        }
    }
}
