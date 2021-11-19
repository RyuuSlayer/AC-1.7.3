package io.github.ryuu.adventurecraft.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Version {

    public static String version = "AdventureCraft - Minecraft Beta 1.7.3";

    public static String shortVersion = "AdventureCraft - Minecraft Beta 1.7.3";

    static {
        Version.getVersion();
    }

    private static void getVersion() {
        try {
            BufferedReader bufferedReader;
            String string;
            File file = new File("version.txt");
            if (file.exists() && (string = (bufferedReader = new BufferedReader(new FileReader(file))).readLine()) != null) {
                version = String.format("AdventureCraft %s", new Object[]{string});
                shortVersion = String.format("AC %s - (MC 1.7.3)", new Object[]{string});
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
