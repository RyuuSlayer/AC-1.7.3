/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package io.github.ryuu.adventurecraft.mixin.entity.monster;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.monster.Monster;
import net.minecraft.entity.monster.Skeleton;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.MathsHelper;
import io.github.ryuu.adventurecraft.mixin.item.MixinMonster;
import io.github.ryuu.adventurecraft.mixin.item.MixinLevel;
import io.github.ryuu.adventurecraft.mixin.item.MixinCompoundTag;
import io.github.ryuu.adventurecraft.mixin.item.MixinEntity;
import io.github.ryuu.adventurecraft.mixin.item.MixinArrow;
import io.github.ryuu.adventurecraft.mixin.item.MixinItemInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import io.github.ryuu.adventurecraft.mixin.item.MixinItemEntity;

@Mixin(Skeleton.class)
public class MixinSkeleton extends MixinMonster {

    @Shadow()
    private static final MixinItemInstance field_302 = new MixinItemInstance(ItemType.bow, 1);

    public MixinSkeleton(MixinLevel world) {
        super(world);
        this.texture = "/mob/skeleton.png";
        this.heldItem = new MixinItemInstance(ItemType.bow, 1);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected String getAmbientSound() {
        return "mob.skeleton";
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected String getHurtSound() {
        return "mob.skeletonhurt";
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected String getDeathSound() {
        return "mob.skeletonhurt";
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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void method_637(MixinEntity entity, float f) {
        if (f < 10.0f) {
            double d = entity.x - this.x;
            double d1 = entity.z - this.z;
            if (this.attackTime == 0) {
                MixinArrow entityarrow = new MixinArrow(this.level, this, this.attackDamage);
                entityarrow.y += (double) 1.4f;
                double d2 = entity.y + (double) entity.getStandingEyeHeight() - (double) 0.2f - entityarrow.y;
                float f1 = MathsHelper.sqrt(d * d + d1 * d1) * 0.2f;
                this.level.playSound(this, "random.bow", 1.0f, 1.0f / (this.rand.nextFloat() * 0.4f + 0.8f));
                this.level.spawnEntity(entityarrow);
                entityarrow.method_1291(d, d2 + (double) f1, d1, 0.6f, 12.0f);
                this.attackTime = 30;
            }
            this.yaw = (float) (Math.atan2((double) d1, (double) d) * 180.0 / 3.1415927410125732) - 90.0f;
            this.field_663 = true;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void writeCustomDataToTag(MixinCompoundTag tag) {
        super.writeCustomDataToTag(tag);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void readCustomDataFromTag(MixinCompoundTag tag) {
        super.readCustomDataFromTag(tag);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected int getMobDrops() {
        return ItemType.arrow.id;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void dropLoot() {
        int i;
        if (this.getMobDrops() != 0) {
            i = this.rand.nextInt(3) + 1;
            for (int j = 0; j < i; ++j) {
                MixinItemEntity item = this.dropItem(this.getMobDrops(), 1);
                if (this.getMobDrops() == ItemType.arrow.id)
                    continue;
                item.item.count = 3;
            }
        }
        i = this.rand.nextInt(3);
        for (int k = 0; k < i; ++k) {
            this.dropItem(ItemType.bone.id, 1);
        }
    }
}
