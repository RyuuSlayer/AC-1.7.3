package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.entities.EntityAirFX;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import io.github.ryuu.adventurecraft.extensions.client.particle.ExParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingTile;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.Vec3d;

import java.util.List;

public class ItemUmbrella extends ItemType implements DamageableItemType {

    public ItemUmbrella(int id) {
        super(id);
        this.maxStackSize = 1;
    }

    @Override
    public boolean canDecrementDamage() {
        return true;
    }

    @Override
    public int getTexturePosition(int damage) {
        if (damage > 0) {
            return this.texturePosition - 1;
        }
        return this.texturePosition;
    }

    @Override
    public ItemInstance use(ItemInstance item, Level level, Player player) {
        if (!player.onGround || item.getDamage() > 0) {
            return item;
        }
        Vec3d lookVec = player.getRotation();
        lookVec.normalise();
        Box aabb = Box.getOrCreate(player.x, player.y, player.z, player.x, player.y, player.z).expand(6.0, 6.0, 6.0);
        List<Entity> entities = (List<Entity>)level.getEntities(player, aabb);
        for (Entity e : entities) {
            double dist = e.method_1352(player);
            if (dist < 36.0 && !(e instanceof FallingTile)) {
                double dX = e.x - player.x;
                double dY = e.y - player.y;
                double dZ = e.z - player.z;
                double dot = (dX /= (dist = Math.sqrt(dist))) * lookVec.x + (dY /= dist) * lookVec.y + (dZ /= dist) * lookVec.z;
                if (dot > 0.0 && Math.acos(dot) < 1.5707963267948966) {
                    dist = Math.max(dist, 3.0);
                    e.method_1322(3.0 * dX / dist, 3.0 * dY / dist, 3.0 * dZ / dist);
                }
            }
        }
        entities = ((ExParticleManager)AccessMinecraft.getInstance().particleManager).getEffectsWithinAABB(aabb);
        for (Entity e : entities) {
            double dist = e.method_1352(player);
            if (dist < 36.0) {
                double dX = e.x - player.x;
                double dY = e.y - player.y;
                double dZ = e.z - player.z;
                double dot = (dX /= (dist = Math.sqrt(dist))) * lookVec.x + (dY /= dist) * lookVec.y + (dZ /= dist) * lookVec.z;
                if (dot > 0.0 && Math.acos(dot) < 1.5707963267948966) {
                    e.method_1322(6.0 * dX / dist, 6.0 * dY / dist, 6.0 * dZ / dist);
                }
            }
        }
        for (int i = 0; i < 25; ++i) {
            EntityAirFX fx = new EntityAirFX(level, player.x, player.y, player.z);
            fx.velocityX = lookVec.x * (1.0 + 0.05 * level.rand.nextGaussian()) + 0.2 * level.rand.nextGaussian();
            fx.velocityY = lookVec.y * (1.0 + 0.05 * level.rand.nextGaussian()) + 0.2 * level.rand.nextGaussian();
            fx.velocityZ = lookVec.z * (1.0 + 0.05 * level.rand.nextGaussian()) + 0.2 * level.rand.nextGaussian();
            AccessMinecraft.getInstance().particleManager.addParticle(fx);
        }
        player.swingHand();
        item.setDamage(10);
        return item;
    }
}
