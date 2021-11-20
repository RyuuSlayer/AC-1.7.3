package io.github.ryuu.adventurecraft.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.ProfileContext;

public class Profiler {

    static Stack<ProfileContext> profileStack;

    static HashMap<String, Long> times;

    static long startTime;

    public static void startTiming(String context) {
        if (profileStack != null) {
            profileStack.push((Object) new ProfileContext(context));
        }
    }

    public static void stopTiming() {
        if (profileStack != null) {
            ProfileContext c = (ProfileContext) profileStack.pop();
            long t = c.getTime();
            if (times.containsKey((Object) c.contextName)) {
                times.put((Object) c.contextName, (Object) ((Long) times.get((Object) c.contextName) + t));
            } else {
                times.put((Object) c.contextName, (Object) t);
            }
        }
    }

    public static void startFrame() {
        profileStack = new Stack();
        times = new HashMap();
        startTime = System.nanoTime();
    }

    public static void stopFrame() {
        if (profileStack != null) {
            long time = System.nanoTime() - startTime;
            if (time > 100000000L) {
                for (Map.Entry e : times.entrySet()) {
                    System.out.printf("%s\t\t%d\n", new Object[] { e.getKey(), e.getValue() });
                }
            }
            profileStack = null;
            times = null;
        }
    }
}
