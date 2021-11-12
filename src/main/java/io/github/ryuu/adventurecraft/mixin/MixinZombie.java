package io.github.ryuu.adventurecraft.mixin;

import net.minecraft.entity.monster.Monster;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.util.maths.MathsHelper;

public class MixinZombie extends Monster {
    public MixinZombie(Level world) {
        super(world);
        this.O = "/mob/zombie.png";
        this.aB = 0.5F;
        this.c = 5;
    }

    public void o() {
        if (this.aI.f() && this.aI.x.mobsBurn) {
            float f = a(1.0F);
            if (f > 0.5F && this.aI.l(MathsHelper.b(this.aM), MathsHelper.b(this.aN), MathsHelper.b(this.aO)) && this.bs.nextFloat() * 30.0F < (f - 0.4F) * 2.0F)
                this.bv = 300;
        }
        super.o();
    }

    protected String g() {
        return "mob.zombie";
    }

    protected String j_() {
        return "mob.zombiehurt";
    }

    protected String i() {
        return "mob.zombiedeath";
    }

    protected int j() {
        return ItemType.J.bf;
    }
}
