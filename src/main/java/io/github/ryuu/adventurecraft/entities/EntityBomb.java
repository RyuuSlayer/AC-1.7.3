package io.github.ryuu.adventurecraft.entities;

import java.util.List;
import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.Box;

public class EntityBomb extends ItemEntity {

    private static final double BOMB_DAMAGE = 20.0;

    private static final double BOMB_RANGE = 5.0;

    private static final double BOMB_DESTROY_RANGE = 3.0;

    private static final int BOMB_FUSE = 45;

    private int fuse;

    private Entity parentEntity;

    public EntityBomb(Level world) {
        super(world);
        this.setSize(0.5f, 0.5f);
        this.item = new ItemInstance(Items.bomb);
        this.fuse = 45;
    }

    public EntityBomb(Level world, Entity entity) {
        this(world);
        this.parentEntity = entity;
        this.setRotation(entity.yaw, entity.pitch);
        this.velocityX = 0.3 * -Math.sin((double) (this.yaw / 180.0f * 3.141593f)) * Math.cos((double) (this.pitch / 180.0f * 3.141593f));
        this.velocityZ = 0.3 * Math.cos((double) (this.yaw / 180.0f * 3.141593f)) * Math.cos((double) (this.pitch / 180.0f * 3.141593f));
        this.velocityY = 0.3 * -Math.sin((double) (this.pitch / 180.0f * 3.141593f)) + (double) 0.1f;
        this.setPosition(entity.x, entity.y, entity.z);
        this.prevX = this.x;
        this.prevY = this.y;
        this.prevZ = this.z;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.fuse == 45) {
            this.level.playSound(this, "random.fuse", 1.0f, 1.0f);
        }
        --this.fuse;
        double fuseDuration = (double) this.fuse / 45.0;
        double fuseOffset = 0.2 * fuseDuration;
        if (this.fuse == 0) {
            EntityBomb.explode(this, this.parentEntity, this.level, this.x, this.y, this.z);
        } else if (this.fuse % 2 == 0) {
            this.level.addParticle("smoke", this.x, this.y + 0.675 + fuseOffset, this.z, 0.0, 0.0, 0.0);
        } else {
            this.level.addParticle("flame", this.x, this.y + 0.675 + fuseOffset, this.z, 0.0, 0.0, 0.0);
        }
    }

    public static void explode(Entity exploding, Entity parentEntity, Level worldObj, double posX, double posY, double posZ) {
        exploding.remove();
        worldObj.playSound(posX, posY, posZ, "random.explode", 4.0f, 1.0f);
        List list = worldObj.getEntities(exploding, Box.getOrCreate(Math.floor((double) (posX - 5.0)), Math.floor((double) (posY - 5.0)), Math.floor((double) (posZ - 5.0)), Math.ceil((double) (posX + 5.0)), Math.ceil((double) (posY + 5.0)), Math.ceil((double) (posZ + 5.0))));
        for (int i = 0; i < list.size(); ++i) {
            Entity entity = (Entity) list.get(i);
            double dist = entity.method_1350(posX, posY, posZ);
            if (!(dist < 5.0))
                continue;
            dist = (5.0 - dist) / 5.0;
            double dX = entity.x - posX;
            double dY = entity.y - posY;
            double dZ = entity.z - posZ;
            entity.method_1322(dist * dX, dist * dY, dist * dZ);
            entity.damage(parentEntity, (int) Math.ceil((double) (dist * 20.0)));
        }
        int coordX = (int) posX;
        int coordY = (int) posY;
        int coordZ = (int) posZ;
        for (int x = -3; x <= 3; ++x) {
            for (int y = -3; y <= 3; ++y) {
                for (int z = -3; z <= 3; ++z) {
                    int blockID;
                    Double distSq = (double) x * (double) x + (double) (y * y) + (double) (z * z);
                    if (!(distSq <= 9.0) || !(Tile.BY_ID[blockID = worldObj.getTileId(coordX + x, coordY + y, coordZ + z)] instanceof BlockBombable))
                        continue;
                    worldObj.setTile(coordX + x, coordY + y, coordZ + z, 0);
                }
            }
        }
        Random rand = new Random();
        rand.setSeed(worldObj.getLevelTime());
        for (int x = -3; x <= 3; ++x) {
            for (int y = -3; y <= 3; ++y) {
                for (int z = -3; z <= 3; ++z) {
                    Double distSq = (double) x * (double) x + (double) (y * y) + (double) (z * z);
                    if (rand.nextInt(3) != 0 || !(distSq <= 9.0))
                        continue;
                    Double velX = x;
                    Double velY = y;
                    Double velZ = z;
                    Double dist = Math.sqrt((double) distSq) * (0.75 + 0.5 * rand.nextDouble()) * 1.5 / 3.0;
                    velX = velX / dist;
                    velY = velY / dist;
                    velZ = velZ / dist;
                    worldObj.addParticle("explode", posX, posY, posZ, velX, velY, velZ);
                    worldObj.addParticle("smoke", posX, posY, posZ, velX, velY, velZ);
                }
            }
        }
    }

    @Override
    public boolean damage(Entity target, int amount) {
        if (!this.removed) {
            this.method_1336();
            EntityBomb.explode(this, this.parentEntity, this.level, this.x, this.y, this.z);
        }
        return false;
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.put("Fuse", (byte) this.fuse);
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.fuse = tag.getByte("Fuse");
    }

    @Override
    public void onPlayerCollision(Player entityplayer) {
    }
}
