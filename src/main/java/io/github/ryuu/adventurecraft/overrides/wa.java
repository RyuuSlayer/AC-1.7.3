package io.github.ryuu.adventurecraft.overrides;

import net.minecraft.item.ItemType;

public class wa extends ItemType {
    public wa(int i, int j, int k, int l) {
        super(i);
        this.a = j;
        this.bk = l;
        this.bm = k;
        float reduction = (j + 1.0F) / 4.0F;
        this.bl = reduction * bn[l];
        e(bo[l] * 3 << j);
        this.bg = 1;
    }

    private static final int[] bn = new int[]{3, 8, 6, 3};

    private static final int[] bo = new int[]{11, 16, 15, 13};

    public final int a;

    public final int bk;

    public final float bl;

    public final int bm;
}
