package io.github.ryuu.adventurecraft.gui;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.script.ScriptUIContainer;
import net.minecraft.script.ScriptUILabel;
import net.minecraft.script.ScriptUIRect;
import net.minecraft.script.ScriptUISprite;

public class GuiMapElement extends ScriptUIContainer {

    ScriptUIRect topBack;

    ScriptUIRect ratingBack;

    ScriptUISprite ratingBar;

    ScriptUIRect botFadeBack;

    ScriptUISprite background;

    ScriptUILabel[] descriptions;

    ScriptUIRect topFadeBack;

    ScriptUILabel[] topFadeText;

    long fadeTimePrev;

    boolean fadeIn = false;

    boolean fadeOut = false;

    public String mapName;

    public String mapURL;

    int mapID;

    int totalRating = 0;

    int numRatings = 0;

    int voted = 0;

    boolean hoveringOverRating = false;

    private boolean downloaded;

    public GuiMapElement(int xPos, int yPos, ScriptUIContainer p, String mName, String topDescription, String description, String texture, String mURL, int mID, int tRating, int nRatings) {
        super(xPos, yPos, p);
        String[] labels;
        ArrayList splitLines = new ArrayList();
        this.background = new ScriptUISprite(texture, 0.0f, 0.0f, 100.0f, 100.0f, 0.0, 0.0, this);
        this.topBack = new ScriptUIRect(0.0f, 0.0f, 100.0f, 12.0f, 0.0f, 0.0f, 0.0f, 0.5f, this);
        this.mapID = mID;
        this.ratingBack = new ScriptUIRect(0.0f, 85.0f, 100.0f, 15.0f, 0.0f, 0.0f, 0.0f, 0.5f, this);
        this.ratingBar = new ScriptUISprite("/misc/adventureCraftMisc.png", 0.0f, 86.0f, 64.0f, 13.0f, 0.0, 0.0, this);
        this.totalRating = tRating;
        this.numRatings = nRatings;
        this.updateRatingBar();
        int i = 0;
        for (String label : labels = mName.split("\n")) {
            splitLines.clear();
            this.getLines(label, (List<String>) splitLines);
            for (String line : splitLines) {
                new ScriptUILabel(line, 2.0f, 2 + i * 10, this);
                ++i;
            }
        }
        this.topBack.height = 2 + 10 * i;
        i = 0;
        labels = description.split("\n");
        this.botFadeBack = new ScriptUIRect(0.0f, 0.0f, 100.0f, 12.0f, 0.0f, 0.0f, 0.0f, 0.0f, this);
        this.botFadeBack.height = 2 + 10 * labels.length;
        this.botFadeBack.setY(98 - (labels.length - i) * 10 - 13);
        this.descriptions = new ScriptUILabel[labels.length];
        for (String label : labels) {
            this.descriptions[i] = new ScriptUILabel(label, 2.0f, 100 - (labels.length - i) * 10 - 13, this);
            this.descriptions[i].alpha = 0.0f;
            ++i;
        }
        if (description.equals((Object) "")) {
            this.botFadeBack.removeFromScreen();
        }
        i = 0;
        labels = topDescription.split("\n");
        this.topFadeBack = new ScriptUIRect(0.0f, this.topBack.height, 100.0f, 12.0f, 0.0f, 0.0f, 0.0f, 0.0f, this);
        this.topFadeBack.height = 10 * labels.length;
        this.topFadeText = new ScriptUILabel[labels.length];
        for (String label : labels) {
            this.topFadeText[i] = new ScriptUILabel(label, 2.0f, (int) ((float) (i * 10) + this.topFadeBack.curY), this);
            this.topFadeText[i].alpha = 0.0f;
            ++i;
        }
        if (topDescription.equals((Object) "")) {
            this.topFadeBack.removeFromScreen();
        }
        this.background.imageHeight = 100.0f;
        this.background.imageWidth = 100.0f;
        this.mapName = mName.replace((CharSequence) "\n", (CharSequence) " ");
        this.mapURL = mURL;
    }

    void updateRatingBar() {
        if (this.voted != 0) {
            float rating = 12.8f * (float) this.voted;
            this.ratingBar.width = Math.round((float) rating);
        } else if (this.numRatings == 0) {
            this.ratingBar.width = 0.0f;
        } else {
            float rating = 12.8f * (float) this.totalRating / (float) this.numRatings;
            this.ratingBar.width = Math.round((float) rating);
        }
    }

    void mouseMoved(int mouseX, int mouseY) {
        if (0 <= mouseX && mouseX <= 64 && 85 < mouseY && mouseY < 100) {
            this.ratingBar.width = Math.min((int) ((mouseX / 13 + 1) * 13), (int) 64);
            this.hoveringOverRating = true;
        } else if (this.hoveringOverRating) {
            this.updateRatingBar();
            this.hoveringOverRating = false;
        }
    }

    void ratingClicked(int mouseX, int mouseY) {
        if (this.voted != 0) {
            this.totalRating -= this.voted;
            --this.numRatings;
        }
        this.voted = mouseX / 13 + 1;
        SwingUtilities.invokeLater((Runnable) new Runnable() {

            public void run() {
                try {
                    URL url = new URL(String.format((String) "http://www.adventurecraft.org/cgi-bin/vote.py?mapID=%d&rating=%d", (Object[]) new Object[] { GuiMapElement.this.mapID, GuiMapElement.this.voted }));
                    URLConnection urlconnection = url.openConnection();
                    urlconnection.connect();
                    urlconnection.getInputStream();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void getLines(String text, List<String> lines) {
        String[] parts = text.split(" ");
        String curLine = "";
        for (String part : parts) {
            if (curLine.equals((Object) "")) {
                curLine = part;
                continue;
            }
            String potential = curLine + " " + part;
            if (Minecraft.minecraftInstance.textRenderer.getTextWidth(potential) > 100) {
                lines.add((Object) curLine);
                curLine = part;
                continue;
            }
            curLine = potential;
        }
        if (!curLine.equals((Object) "")) {
            lines.add((Object) curLine);
        }
    }

    void fadeDescriptionIn() {
        this.fadeIn = true;
        this.fadeOut = false;
        this.fadeTimePrev = System.nanoTime();
    }

    void fadeDescriptionOut() {
        this.fadeIn = false;
        this.fadeOut = true;
        this.fadeTimePrev = System.nanoTime();
    }

    @Override
    public void render(MixinTextRenderer fontRenderer, MixinTextureManager renderEngine, float partialTickTime) {
        long fadeCurTime;
        long fadeDiff;
        float fadeChange;
        if ((this.fadeIn || this.fadeOut) && (fadeChange = (float) (2L * (fadeDiff = (fadeCurTime = System.nanoTime()) - this.fadeTimePrev)) / 1.0E9f) != 0.0f) {
            this.fadeTimePrev = fadeCurTime;
            for (ScriptUILabel desc : this.descriptions) {
                desc.alpha = this.fadeIn ? (desc.alpha += fadeChange) : (desc.alpha -= fadeChange);
                desc.alpha = Math.max((float) Math.min((float) desc.alpha, (float) 1.0f), (float) 0.0f);
            }
            for (ScriptUILabel desc : this.topFadeText) {
                desc.alpha = this.fadeIn ? (desc.alpha += fadeChange) : (desc.alpha -= fadeChange);
                desc.alpha = Math.max((float) Math.min((float) desc.alpha, (float) 1.0f), (float) 0.0f);
            }
            if (this.fadeIn) {
                this.botFadeBack.alpha += fadeChange / 2.0f;
                this.topFadeBack.alpha += fadeChange / 2.0f;
            } else {
                this.botFadeBack.alpha -= fadeChange / 2.0f;
                this.topFadeBack.alpha -= fadeChange / 2.0f;
            }
            this.botFadeBack.alpha = Math.max((float) Math.min((float) this.botFadeBack.alpha, (float) 0.5f), (float) 0.0f);
            this.topFadeBack.alpha = Math.max((float) Math.min((float) this.topFadeBack.alpha, (float) 0.5f), (float) 0.0f);
            if (this.botFadeBack.alpha <= 0.0f || this.botFadeBack.alpha >= 0.5f) {
                this.fadeIn = false;
                this.fadeOut = false;
            }
        }
        super.render(fontRenderer, renderEngine, partialTickTime);
    }

    public void setAsDownloaded() {
        if (!this.downloaded) {
            int stringLength = Minecraft.minecraftInstance.textRenderer.getTextWidth("Downloaded");
            new ScriptUIRect(0.0f, 0.0f, 100.0f, 100.0f, 0.0f, 0.0f, 0.0f, 0.5f, this);
            new ScriptUILabel("Downloaded", 50 - stringLength / 2, 46.0f, this);
            this.downloaded = true;
        }
    }
}
