package io.github.ryuu.adventurecraft.items;

class ItemBomb extends MixinItemType {

    public ItemBomb(int id) {
        super(id);
        this.setTexturePosition(150);
    }

    @Override
    public MixinItemInstance use(MixinItemInstance item, MixinLevel level, MixinPlayer player) {
        --item.count;
        level.spawnEntity(new EntityBomb(level, player));
        return item;
    }
}
