package io.github.ryuu.adventurecraft.util;

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
