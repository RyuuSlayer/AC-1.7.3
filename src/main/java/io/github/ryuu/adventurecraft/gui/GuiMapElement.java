package io.github.ryuu.adventurecraft.gui;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;

import io.github.ryuu.adventurecraft.scripting.ScriptUIContainer;
import io.github.ryuu.adventurecraft.scripting.ScriptUILabel;
import io.github.ryuu.adventurecraft.scripting.ScriptUIRect;
import io.github.ryuu.adventurecraft.scripting.ScriptUISprite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.texture.TextureManager;

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

    boolean fadeIn;

    boolean fadeOut;

    public String mapName;

    public String mapURL;

    int mapID;

    int totalRating;

    int numRatings;

    int voted;

    boolean hoveringOverRating;

    private boolean downloaded;

    public GuiMapElement(int xPos, int yPos, ScriptUIContainer p, String mName, String topDescription, String description, String texture, String mURL, int mID, int tRating, int nRatings) {
        super(xPos, yPos, p);
        this.fadeIn = false;
        this.fadeOut = false;
        this.totalRating = 0;
        this.numRatings = 0;
        this.voted = 0;
        this.hoveringOverRating = false;
        List<String> splitLines = new ArrayList<>();
        this.background = new ScriptUISprite(texture, 0.0F, 0.0F, 100.0F, 100.0F, 0.0D, 0.0D, this);
        this.topBack = new ScriptUIRect(0.0F, 0.0F, 100.0F, 12.0F, 0.0F, 0.0F, 0.0F, 0.5F, this);
        this.mapID = mID;
        this.ratingBack = new ScriptUIRect(0.0F, 85.0F, 100.0F, 15.0F, 0.0F, 0.0F, 0.0F, 0.5F, this);
        this.ratingBar = new ScriptUISprite("/misc/adventureCraftMisc.png", 0.0F, 86.0F, 64.0F, 13.0F, 0.0D, 0.0D, this);
        this.totalRating = tRating;
        this.numRatings = nRatings;
        updateRatingBar();
        int i = 0;
        String[] labels = mName.split("\n");
        for (String label : labels) {
            splitLines.clear();
            getLines(label, splitLines);
            for (String line : splitLines) {
                new ScriptUILabel(line, 2.0F, (2 + i * 10), this);
                i++;
            }
        }
        this.topBack.height = (2 + 10 * i);
        i = 0;
        labels = description.split("\n");
        this.botFadeBack = new ScriptUIRect(0.0F, 0.0F, 100.0F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0F, this);
        this.botFadeBack.height = (2 + 10 * labels.length);
        this.botFadeBack.setY((98 - (labels.length - i) * 10 - 13));
        this.descriptions = new ScriptUILabel[labels.length];
        for (String label : labels) {
            this.descriptions[i] = new ScriptUILabel(label, 2.0F, (100 - (labels.length - i) * 10 - 13), this);
            (this.descriptions[i]).alpha = 0.0F;
            i++;
        }
        if (description.equals(""))
            this.botFadeBack.removeFromScreen();
        i = 0;
        labels = topDescription.split("\n");
        this.topFadeBack = new ScriptUIRect(0.0F, this.topBack.height, 100.0F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0F, this);
        this.topFadeBack.height = (10 * labels.length);
        this.topFadeText = new ScriptUILabel[labels.length];
        for (String label : labels) {
            this.topFadeText[i] = new ScriptUILabel(label, 2.0F, (int) ((i * 10) + this.topFadeBack.curY), this);
            (this.topFadeText[i]).alpha = 0.0F;
            i++;
        }
        if (topDescription.equals(""))
            this.topFadeBack.removeFromScreen();
        this.background.imageHeight = 100.0F;
        this.background.imageWidth = 100.0F;
        this.mapName = mName.replace("\n", " ");
        this.mapURL = mURL;
    }

    void updateRatingBar() {
        if (this.voted != 0) {
            float rating = 12.8F * this.voted;
            this.ratingBar.width = Math.round(rating);
        } else if (this.numRatings == 0) {
            this.ratingBar.width = 0.0F;
        } else {
            float rating = 12.8F * this.totalRating / this.numRatings;
            this.ratingBar.width = Math.round(rating);
        }
    }

    void mouseMoved(int mouseX, int mouseY) {
        if (0 <= mouseX && mouseX <= 64 && 85 < mouseY && mouseY < 100) {
            this.ratingBar.width = Math.min((mouseX / 13 + 1) * 13, 64);
            this.hoveringOverRating = true;
        } else if (this.hoveringOverRating) {
            updateRatingBar();
            this.hoveringOverRating = false;
        }
    }

    public void ratingClicked(int mouseX, int mouseY) {
        if (this.voted != 0) {
            this.totalRating -= this.voted;
            this.numRatings--;
        }
        this.voted = mouseX / 13 + 1;
        SwingUtilities.invokeLater(new Runnable() { // TODO: validate if this is the correct code (should be)
            public void run() {
                try {
                    URL url = new URL(String.format("http://www.adventurecraft.org/cgi-bin/vote.py?mapID=%d&rating=%d", GuiMapElement.this.mapID, GuiMapElement.this.voted));
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
            if (curLine.equals("")) {
                curLine = part;
            } else {
                String potential = curLine + " " + part;
                if (Minecraft.minecraftInstance.textRenderer.getTextWidth(potential) > 100) {
                    lines.add(curLine);
                    curLine = part;
                } else {
                    curLine = potential;
                }
            }
        }
        if (!curLine.equals(""))
            lines.add(curLine);
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
    public void render(TextRenderer fontRenderer, TextureManager renderEngine, float partialTickTime) {
        if (this.fadeIn || this.fadeOut) {
            long fadeCurTime = System.nanoTime();
            long fadeDiff = fadeCurTime - this.fadeTimePrev;
            float fadeChange = (float) (2L * fadeDiff) / 1.0E9F;
            if (fadeChange != 0.0F) {
                this.fadeTimePrev = fadeCurTime;
                for (ScriptUILabel desc : this.descriptions) {
                    if (this.fadeIn) {
                        desc.alpha += fadeChange;
                    } else {
                        desc.alpha -= fadeChange;
                    }
                    desc.alpha = Math.max(Math.min(desc.alpha, 1.0F), 0.0F);
                }
                for (ScriptUILabel desc : this.topFadeText) {
                    if (this.fadeIn) {
                        desc.alpha += fadeChange;
                    } else {
                        desc.alpha -= fadeChange;
                    }
                    desc.alpha = Math.max(Math.min(desc.alpha, 1.0F), 0.0F);
                }
                if (this.fadeIn) {
                    this.botFadeBack.alpha += fadeChange / 2.0F;
                    this.topFadeBack.alpha += fadeChange / 2.0F;
                } else {
                    this.botFadeBack.alpha -= fadeChange / 2.0F;
                    this.topFadeBack.alpha -= fadeChange / 2.0F;
                }
                this.botFadeBack.alpha = Math.max(Math.min(this.botFadeBack.alpha, 0.5F), 0.0F);
                this.topFadeBack.alpha = Math.max(Math.min(this.topFadeBack.alpha, 0.5F), 0.0F);
                if (this.botFadeBack.alpha <= 0.0F || this.botFadeBack.alpha >= 0.5F) {
                    this.fadeIn = false;
                    this.fadeOut = false;
                }
            }
        }
        super.render(fontRenderer, renderEngine, partialTickTime);
    }

    public void setAsDownloaded() {
        if (!this.downloaded) {
            int stringLength = Minecraft.minecraftInstance.textRenderer.getTextWidth("Downloaded");
            new ScriptUIRect(0.0F, 0.0F, 100.0F, 100.0F, 0.0F, 0.0F, 0.0F, 0.5F, this);
            new ScriptUILabel("Downloaded", (50 - stringLength / 2), 46.0F, this);
            this.downloaded = true;
        }
    }
}