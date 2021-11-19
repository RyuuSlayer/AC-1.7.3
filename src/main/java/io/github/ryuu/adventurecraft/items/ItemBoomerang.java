package io.github.ryuu.adventurecraft.items;

class ItemBoomerang extends MixinItemType {

    public ItemBoomerang(int id) {
        super(id);
        this.setTexturePosition(144);
        this.maxStackSize = 1;
        this.setDurability(0);
        this.method_457(true);
    }

    @Override
    public int getTexturePosition(int damage) {
        if (damage == 0) {
            return this.texturePosition;
        }
        return 165;
    }

    @Override
    public MixinItemInstance use(MixinItemInstance item, MixinLevel level, MixinPlayer player) {
        if (item.getDamage() == 0) {
            level.spawnEntity(new EntityBoomerang(level, player, item));
            item.setDamage(1);
        }
        return item;
    }
}
