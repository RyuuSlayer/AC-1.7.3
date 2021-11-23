package io.github.ryuu.adventurecraft.util;

import net.minecraft.client.Minecraft;
import net.minecraft.level.Level;
import org.mozilla.javascript.Scriptable;

import java.io.*;
import java.util.HashMap;

public class JScriptHandler {

    public HashMap<String, JScriptInfo> scripts;
    Level world;
    File scriptDir;

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
                if (!fileName.endsWith(".js")) continue;
                System.out.printf("Compiling %s\n", fileName);
                String script = this.readFile(f);
                this.scripts.put((Object) fileName, (Object) new JScriptInfo(f.getName(), this.world.script.compileString(script, f.getName())));
            }
        }
    }

    public Object runScript(String fileName, Scriptable scope) {
        return this.runScript(fileName, scope, true);
    }

    public Object runScript(String fileName, Scriptable scope, boolean verbose) {
        JScriptInfo scriptInfo = this.scripts.get(fileName.toLowerCase());
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
            Minecraft.minecraftInstance.overlay.addChatMessage(String.format("Missing '%s'", new Object[]{fileName}));
        }
        return null;
    }

    private String readFile(File f) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));
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
