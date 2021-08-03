package io.github.ryuu.adventurecraft.entities;

import io.github.ryuu.adventurecraft.Minecraft;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;

public class EntityNPC extends EntityLivingScript {
    public String npcName;

    public String chatMsg;

    boolean initialSpot;

    public double spawnX;

    public double spawnY;

    public double spawnZ;

    public boolean pathToHome;

    public boolean trackPlayer;

    public boolean isAttackable;

    int ticksTillNewPath;

    dh pathToPoint;

    sn entityToTrack;

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
        this.O = "/mob/char.png";
        this.npcName = "New NPC";
        this.chatMsg = "Hello!";
    }

    protected void b() {
    }

    public void w_() {
        if (!this.ranOnCreate) {
            this.ranOnCreate = true;
            runCreatedScript();
        }
        if (this.pathToHome && !isPathing() && g(this.spawnX, this.spawnY, this.spawnZ) > 4.0D)
            pathToPosition((int) this.spawnX, (int) this.spawnY, (int) this.spawnZ);
        super.w_();
        if (this.trackPlayer && this.entityToTrack == null)
            this.entityToTrack = findPlayerToTrack();
        if (this.entityToTrack != null)
            if (!this.entityToTrack.W()) {
                this.entityToTrack = null;
            } else if (this.entityToTrack.f((sn) this) > 16.0F || !e(this.entityToTrack)) {
                this.entityToTrack = null;
            }
        if (this.entityToTrack != null) {
            double d4 = this.entityToTrack.aM - this.aM;
            double d5 = this.entityToTrack.aO - this.aO;
            float desiredYaw = (float) (Math.atan2(d5, d4) * 180.0D / 3.1415927410125732D) - 90.0F;
            float delta = desiredYaw - this.aS;
            while (delta < -180.0F)
                delta += 360.0F;
            while (delta > 180.0F)
                delta -= 360.0F;
            delta = Math.max(Math.min(delta, 10.0F), -10.0F);
            this.aS += delta;
        }
    }

    protected void f_() {
        if (!this.initialSpot) {
            this.initialSpot = true;
            this.spawnX = this.aM;
            this.spawnY = this.aN;
            this.spawnZ = this.aO;
        }
    }

    protected sn findPlayerToTrack() {
        Player entityplayer = this.aI.a((sn) this, 16.0D);
        if (entityplayer != null && e(entityplayer))
            return entityplayer;
        return null;
    }

    public boolean i_() {
        return false;
    }

    public void h(sn entity) {
        System.out.println("collision");
    }

    public boolean seesThePlayer() {
        return (this.entityToTrack != null);
    }

    public void b(nu nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("npcName", this.npcName);
        nbttagcompound.a("chatMsg", this.chatMsg);
        nbttagcompound.a("texture", this.O);
        nbttagcompound.a("spawnX", this.spawnX);
        nbttagcompound.a("spawnY", this.spawnY);
        nbttagcompound.a("spawnZ", this.spawnZ);
        nbttagcompound.a("pathToHome", this.pathToHome);
        nbttagcompound.a("trackPlayer", this.trackPlayer);
        nbttagcompound.a("isAttackable", this.isAttackable);
    }

    public void a(nu nbttagcompound) {
        super.a(nbttagcompound);
        this.npcName = nbttagcompound.i("npcName");
        this.chatMsg = nbttagcompound.i("chatMsg");
        this.O = nbttagcompound.i("texture");
        if (nbttagcompound.b("spawnX")) {
            this.spawnX = nbttagcompound.h("spawnX");
            this.spawnY = nbttagcompound.h("spawnY");
            this.spawnZ = nbttagcompound.h("spawnZ");
        }
        if (nbttagcompound.b("pathToHome"))
            this.pathToHome = nbttagcompound.m("pathToHome");
        if (nbttagcompound.b("trackPlayer"))
            this.trackPlayer = nbttagcompound.m("trackPlayer");
        if (nbttagcompound.b("isAttackable"))
            this.isAttackable = nbttagcompound.m("isAttackable");
    }

    public boolean a(Player entityplayer) {
        if (super.a(entityplayer)) {
            if (this.chatMsg != null && !this.chatMsg.equals(""))
                Minecraft.minecraftInstance.v.a(String.format("<%s> %s", new Object[]{this.npcName, this.chatMsg}));
            return true;
        }
        return false;
    }

    public boolean a(sn entity, int i) {
        if (this.isAttackable)
            return super.a(entity, i);
        return false;
    }

    public boolean attackEntityFromMulti(sn entity, int i) {
        if (this.isAttackable)
            return super.attackEntityFromMulti(entity, i);
        return false;
    }
}