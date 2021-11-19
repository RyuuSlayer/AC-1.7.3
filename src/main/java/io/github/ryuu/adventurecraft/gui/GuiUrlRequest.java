package io.github.ryuu.adventurecraft.gui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.gui.widgets.OptionButton;

public class GuiUrlRequest extends Screen {
    private final String url;

    private final String msg;

    public GuiUrlRequest(String u) {
        this(u, "The map wants you to goto");
    }

    public GuiUrlRequest(String u, String m) {
        this.url = u;
        this.msg = m;
    }

    public static void showUI(String url) {
        Minecraft.minecraftInstance.a(new GuiUrlRequest(url));
    }

    public static void showUI(String url, String msg) {
        Minecraft.minecraftInstance.a(new GuiUrlRequest(url, msg));
    }

    @Override
    public void tick() {
    }

    @Override
    public void init() {
        this.buttons.add(new OptionButton(0, this.width / 2 - 75, this.height / 2 + 10, "Open URL"));
        this.buttons.add(new OptionButton(1, this.width / 2 - 75, this.height / 2 + 32, "Don't Open"));
    }

    @Override
    protected void buttonClicked(Button guibutton) {
        if (guibutton.id == 0) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE))
                try {
                    desktop.browse(new URI(this.url));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
        }
        this.minecraft.openScreen(null);
    }

    @Override
    public void render(int i, int j, float f) {
        fill(0, 0, this.width, this.height, -2147483648);
        drawTextWithShadow(this.textManager, this.msg, this.width / 2 - this.textManager.getTextWidth(this.msg) / 2, this.height / 2 - 15, 14737632);
        drawTextWithShadow(this.textManager, this.url, this.width / 2 - this.textManager.getTextWidth(this.url) / 2, this.height / 2, 14737632);
        super.render(i, j, f);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }
}
