package io.github.ryuu.adventurecraft.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MonsterEntityType;
import net.minecraft.entity.WalkingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.MathsHelper;

public class MixinMonster extends WalkingEntity implements MonsterEntityType {
    public int c;

    public MixinMonster(Level world) {
        super(world);
        this.c = 2;
        this.Y = 20;
    }

    public void o() {
        float f = a(1.0F);
        if (f > 0.5F)
            this.av += 2;
        super.o();
    }

    public void w_() {
        super.w_();
        if (!this.aI.B && this.aI.q == 0)
            K();
    }

    protected Entity g_() {
        Player entityplayer = this.aI.a(this, 16.0D);
        if (entityplayer != null && e(entityplayer))
            return entityplayer;
        return null;
    }

    public boolean a(Entity entity, int i) {
        this.timeBeforeForget = 40;
        if (super.a(entity, i)) {
            if (this.aG == entity || this.aH == entity)
                return true;
            if (entity != this)
                this.d = entity;
            return true;
        }
        return false;
    }

    public boolean attackEntityFromMulti(Entity entity, int i) {
        this.timeBeforeForget = 40;
        if (super.attackEntityFromMulti(entity, i)) {
            if (this.aG == entity || this.aH == entity)
                return true;
            if (entity != this)
                this.d = entity;
            return true;
        }
        return false;
    }

    protected void a(Entity entity, float f) {
        if (this.ae <= 0 && f < 2.0F && entity.aW.e > this.aW.b && entity.aW.b < this.aW.e) {
            this.ae = 20;
            entity.a(this, this.c);
        }
    }

    protected float a(int i, int j, int k) {
        return 0.5F - this.aI.c(i, j, k);
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
    }

    public void a(CompoundTag nbttagcompound) {
        super.a(nbttagcompound);
    }

    public boolean d() {
        int i = MathsHelper.b(this.aM);
        int j = MathsHelper.b(this.aW.b);
        int k = MathsHelper.b(this.aO);
        if (this.aI.a(eb.a, i, j, k) > this.bs.nextInt(32))
            return false;
        int l = this.aI.n(i, j, k);
        if (this.aI.B()) {
            int i1 = this.aI.f;
            this.aI.f = 10;
            l = this.aI.n(i, j, k);
            this.aI.f = i1;
        }
        return (l <= this.bs.nextInt(8) && super.d());
    }
}
