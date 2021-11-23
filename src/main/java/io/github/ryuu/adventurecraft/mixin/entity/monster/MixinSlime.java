package io.github.ryuu.adventurecraft.mixin.entity.monster;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MonsterEntityType;
import net.minecraft.entity.monster.Slime;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.MathsHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Slime.class)
public class MixinSlime extends LivingEntity implements MonsterEntityType {

    @Shadow()
    public float field_1951;

    public float field_1952;
    public int attackStrength;
    private int field_1953 = 0;

    public MixinSlime(Level world) {
        super(world);
        this.texture = "/mob/slime.png";
        int i = 1 << this.rand.nextInt(3);
        this.standingEyeHeight = 0.0f;
        this.field_1953 = this.rand.nextInt(20) + 10;
        this.setSize(i);
        this.attackStrength = -1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(16, new Byte(1));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setSize(int i) {
        this.dataTracker.setData(16, new Byte((byte) i));
        this.setSize(0.6f * (float) i, 0.6f * (float) i);
        this.health = i * i;
        this.setPosition(this.x, this.y, this.z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.put("Size", this.getSize() - 1);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.setSize(tag.getInt("Size") + 1);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void tick() {
        this.field_1952 = this.field_1951;
        boolean flag = this.onGround;
        super.tick();
        if (this.onGround && !flag) {
            int i = this.getSize();
            for (int j = 0; j < i * 8; ++j) {
                float f = this.rand.nextFloat() * 3.141593f * 2.0f;
                float f1 = this.rand.nextFloat() * 0.5f + 0.5f;
                float f2 = MathsHelper.sin(f) * (float) i * 0.5f * f1;
                float f3 = MathsHelper.cos(f) * (float) i * 0.5f * f1;
                this.level.addParticle("slime", this.x + (double) f2, this.boundingBox.minY, this.z + (double) f3, 0.0, 0.0, 0.0);
            }
            if (i > 2) {
                this.level.playSound(this, "mob.slime", this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) / 0.8f);
            }
            this.field_1951 = -0.5f;
        }
        this.field_1951 *= 0.6f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void tickHandSwing() {
        this.method_920();
        Player entityplayer = this.level.getClosestPlayerTo(this, 16.0);
        if (entityplayer != null) {
            this.method_924(entityplayer, 10.0f, 20.0f);
        }
        if (this.onGround && this.field_1953-- <= 0) {
            this.field_1953 = this.rand.nextInt(20) + 10;
            if (entityplayer != null) {
                this.field_1953 /= 3;
            }
            this.jumping = true;
            if (this.getSize() > 1) {
                this.level.playSound(this, "mob.slime", this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) * 0.8f);
            }
            this.field_1951 = 1.0f;
            this.perpendicularMovement = 1.0f - this.rand.nextFloat() * 2.0f;
            this.parallelMovement = 1 * this.getSize();
            float length = (float) Math.sqrt(this.perpendicularMovement * this.perpendicularMovement + this.parallelMovement * this.parallelMovement);
            this.perpendicularMovement /= length;
            this.parallelMovement /= length;
        } else {
            this.jumping = false;
            if (this.onGround) {
                this.parallelMovement = 0.0f;
                this.perpendicularMovement = 0.0f;
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void remove() {
        super.remove();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void onPlayerCollision(Player entityplayer) {
        int i;
        int j = i = this.getSize();
        if (this.attackStrength != -1) {
            j = this.attackStrength;
        }
        if ((i > 1 || this.attackStrength != -1) && this.method_928(entityplayer) && (double) this.distanceTo(entityplayer) < 0.6 * (double) i && entityplayer.damage(this, j)) {
            this.level.playSound(this, "mob.slimeattack", 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
        }
    }
}
