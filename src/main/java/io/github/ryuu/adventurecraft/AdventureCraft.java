package io.github.ryuu.adventurecraft;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.io.BufferedReader
 *  java.io.File
 *  java.io.IOException
 *  java.io.InputStreamReader
 *  java.io.Reader
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Float
 *  java.lang.NoClassDefFoundError
 *  java.lang.Object
 *  java.lang.Process
 *  java.lang.ProcessBuilder
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.lang.System
 *  java.lang.Thread
 *  java.lang.reflect.AccessibleObject
 *  java.lang.reflect.Field
 *  java.util.ArrayList
 *  java.util.List
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;

public class AdventureCraft {

    public static void main(String[] args) {
        String[] stringArray = args;
        int n = args.length;
        int n2 = 0;
        while (n2 < n) {
            String arg = stringArray[n2];
            if (arg.equals((Object) "ran")) {
                AdventureCraft.startAC(args);
                return;
            }
            ++n2;
        }
        AdventureCraft.runAC();
    }

    public static void runAC() {
        try {
            String[] emptyArgs = new String[] {};
            AdventureCraft.startAC(emptyArgs);
        } catch (NoClassDefFoundError e) {
            AdventureCraft.startNewAC();
            System.exit((int) 0);
        }
    }

    public static void startNewAC() {
        String os = System.getProperty((String) "os.name").toLowerCase();
        ArrayList params = new ArrayList();
        float javaVersion = Float.valueOf((String) System.getProperty((String) "java.specification.version")).floatValue();
        if (os.contains((CharSequence) "win")) {
            params.add((Object) "javaw");
        } else {
            params.add((Object) "java");
        }
        params.add((Object) "-Xmx512m");
        params.add((Object) "-Xms512m");
        if ((double) javaVersion >= 1.6) {
            params.add((Object) "-Xincgc");
        }
        params.add((Object) "-cp");
        if (os.contains((CharSequence) "win")) {
            params.add((Object) "AdventureCraft.jar;ACBin.jar;js.jar;./resources/;./.minecraft/bin/jinput.jar;./.minecraft/bin/lwjgl.jar;./.minecraft/bin/lwjgl_util.jar;./.minecraft/bin/minecraft.jar");
        } else {
            params.add((Object) "AdventureCraft.jar:ACBin.jar:js.jar;./resources/:./.minecraft/bin/jinput.jar:./.minecraft/bin/lwjgl.jar:./.minecraft/bin/lwjgl_util.jar:./.minecraft/bin/minecraft.jar");
        }
        params.add((Object) "-Djava.library.path=./.minecraft/bin/natives");
        params.add((Object) "AdventureCraft");
        params.add((Object) "ran");
        ProcessBuilder pb = new ProcessBuilder((List) params);
        pb.directory(new File(System.getProperty((String) "user.dir")));
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
            Field.setAccessible((AccessibleObject[]) new Field[] { f }, (boolean) true);
            f.set(null, (Object) new File(".minecraft"));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Minecraft.main(args);
        Thread[] threads = new Thread[256];
        int count = Thread.enumerate((Thread[]) threads);
        Minecraft mc = null;
        int i = 0;
        while (i < count) {
            if (threads[i].getName().equals((Object) "Minecraft main thread")) {
                try {
                    Field f = Thread.class.getDeclaredField("target");
                    Field.setAccessible((AccessibleObject[]) new Field[] { f }, (boolean) true);
                    mc = (Minecraft) f.get((Object) threads[i]);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit((int) 0);
                }
                break;
            }
            ++i;
        }
    }

    public static void startWithLauncher(String arg) {
        String os = System.getProperty((String) "os.name").toLowerCase();
        ArrayList params = new ArrayList();
        if (os.contains((CharSequence) "win")) {
            params.add((Object) "javaw");
        } else {
            params.add((Object) "java");
        }
        params.add((Object) "-Xmx1024m");
        params.add((Object) "-Xms512m");
        if (os.startsWith("mac")) {
            params.add((Object) "-XstartOnFirstThread");
        }
        params.add((Object) "-cp");
        if (os.contains((CharSequence) "win")) {
            params.add((Object) "AdventureCraft.jar;ACBin.jar;js.jar;DJNativeSwing.jar;DJNativeSwing-SWT.jar;swt.jar;./resources/;./.minecraft/bin/jinput.jar;./.minecraft/bin/lwjgl.jar;./.minecraft/bin/lwjgl_util.jar;./.minecraft/bin/minecraft.jar");
        } else {
            params.add((Object) "AdventureCraft.jar:ACBin.jar:js.jar:DJNativeSwing.jar:DJNativeSwing-SWT.jar:swt.jar:./resources/:./.minecraft/bin/jinput.jar:./.minecraft/bin/lwjgl.jar:./.minecraft/bin/lwjgl_util.jar:./.minecraft/bin/minecraft.jar");
        }
        params.add((Object) "-Djava.library.path=./.minecraft/bin/natives");
        params.add((Object) "ACLauncher");
        params.add((Object) arg);
        if (os.startsWith("mac")) {
            params.add((Object) "firstThread");
        }
        ProcessBuilder pb = new ProcessBuilder((List) params);
        pb.directory(new File(System.getProperty((String) "user.dir")));
        Process process = null;
        try {
            process = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (process == null) {
            System.out.println("Process didn't start :(");
        } else {
            BufferedReader br = new BufferedReader((Reader) new InputStreamReader(process.getInputStream()));
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
