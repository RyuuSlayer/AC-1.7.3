package io.github.ryuu.adventurecraft.mixin.item.armour;

import net.minecraft.item.ItemType;

public class MixinArmourItem extends ItemType {
    private static final int[] field_2086 = new int[]{3, 8, 6, 3};
    private static final int[] BASE_DURABILITY = new int[]{11, 16, 15, 13};
    public final int field_2082;
    public final int armourSlot;
    public final float bl;
    public final int field_2085;

    public MixinArmourItem(int id, int j, int k, int slot) {
        super(id);
        this.field_2082 = j;
        this.armourSlot = slot;
        this.field_2085 = k;
        float reduction = ((float)j + 1.0f) / 4.0f;
        this.bl = reduction * (float)field_2086[slot];
        this.setDurability(BASE_DURABILITY[slot] * 3 << j);
        this.maxStackSize = 1;
    }
}