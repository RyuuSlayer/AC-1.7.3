package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityWeather;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
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

    public static void showUI(Level worldArg, TileEntityWeather w) {
        AccessMinecraft.getInstance().openScreen(new GuiWeather(worldArg, w));
    }

    @Override
    public void tick() {
    }

    @Override
    public void init() {
        OptionButton b = new OptionButton(0, 4, 0, "Don't Change Precipitation");
        this.buttons.add(b);
        if (this.weather.changePrecipitate) {
            b.text = this.weather.precipitate ? "Start Precipitation" : "Stop Precipitation";
        }
        b = new OptionButton(1, 4, 22, "Don't Change Temperature");
        this.buttons.add(b);
        if (this.weather.changeTempOffset) {
            b.text = "Change Temperature";
        }
        this.tempOffset = new GuiSlider2(2, 4, 44, 10, String.format("Temp Offset: %.2f", this.weather.tempOffset), (float) ((this.weather.tempOffset + 1.0) / 2.0));
        this.buttons.add(this.tempOffset);
        b = new OptionButton(3, 4, 66, "Don't Change Time");
        this.buttons.add(b);
        if (this.weather.changeTimeOfDay) {
            b.text = "Change Time";
        }
        this.timeOfDay = new GuiSlider2(4, 4, 88, 10, String.format("Time: %d", this.weather.timeOfDay), (float) this.weather.timeOfDay / 24000.0f);
        this.buttons.add(this.timeOfDay);
        b = new OptionButton(5, 4, 110, "Don't Change Time Rate");
        this.buttons.add(b);
        if (this.weather.changeTimeRate) {
            b.text = "Change Time Rate";
        }
        this.timeRate = new GuiSlider2(6, 4, 132, 10, String.format("Time Rate: %.2f", Float.valueOf(this.weather.timeRate)), (this.weather.timeRate + 16.0f) / 32.0f);
        this.buttons.add(this.timeRate);
        b = new OptionButton(7, 4, 152, "Don't Change Thundering");
        this.buttons.add(b);
        if (this.weather.changeThundering) {
            b.text = this.weather.thundering ? "Start Thundering" : "Stop Thundering";
        }
    }

    @Override
    protected void buttonClicked(Button button) {
        if (button.id == 0) {
            if (this.weather.changePrecipitate && this.weather.precipitate) {
                this.weather.precipitate = false;
            } else if (this.weather.changePrecipitate && !this.weather.precipitate) {
                this.weather.changePrecipitate = false;
            } else {
                this.weather.changePrecipitate = true;
                this.weather.precipitate = true;
            }
            button.text = this.weather.changePrecipitate ? (this.weather.precipitate ? "Start Precipitation" : "Stop Precipitation") : "Don't Change Precipitation";
        }
        if (button.id == 7) {
            if (this.weather.changeThundering && this.weather.thundering) {
                this.weather.thundering = false;
            } else if (this.weather.changeThundering && !this.weather.thundering) {
                this.weather.changeThundering = false;
            } else {
                this.weather.changeThundering = true;
                this.weather.thundering = true;
            }
            button.text = this.weather.changeThundering ? (this.weather.thundering ? "Start Thundering" : "Stop Thundering") : "Don't Change Thundering";
        } else if (button.id == 1) {
            boolean bl = this.weather.changeTempOffset = !this.weather.changeTempOffset;
            button.text = this.weather.changeTempOffset ? "Change Temperature" : "Don't Change Temperature";
        } else if (button.id == 3) {
            boolean bl = this.weather.changeTimeOfDay = !this.weather.changeTimeOfDay;
            button.text = this.weather.changeTimeOfDay ? "Change Time" : "Don't Change Time";
        } else if (button.id == 5) {
            this.weather.changeTimeRate = !this.weather.changeTimeRate;
            button.text = this.weather.changeTimeRate ? "Change Time Rate" : "Don't Change Time Rate";
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.fill(0, 0, this.width, this.height, Integer.MIN_VALUE);
        this.weather.tempOffset = (double) this.tempOffset.sliderValue * 2.0 - 1.0;
        this.tempOffset.text = String.format("Temp Offset: %.2f", this.weather.tempOffset);
        this.weather.timeOfDay = (int) (this.timeOfDay.sliderValue * 24000.0f);
        this.timeOfDay.text = String.format("Time: %d", this.weather.timeOfDay);
        this.weather.timeRate = this.timeRate.sliderValue * 32.0f - 16.0f;
        this.timeRate.text = String.format("Time Rate: %.2f", Float.valueOf(this.weather.timeRate));
        super.render(mouseX, mouseY, delta);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
