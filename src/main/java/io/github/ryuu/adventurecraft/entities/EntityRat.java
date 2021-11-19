package io.github.ryuu.adventurecraft.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.Monster;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;

public class EntityRat extends Monster {
    public EntityRat(Level world) {
        super(world);
        this.texture = "/mob/rat.png";
        this.movementSpeed = 0.5F;
        this.attackDamage = 1;
        setSize(0.6F, 0.6F);
        this.health = 6;
        this.maxHealth = 6;
    }

    protected Entity findPlayerToTrack() {
        Player entityplayer = this.level.getClosestPlayerTo(this, 5.0D);
        if (entityplayer != null && method_928(entityplayer))
            return entityplayer;
        return null;
    }

    @Override
    protected String getAmbientSound() {
        return "mob.rat.ambient";
    }

    @Override
    protected String getHurtSound() {
        return "mob.rat.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "mob.rat.death";
    }
}
