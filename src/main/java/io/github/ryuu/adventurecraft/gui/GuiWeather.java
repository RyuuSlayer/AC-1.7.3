package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityWeather;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.gui.widgets.OptionButton;
import net.minecraft.level.Level;

public class GuiWeather extends Screen {
    private final TileEntityWeather weather;

    private GuiSlider2 tempOffset;

    private GuiSlider2 timeOfDay;

    private GuiSlider2 timeRate;

    public GuiWeather(Level worldArg, TileEntityWeather w) {
        this.weather = w;
    }

    @Override
    public void tick() {
    }

    @Override
    public void init() {
        OptionButton b = new OptionButton(0, 4, 0, "Don't Change Precipitation");
        this.buttons.add(b);
        if (this.weather.changePrecipitate)
            if (this.weather.precipitate) {
                b.text = "Start Precipitation";
            } else {
                b.text = "Stop Precipitation";
            }
        b = new OptionButton(1, 4, 22, "Don't Change Temperature");
        this.buttons.add(b);
        if (this.weather.changeTempOffset)
            b.text = "Change Temperature";
        this.tempOffset = new GuiSlider2(2, 4, 44, 10, String.format("Temp Offset: %.2f", this.weather.tempOffset), (float) ((this.weather.tempOffset + 1.0D) / 2.0D));
        this.buttons.add(this.tempOffset);
        b = new OptionButton(3, 4, 66, "Don't Change Time");
        this.buttons.add(b);
        if (this.weather.changeTimeOfDay)
            b.text = "Change Time";
        this.timeOfDay = new GuiSlider2(4, 4, 88, 10, String.format("Time: %d", this.weather.timeOfDay), this.weather.timeOfDay / 24000.0F);
        this.buttons.add(this.timeOfDay);
        b = new OptionButton(5, 4, 110, "Don't Change Time Rate");
        this.buttons.add(b);
        if (this.weather.changeTimeRate)
            b.text = "Change Time Rate";
        this.timeRate = new GuiSlider2(6, 4, 132, 10, String.format("Time Rate: %.2f", this.weather.timeRate), (this.weather.timeRate + 16.0F) / 32.0F);
        this.buttons.add(this.timeRate);
        b = new OptionButton(7, 4, 152, "Don't Change Thundering");
        this.buttons.add(b);
        if (this.weather.changeThundering)
            if (this.weather.thundering) {
                b.text = "Start Thundering";
            } else {
                b.text = "Stop Thundering";
            }
    }

    @Override
    protected void buttonClicked(Button guibutton) {
        if (guibutton.id == 0) {
            if (this.weather.changePrecipitate && this.weather.precipitate) {
                this.weather.precipitate = false;
            } else if (this.weather.changePrecipitate && !this.weather.precipitate) {
                this.weather.changePrecipitate = false;
            } else {
                this.weather.changePrecipitate = true;
                this.weather.precipitate = true;
            }
            if (this.weather.changePrecipitate) {
                if (this.weather.precipitate) {
                    guibutton.text = "Start Precipitation";
                } else {
                    guibutton.text = "Stop Precipitation";
                }
            } else {
                guibutton.text = "Don't Change Precipitation";
            }
        }
        if (guibutton.id == 7) {
            if (this.weather.changeThundering && this.weather.thundering) {
                this.weather.thundering = false;
            } else if (this.weather.changeThundering && !this.weather.thundering) {
                this.weather.changeThundering = false;
            } else {
                this.weather.changeThundering = true;
                this.weather.thundering = true;
            }
            if (this.weather.changeThundering) {
                if (this.weather.thundering) {
                    guibutton.text = "Start Thundering";
                } else {
                    guibutton.text = "Stop Thundering";
                }
            } else {
                guibutton.text = "Don't Change Thundering";
            }
        } else if (guibutton.id == 1) {
            this.weather.changeTempOffset = !this.weather.changeTempOffset;
            if (this.weather.changeTempOffset) {
                guibutton.text = "Change Temperature";
            } else {
                guibutton.text = "Don't Change Temperature";
            }
        } else if (guibutton.id == 3) {
            this.weather.changeTimeOfDay = !this.weather.changeTimeOfDay;
            if (this.weather.changeTimeOfDay) {
                guibutton.text = "Change Time";
            } else {
                guibutton.text = "Don't Change Time";
            }
        } else if (guibutton.id == 5) {
            this.weather.changeTimeRate = !this.weather.changeTimeRate;
            if (this.weather.changeTimeRate) {
                guibutton.text = "Change Time Rate";
            } else {
                guibutton.text = "Don't Change Time Rate";
            }
        }
    }

    @Override
    public void render(int i, int j, float f) {
        fill(0, 0, this.width, this.height, -2147483648);
        this.weather.tempOffset = this.tempOffset.sliderValue * 2.0D - 1.0D;
        this.tempOffset.text = String.format("Temp Offset: %.2f", this.weather.tempOffset);
        this.weather.timeOfDay = (int) (this.timeOfDay.sliderValue * 24000.0F);
        this.timeOfDay.text = String.format("Time: %d", this.weather.timeOfDay);
        this.weather.timeRate = this.timeRate.sliderValue * 32.0F - 16.0F;
        this.timeRate.text = String.format("Time Rate: %.2f", this.weather.timeRate);
        super.render(i, j, f);
    }

    public static void showUI(Level worldArg, TileEntityWeather w) {
        Minecraft.minecraftInstance.a(new GuiWeather(worldArg, w));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}