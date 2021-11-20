package io.github.ryuu.adventurecraft.mixin.entity.monster;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MonsterEntityType;
import net.minecraft.entity.WalkingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.LightType;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.MathsHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Monster.class)
public class MixinMonster extends WalkingEntity implements MonsterEntityType {

    @Shadow()
    public int attackDamage = 2;

    public MixinMonster(Level world) {
        super(world);
        this.health = 20;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected Entity method_638() {
        Player entityplayer = this.level.getClosestPlayerTo(this, 16.0);
        if (entityplayer != null && this.method_928(entityplayer)) {
            return entityplayer;
        }
        return null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean damage(Entity target, int amount) {
        this.timeBeforeForget = 40;
        if (super.damage(target, amount)) {
            if (this.passenger == target || this.vehicle == target) {
                return true;
            }
            if (target != this) {
                this.entity = target;
            }
            return true;
        }
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean attackEntityFromMulti(Entity entity, int i) {
        this.timeBeforeForget = 40;
        if (super.attackEntityFromMulti(entity, i)) {
            if (this.passenger == entity || this.vehicle == entity) {
                return true;
            }
            if (entity != this) {
                this.entity = entity;
            }
            return true;
        }
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected float getPathfindingFavour(int i, int j, int k) {
        return 0.5f - this.level.getBrightness(i, j, k);
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
    public boolean canSpawn() {
        int k;
        int j;
        int i = MathsHelper.floor(this.x);
        if (this.level.getLightLevel(LightType.Sky, i, j = MathsHelper.floor(this.boundingBox.minY), k = MathsHelper.floor(this.z)) > this.rand.nextInt(32)) {
            return false;
        }
        int l = this.level.getLightLevel(i, j, k);
        if (this.level.thundering()) {
            int i1 = this.level.field_202;
            this.level.field_202 = 10;
            l = this.level.getLightLevel(i, j, k);
            this.level.field_202 = i1;
        }
        return l <= this.rand.nextInt(8) && super.canSpawn();
    }
}
