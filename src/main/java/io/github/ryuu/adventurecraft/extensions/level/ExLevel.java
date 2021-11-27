package io.github.ryuu.adventurecraft.extensions.level;

import io.github.ryuu.adventurecraft.scripting.Script;
import io.github.ryuu.adventurecraft.util.JScriptHandler;
import io.github.ryuu.adventurecraft.util.TriggerManager;
import io.github.ryuu.adventurecraft.util.UndoStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.maths.Vec3f;
import org.mozilla.javascript.Scriptable;

import java.io.File;

public interface ExLevel {

    File getLevelDir();

    TriggerManager getTriggerManager();

    Script getScript();

    JScriptHandler getScriptHandler();

    Scriptable getScope();

    UndoStack getUndoStack();

    float getSpawnYaw();

    void setSpawnYaw(float spawnYaw);

    float getTimeOfDay();

    void setTimeOfDay(long l);

    void loadMapTextures();

    void loadBrightness();

    void undo();

    void redo();

    void resetCoordOrder();

    float getLightValue(int i, int j, int k);

    float getFogStart(float start, float f);

    float getFogEnd(float end, float f);

    Entity getEntityByID(int entityID);

    double getTemperatureValue(int x, int z);

    void setTemperatureValue(int x, int z, double temp);

    void cancelBlockUpdate(int i, int j, int k, int l);

    boolean setBlockAndMetadataTemp(int i, int j, int k, int l, int i1);

    HitResult rayTraceBlocks2(Vec3f vec3d, Vec3f vec3d1, boolean flag, boolean flag1, boolean collideWithClip);
}
