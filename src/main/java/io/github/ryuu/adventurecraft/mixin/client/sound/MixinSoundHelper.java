package io.github.ryuu.adventurecraft.mixin.client.sound;

import io.github.ryuu.adventurecraft.extensions.client.sound.ExSoundHelper;
import io.github.ryuu.adventurecraft.extensions.level.ExLevelProperties;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.class_266;
import net.minecraft.class_267;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.sound.SoundHelper;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulscode.sound.SoundSystem;

@Mixin(SoundHelper.class)
public abstract class MixinSoundHelper implements ExSoundHelper {

    @Shadow
    private static SoundSystem soundSystem;

    @Shadow
    private static boolean field_2673;

    @Shadow
    private class_266 field_2669;

    @Shadow
    private GameOptions gameOptions;

    private String currentSoundName;

    @Redirect(method = "playBackgroundMusic", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/sound/SoundHelper;field_2673:Z"))
    public boolean removeBackgroundMusic() {
        return false;
    }

    @Inject(method = "setSoundPosition", at = @At(
            value = "INVOKE",
            target = "Lpaulscode/sound/SoundSystem;setListenerOrientation(FFFFFF)V",
            shift = At.Shift.AFTER))
    public void setSoundPosition(LivingEntity entity, float f, CallbackInfo ci) {
        double d = entity.prevX + (entity.x - entity.prevX) * (double) f;
        double d1 = entity.prevY + (entity.y - entity.prevY) * (double) f;
        double d2 = entity.prevZ + (entity.z - entity.prevZ) * (double) f;
        soundSystem.setPosition("BgMusic", (float) d, (float) d1, (float) d2);
    }

    @Override
    public void playMusicFromStreaming(String s, int fadeOut, int fadeIn) {
        class_267 soundpoolentry;
        if (!field_2673) {
            return;
        }
        if (s.equals("")) {
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
            if (AccessMinecraft.getInstance().level != null) {
                ((ExLevelProperties) AccessMinecraft.getInstance().level.getProperties()).setPlayingMusic(s);
            }
        }
    }

    @Override
    public void stopMusic() {
        if (field_2673 && soundSystem != null && soundSystem.playing("BgMusic")) {
            soundSystem.stop("BgMusic");
            if (AccessMinecraft.getInstance().level != null) {
                ((ExLevelProperties) AccessMinecraft.getInstance().level.getProperties()).setPlayingMusic("");
            }
        }
    }
}
