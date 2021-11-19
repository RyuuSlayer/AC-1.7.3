/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  org.mozilla.javascript.Scriptable
 */
package io.github.ryuu.adventurecraft.scripting;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.mozilla.javascript.Scriptable;

class ScriptContinuation {

    Object contituation;

    long wakeUp;

    Scriptable scope;

    ScriptContinuation(Object c, long wakeUpOn, Scriptable s) {
        this.contituation = c;
        this.wakeUp = wakeUpOn;
        this.scope = s;
    }
}
