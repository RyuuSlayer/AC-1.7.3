package io.github.ryuu.adventurecraft.scripting;

import net.minecraft.client.sound.SoundHelper;

public class ScriptSound {
    SoundHelper soundMgr;

    ScriptSound(SoundHelper s) {
        this.soundMgr = s;
    }

    public void playSoundUI(String soundName) {
        this.soundMgr.a(soundName.toLowerCase(), 1.0F, 1.0F);
    }

    public void playSoundUI(String soundName, float volume, float pitch) {
        this.soundMgr.a(soundName.toLowerCase(), volume, pitch);
    }

    public void playSound3D(String soundName, float x, float y, float z) {
        this.soundMgr.b(soundName.toLowerCase(), x, y, z, 1.0F, 1.0F);
    }

    public void playSound3D(String soundName, float x, float y, float z, float volume, float pitch) {
        this.soundMgr.b(soundName.toLowerCase(), x, y, z, volume, pitch);
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
