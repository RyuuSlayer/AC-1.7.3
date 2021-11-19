package io.github.ryuu.adventurecraft.mixin.entity.monster;

import net.minecraft.entity.monster.ZombiePigman;
import net.minecraft.item.ItemType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(ZombiePigman.class)
public class MixinZombiePigman extends MixinZombie {

    private static final MixinItemInstance field_2244 = new MixinItemInstance(ItemType.swordGold, 1);
    @Shadow()
    private int anger = 0;
    private int field_2243 = 0;

    public MixinZombiePigman(MixinLevel world) {
        super(world);
        this.texture = "/mob/pigzombie.png";
        this.movementSpeed = 0.5f;
        this.attackDamage = 5;
        this.immuneToFire = true;
        this.heldItem = new MixinItemInstance(ItemType.swordGold, 1);
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
    public boolean canSpawn() {
        return this.level.difficulty > 0 && this.level.canSpawnEntity(this.boundingBox) && this.level.method_190(this, this.boundingBox).size() == 0 && !this.level.method_218(this.boundingBox);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void writeCustomDataToTag(MixinCompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.put("Anger", (short) this.anger);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void readCustomDataFromTag(MixinCompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.anger = tag.getShort("Anger");
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected MixinEntity method_638() {
        if (this.anger == 0) {
            return null;
        }
        return super.method_638();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void updateDespawnCounter() {
        super.updateDespawnCounter();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean damage(MixinEntity target, int amount) {
        if (target instanceof MixinPlayer) {
            List list = this.level.getEntities(this, this.boundingBox.expand(32.0, 32.0, 32.0));
            for (int j = 0; j < list.size(); ++j) {
                MixinEntity entity1 = (MixinEntity) list.get(j);
                if (!(entity1 instanceof MixinZombiePigman)) continue;
                MixinZombiePigman entitypigzombie = (MixinZombiePigman) entity1;
                entitypigzombie.method_1792(target);
            }
            this.method_1792(target);
        }
        return super.damage(target, amount);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_1792(MixinEntity entity) {
        this.entity = entity;
        this.anger = 400 + this.rand.nextInt(400);
        this.field_2243 = this.rand.nextInt(40);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected String getAmbientSound() {
        return "mob.zombiepig.zpig";
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected String getHurtSound() {
        return "mob.zombiepig.zpighurt";
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected String getDeathSound() {
        return "mob.zombiepig.zpigdeath";
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected int getMobDrops() {
        return ItemType.porkchopCooked.id;
    }
}
