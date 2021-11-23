package io.github.ryuu.adventurecraft.mixin.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.SmokeRenderer;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TextRenderer;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.List;

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
