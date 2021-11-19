package io.github.ryuu.adventurecraft.mixin.client.gui.screen;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import io.github.ryuu.adventurecraft.gui.GuiMapDownload;
import io.github.ryuu.adventurecraft.gui.GuiMapSelect;
import io.github.ryuu.adventurecraft.scripting.ScriptModel;
import io.github.ryuu.adventurecraft.util.Version;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.util.maths.MathsHelper;
import org.lwjgl.opengl.GL11;

public class MixinTitleScreen extends Screen {
    private static final Random RANDOM = new Random();
    private float ticksOpened;
    private String splashMessage;
    private Button multiplayerButton;

    public MixinTitleScreen() {
        ScriptModel.clearAll();
        Minecraft.minecraftInstance.soundHelper.stopMusic();
        this.ticksOpened = 0.0f;
        this.splashMessage = "missingno";
        try {
            String s1;
            ArrayList arraylist = new ArrayList();
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(TitleScreen.class.getResourceAsStream("/title/splashes.txt"), Charset.forName((String)"UTF-8")));
            String s = "";
            while ((s1 = bufferedreader.readLine()) != null) {
                if ((s1 = s1.trim()).length() <= 0) continue;
                arraylist.add(s1);
            }
            this.splashMessage = (String)arraylist.get(RANDOM.nextInt(arraylist.size()));
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void tick() {
        this.ticksOpened += 1.0f;
    }

    protected void keyPressed(char character, int key) {
    }

    public void init() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (calendar.get(2) + 1 == 11 && calendar.get(5) == 9) {
            this.splashMessage = "Happy birthday, ez!";
        } else if (calendar.get(2) + 1 == 6 && calendar.get(5) == 1) {
            this.splashMessage = "Happy birthday, Notch!";
        } else if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24) {
            this.splashMessage = "Merry X-mas!";
        } else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1) {
            this.splashMessage = "Happy new year!";
        }
        this.splashMessage = "A Minecraft Total Conversion!";
        TranslationStorage stringtranslate = TranslationStorage.getInstance();
        int i = this.height / 4 + 48;
        this.buttons.add(new Button(6, this.width / 2 - 100, i, "New Save"));
        this.buttons.add(new Button(1, this.width / 2 - 100, i + 22, "Load Save"));
        this.buttons.add(new Button(7, this.width / 2 - 100, i + 44, "Craft a Map"));
        this.buttons.add(new Button(5, this.width / 2 - 100, i + 66, "Download a Map"));
        if (this.minecraft.isApplet) {
            this.buttons.add(new Button(0, this.width / 2 - 100, i + 88, stringtranslate.translate("menu.options")));
        } else {
            this.buttons.add(new Button(0, this.width / 2 - 100, i + 88 + 11, 98, 20, stringtranslate.translate("menu.options")));
            this.buttons.add(new Button(4, this.width / 2 + 2, i + 88 + 11, 98, 20, stringtranslate.translate("menu.quit")));
        }
        if (this.minecraft.session == null) {
            this.multiplayerButton.active = false;
        }
    }

    protected void buttonClicked(Button button) {
        if (button.id == 0) {
            this.minecraft.openScreen(new OptionsScreen(this, this.minecraft.options));
        } else if (button.id == 1) {
            this.minecraft.openScreen(new SelectWorldScreen(this));
        } else if (button.id == 2) {
            this.minecraft.openScreen(new MultiplayerScreen(this));
        } else if (button.id == 3) {
            this.minecraft.openScreen(new TexturePackScreen(this));
        } else if (button.id == 4) {
            this.minecraft.scheduleStop();
        } else if (button.id == 5) {
            this.minecraft.openScreen(new GuiMapDownload(this));
        } else if (button.id == 6) {
            this.minecraft.openScreen(new GuiMapSelect(this, ""));
        } else if (button.id == 7) {
            this.minecraft.openScreen(new GuiMapSelect(this, null));
        }
    }

    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        Tessellator tessellator = Tessellator.INSTANCE;
        int c = 320;
        int k = this.width / 2 - c / 2;
        int byte0 = 30;
        GL11.glBindTexture(3553, this.minecraft.textureManager.getTextureId("/acLogo.png"));
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.blit(k + 0, byte0 + 0, 0, 0, 256, 31);
        this.blit(k + 256, byte0 + 0, 0, 128, 64, 31);
        tessellator.colour(0xFFFFFF);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.width / 2 + 90), 70.0f, 0.0f);
        GL11.glRotatef(-20.0f, 0.0f, 0.0f, 1.0f);
        float f1 = 1.8f - MathsHelper.abs(MathsHelper.sin((float)(System.currentTimeMillis() % 1000L) / 1000.0f * 3.141593f * 2.0f) * 0.1f);
        f1 = f1 * 100.0f / (float)(this.textManager.getTextWidth(this.splashMessage) + 32);
        GL11.glScalef(f1, f1, f1);
        this.drawTextWithShadowCentred(this.textManager, this.splashMessage, 0, -8, 0xFFFF00);
        GL11.glPopMatrix();
        this.drawTextWithShadow(this.textManager, Version.version, 2, 2, 0x505050);
        String s = "Copyright Mojang AB. Do not distribute.";
        this.drawTextWithShadow(this.textManager, s, this.width - this.textManager.getTextWidth(s) - 2, this.height - 10, 0xFFFFFF);
        super.render(mouseX, mouseY, delta);
    }
}