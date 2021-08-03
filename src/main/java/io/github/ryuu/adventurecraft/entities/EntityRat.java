package io.github.ryuu.adventurecraft.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.Monster;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;

public class EntityRat extends Monster {
    public EntityRat(Level world) {
        super(world);
        this.O = "/mob/rat.png";
        this.aB = 0.5F;
        this.c = 1;
        b(0.6F, 0.6F);
        this.Y = 6;
        this.maxHealth = 6;
    }

    protected Entity g_() {
        Player entityplayer = this.aI.a(this, 5.0D);
        if (entityplayer != null && e(entityplayer))
            return entityplayer;
        return null;
    }

    protected String g() {
        return "mob.rat.ambient";
    }

    protected String j_() {
        return "mob.rat.hurt";
    }

    protected String i() {
        return "mob.rat.death";
    }
}
