package io.github.ryuu.adventurecraft.extensions.client;

public interface ExModel {
    int getTWidth();

    void setTWidth(int w);

    int getTHeight();

    void setTHeight(int h);

    void setDimensions(int w, int h);

    void addBoxInverted(float f, float f1, float f2, int i, int j, int k, float f3);
}
