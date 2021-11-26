package io.github.ryuu.adventurecraft.util;

import io.github.ryuu.adventurecraft.scripting.Script;
import org.mozilla.javascript.Scriptable;

import java.io.*;
import java.util.ArrayList;

public class MusicScripts {

    public Scriptable scope;
    ArrayList<MusicScriptEntry> musicEntries = new ArrayList<>();
    JScriptHandler handler;

    public MusicScripts(Script script, File mapDir, JScriptHandler h) {
        this.handler = h;
        this.scope = script.getNewScope();
        this.loadMusic(mapDir);
    }

    public void loadMusic(File mapDir) {
        this.musicEntries.clear();
        File musicFile = new File(mapDir, "musicScripts.txt");
        if (musicFile.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(musicFile));
                try {
                    while (reader.ready()) {
                        this.processLine(reader.readLine());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void processLine(String line) {
        String[] parts = line.split(",", 3);
        if (parts.length == 3) {
            this.musicEntries.add(new MusicScriptEntry(parts[0].trim(), parts[1].trim(), parts[2].trim()));
        }
    }

    public String executeMusic(String music) {
        for (MusicScriptEntry m : this.musicEntries) {
            if (!m.music.equals(music)) continue;
            this.handler.runScript(m.scriptFile, this.scope);
            return m.songName;
        }
        return null;
    }
}
