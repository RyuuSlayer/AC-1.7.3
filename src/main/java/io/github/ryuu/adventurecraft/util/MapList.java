package io.github.ryuu.adventurecraft.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class MapList {
    private final File mapDir;
    private List<MapInfo> availableMaps;

    public MapList(File file) {
        this.availableMaps = new ArrayList<>();
        this.mapDir = new File(file, "../maps");
        if (!this.mapDir.exists())
            this.mapDir.mkdirs();
        findMaps();
    }

    public void findMaps() {
        ArrayList<MapInfo> arraylist = new ArrayList<>();
        if (this.mapDir.exists() && this.mapDir.isDirectory()) {
            File[] afile = this.mapDir.listFiles();
            File[] afile1 = afile;
            int i = afile1.length;
            for (int j = 0; j < i; j++) {
                File file = afile1[j];
                if (file.isDirectory()) {
                    String mapName = file.getName();
                    String description1 = "";
                    String description2 = "";
                    File infoFile = new File(file, "description.txt");
                    try {
                        BufferedReader input = new BufferedReader(new FileReader(infoFile));
                        description1 = input.readLine();
                        description2 = input.readLine();
                    } catch (FileNotFoundException e) {

                    } catch (IOException e) {
                    }
                    File thumbnailFile = new File(file, "thumbnail.png");
                    BufferedImage thumbnail = null;
                    try {
                        thumbnail = ImageIO.read(thumbnailFile);
                    } catch (FileNotFoundException e) {

                    } catch (IOException e) {
                    }
                    arraylist.add(new MapInfo(mapName, description1, description2, thumbnail));
                }
            }
        }
        this.availableMaps = arraylist;
    }

    public List<MapInfo> availableMaps() {
        return new ArrayList<>(this.availableMaps);
    }
}
