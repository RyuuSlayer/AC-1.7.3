/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package io.github.ryuu.adventurecraft.scripting;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.SoundHelper;
import io.github.ryuu.adventurecraft.mixin.item.MixinSoundHelper;

public class ScriptSound {

    MixinSoundHelper soundMgr;

    ScriptSound(MixinSoundHelper s) {
        this.soundMgr = s;
    }

    public void playSoundUI(String soundName) {
        this.soundMgr.playSound(soundName.toLowerCase(), 1.0f, 1.0f);
    }

    public void playSoundUI(String soundName, float volume, float pitch) {
        this.soundMgr.playSound(soundName.toLowerCase(), volume, pitch);
    }

    public void playSound3D(String soundName, float x, float y, float z) {
        this.soundMgr.playSound(soundName.toLowerCase(), x, y, z, 1.0f, 1.0f);
    }

    public void playSound3D(String soundName, float x, float y, float z, float volume, float pitch) {
        this.soundMgr.playSound(soundName.toLowerCase(), x, y, z, volume, pitch);
    }

    public void playMusic(String musicName) {
        this.soundMgr.playMusicFromStreaming(musicName.toLowerCase(), 0, 0);
    }

    public void playMusic(String musicName, int fadeOut, int fadeIn) {
        this.soundMgr.playMusicFromStreaming(musicName.toLowerCase(), fadeOut, fadeIn);
    }

    public void stopMusic() {
        this.soundMgr.stopMusic();
    }
}
