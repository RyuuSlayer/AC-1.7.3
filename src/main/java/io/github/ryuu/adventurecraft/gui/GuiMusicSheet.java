package io.github.ryuu.adventurecraft.gui;

import java.util.ArrayList;

import io.github.ryuu.adventurecraft.util.MusicPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiMusicSheet extends Screen {
    private String instrument;

    private ArrayList<Integer> notesPlayed;

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

    public void a() {}

    public void b() {}

    protected void a(char c, int i) {
        super.a(c, i);
        if (this.songPlayed == null && i >= 2 && i <= 11) {
            boolean isSharp = (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
            if (isSharp && (i == 2 || i == 4 || i == 5 || i == 6 || i == 8 || i == 9 || i == 11)) {
                if (this.spaceTaken + 25 >= 168) {
                    this.notesPlayed.clear();
                    this.notesPlayedString = "";
                    this.spaceTaken = 0;
                }
                this.notesPlayed.add(new Integer(-i));
                this.spaceTaken += 14;
            }
            if (this.spaceTaken + 11 >= 168) {
                this.notesPlayed.clear();
                this.notesPlayedString = "";
                this.spaceTaken = 0;
            }
            this.spaceTaken += 11;
            this.notesPlayed.add(new Integer(i));
            if (i == 2) {
                MusicPlayer.playNoteFromEntity(this.b.f, this.b.h, this.instrument, 'D', isSharp, 0.5F, 1.0F);
                this.notesPlayedString += c;
            } else if (i == 3) {
                MusicPlayer.playNoteFromEntity(this.b.f, this.b.h, this.instrument, 'E', false, 0.5F, 1.0F);
                this.notesPlayedString += "2";
            } else if (i == 4) {
                MusicPlayer.playNoteFromEntity(this.b.f, this.b.h, this.instrument, 'F', isSharp, 0.5F, 1.0F);
                this.notesPlayedString += c;
            } else if (i == 5) {
                MusicPlayer.playNoteFromEntity(this.b.f, this.b.h, this.instrument, 'G', isSharp, 0.5F, 1.0F);
                this.notesPlayedString += c;
            } else if (i == 6) {
                MusicPlayer.playNoteFromEntity(this.b.f, this.b.h, this.instrument, 'A', isSharp, 1.0F, 1.0F);
                this.notesPlayedString += c;
            } else if (i == 7) {
                MusicPlayer.playNoteFromEntity(this.b.f, this.b.h, this.instrument, 'B', false, 1.0F, 1.0F);
                this.notesPlayedString += "6";
            } else if (i == 8) {
                MusicPlayer.playNoteFromEntity(this.b.f, this.b.h, this.instrument, 'C', isSharp, 1.0F, 1.0F);
                this.notesPlayedString += c;
            } else if (i == 9) {
                MusicPlayer.playNoteFromEntity(this.b.f, this.b.h, this.instrument, 'D', isSharp, 1.0F, 1.0F);
                this.notesPlayedString += c;
            } else if (i == 10) {
                MusicPlayer.playNoteFromEntity(this.b.f, this.b.h, this.instrument, 'E', false, 1.0F, 1.0F);
                this.notesPlayedString += "9";
            } else if (i == 11) {
                MusicPlayer.playNoteFromEntity(this.b.f, this.b.h, this.instrument, 'F', isSharp, 1.0F, 1.0F);
                this.notesPlayedString += c;
            }
            String songName = this.b.f.musicScripts.executeMusic(this.notesPlayedString);
            if (songName != null) {
                this.songPlayed = songName;
                this.timeToFade = 2500L + System.currentTimeMillis();
                this.b.N = true;
                this.disableInputGrabbing = true;
                this.b.C.a();
            }
        }
    }

    public void a(int i, int j, float f) {
        float alpha = 1.0F;
        int alpha2 = Integer.MIN_VALUE;
        int alpha3 = -16777216;
        if (this.songPlayed != null) {
            float timeDelta = (float)(this.timeToFade - System.currentTimeMillis()) / 1000.0F;
            if (timeDelta < 1.0F) {
                alpha = timeDelta;
                alpha2 = (int)(128.0F * timeDelta) << 24;
                alpha3 = (int)(255.0F * timeDelta) << 24;
                if (timeDelta < 0.004D) {
                    this.b.a(null);
                    return;
                }
            }
        }
        a((this.c - 215) / 2, this.d - 59 - 4 - 48, (this.c + 215) / 2, this.d - 48, alpha2);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3008);
        if (this.songPlayed != null)
            a(this.g, this.songPlayed, this.c / 2, this.d - 59 - 48, 14737632 + alpha3);
        int t = this.b.p.b("/gui/musicSheet.png");
        GL11.glColor4f(0.9F, 0.1F, 0.1F, alpha);
        this.b.p.b(t);
        b((this.c - 205) / 2, this.d - 59 - 2 - 48, 0, 0, 205, 59);
        int x = 0;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
        for (Integer note : this.notesPlayed) {
            if (note.intValue() > 0) {
                drawNote(x, note.intValue());
                x += 11;
                continue;
            }
            drawSharp(x, -note.intValue());
            x += 14;
        }
        GL11.glEnable(3008);
        GL11.glDisable(3042);
        super.a(i, j, f);
    }

    private void drawNote(int x, int note) {
        b((this.c - 205) / 2 + 36 + x, this.d - 59 - 2 - 48 + 46 - (note - 2) * 4, 0, 64, 9, 7);
    }

    private void drawSharp(int x, int note) {
        b((this.c - 205) / 2 + 36 + x, this.d - 59 - 2 - 48 + 46 - (note - 2) * 4 - 5, 16, 64, 12, 17);
    }

    public static void showUI(String i) {
        Minecraft.minecraftInstance.a(new GuiMusicSheet(i));
    }

    public boolean c() {
        return false;
    }
}