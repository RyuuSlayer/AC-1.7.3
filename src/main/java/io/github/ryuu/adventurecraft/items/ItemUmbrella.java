package io.github.ryuu.adventurecraft.items;

import java.util.List;

import io.github.ryuu.adventurecraft.entities.EntityAirFX;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingTile;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.Vec3f;

class ItemUmbrella extends ItemType {
    public ItemUmbrella(int itemIndex) {
        super(itemIndex);
        this.bg = 1;
        this.decrementDamage = true;
    }

    public int a(int i) {
        if (i > 0)
            return this.bh - 1;
        return this.bh;
    }

    public ItemInstance a(ItemInstance itemstack, Level world, Player entityplayer) {
        if (!entityplayer.aX || itemstack.i() > 0)
            return itemstack;
        Vec3f lookVec = entityplayer.ac();
        lookVec.c();
        Box aabb = Box.b(entityplayer.aM, entityplayer.aN, entityplayer.aO, entityplayer.aM, entityplayer.aN, entityplayer.aO).b(6.0D, 6.0D, 6.0D);
        List entities = world.b(entityplayer, aabb);
        for (Object obj : entities) {
            Entity e = (Entity) obj;
            double dist = e.g(entityplayer);
            if (dist < 36.0D && !(e instanceof FallingTile)) {
                double dX = e.aM - entityplayer.aM;
                double dY = e.aN - entityplayer.aN;
                double dZ = e.aO - entityplayer.aO;
                dist = Math.sqrt(dist);
                dX /= dist;
                dY /= dist;
                dZ /= dist;
                double dotProduct = dX * lookVec.a + dY * lookVec.b + dZ * lookVec.c;
                if (dotProduct > 0.0D && Math.acos(dotProduct) < 1.5707963267948966D) {
                    dist = Math.max(dist, 3.0D);
                    e.d(3.0D * dX / dist, 3.0D * dY / dist, 3.0D * dZ / dist);
                }
            }
        }
        entities = Minecraft.minecraftInstance.j.getEffectsWithinAABB(aabb);
        for (Object obj : entities) {
            Entity e = (Entity) obj;
            double dist = e.g(entityplayer);
            if (dist < 36.0D) {
                double dX = e.aM - entityplayer.aM;
                double dY = e.aN - entityplayer.aN;
                double dZ = e.aO - entityplayer.aO;
                dist = Math.sqrt(dist);
                dX /= dist;
                dY /= dist;
                dZ /= dist;
                double dotProduct = dX * lookVec.a + dY * lookVec.b + dZ * lookVec.c;
                if (dotProduct > 0.0D && Math.acos(dotProduct) < 1.5707963267948966D)
                    e.d(6.0D * dX / dist, 6.0D * dY / dist, 6.0D * dZ / dist);
            }
        }
        for (int i = 0; i < 25; i++) {
            EntityAirFX fx = new EntityAirFX(world, entityplayer.aM, entityplayer.aN, entityplayer.aO);
            fx.aP = lookVec.a * (1.0D + 0.05D * world.r.nextGaussian()) + 0.2D * world.r.nextGaussian();
            fx.aQ = lookVec.b * (1.0D + 0.05D * world.r.nextGaussian()) + 0.2D * world.r.nextGaussian();
            fx.aR = lookVec.c * (1.0D + 0.05D * world.r.nextGaussian()) + 0.2D * world.r.nextGaussian();
            Minecraft.minecraftInstance.j.a((Particle) fx);
        }
        entityplayer.J();
        itemstack.b(10);
        return itemstack;
    }
}