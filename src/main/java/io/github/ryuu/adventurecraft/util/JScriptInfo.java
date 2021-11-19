package io.github.ryuu.adventurecraft.util;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Comparable
 *  java.lang.Object
 *  java.lang.String
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.src.JScriptInfo
 *  org.mozilla.javascript.Script
 */
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.mozilla.javascript.Script;

public class JScriptInfo implements Comparable<net.minecraft.src.JScriptInfo> {

    public String name;

    public Script compiledScript;

    public long totalTime;

    public long maxTime;

    public int count;

    JScriptInfo(String n, Script s) {
        this.name = n.replace((CharSequence) ".js", (CharSequence) "");
        this.compiledScript = s;
    }

    public void addStat(long time) {
        this.totalTime += time;
        if (time > this.maxTime) {
            this.maxTime = time;
        }
        ++this.count;
    }

    public int compareTo(JScriptInfo o) {
        if (this.totalTime < o.totalTime) {
            return 1;
        }
        if (this.totalTime == o.totalTime) {
            return 0;
        }
        return -1;
    }
}
