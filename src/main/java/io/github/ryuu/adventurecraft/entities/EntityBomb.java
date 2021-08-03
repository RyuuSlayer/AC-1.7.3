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

    private static final double BOMB_DESTROY_RANGE = 3.0D;

    private static final int BOMB_FUSE = 45;

    private int fuse;

    private Entity parentEntity;

    public EntityBomb(Level world) {
        super(world);
        b(0.5F, 0.5F);
        this.a = new ItemInstance(Items.bomb);
        this.fuse = 45;
    }

    public EntityBomb(Level world, Entity entity) {
        this(world);
        this.parentEntity = entity;
        c(entity.aS, entity.aT);
        this.aP = 0.3D * -Math.sin((this.aS / 180.0F * 3.141593F)) * Math.cos((this.aT / 180.0F * 3.141593F));
        this.aR = 0.3D * Math.cos((this.aS / 180.0F * 3.141593F)) * Math.cos((this.aT / 180.0F * 3.141593F));
        this.aQ = 0.3D * -Math.sin((this.aT / 180.0F * 3.141593F)) + 0.10000000149011612D;
        e(entity.aM, entity.aN, entity.aO);
        this.aJ = this.aM;
        this.aK = this.aN;
        this.aL = this.aO;
    }

    public void w_() {
        super.w_();
        if (this.fuse == 45)
            this.aI.a(this, "random.fuse", 1.0F, 1.0F);
        this.fuse--;
        double fuseDuration = this.fuse / 45.0D;
        double fuseOffset = 0.2D * fuseDuration;
        if (this.fuse == 0) {
            explode(this, this.parentEntity, this.aI, this.aM, this.aN, this.aO);
        } else if (this.fuse % 2 == 0) {
            this.aI.a("smoke", this.aM, this.aN + 0.675D + fuseOffset, this.aO, 0.0D, 0.0D, 0.0D);
        } else {
            this.aI.a("flame", this.aM, this.aN + 0.675D + fuseOffset, this.aO, 0.0D, 0.0D, 0.0D);
        }
    }

    public static void explode(Entity exploding, Entity parentEntity, Level worldObj, double posX, double posY, double posZ) {
        exploding.K();
        worldObj.a(posX, posY, posZ, "random.explode", 4.0F, 1.0F);
        List<Entity> list = worldObj.b(exploding, Box.b(Math.floor(posX - 5.0D), Math.floor(posY - 5.0D), Math.floor(posZ - 5.0D), Math.ceil(posX + 5.0D), Math.ceil(posY + 5.0D), Math.ceil(posZ + 5.0D)));
        for (int i = 0; i < list.size(); i++) {
            Entity entity = list.get(i);
            double dist = entity.h(posX, posY, posZ);
            if (dist < 5.0D) {
                dist = (5.0D - dist) / 5.0D;
                double dX = entity.aM - posX;
                double dY = entity.aN - posY;
                double dZ = entity.aO - posZ;
                entity.d(dist * dX, dist * dY, dist * dZ);
                entity.a(parentEntity, (int) Math.ceil(dist * 20.0D));
            }
        }
        int coordX = (int) posX;
        int coordY = (int) posY;
        int coordZ = (int) posZ;
        for (int x = -3; x <= 3; x++) {
            for (int y = -3; y <= 3; y++) {
                for (int z = -3; z <= 3; z++) {
                    Double distSq = Double.valueOf(x * x + (y * y) + (z * z));
                    if (distSq.doubleValue() <= 9.0D) {
                        int blockID = worldObj.a(coordX + x, coordY + y, coordZ + z);
                        if (Tile.m[blockID] instanceof BlockBombable)
                            worldObj.f(coordX + x, coordY + y, coordZ + z, 0);
                    }
                }
            }
        }
        Random rand = new Random();
        rand.setSeed(worldObj.t());
        for (int j = -3; j <= 3; j++) {
            for (int y = -3; y <= 3; y++) {
                for (int z = -3; z <= 3; z++) {
                    Double distSq = Double.valueOf(j * j + (y * y) + (z * z));
                    if (rand.nextInt(3) == 0 && distSq.doubleValue() <= 9.0D) {
                        Double velX = Double.valueOf(j);
                        Double velY = Double.valueOf(y);
                        Double velZ = Double.valueOf(z);
                        Double dist = Double.valueOf(Math.sqrt(distSq.doubleValue()) * (0.75D + 0.5D * rand.nextDouble()) * 1.5D / 3.0D);
                        velX = Double.valueOf(velX.doubleValue() / dist.doubleValue());
                        velY = Double.valueOf(velY.doubleValue() / dist.doubleValue());
                        velZ = Double.valueOf(velZ.doubleValue() / dist.doubleValue());
                        worldObj.a("explode", posX, posY, posZ, velX.doubleValue(), velY.doubleValue(), velZ.doubleValue());
                        worldObj.a("smoke", posX, posY, posZ, velX.doubleValue(), velY.doubleValue(), velZ.doubleValue());
                    }
                }
            }
        }
    }

    public boolean a(Entity entity, int i) {
        if (!this.be) {
            ai();
            explode(this, this.parentEntity, this.aI, this.aM, this.aN, this.aO);
        }
        return false;
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Fuse", (byte) this.fuse);
    }

    public void a(CompoundTag nbttagcompound) {
        super.a(nbttagcompound);
        this.fuse = nbttagcompound.c("Fuse");
    }

    public void b(Player entityplayer) {
    }
}
