package io.github.ryuu.adventurecraft.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import net.minecraft.client.Minecraft;
import org.mozilla.javascript.Scriptable;

public class JScriptHandler {
    fd world;

    File scriptDir;

    public HashMap<String, JScriptInfo> scripts;

    JScriptHandler(fd w, File mapDir) {
        this.world = w;
        this.scriptDir = new File(mapDir, "scripts");
        this.scripts = new HashMap<>();
        loadScripts();
    }

    public void loadScripts() {
        this.scripts.clear();
        if (this.scriptDir.exists())
            for (File f : this.scriptDir.listFiles()) {
                String fileName = f.getName().toLowerCase();
                if (fileName.endsWith(".js")) {
                    System.out.printf("Compiling %s\n", new Object[] { fileName });
                    String script = readFile(f);
                    this.scripts.put(fileName, new JScriptInfo(f.getName(), this.world.script.compileString(script, f.getName())));
                }
            }
    }

    public Object runScript(String fileName, Scriptable scope) {
        return runScript(fileName, scope, true);
    }

    public Object runScript(String fileName, Scriptable scope, boolean verbose) {
        JScriptInfo scriptInfo = this.scripts.get(fileName.toLowerCase());
        if (scriptInfo != null) {
            fileName = fileName.toLowerCase();
            long startTime = System.nanoTime();
            try {
                return this.world.script.runScript(scriptInfo.compiledScript, scope);
            } finally {
                scriptInfo.addStat(System.nanoTime() - startTime);
            }
        }
        if (verbose)
            Minecraft.minecraftInstance.v.a(String.format("Missing '%s'", new Object[] { fileName }));
        return null;
    }

    private String readFile(File f) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String buffer = "";
            try {
                while (reader.ready())
                    buffer = buffer + reader.readLine() + "\n";
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
