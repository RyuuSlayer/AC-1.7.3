package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityWeather;
import net.minecraft.client.Minecraft;

public class GuiWeather extends da {
    private TileEntityWeather weather;

    private GuiSlider2 tempOffset;

    private GuiSlider2 timeOfDay;

    private GuiSlider2 timeRate;

    public GuiWeather(fd worldArg, TileEntityWeather w) {
        this.weather = w;
    }

    public void a() {}

    public void b() {
        ab b = new ab(0, 4, 0, "Don't Change Precipitation");
        this.e.add(b);
        if (this.weather.changePrecipitate)
            if (this.weather.precipitate) {
                b.e = "Start Precipitation";
            } else {
                b.e = "Stop Precipitation";
            }
        b = new ab(1, 4, 22, "Don't Change Temperature");
        this.e.add(b);
        if (this.weather.changeTempOffset)
            b.e = "Change Temperature";
        this.tempOffset = new GuiSlider2(2, 4, 44, 10, String.format("Temp Offset: %.2f", new Object[] { Double.valueOf(this.weather.tempOffset) }), (float)((this.weather.tempOffset + 1.0D) / 2.0D));
        this.e.add(this.tempOffset);
        b = new ab(3, 4, 66, "Don't Change Time");
        this.e.add(b);
        if (this.weather.changeTimeOfDay)
            b.e = "Change Time";
        this.timeOfDay = new GuiSlider2(4, 4, 88, 10, String.format("Time: %d", new Object[] { Integer.valueOf(this.weather.timeOfDay) }), this.weather.timeOfDay / 24000.0F);
        this.e.add(this.timeOfDay);
        b = new ab(5, 4, 110, "Don't Change Time Rate");
        this.e.add(b);
        if (this.weather.changeTimeRate)
            b.e = "Change Time Rate";
        this.timeRate = new GuiSlider2(6, 4, 132, 10, String.format("Time Rate: %.2f", new Object[] { Float.valueOf(this.weather.timeRate) }), (this.weather.timeRate + 16.0F) / 32.0F);
        this.e.add(this.timeRate);
        b = new ab(7, 4, 152, "Don't Change Thundering");
        this.e.add(b);
        if (this.weather.changeThundering)
            if (this.weather.thundering) {
                b.e = "Start Thundering";
            } else {
                b.e = "Stop Thundering";
            }
    }

    protected void a(ke guibutton) {
        if (guibutton.f == 0) {
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
                    guibutton.e = "Start Precipitation";
                } else {
                    guibutton.e = "Stop Precipitation";
                }
            } else {
                guibutton.e = "Don't Change Precipitation";
            }
        }
        if (guibutton.f == 7) {
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
                    guibutton.e = "Start Thundering";
                } else {
                    guibutton.e = "Stop Thundering";
                }
            } else {
                guibutton.e = "Don't Change Thundering";
            }
        } else if (guibutton.f == 1) {
            this.weather.changeTempOffset = !this.weather.changeTempOffset;
            if (this.weather.changeTempOffset) {
                guibutton.e = "Change Temperature";
            } else {
                guibutton.e = "Don't Change Temperature";
            }
        } else if (guibutton.f == 3) {
            this.weather.changeTimeOfDay = !this.weather.changeTimeOfDay;
            if (this.weather.changeTimeOfDay) {
                guibutton.e = "Change Time";
            } else {
                guibutton.e = "Don't Change Time";
            }
        } else if (guibutton.f == 5) {
            this.weather.changeTimeRate = !this.weather.changeTimeRate;
            if (this.weather.changeTimeRate) {
                guibutton.e = "Change Time Rate";
            } else {
                guibutton.e = "Don't Change Time Rate";
            }
        }
    }

    public void a(int i, int j, float f) {
        a(0, 0, this.c, this.d, -2147483648);
        this.weather.tempOffset = this.tempOffset.sliderValue * 2.0D - 1.0D;
        this.tempOffset.e = String.format("Temp Offset: %.2f", new Object[] { Double.valueOf(this.weather.tempOffset) });
        this.weather.timeOfDay = (int)(this.timeOfDay.sliderValue * 24000.0F);
        this.timeOfDay.e = String.format("Time: %d", new Object[] { Integer.valueOf(this.weather.timeOfDay) });
        this.weather.timeRate = this.timeRate.sliderValue * 32.0F - 16.0F;
        this.timeRate.e = String.format("Time Rate: %.2f", new Object[] { Float.valueOf(this.weather.timeRate) });
        super.a(i, j, f);
    }

    public static void showUI(fd worldArg, TileEntityWeather w) {
        Minecraft.minecraftInstance.a(new GuiWeather(worldArg, w));
    }

    public boolean c() {
        return false;
    }
}