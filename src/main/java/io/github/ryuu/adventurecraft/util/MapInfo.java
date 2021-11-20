package io.github.ryuu.adventurecraft.util;

import java.awt.image.BufferedImage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class MapInfo {

    public String name;

    public String description1;

    public String description2;

    private BufferedImage mapThumbnail;

    private int textureID = -1;

    MapInfo(String mapName, String mapDescription1, String mapDescription2, BufferedImage thumbnail) {
        this.name = mapName;
        this.description1 = mapDescription1;
        this.description2 = mapDescription2;
        if (this.description1 == null) {
            this.description1 = "";
        }
        if (this.description2 == null) {
            this.description2 = "";
        }
        this.mapThumbnail = thumbnail;
    }

    public void bindTexture(Minecraft minecraft) {
        if (this.mapThumbnail != null && this.textureID < 0) {
            this.textureID = minecraft.textureManager.glLoadImage(this.mapThumbnail);
        }
        if (this.mapThumbnail != null) {
            minecraft.textureManager.bindTexture(this.textureID);
        } else {
            GL11.glBindTexture((int) 3553, (int) minecraft.textureManager.getTextureId("/gui/unknown_pack.png"));
        }
    }
}
