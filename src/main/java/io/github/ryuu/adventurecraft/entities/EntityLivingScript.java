package io.github.ryuu.adventurecraft.entities;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityNpcPath;
import io.github.ryuu.adventurecraft.scripting.EntityDescriptions;
import io.github.ryuu.adventurecraft.scripting.ScopeTag;
import io.github.ryuu.adventurecraft.scripting.ScriptEntity;
import io.github.ryuu.adventurecraft.scripting.ScriptEntityDescription;
import io.github.ryuu.adventurecraft.util.CoordBlock;
import io.github.ryuu.adventurecraft.util.IEntityPather;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.Vec3f;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class EntityLivingScript extends LivingEntity implements IEntityPather {
    String initDescTo;

    String descriptionName;

    float prevWidth;

    float prevHeight;

    protected Scriptable scope;

    public String onCreated;

    public String onUpdate;

    public String onPathReached;

    public String onAttacked;

    public String onDeath;

    public String onInteraction;

    private dh path;

    private Entity pathToEntity;

    private CoordBlock pathToVec;

    public Float maxPathDistance;

    private int nextPathIn;

    private double prevDistToPoint;

    public TileEntityNpcPath triggerOnPath;

    public EntityLivingScript(Level w) {
        super(w);
        this.prevWidth = 0.6F;
        this.prevHeight = 1.8F;
        this.onCreated = "";
        this.onUpdate = "";
        this.onPathReached = "";
        this.onAttacked = "";
        this.onDeath = "";
        this.onInteraction = "";
        this.maxPathDistance = Float.valueOf(64.0F);
        this.prevDistToPoint = 999999.0D;
        this.triggerOnPath = null;
        this.scope = w.script.getNewScope();
        Object wrappedOut = Context.javaToJS(ScriptEntity.getEntityClass(this), this.scope);
        ScriptableObject.putProperty(this.scope, "entity", wrappedOut);
    }

    public void setEntityDescription(String descName) {
        setEntityDescription(descName, true);
    }

    private void setEntityDescription(String descName, boolean setHealth) {
        this.descriptionName = descName;
        ScriptEntityDescription description = EntityDescriptions.getDescription(descName);
        if (description != null) {
            if (setHealth) {
                this.Y = description.health;
                this.maxHealth = description.health;
                this.onCreated = description.onCreated;
                this.onUpdate = description.onUpdate;
                this.onPathReached = description.onPathReached;
                this.onAttacked = description.onAttacked;
                this.onDeath = description.onDeath;
                this.onInteraction = description.onInteraction;
            }
            this.bg = description.width;
            this.bh = description.height;
            this.O = description.texture;
            this.aB = description.moveSpeed;
            runCreatedScript();
        }
    }

    protected void f_() {
    }

    public void w_() {
        if (this.initDescTo != null) {
            if (!this.initDescTo.equals(""))
                setEntityDescription(this.initDescTo, false);
            this.initDescTo = null;
        }
        this.prevWidth = this.bg;
        this.prevHeight = this.bh;
        continuePathing();
        runUpdateScript();
        super.w_();
    }

    public boolean a(Entity entity, int i) {
        Object wrappedOut = Context.javaToJS(ScriptEntity.getEntityClass(entity), this.scope);
        ScriptableObject.putProperty(this.scope, "attackingEntity", wrappedOut);
        wrappedOut = Context.javaToJS(new Integer(i), this.scope);
        ScriptableObject.putProperty(this.scope, "attackingDamage", wrappedOut);
        if (runOnAttackedScript())
            return super.a(entity, i);
        return false;
    }

    public boolean attackEntityFromMulti(Entity entity, int i) {
        Object wrappedOut = Context.javaToJS(ScriptEntity.getEntityClass(entity), this.scope);
        ScriptableObject.putProperty(this.scope, "attackingEntity", wrappedOut);
        wrappedOut = Context.javaToJS(new Integer(i), this.scope);
        ScriptableObject.putProperty(this.scope, "attackingDamage", wrappedOut);
        if (runOnAttackedScript())
            return super.attackEntityFromMulti(entity, i);
        return false;
    }

    public void K() {
        super.K();
        runDeathScript();
    }

    public boolean a(Player entityplayer) {
        return runOnInteractionScript();
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        if (this.descriptionName != null && !this.descriptionName.equals(""))
            nbttagcompound.put("descriptionName", this.descriptionName);
        if (!this.onCreated.equals(""))
            nbttagcompound.put("onCreated", this.onCreated);
        if (!this.onUpdate.equals(""))
            nbttagcompound.put("onUpdate", this.onUpdate);
        if (!this.onPathReached.equals(""))
            nbttagcompound.put("onPathReached", this.onPathReached);
        if (!this.onAttacked.equals(""))
            nbttagcompound.put("onAttacked", this.onAttacked);
        if (!this.onDeath.equals(""))
            nbttagcompound.put("onDeath", this.onDeath);
        if (!this.onInteraction.equals(""))
            nbttagcompound.put("onInteraction", this.onInteraction);
        if (nbttagcompound.containsKey("scope"))
            ScopeTag.loadScopeFromTag(this.scope, nbttagcompound.k("scope"));
    }

    public void a(CompoundTag nbttagcompound) {
        super.a(nbttagcompound);
        this.initDescTo = nbttagcompound.getString("descriptionName");
        this.onCreated = nbttagcompound.getString("onCreated");
        this.onUpdate = nbttagcompound.getString("onUpdate");
        this.onPathReached = nbttagcompound.getString("onPathReached");
        this.onAttacked = nbttagcompound.getString("onAttacked");
        this.onDeath = nbttagcompound.getString("onDeath");
        this.onInteraction = nbttagcompound.getString("onInteraction");
        nbttagcompound.put("scope", ScopeTag.getTagFromScope(this.scope));
    }

    public void runCreatedScript() {
        if (!this.onCreated.equals(""))
            this.aI.scriptHandler.runScript(this.onCreated, this.scope);
    }

    private void runUpdateScript() {
        if (!this.onUpdate.equals(""))
            this.aI.scriptHandler.runScript(this.onUpdate, this.scope);
    }

    private void runPathCompletedScript() {
        if (!this.onPathReached.equals(""))
            this.aI.scriptHandler.runScript(this.onPathReached, this.scope);
    }

    private boolean runOnAttackedScript() {
        if (!this.onAttacked.equals("")) {
            Object obj = this.aI.scriptHandler.runScript(this.onAttacked, this.scope);
            if (obj == null || !(obj instanceof Boolean))
                return true;
            return ((Boolean) obj).booleanValue();
        }
        return true;
    }

    private void runDeathScript() {
        if (!this.onDeath.equals(""))
            this.aI.scriptHandler.runScript(this.onDeath, this.scope);
    }

    private boolean runOnInteractionScript() {
        if (!this.onInteraction.equals("")) {
            Object obj = this.aI.scriptHandler.runScript(this.onInteraction, this.scope);
            if (obj == null || !(obj instanceof Boolean))
                return true;
            return ((Boolean) obj).booleanValue();
        }
        return true;
    }

    public boolean isPathing() {
        return (this.pathToEntity != null || this.pathToVec != null || this.path != null);
    }

    public void pathToEntity(Entity p) {
        this.pathToEntity = p;
        this.pathToVec = null;
        this.path = this.aI.a(this, this.pathToEntity, this.maxPathDistance.floatValue());
        this.nextPathIn = this.aI.r.nextInt(40) + 60;
        this.prevDistToPoint = 999999.0D;
        this.triggerOnPath = null;
    }

    public void pathToPosition(int x, int y, int z) {
        this.pathToEntity = null;
        this.pathToVec = new CoordBlock(x, y, z);
        this.path = this.aI.a(this, x, y, z, this.maxPathDistance.floatValue());
        this.nextPathIn = this.aI.r.nextInt(40) + 60;
        this.prevDistToPoint = 999999.0D;
        this.triggerOnPath = null;
    }

    public void clearPathing() {
        this.pathToEntity = null;
        this.pathToVec = null;
        this.path = null;
        this.triggerOnPath = null;
    }

    private void continuePathing() {
        if (isPathing()) {
            if (this.path == null || (--this.nextPathIn <= 0 && this.pathToEntity != null && this.path.needNewPath(this.pathToEntity))) {
                if (this.pathToEntity != null) {
                    this.path = this.aI.a(this, this.pathToEntity, this.maxPathDistance.floatValue());
                } else if (this.pathToVec != null) {
                    this.path = this.aI.a(this, this.pathToVec.x, this.pathToVec.y, this.pathToVec.z, this.maxPathDistance.floatValue());
                }
                this.nextPathIn = this.aI.r.nextInt(40) + 10;
                this.prevDistToPoint = 999999.0D;
            }
            if (this.path == null)
                return;
            Vec3f vec3d = this.path.a(this);
            this.ax = 0.0F;
            this.az = false;
            double dist = vec3d.d(this.aM, vec3d.b, this.aO);
            if (dist >= this.prevDistToPoint && this.nextPathIn > 5)
                this.nextPathIn = this.aI.r.nextInt(5) + 1;
            this.prevDistToPoint = dist;
            for (double d = this.bg * 1.1D; vec3d != null && vec3d.d(this.aM, vec3d.b, this.aO) < d * d; ) {
                this.path.a();
                if (this.path.b()) {
                    vec3d = null;
                    this.path = null;
                    runPathCompletedScript();
                    if (this.triggerOnPath != null)
                        this.triggerOnPath.pathFinished();
                    return;
                }
                vec3d = this.path.a(this);
                this.prevDistToPoint = 999999.0D;
            }
            if (vec3d != null) {
                double dX = vec3d.a - this.aM;
                double dZ = vec3d.c - this.aO;
                double dY = vec3d.b - in.b(this.aW.b + 0.5D);
                float yawDir = (float) (Math.atan2(dZ, dX) * 180.0D / 3.1415927410125732D) - 90.0F;
                float yawDelta = yawDir - this.aS;
                this.ax = this.aB;
                for (; yawDelta < -180.0F; yawDelta += 360.0F) ;
                for (; yawDelta >= 180.0F; yawDelta -= 360.0F) ;
                if (yawDelta > 30.0F)
                    yawDelta = 30.0F;
                if (yawDelta < -30.0F)
                    yawDelta = -30.0F;
                this.aS += yawDelta;
                if (dY > 0.0D)
                    this.az = true;
            }
        }
    }

    public dh getCurrentPath() {
        return this.path;
    }
}
