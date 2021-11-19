package io.github.ryuu.adventurecraft.entities;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.lang.System
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_61;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;

public class EntityNPC extends EntityLivingScript {

    public String npcName;

    public String chatMsg;

    boolean initialSpot = false;

    public double spawnX;

    public double spawnY;

    public double spawnZ;

    public boolean pathToHome = true;

    public boolean trackPlayer = true;

    public boolean isAttackable = false;

    int ticksTillNewPath = 0;

    class_61 pathToPoint = null;

    Entity entityToTrack;

    private boolean ranOnCreate = false;

    public EntityNPC(Level world) {
        super(world);
        this.texture = "/mob/char.png";
        this.npcName = "New NPC";
        this.chatMsg = "Hello!";
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    public void tick() {
        if (!this.ranOnCreate) {
            this.ranOnCreate = true;
            this.runCreatedScript();
        }
        if (this.pathToHome && !this.isPathing() && this.squaredDistanceTo(this.spawnX, this.spawnY, this.spawnZ) > 4.0) {
            this.pathToPosition((int) this.spawnX, (int) this.spawnY, (int) this.spawnZ);
        }
        super.tick();
        if (this.trackPlayer && this.entityToTrack == null) {
            this.entityToTrack = this.findPlayerToTrack();
        }
        if (this.entityToTrack != null) {
            if (!this.entityToTrack.isAlive()) {
                this.entityToTrack = null;
            } else if (this.entityToTrack.distanceTo(this) > 16.0f || !this.method_928(this.entityToTrack)) {
                this.entityToTrack = null;
            }
        }
        if (this.entityToTrack != null) {
            float delta;
            double d4 = this.entityToTrack.x - this.x;
            double d5 = this.entityToTrack.z - this.z;
            float desiredYaw = (float) (Math.atan2((double) d5, (double) d4) * 180.0 / 3.1415927410125732) - 90.0f;
            for (delta = desiredYaw - this.yaw; delta < -180.0f; delta += 360.0f) {
            }
            while (delta > 180.0f) {
                delta -= 360.0f;
            }
            delta = Math.max((float) Math.min((float) delta, (float) 10.0f), (float) -10.0f);
            this.yaw += delta;
        }
    }

    @Override
    protected void tickHandSwing() {
        if (!this.initialSpot) {
            this.initialSpot = true;
            this.spawnX = this.x;
            this.spawnY = this.y;
            this.spawnZ = this.z;
        }
    }

    protected Entity findPlayerToTrack() {
        Player entityplayer = this.level.getClosestPlayerTo(this, 16.0);
        if (entityplayer != null && this.method_928(entityplayer)) {
            return entityplayer;
        }
        return null;
    }

    @Override
    public boolean method_1380() {
        return false;
    }

    @Override
    public void method_1353(Entity entity) {
        System.out.println("collision");
    }

    public boolean seesThePlayer() {
        return this.entityToTrack != null;
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.put("npcName", this.npcName);
        tag.put("chatMsg", this.chatMsg);
        tag.put("texture", this.texture);
        tag.put("spawnX", this.spawnX);
        tag.put("spawnY", this.spawnY);
        tag.put("spawnZ", this.spawnZ);
        tag.put("pathToHome", this.pathToHome);
        tag.put("trackPlayer", this.trackPlayer);
        tag.put("isAttackable", this.isAttackable);
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.npcName = tag.getString("npcName");
        this.chatMsg = tag.getString("chatMsg");
        this.texture = tag.getString("texture");
        if (tag.containsKey("spawnX")) {
            this.spawnX = tag.getDouble("spawnX");
            this.spawnY = tag.getDouble("spawnY");
            this.spawnZ = tag.getDouble("spawnZ");
        }
        if (tag.containsKey("pathToHome")) {
            this.pathToHome = tag.getBoolean("pathToHome");
        }
        if (tag.containsKey("trackPlayer")) {
            this.trackPlayer = tag.getBoolean("trackPlayer");
        }
        if (tag.containsKey("isAttackable")) {
            this.isAttackable = tag.getBoolean("isAttackable");
        }
    }

    @Override
    public boolean interact(Player entityplayer) {
        if (super.interact(entityplayer)) {
            if (this.chatMsg != null && !this.chatMsg.equals((Object) "")) {
                Minecraft.minecraftInstance.overlay.addChatMessage(String.format((String) "<%s> %s", (Object[]) new Object[] { this.npcName, this.chatMsg }));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean damage(Entity target, int amount) {
        if (this.isAttackable) {
            return super.damage(target, amount);
        }
        return false;
    }

    @Override
    public boolean attackEntityFromMulti(Entity entity, int i) {
        if (this.isAttackable) {
            return super.attackEntityFromMulti(entity, i);
        }
        return false;
    }
}
