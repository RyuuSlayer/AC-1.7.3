package io.github.ryuu.adventurecraft.mixin.entity.player;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.PlayerTorch;
import net.minecraft.achievement.Achievements;
import net.minecraft.container.Container;
import net.minecraft.entity.*;
import net.minecraft.entity.animal.Pig;
import net.minecraft.entity.animal.Wolf;
import net.minecraft.entity.monster.Creeper;
import net.minecraft.entity.monster.Ghast;
import net.minecraft.entity.monster.Monster;
import net.minecraft.entity.player.Player;
import net.minecraft.entity.player.PlayerContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.level.source.LevelSource;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.tile.BedTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.SleepStatus;
import net.minecraft.util.Vec3i;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.io.ListTag;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathsHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(Player.class)
public class MixinPlayer extends LivingEntity implements PlayerAccessor {

    @Shadow()
    public PlayerInventory inventory = new PlayerInventory(this);

    public Container playerContainer;

    public Container container;

    public byte field_522 = 0;

    public int score = 0;

    public float field_524;

    public float field_525;

    public boolean handSwinging = false;

    public int handSwingTicks = 0;

    public String name;

    public int dimensionId;

    public String playerCloakUrl;

    public double field_530;

    public double field_531;

    public double field_532;

    public double field_533;

    public double field_534;

    public double field_535;
    public Vec3i bedPosition;
    public float field_509;
    public float field_507;
    public float field_510;
    public int field_511 = 20;
    public float field_504;
    public float field_505;
    public float field_513;
    public FishHook fishHook = null;
    public boolean isSwingingOffhand;
    public int swingProgressIntOffhand;
    public float prevSwingProgressOffhand;
    public float swingProgressOffhand;
    public boolean swappedItems;
    public int numHeartPieces;
    public String cloakTexture;
    protected boolean sleeping;
    protected boolean field_512 = false;
    private int sleepTimer;
    private Vec3i spawnPos;
    private Vec3i field_517;
    private int field_518 = 0;

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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static Vec3i getRespawnPos(Level level, Vec3i respawnPos) {
        LevelSource ichunkprovider = level.getLevelSource();
        ichunkprovider.loadChunk(respawnPos.x - 3 >> 4, respawnPos.z - 3 >> 4);
        ichunkprovider.loadChunk(respawnPos.x + 3 >> 4, respawnPos.z - 3 >> 4);
        ichunkprovider.loadChunk(respawnPos.x - 3 >> 4, respawnPos.z + 3 >> 4);
        ichunkprovider.loadChunk(respawnPos.x + 3 >> 4, respawnPos.z + 3 >> 4);
        if (level.getTileId(respawnPos.x, respawnPos.y, respawnPos.z) != Tile.BED.id) {
            return null;
        }
        Vec3i chunkcoordinates1 = BedTile.findWakeUpPosition(level, respawnPos.x, respawnPos.y, respawnPos.z, 0);
        return chunkcoordinates1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(16, (byte) 0);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void tick() {
        if (this.isSleeping()) {
            ++this.sleepTimer;
            if (this.sleepTimer > 100) {
                this.sleepTimer = 100;
            }
            if (!this.level.isClient) {
                if (!this.isBedPosValid()) {
                    this.wakeUp(true, true, false);
                } else if (this.level.isDaylight()) {
                    this.wakeUp(false, true, true);
                }
            }
        } else if (this.sleepTimer > 0) {
            ++this.sleepTimer;
            if (this.sleepTimer >= 110) {
                this.sleepTimer = 0;
            }
        }
        super.tick();
        if (!this.level.isClient && this.container != null && !this.container.canUse(this)) {
            this.closeContainer();
            this.container = this.playerContainer;
        }
        this.field_530 = this.field_533;
        this.field_531 = this.field_534;
        this.field_532 = this.field_535;
        double d = this.x - this.field_533;
        double d1 = this.y - this.field_534;
        double d2 = this.z - this.field_535;
        double d3 = 10.0;
        if (d > d3) {
            this.field_530 = this.field_533 = this.x;
        }
        if (d2 > d3) {
            this.field_532 = this.field_535 = this.z;
        }
        if (d1 > d3) {
            this.field_531 = this.field_534 = this.y;
        }
        if (d < -d3) {
            this.field_530 = this.field_533 = this.x;
        }
        if (d2 < -d3) {
            this.field_532 = this.field_535 = this.z;
        }
        if (d1 < -d3) {
            this.field_531 = this.field_534 = this.y;
        }
        this.field_533 += d * 0.25;
        this.field_535 += d2 * 0.25;
        this.field_534 += d1 * 0.25;
        this.increaseStat(Stats.playOneMinute, 1);
        if (this.vehicle == null) {
            this.field_517 = null;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void tickRiding() {
        double d = this.x;
        double d1 = this.y;
        double d2 = this.z;
        super.tickRiding();
        this.field_524 = this.field_525;
        this.field_525 = 0.0f;
        this.method_519(this.x - d, this.y - d1, this.z - d2);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
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
    @Overwrite()
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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void baseTick() {
        this.prevSwingProgressOffhand = this.swingProgressOffhand;
        super.baseTick();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
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
                if (entity.removed) continue;
                this.method_520(entity);
            }
        }
        ItemInstance mainItem = this.inventory.main[this.inventory.selectedHotbarSlot];
        ItemInstance offItem = this.inventory.main[this.inventory.offhandItem];
        boolean litFromItem = mainItem != null && ItemType.byId[mainItem.itemId].isLighting(mainItem);
        if ((litFromItem |= offItem != null && ItemType.byId[offItem.itemId].isLighting(offItem)) || mainItem != null && (mainItem.itemId == Tile.TORCH.id || mainItem.itemId == Blocks.lights1.id) || offItem != null && (offItem.itemId == Tile.TORCH.id || offItem.itemId == Blocks.lights1.id)) {
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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void onKilledBy(Entity entity) {
        super.onKilledBy(entity);
        this.setSize(0.2f, 0.2f);
        this.setPosition(this.x, this.y, this.z);
        this.velocityY = 0.1f;
        if (this.name.equals("Notch")) {
            this.dropItem(new ItemInstance(ItemType.apple, 1), true);
        }
        if (entity != null) {
            this.velocityX = -MathsHelper.cos((this.field_1040 + this.yaw) * 3.141593f / 180.0f) * 0.1f;
            this.velocityZ = -MathsHelper.sin((this.field_1040 + this.yaw) * 3.141593f / 180.0f) * 0.1f;
        } else {
            this.velocityZ = 0.0;
            this.velocityX = 0.0;
        }
        this.standingEyeHeight = 0.1f;
        this.increaseStat(Stats.deaths, 1);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void onKilledOther(Entity entity, int i) {
        this.score += i;
        if (entity instanceof Player) {
            this.increaseStat(Stats.playerKills, 1);
        } else {
            this.increaseStat(Stats.mobKills, 1);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void dropItem(ItemInstance itemstack) {
        this.dropItem(itemstack, false);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void dropItem(ItemInstance itemstack, boolean flag) {
        if (itemstack == null) {
            return;
        }
        ItemEntity entityitem = new ItemEntity(this.level, this.x, this.y - (double) 0.3f + (double) this.getStandingEyeHeight(), this.z, itemstack);
        entityitem.pickupDelay = 40;
        float f = 0.1f;
        if (flag) {
            float f2 = this.rand.nextFloat() * 0.5f;
            float f4 = this.rand.nextFloat() * 3.141593f * 2.0f;
            entityitem.velocityX = -MathsHelper.sin(f4) * f2;
            entityitem.velocityZ = MathsHelper.cos(f4) * f2;
            entityitem.velocityY = 0.2f;
        } else {
            float f1 = 0.3f;
            entityitem.velocityX = -MathsHelper.sin(this.yaw / 180.0f * 3.141593f) * MathsHelper.cos(this.pitch / 180.0f * 3.141593f) * f1;
            entityitem.velocityZ = MathsHelper.cos(this.yaw / 180.0f * 3.141593f) * MathsHelper.cos(this.pitch / 180.0f * 3.141593f) * f1;
            entityitem.velocityY = -MathsHelper.sin(this.pitch / 180.0f * 3.141593f) * f1 + 0.1f;
            f1 = 0.02f;
            float f3 = this.rand.nextFloat() * 3.141593f * 2.0f;
            entityitem.velocityX += Math.cos(f3) * (double) (f1 *= this.rand.nextFloat());
            entityitem.velocityY += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f;
            entityitem.velocityZ += Math.sin(f3) * (double) f1;
        }
        this.spawnItem(entityitem);
        this.increaseStat(Stats.drop, 1);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void spawnItem(ItemEntity entityitem) {
        this.level.spawnEntity(entityitem);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float method_511(Tile block) {
        float f = this.inventory.method_674(block);
        if (this.isInFluid(Material.WATER)) {
            f /= 5.0f;
        }
        if (!this.onGround) {
            f /= 5.0f;
        }
        return f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_514(Tile block) {
        return this.inventory.isUsingEffectiveTool(block);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        ListTag nbttaglist = tag.getListTag("Inventory");
        this.inventory.fromTag(nbttaglist);
        this.dimensionId = tag.getInt("Dimension");
        this.sleeping = tag.getBoolean("Sleeping");
        this.sleepTimer = tag.getShort("SleepTimer");
        if (this.sleeping) {
            this.bedPosition = new Vec3i(MathsHelper.floor(this.x), MathsHelper.floor(this.y), MathsHelper.floor(this.z));
            this.wakeUp(true, true, false);
        }
        if (tag.containsKey("SpawnX") && tag.containsKey("SpawnY") && tag.containsKey("SpawnZ")) {
            this.spawnPos = new Vec3i(tag.getInt("SpawnX"), tag.getInt("SpawnY"), tag.getInt("SpawnZ"));
        }
        this.numHeartPieces = tag.getInt("NumHeartPieces");
        if (this.maxHealth < 12) {
            this.health = this.health * 12 / this.maxHealth;
            this.maxHealth = 12;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.put("Inventory", this.inventory.toTag(new ListTag()));
        tag.put("Dimension", this.dimensionId);
        tag.put("Sleeping", this.sleeping);
        tag.put("SleepTimer", (short) this.sleepTimer);
        if (this.spawnPos != null) {
            tag.put("SpawnX", this.spawnPos.x);
            tag.put("SpawnY", this.spawnPos.y);
            tag.put("SpawnZ", this.spawnPos.z);
        }
        tag.put("NumHeartPieces", this.numHeartPieces);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean damage(Entity target, int amount) {
        this.despawnCounter = 0;
        if (this.health <= 0) {
            return false;
        }
        if (this.isSleeping()) {
            this.wakeUp(true, true, false);
        }
        if (target instanceof Monster || target instanceof Arrow) {
            if (this.level.difficulty == 0) {
                amount = 0;
            }
            if (this.level.difficulty == 1) {
                amount = amount / 3 + 1;
            }
            if (this.level.difficulty == 3) {
                amount = amount * 3 / 2;
            }
        }
        if (amount == 0) {
            return false;
        }
        Entity obj = target;
        if (obj instanceof Arrow && ((Arrow) obj).field_1576 != null) {
            obj = ((Arrow) obj).field_1576;
        }
        if (obj instanceof LivingEntity) {
            this.method_510((LivingEntity) obj, false);
        }
        this.increaseStat(Stats.damageTaken, amount);
        return super.damage(target, amount);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void method_510(LivingEntity entityliving, boolean flag) {
        Wolf entitywolf;
        if (entityliving instanceof Creeper || entityliving instanceof Ghast) {
            return;
        }
        if (entityliving instanceof Wolf && (entitywolf = (Wolf) entityliving).isTamed() && this.name.equals(entitywolf.getOwner())) {
            return;
        }
        if (entityliving instanceof Player && !this.method_498()) {
            return;
        }
        List list = this.level.getEntities(Wolf.class, Box.getOrCreate(this.x, this.y, this.z, this.x + 1.0, this.y + 1.0, this.z + 1.0).expand(16.0, 4.0, 16.0));
        for (Entity entity : list) {
            Wolf entitywolf1 = (Wolf) entity;
            if (!entitywolf1.isTamed() || entitywolf1.method_634() != null || !this.name.equals(entitywolf1.getOwner()) || flag && entitywolf1.isSitting())
                continue;
            entitywolf1.setSitting(false);
            entitywolf1.method_636(entityliving);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void applyDamage(int i) {
        if (DebugMode.active) {
            return;
        }
        int j = 25 - this.inventory.method_687();
        int k = i * j + this.field_518;
        i = k / 25;
        this.field_518 = k % 25;
        super.applyDamage(i);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void interactWith(Entity entity) {
        if (entity.interact(this)) {
            return;
        }
        ItemInstance itemstack = this.getHeldItem();
        if (itemstack != null && entity instanceof LivingEntity) {
            itemstack.interactWithEntity((LivingEntity) entity);
            if (itemstack.count == 0) {
                itemstack.method_700(this);
                this.method_503();
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void swingHand() {
        if (this.swappedItems) {
            this.swingOffhandItem();
            return;
        }
        this.handSwingTicks = -1;
        this.handSwinging = true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void swingOffhandItem() {
        this.swingProgressIntOffhand = -1;
        this.isSwingingOffhand = true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float getSwingOffhandProgress(float f) {
        float f1 = this.swingProgressOffhand - this.prevSwingProgressOffhand;
        if (f1 < 0.0f) {
            f1 += 1.0f;
        }
        return this.prevSwingProgressOffhand + f1 * f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void attack(Entity entity) {
        int i = this.inventory.method_672(entity);
        if (i > 0) {
            if (this.velocityY < 0.0) {
                ++i;
            }
            entity.damage(this, i);
            ItemInstance itemstack = this.getHeldItem();
            if (itemstack != null && entity instanceof LivingEntity) {
                itemstack.postHit((LivingEntity) entity, this);
                if (itemstack.count == 0) {
                    itemstack.method_700(this);
                    this.method_503();
                }
            }
            if (entity instanceof LivingEntity) {
                if (entity.isAlive()) {
                    this.method_510((LivingEntity) entity, true);
                }
                this.increaseStat(Stats.damageDealt, i);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean protectedByShield() {
        ItemInstance curItem = this.inventory.getHeldItem();
        ItemInstance offItem = this.inventory.getOffhandItem();
        if (curItem != null && curItem.itemId == Items.woodenShield.id || offItem != null && offItem.itemId == Items.woodenShield.id) {
            return this.getSwingOffhandProgress(1.0f) == 0.0f;
        }
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public SleepStatus trySleep(int x, int y, int z) {
        if (!this.level.isClient) {
            if (this.isSleeping() || !this.isAlive()) {
                return SleepStatus.OTHER_PROBLEM;
            }
            if (this.level.dimension.hasFog) {
                return SleepStatus.NOT_POSSIBLE_HERE;
            }
            if (this.level.isDaylight()) {
                return SleepStatus.NOT_POSSIBLE_NOW;
            }
            if (Math.abs(this.x - (double) x) > 3.0 || Math.abs(this.y - (double) y) > 2.0 || Math.abs(this.z - (double) z) > 3.0) {
                return SleepStatus.TOO_FAR_AWAY;
            }
        }
        this.setSize(0.2f, 0.2f);
        this.standingEyeHeight = 0.2f;
        if (this.level.isTileLoaded(x, y, z)) {
            int l = this.level.getTileMeta(x, y, z);
            int i1 = BedTile.orientationOnly(l);
            float f = 0.5f;
            float f1 = 0.5f;
            switch (i1) {
                case 0: {
                    f1 = 0.9f;
                    break;
                }
                case 2: {
                    f1 = 0.1f;
                    break;
                }
                case 1: {
                    f = 0.1f;
                    break;
                }
                case 3: {
                    f = 0.9f;
                }
            }
            this.method_517(i1);
            this.setPosition((float) x + f, (float) y + 0.9375f, (float) z + f1);
        } else {
            this.setPosition((float) x + 0.5f, (float) y + 0.9375f, (float) z + 0.5f);
        }
        this.sleeping = true;
        this.sleepTimer = 0;
        this.bedPosition = new Vec3i(x, y, z);
        this.velocityY = 0.0;
        this.velocityZ = 0.0;
        this.velocityX = 0.0;
        if (!this.level.isClient) {
            this.level.areAllPlayersSleeping();
        }
        return SleepStatus.OK;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_517(int i) {
        this.field_509 = 0.0f;
        this.field_510 = 0.0f;
        switch (i) {
            case 0: {
                this.field_510 = -1.8f;
                break;
            }
            case 2: {
                this.field_510 = 1.8f;
                break;
            }
            case 1: {
                this.field_509 = 1.8f;
                break;
            }
            case 3: {
                this.field_509 = -1.8f;
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void wakeUp(boolean flag, boolean flag1, boolean flag2) {
        this.setSize(0.6f, 1.8f);
        this.updateEyeHeight();
        Vec3i chunkcoordinates = this.bedPosition;
        Vec3i chunkcoordinates1 = this.bedPosition;
        if (chunkcoordinates != null && this.level.getTileId(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z) == Tile.BED.id) {
            BedTile.setOccupied(this.level, chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z, false);
            Vec3i chunkcoordinates2 = BedTile.findWakeUpPosition(this.level, chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z, 0);
            if (chunkcoordinates2 == null) {
                chunkcoordinates2 = new Vec3i(chunkcoordinates.x, chunkcoordinates.y + 1, chunkcoordinates.z);
            }
            this.setPosition((float) chunkcoordinates2.x + 0.5f, (float) chunkcoordinates2.y + this.standingEyeHeight + 0.1f, (float) chunkcoordinates2.z + 0.5f);
        }
        this.sleeping = false;
        if (!this.level.isClient && flag1) {
            this.level.areAllPlayersSleeping();
        }
        this.sleepTimer = flag ? 0 : 100;
        if (flag2) {
            this.setPlayerSpawn(this.bedPosition);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float method_482() {
        if (this.bedPosition != null) {
            int i = this.level.getTileMeta(this.bedPosition.x, this.bedPosition.y, this.bedPosition.z);
            int j = BedTile.orientationOnly(i);
            switch (j) {
                case 0: {
                    return 90.0f;
                }
                case 1: {
                    return 0.0f;
                }
                case 2: {
                    return 270.0f;
                }
                case 3: {
                    return 180.0f;
                }
            }
        }
        return 0.0f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setPlayerSpawn(Vec3i chunkcoordinates) {
        this.spawnPos = chunkcoordinates != null ? new Vec3i(chunkcoordinates) : null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void incrementStat(Stat statbase) {
        this.increaseStat(statbase, 1);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void travel(float f, float f1) {
        double d = this.x;
        double d1 = this.y;
        double d2 = this.z;
        super.travel(f, f1);
        this.method_518(this.x - d, this.y - d1, this.z - d2);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_518(double d, double d1, double d2) {
        if (this.vehicle != null) {
            return;
        }
        if (this.isInFluid(Material.WATER)) {
            int i = Math.round(MathsHelper.sqrt(d * d + d1 * d1 + d2 * d2) * 100.0f);
            if (i > 0) {
                this.increaseStat(Stats.diveOneCm, i);
            }
        } else if (this.method_1334()) {
            int j = Math.round(MathsHelper.sqrt(d * d + d2 * d2) * 100.0f);
            if (j > 0) {
                this.increaseStat(Stats.swimOneCm, j);
            }
        } else if (this.isOnLadder()) {
            if (d1 > 0.0) {
                this.increaseStat(Stats.climbOneCm, (int) Math.round(d1 * 100.0));
            }
        } else if (this.onGround) {
            int k = Math.round(MathsHelper.sqrt(d * d + d2 * d2) * 100.0f);
            if (k > 0) {
                this.increaseStat(Stats.walkOneCm, k);
            }
        } else {
            int l = Math.round(MathsHelper.sqrt(d * d + d2 * d2) * 100.0f);
            if (l > 25) {
                this.increaseStat(Stats.flyOneCm, l);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_519(double d, double d1, double d2) {
        int i;
        if (this.vehicle != null && (i = Math.round(MathsHelper.sqrt(d * d + d1 * d1 + d2 * d2) * 100.0f)) > 0) {
            if (this.vehicle instanceof Minecart) {
                this.increaseStat(Stats.minecartOneCm, i);
                if (this.field_517 == null) {
                    this.field_517 = new Vec3i(MathsHelper.floor(this.x), MathsHelper.floor(this.y), MathsHelper.floor(this.z));
                } else if (this.field_517.method_1235(MathsHelper.floor(this.x), MathsHelper.floor(this.y), MathsHelper.floor(this.z)) >= 1000.0) {
                    this.increaseStat(Achievements.ON_A_RAIL, 1);
                }
            } else if (this.vehicle instanceof Boat) {
                this.increaseStat(Stats.boatOneCm, i);
            } else if (this.vehicle instanceof Pig) {
                this.increaseStat(Stats.pigOneCm, i);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void handleKilledEntity(LivingEntity entityliving) {
        if (entityliving instanceof Monster) {
            this.incrementStat(Achievements.KILL_ENEMY);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getItemTexturePosition(ItemInstance itemstack) {
        int i = super.getItemTexturePosition(itemstack);
        if (itemstack.itemId == ItemType.fishingRod.id && this.fishHook != null) {
            i = itemstack.getTexturePosition() + 16;
        }
        return i;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public double getGravity() {
        if (Items.hookshot.mainHookshot != null && Items.hookshot.mainHookshot.attachedToSurface || Items.hookshot.offHookshot != null && Items.hookshot.offHookshot.attachedToSurface) {
            return 0.0;
        }
        return super.getGravity();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean usingUmbrella() {
        return this.inventory.getHeldItem() != null && this.inventory.getHeldItem().itemId == Items.umbrella.id || this.inventory.getOffhandItem() != null && this.inventory.getOffhandItem().itemId == Items.umbrella.id;
    }

    @Override
    public int getHeartPieces() {
        return numHeartPieces;
    }
}
