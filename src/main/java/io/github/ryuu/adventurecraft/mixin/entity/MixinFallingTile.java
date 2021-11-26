package io.github.ryuu.adventurecraft.mixin.entity;

import io.github.ryuu.adventurecraft.extensions.entity.ExFallingTile;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FallingTile.class)
public abstract class MixinFallingTile extends Entity implements ExFallingTile {

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

    @Inject(method = "<init>(Lnet/minecraft/level/Level;DDDI)V", at = @At("TAIL"))
    private void init(Level d, double d1, double d2, double i, int par5, CallbackInfo ci) {
        this.startX = d1;
        this.startZ = d2;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite
    public void tick() {
        if (this.tile == 0) {
            this.remove();
            return;
        }
        this.prevX = this.x;
        this.prevY = this.y;
        this.prevZ = this.z;
        ++this.field_848;
        this.velocityY -= 0.04f;
        this.move(this.velocityX, this.velocityY, this.velocityZ);
        this.velocityX *= 0.98f;
        this.velocityY *= 0.98f;
        this.velocityZ *= 0.98f;
        int i = MathsHelper.floor(this.x);
        int j = MathsHelper.floor(this.y);
        int k = MathsHelper.floor(this.z);
        if (this.level.getTileId(i, j, k) == this.tile) {
            this.level.setTile(i, j, k, 0);
        }
        if (this.onGround && Math.abs(this.velocityX) < 0.01 && Math.abs(this.velocityZ) < 0.01) {
            this.velocityX *= 0.7f;
            this.velocityZ *= 0.7f;
            this.velocityY *= -0.5;
            if (!SandTile.method_435(this.level, i, j - 1, k)) {
                this.remove();
                if (!(this.level.canPlaceTile(this.tile, i, j, k, true, 1) && this.level.method_201(i, j, k, this.tile, this.metadata) || this.level.isClient)) {
                    this.dropItem(this.tile, 1);
                }
            } else {
                this.setPosition((double) i + 0.5, this.y, (double) k + 0.5);
                this.velocityX = 0.0;
                this.velocityZ = 0.0;
            }
        } else if (this.field_848 > 100 && !this.level.isClient) {
            this.dropItem(this.tile, 1);
            this.remove();
        }
        if (Math.abs(this.x - this.startX) >= 1.0) {
            this.velocityX = 0.0;
            this.setPosition((double) i + 0.5, this.y, this.z);
        }
        if (Math.abs(this.z - this.startZ) >= 1.0) {
            this.velocityZ = 0.0;
            this.setPosition(this.x, this.y, (double) k + 0.5);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite
    protected void writeCustomDataToTag(CompoundTag tag) {
        tag.put("Tile", (byte) this.tile);
        tag.put("EntityID", this.id);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite
    protected void readCustomDataFromTag(CompoundTag tag) {
        this.tile = tag.getByte("Tile") & 0xFF;
        if (tag.containsKey("EntityID")) {
            this.id = tag.getInt("EntityID");
        }
    }

    @Override
    public int getMetadata() {
        return this.metadata;
    }

    @Override
    public void setMetadata(int metadata) {
        this.metadata = metadata;
    }
}
