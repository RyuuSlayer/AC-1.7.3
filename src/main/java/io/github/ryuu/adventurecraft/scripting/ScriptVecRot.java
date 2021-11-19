/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package io.github.ryuu.adventurecraft.scripting;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class ScriptVecRot {

    public final double yaw;

    public final double pitch;

    ScriptVecRot(float setYaw, float setPitch) {
        this.yaw = setYaw;
        this.pitch = setPitch;
    }
}
