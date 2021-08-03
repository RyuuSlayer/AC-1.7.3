package io.github.ryuu.adventurecraft.util;

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
