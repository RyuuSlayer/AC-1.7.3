package io.github.ryuu.adventurecraft.mixin.entity.player;

import io.github.ryuu.adventurecraft.accessors.entity.player.PlayerAccessor;
import io.github.ryuu.adventurecraft.accessors.items.ItemTypeLightEmitter;
import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.PlayerTorch;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.container.Container;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.Monster;
import net.minecraft.entity.player.Player;
import net.minecraft.entity.player.PlayerContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.tile.Tile;
import net.minecraft.util.Vec3i;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.MathsHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Player.class)
public abstract class MixinPlayer extends LivingEntity implements PlayerAccessor {

    @Shadow
    public PlayerInventory inventory;
    @Shadow
    public Container playerContainer;
    @Shadow
    public Container container;
    @Shadow
    public int score = 0;
    @Shadow
    public float field_524;
    @Shadow
    public float field_525;
    @Shadow
    public boolean handSwinging = false;
    @Shadow
    public int handSwingTicks = 0;
    @Shadow
    public String name;

    public boolean isSwingingOffhand;
    public int swingProgressIntOffhand;
    public float prevSwingProgressOffhand;
    public float swingProgressOffhand;
    public boolean swappedItems;
    public int numHeartPieces;
    public String cloakTexture;

    public MixinPlayer(Level world) {
        super(world);
        this.container = this.playerContainer = new PlayerContainer(this.inventory, !world.isClient);
        this.standingEyeHeight = 1.62f;
        Vec3i chunkcoordinates = world.getSpawnPosition();
        this.setPositionAndAngles((double) chunkcoordinates.x + 0.5, chunkcoordinates.y + 1, (double) chunkcoordinates.z + 0.5, 0.0f, 0.0f);
        this.health = 12;
        this.maxHealth = 12;
        this.field_1022 = "humanoid";
        this.field_1021 = 180.0f;
        this.field_1646 = 20;
        this.texture = "/mob/char.png";
        this.isSwingingOffhand = false;
        this.swingProgressIntOffhand = 0;
        this.swappedItems = false;
        this.numHeartPieces = 0;
    }

    @Shadow
    public abstract void increaseStat(Stat arg, int i);

    @Shadow
    public abstract void wakeUp(boolean flag, boolean flag1, boolean flag2);

    @Shadow
    protected abstract void method_520(Entity arg);

    @Shadow
    protected abstract void method_510(LivingEntity arg, boolean flag);

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Environment(EnvType.CLIENT)
    @Override
    @Overwrite
    public void afterSpawn() {
        this.standingEyeHeight = 1.62f;
        this.setSize(0.6f, 1.8f);
        super.afterSpawn();
        this.health = this.maxHealth;
        this.deathTime = 0;
        this.removed = false;
        this.fire = -this.field_1646;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite
    protected void tickHandSwing() {
        if (this.handSwinging) {
            ++this.handSwingTicks;
            if (this.handSwingTicks == 8) {
                this.handSwingTicks = 0;
                this.handSwinging = false;
            }
        } else {
            this.handSwingTicks = 0;
        }
        this.handSwingProgress = (float) this.handSwingTicks / 8.0f;

        if (this.isSwingingOffhand) {
            ++this.swingProgressIntOffhand;
            if (this.swingProgressIntOffhand == 8) {
                this.swingProgressIntOffhand = 0;
                this.isSwingingOffhand = false;
            }
        } else {
            this.swingProgressIntOffhand = 0;
        }
        this.swingProgressOffhand = (float) this.swingProgressIntOffhand / 8.0f;
    }

    public void baseTick() {
        this.prevSwingProgressOffhand = this.swingProgressOffhand;
        super.baseTick();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite
    public void updateDespawnCounter() {
        List list;
        if (this.level.difficulty == 0 && this.health < this.maxHealth && this.field_1645 % 20 * 12 == 0) {
            this.addHealth(1);
        }
        this.inventory.inventoryTick();
        this.field_524 = this.field_525;
        super.updateDespawnCounter();
        float f = MathsHelper.sqrt(this.velocityX * this.velocityX + this.velocityZ * this.velocityZ);
        float f1 = (float) Math.atan(-this.velocityY * (double) 0.2f) * 15.0f;
        if (f > 0.1f) {
            f = 0.1f;
        }
        if (!this.onGround || this.health <= 0) {
            f = 0.0f;
        }
        if (this.onGround || this.health <= 0) {
            f1 = 0.0f;
        }
        this.field_525 += (f - this.field_525) * 0.4f;
        this.field_1044 += (f1 - this.field_1044) * 0.8f;
        if (this.health > 0 && (list = this.level.getEntities(this, this.boundingBox.expand(1.0, 0.0, 1.0))) != null) {
            for (int i = 0; i < list.size(); ++i) {
                Entity entity = (Entity) list.get(i);
                if (!entity.removed) {
                    this.method_520(entity);
                }
            }
        }
        ItemInstance mainItem = this.inventory.main[this.inventory.selectedHotbarSlot];
        ItemInstance offItem = this.inventory.main[this.inventory.offhandItem];
        boolean litFromItem = mainItem != null && ((ItemTypeLightEmitter) ItemType.byId[mainItem.itemId]).isLighting(mainItem);
        if ((litFromItem |= offItem != null && ((ItemTypeLightEmitter) ItemType.byId[offItem.itemId]).isLighting(offItem)) || mainItem != null && (mainItem.itemId == Tile.TORCH.id || mainItem.itemId == Blocks.lights1.id) || offItem != null && (offItem.itemId == Tile.TORCH.id || offItem.itemId == Blocks.lights1.id)) {
            PlayerTorch.setTorchState(this.level, true);
            PlayerTorch.setTorchPos(this.level, (float) this.x, (float) this.y, (float) this.z);
        } else {
            PlayerTorch.setTorchState(this.level, false);
        }
        if (this.velocityY < -0.2 && this.usingUmbrella()) {
            this.velocityY = -0.2;
        }
        if (!this.onGround) {
            if (this.inventory.getHeldItem() != null && this.inventory.getHeldItem().itemId == Items.umbrella.id) {
                this.inventory.getHeldItem().setDamage(1);
            }
            if (this.inventory.getOffhandItem() != null && this.inventory.getOffhandItem().itemId == Items.umbrella.id) {
                this.inventory.getOffhandItem().setDamage(1);
            }
        }
    }

    private boolean handleLantern(ItemInstance i) {
        if (i == null) {
            return false;
        }
        if (i.itemId != Items.lantern.id) {
            return false;
        }
        if (i.getDamage() < i.method_723()) {
            i.setDamage(i.getDamage() + 1);
            PlayerTorch.setTorchState(this.level, true);
            PlayerTorch.setTorchPos(this.level, (float) this.x, (float) this.y, (float) this.z);
        }
        if (i.getDamage() == i.method_723()) {
            if (this.inventory.decreaseAmountOfItem(Items.oil.id)) {
                i.setDamage(0);
            } else {
                return false;
            }
        }
        return true;
    }

    @Redirect(method = "onKilledBy", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/PlayerInventory;dropInventory()V"))
    private void redirectInventoryDropOnKilledBy(PlayerInventory instance) {
    }

    @Inject(method = "readCustomDataFromTag", at = @At(value = "TAIL"))
    private void readHeartPiecesFromTag(CompoundTag tag, CallbackInfo ci) {
        this.numHeartPieces = tag.getInt("NumHeartPieces");
        if (this.maxHealth < 12) {
            this.health = this.health * 12 / this.maxHealth;
            this.maxHealth = 12;
        }
    }

    @Inject(method = "writeCustomDataToTag", at = @At(value = "TAIL"))
    public void writeHeartPiecesToTag(CompoundTag tag, CallbackInfo ci) {
        tag.put("NumHeartPieces", this.numHeartPieces);
    }

    public boolean attackEntityFromMulti(Entity entity, int i) {
        this.despawnCounter = 0;
        if (this.health <= 0) {
            return false;
        }
        if (this.isSleeping()) {
            this.wakeUp(true, true, false);
        }
        if (entity instanceof Monster || entity instanceof Arrow) {
            if (this.level.difficulty == 0) {
                i = 0;
            }
            if (this.level.difficulty == 1) {
                i = i / 3 + 1;
            }
            if (this.level.difficulty == 3) {
                i = i * 3 / 2;
            }
        }
        if (i == 0) {
            return false;
        }
        Entity obj = entity;
        if (obj instanceof Arrow && ((Arrow) obj).field_1576 != null) {
            obj = ((Arrow) obj).field_1576;
        }
        if (obj instanceof LivingEntity) {
            this.method_510((LivingEntity) obj, false);
        }
        this.increaseStat(Stats.damageTaken, i);
        return super.attackEntityFromMulti(entity, i);
    }

    @Inject(method = "applyDamage", at = @At("HEAD"), cancellable = true)
    private void stopDamageWhenDebug(int par1, CallbackInfo ci) {
        if (DebugMode.active)
            ci.cancel();
    }

    @Redirect(method = "applyDamage", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/PlayerInventory;damageArmour(I)V"))
    private void stopArmorDamageOnApplyDamage(PlayerInventory instance, int i) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void swingHand() {
        if (this.swappedItems) {
            this.swingOffhandItem();
            return;
        }
        this.handSwingTicks = -1;
        this.handSwinging = true;
    }

    public void swingOffhandItem() {
        this.swingProgressIntOffhand = -1;
        this.isSwingingOffhand = true;
    }

    public float getSwingOffhandProgress(float f) {
        float f1 = this.swingProgressOffhand - this.prevSwingProgressOffhand;
        if (f1 < 0.0f) {
            f1 += 1.0f;
        }
        return this.prevSwingProgressOffhand + f1 * f;
    }

    public boolean protectedByShield() {
        ItemInstance curItem = this.inventory.getHeldItem();
        ItemInstance offItem = this.inventory.getOffhandItem();
        if (curItem != null && curItem.itemId == Items.woodenShield.id || offItem != null && offItem.itemId == Items.woodenShield.id) {
            return this.getSwingOffhandProgress(1.0f) == 0.0f;
        }
        return false;
    }


    public double getGravity() {
        if (Items.hookshot.mainHookshot != null && Items.hookshot.mainHookshot.attachedToSurface || Items.hookshot.offHookshot != null && Items.hookshot.offHookshot.attachedToSurface) {
            return 0.0;
        }
        return super.getGravity();
    }

    public boolean usingUmbrella() {
        return this.inventory.getHeldItem() != null && this.inventory.getHeldItem().itemId == Items.umbrella.id || this.inventory.getOffhandItem() != null && this.inventory.getOffhandItem().itemId == Items.umbrella.id;
    }

    @Override
    public int getHeartPieces() {
        return numHeartPieces;
    }
}
