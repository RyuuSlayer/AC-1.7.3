package io.github.ryuu.adventurecraft.entities;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import io.github.ryuu.adventurecraft.scripting.ScriptEntity;
import io.github.ryuu.adventurecraft.scripting.EntityDescriptions;
import io.github.ryuu.adventurecraft.scripting.ScopeTag;
import io.github.ryuu.adventurecraft.util.CoordBlock;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityNpcPath;
import io.github.ryuu.adventurecraft.scripting.ScriptEntityDescription;
import io.github.ryuu.adventurecraft.util.IEntityPather;

public class EntityLivingScript extends LivingEntity implements IEntityPather {

    String initDescTo;

    String descriptionName;

    float prevWidth = 0.6f;

    float prevHeight = 1.8f;

    protected Scriptable scope;

    public String onCreated = "";

    public String onUpdate = "";

    public String onPathReached = "";

    public String onAttacked = "";

    public String onDeath = "";

    public String onInteraction = "";

    private class_61 path;

    private Entity pathToEntity;

    private CoordBlock pathToVec;

    public Float maxPathDistance = Float.valueOf((float) 64.0f);

    private int nextPathIn;

    private double prevDistToPoint = 999999.0;

    TileEntityNpcPath triggerOnPath = null;

    public EntityLivingScript(Level w) {
        super(w);
        this.scope = w.script.getNewScope();
        Object wrappedOut = Context.javaToJS((Object) ScriptEntity.getEntityClass(this), (Scriptable) this.scope);
        ScriptableObject.putProperty((Scriptable) this.scope, (String) "entity", (Object) wrappedOut);
    }

    public void setEntityDescription(String descName) {
        this.setEntityDescription(descName, true);
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
            this.runCreatedScript();
        }
    }

    @Override
    protected void tickHandSwing() {
    }

    @Override
    public void tick() {
        if (this.initDescTo != null) {
            if (!this.initDescTo.equals((Object) "")) {
                this.setEntityDescription(this.initDescTo, false);
            }
            this.initDescTo = null;
        }
        this.prevWidth = this.width;
        this.prevHeight = this.height;
        this.continuePathing();
        this.runUpdateScript();
        super.tick();
    }

    @Override
    public boolean damage(Entity target, int amount) {
        Object wrappedOut = Context.javaToJS((Object) ScriptEntity.getEntityClass(target), (Scriptable) this.scope);
        ScriptableObject.putProperty((Scriptable) this.scope, (String) "attackingEntity", (Object) wrappedOut);
        wrappedOut = Context.javaToJS((Object) new Integer(amount), (Scriptable) this.scope);
        ScriptableObject.putProperty((Scriptable) this.scope, (String) "attackingDamage", (Object) wrappedOut);
        if (this.runOnAttackedScript()) {
            return super.damage(target, amount);
        }
        return false;
    }

    @Override
    public boolean attackEntityFromMulti(Entity entity, int i) {
        Object wrappedOut = Context.javaToJS((Object) ScriptEntity.getEntityClass(entity), (Scriptable) this.scope);
        ScriptableObject.putProperty((Scriptable) this.scope, (String) "attackingEntity", (Object) wrappedOut);
        wrappedOut = Context.javaToJS((Object) new Integer(i), (Scriptable) this.scope);
        ScriptableObject.putProperty((Scriptable) this.scope, (String) "attackingDamage", (Object) wrappedOut);
        if (this.runOnAttackedScript()) {
            return super.attackEntityFromMulti(entity, i);
        }
        return false;
    }

    @Override
    public void remove() {
        super.remove();
        this.runDeathScript();
    }

    @Override
    public boolean interact(Player entityplayer) {
        return this.runOnInteractionScript();
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        if (this.descriptionName != null && !this.descriptionName.equals((Object) "")) {
            tag.put("descriptionName", this.descriptionName);
        }
        if (!this.onCreated.equals((Object) "")) {
            tag.put("onCreated", this.onCreated);
        }
        if (!this.onUpdate.equals((Object) "")) {
            tag.put("onUpdate", this.onUpdate);
        }
        if (!this.onPathReached.equals((Object) "")) {
            tag.put("onPathReached", this.onPathReached);
        }
        if (!this.onAttacked.equals((Object) "")) {
            tag.put("onAttacked", this.onAttacked);
        }
        if (!this.onDeath.equals((Object) "")) {
            tag.put("onDeath", this.onDeath);
        }
        if (!this.onInteraction.equals((Object) "")) {
            tag.put("onInteraction", this.onInteraction);
        }
        if (tag.containsKey("scope")) {
            ScopeTag.loadScopeFromTag(this.scope, tag.getCompoundTag("scope"));
        }
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.initDescTo = tag.getString("descriptionName");
        this.onCreated = tag.getString("onCreated");
        this.onUpdate = tag.getString("onUpdate");
        this.onPathReached = tag.getString("onPathReached");
        this.onAttacked = tag.getString("onAttacked");
        this.onDeath = tag.getString("onDeath");
        this.onInteraction = tag.getString("onInteraction");
        tag.put("scope", (AbstractTag) ScopeTag.getTagFromScope(this.scope));
    }

    public void runCreatedScript() {
        if (!this.onCreated.equals((Object) "")) {
            this.level.scriptHandler.runScript(this.onCreated, this.scope);
        }
    }

    private void runUpdateScript() {
        if (!this.onUpdate.equals((Object) "")) {
            this.level.scriptHandler.runScript(this.onUpdate, this.scope);
        }
    }

    private void runPathCompletedScript() {
        if (!this.onPathReached.equals((Object) "")) {
            this.level.scriptHandler.runScript(this.onPathReached, this.scope);
        }
    }

    private boolean runOnAttackedScript() {
        if (!this.onAttacked.equals((Object) "")) {
            Object obj = this.level.scriptHandler.runScript(this.onAttacked, this.scope);
            if (obj == null || !(obj instanceof Boolean)) {
                return true;
            }
            return (Boolean) obj;
        }
        return true;
    }

    private void runDeathScript() {
        if (!this.onDeath.equals((Object) "")) {
            this.level.scriptHandler.runScript(this.onDeath, this.scope);
        }
    }

    private boolean runOnInteractionScript() {
        if (!this.onInteraction.equals((Object) "")) {
            Object obj = this.level.scriptHandler.runScript(this.onInteraction, this.scope);
            if (obj == null || !(obj instanceof Boolean)) {
                return true;
            }
            return (Boolean) obj;
        }
        return true;
    }

    public boolean isPathing() {
        return this.pathToEntity != null || this.pathToVec != null || this.path != null;
    }

    public void pathToEntity(Entity p) {
        this.pathToEntity = p;
        this.pathToVec = null;
        this.path = this.level.method_192(this, this.pathToEntity, this.maxPathDistance.floatValue());
        this.nextPathIn = this.level.rand.nextInt(40) + 60;
        this.prevDistToPoint = 999999.0;
        this.triggerOnPath = null;
    }

    public void pathToPosition(int x, int y, int z) {
        this.pathToEntity = null;
        this.pathToVec = new CoordBlock(x, y, z);
        this.path = this.level.method_189(this, x, y, z, this.maxPathDistance.floatValue());
        this.nextPathIn = this.level.rand.nextInt(40) + 60;
        this.prevDistToPoint = 999999.0;
        this.triggerOnPath = null;
    }

    public void clearPathing() {
        this.pathToEntity = null;
        this.pathToVec = null;
        this.path = null;
        this.triggerOnPath = null;
    }

    private void continuePathing() {
        if (this.isPathing()) {
            if (this.path == null || --this.nextPathIn <= 0 && this.pathToEntity != null && this.path.needNewPath(this.pathToEntity)) {
                if (this.pathToEntity != null) {
                    this.path = this.level.method_192(this, this.pathToEntity, this.maxPathDistance.floatValue());
                } else if (this.pathToVec != null) {
                    this.path = this.level.method_189(this, this.pathToVec.x, this.pathToVec.y, this.pathToVec.z, this.maxPathDistance.floatValue());
                }
                this.nextPathIn = this.level.rand.nextInt(40) + 10;
                this.prevDistToPoint = 999999.0;
            }
            if (this.path == null) {
                return;
            }
            Vec3f vec3d = this.path.method_2041(this);
            this.parallelMovement = 0.0f;
            this.jumping = false;
            double dist = vec3d.method_1303(this.x, vec3d.y, this.z);
            if (dist >= this.prevDistToPoint && this.nextPathIn > 5) {
                this.nextPathIn = this.level.rand.nextInt(5) + 1;
            }
            this.prevDistToPoint = dist;
            double d = (double) this.width * 1.1;
            while (vec3d != null && vec3d.method_1303(this.x, vec3d.y, this.z) < d * d) {
                this.path.method_2040();
                if (this.path.method_2042()) {
                    vec3d = null;
                    this.path = null;
                    this.runPathCompletedScript();
                    if (this.triggerOnPath != null) {
                        this.triggerOnPath.pathFinished();
                    }
                    return;
                }
                vec3d = this.path.method_2041(this);
                this.prevDistToPoint = 999999.0;
            }
            if (vec3d != null) {
                float yawDelta;
                double dX = vec3d.x - this.x;
                double dZ = vec3d.z - this.z;
                double dY = vec3d.y - (double) MathsHelper.floor(this.boundingBox.minY + 0.5);
                float yawDir = (float) (Math.atan2((double) dZ, (double) dX) * 180.0 / 3.1415927410125732) - 90.0f;
                this.parallelMovement = this.movementSpeed;
                for (yawDelta = yawDir - this.yaw; yawDelta < -180.0f; yawDelta += 360.0f) {
                }
                while (yawDelta >= 180.0f) {
                    yawDelta -= 360.0f;
                }
                if (yawDelta > 30.0f) {
                    yawDelta = 30.0f;
                }
                if (yawDelta < -30.0f) {
                    yawDelta = -30.0f;
                }
                this.yaw += yawDelta;
                if (dY > 0.0) {
                    this.jumping = true;
                }
            }
        }
    }

    @Override
    public class_61 getCurrentPath() {
        return this.path;
    }
}
