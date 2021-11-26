package io.github.ryuu.adventurecraft.entities.tile;

import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import io.github.ryuu.adventurecraft.scripting.ScopeTag;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.AbstractTag;
import net.minecraft.util.io.CompoundTag;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class TileEntityScript extends TileEntity {

    public boolean inited = false;

    public boolean checkTrigger = true;

    public boolean isActivated = false;

    public String onTriggerScriptFile = "";

    public String onDetriggerScriptFile = "";

    public String onUpdateScriptFile = "";
    public Scriptable scope;
    boolean loaded = false;

    public TileEntityScript() {
        this.scope = AccessMinecraft.getInstance().level.script.getNewScope();
    }

    @Override
    public void tick() {
        if (!this.inited) {
            this.inited = true;
            Object wrappedOut = Context.javaToJS(this.x, this.scope);
            ScriptableObject.putProperty(this.scope, "xCoord", wrappedOut);
            wrappedOut = Context.javaToJS(this.y, this.scope);
            ScriptableObject.putProperty(this.scope, "yCoord", wrappedOut);
            wrappedOut = Context.javaToJS(this.z, this.scope);
            ScriptableObject.putProperty(this.scope, "zCoord", wrappedOut);
        }
        if (this.checkTrigger) {
            this.isActivated = this.level.triggerManager.isActivated(this.x, this.y, this.z);
            this.checkTrigger = false;
        }
        if (this.isActivated && !this.onUpdateScriptFile.equals("")) {
            this.level.scriptHandler.runScript(this.onUpdateScriptFile, this.scope);
        }
    }

    @Override
    public void readIdentifyingData(CompoundTag tag) {
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
    public void writeIdentifyingData(CompoundTag tag) {
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
