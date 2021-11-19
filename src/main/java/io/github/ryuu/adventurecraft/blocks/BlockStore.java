package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.client.Minecraft;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.material.Material;

public class BlockStore extends TileWithEntity {

    protected BlockStore(int i, int j) {
        super(i, j, Material.GLASS);
    }

    @Override
    protected MixinTileEntity createTileEntity() {
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
    public boolean activate(MixinLevel level, int x, int y, int z, MixinPlayer player) {
        TileEntityStore store = (TileEntityStore) level.getTileEntity(x, y, z);
        if (DebugMode.active) {
            GuiStoreDebug.showUI(store);
            return true;
        }
        if (store.buySupplyLeft != 0) {
            if (store.sellItemID == 0 || player.inventory.consumeItemAmount(store.sellItemID, store.sellItemDamage, store.sellItemAmount)) {
                if (store.buyItemID != 0) {
                    player.inventory.pickupItem(new MixinItemInstance(store.buyItemID, store.buyItemAmount, store.buyItemDamage));
                }
                --store.buySupplyLeft;
                if (store.tradeTrigger != null) {
                    level.triggerManager.addArea(x, y, z, store.tradeTrigger);
                    level.triggerManager.removeArea(x, y, z);
                }
            } else {
                Minecraft.minecraftInstance.overlay.addChatMessage("Don't have enough to trade.");
            }
            return true;
        }
        return false;
    }

    @Override
    public void reset(MixinLevel world, int i, int j, int k, boolean death) {
        TileEntityStore store = (TileEntityStore) world.getTileEntity(i, j, k);
        store.buySupplyLeft = store.buySupply;
    }
}
