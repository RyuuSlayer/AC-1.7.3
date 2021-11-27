package io.github.ryuu.adventurecraft.extensions.tile.entity;

public interface ExTileEntity {

    String getClassName();

    boolean isKilledFromSaving();

    void setKilledFromSaving(boolean killedFromSaving);
}
