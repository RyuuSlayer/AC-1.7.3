package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityStore;
import io.github.ryuu.adventurecraft.extensions.entity.player.ExPlayerInventory;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.gui.GuiStoreDebug;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;

public class BlockStore extends TileWithEntity implements AcResetTile {

    protected BlockStore(int i, int j) {
        super(i, j, Material.GLASS);
        this.hardness(5.0f);
        this.sounds(Tile.GLASS_SOUNDS);
    }

    @Override
    protected TileEntity createTileEntity() {
        return new TileEntityStore();
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public int method_1619() {
        return 1;
    }

    @Override
    public boolean activate(Level level, int x, int y, int z, Player player) {
        TileEntityStore store = (TileEntityStore) level.getTileEntity(x, y, z);
        if (DebugMode.active) {
            GuiStoreDebug.showUI(store);
            return true;
        }
        if (store.buySupplyLeft != 0) {
            if (store.sellItemID == 0 || ((ExPlayerInventory) player.inventory).consumeItemAmount(store.sellItemID, store.sellItemDamage, store.sellItemAmount)) {
                if (store.buyItemID != 0) {
                    player.inventory.pickupItem(new ItemInstance(store.buyItemID, store.buyItemAmount, store.buyItemDamage));
                }
                --store.buySupplyLeft;
                if (store.tradeTrigger != null) {
                    ((ExLevel) level).getTriggerManager().addArea(x, y, z, store.tradeTrigger);
                    ((ExLevel) level).getTriggerManager().removeArea(x, y, z);
                }
            } else {
                AccessMinecraft.getInstance().overlay.addChatMessage("Don't have enough to trade.");
            }
            return true;
        }
        return false;
    }

    @Override
    public void reset(Level world, int i, int j, int k, boolean death) {
        TileEntityStore store = (TileEntityStore) world.getTileEntity(i, j, k);
        store.buySupplyLeft = store.buySupply;
    }
}
