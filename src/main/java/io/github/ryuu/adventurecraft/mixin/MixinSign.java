package io.github.ryuu.adventurecraft.mixin;

import io.github.ryuu.adventurecraft.util.MusicPlayer;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;

public class MixinSign extends TileEntity {
    public int b = -1;
    public String[] a = new String[]{"", "", "", ""};
    public boolean playSong;
    public String instrument;
    public int onNote;
    public int tickSinceStart;
    private boolean c = true;

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Text1", this.a[0]);
        nbttagcompound.a("Text2", this.a[1]);
        nbttagcompound.a("Text3", this.a[2]);
        nbttagcompound.a("Text4", this.a[3]);
    }

    public void a(CompoundTag nbttagcompound) {
        this.c = false;
        super.a(nbttagcompound);
        for (int i = 0; i < 4; i++) {
            this.a[i] = nbttagcompound.i("Text" + (i + 1));
            if (this.a[i].length() > 15)
                this.a[i] = this.a[i].substring(0, 15);
        }
    }

    public void n_() {
        if (this.playSong) {
            if (this.tickSinceStart % 10 == 0) {
                String song = this.a[0] + this.a[1] + this.a[2] + this.a[3];
                if (this.onNote < MusicPlayer.countNotes(song)) {
                    MusicPlayer.playNoteFromSong(this.d, this.e, this.f, this.g, this.instrument, song, this.onNote, 1.0F);
                    this.onNote++;
                } else {
                    this.playSong = false;
                }
            }
            this.tickSinceStart++;
        }
    }

    public void playSong(String useInstrument) {
        this.playSong = true;
        this.instrument = useInstrument;
        this.tickSinceStart = 0;
        this.onNote = 0;
    }
}
