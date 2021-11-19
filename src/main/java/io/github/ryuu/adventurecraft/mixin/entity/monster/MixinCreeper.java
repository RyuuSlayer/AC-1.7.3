package io.github.ryuu.adventurecraft.mixin.entity.monster;

import net.minecraft.entity.Lightning;
import net.minecraft.entity.monster.Creeper;
import net.minecraft.item.ItemType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Creeper.class)
public class MixinCreeper extends MixinMonster {

    @Shadow()
    int currentFuseTime;

    int lastFuseTime;

    public MixinCreeper(MixinLevel world) {
        super(world);
        this.texture = "/mob/creeper.png";
        this.attackDamage = 3;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(16, (byte) -1);
        this.dataTracker.startTracking(17, (byte) 0);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void writeCustomDataToTag(MixinCompoundTag tag) {
        super.writeCustomDataToTag(tag);
        if (this.dataTracker.getByte(17) == 1) {
            tag.put("powered", true);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void readCustomDataFromTag(MixinCompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.dataTracker.setData(17, (byte) (tag.getBoolean("powered") ? 1 : 0));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void method_639(MixinEntity entity, float f) {
        if (this.level.isClient) {
            return;
        }
        if (this.currentFuseTime > 0) {
            this.setFuseSpeed(-1);
            --this.currentFuseTime;
            if (this.currentFuseTime < 0) {
                this.currentFuseTime = 0;
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void tick() {
        this.lastFuseTime = this.currentFuseTime;
        if (this.level.isClient) {
            int i = this.getFuseSpeed();
            if (i > 0 && this.currentFuseTime == 0) {
                this.level.playSound(this, "random.fuse", 1.0f, 0.5f);
            }
            this.currentFuseTime += i;
            if (this.currentFuseTime < 0) {
                this.currentFuseTime = 0;
            }
            if (this.currentFuseTime >= 30) {
                this.currentFuseTime = 30;
            }
        }
        super.tick();
        if (this.entity == null && this.currentFuseTime > 0) {
            this.setFuseSpeed(-1);
            --this.currentFuseTime;
            if (this.currentFuseTime < 0) {
                this.currentFuseTime = 0;
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected String getHurtSound() {
        return "mob.creeper";
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected String getDeathSound() {
        return "mob.creeperdeath";
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void onKilledBy(MixinEntity entity) {
        super.onKilledBy(entity);
        if (entity instanceof MixinSkeleton) {
            this.dropItem(ItemType.record_13.id + this.rand.nextInt(2), 1);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void method_637(MixinEntity entity, float f) {
        if (this.level.isClient) {
            return;
        }
        int i = this.getFuseSpeed();
        if (i <= 0 && f < 3.0f || i > 0 && f < 7.0f) {
            if (this.currentFuseTime == 0) {
                this.level.playSound(this, "random.fuse", 1.0f, 0.5f);
            }
            this.setFuseSpeed(1);
            ++this.currentFuseTime;
            if (this.currentFuseTime >= 30) {
                if (this.isCharged()) {
                    this.level.createExplosion(this, this.x, this.y, this.z, (float) this.attackDamage * 2.0f);
                } else {
                    this.level.createExplosion(this, this.x, this.y, this.z, this.attackDamage);
                }
                this.remove();
            }
            this.field_663 = true;
        } else {
            this.setFuseSpeed(-1);
            --this.currentFuseTime;
            if (this.currentFuseTime < 0) {
                this.currentFuseTime = 0;
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isCharged() {
        return this.dataTracker.getByte(17) == 1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float method_410(float f) {
        return ((float) this.lastFuseTime + (float) (this.currentFuseTime - this.lastFuseTime) * f) / 28.0f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected int getMobDrops() {
        return ItemType.sulphur.id;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private int getFuseSpeed() {
        return this.dataTracker.getByte(16);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void setFuseSpeed(int i) {
        this.dataTracker.setData(16, (byte) i);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void onStruckByLightning(Lightning entitylightningbolt) {
        super.onStruckByLightning(entitylightningbolt);
        this.dataTracker.setData(17, (byte) 1);
    }
}
