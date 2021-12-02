package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.extensions.client.sound.ExSoundHelper;
import net.minecraft.client.sound.SoundHelper;

public class ScriptSound {

    SoundHelper soundMgr;

    public ScriptSound(SoundHelper s) {
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
        ((ExSoundHelper)this.soundMgr).playMusicFromStreaming(musicName.toLowerCase(), 0, 0);
    }

    public void playMusic(String musicName, int fadeOut, int fadeIn) {
        ((ExSoundHelper)this.soundMgr).playMusicFromStreaming(musicName.toLowerCase(), fadeOut, fadeIn);
    }

    public void stopMusic() {
        ((ExSoundHelper)this.soundMgr).stopMusic();
    }
}
