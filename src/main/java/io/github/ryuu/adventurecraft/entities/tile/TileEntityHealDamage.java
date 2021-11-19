package io.github.ryuu.adventurecraft.entities.tile;

public class TileEntityHealDamage extends MixinTileEntity {

    public int healDamage;

    @Override
    public void readIdentifyingData(MixinCompoundTag tag) {
        super.readIdentifyingData(tag);
        this.healDamage = tag.getInt("healDamage");
    }

    @Override
    public void writeIdentifyingData(MixinCompoundTag tag) {
        super.writeIdentifyingData(tag);
        tag.put("healDamage", this.healDamage);
    }
}
