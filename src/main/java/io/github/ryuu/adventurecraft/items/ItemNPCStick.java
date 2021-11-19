package io.github.ryuu.adventurecraft.items;

class ItemNPCStick extends MixinItemType {

    public ItemNPCStick(int id) {
        super(id);
        this.setTexturePosition(5, 3);
        this.method_466();
    }

    @Override
    public boolean useOnTile(MixinItemInstance item, MixinPlayer player, MixinLevel level, int x, int y, int z, int facing) {
        EntityNPC npc = new EntityNPC(level);
        npc.method_1338((double) x + 0.5, y + 1, (double) z + 0.5, player.yaw + 180.0f, 0.0f);
        npc.field_1012 = npc.yaw;
        level.spawnEntity(npc);
        return true;
    }

    @Override
    public boolean postHit(MixinItemInstance itemstack, MixinLivingEntity entityliving, MixinLivingEntity entityliving1) {
        if (entityliving instanceof EntityNPC) {
            GuiNPC.showUI((EntityNPC) entityliving);
            return true;
        }
        return false;
    }
}
