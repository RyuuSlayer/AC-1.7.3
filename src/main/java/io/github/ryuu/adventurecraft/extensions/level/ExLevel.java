package io.github.ryuu.adventurecraft.extensions.level;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityNpcPath;
import io.github.ryuu.adventurecraft.extensions.client.resource.language.ExTranslationStorage;
import io.github.ryuu.adventurecraft.items.ItemCustom;
import io.github.ryuu.adventurecraft.mixin.level.AccessLevel;
import io.github.ryuu.adventurecraft.scripting.EntityDescriptions;
import io.github.ryuu.adventurecraft.scripting.ScopeTag;
import io.github.ryuu.adventurecraft.scripting.Script;
import io.github.ryuu.adventurecraft.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.entity.Entity;
import net.minecraft.level.Level;
import net.minecraft.level.LevelData;
import net.minecraft.level.LevelProperties;
import net.minecraft.level.dimension.Dimension;
import net.minecraft.level.dimension.DimensionData;
import net.minecraft.level.dimension.McRegionDimensionFile;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.Vec3d;
import org.mozilla.javascript.Scriptable;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.TreeSet;

public interface ExLevel {

    static String getAcMapDirectory() {
        //return "../maps";
        return "maps";
    }

    static Level createLevel(
            String acLevelName, DimensionData isavehandler, String levelName, long randomSeed, Dimension dimension)
            throws InstantiationException {

        Level level = new Level(isavehandler, levelName, randomSeed, dimension);
        ExLevel exLevel = (ExLevel) level;
        AccessLevel aLevel = (AccessLevel) level;

        exLevel.sharedInit();

        File mcDir = Minecraft.getGameDirectory();
        File mapDir = new File(mcDir, getAcMapDirectory());
        File levelFile = new File(mapDir, acLevelName);
        ((ExTranslationStorage) TranslationStorage.getInstance()).loadMapTranslation(levelFile);
        exLevel.setMapHandler(new McRegionDimensionFile(mapDir, acLevelName, false));
        exLevel.setLevelDir(levelFile);

        //aLevel.field_197 = false;
        aLevel.setField_181(new ArrayList<>());
        aLevel.setEntities(new ArrayList<>());
        aLevel.setField_182(new ArrayList<>());
        aLevel.setField_183(new TreeSet<>());
        aLevel.setField_184(new HashSet<>());
        aLevel.setTileEntities(new ArrayList<>());
        aLevel.setField_185(new ArrayList<>());
        aLevel.setPlayers(new ArrayList<>());
        aLevel.setField_201(new ArrayList<>());
        aLevel.setField_186(0xFFFFFFL);
        //aLevel.field_202 = 0;
        aLevel.setField_203(new Random().nextInt());
        aLevel.setUnusedIncrement(1013904223);
        //aLevel.field_209 = 0;
        //aLevel.field_210 = 0;
        //aLevel.field_211 = false;
        aLevel.setTime(System.currentTimeMillis());
        aLevel.setField_212(40);
        aLevel.setRand(new Random());
        //aLevel.generating = false;
        aLevel.setListeners(new ArrayList<>());
        aLevel.setField_189(new ArrayList<>());
        //aLevel.field_191 = 0;
        aLevel.setField_192(true);
        aLevel.setField_193(true);
        aLevel.setField_195(level.rand.nextInt(12000));
        aLevel.setField_196(new ArrayList<>());
        //aLevel.isClient = false;
        aLevel.setDimensionData(isavehandler);
        aLevel.setProperties(null);

        if (isavehandler != null) {
            level.data = new LevelData(isavehandler);
            aLevel.setProperties(isavehandler.getLevelProperties());
        } else {
            level.data = new LevelData(exLevel.getMapHandler());
        }
        if (level.getProperties() == null) {
            exLevel.setNewSave(true);
            aLevel.setProperties(exLevel.getMapHandler().getLevelProperties());
        }
        if (!TerrainImage.loadMap(levelFile)) {
            TerrainImage.loadMap(new File(new File(mcDir, "saves"), levelName));
        }
        level.generating = level.getProperties() == null;

        if (dimension != null) {
            aLevel.setDimension(dimension);
        } else if (level.getProperties() != null && level.getProperties().getDimensionId() == -1) {
            aLevel.setDimension(Dimension.getByID(-1));
        } else {
            aLevel.setDimension(Dimension.getByID(0));
        }

        boolean flag = false;
        if (level.getProperties() == null) {
            aLevel.setProperties(new LevelProperties(randomSeed, levelName));
            flag = true;
        } else {
            level.getProperties().setName(levelName);
        }
        ExLevelProperties exProps = (ExLevelProperties) level.getProperties();

        exProps.setUseBiomeImages(TerrainImage.isLoaded);
        CompoundTag triggerData = exProps.getTriggerData();
        if (triggerData != null) {
            exLevel.getTriggerManager().loadFromTagCompound(triggerData);
        }
        level.dimension.setLevel(level);
        exLevel.loadBrightness();
        aLevel.setCache(aLevel.invokeCreateChunkCache());
        if (flag) {
            aLevel.invokeComputeSpawnPosition();
            level.forceLoadChunks = true;
            int i = 0;
            int j = 0;
            while (!level.dimension.isValidSpawnPos(i, j)) {
                i += level.rand.nextInt(64) - level.rand.nextInt(64);
                j += level.rand.nextInt(64) - level.rand.nextInt(64);
            }
            level.getProperties().setSpawnPosition(i, level.getTileAtSurface(i, j), j);
            level.forceLoadChunks = false;
        }
        level.method_237();
        aLevel.invokeInitWeatherGradients();

        exLevel.loadMapMusic();
        exLevel.loadMapSounds();

        if (exProps.getGlobalScope() != null) {
            ScopeTag.loadScopeFromTag(exLevel.getScript().globalScope, exProps.getGlobalScope());
        }
        exLevel.setScriptHandler(new JScriptHandler(level, levelFile));
        exLevel.setMusicScripts(new MusicScripts(exLevel.getScript(), levelFile, exLevel.getScriptHandler()));
        if (exProps.getMusicScope() != null) {
            ScopeTag.loadScopeFromTag(exLevel.getMusicScripts().scope, exProps.getMusicScope());
        }
        exLevel.setScope(exLevel.getScript().getNewScope());
        if (exProps.getWorldScope() != null) {
            ScopeTag.loadScopeFromTag(exLevel.getScope(), exProps.getWorldScope());
        }
        exLevel.loadSoundOverrides();
        EntityDescriptions.loadDescriptions(new File(levelFile, "entitys"));
        ItemCustom.loadItems(new File(levelFile, "items"));
        exLevel.setUndoStack(new UndoStack());
        TileEntityNpcPath.lastEntity = null;

        return level;
    }

    void sharedInit();

    File getLevelDir();

    void setLevelDir(File levelDir);

    String[] getMusicList();

    String[] getSoundList();

    DimensionData getMapHandler();

    void setMapHandler(DimensionData mapHandler);

    TriggerManager getTriggerManager();

    Script getScript();

    JScriptHandler getScriptHandler();

    void setScriptHandler(JScriptHandler scriptHandler);

    MusicScripts getMusicScripts();

    void setMusicScripts(MusicScripts musicScripts);

    Scriptable getScope();

    void setScope(Scriptable scope);

    UndoStack getUndoStack();

    void setUndoStack(UndoStack undoStack);

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

    TileEntity getBlockTileEntityDontCreate(int i, int j, int k);

    HitResult rayTraceBlocks2(Vec3d vec3d, Vec3d vec3d1, boolean flag, boolean flag1, boolean collideWithClip);

    BufferedImage loadMapTexture(String texName);

    boolean isNewSave();

    void setNewSave(boolean newSave);

    void updateChunkProvider();

    void loadSoundOverrides();

    void loadMapMusic();

    void loadMapSounds();

}
