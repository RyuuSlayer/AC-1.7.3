package io.github.ryuu.adventurecraft.entities;

import io.github.ryuu.adventurecraft.blocks.BlockBombable;
import io.github.ryuu.adventurecraft.items.Items;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.Box;

import java.util.List;
import java.util.Random;

public class EntityBomb extends ItemEntity {
    private static final double BOMB_DAMAGE = 20.0D;

    private static final double BOMB_RANGE = 5.0D;

    // TODO: utilize this constant
    private static final double BOMB_DESTROY_RANGE = 3.0D;

    private static final int BOMB_FUSE = 45;

    private int fuse;

    private Entity parentEntity;

    public EntityBomb(Level world) {
        super(world);
        setSize(0.5F, 0.5F);
        this.item = new ItemInstance(Items.bomb);
        this.fuse = BOMB_FUSE;
    }

    public EntityBomb(Level world, Entity entity) {
        this(world);
        this.parentEntity = entity;
        setRotation(entity.yaw, entity.pitch);
        this.velocityX = 0.3D * -Math.sin((this.yaw / 180.0F * 3.141593F)) * Math.cos((this.pitch / 180.0F * 3.141593F));
        this.velocityZ = 0.3D * Math.cos((this.yaw / 180.0F * 3.141593F)) * Math.cos((this.pitch / 180.0F * 3.141593F));
        this.velocityY = 0.3D * -Math.sin((this.pitch / 180.0F * 3.141593F)) + 0.10000000149011612D;
        setPosition(entity.x, entity.y, entity.z);
        this.prevX = this.x;
        this.prevY = this.y;
        this.prevZ = this.z;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.fuse == BOMB_FUSE)
            this.level.playSound(this, "random.fuse", 1.0F, 1.0F);
        this.fuse--;
        double fuseDuration = this.fuse / (double)BOMB_FUSE;
        double fuseOffset = 0.2D * fuseDuration;
        if (this.fuse == 0) {
            explode(this, this.parentEntity, this.level, this.x, this.y, this.z);
        } else if (this.fuse % 2 == 0) {
            this.level.addParticle("smoke", this.x, this.y + 0.675D + fuseOffset, this.z, 0.0D, 0.0D, 0.0D);
        } else {
            this.level.addParticle("flame", this.x, this.y + 0.675D + fuseOffset, this.z, 0.0D, 0.0D, 0.0D);
        }
    }

    public static void explode(Entity exploding, Entity parentEntity, Level worldObj, double posX, double posY, double posZ) {
        exploding.remove();
        worldObj.playSound(posX, posY, posZ, "random.explode", 4.0F, 1.0F);
        List<Entity> list = worldObj.getEntities(
            exploding,
            Box.create(
                Math.floor(posX - BOMB_RANGE),
                Math.floor(posY - BOMB_RANGE),
                Math.floor(posZ - BOMB_RANGE),
                Math.ceil(posX + BOMB_RANGE),
                Math.ceil(posY + BOMB_RANGE),
                Math.ceil(posZ + BOMB_RANGE)));

        for (int i = 0; i < list.size(); i++) {
            Entity entity = list.get(i);
            double dist = entity.method_1350(posX, posY, posZ);
            if (dist < BOMB_RANGE) {
                dist = (BOMB_RANGE - dist) / BOMB_RANGE;
                double dX = entity.x - posX;
                double dY = entity.y - posY;
                double dZ = entity.z - posZ;
                entity.method_1322(dist * dX, dist * dY, dist * dZ);
                entity.damage(parentEntity, (int) Math.ceil(dist * BOMB_DAMAGE));
            }
        }
        int coordX = (int) posX;
        int coordY = (int) posY;
        int coordZ = (int) posZ;
        for (int x = -3; x <= 3; x++) {
            for (int y = -3; y <= 3; y++) {
                for (int z = -3; z <= 3; z++) {
                    double distSq = x * x + (y * y) + (z * z);
                    if (distSq <= 9.0D) {
                        int blockID = worldObj.getTileId(coordX + x, coordY + y, coordZ + z);
                        if (Tile.BY_ID[blockID] instanceof BlockBombable)
                            worldObj.setTile(coordX + x, coordY + y, coordZ + z, 0);
                    }
                }
            }
        }
        Random rand = new Random();
        rand.setSeed(worldObj.getSeed());
        for (int j = -3; j <= 3; j++) {
            for (int y = -3; y <= 3; y++) {
                for (int z = -3; z <= 3; z++) {
                    double distSq = j * j + (y * y) + (z * z);
                    if (rand.nextInt(3) == 0 && distSq <= 9.0D) {
                        double velX = j;
                        double velY = y;
                        double velZ = z;
                        double dist = Math.sqrt(distSq) * (0.75D + 0.5D * rand.nextDouble()) * 1.5D / 3.0D;
                        velX = velX / dist;
                        velY = velY / dist;
                        velZ = velZ / dist;
                        worldObj.addParticle("explode", posX, posY, posZ, velX, velY, velZ);
                        worldObj.addParticle("smoke", posX, posY, posZ, velX, velY, velZ);
                    }
                }
            }
        }
    }

    @Override
    public boolean damage(Entity entity, int i) {
        if (!this.removed) {
            method_1336();
            explode(this, this.parentEntity, this.level, this.x, this.y, this.z);
        }
        return false;
    }

    @Override
    public void writeCustomDataToTag(CompoundTag nbttagcompound) {
        super.writeCustomDataToTag(nbttagcompound);
        nbttagcompound.put("Fuse", (byte) this.fuse);
    }

    @Override
    public void readCustomDataFromTag(CompoundTag nbttagcompound) {
        super.readCustomDataFromTag(nbttagcompound);
        this.fuse = nbttagcompound.getByte("Fuse");
    }

    @Override
    public void onPlayerCollision(Player entityplayer) {
    }
}
