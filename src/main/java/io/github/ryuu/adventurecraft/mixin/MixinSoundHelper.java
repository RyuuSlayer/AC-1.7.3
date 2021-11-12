package io.github.ryuu.adventurecraft.mixin;

import java.io.File;
import java.util.Random;

import io.github.ryuu.adventurecraft.overrides.hr;
import net.minecraft.client.Minecraft;
import net.minecraft.client.options.GameOptions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.maths.MathsHelper;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

public class MixinSoundHelper {
    private static boolean g = false;
    private static SoundSystem a;
    private final hr b = new hr();
    private final hr c = new hr();
    private final hr d = new hr();
    private final Random h = new Random();
    private final int i = this.h.nextInt(12000);
    private int e = 0;
    private GameOptions f;
    private String currentSoundName;

    public void a(GameOptions gamesettings) {
        this.c.b = false;
        this.f = gamesettings;
        if (!g && (gamesettings == null || gamesettings.b != 0.0F || gamesettings.a != 0.0F))
            d();
    }

    private void d() {
        try {
            float f = this.f.b;
            float f1 = this.f.a;
            this.f.b = 0.0F;
            this.f.a = 0.0F;
            this.f.b();
            SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
            SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
            SoundSystemConfig.setCodec("mus", io.class);
            SoundSystemConfig.setCodec("wav", CodecWav.class);
            a = new SoundSystem();
            this.f.b = f;
            this.f.a = f1;
            this.f.b();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.err.println("error linking with the LibraryJavaSound plug-in");
        }
        g = true;
    }

    public void a() {
        if (!g && (this.f.b != 0.0F || this.f.a != 0.0F))
            d();
        if (g)
            if (this.f.a == 0.0F) {
                a.stop("BgMusic");
            } else {
                a.setVolume("BgMusic", this.f.a);
            }
    }

    public void b() {
        if (g)
            a.cleanup();
    }

    public void a(String s, File file) {
        this.b.a(s, file);
    }

    public void b(String s, File file) {
        this.c.a(s, file);
    }

    public void c(String s, File file) {
        this.d.a(s, file);
    }

    public void c() {
    }

    public void a(LivingEntity entityliving, float f) {
        if (!g || this.f.b == 0.0F)
            return;
        if (entityliving == null)
            return;
        float f1 = entityliving.aU + (entityliving.aS - entityliving.aU) * f;
        double d = entityliving.aJ + (entityliving.aM - entityliving.aJ) * f;
        double d1 = entityliving.aK + (entityliving.aN - entityliving.aK) * f;
        double d2 = entityliving.aL + (entityliving.aO - entityliving.aL) * f;
        float f2 = MathsHelper.b(-f1 * 0.01745329F - 3.141593F);
        float f3 = MathsHelper.a(-f1 * 0.01745329F - 3.141593F);
        float f4 = -f3;
        float f5 = 0.0F;
        float f6 = -f2;
        float f7 = 0.0F;
        float f8 = 1.0F;
        float f9 = 0.0F;
        a.setListenerPosition((float) d, (float) d1, (float) d2);
        a.setListenerOrientation(f4, f5, f6, f7, f8, f9);
        a.setPosition("BgMusic", (float) d, (float) d1, (float) d2);
    }

    public void a(String s, float f, float f1, float f2, float f3, float f4) {
        if (!g || this.f.b == 0.0F)
            return;
        String s1 = "streaming";
        if (a.playing("streaming"))
            a.stop("streaming");
        if (s == null)
            return;
        bh soundpoolentry = this.c.a(s);
        if (soundpoolentry != null && f3 > 0.0F) {
            if (a.playing("BgMusic"))
                a.stop("BgMusic");
            float f5 = 16.0F;
            a.newStreamingSource(true, s1, soundpoolentry.b, soundpoolentry.a, false, f, f1, f2, 2, f5 * 4.0F);
            a.setVolume(s1, 0.5F * this.f.b);
            a.play(s1);
        }
    }

    public void b(String s, float f, float f1, float f2, float f3, float f4) {
        if (!g || this.f.b == 0.0F)
            return;
        bh soundpoolentry = this.b.a(s);
        if (soundpoolentry != null && f3 > 0.0F) {
            this.e = (this.e + 1) % 256;
            String s1 = "sound_" + this.e;
            float f5 = 16.0F;
            if (f3 > 1.0F)
                f5 *= f3;
            a.newSource((f3 > 1.0F), s1, soundpoolentry.b, soundpoolentry.a, false, f, f1, f2, 2, f5);
            a.setPitch(s1, f4);
            if (f3 > 1.0F)
                f3 = 1.0F;
            a.setVolume(s1, f3 * this.f.b);
            a.play(s1);
        }
    }

    public void a(String s, float f, float f1) {
        if (!g || this.f.b == 0.0F)
            return;
        bh soundpoolentry = this.b.a(s);
        if (soundpoolentry != null) {
            this.e = (this.e + 1) % 256;
            String s1 = "sound_" + this.e;
            a.newSource(false, s1, soundpoolentry.b, soundpoolentry.a, false, 0.0F, 0.0F, 0.0F, 0, 0.0F);
            if (f > 1.0F)
                f = 1.0F;
            f *= 0.25F;
            a.setPitch(s1, f1);
            a.setVolume(s1, f * this.f.b);
            a.play(s1);
        }
    }

    public void playMusicFromStreaming(String s, int fadeOut, int fadeIn) {
        if (!g)
            return;
        if (s.equals(""))
            stopMusic();
        bh soundpoolentry = this.c.a(s);
        if (soundpoolentry != null) {
            if (a.playing("BgMusic")) {
                if (this.currentSoundName == soundpoolentry.a)
                    return;
                a.fadeOutIn("BgMusic", soundpoolentry.b, soundpoolentry.a, fadeOut, fadeIn);
            } else {
                a.backgroundMusic("BgMusic", soundpoolentry.b, soundpoolentry.a, true);
            }
            a.setVolume("BgMusic", this.f.a);
            a.play("BgMusic");
            this.currentSoundName = soundpoolentry.a;
            if (Minecraft.minecraftInstance.f != null)
                Minecraft.minecraftInstance.f.x.playingMusic = s;
        }
    }

    public void stopMusic() {
        if (g && a != null && a.playing("BgMusic")) {
            a.stop("BgMusic");
            if (Minecraft.minecraftInstance.f != null)
                Minecraft.minecraftInstance.f.x.playingMusic = "";
        }
    }
}
