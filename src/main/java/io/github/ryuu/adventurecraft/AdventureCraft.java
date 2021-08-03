package io.github.ryuu.adventurecraft;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;

public class AdventureCraft {

    //TODO: This was used by the old AdventureCraft launcher to start the game, the AdventureCraft.jar, ACBin.jar & js.jar were added through startup parameters.

    public static void main(String[] args) {
        byte b;
        int i;
        String[] arrayOfString;
        for (i = (arrayOfString = args).length, b = 0; b < i; ) {
            String arg = arrayOfString[b];
            if (arg.equals("ran")) {
                startAC(args);
                return;
            }
            b++;
        }
        runAC();
    }

    public static void runAC() {
        try {
            String[] emptyArgs = new String[0];
            startAC(emptyArgs);
        } catch (NoClassDefFoundError e) {
            startNewAC();
            System.exit(0);
        }
    }

    public static void startNewAC() {
        String os = System.getProperty("os.name").toLowerCase();
        ArrayList<String> params = new ArrayList<String>();
        float javaVersion = Float.valueOf(System.getProperty("java.specification.version")).floatValue();
        if (os.contains("win")) {
            params.add("javaw");
        } else {
            params.add("java");
        }
        params.add("-Xmx512m");
        params.add("-Xms512m");
        if (javaVersion >= 1.6D)
            params.add("-Xincgc");
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
        if (process == null)
            System.out.println("Process didn't start :(");
    }

    public static void startAC(String[] args) {
        try {
            Field f = Minecraft.class.getDeclaredField("af");
            Field.setAccessible((AccessibleObject[])new Field[] { f }, true);
            f.set((Object)null, new File(".minecraft"));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Minecraft.main(args);
        Thread[] threads = new Thread[256];
        int count = Thread.enumerate(threads);
        Minecraft mc = null;
        for (int i = 0; i < count; i++) {
            if (threads[i].getName().equals("Minecraft main thread")) {
                try {
                    Field f = Thread.class.getDeclaredField("target");
                    Field.setAccessible((AccessibleObject[])new Field[] { f }, true);
                    mc = (Minecraft)f.get(threads[i]);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
                break;
            }
        }
    }

    public static void startWithLauncher(String arg) {
        String os = System.getProperty("os.name").toLowerCase();
        ArrayList<String> params = new ArrayList<String>();
        if (os.contains("win")) {
            params.add("javaw");
        } else {
            params.add("java");
        }
        params.add("-Xmx1024m");
        params.add("-Xms512m");
        if (os.startsWith("mac"))
            params.add("-XstartOnFirstThread");
        params.add("-cp");
        if (os.contains("win")) {
            params.add("AdventureCraft.jar;ACBin.jar;js.jar;DJNativeSwing.jar;DJNativeSwing-SWT.jar;swt.jar;./resources/;./.minecraft/bin/jinput.jar;./.minecraft/bin/lwjgl.jar;./.minecraft/bin/lwjgl_util.jar;./.minecraft/bin/minecraft.jar");
        } else {
            params.add("AdventureCraft.jar:ACBin.jar:js.jar:DJNativeSwing.jar:DJNativeSwing-SWT.jar:swt.jar:./resources/:./.minecraft/bin/jinput.jar:./.minecraft/bin/lwjgl.jar:./.minecraft/bin/lwjgl_util.jar:./.minecraft/bin/minecraft.jar");
        }
        params.add("-Djava.library.path=./.minecraft/bin/natives");
        params.add("ACLauncher");
        params.add(arg);
        if (os.startsWith("mac"))
            params.add("firstThread");
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
                while ((line = br.readLine()) != null)
                    System.out.println(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}