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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Skeleton.class)
public class MixinSkeleton extends Monster {

    @Shadow()
    private static final ItemInstance field_302 = new ItemInstance(ItemType.bow, 1);

    public MixinSkeleton(Level world) {
        super(world);
        this.texture = "/mob/skeleton.png";
        this.heldItem = new ItemInstance(ItemType.bow, 1);
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
    protected void method_637(Entity entity, float f) {
        if (f < 10.0f) {
            double d = entity.x - this.x;
            double d1 = entity.z - this.z;
            if (this.attackTime == 0) {
                Arrow entityarrow = new Arrow(this.level, this, this.attackDamage);
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
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
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
                ItemEntity item = this.dropItem(this.getMobDrops(), 1);
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
