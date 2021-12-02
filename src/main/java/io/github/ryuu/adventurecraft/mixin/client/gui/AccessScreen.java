package io.github.ryuu.adventurecraft.mixin.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.render.TextRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(Screen.class)
public interface AccessScreen {
    @Accessor
    Minecraft getMinecraft();

    @Accessor
    List<Button> getButtons();

    @Accessor
    TextRenderer getTextManager();

    @Accessor
    Button getLastClickedButton();

    @Accessor
    void setLastClickedButton(Button lastClickedButton);
}
