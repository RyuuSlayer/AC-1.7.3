package io.github.ryuu.adventurecraft.entities.tile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.script.ScopeTag;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.AbstractTag;
import net.minecraft.util.io.CompoundTag;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class TileEntityScript extends MixinTileEntity {

    public boolean inited = false;

    public boolean checkTrigger = true;

    public boolean isActivated = false;

    public String onTriggerScriptFile = "";

    public String onDetriggerScriptFile = "";

    public String onUpdateScriptFile = "";

    boolean loaded = false;

    public Scriptable scope;

    public TileEntityScript() {
        this.scope = Minecraft.minecraftInstance.level.script.getNewScope();
    }

    @Override
    public void tick() {
        if (!this.inited) {
            this.inited = true;
            Object wrappedOut = Context.javaToJS((Object) new Integer(this.x), (Scriptable) this.scope);
            ScriptableObject.putProperty((Scriptable) this.scope, (String) "xCoord", (Object) wrappedOut);
            wrappedOut = Context.javaToJS((Object) new Integer(this.y), (Scriptable) this.scope);
            ScriptableObject.putProperty((Scriptable) this.scope, (String) "yCoord", (Object) wrappedOut);
            wrappedOut = Context.javaToJS((Object) new Integer(this.z), (Scriptable) this.scope);
            ScriptableObject.putProperty((Scriptable) this.scope, (String) "zCoord", (Object) wrappedOut);
        }
        if (this.checkTrigger) {
            this.isActivated = this.level.triggerManager.isActivated(this.x, this.y, this.z);
            this.checkTrigger = false;
        }
        if (this.isActivated && !this.onUpdateScriptFile.equals((Object) "")) {
            this.level.scriptHandler.runScript(this.onUpdateScriptFile, this.scope);
        }
    }

    @Override
    public void readIdentifyingData(MixinCompoundTag tag) {
        super.readIdentifyingData(tag);
        this.onTriggerScriptFile = tag.getString("onTriggerScriptFile");
        this.onDetriggerScriptFile = tag.getString("onDetriggerScriptFile");
        this.onUpdateScriptFile = tag.getString("onUpdateScriptFile");
        this.isActivated = tag.getBoolean("isActivated");
        if (tag.containsKey("scope")) {
            ScopeTag.loadScopeFromTag(this.scope, tag.getCompoundTag("scope"));
        }
    }

    @Override
    public void writeIdentifyingData(MixinCompoundTag tag) {
        super.writeIdentifyingData(tag);
        if (!this.onTriggerScriptFile.isEmpty()) {
            tag.put("onTriggerScriptFile", this.onTriggerScriptFile);
        }
        if (!this.onDetriggerScriptFile.isEmpty()) {
            tag.put("onDetriggerScriptFile", this.onDetriggerScriptFile);
        }
        if (!this.onUpdateScriptFile.isEmpty()) {
            tag.put("onUpdateScriptFile", this.onUpdateScriptFile);
        }
        tag.put("isActivated", this.isActivated);
        tag.put("scope", (AbstractTag) ScopeTag.getTagFromScope(this.scope));
    }
}
