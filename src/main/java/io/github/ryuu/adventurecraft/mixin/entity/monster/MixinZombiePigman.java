package io.github.ryuu.adventurecraft.mixin.entity.monster;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.Zombie;
import net.minecraft.entity.monster.ZombiePigman;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ZombiePigman.class)
public class MixinZombiePigman extends Zombie {

    @Shadow()
    private int anger = 0;

    private int field_2243 = 0;

    private static final ItemInstance field_2244 = new ItemInstance(ItemType.swordGold, 1);

    public MixinZombiePigman(Level world) {
        super(world);
        this.texture = "/mob/pigzombie.png";
        this.movementSpeed = 0.5f;
        this.attackDamage = 5;
        this.immuneToFire = true;
        this.heldItem = new ItemInstance(ItemType.swordGold, 1);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void tick() {
        float f = this.movementSpeed = this.entity == null ? 0.5f : 0.95f;
        if (this.field_2243 > 0 && --this.field_2243 == 0) {
            this.level.playSound(this, "mob.zombiepig.zpigangry", this.getSoundVolume() * 2.0f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) * 1.8f);
        }
        super.tick();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.put("Anger", (short) this.anger);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.anger = tag.getShort("Anger");
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean damage(Entity target, int amount) {
        if (target instanceof Player) {
            List list = this.level.getEntities(this, this.boundingBox.expand(32.0, 32.0, 32.0));
            for (int j = 0; j < list.size(); ++j) {
                Entity entity1 = (Entity) list.get(j);
                if (!(entity1 instanceof ZombiePigman))
                    continue;
                ZombiePigman entitypigzombie = (ZombiePigman) entity1;
                entitypigzombie.method_1792(target);
            }
            this.method_1792(target);
        }
        return super.damage(target, amount);
    }
}
