package io.github.ryuu.adventurecraft.mixin.level;

import io.github.ryuu.adventurecraft.blocks.BlockStairMulti;
import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityNpcPath;
import io.github.ryuu.adventurecraft.extensions.client.ExMinecraft;
import io.github.ryuu.adventurecraft.extensions.entity.ExEntity;
import io.github.ryuu.adventurecraft.extensions.entity.ExLivingEntity;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.extensions.level.ExLevelProperties;
import io.github.ryuu.adventurecraft.extensions.level.chunk.ExChunk;
import io.github.ryuu.adventurecraft.extensions.tile.entity.ExTileEntity;
import io.github.ryuu.adventurecraft.items.ItemCustom;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import io.github.ryuu.adventurecraft.scripting.EntityDescriptions;
import io.github.ryuu.adventurecraft.scripting.ScopeTag;
import io.github.ryuu.adventurecraft.scripting.Script;
import io.github.ryuu.adventurecraft.scripting.ScriptModel;
import io.github.ryuu.adventurecraft.util.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_366;
import net.minecraft.client.Minecraft;
import net.minecraft.client.colour.FoliageColour;
import net.minecraft.client.colour.GrassColour;
import net.minecraft.client.render.*;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Lightning;
import net.minecraft.entity.player.Player;
import net.minecraft.level.*;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.level.chunk.ChunkIO;
import net.minecraft.level.chunk.ClientChunkCache;
import net.minecraft.level.dimension.Dimension;
import net.minecraft.level.dimension.DimensionData;
import net.minecraft.level.dimension.McRegionDimensionFile;
import net.minecraft.level.source.LevelSource;
import net.minecraft.tile.LadderTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathsHelper;
import net.minecraft.util.maths.Vec3f;
import org.mozilla.javascript.Scriptable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

@Mixin(Level.class)
public abstract class MixinLevel implements TileView, ExLevel, AccessLevel {

    protected DimensionData mapHandler;
    public File levelDir;
    public String[] musicList;
    public String[] soundList;
    public TriggerManager triggerManager;
    public Script script;
    public JScriptHandler scriptHandler;
    public MusicScripts musicScripts;
    public boolean fogColorOverridden;
    public boolean fogDensityOverridden;
    public Scriptable scope;
    public UndoStack undoStack;
    boolean firstTick;
    boolean newSave;
    private int[] coordOrder;

    @Shadow
    static int field_179;
    @Final
    @Shadow
    public Dimension dimension;
    @Final
    @Shadow
    protected int unusedIncrement;
    @Final
    @Shadow
    protected DimensionData dimensionData;
    @Shadow
    private final List field_181;
    @Shadow
    private final List field_182;
    @Shadow
    private final TreeSet field_183;
    @Shadow
    private final Set field_184;
    @Shadow
    private final List field_185;
    @Shadow
    private final long field_186;
    @Shadow
    private final ArrayList field_189;
    @Shadow
    private final List field_196;
    @Shadow
    public boolean field_197;
    @Shadow
    public List<Entity> entities;
    @Shadow
    public List tileEntities;
    @Shadow
    public List players;
    @Shadow
    public List field_201;
    @Shadow
    public int field_202;
    @Shadow
    public int field_210;
    @Shadow
    public boolean field_211;
    @Shadow
    public Random rand;
    @Shadow
    public boolean generating;
    @Shadow
    protected LevelProperties properties;
    @Shadow
    public boolean forceLoadChunks;
    @Shadow
    public LevelData data;
    @Shadow
    public boolean isClient;
    @Shadow
    protected int field_203;
    @Shadow
    protected float prevRainGradient;
    @Shadow
    protected float rainGradient;
    @Shadow
    protected float prevThunderGradient;
    @Shadow
    protected float thunderGradient;
    @Shadow
    protected int field_209;
    @Shadow
    protected int field_212;
    @Shadow
    protected List listeners;
    @Shadow
    protected LevelSource cache;
    @Shadow
    private long time;
    @Shadow
    private boolean field_190;
    @Shadow
    private int field_191;
    @Shadow
    private boolean field_192;
    @Shadow
    private boolean field_193;
    @Shadow
    private Set field_194;
    @Shadow
    private int field_195;

    public MixinLevel(DimensionData dimensionData, String name, Dimension dimension, long seed) {
        this.unusedIncrement = 1013904223;
        this.fogColorOverridden = false;
        this.fogDensityOverridden = false;
        this.firstTick = true;
        this.newSave = false;
        this.triggerManager = new TriggerManager(this);
        this.field_197 = false;
        this.field_181 = new ArrayList();
        this.entities = new ArrayList();
        this.field_182 = new ArrayList();
        this.field_183 = new TreeSet();
        this.field_184 = new HashSet();
        this.tileEntities = new ArrayList();
        this.field_185 = new ArrayList();
        this.players = new ArrayList();
        this.field_201 = new ArrayList();
        this.field_186 = 0xFFFFFFL;
        this.field_202 = 0;
        this.field_203 = new Random().nextInt();
        this.field_209 = 0;
        this.field_210 = 0;
        this.field_211 = false;
        this.time = System.currentTimeMillis();
        this.field_212 = 40;
        this.rand = new Random();
        this.generating = false;
        this.listeners = new ArrayList();
        this.field_189 = new ArrayList();
        this.field_191 = 0;
        this.field_192 = true;
        this.field_193 = true;
        this.field_195 = this.rand.nextInt(12000);
        this.field_196 = new ArrayList();
        this.isClient = false;
        this.dimensionData = dimensionData;
        this.properties = new LevelProperties(seed, name);
        this.dimension = dimension;
        this.data = new LevelData(dimensionData);
        dimension.setLevel(this);
        this.cache = this.createChunkCache();
        this.method_237();
        this.initWeatherGradients();
        this.mapHandler = null;
        this.script = new Script(this);
    }

    public MixinLevel(Level level, Dimension dimension) {
        this.unusedIncrement = 1013904223;
        this.fogColorOverridden = false;
        this.fogDensityOverridden = false;
        this.firstTick = true;
        this.newSave = false;
        this.triggerManager = new TriggerManager(this);
        this.field_197 = false;
        this.field_181 = new ArrayList();
        this.entities = new ArrayList();
        this.field_182 = new ArrayList();
        this.field_183 = new TreeSet();
        this.field_184 = new HashSet();
        this.tileEntities = new ArrayList();
        this.field_185 = new ArrayList();
        this.players = new ArrayList();
        this.field_201 = new ArrayList();
        this.field_186 = 0xFFFFFFL;
        this.field_202 = 0;
        this.field_203 = new Random().nextInt();
        this.field_209 = 0;
        this.field_210 = 0;
        this.field_211 = false;
        this.time = System.currentTimeMillis();
        this.field_212 = 40;
        this.rand = new Random();
        this.generating = false;
        this.listeners = new ArrayList();
        this.field_189 = new ArrayList();
        this.field_191 = 0;
        this.field_192 = true;
        this.field_193 = true;
        this.field_195 = this.rand.nextInt(12000);
        this.field_196 = new ArrayList();
        this.isClient = false;
        this.time = level.time;
        this.dimensionData = level.dimensionData;
        this.properties = new LevelProperties(level.properties);
        this.data = new LevelData(this.dimensionData);
        this.dimension = dimension;
        dimension.setLevel(this);
        this.cache = this.createChunkCache();
        this.method_237();
        this.initWeatherGradients();
        this.mapHandler = null;
        this.script = new Script(this);
    }

    public MixinLevel(DimensionData isavehandler, String name, long seed) {
        this(null, isavehandler, name, seed, null);
    }

    public MixinLevel(String levelName, DimensionData isavehandler, String s, long l) {
        this(levelName, isavehandler, s, l, null);
    }

    public MixinLevel(String levelName, DimensionData isavehandler, String s, long l, Dimension worldprovider) {
        this.unusedIncrement = 1013904223;
        this.fogColorOverridden = false;
        this.fogDensityOverridden = false;
        this.firstTick = true;
        this.newSave = false;
        this.triggerManager = new TriggerManager(this);
        File mcDir = Minecraft.getGameDirectory();
        File mapDir = new File(mcDir, "../maps");
        File levelFile = new File(mapDir, levelName);
        TranslationStorage.getInstance().loadMapTranslation(levelFile);
        this.mapHandler = new McRegionDimensionFile(mapDir, levelName, false);
        this.levelDir = levelFile;
        this.field_197 = false;
        this.field_181 = new ArrayList();
        this.entities = new ArrayList();
        this.field_182 = new ArrayList();
        this.field_183 = new TreeSet();
        this.field_184 = new HashSet();
        this.tileEntities = new ArrayList();
        this.field_185 = new ArrayList();
        this.players = new ArrayList();
        this.field_201 = new ArrayList();
        this.field_186 = 0xFFFFFFL;
        this.field_202 = 0;
        this.field_203 = new Random().nextInt();
        this.field_209 = 0;
        this.field_210 = 0;
        this.field_211 = false;
        this.time = System.currentTimeMillis();
        this.field_212 = 40;
        this.rand = new Random();
        this.generating = false;
        this.listeners = new ArrayList();
        this.field_189 = new ArrayList();
        this.field_191 = 0;
        this.field_192 = true;
        this.field_193 = true;
        this.field_195 = this.rand.nextInt(12000);
        this.field_196 = new ArrayList();
        this.isClient = false;
        this.dimensionData = isavehandler;
        if (isavehandler != null) {
            this.data = new LevelData(isavehandler);
            this.properties = isavehandler.getLevelProperties();
        } else {
            this.data = new LevelData(this.mapHandler);
        }
        if (this.properties == null) {
            this.newSave = true;
            this.properties = this.mapHandler.getLevelProperties();
        }
        if (!TerrainImage.loadMap(levelFile)) {
            TerrainImage.loadMap(new File(new File(mcDir, "saves"), s));
        }
        boolean bl = this.generating = this.properties == null;
        this.dimension = worldprovider != null ? worldprovider : (this.properties != null && this.properties.getDimensionId() == -1 ? Dimension.getByID(-1) : Dimension.getByID(0));
        boolean flag = false;
        if (this.properties == null) {
            this.properties = new LevelProperties(l, s);
            flag = true;
        } else {
            this.properties.setName(s);
        }
        this.properties.useImages = TerrainImage.isLoaded;
        if (this.properties.triggerData != null) {
            this.triggerManager.loadFromTagCompound(this.properties.triggerData);
        }
        this.dimension.setLevel(this);
        this.loadBrightness();
        this.cache = this.createChunkCache();
        if (flag) {
            this.computeSpawnPosition();
            this.forceLoadChunks = true;
            int i = 0;
            int j = 0;
            while (!this.dimension.isValidSpawnPos(i, j)) {
                i += this.rand.nextInt(64) - this.rand.nextInt(64);
                j += this.rand.nextInt(64) - this.rand.nextInt(64);
            }
            this.properties.setSpawnPosition(i, this.getTileAtSurface(i, j), j);
            this.forceLoadChunks = false;
        }
        this.method_237();
        this.initWeatherGradients();
        this.loadMapMusic();
        this.loadMapSounds();
        this.script = new Script((Level) (Object) this);
        if (this.properties.globalScope != null) {
            ScopeTag.loadScopeFromTag(this.script.globalScope, this.properties.globalScope);
        }
        this.scriptHandler = new JScriptHandler((Level) (Object) this, levelFile);
        this.musicScripts = new MusicScripts(this.script, levelFile, this.scriptHandler);
        if (this.properties.musicScope != null) {
            ScopeTag.loadScopeFromTag(this.musicScripts.scope, this.properties.musicScope);
        }
        this.scope = this.script.getNewScope();
        if (this.properties.worldScope != null) {
            ScopeTag.loadScopeFromTag(this.scope, this.properties.worldScope);
        }
        this.loadSoundOverrides();
        EntityDescriptions.loadDescriptions(new File(levelFile, "entitys"));
        ItemCustom.loadItems(new File(levelFile, "items"));
        this.undoStack = new UndoStack();
        TileEntityNpcPath.lastEntity = null;
    }

    public MixinLevel(DimensionData dimensionData, String string, long l, Dimension dimension) {
        this.field_197 = false;
        this.field_181 = new ArrayList();
        this.entities = new ArrayList();
        this.field_182 = new ArrayList();
        this.field_183 = new TreeSet();
        this.field_184 = new HashSet();
        this.tileEntities = new ArrayList();
        this.field_185 = new ArrayList();
        this.players = new ArrayList();
        this.field_201 = new ArrayList();
        this.field_186 = 0xFFFFFFL;
        this.field_202 = 0;
        this.field_203 = new Random().nextInt();
        this.unusedIncrement = 1013904223;
        this.field_209 = 0;
        this.field_210 = 0;
        this.field_211 = false;
        this.time = System.currentTimeMillis();
        this.field_212 = 40;
        this.rand = new Random();
        this.generating = false;
        this.listeners = new ArrayList();
        this.field_189 = new ArrayList();
        this.field_191 = 0;
        this.field_192 = true;
        this.field_193 = true;
        this.field_194 = new HashSet();
        this.field_195 = this.rand.nextInt(12000);
        this.field_196 = new ArrayList();
        this.isClient = false;
        this.dimensionData = dimensionData;
        this.data = new LevelData(dimensionData);
        this.properties = dimensionData.getLevelProperties();
        boolean bl = this.generating = this.properties == null;
        this.dimension = dimension != null ? dimension : (this.properties != null && this.properties.getDimensionId() == -1 ? Dimension.getByID(-1) : Dimension.getByID(0));
        boolean bl2 = false;
        if (this.properties == null) {
            this.properties = new LevelProperties(l, string);
            bl2 = true;
        } else {
            this.properties.setName(string);
        }
        this.dimension.setLevel(this);
        this.cache = this.createChunkCache();
        if (bl2) {
            this.computeSpawnPosition();
        }
        this.method_237();
        this.initWeatherGradients();
    }

    @Shadow
    public abstract long getLevelTime();

    @Shadow
    public abstract Chunk getChunkFromCache(int i, int j);

    @Shadow
    protected abstract boolean isChunkLoaded(int i, int j);

    @Shadow
    public abstract boolean isRegionLoaded(int i, int j, int k, int i1, int i2, int i3);

    @Shadow
    public abstract boolean isTileLoaded(int i, int j, int k);

    @Shadow
    public abstract boolean isAboveGround(int i, int j, int k);

    @Shadow
    public abstract int computeWeatherGradients(float f);

    @Shadow
    public abstract int getLightLevel(int i, int j, int k);

    @Shadow
    public abstract void removeTileEntity(int i, int j, int k);

    @Shadow
    public abstract void forceUpdatePosition(Entity arg);

    @Override
    public void loadMapTextures() {
        AccessMinecraft.getInstance().textureManager.reload();
        for (Object obj : AccessMinecraft.getInstance().textureManager.TEXTURE_ID_MAP.entrySet()) {
            Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) obj;
            String texName = entry.getKey();
            int texID = entry.getValue();
            try {
                AccessMinecraft.getInstance().textureManager.loadTexture(texID, texName);
            } catch (IllegalArgumentException ignoreNulls) {
            }
        }
        this.loadTextureAnimations();
        TextureFanFX.loadImage();
        FireTextureBinder.loadImage();
        FlowingLavaTextureBinder.loadImage();
        FlowingLavaTextureBinder2.loadImage();
        PortalTextureBinder.loadImage();
        FlowingWaterTextureBinder2.loadImage();
        FlowingWaterTextureBinder.loadImage();
        GrassColour.loadGrass("/misc/grasscolor.png");
        FoliageColour.loadFoliage("/misc/foliagecolor.png");
        this.properties.loadTextureReplacements(this);
    }

    private void loadTextureAnimations() {
        AccessMinecraft.getInstance().textureManager.clearTextureAnimations();
        File animationFile = new File(this.levelDir, "animations.txt");
        if (!animationFile.exists()) return;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(animationFile));
            try {
                while (reader.ready()) {
                    String line = reader.readLine();
                    String[] parts = line.split(",", 7);
                    if (parts.length != 7) continue;
                    try {
                        String texName = parts[1].trim();
                        String animTex = parts[2].trim();
                        int x = Integer.parseInt(parts[3].trim());
                        int y = Integer.parseInt(parts[4].trim());
                        int w = Integer.parseInt(parts[5].trim());
                        int h = Integer.parseInt(parts[6].trim());
                        TextureAnimated t = new TextureAnimated(texName, animTex, x, y, w, h);
                        AccessMinecraft.getInstance().textureManager.registerTextureAnimation(parts[0].trim(), t);
                    } catch (Exception e) {
                    }
                }
                return;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public BufferedImage loadMapTexture(String texName) {
        File terrainTexture = new File(this.levelDir, texName);
        if (terrainTexture.exists()) {
            try {
                BufferedImage bufferedimage = ImageIO.read(terrainTexture);
                return bufferedimage;
            } catch (Exception exception) {
            }
        }
        return null;
    }

    public void updateChunkProvider() {
        this.cache = this.createChunkCache();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    protected LevelSource createChunkCache() {
        ChunkIO ichunkloader;
        if (this.dimensionData == null) {
            ichunkloader = this.mapHandler.getChunkIO(this.dimension);
        } else {
            ichunkloader = this.dimensionData.getChunkIO(this.dimension);
            if (this.mapHandler != null) {
                ichunkloader = new MapChunkLoader(this.mapHandler.getChunkIO(this.dimension), ichunkloader);
            }
        }
        return new ClientChunkCache((Level) (Object) this, ichunkloader, this.dimension.createLevelSource());
    }

    @Inject(method = "revalidateSpawnPos", locals = LocalCapture.CAPTURE_FAILHARD, at = @At("TAIL"))
    private void revalidateSpawnPos(CallbackInfo ci, int var1, int var2) {
        this.properties.setSpawnY(this.getFirstUncoveredBlockY(var1, var2));
    }

    @Redirect(method = "computeSpawnPosition", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/level/LevelProperties;setSpawnPosition(III)V"))
    private void changeYOnSpawnPosition(LevelProperties instance, int x, int y, int z) {
        this.properties.setSpawnPosition(x, this.getFirstUncoveredBlockY(x, z), z);
    }

    public int getFirstUncoveredBlockY(int i, int j) {
        int k = 127;
        while (this.isAir(i, k, j) && k > 0) {
            --k;
        }
        return k;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public int getTileAtSurface(int x, int z) {
        int k = 127;
        while (this.isAir(x, k, z) && k > 0) {
            --k;
        }
        return this.getTileId(x, k, z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    private void prepareSaveLevel() {
        this.checkSessionLock();
        this.properties.globalScope = ScopeTag.getTagFromScope(this.script.globalScope);
        this.properties.worldScope = ScopeTag.getTagFromScope(this.scope);
        this.properties.musicScope = ScopeTag.getTagFromScope(this.musicScripts.scope);
        if (this.dimensionData != null) {
            this.dimensionData.writeProperties(this.properties, this.players);
        }
        if (DebugMode.levelEditing || this.dimensionData == null) {
            this.mapHandler.writeProperties(this.properties, this.players);
        }
        this.data.flush();
    }

    @Override
    public boolean setBlockAndMetadataTemp(int i, int j, int k, int l, int i1) {
        if (i < -32000000 || k < -32000000 || i >= 32000000 || k > 32000000) {
            return false;
        }
        if (j < 0) {
            return false;
        }
        if (j >= 128) {
            return false;
        }
        Chunk chunk = this.getChunkFromCache(i >> 4, k >> 4);
        return ((ExChunk) chunk).setBlockIDWithMetadataTemp(i & 0xF, j, k & 0xF, l, i1);
    }

    @Inject(method = "placeTile", locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true, at = @At(
            value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/level/Level;getTileId(III)I",
            shift = At.Shift.AFTER))
    private void afterGetTileInPlaceTile(int x, int y, int z, boolean flag, CallbackInfoReturnable<Integer> cir, int var5) {
        if (Tile.BY_ID[var5] instanceof BlockStairMulti) {
            int i1 = this.placeTile(x, y + 1, z, false);
            int j1 = this.placeTile(x + 1, y, z, false);
            int k1 = this.placeTile(x - 1, y, z, false);
            int l1 = this.placeTile(x, y, z + 1, false);
            int i2 = this.placeTile(x, y, z - 1, false);
            if (j1 > i1) {
                i1 = j1;
            }
            if (k1 > i1) {
                i1 = k1;
            }
            if (l1 > i1) {
                i1 = l1;
            }
            if (i2 > i1) {
                i1 = i2;
            }
            cir.setReturnValue(i1);
            cir.cancel();
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void method_165(LightType tpe, int i, int j, int k, int l) {
        if (!this.dimension.fixedSpawnPos || tpe != LightType.SKY) {
            if (this.isTileLoaded(i, j, k)) {
                if (tpe == LightType.SKY) {
                    if (this.isAboveGround(i, j, k)) {
                        l = 15;
                    }
                } else if (tpe == LightType.BLOCK) {
                    int i1 = this.getTileId(i, j, k);
                    if (Tile.BY_ID[i1] != null && Tile.BY_ID[i1].getBlockLightValue(this, i, j, k) < l) {
                        l = Tile.BY_ID[i1].getBlockLightValue(this, i, j, k);
                    }
                }
                if (this.getLightLevel(tpe, i, j, k) != l) {
                    this.method_166(tpe, i, j, k, i, j, k);
                }
            }
        }
    }

    @Override
    public float getLightValue(int i, int j, int k) {
        int lightValue = this.getLightLevel(i, j, k);
        float torchLight = PlayerTorch.getTorchLight((Level) (Object) this, i, j, k);
        if (lightValue < torchLight) {
            return Math.min(torchLight, 15.0f);
        }
        return lightValue;
    }

    private float getBrightnessLevel(float lightValue) {
        int floorValue = (int) Math.floor(lightValue);
        if (floorValue != lightValue) {
            int ceilValue = (int) Math.ceil(lightValue);
            float lerpValue = lightValue - floorValue;
            return (1.0f - lerpValue) * this.dimension.field_2178[floorValue] + lerpValue * this.dimension.field_2178[ceilValue];
        }
        return this.dimension.field_2178[floorValue];
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Environment(EnvType.CLIENT)
    @Override
    @Overwrite
    public float method_1784(int i, int j, int k, int l) {
        float lightValue = this.getLightValue(i, j, k);
        if (lightValue < (float) l) {
            lightValue = l;
        }
        return this.getBrightnessLevel(lightValue);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite
    public float getBrightness(int i, int j, int k) {
        float lightValue = this.getLightValue(i, j, k);
        return this.getBrightnessLevel(lightValue);
    }

    public float getDayLight() {
        int lightValue = 15 - this.field_202;
        return this.dimension.field_2178[lightValue];
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public HitResult raycast(Vec3f vec3d, Vec3f vec3d1, boolean flag, boolean flag1) {
        return this.rayTraceBlocks2(vec3d, vec3d1, flag, flag1, true);
    }

    @Override
    public HitResult rayTraceBlocks2(Vec3f vec3d, Vec3f vec3d1, boolean flag, boolean flag1, boolean collideWithClip) {
        if (Double.isNaN(vec3d.x) || Double.isNaN(vec3d.y) || Double.isNaN(vec3d.z)) {
            return null;
        }
        if (Double.isNaN(vec3d1.x) || Double.isNaN(vec3d1.y) || Double.isNaN(vec3d1.z)) {
            return null;
        }
        int i = MathsHelper.floor(vec3d1.x);
        int j = MathsHelper.floor(vec3d1.y);
        int k = MathsHelper.floor(vec3d1.z);
        int l = MathsHelper.floor(vec3d.x);
        int i1 = MathsHelper.floor(vec3d.y);
        int j1 = MathsHelper.floor(vec3d.z);
        int k1 = this.getTileId(l, i1, j1);
        int i2 = this.getTileMeta(l, i1, j1);
        Tile block = Tile.BY_ID[k1];
        if ((!flag1 || block == null || block.getCollisionShape((Level) (Object) this, l, i1, j1) != null) && k1 > 0 && block != null && block.method_1571(i2, flag)) {
            if ((collideWithClip || k1 != Blocks.clipBlock.id && !LadderTile.isLadderID(k1))) {
                HitResult movingobjectposition = block.raycast((Level) (Object) this, l, i1, j1, vec3d, vec3d1);
                if (movingobjectposition != null) {
                    return movingobjectposition;
                }
            }
        }
        int l1 = 200;
        while (l1-- >= 0) {
            if (Double.isNaN(vec3d.x) || Double.isNaN(vec3d.y) || Double.isNaN(vec3d.z)) {
                return null;
            }
            if (l == i && i1 == j && j1 == k) {
                return null;
            }
            boolean flag2 = true;
            boolean flag3 = true;
            boolean flag4 = true;
            double d = 999.0;
            double d1 = 999.0;
            double d2 = 999.0;
            if (i > l) {
                d = l + 1.0;
            } else if (i < l) {
                d = l + 0.0;
            } else {
                flag2 = false;
            }
            if (j > i1) {
                d1 = i1 + 1.0;
            } else if (j < i1) {
                d1 = i1 + 0.0;
            } else {
                flag3 = false;
            }
            if (k > j1) {
                d2 = j1 + 1.0;
            } else if (k < j1) {
                d2 = j1 + 0.0;
            } else {
                flag4 = false;
            }
            double d3 = 999.0;
            double d4 = 999.0;
            double d5 = 999.0;
            double d6 = vec3d1.x - vec3d.x;
            double d7 = vec3d1.y - vec3d.y;
            double d8 = vec3d1.z - vec3d.z;
            if (flag2) {
                d3 = (d - vec3d.x) / d6;
            }
            if (flag3) {
                d4 = (d1 - vec3d.y) / d7;
            }
            if (flag4) {
                d5 = (d2 - vec3d.z) / d8;
            }
            int byte0;
            if (d3 < d4 && d3 < d5) {
                byte0 = i > l ? 4 : 5;
                vec3d.x = d;
                vec3d.y += d7 * d3;
                vec3d.z += d8 * d3;
            } else if (d4 < d5) {
                byte0 = j > i1 ? 0 : 1;
                vec3d.x += d6 * d4;
                vec3d.y = d1;
                vec3d.z += d8 * d4;
            } else {
                byte0 = k > j1 ? 2 : 3;
                vec3d.x += d6 * d5;
                vec3d.y += d7 * d5;
                vec3d.z = d2;
            }
            Vec3f vec3d2 = Vec3f.from(vec3d.x, vec3d.y, vec3d.z);
            vec3d2.x = MathsHelper.floor(vec3d.x);
            l = (int) vec3d2.x;
            if (byte0 == 5) {
                --l;
                vec3d2.x += 1.0;
            }
            vec3d2.y = MathsHelper.floor(vec3d.y);
            i1 = (int) vec3d2.y;
            if (byte0 == 1) {
                --i1;
                vec3d2.y += 1.0;
            }
            vec3d2.z = MathsHelper.floor(vec3d.z);
            j1 = (int) vec3d2.z;
            if (byte0 == 3) {
                --j1;
                vec3d2.z += 1.0;
            }
            int j2 = this.getTileId(l, i1, j1);
            int k2 = this.getTileMeta(l, i1, j1);
            Tile block1 = Tile.BY_ID[j2];
            if (flag1 && block1 != null && block1.getCollisionShape((Level) (Object) this, l, i1, j1) == null) {
                continue;
            }
            if (j2 <= 0 || block1 == null || !block1.method_1571(k2, flag) || !block1.shouldRender((Level) (Object) this, l, i1, j1)) {
                continue;
            }
            HitResult movingobjectposition1 = block1.raycast((Level) (Object) this, l, i1, j1, vec3d, vec3d1);
            if (movingobjectposition1 != null && (collideWithClip || block1.id != Blocks.clipBlock.id && !LadderTile.isLadderID(block1.id))) {
                return movingobjectposition1;
            }
        }
        return null;
    }

    @Redirect(method = "spawnEntity", at = @At(
            value = "INVOKE",
            target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
            ordinal = 1))
    private static <E> boolean redirectSpawnEntityStore(List<E> instance, E e) {
        if (!instance.contains(e)) {
            instance.add(e);
        }
        return true;
    }

    @Inject(method = "method_190", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/level/Level;isTileLoaded(III)Z",
            shift = At.Shift.BEFORE))
    private void replaceIntersectionBody(
            Entity entity, Box axisalignedbb, CallbackInfoReturnable<List> cir, int var5, int var6, int var9, int var10) {

        for (int i2 = var5 - 1; i2 < var6; ++i2) {
            Tile block = Tile.BY_ID[this.getTileId(var9, i2, var10)];
            if (block == null || !((ExEntity) entity).getCollidesWithClipBlocks() && (block.id == Blocks.clipBlock.id || LadderTile.isLadderID(block.id)))
                continue;
            block.intersectsInLevel((Level) (Object) this, var9, i2, var10, axisalignedbb, this.field_189);
        }
    }

    @Redirect(method = "method_190", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/level/Level;isTileLoaded(III)Z"))
    private boolean disableTileLoadedCheck(Level instance, int j, int k, int i) {
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public float method_198(float f) {
        return this.dimension.method_1771(
                ((ExLevelProperties) this.properties).getTimeOfDay(),
                f * ((ExLevelProperties) this.properties).getTimeRate());
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Environment(EnvType.CLIENT)
    @Overwrite
    public Vec3f getSkyColour(float f) {
        float f1 = this.method_198(f);
        Vec3f returnColor = this.dimension.getSkyColour(f1, f);
        ExLevelProperties exProps = (ExLevelProperties) this.properties;
        if (exProps.getOverrideFogColor()) {
            if (this.fogColorOverridden) {
                returnColor.x = exProps.getFogR();
                returnColor.y = exProps.getFogG();
                returnColor.z = exProps.getFogB();
            } else {
                returnColor.x = (double) (1.0f - f) * returnColor.x + (double) (f * exProps.getFogR());
                returnColor.y = (double) (1.0f - f) * returnColor.y + (double) (f * exProps.getFogG());
                returnColor.z = (double) (1.0f - f) * returnColor.z + (double) (f * exProps.getFogB());
            }
        }
        return returnColor;
    }

    @Override
    public float getFogStart(float start, float f) {
        ExLevelProperties exProps = (ExLevelProperties) this.properties;
        if (exProps.getOverrideFogDensity()) {
            if (this.fogDensityOverridden) {
                return exProps.getFogStart();
            }
            return f * exProps.getFogStart() + (1.0f - f) * start;
        }
        return start;
    }

    @Override
    public float getFogEnd(float end, float f) {
        ExLevelProperties exProps = (ExLevelProperties) this.properties;
        if (exProps.getOverrideFogDensity()) {
            if (this.fogDensityOverridden) {
                return exProps.getFogEnd();
            }
            return f * exProps.getFogEnd() + (1.0f - f) * end;
        }
        return end;
    }

    @Override
    public void cancelBlockUpdate(int i, int j, int k, int l) {
        class_366 nextticklistentry = new class_366(i, j, k, l);
        this.field_184.remove(nextticklistentry);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void method_227() {
        for (int i = 0; i < this.field_201.size(); ++i) {
            Entity entity = (Entity) this.field_201.get(i);
            entity.tick();
            if (!entity.removed) continue;
            this.field_201.remove(i--);
        }
        this.entities.removeAll(this.field_182);
        for (Object o : this.field_182) {
            Entity entity1 = (Entity) o;
            int i1 = entity1.chunkX;
            int k1 = entity1.chunkZ;
            if (!entity1.shouldTick || !this.isChunkLoaded(i1, k1)) continue;
            this.getChunkFromCache(i1, k1).removeEntity(entity1);
        }
        for (Object o : this.field_182) {
            this.onEntityRemoved((Entity) o);
        }
        this.field_182.clear();

        for (int l = 0; l < this.entities.size(); ++l) {
            Entity entity2 = this.entities.get(l);
            if (entity2.vehicle != null) {
                if (!entity2.vehicle.removed && entity2.vehicle.passenger == entity2) continue;
                entity2.vehicle.passenger = null;
                entity2.vehicle = null;
            }
            if (!(entity2.removed || ExMinecraft.getInstance().isCameraActive() && ExMinecraft.getInstance().isCameraPause() || DebugMode.active && !(entity2 instanceof Player))) {
                this.forceUpdatePosition(entity2);
                Box.method_85();
            }
            if (!entity2.removed) continue;
            int j1 = entity2.chunkX;
            int l1 = entity2.chunkZ;
            if (entity2.shouldTick && this.isChunkLoaded(j1, l1)) {
                this.getChunkFromCache(j1, l1).removeEntity(entity2);
            }
            this.entities.remove(l--);
            this.onEntityRemoved(entity2);
        }
        this.field_190 = true;
        Iterator iterator = this.tileEntities.iterator();
        while (iterator.hasNext()) {
            Chunk chunk;
            TileEntity tileentity = (TileEntity) iterator.next();
            if (!tileentity.isInvalid()) {
                tileentity.tick();
            }
            if (!tileentity.isInvalid()) continue;
            iterator.remove();
            if (((ExTileEntity) tileentity).isKilledFromSaving() || (chunk = this.getChunkFromCache(tileentity.x >> 4, tileentity.z >> 4)) == null)
                continue;
            chunk.removeTileEntity(tileentity.x & 0xF, tileentity.y, tileentity.z & 0xF);
        }
        this.field_190 = false;
        if (!this.field_185.isEmpty()) {
            for (Object tileentity : this.field_185) {
                TileEntity tileentity1 = (TileEntity) tileentity;
                Chunk chunk1;
                if (tileentity1.isInvalid()) continue;
                if (!this.tileEntities.contains(tileentity1)) {
                    this.tileEntities.add(tileentity1);
                }
                if ((chunk1 = this.getChunkFromCache(tileentity1.x >> 4, tileentity1.z >> 4)) != null) {
                    chunk1.placeTileEntity(tileentity1.x & 0xF, tileentity1.y, tileentity1.z & 0xF, tileentity1);
                }
                this.method_243(tileentity1.x, tileentity1.y, tileentity1.z);
            }
            this.field_185.clear();
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void updatePosition(Entity entity, boolean flag) {
        int i = MathsHelper.floor(entity.x);
        int j = MathsHelper.floor(entity.z);
        int byte0 = 32;
        if (flag && !this.isRegionLoaded(i - byte0, 0, j - byte0, i + byte0, 128, j + byte0)) {
            return;
        }
        entity.prevRenderX = entity.x;
        entity.prevRenderY = entity.y;
        entity.prevRenderZ = entity.z;
        entity.prevYaw = entity.yaw;
        entity.prevPitch = entity.pitch;
        if (flag && entity.shouldTick) {
            int stunned = ((ExLivingEntity) entity).getStunned();
            if (stunned > 0) {
                ((ExLivingEntity) entity).setStunned(stunned - 1);
            } else if (entity.vehicle != null) {
                entity.tickRiding();
            } else {
                entity.tick();
            }
        }
        if (Double.isNaN(entity.x) || Double.isInfinite(entity.x)) {
            entity.x = entity.prevRenderX;
        }
        if (Double.isNaN(entity.y) || Double.isInfinite(entity.y)) {
            entity.y = entity.prevRenderY;
        }
        if (Double.isNaN(entity.z) || Double.isInfinite(entity.z)) {
            entity.z = entity.prevRenderZ;
        }
        if (Double.isNaN(entity.pitch) || Double.isInfinite(entity.pitch)) {
            entity.pitch = entity.prevPitch;
        }
        if (Double.isNaN(entity.yaw) || Double.isInfinite(entity.yaw)) {
            entity.yaw = entity.prevYaw;
        }
        int k = MathsHelper.floor(entity.x / 16.0);
        int l = MathsHelper.floor(entity.y / 16.0);
        int i1 = MathsHelper.floor(entity.z / 16.0);
        if (!entity.shouldTick || entity.chunkX != k || entity.chunkIndex != l || entity.chunkZ != i1) {
            if (entity.shouldTick && this.isChunkLoaded(entity.chunkX, entity.chunkZ)) {
                this.getChunkFromCache(entity.chunkX, entity.chunkZ).removeEntity(entity, entity.chunkIndex);
            }
            if (this.isChunkLoaded(k, i1)) {
                entity.shouldTick = true;
                this.getChunkFromCache(k, i1).addEntity(entity);
            } else {
                entity.shouldTick = false;
            }
        }
        if (flag && entity.shouldTick && entity.passenger != null) {
            if (entity.passenger.removed || entity.passenger.vehicle != entity) {
                entity.passenger.vehicle = null;
                entity.passenger = null;
            } else {
                this.forceUpdatePosition(entity.passenger);
            }
        }
    }

    public TileEntity getBlockTileEntityDontCreate(int i, int j, int k) {
        Chunk chunk = this.getChunkFromCache(i >> 4, k >> 4);
        if (chunk != null) {
            return ((ExChunk) chunk).getChunkBlockTileEntityDontCreate(i & 0xF, j, k & 0xF);
        }
        return null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void setTileEntity(int i, int j, int k, TileEntity tileentity) {
        if (!tileentity.isInvalid()) {
            this.removeTileEntity(i, j, k);
            if (this.field_190) {
                tileentity.x = i;
                tileentity.y = j;
                tileentity.z = k;
                this.field_185.add(tileentity);
            } else {
                this.tileEntities.add(tileentity);
                Chunk chunk = this.getChunkFromCache(i >> 4, k >> 4);
                if (chunk != null) {
                    chunk.placeTileEntity(i & 0xF, j, k & 0xF, tileentity);
                }
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void method_242() {
        long l1;
        if (this.firstTick) {
            if (this.newSave && !this.properties.onNewSaveScript.equals((Object) "")) {
                this.scriptHandler.runScript(this.properties.onNewSaveScript, this.scope);
            }
            if (!this.properties.onLoadScript.equals((Object) "")) {
                this.scriptHandler.runScript(this.properties.onLoadScript, this.scope);
            }
            this.firstTick = false;
        }
        ScriptModel.updateAll();
        if (!this.properties.onUpdateScript.equals((Object) "")) {
            this.scriptHandler.runScript(this.properties.onUpdateScript, this.scope);
        }
        this.fogColorOverridden = ((ExLevelProperties) this.properties).getOverrideFogColor();
        this.fogDensityOverridden = ((ExLevelProperties) this.properties).getOverrideFogDensity();
        this.method_245();
        this.cache.method_1801();
        int i = this.computeWeatherGradients(1.0f);
        if (i != this.field_202) {
            this.field_202 = i;
            for (Object listener : this.listeners) {
                ((LevelListener) listener).method_1148();
            }
        }
        if ((l1 = this.properties.getTime() + 1L) % (long) this.field_212 == 0L) {
            this.saveLevel(false, null);
        }
        this.properties.setTime(l1);
        ((ExLevelProperties) this.properties).addToTimeOfDay(((ExLevelProperties) this.properties).getTimeRate());
        this.method_194(false);
        this.method_248();
        if (this.properties.isRaining()) {
            this.DoSnowModUpdate();
        }
        this.script.wakeupScripts(l1);
    }

    private void DoSnowModUpdate() {
        if (this.isClient) {
            return;
        }
        if (this.coordOrder == null) {
            this.initCoordOrder();
        }
        for (Object player : this.players) {
            Player entityplayer = (Player) player;
            int pcx = MathsHelper.floor(entityplayer.x / 16.0);
            int pcz = MathsHelper.floor(entityplayer.z / 16.0);
            int radius = 9;
            for (int cx = -radius; cx <= radius; ++cx) {
                for (int cz = -radius; cz <= radius; ++cz) {
                    long iteration = (long) (cx + cz * 2) + this.getLevelTime();
                    if (iteration % 14L != 0L || !this.isChunkLoaded(cx + pcx, cz + pcz)) continue;
                    iteration /= 14L;
                    int chunkX = cx + pcx;
                    int chunkZ = cz + pcz;
                    iteration += chunkX * chunkX * 3121 + chunkX * 45238971 + chunkZ * chunkZ * 418711 + chunkZ * 13761;
                    iteration = Math.abs(iteration);
                    int x = chunkX * 16 + this.coordOrder[(int) (iteration % 256L)] % 16;
                    int z = chunkZ * 16 + this.coordOrder[(int) (iteration % 256L)] / 16;
                    this.SnowModUpdate(x, z);
                }
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    protected void method_245() {
        if (!this.dimension.fixedSpawnPos) {
            if (this.field_209 > 0) {
                --this.field_209;
            }
            this.prevRainGradient = this.rainGradient;
            this.rainGradient = this.properties.isRaining() ? (float) ((double) this.rainGradient + 0.01) : (float) ((double) this.rainGradient - 0.01);
            if (this.rainGradient < 0.0f) {
                this.rainGradient = 0.0f;
            }
            if (this.rainGradient > 1.0f) {
                this.rainGradient = 1.0f;
            }
            this.prevThunderGradient = this.thunderGradient;
            this.thunderGradient = this.properties.isThundering() ? (float) ((double) this.thunderGradient + 0.01) : (float) ((double) this.thunderGradient - 0.01);
            if (this.thunderGradient < 0.0f) {
                this.thunderGradient = 0.0f;
            }
            if (this.thunderGradient > 1.0f) {
                this.thunderGradient = 1.0f;
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    protected void method_248() {
        for (Object player : this.players) {
            Player entityplayer = (Player) player;
            int j = MathsHelper.floor(entityplayer.x / 16.0);
            int l = MathsHelper.floor(entityplayer.z / 16.0);
            int byte0 = 9;
            for (int j1 = -byte0; j1 <= byte0; ++j1) {
                for (int k2 = -byte0; k2 <= byte0; ++k2) {
                    this.updateChunk(j1 + j, k2 + l);
                }
            }
        }
        if (this.field_195 > 0) {
            --this.field_195;
        }
    }

    protected void updateChunk(int chunkX, int chunkZ) {
        Chunk chunk = this.getChunkFromCache(chunkX, chunkZ);
        if (chunk.lastUpdated == this.getLevelTime()) {
            return;
        }
        int coordX = chunkX * 16;
        int coordZ = chunkZ * 16;
        chunk.lastUpdated = this.getLevelTime();
        if (this.rand.nextInt(100000) == 0 && this.raining() && this.thundering()) {
            this.field_203 = this.field_203 * 3 + 1013904223;
            int l1 = this.field_203 >> 2;
            int i3 = coordX + (l1 & 0xF);
            int i4 = coordZ + (l1 >> 8 & 0xF);
            int i5 = this.getOceanFloorHeight(i3, i4);
            if (this.canRainAt(i3, i5, i4)) {
                this.method_184(new Lightning((Level) (Object) this, i3, i5, i4));
                this.field_209 = 2;
            }
        }
        for (int j2 = 0; j2 < 80; ++j2) {
            this.field_203 = this.field_203 * 3 + 1013904223;
            int k3 = this.field_203 >> 2;
            int k4 = k3 & 0xF;
            int k5 = k3 >> 8 & 0xF;
            int j6 = k3 >> 16 & 0x7F;
            int l6 = chunk.tiles[k4 << 11 | k5 << 7 | j6] & 0xFF;
            if (!Tile.TICKS_RANDOMLY[l6]) continue;
            Tile.BY_ID[l6].onScheduledTick((Level) (Object) this, k4 + coordX, j6, k5 + coordZ, this.rand);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public boolean method_194(boolean flag) {
        int i = this.field_183.size();
        if (i > 1000) {
            i = 1000;
        }
        for (int j = 0; j < i; ++j) {
            int k;
            int byte0;
            class_366 nextticklistentry = (class_366) this.field_183.first();
            if (!flag && nextticklistentry.field_1404 > this.properties.getTime()) break;
            this.field_183.remove(nextticklistentry);
            if (!this.field_184.remove(nextticklistentry) || !this.isRegionLoaded(nextticklistentry.field_1400 - (byte0 = 8), nextticklistentry.field_1401 - byte0, nextticklistentry.field_1402 - byte0, nextticklistentry.field_1400 + byte0, nextticklistentry.field_1401 + byte0, nextticklistentry.field_1402 + byte0) || (k = this.getTileId(nextticklistentry.field_1400, nextticklistentry.field_1401, nextticklistentry.field_1402)) != nextticklistentry.field_1403 || k <= 0)
                continue;
            Tile.BY_ID[k].onScheduledTick((Level) (Object) this, nextticklistentry.field_1400, nextticklistentry.field_1401, nextticklistentry.field_1402, this.rand);
            Box.method_85();
        }
        return this.field_183.size() != 0;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void checkSessionLock() {
        if (this.dimensionData != null) {
            this.dimensionData.checkSessionLock();
        } else {
            this.mapHandler.checkSessionLock();
        }
    }

    @Override
    public float getTimeOfDay() {
        return ((ExLevelProperties) this.properties).getTimeOfDay();
    }

    @Override
    public void setTimeOfDay(long l) {
        ((ExLevelProperties) this.properties).setTimeOfDay(l);
    }

    @Override
    public float getSpawnYaw() {
        return ((ExLevelProperties) this.properties).getSpawnYaw();
    }

    @Override
    public void setSpawnYaw(float y) {
        ((ExLevelProperties) this.properties).setSpawnYaw(y);
    }

    @Override
    public double getTemperatureValue(int x, int z) {
        if (x < -32000000 || z < -32000000 || x >= 32000000 || z > 32000000) {
            return 0.0;
        }
        ExChunk c = (ExChunk) this.getChunkFromCache(x >> 4, z >> 4);
        return c.getTemperatureValue(x & 0xF, z & 0xF) + ((ExLevelProperties) this.properties).getTempOffset();
    }

    @Override
    public void setTemperatureValue(int x, int z, double temp) {
        if (x < -32000000 || z < -32000000 || x >= 32000000 || z > 32000000) {
            return;
        }
        ExChunk c = (ExChunk) this.getChunkFromCache(x >> 4, z >> 4);
        if (c.getTemperatureValue(x & 0xF, z & 0xF) == temp) {
            return;
        }
        c.setTemperatureValue(x & 0xF, z & 0xF, temp);
    }

    @Override
    public void resetCoordOrder() {
        this.coordOrder = null;
    }

    private void initCoordOrder() {
        int i;
        Random r = new Random();
        r.setSeed((this.getLevelTime()));
        this.coordOrder = new int[256];
        for (i = 0; i < 256; ++i) {
            this.coordOrder[i] = i;
        }
        for (i = 0; i < 255; ++i) {
            int j = r.nextInt(256 - i);
            int t = this.coordOrder[i];
            this.coordOrder[i] = this.coordOrder[i + j];
            this.coordOrder[i + j] = t;
        }
    }

    public boolean SnowModUpdate(int x, int z) {
        int height = this.getOceanFloorHeight(x, z);
        if (height < 0) {
            height = 0;
        }
        int topBlock = this.getTileId(x, height - 1, z);
        if (this.getTemperatureValue(x, z) < 0.5) {
            if (!this.isAir(x, height, z)) {
                return false;
            }
            if (topBlock != 0 && Tile.BY_ID[topBlock].isFullOpaque()) {
                if (!this.getMaterial(x, height - 1, z).blocksMovement()) {
                    return false;
                }
                if (this.getLightLevel(LightType.BLOCK, x, height, z) > 11) {
                    return false;
                }
                this.setTile(x, height, z, Tile.SNOW.id);
            } else if (topBlock == Tile.FLOWING_WATER.id && this.getTileMeta(x, height - 1, z) == 0) {
                if (this.getLightLevel(LightType.BLOCK, x, height, z) > 11) {
                    return false;
                }
                this.setTile(x, height - 1, z, Tile.ICE.id);
            }
            return true;
        }
        int aboveTopBlock = this.getTileId(x, height, z);
        if (aboveTopBlock == Tile.SNOW.id) {
            this.setTile(x, height, z, 0);
            return true;
        }
        if (topBlock == Tile.SNOW_BLOCK.id) {
            this.setTile(x, height - 1, z, Tile.SNOW.id);
            return true;
        }
        if (topBlock == Tile.ICE.id) {
            this.setTile(x, height - 1, z, Tile.FLOWING_WATER.id);
            return true;
        }
        return false;
    }

    public void loadMapMusic() {
        File musicDir = new File(this.levelDir, "music");
        if (musicDir.exists() && musicDir.isDirectory()) {
            String streamName;
            File[] musicFiles;
            int musicCount = 0;
            for (File musicFile : musicFiles = musicDir.listFiles()) {
                if (!musicFile.isFile() || !musicFile.getName().endsWith(".ogg")) continue;
                streamName = String.format("music/%s", musicFile.getName().toLowerCase());
                AccessMinecraft.getInstance().soundHelper.method_2016(streamName, musicFile);
                ++musicCount;
            }
            this.musicList = new String[musicCount];
            musicCount = 0;
            for (File musicFile : musicFiles) {
                if (!musicFile.isFile() || !musicFile.getName().endsWith(".ogg")) continue;
                this.musicList[musicCount] = streamName = String.format("music.%s", musicFile.getName().toLowerCase().replace(".ogg", ""));
                ++musicCount;
            }
        } else {
            this.musicList = new String[0];
        }
        if (!this.properties.playingMusic.equals((Object) "")) {
            AccessMinecraft.getInstance().soundHelper.playMusicFromStreaming(this.properties.playingMusic, 0, 0);
        }
    }

    public void loadMapSounds() {
        File soundDir = new File(this.levelDir, "sound");
        if (soundDir.exists() && soundDir.isDirectory()) {
            String streamName;
            File[] soundFiles;
            int soundCount = 0;
            for (File soundFile : soundFiles = soundDir.listFiles()) {
                if (!soundFile.isFile() || !soundFile.getName().endsWith(".ogg")) continue;
                streamName = String.format("sound/%s", soundFile.getName().toLowerCase());
                AccessMinecraft.getInstance().soundHelper.method_2011(streamName, soundFile);
                ++soundCount;
            }
            this.soundList = new String[soundCount];
            soundCount = 0;
            for (File soundFile : soundFiles) {
                if (!soundFile.isFile() || !soundFile.getName().endsWith(".ogg")) continue;
                this.soundList[soundCount] = streamName = String.format("sound.%s", soundFile.getName().toLowerCase().replace(".ogg", ""));
                ++soundCount;
            }
        } else {
            this.soundList = new String[0];
        }
    }

    public void loadSoundOverrides() {
        AccessMinecraft.getInstance().resourceDownloadThread.method_107();
        File soundDir = new File(this.levelDir, "soundOverrides");
        if (soundDir.exists()) {
            AccessMinecraft.getInstance().resourceDownloadThread.method_108(soundDir, "");
        }
    }

    @Override
    public void loadBrightness() {
        for (int i = 0; i < 16; ++i) {
            this.dimension.field_2178[i] = ((ExLevelProperties) this.properties).getBrightness()[i];
        }
    }

    @Override
    public void undo() {
        this.undoStack.undo((Level) (Object) this);
    }

    @Override
    public void redo() {
        this.undoStack.redo((Level) (Object) this);
    }

    @Override
    public Entity getEntityByID(int entityID) {
        for (Entity e : this.entities) {
            if (e.id != entityID) continue;
            return e;
        }
        return null;
    }

    @Override
    public File getLevelDir() {
        return this.levelDir;
    }

    @Override
    public TriggerManager getTriggerManager() {
        return this.triggerManager;
    }

    @Override
    public Script getScript() {
        return this.script;
    }

    @Override
    public JScriptHandler getScriptHandler() {
        return this.scriptHandler;
    }

    @Override
    public Scriptable getScope() {
        return this.scope;
    }

    @Override
    public UndoStack getUndoStack() {
        return this.undoStack;
    }

    @Override
    public LevelProperties getLevelProperties() {
        return properties;
    }

}
