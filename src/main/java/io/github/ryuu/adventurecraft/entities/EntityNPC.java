package io.github.ryuu.adventurecraft.entities;

import io.github.ryuu.adventurecraft.mixin.MixinMinecraft;
import net.minecraft.class_61;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;

public class EntityNPC extends EntityLivingScript {
    public String npcName;

    public String chatMsg;
    public double spawnX;
    public double spawnY;
    public double spawnZ;
    public boolean pathToHome;
    public boolean trackPlayer;
    public boolean isAttackable;
    boolean initialSpot;
    int ticksTillNewPath;

    class_61 pathToPoint;

    Entity entityToTrack;

    private boolean ranOnCreate;

    public EntityNPC(Level world) {
        super(world);
        this.initialSpot = false;
        this.pathToHome = true;
        this.trackPlayer = true;
        this.isAttackable = false;
        this.ticksTillNewPath = 0;
        this.pathToPoint = null;
        this.ranOnCreate = false;
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
            runCreatedScript();
        }
        if (this.pathToHome && !isPathing() && squaredDistanceTo(this.spawnX, this.spawnY, this.spawnZ) > 4.0D)
            pathToPosition((int) this.spawnX, (int) this.spawnY, (int) this.spawnZ);
        super.tick();
        if (this.trackPlayer && this.entityToTrack == null)
            this.entityToTrack = findPlayerToTrack();
        if (this.entityToTrack != null)
            if (!this.entityToTrack.isAlive()) {
                this.entityToTrack = null;
            } else if (this.entityToTrack.distanceTo(this) > 16.0F || !method_928(this.entityToTrack)) {
                this.entityToTrack = null;
            }
        if (this.entityToTrack != null) {
            double d4 = this.entityToTrack.x - this.x;
            double d5 = this.entityToTrack.z - this.z;
            float desiredYaw = (float) (Math.atan2(d5, d4) * 180.0D / 3.1415927410125732D) - 90.0F;
            float delta = desiredYaw - this.yaw;
            while (delta < -180.0F)
                delta += 360.0F;
            while (delta > 180.0F)
                delta -= 360.0F;
            delta = Math.max(Math.min(delta, 10.0F), -10.0F);
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
        Player entityplayer = this.level.getClosestPlayerTo(this, 16.0D);
        if (entityplayer != null && method_928(entityplayer))
            return entityplayer;
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
        return (this.entityToTrack != null);
    }

    @Override
    public void writeCustomDataToTag(CompoundTag nbttagcompound) {
        super.writeCustomDataToTag(nbttagcompound);
        nbttagcompound.put("npcName", this.npcName);
        nbttagcompound.put("chatMsg", this.chatMsg);
        nbttagcompound.put("texture", this.texture);
        nbttagcompound.put("spawnX", this.spawnX);
        nbttagcompound.put("spawnY", this.spawnY);
        nbttagcompound.put("spawnZ", this.spawnZ);
        nbttagcompound.put("pathToHome", this.pathToHome);
        nbttagcompound.put("trackPlayer", this.trackPlayer);
        nbttagcompound.put("isAttackable", this.isAttackable);
    }

    @Override
    public void readCustomDataFromTag(CompoundTag nbttagcompound) {
        super.readCustomDataFromTag(nbttagcompound);
        this.npcName = nbttagcompound.getString("npcName");
        this.chatMsg = nbttagcompound.getString("chatMsg");
        this.texture = nbttagcompound.getString("texture");
        if (nbttagcompound.containsKey("spawnX")) {
            this.spawnX = nbttagcompound.getDouble("spawnX");
            this.spawnY = nbttagcompound.getDouble("spawnY");
            this.spawnZ = nbttagcompound.getDouble("spawnZ");
        }
        if (nbttagcompound.containsKey("pathToHome"))
            this.pathToHome = nbttagcompound.getBoolean("pathToHome");
        if (nbttagcompound.containsKey("trackPlayer"))
            this.trackPlayer = nbttagcompound.getBoolean("trackPlayer");
        if (nbttagcompound.containsKey("isAttackable"))
            this.isAttackable = nbttagcompound.getBoolean("isAttackable");
    }

    @Override
    public boolean interact(Player entityplayer) {
        if (super.interact(entityplayer)) {
            if (this.chatMsg != null && !this.chatMsg.equals(""))
                MixinMinecraft.minecraftInstance.overlay.addChatMessage(String.format("<%s> %s", this.npcName, this.chatMsg));
            return true;
        }
        return false;
    }

    @Override
    public boolean damage(Entity entity, int i) {
        if (this.isAttackable)
            return super.damage(entity, i);
        return false;
    }

    public boolean attackEntityFromMulti(Entity entity, int i) {
        if (this.isAttackable)
            return super.attackEntityFromMulti(entity, i);
        return false;
    }
}