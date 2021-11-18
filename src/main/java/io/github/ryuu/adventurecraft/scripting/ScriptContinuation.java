package io.github.ryuu.adventurecraft.scripting;

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