package io.github.ryuu.adventurecraft.mixin;

import net.minecraft.item.ItemType;

public class MixinArmourItem extends ItemType {
    private static final int[] field_2086 = new int[]{3, 8, 6, 3};
    private static final int[] BASE_DURABILITY = new int[]{11, 16, 15, 13};
    public final int field_2082;
    public final int armourSlot;
    public final float field_2084;
    public final int field_2085;

    public MixinArmourItem(int i, int j, int k, int l) {
        super(i);
        this.field_2082 = j;
        this.armourSlot = l;
        this.field_2085 = k;
        float reduction = (j + 1.0F) / 4.0F;
        this.field_2084 = reduction * field_2086[l];
        setDurability(BASE_DURABILITY[l] * 3 << j);
        this.maxStackSize = 1;
    }
}
