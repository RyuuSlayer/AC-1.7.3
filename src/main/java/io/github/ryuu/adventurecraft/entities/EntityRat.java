package io.github.ryuu.adventurecraft.entities;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class EntityRat extends Monster {

    public EntityRat(Level world) {
        super(world);
        this.texture = "/mob/rat.png";
        this.movementSpeed = 0.5f;
        this.attackDamage = 1;
        this.setSize(0.6f, 0.6f);
        this.health = 6;
        this.maxHealth = 6;
    }

    @Override
    protected Entity method_638() {
        Player entityplayer = this.level.getClosestPlayerTo(this, 5.0);
        if (entityplayer != null && this.method_928(entityplayer)) {
            return entityplayer;
        }
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
