package io.github.ryuu.adventurecraft.mixin.entity;

import net.minecraft.achievement.Achievements;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemType;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.MathsHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemEntity.class)
public class MixinItemEntity extends MixinEntity {

    @Shadow()
    public MixinItemInstance item;
    public int age = 0;
    public int pickupDelay;
    public float field_567 = (float) (Math.random() * Math.PI * 2.0);
    private int field_568;
    private int health = 5;

    public MixinItemEntity(MixinLevel world, double d, double d1, double d2, MixinItemInstance itemstack) {
        super(world);
        this.setSize(0.25f, 0.25f);
        this.standingEyeHeight = this.height / 2.0f;
        this.setPosition(d, d1, d2);
        this.item = itemstack;
        this.yaw = (float) (Math.random() * 360.0);
        this.velocityX = (float) (Math.random() * (double) 0.2f - (double) 0.1f);
        this.velocityY = 0.2f;
        this.velocityZ = (float) (Math.random() * (double) 0.2f - (double) 0.1f);
        if (ItemType.byId[this.item.itemId] == null) {
            this.remove();
        }
    }

    public MixinItemEntity(MixinLevel world) {
        super(world);
        this.setSize(0.25f, 0.25f);
        this.standingEyeHeight = this.height / 2.0f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected boolean canClimb() {
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void initDataTracker() {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void tick() {
        super.tick();
        if (this.pickupDelay > 0) {
            --this.pickupDelay;
        }
        this.prevX = this.x;
        this.prevY = this.y;
        this.prevZ = this.z;
        this.velocityY -= 0.04f;
        if (this.level.getMaterial(MathsHelper.floor(this.x), MathsHelper.floor(this.y), MathsHelper.floor(this.z)) == Material.LAVA) {
            this.velocityY = 0.2f;
            this.velocityX = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f;
            this.velocityZ = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f;
            this.level.playSound(this, "random.fizz", 0.4f, 2.0f + this.rand.nextFloat() * 0.4f);
        }
        this.method_1372(this.x, (this.boundingBox.minY + this.boundingBox.maxY) / 2.0, this.z);
        this.move(this.velocityX, this.velocityY, this.velocityZ);
        float f = 0.98f;
        if (this.onGround) {
            f = 0.5880001f;
            int i = this.level.getTileId(MathsHelper.floor(this.x), MathsHelper.floor(this.boundingBox.minY) - 1, MathsHelper.floor(this.z));
            if (i > 0) {
                f = Tile.BY_ID[i].field_1901 * 0.98f;
            }
        }
        this.velocityX *= f;
        this.velocityY *= 0.98f;
        this.velocityZ *= f;
        if (this.onGround) {
            this.velocityY *= -0.5;
        }
        ++this.field_568;
        ++this.age;
        if (this.age >= 6000) {
            this.remove();
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean method_1393() {
        return this.level.method_170(this.boundingBox, Material.WATER, this);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void method_1392(int i) {
        this.damage(null, i);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean damage(MixinEntity target, int amount) {
        this.method_1336();
        this.health -= amount;
        if (this.health <= 0) {
            this.remove();
        }
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void writeCustomDataToTag(MixinCompoundTag tag) {
        tag.put("Health", (short) ((byte) this.health));
        tag.put("Age", (short) this.age);
        tag.put("Item", this.item.toTag(new MixinCompoundTag()));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void readCustomDataFromTag(MixinCompoundTag tag) {
        this.health = tag.getShort("Health") & 0xFF;
        this.age = tag.getShort("Age");
        MixinCompoundTag nbttagcompound1 = tag.getCompoundTag("Item");
        this.item = new MixinItemInstance(nbttagcompound1);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void onPlayerCollision(MixinPlayer entityplayer) {
        if (this.level.isClient) {
            return;
        }
        int i = this.item.count;
        if (this.pickupDelay == 0 && entityplayer.inventory.pickupItem(this.item)) {
            if (this.item.itemId == Tile.LOG.id) {
                entityplayer.incrementStat(Achievements.MINE_WOOD);
            }
            if (this.item.itemId == ItemType.leather.id) {
                entityplayer.incrementStat(Achievements.KILL_COW);
            }
            this.level.playSound(this, "random.pop", 0.2f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
            entityplayer.onEntityCollision(this, i);
            if (this.item.count <= 0) {
                this.remove();
            }
        }
    }
}
