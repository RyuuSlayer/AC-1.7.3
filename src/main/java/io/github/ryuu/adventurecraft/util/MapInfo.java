package io.github.ryuu.adventurecraft.util;

import java.awt.image.BufferedImage;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class MapInfo {
    private final BufferedImage mapThumbnail;
    public String name;
    public String description1;
    public String description2;
    private int textureID;

    MapInfo(String mapName, String mapDescription1, String mapDescription2, BufferedImage thumbnail) {
        this.textureID = -1;
        this.name = mapName;
        this.description1 = mapDescription1;
        this.description2 = mapDescription2;
        if (this.description1 == null)
            this.description1 = "";
        if (this.description2 == null)
            this.description2 = "";
        this.mapThumbnail = thumbnail;
    }

    public void bindTexture(Minecraft minecraft) {
        if (this.mapThumbnail != null && this.textureID < 0)
            this.textureID = minecraft.p.a(this.mapThumbnail);
        if (this.mapThumbnail != null) {
            minecraft.p.b(this.textureID);
        } else {
            GL11.glBindTexture(3553, minecraft.p.b("/gui/unknown_pack.png"));
        }
    }
}
