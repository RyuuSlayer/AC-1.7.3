package io.github.ryuu.adventurecraft.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Version {
    public static String version = "AdventureCraft Awakening";

    public static String shortVersion = "AdventureCraft Awakening";

    static {
        getVersion();
    }

    private static void getVersion() {
        try {
            File file = new File("version.txt");
            if (file.exists()) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String str = bufferedReader.readLine();
                if (str != null) {
                    version = String.format("AdventureCraft %s", str);
                    shortVersion = String.format("AC %s - (MC 1.7.3)", str);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
