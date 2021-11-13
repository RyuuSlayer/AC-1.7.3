package io.github.ryuu.adventurecraft.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingTile;
import net.minecraft.level.Level;
import net.minecraft.tile.SandTile;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.MathsHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(FallingTile.class)
public abstract class MixinFallingTile extends Entity {

    @Shadow
    public int tile;

    @Shadow
    public int field_848;

    public int metadata;
    public double startX;
    public double startZ;

    public MixinFallingTile(Level arg) {
        super(arg);
    }

    @Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/level/Level;)V")
    private void constructor(Level world) {
        setSize(0.98F, 0.98F);
        this.standingEyeHeight = this.height / 2.0F;
    }

    @Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/level/Level;DDDI)V")
    private void constructor(Level world, double d, double d1, double d2, int i) {
        constructor(world);
        this.startX = d;
        this.startZ = d2;
    }

    protected boolean n() {
        return false;
    }

    protected void b() {
    }

    @Overwrite
    public boolean method_1356() {
        return !this.removed;
    }

    /**
     * @author TechPizza
     */
    @Overwrite
    public void tick() {
        if (this.tile == 0) {
            remove();
        } else {
            this.prevX = this.x;
            this.prevY = this.y;
            this.prevZ = this.z;
            this.field_848++;
            this.velocityY -= 0.03999999910593033D;
            move(this.velocityX, this.velocityY, this.velocityZ);
            this.velocityX *= 0.9800000190734863D;
            this.velocityY *= 0.9800000190734863D;
            this.velocityZ *= 0.9800000190734863D;
            int i = MathsHelper.floor(this.x);
            int j = MathsHelper.floor(this.y);
            int k = MathsHelper.floor(this.z);
            if (this.level.getTileId(i, j, k) == this.tile)
                this.level.setTile(i, j, k, 0);
            if (this.onGround && Math.abs(this.velocityX) < 0.01D && Math.abs(this.velocityZ) < 0.01D) {
                this.velocityX *= 0.699999988079071D;
                this.velocityZ *= 0.699999988079071D;
                this.velocityY *= -0.5D;
                if (!SandTile.method_435(this.level, i, j - 1, k)) {
                    remove();
                    if ((!this.level.canPlaceTile(this.tile, i, j, k, true, 1) || !this.level.method_201(i, j, k, this.tile, this.metadata)) && !this.level.isClient)
                        dropItem(this.tile, 1);
                } else {
                    setPosition(i + 0.5D, this.y, k + 0.5D);
                    this.velocityX = 0.0D;
                    this.velocityZ = 0.0D;
                }
            } else if (this.field_848 > 100 && !this.level.isClient) {
                dropItem(this.tile, 1);
                remove();
            }
            if (Math.abs(this.x - this.startX) >= 1.0D) {
                this.velocityX = 0.0D;
                setPosition(i + 0.5D, this.y, this.z);
            }
            if (Math.abs(this.z - this.startZ) >= 1.0D) {
                this.velocityZ = 0.0D;
                setPosition(this.x, this.y, k + 0.5D);
            }
        }
    }

    @Inject(at = @At("TAIL"), method = "writeCustomDataToTag(Lnet/minecraft/util/io/CompoundTag;)V")
    private void writeACDataToTag(CompoundTag nbttagcompound) {
        nbttagcompound.put("EntityID", this.id);
    }

    @Inject(at = @At("TAIL"), method = "readCustomDataFromTag(Lnet/minecraft/util/io/CompoundTag;)V")
    private void readACDataFromTag(CompoundTag nbttagcompound) {
        if (nbttagcompound.containsKey("EntityID"))
            this.id = nbttagcompound.getInt("EntityID");
    }
}
