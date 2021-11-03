package io.github.ryuu.adventurecraft.overrides;

import net.minecraft.entity.monster.Monster;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

public class Zombie extends Monster {
    public Zombie(Level world) {
        super(world);
        this.O = "/mob/zombie.png";
        this.aB = 0.5F;
        this.c = 5;
    }

    public void o() {
        if (this.aI.f() && this.aI.x.mobsBurn) {
            float f = a(1.0F);
            if (f > 0.5F && this.aI.l(in.b(this.aM), in.b(this.aN), in.b(this.aO)) && this.bs.nextFloat() * 30.0F < (f - 0.4F) * 2.0F)
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
