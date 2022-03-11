package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.extensions.client.gui.ExScreen;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import io.github.ryuu.adventurecraft.util.MusicPlayer;
import net.minecraft.client.gui.Screen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class GuiMusicSheet extends Screen {

    private final String instrument;

    private final ArrayList<Integer> notesPlayed;

    private String notesPlayedString;

    private int spaceTaken;

    private String songPlayed;

    private long timeToFade;

    public GuiMusicSheet(String i) {
        this.instrument = i;
        this.notesPlayed = new ArrayList<>();
        this.notesPlayedString = "";
        this.songPlayed = null;
    }

    public static void showUI(String i) {
        AccessMinecraft.getInstance().openScreen(new GuiMusicSheet(i));
    }

    @Override
    public void tick() {
    }

    @Override
    public void init() {
    }

    @Override
    protected void keyPressed(char character, int key) {
        super.keyPressed(character, key);
        if (this.songPlayed == null && key >= 2 && key <= 11) {
            boolean isSharp = Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
            if (isSharp && (key == 2 || key == 4 || key == 5 || key == 6 || key == 8 || key == 9 || key == 11)) {
                if (this.spaceTaken + 25 >= 168) {
                    this.notesPlayed.clear();
                    this.notesPlayedString = "";
                    this.spaceTaken = 0;
                }
                this.notesPlayed.add(-key);
                this.spaceTaken += 14;
            }
            if (this.spaceTaken + 11 >= 168) {
                this.notesPlayed.clear();
                this.notesPlayedString = "";
                this.spaceTaken = 0;
            }
            this.spaceTaken += 11;
            this.notesPlayed.add(key);
            if (key == 2) {
                MusicPlayer.playNoteFromEntity(this.minecraft.level, this.minecraft.player, this.instrument, 'D', isSharp, 0.5f, 1.0f);
                this.notesPlayedString = this.notesPlayedString + character;
            } else if (key == 3) {
                MusicPlayer.playNoteFromEntity(this.minecraft.level, this.minecraft.player, this.instrument, 'E', false, 0.5f, 1.0f);
                this.notesPlayedString = this.notesPlayedString + "2";
            } else if (key == 4) {
                MusicPlayer.playNoteFromEntity(this.minecraft.level, this.minecraft.player, this.instrument, 'F', isSharp, 0.5f, 1.0f);
                this.notesPlayedString = this.notesPlayedString + character;
            } else if (key == 5) {
                MusicPlayer.playNoteFromEntity(this.minecraft.level, this.minecraft.player, this.instrument, 'G', isSharp, 0.5f, 1.0f);
                this.notesPlayedString = this.notesPlayedString + character;
            } else if (key == 6) {
                MusicPlayer.playNoteFromEntity(this.minecraft.level, this.minecraft.player, this.instrument, 'A', isSharp, 1.0f, 1.0f);
                this.notesPlayedString = this.notesPlayedString + character;
            } else if (key == 7) {
                MusicPlayer.playNoteFromEntity(this.minecraft.level, this.minecraft.player, this.instrument, 'B', false, 1.0f, 1.0f);
                this.notesPlayedString = this.notesPlayedString + "6";
            } else if (key == 8) {
                MusicPlayer.playNoteFromEntity(this.minecraft.level, this.minecraft.player, this.instrument, 'C', isSharp, 1.0f, 1.0f);
                this.notesPlayedString = this.notesPlayedString + character;
            } else if (key == 9) {
                MusicPlayer.playNoteFromEntity(this.minecraft.level, this.minecraft.player, this.instrument, 'D', isSharp, 1.0f, 1.0f);
                this.notesPlayedString = this.notesPlayedString + character;
            } else if (key == 10) {
                MusicPlayer.playNoteFromEntity(this.minecraft.level, this.minecraft.player, this.instrument, 'E', false, 1.0f, 1.0f);
                this.notesPlayedString = this.notesPlayedString + "9";
            } else if (key == 11) {
                MusicPlayer.playNoteFromEntity(this.minecraft.level, this.minecraft.player, this.instrument, 'F', isSharp, 1.0f, 1.0f);
                this.notesPlayedString = this.notesPlayedString + character;
            }
            String songName = ((ExLevel) this.minecraft.level).getMusicScripts().executeMusic(this.notesPlayedString);
            if (songName != null) {
                this.songPlayed = songName;
                this.timeToFade = 2500L + System.currentTimeMillis();
                this.minecraft.field_2778 = true;
                ((ExScreen) this).setDisableInputGrabbing(true);
                this.minecraft.field_2767.method_1970();
            }
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        float timeDelta;
        float alpha = 1.0f;
        int alpha2 = Integer.MIN_VALUE;
        int alpha3 = -16777216;
        if (this.songPlayed != null && (timeDelta = (float) (this.timeToFade - System.currentTimeMillis()) / 1000.0f) < 1.0f) {
            alpha = timeDelta;
            alpha2 = (int) (128.0f * timeDelta) << 24;
            alpha3 = (int) (255.0f * timeDelta) << 24;
            if ((double) timeDelta < 0.004) {
                this.minecraft.openScreen(null);
                return;
            }
        }
        this.fill((this.width - 215) / 2, this.height - 59 - 4 - 48, (this.width + 215) / 2, this.height - 48, alpha2);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3008);
        if (this.songPlayed != null) {
            this.drawTextWithShadowCentred(this.textManager, this.songPlayed, this.width / 2, this.height - 59 - 48, 0xE0E0E0 + alpha3);
        }
        int t = this.minecraft.textureManager.getTextureId("/gui/musicSheet.png");
        GL11.glColor4f(0.9f, 0.1f, 0.1f, alpha);
        this.minecraft.textureManager.bindTexture(t);
        this.blit((this.width - 205) / 2, this.height - 59 - 2 - 48, 0, 0, 205, 59);
        int x = 0;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
        for (Integer note : this.notesPlayed) {
            if (note > 0) {
                this.drawNote(x, note);
                x += 11;
                continue;
            }
            this.drawSharp(x, -note);
            x += 14;
        }
        GL11.glEnable(3008);
        GL11.glDisable(3042);
        super.render(mouseX, mouseY, delta);
    }

    private void drawNote(int x, int note) {
        this.blit((this.width - 205) / 2 + 36 + x, this.height - 59 - 2 - 48 + 46 - (note - 2) * 4, 0, 64, 9, 7);
    }

    private void drawSharp(int x, int note) {
        this.blit((this.width - 205) / 2 + 36 + x, this.height - 59 - 2 - 48 + 46 - (note - 2) * 4 - 5, 16, 64, 12, 17);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
