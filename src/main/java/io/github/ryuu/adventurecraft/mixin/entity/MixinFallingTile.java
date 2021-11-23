package io.github.ryuu.adventurecraft.mixin.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingTile;
import net.minecraft.level.Level;
import net.minecraft.tile.SandTile;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.MathsHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FallingTile.class)
public class MixinFallingTile extends Entity {

    @Shadow()
    public int tile;

    public int metadata;

    public int field_848 = 0;

    public double startX;

    public double startZ;

    public MixinFallingTile(Level world) {
        super(world);
        this.setSize(0.98f, 0.98f);
        this.height = 0.98f;
        this.standingEyeHeight = this.height / 2.0f;
    }

    public MixinFallingTile(Level world, double d, double d1, double d2, int i) {
        super(world);
        this.tile = i;
        this.field_1593 = true;
        this.setSize(0.98f, 0.98f);
        this.height = 0.98f;
        this.standingEyeHeight = this.height / 2.0f;
        this.setPosition(d, d1, d2);
        this.velocityX = 0.0;
        this.velocityY = 0.0;
        this.velocityZ = 0.0;
        this.prevX = d;
        this.prevY = d1;
        this.prevZ = d2;
        this.startX = d;
        this.startZ = d2;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
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
    @Overwrite()
    protected void writeCustomDataToTag(CompoundTag tag) {
        tag.put("Tile", (byte) this.tile);
        tag.put("EntityID", this.id);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void readCustomDataFromTag(CompoundTag tag) {
        this.tile = tag.getByte("Tile") & 0xFF;
        if (tag.containsKey("EntityID")) {
            this.id = tag.getInt("EntityID");
        }
    }
}
