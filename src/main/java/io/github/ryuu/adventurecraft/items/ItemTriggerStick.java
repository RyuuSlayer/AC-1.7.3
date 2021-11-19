package io.github.ryuu.adventurecraft.items;

import net.minecraft.client.Minecraft;

public class ItemTriggerStick extends MixinItemType {

    protected ItemTriggerStick(int id) {
        super(id);
        this.setTexturePosition(5, 3);
    }

    @Override
    public boolean onItemUseLeftClick(MixinItemInstance itemstack, MixinPlayer entityplayer, MixinLevel world, int i, int j, int k, int l) {
        Minecraft.minecraftInstance.overlay.addChatMessage(String.format("Triggering (%d, %d, %d)", new Object[]{i, j, k}));
        world.triggerManager.addArea(0, -1, 0, new TriggerArea(i, j, k, i, j, k));
        world.triggerManager.removeArea(0, -1, 0);
        return false;
    }

    @Override
    public boolean useOnTile(MixinItemInstance item, MixinPlayer player, MixinLevel level, int x, int y, int z, int facing) {
        Minecraft.minecraftInstance.overlay.addChatMessage(String.format("Checking (%d, %d, %d)", new Object[]{x, y, z}));
        level.triggerManager.outputTriggerSources(x, y, z);
        return false;
    }
}
