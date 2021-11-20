package io.github.ryuu.adventurecraft.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class DebugMode {

    public static boolean active = false;

    public static boolean levelEditing = false;

    public static boolean editMode = false;

    public static MapEditing mapEditing = null;

    public static boolean renderPaths = false;

    public static int reachDistance = 4;

    public static boolean renderFov = false;

    private DebugMode() {
    }
}
