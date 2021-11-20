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
