package io.github.ryuu.adventurecraft.util;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
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
