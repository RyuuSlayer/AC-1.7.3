package io.github.ryuu.adventurecraft.mixin.client.sound;

import java.io.File;
import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_266;
import net.minecraft.class_267;
import net.minecraft.class_309;
import net.minecraft.client.Minecraft;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.sound.SoundHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.maths.MathsHelper;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SoundHelper.class)
public class MixinSoundHelper {

    @Shadow()
    private static SoundSystem soundSystem;

    private class_266 field_2668 = new class_266();

    private class_266 field_2669 = new class_266();

    private class_266 field_2670 = new class_266();

    private int field_2671 = 0;

    private GameOptions gameOptions;

    private static boolean field_2673;

    private Random rand = new Random();

    private int field_2675 = this.rand.nextInt(12000);

    private String currentSoundName;

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_2019() {
        try {
            float f = this.gameOptions.sound;
            float f1 = this.gameOptions.music;
            this.gameOptions.sound = 0.0f;
            this.gameOptions.music = 0.0f;
            this.gameOptions.saveOptions();
            SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
            SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
            SoundSystemConfig.setCodec("mus", class_309.class);
            SoundSystemConfig.setCodec("wav", CodecWav.class);
            soundSystem = new SoundSystem();
            this.gameOptions.sound = f;
            this.gameOptions.music = f1;
            this.gameOptions.saveOptions();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.err.println("error linking with the LibraryJavaSound plug-in");
        }
        field_2673 = true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_2011(String s, File file) {
        this.field_2668.method_959(s, file);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_2016(String s, File file) {
        this.field_2669.method_959(s, file);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_2018(String s, File file) {
        this.field_2670.method_959(s, file);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void playBackgroundMusic() {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setSoundPosition(LivingEntity entity, float f) {
        if (!field_2673 || this.gameOptions.sound == 0.0f) {
            return;
        }
        if (entity == null) {
            return;
        }
        float f1 = entity.prevYaw + (entity.yaw - entity.prevYaw) * f;
        double d = entity.prevX + (entity.x - entity.prevX) * (double) f;
        double d1 = entity.prevY + (entity.y - entity.prevY) * (double) f;
        double d2 = entity.prevZ + (entity.z - entity.prevZ) * (double) f;
        float f2 = MathsHelper.cos(-f1 * 0.01745329f - 3.141593f);
        float f3 = MathsHelper.sin(-f1 * 0.01745329f - 3.141593f);
        float f4 = -f3;
        float f5 = 0.0f;
        float f6 = -f2;
        float f7 = 0.0f;
        float f8 = 1.0f;
        float f9 = 0.0f;
        soundSystem.setListenerPosition((float) d, (float) d1, (float) d2);
        soundSystem.setListenerOrientation(f4, f5, f6, f7, f8, f9);
        soundSystem.setPosition("BgMusic", (float) d, (float) d1, (float) d2);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_2010(String s, float f, float f1, float f2, float f3, float f4) {
        if (!field_2673 || this.gameOptions.sound == 0.0f) {
            return;
        }
        String s1 = "streaming";
        if (soundSystem.playing("streaming")) {
            soundSystem.stop("streaming");
        }
        if (s == null) {
            return;
        }
        class_267 soundpoolentry = this.field_2669.method_958(s);
        if (soundpoolentry != null && f3 > 0.0f) {
            if (soundSystem.playing("BgMusic")) {
                soundSystem.stop("BgMusic");
            }
            float f5 = 16.0f;
            soundSystem.newStreamingSource(true, s1, soundpoolentry.field_2127, soundpoolentry.field_2126, false, f, f1, f2, 2, f5 * 4.0f);
            soundSystem.setVolume(s1, 0.5f * this.gameOptions.sound);
            soundSystem.play(s1);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void playSound(String s, float f, float f1, float f2, float f3, float f4) {
        if (!field_2673 || this.gameOptions.sound == 0.0f) {
            return;
        }
        class_267 soundpoolentry = this.field_2668.method_958(s);
        if (soundpoolentry != null && f3 > 0.0f) {
            this.field_2671 = (this.field_2671 + 1) % 256;
            String s1 = "sound_" + this.field_2671;
            float f5 = 16.0f;
            if (f3 > 1.0f) {
                f5 *= f3;
            }
            soundSystem.newSource(f3 > 1.0f, s1, soundpoolentry.field_2127, soundpoolentry.field_2126, false, f, f1, f2, 2, f5);
            soundSystem.setPitch(s1, f4);
            if (f3 > 1.0f) {
                f3 = 1.0f;
            }
            soundSystem.setVolume(s1, f3 * this.gameOptions.sound);
            soundSystem.play(s1);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void playSound(String s, float f, float f1) {
        if (!field_2673 || this.gameOptions.sound == 0.0f) {
            return;
        }
        class_267 soundpoolentry = this.field_2668.method_958(s);
        if (soundpoolentry != null) {
            this.field_2671 = (this.field_2671 + 1) % 256;
            String s1 = "sound_" + this.field_2671;
            soundSystem.newSource(false, s1, soundpoolentry.field_2127, soundpoolentry.field_2126, false, 0.0f, 0.0f, 0.0f, 0, 0.0f);
            if (f > 1.0f) {
                f = 1.0f;
            }
            soundSystem.setPitch(s1, f1);
            soundSystem.setVolume(s1, (f *= 0.25f) * this.gameOptions.sound);
            soundSystem.play(s1);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void playMusicFromStreaming(String s, int fadeOut, int fadeIn) {
        class_267 soundpoolentry;
        if (!field_2673) {
            return;
        }
        if (s.equals((Object) "")) {
            this.stopMusic();
        }
        if ((soundpoolentry = this.field_2669.method_958(s)) != null) {
            if (soundSystem.playing("BgMusic")) {
                if (this.currentSoundName == soundpoolentry.field_2126) {
                    return;
                }
                soundSystem.fadeOutIn("BgMusic", soundpoolentry.field_2127, soundpoolentry.field_2126, fadeOut, fadeIn);
            } else {
                soundSystem.backgroundMusic("BgMusic", soundpoolentry.field_2127, soundpoolentry.field_2126, true);
            }
            soundSystem.setVolume("BgMusic", this.gameOptions.music);
            soundSystem.play("BgMusic");
            this.currentSoundName = soundpoolentry.field_2126;
            if (Minecraft.minecraftInstance.level != null) {
                Minecraft.minecraftInstance.level.properties.playingMusic = s;
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void stopMusic() {
        if (field_2673 && soundSystem != null && soundSystem.playing("BgMusic")) {
            soundSystem.stop("BgMusic");
            if (Minecraft.minecraftInstance.level != null) {
                Minecraft.minecraftInstance.level.properties.playingMusic = "";
            }
        }
    }

    static {
        field_2673 = false;
    }
}
