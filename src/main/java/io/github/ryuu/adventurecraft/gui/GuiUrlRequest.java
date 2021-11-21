package io.github.ryuu.adventurecraft.gui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.gui.widgets.OptionButton;

public class GuiUrlRequest extends Screen {

    private String url;

    private String msg;

    public GuiUrlRequest(String u) {
        this(u, "The map wants you to goto");
    }

    public GuiUrlRequest(String u, String m) {
        this.url = u;
        this.msg = m;
    }

    @Override
    public void tick() {
    }

    @Override
    public void init() {
        this.buttons.add((Object) new OptionButton(0, this.width / 2 - 75, this.height / 2 + 10, "Open URL"));
        this.buttons.add((Object) new OptionButton(1, this.width / 2 - 75, this.height / 2 + 32, "Don't Open"));
    }

    @Override
    protected void buttonClicked(Button button) {
        Desktop desktop;
        if (button.id == 0 && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.BROWSE)) {
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
    public void render(int mouseX, int mouseY, float delta) {
        this.fill(0, 0, this.width, this.height, Integer.MIN_VALUE);
        this.drawTextWithShadow(this.textManager, this.msg, this.width / 2 - this.textManager.getTextWidth(this.msg) / 2, this.height / 2 - 15, 0xE0E0E0);
        this.drawTextWithShadow(this.textManager, this.url, this.width / 2 - this.textManager.getTextWidth(this.url) / 2, this.height / 2, 0xE0E0E0);
        super.render(mouseX, mouseY, delta);
    }

    public static void showUI(String url) {
        Minecraft.minecraftInstance.openScreen(new GuiUrlRequest(url));
    }

    public static void showUI(String url, String msg) {
        Minecraft.minecraftInstance.openScreen(new GuiUrlRequest(url, msg));
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }
}
