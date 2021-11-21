package io.github.ryuu.adventurecraft.items;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingTile;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.Vec3f;

class ItemUmbrella extends ItemType {

    public ItemUmbrella(int id) {
        super(id);
        this.maxStackSize = 1;
        this.decrementDamage = true;
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
        double dotProduct;
        double dZ;
        double dY;
        double dX;
        double dist;
        Entity e;
        if (!player.onGround || item.getDamage() > 0) {
            return item;
        }
        Vec3f lookVec = player.method_1320();
        lookVec.method_1296();
        Box aabb = Box.getOrCreate(player.x, player.y, player.z, player.x, player.y, player.z).expand(6.0, 6.0, 6.0);
        List entities = level.getEntities(player, aabb);
        for (Object obj : entities) {
            e = (Entity) obj;
            dist = e.method_1352(player);
            if (!(dist < 36.0) || e instanceof FallingTile)
                continue;
            dX = e.x - player.x;
            dY = e.y - player.y;
            dZ = e.z - player.z;
            dotProduct = (dX /= (dist = Math.sqrt((double) dist))) * lookVec.x + (dY /= dist) * lookVec.y + (dZ /= dist) * lookVec.z;
            if (!(dotProduct > 0.0) || !(Math.acos((double) dotProduct) < 1.5707963267948966))
                continue;
            dist = Math.max((double) dist, (double) 3.0);
            e.method_1322(3.0 * dX / dist, 3.0 * dY / dist, 3.0 * dZ / dist);
        }
        entities = Minecraft.minecraftInstance.particleManager.getEffectsWithinAABB(aabb);
        for (Object obj : entities) {
            e = (Entity) obj;
            dist = e.method_1352(player);
            if (!(dist < 36.0))
                continue;
            dX = e.x - player.x;
            dY = e.y - player.y;
            dZ = e.z - player.z;
            dotProduct = (dX /= (dist = Math.sqrt((double) dist))) * lookVec.x + (dY /= dist) * lookVec.y + (dZ /= dist) * lookVec.z;
            if (!(dotProduct > 0.0) || !(Math.acos((double) dotProduct) < 1.5707963267948966))
                continue;
            e.method_1322(6.0 * dX / dist, 6.0 * dY / dist, 6.0 * dZ / dist);
        }
        for (int i = 0; i < 25; ++i) {
            EntityAirFX fx = new EntityAirFX(level, player.x, player.y, player.z);
            fx.velocityX = lookVec.x * (1.0 + 0.05 * level.rand.nextGaussian()) + 0.2 * level.rand.nextGaussian();
            fx.velocityY = lookVec.y * (1.0 + 0.05 * level.rand.nextGaussian()) + 0.2 * level.rand.nextGaussian();
            fx.velocityZ = lookVec.z * (1.0 + 0.05 * level.rand.nextGaussian()) + 0.2 * level.rand.nextGaussian();
            Minecraft.minecraftInstance.particleManager.addParticle(fx);
        }
        player.swingHand();
        item.setDamage(10);
        return item;
    }
}
