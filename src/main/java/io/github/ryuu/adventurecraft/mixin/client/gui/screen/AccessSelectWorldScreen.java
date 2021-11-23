package io.github.ryuu.adventurecraft.mixin.client.gui.screen;

import io.github.ryuu.adventurecraft.mixin.client.gui.AccessScreen;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.screen.SelectWorldScreen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.level.storage.LevelMetadata;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.text.DateFormat;
import java.util.List;

@Mixin(SelectWorldScreen.class)
public interface AccessSelectWorldScreen extends AccessScreen {
    @Accessor
    DateFormat getDateFormat();

    @Accessor
    Screen getParent();

    @Accessor
    String getTitle();

    @Accessor
    boolean getField_2434();

    @Accessor
    int getField_2435();

    @Accessor
    void setField_2435(int value);

    @Accessor
    List<LevelMetadata> getField_2436();

    @Accessor
    SelectWorldScreen.class_569 getField_2437();

    @Accessor
    String getField_2438();

    @Accessor
    String getField_2439();

    @Accessor
    boolean getField_2440();

    // The rename button is never initialized in AC.
    //@Accessor
    //Button getRenameButton();

    @Accessor
    Button getSelectButton();

    @Accessor
    Button getDeleteButton();
}
