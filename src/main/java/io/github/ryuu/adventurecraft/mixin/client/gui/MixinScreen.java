package io.github.ryuu.adventurecraft.mixin.client.gui;

import io.github.ryuu.adventurecraft.accessors.client.gui.ScreenInputGrab;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Screen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Screen.class)
public abstract class MixinScreen extends DrawableHelper implements AccessScreen, ScreenInputGrab {

    public boolean disableInputGrabbing = false;

    @Override
    public boolean getDisableInputGrabbing() {
        return disableInputGrabbing;
    }

    @Override
    public void setDisableInputGrabbing(boolean value) {
        disableInputGrabbing = value;
    }
}
