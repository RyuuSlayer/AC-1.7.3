package io.github.ryuu.adventurecraft.util;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.io.BufferedReader
 *  java.io.File
 *  java.io.FileNotFoundException
 *  java.io.FileReader
 *  java.io.IOException
 *  java.io.Reader
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.HashMap
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.src.JScriptInfo
 *  org.mozilla.javascript.Scriptable
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.level.Level;
import net.minecraft.src.JScriptInfo;
import org.mozilla.javascript.Scriptable;

public class JScriptHandler {

    Level world;

    File scriptDir;

    public HashMap<String, JScriptInfo> scripts;

    JScriptHandler(Level w, File mapDir) {
        this.world = w;
        this.scriptDir = new File(mapDir, "scripts");
        this.scripts = new HashMap();
        this.loadScripts();
    }

    public void loadScripts() {
        this.scripts.clear();
        if (this.scriptDir.exists()) {
            for (File f : this.scriptDir.listFiles()) {
                String fileName = f.getName().toLowerCase();
                if (!fileName.endsWith(".js"))
                    continue;
                System.out.printf("Compiling %s\n", new Object[] { fileName });
                String script = this.readFile(f);
                this.scripts.put((Object) fileName, (Object) new JScriptInfo(f.getName(), this.world.script.compileString(script, f.getName())));
            }
        }
    }

    public Object runScript(String fileName, Scriptable scope) {
        return this.runScript(fileName, scope, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object runScript(String fileName, Scriptable scope, boolean verbose) {
        JScriptInfo scriptInfo = (JScriptInfo) this.scripts.get((Object) fileName.toLowerCase());
        if (scriptInfo != null) {
            fileName = fileName.toLowerCase();
            long startTime = System.nanoTime();
            try {
                Object object = this.world.script.runScript(scriptInfo.compiledScript, scope);
                return object;
            } finally {
                scriptInfo.addStat(System.nanoTime() - startTime);
            }
        }
        if (verbose) {
            Minecraft.minecraftInstance.overlay.addChatMessage(String.format((String) "Missing '%s'", (Object[]) new Object[] { fileName }));
        }
        return null;
    }

    private String readFile(File f) {
        try {
            BufferedReader reader = new BufferedReader((Reader) new FileReader(f));
            String buffer = "";
            try {
                while (reader.ready()) {
                    buffer = buffer + reader.readLine() + "\n";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return buffer;
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return "";
        }
    }
}
