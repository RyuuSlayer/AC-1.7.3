package io.github.ryuu.adventurecraft.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import io.github.ryuu.adventurecraft.scripting.Script;
import org.mozilla.javascript.Scriptable;

public class MusicScripts {
    ArrayList<MusicScriptEntry> musicEntries;

    JScriptHandler handler;

    public Scriptable scope;

    MusicScripts(Script script, File mapDir, JScriptHandler h) {
        this.musicEntries = new ArrayList<>();
        this.handler = h;
        this.scope = script.getNewScope();
        loadMusic(mapDir);
    }

    public void loadMusic(File mapDir) {
        this.musicEntries.clear();
        File musicFile = new File(mapDir, "musicScripts.txt");
        if (musicFile.exists())
            try {
                BufferedReader reader = new BufferedReader(new FileReader(musicFile));
                try {
                    while (reader.ready())
                        processLine(reader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
    }

    private void processLine(String line) {
        String[] parts = line.split(",", 3);
        if (parts.length == 3)
            this.musicEntries.add(new MusicScriptEntry(parts[0].trim(), parts[1].trim(), parts[2].trim()));
    }

    public String executeMusic(String music) {
        for (MusicScriptEntry m : this.musicEntries) {
            if (m.music.equals(music)) {
                this.handler.runScript(m.scriptFile, this.scope);
                return m.songName;
            }
        }
        return null;
    }
}
