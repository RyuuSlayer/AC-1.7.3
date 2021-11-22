package io.github.ryuu.adventurecraft.mixin.tile.entity;

import io.github.ryuu.adventurecraft.util.MusicPlayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.packet.AbstractPacket;
import net.minecraft.packet.play.UpdateSignPacket;
import net.minecraft.tile.entity.Sign;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Sign.class)
public class MixinSign extends TileEntity {

    @Shadow()
    public String[] lines = new String[] { "", "", "", "" };

    public int field_2270 = -1;

    private boolean field_2271 = true;

    public boolean playSong;

    public String instrument;

    public int onNote;

    public int tickSinceStart;

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void writeIdentifyingData(CompoundTag tag) {
        super.writeIdentifyingData(tag);
        tag.put("Text1", this.lines[0]);
        tag.put("Text2", this.lines[1]);
        tag.put("Text3", this.lines[2]);
        tag.put("Text4", this.lines[3]);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void readIdentifyingData(CompoundTag tag) {
        this.field_2271 = false;
        super.readIdentifyingData(tag);
        for (int i = 0; i < 4; ++i) {
            this.lines[i] = tag.getString("Text" + (i + 1));
            if (this.lines[i].length() <= 15)
                continue;
            this.lines[i] = this.lines[i].substring(0, 15);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void playSong(String useInstrument) {
        this.playSong = true;
        this.instrument = useInstrument;
        this.tickSinceStart = 0;
        this.onNote = 0;
    }
}
