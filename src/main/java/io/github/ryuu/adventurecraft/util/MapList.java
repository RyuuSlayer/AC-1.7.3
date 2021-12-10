package io.github.ryuu.adventurecraft.util;

import io.github.ryuu.adventurecraft.extensions.level.ExLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapList {

    private final File mapDir;
    private List<MapInfo> availableMaps = new ArrayList<>();

    public MapList(File file) {
        this.mapDir = new File(file, ExLevel.getAcMapDirectory());
        if (!this.mapDir.exists()) {
            this.mapDir.mkdirs();
        }
        this.findMaps();
    }

    public void findMaps() {
        ArrayList<MapInfo> arraylist = new ArrayList<>();
        if (this.mapDir.exists() && this.mapDir.isDirectory()) {
            File[] files = this.mapDir.listFiles();
            if (files == null)
                return;

            for (File file : files) {
                if (!file.isDirectory()) continue;
                String mapName = file.getName();
                String description1 = "";
                String description2 = "";
                File infoFile = new File(file, "description.txt");
                try {
                    BufferedReader input = new BufferedReader(new FileReader(infoFile));
                    description1 = input.readLine();
                    description2 = input.readLine();
                } catch (IOException e) {
                }
                File thumbnailFile = new File(file, "thumbnail.png");
                BufferedImage thumbnail = null;
                try {
                    thumbnail = ImageIO.read(thumbnailFile);
                } catch (IOException e) {
                }
                arraylist.add(new MapInfo(mapName, description1, description2, thumbnail));
            }
        }
        this.availableMaps = arraylist;
    }

    public List<MapInfo> getAvailableMaps() {
        return new ArrayList<>(this.availableMaps);
    }
}
