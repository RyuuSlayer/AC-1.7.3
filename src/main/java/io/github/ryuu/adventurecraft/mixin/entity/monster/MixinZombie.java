package io.github.ryuu.adventurecraft.mixin.entity.monster;

import net.minecraft.entity.monster.Monster;
import net.minecraft.entity.monster.Zombie;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.util.maths.MathsHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Zombie.class)
public class MixinZombie extends Monster {

    public MixinZombie(Level world) {
        super(world);
        this.texture = "/mob/zombie.png";
        this.movementSpeed = 0.5f;
        this.attackDamage = 5;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void updateDespawnCounter() {
        float f;
        if (this.level.isDaylight() && this.level.properties.mobsBurn && (f = this.getBrightnessAtEyes(1.0f)) > 0.5f && this.level.isAboveGroundCached(MathsHelper.floor(this.x), MathsHelper.floor(this.y), MathsHelper.floor(this.z)) && this.rand.nextFloat() * 30.0f < (f - 0.4f) * 2.0f) {
            this.fire = 300;
        }
        super.updateDespawnCounter();
    }
}
