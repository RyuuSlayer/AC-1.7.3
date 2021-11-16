package io.github.ryuu.adventurecraft.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;

public class MixinClientInteractionManager {
    protected final Minecraft a;

    public boolean b;

    public int destroyExtraWidth;

    public int destroyExtraDepth;

    public MixinClientInteractionManager(Minecraft minecraft) {
        this.destroyExtraWidth = 0;
        this.destroyExtraDepth = 0;
        this.b = false;
        this.a = minecraft;
    }

    public void a(Level world) {
    }

    public void a(int i, int j, int k, int l) {
        this.a.f.a((Player) this.a.h, i, j, k, l);
        b(i, j, k, l);
    }

    public boolean b(int i, int j, int k, int l) {
        Level world = this.a.f;
        Tile block = Tile.BY_ID[world.a(i, j, k)];
        world.e(2001, i, j, k, block.bn + world.e(i, j, k) * 256);
        int i1 = world.e(i, j, k);
        boolean flag = world.f(i, j, k, 0);
        if (block != null && flag)
            block.c(world, i, j, k, i1);
        return flag;
    }

    public void c(int i, int j, int k, int l) {
    }

    public void a() {
    }

    public void a(float f) {
    }

    public float b() {
        return 5.0F;
    }

    public boolean a(Player entityplayer, Level world, iz itemstack) {
        int i = itemstack.a;
        iz itemstack1 = itemstack.a(world, entityplayer);
        if (itemstack1 != itemstack || (itemstack1 != null && itemstack1.a != i)) {
            entityplayer.c.a[entityplayer.c.c] = itemstack1;
            if (itemstack1.a == 0)
                entityplayer.c.a[entityplayer.c.c] = null;
            return true;
        }
        return false;
    }

    public void a(Player entityplayer) {
    }

    public void c() {
    }

    public boolean d() {
        return true;
    }

    public void b(Player entityplayer) {
    }

    public boolean a(Player entityplayer, Level world, iz itemstack, int i, int j, int k, int l) {
        int i1 = world.a(i, j, k);
        if (i1 > 0 && Tile.BY_ID[i1].a(world, i, j, k, entityplayer))
            return true;
        if (itemstack == null)
            return false;
        return itemstack.a(entityplayer, world, i, j, k, l);
    }

    public Player b(Level world) {
        return (Player) new dc(this.a, world, this.a.k, world.t.g);
    }

    public void a(Player entityplayer, Entity entity) {
        entityplayer.c(entity);
    }

    public void b(Player entityplayer, Entity entity) {
        entityplayer.d(entity);
    }

    public iz a(int i, int j, int k, boolean flag, Player entityplayer) {
        return entityplayer.e.a(j, k, flag, entityplayer);
    }

    public void a(int i, Player entityplayer) {
        entityplayer.e.a(entityplayer);
        entityplayer.e = entityplayer.d;
    }
}
