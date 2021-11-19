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
        this.maxStackSize = 1;
        this.decrementDamage = true;
    }

    @Override
    public int getTexturePosition(int i) {
        if (i > 0)
            return this.texturePosition - 1;
        return this.texturePosition;
    }

    @Override
    public ItemInstance use(ItemInstance itemstack, Level world, Player entityplayer) {
        if (!entityplayer.onGround || itemstack.getDamage() > 0)
            return itemstack;
        Vec3f lookVec = entityplayer.method_1320();
        lookVec.c();
        Box aabb = Box.create(entityplayer.x, entityplayer.y, entityplayer.z, entityplayer.x, entityplayer.y, entityplayer.z).b(6.0D, 6.0D, 6.0D);
        List entities = world.getEntities(entityplayer, aabb);
        for (Object obj : entities) {
            Entity e = (Entity) obj;
            double dist = e.method_1352(entityplayer);
            if (dist < 36.0D && !(e instanceof FallingTile)) {
                double dX = e.x - entityplayer.x;
                double dY = e.y - entityplayer.y;
                double dZ = e.z - entityplayer.z;
                dist = Math.sqrt(dist);
                dX /= dist;
                dY /= dist;
                dZ /= dist;
                double dotProduct = dX * lookVec.x + dY * lookVec.y + dZ * lookVec.z;
                if (dotProduct > 0.0D && Math.acos(dotProduct) < 1.5707963267948966D) {
                    dist = Math.max(dist, 3.0D);
                    e.method_1322(3.0D * dX / dist, 3.0D * dY / dist, 3.0D * dZ / dist); // not sure if it's method_1322
                }
            }
        }
        entities = Minecraft.minecraftInstance.j.getEffectsWithinAABB(aabb);
        for (Object obj : entities) {
            Entity e = (Entity) obj;
            double dist = e.distanceTo(entityplayer);
            if (dist < 36.0D) {
                double dX = e.x - entityplayer.x;
                double dY = e.y - entityplayer.y;
                double dZ = e.z - entityplayer.z;
                dist = Math.sqrt(dist);
                dX /= dist;
                dY /= dist;
                dZ /= dist;
                double dotProduct = dX * lookVec.x + dY * lookVec.y + dZ * lookVec.z;
                if (dotProduct > 0.0D && Math.acos(dotProduct) < 1.5707963267948966D)
                    e.method_1322(6.0D * dX / dist, 6.0D * dY / dist, 6.0D * dZ / dist); // not sure if it's method_1322
            }
        }
        for (int i = 0; i < 25; i++) {
            EntityAirFX fx = new EntityAirFX(world, entityplayer.x, entityplayer.y, entityplayer.z);
            fx.velocityX = lookVec.x * (1.0D + 0.05D * world.rand.nextGaussian()) + 0.2D * world.rand.nextGaussian();
            fx.velocityY = lookVec.y * (1.0D + 0.05D * world.rand.nextGaussian()) + 0.2D * world.rand.nextGaussian();
            fx.velocityZ = lookVec.z * (1.0D + 0.05D * world.rand.nextGaussian()) + 0.2D * world.rand.nextGaussian();
            Minecraft.minecraftInstance.j.a((Particle) fx);
        }
        entityplayer.swingHand();
        itemstack.setDamage(10);
        return itemstack;
    }
}