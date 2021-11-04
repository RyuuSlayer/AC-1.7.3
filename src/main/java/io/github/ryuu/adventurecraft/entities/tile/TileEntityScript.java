package io.github.ryuu.adventurecraft.entities.tile;

import io.github.ryuu.adventurecraft.scripting.ScopeTag;
import net.minecraft.client.Minecraft;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class TileEntityScript extends TileEntity {
    public String onTriggerScriptFile = "";

    public String onDetriggerScriptFile = "";

    public String onUpdateScriptFile = "";

    public Scriptable scope = Minecraft.minecraftInstance.f.script.getNewScope();

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
        if (this.isActivated && !this.onUpdateScriptFile.equals(""))
            this.level.scriptHandler.runScript(this.onUpdateScriptFile, this.scope);
    }

    @Override
    public void readIdentifyingData(CompoundTag nbttagcompound) {
        super.readIdentifyingData(nbttagcompound);
        this.onTriggerScriptFile = nbttagcompound.getString("onTriggerScriptFile");
        this.onDetriggerScriptFile = nbttagcompound.getString("onDetriggerScriptFile");
        this.onUpdateScriptFile = nbttagcompound.getString("onUpdateScriptFile");
        this.isActivated = nbttagcompound.getBoolean("isActivated");
        if (nbttagcompound.containsKey("scope"))
            ScopeTag.loadScopeFromTag(this.scope, nbttagcompound.getCompoundTag("scope"));
    }

    @Override
    public void writeIdentifyingData(CompoundTag nbttagcompound) {
        super.writeIdentifyingData(nbttagcompound);
        if (!this.onTriggerScriptFile.isEmpty())
            nbttagcompound.put("onTriggerScriptFile", this.onTriggerScriptFile);
        if (!this.onDetriggerScriptFile.isEmpty())
            nbttagcompound.put("onDetriggerScriptFile", this.onDetriggerScriptFile);
        if (!this.onUpdateScriptFile.isEmpty())
            nbttagcompound.put("onUpdateScriptFile", this.onUpdateScriptFile);
        nbttagcompound.put("isActivated", this.isActivated);
        nbttagcompound.put("scope", ScopeTag.getTagFromScope(this.scope));
    }

    public boolean inited = false;

    public boolean checkTrigger = true;

    public boolean isActivated = false;

    public boolean loaded = false;
}
