package io.github.ryuu.adventurecraft.mixin.entity.monster;

import io.github.ryuu.adventurecraft.extensions.entity.ExLivingEntity;
import io.github.ryuu.adventurecraft.extensions.entity.projectile.ExArrow;
import io.github.ryuu.adventurecraft.extensions.level.ExLevelProperties;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.monster.Monster;
import net.minecraft.entity.monster.Skeleton;
import net.minecraft.entity.monster.ZombiePigman;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.MathsHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Skeleton.class)
public abstract class MixinSkeleton extends Monster {

    public MixinSkeleton(Level arg) {
        super(arg);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(Level level, CallbackInfo ci) {
        ((ExLivingEntity) this).setHeldItem(new ItemInstance(ItemType.bow, 1));
    }

    @Inject(method = "updateDespawnCounter", at = @At("HEAD"), cancellable = true)
    private void cancelIfBurnDisabled(CallbackInfo ci) {
        if (!((ExLevelProperties) this.level.getProperties()).getMobsBurn()) {
            ci.cancel();
        }
    }

    @Inject(method = "method_637", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/level/Level;spawnEntity(Lnet/minecraft/entity/Entity;)Z",
            shift = At.Shift.BEFORE))
    private void setArrowDamage(Entity entity, float f, CallbackInfo ci, double var3, double var5, Arrow var7, double var8, float var10) {
        ((ExArrow) var7).setAttackDamage(this.attackDamage);
        var7.y += 0.4f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite
    public void dropLoot() {
        if (this.getMobDrops() != 0) {
            int i = this.rand.nextInt(3) + 1;
            for (int j = 0; j < i; ++j) {
                ItemEntity item = this.dropItem(this.getMobDrops(), 1);
                if (this.getMobDrops() == ItemType.arrow.id) continue;
                item.item.count = 3;
            }
        }
        int i = this.rand.nextInt(3);
        for (int k = 0; k < i; ++k) {
            this.dropItem(ItemType.bone.id, 1);
        }
    }
}
