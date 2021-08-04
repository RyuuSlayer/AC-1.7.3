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

    public void n_() {
        if (!this.inited) {
            this.inited = true;
            Object wrappedOut = Context.javaToJS(new Integer(this.e), this.scope);
            ScriptableObject.putProperty(this.scope, "xCoord", wrappedOut);
            wrappedOut = Context.javaToJS(new Integer(this.f), this.scope);
            ScriptableObject.putProperty(this.scope, "yCoord", wrappedOut);
            wrappedOut = Context.javaToJS(new Integer(this.g), this.scope);
            ScriptableObject.putProperty(this.scope, "zCoord", wrappedOut);
        }
        if (this.checkTrigger) {
            this.isActivated = this.d.triggerManager.isActivated(this.e, this.f, this.g);
            this.checkTrigger = false;
        }
        if (this.isActivated && !this.onUpdateScriptFile.equals(""))
            this.d.scriptHandler.runScript(this.onUpdateScriptFile, this.scope);
    }

    public void a(CompoundTag nbttagcompound) {
        super.a(nbttagcompound);
        this.onTriggerScriptFile = nbttagcompound.i("onTriggerScriptFile");
        this.onDetriggerScriptFile = nbttagcompound.i("onDetriggerScriptFile");
        this.onUpdateScriptFile = nbttagcompound.i("onUpdateScriptFile");
        this.isActivated = nbttagcompound.m("isActivated");
        if (nbttagcompound.b("scope"))
            ScopeTag.loadScopeFromTag(this.scope, nbttagcompound.k("scope"));
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        if (!this.onTriggerScriptFile.isEmpty())
            nbttagcompound.a("onTriggerScriptFile", this.onTriggerScriptFile);
        if (!this.onDetriggerScriptFile.isEmpty())
            nbttagcompound.a("onDetriggerScriptFile", this.onDetriggerScriptFile);
        if (!this.onUpdateScriptFile.isEmpty())
            nbttagcompound.a("onUpdateScriptFile", this.onUpdateScriptFile);
        nbttagcompound.a("isActivated", this.isActivated);
        nbttagcompound.a("scope", ScopeTag.getTagFromScope(this.scope));
    }

    public boolean inited = false;

    public boolean checkTrigger = true;

    public boolean isActivated = false;

    boolean loaded = false;
}
