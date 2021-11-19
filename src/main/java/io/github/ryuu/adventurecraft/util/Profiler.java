package io.github.ryuu.adventurecraft.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Profiler {

    static Stack<ProfileContext> profileStack;

    static HashMap<String, Long> times;

    static long startTime;

    public static void startTiming(String context) {
        if (profileStack != null) {
            profileStack.push(new ProfileContext(context));
        }
    }

    public static void stopTiming() {
        if (profileStack != null) {
            ProfileContext c = profileStack.pop();
            long t = c.getTime();
            if (times.containsKey(c.contextName)) {
                times.put((Object) c.contextName, (Object) (times.get(c.contextName) + t));
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
                    System.out.printf("%s\t\t%d\n", e.getKey(), e.getValue());
                }
            }
            profileStack = null;
            times = null;
        }
    }
}
