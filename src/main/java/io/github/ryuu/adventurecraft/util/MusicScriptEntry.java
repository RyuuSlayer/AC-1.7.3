package io.github.ryuu.adventurecraft.util;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
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
