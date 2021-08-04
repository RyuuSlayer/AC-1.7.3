package io.github.ryuu.adventurecraft.gui;

import java.util.ArrayList;

import io.github.ryuu.adventurecraft.util.MusicPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

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

    @Override
    public void tick() {
    }

    @Override
    public void init() {
    }

    @Override
    protected void keyPressed(char c, int i) {
        super.keyPressed(c, i);
        if (this.songPlayed == null && i >= 2 && i <= 11) {
            boolean isSharp = (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
            if (isSharp && (i == 2 || i == 4 || i == 5 || i == 6 || i == 8 || i == 9 || i == 11)) {
                if (this.spaceTaken + 25 >= 168) {
                    this.notesPlayed.clear();
                    this.notesPlayedString = "";
                    this.spaceTaken = 0;
                }
                this.notesPlayed.add(-i);
                this.spaceTaken += 14;
            }
            if (this.spaceTaken + 11 >= 168) {
                this.notesPlayed.clear();
                this.notesPlayedString = "";
                this.spaceTaken = 0;
            }
            this.spaceTaken += 11;
            this.notesPlayed.add(i);
            if (i == 2) {
                MusicPlayer.playNoteFromEntity(this.minecraft.level, this.minecraft.player, this.instrument, 'D', isSharp, 0.5F, 1.0F);
                this.notesPlayedString += c;
            } else if (i == 3) {
                MusicPlayer.playNoteFromEntity(this.minecraft.level, this.minecraft.player, this.instrument, 'E', false, 0.5F, 1.0F);
                this.notesPlayedString += "2";
            } else if (i == 4) {
                MusicPlayer.playNoteFromEntity(this.minecraft.level, this.minecraft.player, this.instrument, 'F', isSharp, 0.5F, 1.0F);
                this.notesPlayedString += c;
            } else if (i == 5) {
                MusicPlayer.playNoteFromEntity(this.minecraft.level, this.minecraft.player, this.instrument, 'G', isSharp, 0.5F, 1.0F);
                this.notesPlayedString += c;
            } else if (i == 6) {
                MusicPlayer.playNoteFromEntity(this.minecraft.level, this.minecraft.player, this.instrument, 'A', isSharp, 1.0F, 1.0F);
                this.notesPlayedString += c;
            } else if (i == 7) {
                MusicPlayer.playNoteFromEntity(this.minecraft.level, this.minecraft.player, this.instrument, 'B', false, 1.0F, 1.0F);
                this.notesPlayedString += "6";
            } else if (i == 8) {
                MusicPlayer.playNoteFromEntity(this.minecraft.level, this.minecraft.player, this.instrument, 'C', isSharp, 1.0F, 1.0F);
                this.notesPlayedString += c;
            } else if (i == 9) {
                MusicPlayer.playNoteFromEntity(this.minecraft.level, this.minecraft.player, this.instrument, 'D', isSharp, 1.0F, 1.0F);
                this.notesPlayedString += c;
            } else if (i == 10) {
                MusicPlayer.playNoteFromEntity(this.minecraft.level, this.minecraft.player, this.instrument, 'E', false, 1.0F, 1.0F);
                this.notesPlayedString += "9";
            } else if (i == 11) {
                MusicPlayer.playNoteFromEntity(this.minecraft.level, this.minecraft.player, this.instrument, 'F', isSharp, 1.0F, 1.0F);
                this.notesPlayedString += c;
            }
            String songName = this.minecraft.level.musicScripts.executeMusic(this.notesPlayedString);
            if (songName != null) {
                this.songPlayed = songName;
                this.timeToFade = 2500L + System.currentTimeMillis();
                this.minecraft.field_2778 = true;
                this.disableInputGrabbing = true;
                this.minecraft.field_2767.method_1970();
            }
        }
    }

    @Override
    public void render(int i, int j, float f) {
        float alpha = 1.0F;
        int alpha2 = Integer.MIN_VALUE;
        int alpha3 = -16777216;
        if (this.songPlayed != null) {
            float timeDelta = (float) (this.timeToFade - System.currentTimeMillis()) / 1000.0F;
            if (timeDelta < 1.0F) {
                alpha = timeDelta;
                alpha2 = (int) (128.0F * timeDelta) << 24;
                alpha3 = (int) (255.0F * timeDelta) << 24;
                if (timeDelta < 0.004D) {
                    this.minecraft.openScreen(null);
                    return;
                }
            }
        }
        fill((this.width - 215) / 2, this.height - 59 - 4 - 48, (this.width + 215) / 2, this.height - 48, alpha2);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3008);
        if (this.songPlayed != null)
            drawTextWithShadowCentred(this.textManager, this.songPlayed, this.width / 2, this.height - 59 - 48, 14737632 + alpha3);
        int t = this.minecraft.textureManager.getTextureId("/gui/musicSheet.png");
        GL11.glColor4f(0.9F, 0.1F, 0.1F, alpha);
        this.minecraft.textureManager.bindTexture(t);
        blit((this.width - 205) / 2, this.height - 59 - 2 - 48, 0, 0, 205, 59);
        int x = 0;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
        for (Integer note : this.notesPlayed) {
            if (note > 0) {
                drawNote(x, note);
                x += 11;
                continue;
            }
            drawSharp(x, -note);
            x += 14;
        }
        GL11.glEnable(3008);
        GL11.glDisable(3042);
        super.render(i, j, f);
    }

    private void drawNote(int x, int note) {
        blit((this.width - 205) / 2 + 36 + x, this.height - 59 - 2 - 48 + 46 - (note - 2) * 4, 0, 64, 9, 7);
    }

    private void drawSharp(int x, int note) {
        blit((this.width - 205) / 2 + 36 + x, this.height - 59 - 2 - 48 + 46 - (note - 2) * 4 - 5, 16, 64, 12, 17);
    }

    public static void showUI(String i) {
        Minecraft.minecraftInstance.a(new GuiMusicSheet(i));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}