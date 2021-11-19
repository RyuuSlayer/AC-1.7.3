package io.github.ryuu.adventurecraft.util;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.awt.image.BufferedImage
 *  java.io.BufferedReader
 *  java.io.File
 *  java.io.FileNotFoundException
 *  java.io.FileReader
 *  java.io.IOException
 *  java.io.Reader
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 *  javax.imageio.ImageIO
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.src.MapInfo
 */
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.MapInfo;

public class MapList {

    private List<MapInfo> availableMaps = new ArrayList();

    private File mapDir;

    public MapList(File file) {
        this.mapDir = new File(file, "../maps");
        if (!this.mapDir.exists()) {
            this.mapDir.mkdirs();
        }
        this.findMaps();
    }

    public void findMaps() {
        ArrayList arraylist = new ArrayList();
        if (this.mapDir.exists() && this.mapDir.isDirectory()) {
            File[] afile;
            for (File file : afile = this.mapDir.listFiles()) {
                if (!file.isDirectory())
                    continue;
                String mapName = file.getName();
                String description1 = "";
                String description2 = "";
                File infoFile = new File(file, "description.txt");
                try {
                    BufferedReader input = new BufferedReader((Reader) new FileReader(infoFile));
                    description1 = input.readLine();
                    description2 = input.readLine();
                } catch (FileNotFoundException e) {
                } catch (IOException e) {
                    // empty catch block
                }
                File thumbnailFile = new File(file, "thumbnail.png");
                BufferedImage thumbnail = null;
                try {
                    thumbnail = ImageIO.read((File) thumbnailFile);
                } catch (FileNotFoundException e) {
                } catch (IOException e) {
                    // empty catch block
                }
                arraylist.add((Object) new MapInfo(mapName, description1, description2, thumbnail));
            }
        }
        this.availableMaps = arraylist;
    }

    public List<MapInfo> availableMaps() {
        return new ArrayList(this.availableMaps);
    }
}
