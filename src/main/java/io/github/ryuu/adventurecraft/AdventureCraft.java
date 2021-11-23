package io.github.ryuu.adventurecraft;

import net.minecraft.client.Minecraft;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class AdventureCraft {

    public static void main(String[] args) {
        String[] stringArray = args;
        int n = args.length;
        int n2 = 0;
        while (n2 < n) {
            String arg = stringArray[n2];
            if (arg.equals("ran")) {
                AdventureCraft.startAC(args);
                return;
            }
            ++n2;
        }
        AdventureCraft.runAC();
    }

    public static void runAC() {
        try {
            String[] emptyArgs = new String[]{};
            AdventureCraft.startAC(emptyArgs);
        } catch (NoClassDefFoundError e) {
            AdventureCraft.startNewAC();
            System.exit(0);
        }
    }

    public static void startNewAC() {
        String os = System.getProperty("os.name").toLowerCase();
        ArrayList params = new ArrayList();
        float javaVersion = Float.valueOf(System.getProperty("java.specification.version")).floatValue();
        if (os.contains("win")) {
            params.add("javaw");
        } else {
            params.add("java");
        }
        params.add("-Xmx512m");
        params.add("-Xms512m");
        if ((double) javaVersion >= 1.6) {
            params.add("-Xincgc");
        }
        params.add("-cp");
        if (os.contains("win")) {
            params.add("AdventureCraft.jar;ACBin.jar;js.jar;./resources/;./.minecraft/bin/jinput.jar;./.minecraft/bin/lwjgl.jar;./.minecraft/bin/lwjgl_util.jar;./.minecraft/bin/minecraft.jar");
        } else {
            params.add("AdventureCraft.jar:ACBin.jar:js.jar;./resources/:./.minecraft/bin/jinput.jar:./.minecraft/bin/lwjgl.jar:./.minecraft/bin/lwjgl_util.jar:./.minecraft/bin/minecraft.jar");
        }
        params.add("-Djava.library.path=./.minecraft/bin/natives");
        params.add("AdventureCraft");
        params.add("ran");
        ProcessBuilder pb = new ProcessBuilder(params);
        pb.directory(new File(System.getProperty("user.dir")));
        Process process = null;
        try {
            process = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (process == null) {
            System.out.println("Process didn't start :(");
        }
    }

    public static void startAC(String[] args) {
        try {
            Field f = Minecraft.class.getDeclaredField("af");
            Field.setAccessible(new Field[]{f}, true);
            f.set(null, new File(".minecraft"));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Minecraft.main(args);
        Thread[] threads = new Thread[256];
        int count = Thread.enumerate(threads);
        Minecraft mc = null;
        int i = 0;
        while (i < count) {
            if (threads[i].getName().equals("Minecraft main thread")) {
                try {
                    Field f = Thread.class.getDeclaredField("target");
                    Field.setAccessible(new Field[]{f}, true);
                    mc = (Minecraft) f.get(threads[i]);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
                break;
            }
            ++i;
        }
    }

    public static void startWithLauncher(String arg) {
        String os = System.getProperty("os.name").toLowerCase();
        ArrayList params = new ArrayList();
        if (os.contains("win")) {
            params.add("javaw");
        } else {
            params.add("java");
        }
        params.add("-Xmx1024m");
        params.add("-Xms512m");
        if (os.startsWith("mac")) {
            params.add("-XstartOnFirstThread");
        }
        params.add("-cp");
        if (os.contains("win")) {
            params.add("AdventureCraft.jar;ACBin.jar;js.jar;DJNativeSwing.jar;DJNativeSwing-SWT.jar;swt.jar;./resources/;./.minecraft/bin/jinput.jar;./.minecraft/bin/lwjgl.jar;./.minecraft/bin/lwjgl_util.jar;./.minecraft/bin/minecraft.jar");
        } else {
            params.add("AdventureCraft.jar:ACBin.jar:js.jar:DJNativeSwing.jar:DJNativeSwing-SWT.jar:swt.jar:./resources/:./.minecraft/bin/jinput.jar:./.minecraft/bin/lwjgl.jar:./.minecraft/bin/lwjgl_util.jar:./.minecraft/bin/minecraft.jar");
        }
        params.add("-Djava.library.path=./.minecraft/bin/natives");
        params.add("ACLauncher");
        params.add(arg);
        if (os.startsWith("mac")) {
            params.add("firstThread");
        }
        ProcessBuilder pb = new ProcessBuilder(params);
        pb.directory(new File(System.getProperty("user.dir")));
        Process process = null;
        try {
            process = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (process == null) {
            System.out.println("Process didn't start :(");
        } else {
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuffer sb = new StringBuffer();
            try {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
