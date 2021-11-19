package io.github.ryuu.adventurecraft.mixin.entity;

import net.minecraft.entity.FlyingEntity;
import net.minecraft.tile.Tile;
import net.minecraft.util.maths.MathsHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FlyingEntity.class)
public class MixinFlyingEntity extends MixinLivingEntity {

    @Shadow()
    public int attackStrength = 1;

    public MixinFlyingEntity(MixinLevel world) {
        super(world);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void handleFallDamage(float f) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void travel(float f, float f1) {
        if (this.method_1334()) {
            this.movementInputToVelocity(f, f1, 0.02f);
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            this.velocityX *= 0.8f;
            this.velocityY *= 0.8f;
            this.velocityZ *= 0.8f;
        } else if (this.method_1335()) {
            this.movementInputToVelocity(f, f1, 0.02f);
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            this.velocityX *= 0.5;
            this.velocityY *= 0.5;
            this.velocityZ *= 0.5;
        } else {
            float f2 = 0.91f;
            if (this.onGround) {
                f2 = 0.5460001f;
                int i = this.level.getTileId(MathsHelper.floor(this.x), MathsHelper.floor(this.boundingBox.minY) - 1, MathsHelper.floor(this.z));
                if (i > 0) {
                    f2 = Tile.BY_ID[i].field_1901 * 0.91f;
                }
            }
            float f3 = 0.1627714f / (f2 * f2 * f2);
            this.movementInputToVelocity(f, f1, this.onGround ? 0.1f * f3 : 0.02f);
            f2 = 0.91f;
            if (this.onGround) {
                f2 = 0.5460001f;
                int j = this.level.getTileId(MathsHelper.floor(this.x), MathsHelper.floor(this.boundingBox.minY) - 1, MathsHelper.floor(this.z));
                if (j > 0) {
                    f2 = Tile.BY_ID[j].field_1901 * 0.91f;
                }
            }
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            this.velocityX *= f2;
            this.velocityY *= f2;
            this.velocityZ *= f2;
        }
        this.field_1048 = this.limbDistance;
        double d = this.x - this.prevX;
        double d1 = this.z - this.prevZ;
        float f4 = MathsHelper.sqrt(d * d + d1 * d1) * 4.0f;
        if (f4 > 1.0f) {
            f4 = 1.0f;
        }
        this.limbDistance += (f4 - this.limbDistance) * 0.4f;
        this.field_1050 += this.limbDistance;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean isOnLadder() {
        return false;
    }
}
