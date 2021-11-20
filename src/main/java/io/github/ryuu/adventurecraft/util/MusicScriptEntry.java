package io.github.ryuu.adventurecraft.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class MusicScriptEntry {

    public String music;

    public String songName;

    public String scriptFile;

    MusicScriptEntry(String _music, String _scriptFile, String _songName) {
        this.music = _music;
        this.songName = _songName;
        this.scriptFile = _scriptFile;
    }
}
