package io.github.ryuu.adventurecraft.entities;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityNpcPath;
import io.github.ryuu.adventurecraft.scripting.EntityDescriptions;
import io.github.ryuu.adventurecraft.scripting.ScopeTag;
import io.github.ryuu.adventurecraft.scripting.ScriptEntity;
import io.github.ryuu.adventurecraft.scripting.ScriptEntityDescription;
import io.github.ryuu.adventurecraft.util.CoordBlock;
import io.github.ryuu.adventurecraft.util.IEntityPather;
import net.minecraft.class_61;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.MathsHelper;
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

    private class_61 path;

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
                this.health = description.health;
                this.maxHealth = description.health;
                this.onCreated = description.onCreated;
                this.onUpdate = description.onUpdate;
                this.onPathReached = description.onPathReached;
                this.onAttacked = description.onAttacked;
                this.onDeath = description.onDeath;
                this.onInteraction = description.onInteraction;
            }
            this.width = description.width;
            this.height = description.height;
            this.texture = description.texture;
            this.movementSpeed = description.moveSpeed;
            runCreatedScript();
        }
    }

    @Override
    protected void tickHandSwing() {
    }

    @Override
    public void tick() {
        if (this.initDescTo != null) {
            if (!this.initDescTo.equals(""))
                setEntityDescription(this.initDescTo, false);
            this.initDescTo = null;
        }
        this.prevWidth = this.width;
        this.prevHeight = this.height;
        continuePathing();
        runUpdateScript();
        super.tick();
    }

    @Override
    public boolean damage(Entity entity, int i) {
        Object wrappedOut = Context.javaToJS(ScriptEntity.getEntityClass(entity), this.scope);
        ScriptableObject.putProperty(this.scope, "attackingEntity", wrappedOut);
        wrappedOut = Context.javaToJS(new Integer(i), this.scope);
        ScriptableObject.putProperty(this.scope, "attackingDamage", wrappedOut);
        if (runOnAttackedScript())
            return super.damage(entity, i);
        return false;
    }

    @Override // TODO: put in mixin
    public boolean attackEntityFromMulti(Entity entity, int i) {
        Object wrappedOut = Context.javaToJS(ScriptEntity.getEntityClass(entity), this.scope);
        ScriptableObject.putProperty(this.scope, "attackingEntity", wrappedOut);
        wrappedOut = Context.javaToJS(new Integer(i), this.scope);
        ScriptableObject.putProperty(this.scope, "attackingDamage", wrappedOut);
        if (runOnAttackedScript())
            return super.attackEntityFromMulti(entity, i);
        return false;
    }

    @Override
    public void remove() {
        super.remove();
        runDeathScript();
    }

    @Override
    public boolean interact(Player entityplayer) {
        return runOnInteractionScript();
    }

    @Override
    public void writeCustomDataToTag(CompoundTag nbttagcompound) {
        super.writeCustomDataToTag(nbttagcompound);
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

    @Override
    public void readCustomDataFromTag(CompoundTag nbttagcompound) {
        super.readCustomDataFromTag(nbttagcompound);
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
            this.level.scriptHandler.runScript(this.onCreated, this.scope);
    }

    private void runUpdateScript() {
        if (!this.onUpdate.equals(""))
            this.level.scriptHandler.runScript(this.onUpdate, this.scope);
    }

    private void runPathCompletedScript() {
        if (!this.onPathReached.equals(""))
            this.level.scriptHandler.runScript(this.onPathReached, this.scope);
    }

    private boolean runOnAttackedScript() {
        if (!this.onAttacked.equals("")) {
            Object obj = this.level.scriptHandler.runScript(this.onAttacked, this.scope);
            if (obj == null || !(obj instanceof Boolean))
                return true;
            return ((Boolean) obj).booleanValue();
        }
        return true;
    }

    private void runDeathScript() {
        if (!this.onDeath.equals(""))
            this.level.scriptHandler.runScript(this.onDeath, this.scope);
    }

    private boolean runOnInteractionScript() {
        if (!this.onInteraction.equals("")) {
            Object obj = this.level.scriptHandler.runScript(this.onInteraction, this.scope);
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
        this.path = this.level.method_192(this, this.pathToEntity, this.maxPathDistance.floatValue());
        this.nextPathIn = this.level.rand.nextInt(40) + 60;
        this.prevDistToPoint = 999999.0D;
        this.triggerOnPath = null;
    }

    public void pathToPosition(int x, int y, int z) {
        this.pathToEntity = null;
        this.pathToVec = new CoordBlock(x, y, z);
        this.path = this.level.method_189(this, x, y, z, this.maxPathDistance.floatValue());
        this.nextPathIn = this.level.rand.nextInt(40) + 60;
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
                    this.path = this.level.method_192(this, this.pathToEntity, this.maxPathDistance.floatValue());
                } else if (this.pathToVec != null) {
                    this.path = this.level.method_189(this, this.pathToVec.x, this.pathToVec.y, this.pathToVec.z, this.maxPathDistance.floatValue());
                }
                this.nextPathIn = this.level.rand.nextInt(40) + 10;
                this.prevDistToPoint = 999999.0D;
            }
            if (this.path == null)
                return;
            Vec3f vec3d = this.path.method_2041(this);
            this.parallelMovement = 0.0F;
            this.jumping = false;
            double dist = vec3d.method_1303(this.x, vec3d.y, this.z);
            if (dist >= this.prevDistToPoint && this.nextPathIn > 5)
                this.nextPathIn = this.level.rand.nextInt(5) + 1;
            this.prevDistToPoint = dist;
            for (double d = this.width * 1.1D; vec3d != null && vec3d.method_1303(this.x, vec3d.y, this.z) < d * d; ) {
                this.path.method_2040();
                if (this.path.method_2042()) {
                    vec3d = null;
                    this.path = null;
                    runPathCompletedScript();
                    if (this.triggerOnPath != null)
                        this.triggerOnPath.pathFinished();
                    return;
                }
                vec3d = this.path.method_2041(this);
                this.prevDistToPoint = 999999.0D;
            }
            if (vec3d != null) {
                double dX = vec3d.x - this.x;
                double dZ = vec3d.z - this.z;
                double dY = vec3d.y - MathsHelper.floor(this.boundingBox.minY + 0.5D);
                float yawDir = (float) (Math.atan2(dZ, dX) * 180.0D / 3.1415927410125732D) - 90.0F;
                float yawDelta = yawDir - this.yaw;
                this.parallelMovement = this.movementSpeed;
                for (; yawDelta < -180.0F; yawDelta += 360.0F) ;
                for (; yawDelta >= 180.0F; yawDelta -= 360.0F) ;
                if (yawDelta > 30.0F)
                    yawDelta = 30.0F;
                if (yawDelta < -30.0F)
                    yawDelta = -30.0F;
                this.yaw += yawDelta;
                if (dY > 0.0D)
                    this.jumping = true;
            }
        }
    }

    @Override
    public class_61 getCurrentPath() {
        return this.path;
    }
}
