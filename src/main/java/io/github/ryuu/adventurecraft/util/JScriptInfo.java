package io.github.ryuu.adventurecraft.util;

import org.mozilla.javascript.Script;

public class JScriptInfo implements Comparable<JScriptInfo> {

    public String name;

    public Script compiledScript;

    public long totalTime;

    public long maxTime;

    public int count;

    JScriptInfo(String n, Script s) {
        this.name = n.replace(".js", "");
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
        return Long.compare(o.totalTime, this.totalTime);
    }
}
