package io.github.ryuu.adventurecraft.mixin.tile.entity;

import net.minecraft.packet.AbstractPacket;
import net.minecraft.packet.play.UpdateSignPacket;
import net.minecraft.tile.entity.Sign;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Sign.class)
public class MixinSign extends MixinTileEntity {

    @Shadow()
    public String[] lines = new String[]{"", "", "", ""};

    public int field_2270 = -1;
    public boolean playSong;
    public String instrument;
    public int onNote;
    public int tickSinceStart;
    private boolean field_2271 = true;

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void writeIdentifyingData(MixinCompoundTag tag) {
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
    public void readIdentifyingData(MixinCompoundTag tag) {
        this.field_2271 = false;
        super.readIdentifyingData(tag);
        for (int i = 0; i < 4; ++i) {
            this.lines[i] = tag.getString("Text" + (i + 1));
            if (this.lines[i].length() <= 15) continue;
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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public AbstractPacket createInitialChunkDataPacket() {
        String[] stringArray = new String[4];
        for (int i = 0; i < 4; ++i) {
            stringArray[i] = this.lines[i];
        }
        return new UpdateSignPacket(this.x, this.y, this.z, stringArray);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_1810() {
        return this.field_2271;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1811(boolean bl) {
        this.field_2271 = bl;
    }
}
