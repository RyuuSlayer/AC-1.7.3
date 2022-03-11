package io.github.ryuu.adventurecraft.util;

import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.level.Level;
import org.mozilla.javascript.Scriptable;

import java.io.*;
import java.util.HashMap;

public class JScriptHandler {

    public HashMap<String, JScriptInfo> scripts;
    Level level;
    File scriptDir;

    public JScriptHandler(Level w, File mapDir) {
        this.level = w;
        this.scriptDir = new File(mapDir, "scripts");
        this.scripts = new HashMap<>();
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
                this.scripts.put(fileName, new JScriptInfo(f.getName(), ((ExLevel) this.level).getScript().compileString(script, f.getName())));
            }
        }
    }

    public Object runScript(String fileName, Scriptable scope) {
        return this.runScript(fileName, scope, true);
    }

    public Object runScript(String fileName, Scriptable scope, boolean verbose) {
        JScriptInfo scriptInfo = this.scripts.get(fileName.toLowerCase());
        if (scriptInfo != null) {
            //fileName = fileName.toLowerCase();
            long startTime = System.nanoTime();
            try {
                Object object = ((ExLevel) this.level).getScript().runScript(scriptInfo.compiledScript, scope);
                return object;
            } finally {
                scriptInfo.addStat(System.nanoTime() - startTime);
            }
        }
        if (verbose) {
            AccessMinecraft.getInstance().overlay.addChatMessage(String.format("Missing '%s'", fileName));
        }
        return null;
    }

    private String readFile(File f) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            StringBuilder buffer = new StringBuilder();
            try {
                while (reader.ready()) {
                    buffer.append(reader.readLine()).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return buffer.toString();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return "";
        }
    }
}
