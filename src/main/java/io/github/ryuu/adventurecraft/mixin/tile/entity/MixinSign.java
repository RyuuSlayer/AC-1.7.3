package io.github.ryuu.adventurecraft.mixin.tile.entity;

import io.github.ryuu.adventurecraft.extensions.tile.entity.ExSign;
import io.github.ryuu.adventurecraft.util.MusicPlayer;
import net.minecraft.tile.entity.Sign;
import net.minecraft.tile.entity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Sign.class)
public abstract class MixinSign extends TileEntity implements ExSign {

    @Shadow
    public String[] lines;

    public boolean playSong;
    public String instrument;
    public int onNote;
    public int tickSinceStart;

    @Override
    public void tick() {
        if (this.playSong) {
            if (this.tickSinceStart % 10 == 0) {
                String song = this.lines[0] + this.lines[1] + this.lines[2] + this.lines[3];
                if (this.onNote < MusicPlayer.countNotes(song)) {
                    MusicPlayer.playNoteFromSong(this.level, this.x, this.y, this.z, this.instrument, song, this.onNote, 1.0f);
                    ++this.onNote;
                } else {
                    this.playSong = false;
                }
            }
            ++this.tickSinceStart;
        }
    }

    @Override
    public void playSong(String useInstrument) {
        this.playSong = true;
        this.instrument = useInstrument;
        this.tickSinceStart = 0;
        this.onNote = 0;
    }
}
