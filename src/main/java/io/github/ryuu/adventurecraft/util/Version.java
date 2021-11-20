package io.github.ryuu.adventurecraft.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class Version {

    public static String version = "AdventureCraft - Minecraft Beta 1.7.3";

    public static String shortVersion = "AdventureCraft - Minecraft Beta 1.7.3";

    private static void getVersion() {
        try {
            BufferedReader bufferedReader;
            String string;
            File file = new File("version.txt");
            if (file.exists() && (string = (bufferedReader = new BufferedReader((Reader) new FileReader(file))).readLine()) != null) {
                version = String.format((String) "AdventureCraft %s", (Object[]) new Object[] { string });
                shortVersion = String.format((String) "AC %s - (MC 1.7.3)", (Object[]) new Object[] { string });
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    static {
        Version.getVersion();
    }
}
