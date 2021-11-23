package io.github.ryuu.adventurecraft.mixin.level;

import io.github.ryuu.adventurecraft.blocks.BlockStairMulti;
import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityNpcPath;
import io.github.ryuu.adventurecraft.items.ItemCustom;
import io.github.ryuu.adventurecraft.util.*;
import net.minecraft.class_108;
import net.minecraft.class_366;
import net.minecraft.class_61;
import net.minecraft.client.Minecraft;
import net.minecraft.client.colour.FoliageColour;
import net.minecraft.client.colour.GrassColour;
import net.minecraft.client.render.*;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.entity.Lightning;
import net.minecraft.entity.player.Player;
import net.minecraft.level.*;
import net.minecraft.level.biome.Biome;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.level.chunk.ChunkIO;
import net.minecraft.level.chunk.ClientChunkCache;
import net.minecraft.level.dimension.Dimension;
import net.minecraft.level.dimension.DimensionData;
import net.minecraft.level.dimension.McRegionDimensionFile;
import net.minecraft.level.source.LevelSource;
import net.minecraft.level.storage.MapStorageBase;
import net.minecraft.script.EntityDescriptions;
import net.minecraft.script.ScopeTag;
import net.minecraft.script.Script;
import net.minecraft.script.ScriptModel;
import net.minecraft.src.Entity;
import net.minecraft.tile.FluidTile;
import net.minecraft.tile.LadderTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.ProgressListener;
import net.minecraft.util.Vec3i;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathsHelper;
import net.minecraft.util.maths.Vec3f;
import org.mozilla.javascript.Scriptable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

@Mixin(Level.class)
public class MixinLevel implements TileView {

    static int field_179 = 0;
    public final Dimension dimension;
    protected final int unusedIncrement = 1013904223;
    protected final DimensionData dimensionData;
    protected final DimensionData mapHandler;
    private final List field_181;
    private final List field_182;
    private final TreeSet field_183;
    private final Set field_184;
    private final List field_185;
    private final long field_186;
    private final ArrayList field_189;
    private final List field_196;
    @Shadow()
    public boolean field_197;
    public List<Entity> entities;
    public List tileEntities;
    public List players;
    public List field_201;
    public int field_202;
    public int field_210;
    public boolean field_211;
    public int difficulty;
    public Random rand;
    public boolean generating;
    public LevelProperties properties;
    public boolean forceLoadChunks;
    public LevelData data;
    public boolean isClient;
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
    protected int field_203;
    protected float prevRainGradient;
    protected float rainGradient;
    protected float prevThunderGradient;
    protected float thunderGradient;
    protected int field_209;
    protected int field_212;
    protected List listeners;
    protected LevelSource cache;
    boolean firstTick;
    boolean newSave;
    private long time;
    private boolean allPlayersSleeping;
    private boolean field_190;
    private int field_191;
    private boolean field_192;
    private boolean field_193;
    private Set field_194;
    private int field_195;
    private int[] coordOrder;

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
        this.script = new Script(this);
        if (this.properties.globalScope != null) {
            ScopeTag.loadScopeFromTag(this.script.globalScope, this.properties.globalScope);
        }
        this.scriptHandler = new JScriptHandler(this, levelFile);
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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void loadMapTextures() {
        Minecraft.minecraftInstance.textureManager.reload();
        for (Object obj : Minecraft.minecraftInstance.textureManager.TEXTURE_ID_MAP.entrySet()) {
            Map.Entry entry = (Map.Entry) obj;
            String texName = (String) entry.getKey();
            int texID = (Integer) entry.getValue();
            try {
                Minecraft.minecraftInstance.textureManager.loadTexture(texID, texName);
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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void loadTextureAnimations() {
        Minecraft.minecraftInstance.textureManager.clearTextureAnimations();
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
                        Minecraft.minecraftInstance.textureManager.registerTextureAnimation(parts[0].trim(), t);
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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void updateChunkProvider() {
        this.cache = this.createChunkCache();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
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
        return new ClientChunkCache(this, ichunkloader, this.dimension.createLevelSource());
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void computeSpawnPosition() {
        this.forceLoadChunks = true;
        int i = 0;
        int byte0 = 64;
        int j = 0;
        while (!this.dimension.isValidSpawnPos(i, j)) {
            i += this.rand.nextInt(64) - this.rand.nextInt(64);
            j += this.rand.nextInt(64) - this.rand.nextInt(64);
        }
        this.properties.setSpawnPosition(i, this.getFirstUncoveredBlockY(i, j), j);
        this.forceLoadChunks = false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void revalidateSpawnPos() {
        if (this.properties.getSpawnY() <= 0) {
            this.properties.setSpawnY(64);
        }
        int i = this.properties.getSpawnX();
        int j = this.properties.getSpawnZ();
        while (this.getTileAtSurface(i, j) == 0) {
            i += this.rand.nextInt(8) - this.rand.nextInt(8);
            j += this.rand.nextInt(8) - this.rand.nextInt(8);
        }
        this.properties.setSpawnX(i);
        this.properties.setSpawnY(this.getFirstUncoveredBlockY(i, j));
        this.properties.setSpawnZ(j);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getFirstUncoveredBlockY(int i, int j) {
        int k;
        for (k = 127; this.isAir(i, k, j) && k > 0; --k) {
        }
        return k;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getTileAtSurface(int x, int z) {
        int k;
        for (k = 127; this.isAir(x, k, z) && k > 0; --k) {
        }
        return this.getTileId(x, k, z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void addPlayer(Player player) {
        try {
            CompoundTag nbttagcompound = this.properties.getPlayerData();
            if (nbttagcompound != null) {
                player.fromTag(nbttagcompound);
                this.properties.setPlayerData(null);
            }
            if (this.cache instanceof ClientChunkCache) {
                ClientChunkCache chunkproviderloadorgenerate = (ClientChunkCache) this.cache;
                int i = MathsHelper.floor((int) player.x) >> 4;
                int j = MathsHelper.floor((int) player.z) >> 4;
                chunkproviderloadorgenerate.setSpawnChunk(i, j);
            }
            this.spawnEntity(player);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void saveLevel(boolean flag, ProgressListener listener) {
        if (!this.cache.isClean()) {
            return;
        }
        if (listener != null) {
            listener.notifyIgnoreGameRunning("Saving level");
        }
        this.prepareSaveLevel();
        if (listener != null) {
            listener.notifySubMessage("Saving chunks");
        }
        this.cache.saveChunks(flag, listener);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_274(int i) {
        if (!this.cache.isClean()) {
            return true;
        }
        if (i == 0) {
            this.prepareSaveLevel();
        }
        return this.cache.saveChunks(false, null);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getTileId(int x, int y, int z) {
        if (x < -32000000 || z < -32000000 || x >= 32000000 || z > 32000000) {
            return 0;
        }
        if (y < 0) {
            return 0;
        }
        if (y >= 128) {
            return 0;
        }
        return this.getChunkFromCache(x >> 4, z >> 4).getTileId(x & 0xF, y, z & 0xF);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isAir(int x, int y, int z) {
        return this.getTileId(x, y, z) == 0;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isTileLoaded(int x, int y, int z) {
        if (y < 0 || y >= 128) {
            return false;
        }
        return this.isChunkLoaded(x >> 4, z >> 4);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isRegionLoaded(int x, int y, int z, int radius) {
        return this.isRegionLoaded(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isRegionLoaded(int startX, int startY, int startZ, int endX, int endY, int endZ) {
        if (endY < 0 || startY >= 128) {
            return false;
        }
        startX >>= 4;
        startY >>= 4;
        startZ >>= 4;
        endX >>= 4;
        endY >>= 4;
        endZ >>= 4;
        for (int k1 = startX; k1 <= endX; ++k1) {
            for (int l1 = startZ; l1 <= endZ; ++l1) {
                if (this.isChunkLoaded(k1, l1)) continue;
                return false;
            }
        }
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private boolean isChunkLoaded(int chunkX, int chunkY) {
        return this.cache.isChunkLoaded(chunkX, chunkY);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public Chunk getChunk(int x, int z) {
        return this.getChunkFromCache(x >> 4, z >> 4);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public Chunk getChunkFromCache(int x, int z) {
        return this.cache.getChunk(x, z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean setTileWithMetadata(int x, int y, int z, int tileId, int metadata) {
        if (x < -32000000 || z < -32000000 || x >= 32000000 || z > 32000000) {
            return false;
        }
        if (y < 0) {
            return false;
        }
        if (y >= 128) {
            return false;
        }
        Chunk chunk = this.getChunkFromCache(x >> 4, z >> 4);
        return chunk.setTileWithMetadata(x & 0xF, y, z & 0xF, tileId, metadata);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
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
        return chunk.setBlockIDWithMetadataTemp(i & 0xF, j, k & 0xF, l, i1);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean setTileInChunk(int x, int y, int z, int tileId) {
        if (x < -32000000 || z < -32000000 || x >= 32000000 || z > 32000000) {
            return false;
        }
        if (y < 0) {
            return false;
        }
        if (y >= 128) {
            return false;
        }
        Chunk chunk = this.getChunkFromCache(x >> 4, z >> 4);
        return chunk.setTile(x & 0xF, y, z & 0xF, tileId);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public Material getMaterial(int x, int y, int z) {
        int l = this.getTileId(x, y, z);
        if (l == 0) {
            return Material.AIR;
        }
        return Tile.BY_ID[l].material;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getTileMeta(int x, int y, int z) {
        if (x < -32000000 || z < -32000000 || x >= 32000000 || z > 32000000) {
            return 0;
        }
        if (y < 0) {
            return 0;
        }
        if (y >= 128) {
            return 0;
        }
        Chunk chunk = this.getChunkFromCache(x >> 4, z >> 4);
        return chunk.getMetadata(x &= 0xF, y, z &= 0xF);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setTileMeta(int x, int y, int z, int meta) {
        if (this.method_223(x, y, z, meta)) {
            int i1 = this.getTileId(x, y, z);
            if (Tile.MULTIPLE_STATES[i1 & 0xFF]) {
                this.method_235(x, y, z, i1);
            } else {
                this.method_244(x, y, z, i1);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_223(int i, int j, int k, int l) {
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
        chunk.setMetadata(i &= 0xF, j, k &= 0xF, l);
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean setTile(int i, int j, int k, int l) {
        if (this.setTileInChunk(i, j, k, l)) {
            this.method_235(i, j, k, l);
            return true;
        }
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_201(int i, int j, int k, int l, int i1) {
        if (this.setTileWithMetadata(i, j, k, l, i1)) {
            this.method_235(i, j, k, l);
            return true;
        }
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_243(int i, int j, int k) {
        for (int l = 0; l < this.listeners.size(); ++l) {
            ((LevelListener) this.listeners.get(l)).method_1149(i, j, k);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void method_235(int i, int j, int k, int l) {
        this.method_243(i, j, k);
        this.method_244(i, j, k, l);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_240(int i, int j, int k, int l) {
        if (k > l) {
            int i1 = l;
            l = k;
            k = i1;
        }
        this.updateRedstone(i, k, j, i, l, j);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_246(int i, int j, int k) {
        for (int l = 0; l < this.listeners.size(); ++l) {
            ((LevelListener) this.listeners.get(l)).method_1150(i, j, k, i, j, k);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void updateRedstone(int i, int j, int k, int l, int i1, int j1) {
        for (int k1 = 0; k1 < this.listeners.size(); ++k1) {
            ((LevelListener) this.listeners.get(k1)).method_1150(i, j, k, l, i1, j1);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_244(int i, int j, int k, int l) {
        this.method_253(i - 1, j, k, l);
        this.method_253(i + 1, j, k, l);
        this.method_253(i, j - 1, k, l);
        this.method_253(i, j + 1, k, l);
        this.method_253(i, j, k - 1, l);
        this.method_253(i, j, k + 1, l);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_253(int i, int j, int k, int l) {
        if (this.field_211 || this.isClient) {
            return;
        }
        Tile block = Tile.BY_ID[this.getTileId(i, j, k)];
        if (block != null) {
            block.method_1609(this, i, j, k, l);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isAboveGroundCached(int x, int y, int z) {
        return this.getChunkFromCache(x >> 4, z >> 4).isAboveGround(x & 0xF, y, z & 0xF);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int method_252(int i, int j, int k) {
        if (j < 0) {
            return 0;
        }
        if (j >= 128) {
            j = 127;
        }
        return this.getChunkFromCache(i >> 4, k >> 4).getAbsoluteLight(i & 0xF, j, k & 0xF, 0);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getLightLevel(int i, int j, int k) {
        return this.placeTile(i, j, k, true);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int placeTile(int x, int y, int z, boolean flag) {
        int l;
        if (x < -32000000 || z < -32000000 || x >= 32000000 || z > 32000000) {
            return 15;
        }
        if (flag && (l = this.getTileId(x, y, z)) != 0 && (l == Tile.STONE_SLAB.id || l == Tile.FARMLAND.id || l == Tile.STAIRS_STONE.id || l == Tile.STAIRS_WOOD.id || Tile.BY_ID[l] instanceof BlockStairMulti)) {
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
            return i1;
        }
        if (y < 0) {
            return 0;
        }
        if (y >= 128) {
            y = 127;
        }
        Chunk chunk = this.getChunkFromCache(x >> 4, z >> 4);
        return chunk.getAbsoluteLight(x &= 0xF, y, z &= 0xF, this.field_202);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isAboveGround(int x, int y, int z) {
        if (x < -32000000 || z < -32000000 || x >= 32000000 || z > 32000000) {
            return false;
        }
        if (y < 0) {
            return false;
        }
        if (y >= 128) {
            return true;
        }
        if (!this.isChunkLoaded(x >> 4, z >> 4)) {
            return false;
        }
        Chunk chunk = this.getChunkFromCache(x >> 4, z >> 4);
        return chunk.isAboveGround(x &= 0xF, y, z &= 0xF);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getHeight(int x, int z) {
        if (x < -32000000 || z < -32000000 || x >= 32000000 || z > 32000000) {
            return 0;
        }
        if (!this.isChunkLoaded(x >> 4, z >> 4)) {
            return 0;
        }
        Chunk chunk = this.getChunkFromCache(x >> 4, z >> 4);
        return chunk.getHeight(x & 0xF, z & 0xF);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_165(LightType tpe, int i, int j, int k, int l) {
        int i1;
        if (this.dimension.fixedSpawnPos && tpe == LightType.Sky) {
            return;
        }
        if (!this.isTileLoaded(i, j, k)) {
            return;
        }
        if (tpe == LightType.Sky) {
            if (this.isAboveGround(i, j, k)) {
                l = 15;
            }
        } else if (tpe == LightType.Block && Tile.BY_ID[i1 = this.getTileId(i, j, k)] != null && Tile.BY_ID[i1].getBlockLightValue(this, i, j, k) < l) {
            l = Tile.BY_ID[i1].getBlockLightValue(this, i, j, k);
        }
        if (this.getLightLevel(tpe, i, j, k) != l) {
            this.method_166(tpe, i, j, k, i, j, k);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getLightLevel(LightType enumskyblock, int i, int j, int k) {
        if (j < 0) {
            j = 0;
        }
        if (j >= 128) {
            j = 127;
        }
        if (j < 0 || j >= 128 || i < -32000000 || k < -32000000 || i >= 32000000 || k > 32000000) {
            return enumskyblock.field_2759;
        }
        int l = i >> 4;
        int i1 = k >> 4;
        if (!this.isChunkLoaded(l, i1)) {
            return 0;
        }
        Chunk chunk = this.getChunkFromCache(l, i1);
        return chunk.getLightLevel(enumskyblock, i & 0xF, j, k & 0xF);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setLightLevel(LightType type, int x, int y, int z, int light) {
        if (x < -32000000 || z < -32000000 || x >= 32000000 || z > 32000000) {
            return;
        }
        if (y < 0) {
            return;
        }
        if (y >= 128) {
            return;
        }
        if (!this.isChunkLoaded(x >> 4, z >> 4)) {
            return;
        }
        Chunk chunk = this.getChunkFromCache(x >> 4, z >> 4);
        chunk.setLightLevel(type, x & 0xF, y, z & 0xF, light);
        for (int i1 = 0; i1 < this.listeners.size(); ++i1) {
            ((LevelListener) this.listeners.get(i1)).method_1149(x, y, z);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float getLightValue(int i, int j, int k) {
        float torchLight;
        int lightValue = this.getLightLevel(i, j, k);
        if ((float) lightValue < (torchLight = PlayerTorch.getTorchLight(this, i, j, k))) {
            return Math.min(torchLight, 15.0f);
        }
        return lightValue;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private float getBrightnessLevel(float lightValue) {
        int floorValue = (int) Math.floor(lightValue);
        if ((float) floorValue != lightValue) {
            int ceilValue = (int) Math.ceil(lightValue);
            float lerpValue = lightValue - (float) floorValue;
            return (1.0f - lerpValue) * this.dimension.field_2178[floorValue] + lerpValue * this.dimension.field_2178[ceilValue];
        }
        return this.dimension.field_2178[floorValue];
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
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
    @Overwrite()
    public float getBrightness(int i, int j, int k) {
        float lightValue = this.getLightValue(i, j, k);
        return this.getBrightnessLevel(lightValue);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float getDayLight() {
        int lightValue = 15 - this.field_202;
        return this.dimension.field_2178[lightValue];
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public HitResult raycast(Vec3f vec3d, Vec3f vec3d1) {
        return this.raycast(vec3d, vec3d1, false, false);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public HitResult raycast(Vec3f vec3d, Vec3f vec3d1, boolean flag) {
        return this.raycast(vec3d, vec3d1, flag, false);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public HitResult raycast(Vec3f vec3d, Vec3f vec3d1, boolean flag, boolean flag1) {
        return this.rayTraceBlocks2(vec3d, vec3d1, flag, flag1, true);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public HitResult rayTraceBlocks2(Vec3f vec3d, Vec3f vec3d1, boolean flag, boolean flag1, boolean collideWithClip) {
        HitResult movingobjectposition;
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
        if ((!flag1 || block == null || block.getCollisionShape(this, l, i1, j1) != null) && k1 > 0 && block.method_1571(i2, flag) && (collideWithClip || k1 != Blocks.clipBlock.id && !LadderTile.isLadderID(k1)) && (movingobjectposition = block.raycast(this, l, i1, j1, vec3d, vec3d1)) != null) {
            return movingobjectposition;
        }
        int l1 = 200;
        while (l1-- >= 0) {
            HitResult movingobjectposition1;
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
                d = (double) l + 1.0;
            } else if (i < l) {
                d = (double) l + 0.0;
            } else {
                flag2 = false;
            }
            if (j > i1) {
                d1 = (double) i1 + 1.0;
            } else if (j < i1) {
                d1 = (double) i1 + 0.0;
            } else {
                flag3 = false;
            }
            if (k > j1) {
                d2 = (double) j1 + 1.0;
            } else if (k < j1) {
                d2 = (double) j1 + 0.0;
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
            int byte0 = 0;
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
            if (flag1 && block1 != null && block1.getCollisionShape(this, l, i1, j1) == null || j2 <= 0 || !block1.method_1571(k2, flag) || !block1.shouldRender(this, l, i1, j1) || (movingobjectposition1 = block1.raycast(this, l, i1, j1, vec3d, vec3d1)) == null || !collideWithClip && (block1.id == Blocks.clipBlock.id || LadderTile.isLadderID(block1.id)))
                continue;
            return movingobjectposition1;
        }
        return null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void playSound(net.minecraft.entity.Entity entity, String sound, float f, float f1) {
        for (int i = 0; i < this.listeners.size(); ++i) {
            ((LevelListener) this.listeners.get(i)).playSound(sound, entity.x, entity.y - (double) entity.standingEyeHeight, entity.z, f, f1);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void playSound(double x, double y, double z, String sound, float f, float f1) {
        for (int i = 0; i < this.listeners.size(); ++i) {
            ((LevelListener) this.listeners.get(i)).playSound(sound, x, y, z, f, f1);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_179(String s, int i, int j, int k) {
        for (int l = 0; l < this.listeners.size(); ++l) {
            ((LevelListener) this.listeners.get(l)).method_1155(s, i, j, k);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void addParticle(String particle, double d, double d1, double d2, double d3, double d4, double d5) {
        for (int i = 0; i < this.listeners.size(); ++i) {
            ((LevelListener) this.listeners.get(i)).addParticle(particle, d, d1, d2, d3, d4, d5);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_184(net.minecraft.entity.Entity entity) {
        this.field_201.add(entity);
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean spawnEntity(net.minecraft.entity.Entity entity) {
        int i = MathsHelper.floor(entity.x / 16.0);
        int j = MathsHelper.floor(entity.z / 16.0);
        boolean flag = entity instanceof Player;
        if (flag || this.isChunkLoaded(i, j)) {
            if (entity instanceof Player) {
                Player entityplayer = (Player) entity;
                this.players.add(entityplayer);
                this.areAllPlayersSleeping();
            }
            this.getChunkFromCache(i, j).addEntity(entity);
            if (!this.entities.contains((Object) entity)) {
                this.entities.add((Object) entity);
            }
            this.onEntityAdded(entity);
            return true;
        }
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void onEntityAdded(net.minecraft.entity.Entity entity) {
        for (int i = 0; i < this.listeners.size(); ++i) {
            ((LevelListener) this.listeners.get(i)).onEntityAdded(entity);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void onEntityRemoved(net.minecraft.entity.Entity entity) {
        for (int i = 0; i < this.listeners.size(); ++i) {
            ((LevelListener) this.listeners.get(i)).onEntityRemoved(entity);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void removeEntity(net.minecraft.entity.Entity entity) {
        if (entity.passenger != null) {
            entity.passenger.startRiding(null);
        }
        if (entity.vehicle != null) {
            entity.startRiding(null);
        }
        entity.remove();
        if (entity instanceof Player) {
            this.players.remove(entity);
            this.areAllPlayersSleeping();
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void removeEntityFromChunk(net.minecraft.entity.Entity entity) {
        entity.remove();
        if (entity instanceof Player) {
            this.players.remove(entity);
            this.areAllPlayersSleeping();
        }
        int n = entity.chunkX;
        int n2 = entity.chunkZ;
        if (entity.shouldTick && this.isChunkLoaded(n, n2)) {
            this.getChunkFromCache(n, n2).removeEntity(entity);
        }
        this.entities.remove((Object) entity);
        this.onEntityRemoved(entity);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void addListener(LevelListener listener) {
        this.listeners.add(listener);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void removeListener(LevelListener listener) {
        this.listeners.remove(listener);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public List method_190(net.minecraft.entity.Entity entity, Box axisalignedbb) {
        this.field_189.clear();
        int i = MathsHelper.floor(axisalignedbb.minX);
        int j = MathsHelper.floor(axisalignedbb.maxX + 1.0);
        int k = MathsHelper.floor(axisalignedbb.minY);
        int l = MathsHelper.floor(axisalignedbb.maxY + 1.0);
        int i1 = MathsHelper.floor(axisalignedbb.minZ);
        int j1 = MathsHelper.floor(axisalignedbb.maxZ + 1.0);
        for (int k1 = i; k1 < j; ++k1) {
            for (int l1 = i1; l1 < j1; ++l1) {
                if (!this.isTileLoaded(k1, 64, l1)) continue;
                for (int i2 = k - 1; i2 < l; ++i2) {
                    Tile block = Tile.BY_ID[this.getTileId(k1, i2, l1)];
                    if (block == null || !entity.collidesWithClipBlocks && (block.id == Blocks.clipBlock.id || LadderTile.isLadderID(block.id)))
                        continue;
                    block.intersectsInLevel(this, k1, i2, l1, axisalignedbb, this.field_189);
                }
            }
        }
        double d = 0.25;
        List list = this.getEntities(entity, axisalignedbb.expand(d, d, d));
        for (int j2 = 0; j2 < list.size(); ++j2) {
            Box axisalignedbb1 = ((net.minecraft.entity.Entity) list.get(j2)).method_1381();
            if (axisalignedbb1 != null && axisalignedbb1.intersects(axisalignedbb)) {
                this.field_189.add(axisalignedbb1);
            }
            if ((axisalignedbb1 = entity.method_1379((net.minecraft.entity.Entity) list.get(j2))) == null || !axisalignedbb1.intersects(axisalignedbb))
                continue;
            this.field_189.add(axisalignedbb1);
        }
        return this.field_189;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int computeWeatherGradients(float f) {
        float f1 = this.method_198(f);
        float f2 = 1.0f - (MathsHelper.cos(f1 * 3.141593f * 2.0f) * 2.0f + 0.5f);
        if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        if (f2 > 1.0f) {
            f2 = 1.0f;
        }
        f2 = 1.0f - f2;
        f2 = (float) ((double) f2 * (1.0 - (double) (this.getRainGradient(f) * 5.0f) / 16.0));
        f2 = (float) ((double) f2 * (1.0 - (double) (this.getThunderGradient(f) * 5.0f) / 16.0));
        f2 = 1.0f - f2;
        return (int) (f2 * 11.0f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public Vec3f method_279(net.minecraft.entity.Entity entity, float f) {
        float f9;
        float f1 = this.method_198(f);
        float f2 = MathsHelper.cos(f1 * 3.141593f * 2.0f) * 2.0f + 0.5f;
        if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        if (f2 > 1.0f) {
            f2 = 1.0f;
        }
        int i = MathsHelper.floor(entity.x);
        int j = MathsHelper.floor(entity.z);
        float f3 = (float) this.getBiomeSource().getTemperature(i, j);
        int k = this.getBiomeSource().getBiome(i, j).getSkyColour(f3);
        float f4 = (float) (k >> 16 & 0xFF) / 255.0f;
        float f5 = (float) (k >> 8 & 0xFF) / 255.0f;
        float f6 = (float) (k & 0xFF) / 255.0f;
        f4 *= f2;
        f5 *= f2;
        f6 *= f2;
        float f7 = this.getRainGradient(f);
        if (f7 > 0.0f) {
            float f8 = (f4 * 0.3f + f5 * 0.59f + f6 * 0.11f) * 0.6f;
            float f10 = 1.0f - f7 * 0.75f;
            f4 = f4 * f10 + f8 * (1.0f - f10);
            f5 = f5 * f10 + f8 * (1.0f - f10);
            f6 = f6 * f10 + f8 * (1.0f - f10);
        }
        if ((f9 = this.getThunderGradient(f)) > 0.0f) {
            float f11 = (f4 * 0.3f + f5 * 0.59f + f6 * 0.11f) * 0.2f;
            float f13 = 1.0f - f9 * 0.75f;
            f4 = f4 * f13 + f11 * (1.0f - f13);
            f5 = f5 * f13 + f11 * (1.0f - f13);
            f6 = f6 * f13 + f11 * (1.0f - f13);
        }
        if (this.field_210 > 0) {
            float f12 = (float) this.field_210 - f;
            if (f12 > 1.0f) {
                f12 = 1.0f;
            }
            f4 = f4 * (1.0f - (f12 *= 0.45f)) + 0.8f * f12;
            f5 = f5 * (1.0f - f12) + 0.8f * f12;
            f6 = f6 * (1.0f - f12) + 1.0f * f12;
        }
        return Vec3f.from(f4, f5, f6);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float method_198(float f) {
        return this.dimension.method_1771(this.properties.getTimeOfDay(), f * this.properties.getTimeRate());
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public Vec3f method_282(float f) {
        float f1 = this.method_198(f);
        float f2 = MathsHelper.cos(f1 * 3.141593f * 2.0f) * 2.0f + 0.5f;
        if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        if (f2 > 1.0f) {
            f2 = 1.0f;
        }
        float f3 = (float) (this.field_186 >> 16 & 0xFFL) / 255.0f;
        float f4 = (float) (this.field_186 >> 8 & 0xFFL) / 255.0f;
        float f5 = (float) (this.field_186 & 0xFFL) / 255.0f;
        float f6 = this.getRainGradient(f);
        if (f6 > 0.0f) {
            float f7 = (f3 * 0.3f + f4 * 0.59f + f5 * 0.11f) * 0.6f;
            float f9 = 1.0f - f6 * 0.95f;
            f3 = f3 * f9 + f7 * (1.0f - f9);
            f4 = f4 * f9 + f7 * (1.0f - f9);
            f5 = f5 * f9 + f7 * (1.0f - f9);
        }
        f3 *= f2 * 0.9f + 0.1f;
        f4 *= f2 * 0.9f + 0.1f;
        f5 *= f2 * 0.85f + 0.15f;
        float f8 = this.getThunderGradient(f);
        if (f8 > 0.0f) {
            float f10 = (f3 * 0.3f + f4 * 0.59f + f5 * 0.11f) * 0.2f;
            float f11 = 1.0f - f8 * 0.95f;
            f3 = f3 * f11 + f10 * (1.0f - f11);
            f4 = f4 * f11 + f10 * (1.0f - f11);
            f5 = f5 * f11 + f10 * (1.0f - f11);
        }
        return Vec3f.from(f3, f4, f5);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public Vec3f getSkyColour(float f) {
        float f1 = this.method_198(f);
        Vec3f returnColor = this.dimension.getSkyColour(f1, f);
        if (this.properties.overrideFogColor) {
            if (this.fogColorOverridden) {
                returnColor.x = this.properties.fogR;
                returnColor.y = this.properties.fogG;
                returnColor.z = this.properties.fogB;
            } else {
                returnColor.x = (double) (1.0f - f) * returnColor.x + (double) (f * this.properties.fogR);
                returnColor.y = (double) (1.0f - f) * returnColor.y + (double) (f * this.properties.fogG);
                returnColor.z = (double) (1.0f - f) * returnColor.z + (double) (f * this.properties.fogB);
            }
        }
        return returnColor;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float getFogStart(float start, float f) {
        if (this.properties.overrideFogDensity) {
            if (this.fogDensityOverridden) {
                return this.properties.fogStart;
            }
            return f * this.properties.fogStart + (1.0f - f) * start;
        }
        return start;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float getFogEnd(float end, float f) {
        if (this.properties.overrideFogDensity) {
            if (this.fogDensityOverridden) {
                return this.properties.fogEnd;
            }
            return f * this.properties.fogEnd + (1.0f - f) * end;
        }
        return end;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getOceanFloorHeight(int x, int z) {
        int k;
        Chunk chunk = this.getChunk(x, z);
        for (k = 127; this.getMaterial(x, k, z).blocksMovement() && k > 0; --k) {
        }
        x &= 0xF;
        z &= 0xF;
        while (k > 0) {
            Material material;
            int l = chunk.getTileId(x, k, z);
            Material material2 = material = l != 0 ? Tile.BY_ID[l].material : Material.AIR;
            if (!material.blocksMovement() && !material.isLiquid()) {
                --k;
                continue;
            }
            return k + 1;
        }
        return -1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float method_286(float f) {
        float f1 = this.method_198(f);
        float f2 = 1.0f - (MathsHelper.cos(f1 * 3.141593f * 2.0f) * 2.0f + 0.75f);
        if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        if (f2 > 1.0f) {
            f2 = 1.0f;
        }
        return f2 * f2 * 0.5f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_216(int i, int j, int k, int l, int i1) {
        class_366 nextticklistentry = new class_366(i, j, k, l);
        int byte0 = 8;
        if (this.field_197) {
            int j1;
            if (this.isRegionLoaded(nextticklistentry.field_1400 - byte0, nextticklistentry.field_1401 - byte0, nextticklistentry.field_1402 - byte0, nextticklistentry.field_1400 + byte0, nextticklistentry.field_1401 + byte0, nextticklistentry.field_1402 + byte0) && (j1 = this.getTileId(nextticklistentry.field_1400, nextticklistentry.field_1401, nextticklistentry.field_1402)) == nextticklistentry.field_1403 && j1 > 0) {
                Tile.BY_ID[j1].onScheduledTick(this, nextticklistentry.field_1400, nextticklistentry.field_1401, nextticklistentry.field_1402, this.rand);
            }
            return;
        }
        if (this.isRegionLoaded(i - byte0, j - byte0, k - byte0, i + byte0, j + byte0, k + byte0)) {
            if (l > 0) {
                nextticklistentry.method_1197((long) i1 + this.properties.getTime());
            }
            if (!this.field_184.contains(nextticklistentry)) {
                this.field_184.add(nextticklistentry);
                this.field_183.add(nextticklistentry);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void cancelBlockUpdate(int i, int j, int k, int l) {
        class_366 nextticklistentry = new class_366(i, j, k, l);
        this.field_184.remove(nextticklistentry);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_227() {
        for (int i = 0; i < this.field_201.size(); ++i) {
            net.minecraft.entity.Entity entity = (net.minecraft.entity.Entity) this.field_201.get(i);
            entity.tick();
            if (!entity.removed) continue;
            this.field_201.remove(i--);
        }
        this.entities.removeAll((Collection) this.field_182);
        for (int j = 0; j < this.field_182.size(); ++j) {
            net.minecraft.entity.Entity entity1 = (net.minecraft.entity.Entity) this.field_182.get(j);
            int i1 = entity1.chunkX;
            int k1 = entity1.chunkZ;
            if (!entity1.shouldTick || !this.isChunkLoaded(i1, k1)) continue;
            this.getChunkFromCache(i1, k1).removeEntity(entity1);
        }
        for (int k = 0; k < this.field_182.size(); ++k) {
            this.onEntityRemoved((net.minecraft.entity.Entity) this.field_182.get(k));
        }
        this.field_182.clear();
        for (int l = 0; l < this.entities.size(); ++l) {
            net.minecraft.entity.Entity entity2 = (net.minecraft.entity.Entity) this.entities.get(l);
            if (entity2.vehicle != null) {
                if (!entity2.vehicle.removed && entity2.vehicle.passenger == entity2) continue;
                entity2.vehicle.passenger = null;
                entity2.vehicle = null;
            }
            if (!(entity2.removed || Minecraft.minecraftInstance.cameraActive && Minecraft.minecraftInstance.cameraPause || DebugMode.active && !(entity2 instanceof Player))) {
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
            if (tileentity.killedFromSaving || (chunk = this.getChunkFromCache(tileentity.x >> 4, tileentity.z >> 4)) == null)
                continue;
            chunk.removeTileEntity(tileentity.x & 0xF, tileentity.y, tileentity.z & 0xF);
        }
        this.field_190 = false;
        if (!this.field_185.isEmpty()) {
            for (TileEntity tileentity1 : this.field_185) {
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
    @Overwrite()
    public void forceUpdatePosition(net.minecraft.entity.Entity entity) {
        this.updatePosition(entity, true);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void updatePosition(net.minecraft.entity.Entity entity, boolean flag) {
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
            if (entity.stunned > 0) {
                --entity.stunned;
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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean canSpawnEntity(Box box) {
        List list = this.getEntities((net.minecraft.entity.Entity) null, box);
        for (int i = 0; i < list.size(); ++i) {
            net.minecraft.entity.Entity entity = (net.minecraft.entity.Entity) list.get(i);
            if (entity.removed || !entity.field_1593) continue;
            return false;
        }
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_218(Box axisalignedbb) {
        int i = MathsHelper.floor(axisalignedbb.minX);
        int j = MathsHelper.floor(axisalignedbb.maxX + 1.0);
        int k = MathsHelper.floor(axisalignedbb.minY);
        int l = MathsHelper.floor(axisalignedbb.maxY + 1.0);
        int i1 = MathsHelper.floor(axisalignedbb.minZ);
        int j1 = MathsHelper.floor(axisalignedbb.maxZ + 1.0);
        if (axisalignedbb.minX < 0.0) {
            --i;
        }
        if (axisalignedbb.minY < 0.0) {
            --k;
        }
        if (axisalignedbb.minZ < 0.0) {
            --i1;
        }
        for (int k1 = i; k1 < j; ++k1) {
            for (int l1 = k; l1 < l; ++l1) {
                for (int i2 = i1; i2 < j1; ++i2) {
                    Tile block = Tile.BY_ID[this.getTileId(k1, l1, i2)];
                    if (block == null || !block.material.isLiquid()) continue;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_225(Box axisalignedbb) {
        int j1;
        int i = MathsHelper.floor(axisalignedbb.minX);
        int j = MathsHelper.floor(axisalignedbb.maxX + 1.0);
        int k = MathsHelper.floor(axisalignedbb.minY);
        int l = MathsHelper.floor(axisalignedbb.maxY + 1.0);
        int i1 = MathsHelper.floor(axisalignedbb.minZ);
        if (this.isRegionLoaded(i, k, i1, j, l, j1 = MathsHelper.floor(axisalignedbb.maxZ + 1.0))) {
            for (int k1 = i; k1 < j; ++k1) {
                for (int l1 = k; l1 < l; ++l1) {
                    for (int i2 = i1; i2 < j1; ++i2) {
                        int j2 = this.getTileId(k1, l1, i2);
                        if (j2 != Tile.FIRE.id && j2 != Tile.FLOWING_LAVA.id && j2 != Tile.STILL_LAVA.id) continue;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_170(Box axisalignedbb, Material material, net.minecraft.entity.Entity entity) {
        int j1;
        int i = MathsHelper.floor(axisalignedbb.minX);
        int j = MathsHelper.floor(axisalignedbb.maxX + 1.0);
        int k = MathsHelper.floor(axisalignedbb.minY);
        int l = MathsHelper.floor(axisalignedbb.maxY + 1.0);
        int i1 = MathsHelper.floor(axisalignedbb.minZ);
        if (!this.isRegionLoaded(i, k, i1, j, l, j1 = MathsHelper.floor(axisalignedbb.maxZ + 1.0))) {
            return false;
        }
        boolean flag = false;
        Vec3f vec3d = Vec3f.from(0.0, 0.0, 0.0);
        for (int k1 = i; k1 < j; ++k1) {
            for (int l1 = k; l1 < l; ++l1) {
                for (int i2 = i1; i2 < j1; ++i2) {
                    double d1;
                    Tile block = Tile.BY_ID[this.getTileId(k1, l1, i2)];
                    if (block == null || block.material != material || !((double) l >= (d1 = (float) (l1 + 1) - FluidTile.method_1218(this.getTileMeta(k1, l1, i2)))))
                        continue;
                    flag = true;
                    block.method_1572(this, k1, l1, i2, entity, vec3d);
                }
            }
        }
        if (vec3d.method_1300() > 0.0) {
            vec3d = vec3d.method_1296();
            double d = 0.014;
            entity.velocityX += vec3d.x * d;
            entity.velocityY += vec3d.y * d;
            entity.velocityZ += vec3d.z * d;
        }
        return flag;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_169(Box axisalignedbb, Material material) {
        int i = MathsHelper.floor(axisalignedbb.minX);
        int j = MathsHelper.floor(axisalignedbb.maxX + 1.0);
        int k = MathsHelper.floor(axisalignedbb.minY);
        int l = MathsHelper.floor(axisalignedbb.maxY + 1.0);
        int i1 = MathsHelper.floor(axisalignedbb.minZ);
        int j1 = MathsHelper.floor(axisalignedbb.maxZ + 1.0);
        for (int k1 = i; k1 < j; ++k1) {
            for (int l1 = k; l1 < l; ++l1) {
                for (int i2 = i1; i2 < j1; ++i2) {
                    Tile block = Tile.BY_ID[this.getTileId(k1, l1, i2)];
                    if (block == null || block.material != material) continue;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_207(Box axisalignedbb, Material material) {
        int i = MathsHelper.floor(axisalignedbb.minX);
        int j = MathsHelper.floor(axisalignedbb.maxX + 1.0);
        int k = MathsHelper.floor(axisalignedbb.minY);
        int l = MathsHelper.floor(axisalignedbb.maxY + 1.0);
        int i1 = MathsHelper.floor(axisalignedbb.minZ);
        int j1 = MathsHelper.floor(axisalignedbb.maxZ + 1.0);
        for (int k1 = i; k1 < j; ++k1) {
            for (int l1 = k; l1 < l; ++l1) {
                for (int i2 = i1; i2 < j1; ++i2) {
                    Tile block = Tile.BY_ID[this.getTileId(k1, l1, i2)];
                    if (block == null || block.material != material) continue;
                    int j2 = this.getTileMeta(k1, l1, i2);
                    double d = l1 + 1;
                    if (j2 < 8) {
                        d = (double) (l1 + 1) - (double) j2 / 8.0;
                    }
                    if (!(d >= axisalignedbb.minY)) continue;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public Explosion createExplosion(net.minecraft.entity.Entity cause, double x, double y, double z, float power) {
        return this.createExplosion(cause, x, y, z, power, false);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public Explosion createExplosion(net.minecraft.entity.Entity cause, double x, double y, double z, float power, boolean flag) {
        Explosion explosion = new Explosion(this, cause, x, y, z, power);
        explosion.causeFires = flag;
        explosion.kaboomPhase1();
        explosion.kaboomPhase2(true);
        return explosion;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float method_163(Vec3f vec3d, Box axisalignedbb) {
        double d = 1.0 / ((axisalignedbb.maxX - axisalignedbb.minX) * 2.0 + 1.0);
        double d1 = 1.0 / ((axisalignedbb.maxY - axisalignedbb.minY) * 2.0 + 1.0);
        double d2 = 1.0 / ((axisalignedbb.maxZ - axisalignedbb.minZ) * 2.0 + 1.0);
        int i = 0;
        int j = 0;
        float f = 0.0f;
        while (f <= 1.0f) {
            float f1 = 0.0f;
            while (f1 <= 1.0f) {
                float f2 = 0.0f;
                while (f2 <= 1.0f) {
                    double d3 = axisalignedbb.minX + (axisalignedbb.maxX - axisalignedbb.minX) * (double) f;
                    double d4 = axisalignedbb.minY + (axisalignedbb.maxY - axisalignedbb.minY) * (double) f1;
                    double d5 = axisalignedbb.minZ + (axisalignedbb.maxZ - axisalignedbb.minZ) * (double) f2;
                    if (this.raycast(Vec3f.from(d3, d4, d5), vec3d) == null) {
                        ++i;
                    }
                    ++j;
                    f2 = (float) ((double) f2 + d2);
                }
                f1 = (float) ((double) f1 + d1);
            }
            f = (float) ((double) f + d);
        }
        return (float) i / (float) j;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_172(Player entityplayer, int i, int j, int k, int l) {
        if (l == 0) {
            --j;
        }
        if (l == 1) {
            ++j;
        }
        if (l == 2) {
            --k;
        }
        if (l == 3) {
            ++k;
        }
        if (l == 4) {
            --i;
        }
        if (l == 5) {
            ++i;
        }
        if (this.getTileId(i, j, k) == Tile.FIRE.id) {
            this.playLevelEvent(entityplayer, 1004, i, j, k, 0);
            this.setTile(i, j, k, 0);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public TileEntity getTileEntity(int i, int j, int k) {
        Chunk chunk = this.getChunkFromCache(i >> 4, k >> 4);
        if (chunk != null) {
            return chunk.getTileEntity(i & 0xF, j, k & 0xF);
        }
        return null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public TileEntity getBlockTileEntityDontCreate(int i, int j, int k) {
        Chunk chunk = this.getChunkFromCache(i >> 4, k >> 4);
        if (chunk != null) {
            return chunk.getChunkBlockTileEntityDontCreate(i & 0xF, j, k & 0xF);
        }
        return null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
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
    @Overwrite()
    public void removeTileEntity(int i, int j, int k) {
        TileEntity tileentity = this.getBlockTileEntityDontCreate(i, j, k);
        if (tileentity != null && this.field_190) {
            tileentity.invalidate();
        } else {
            Chunk chunk;
            if (tileentity != null) {
                this.tileEntities.remove(tileentity);
            }
            if ((chunk = this.getChunkFromCache(i >> 4, k >> 4)) != null) {
                chunk.removeTileEntity(i & 0xF, j, k & 0xF);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean isFullOpaque(int i, int j, int k) {
        Tile block = Tile.BY_ID[this.getTileId(i, j, k)];
        if (block == null) {
            return false;
        }
        return block.isFullOpaque();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean canSuffocate(int i, int j, int k) {
        Tile block = Tile.BY_ID[this.getTileId(i, j, k)];
        if (block == null) {
            return false;
        }
        return block.material.method_897() && block.isFullCube();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_280(ProgressListener iprogressupdate) {
        this.saveLevel(true, iprogressupdate);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_232() {
        if (this.field_191 >= 50) {
            return false;
        }
        ++this.field_191;
        try {
            boolean flag1;
            int i = 500;
            while (this.field_181.size() > 0) {
                if (--i <= 0) {
                    boolean flag;
                    boolean bl = flag = true;
                    return bl;
                }
                ((LightCalculator) this.field_181.remove(this.field_181.size() - 1)).calculateLight(this);
            }
            boolean bl = flag1 = false;
            return bl;
        } finally {
            --this.field_191;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_166(LightType enumskyblock, int i, int j, int k, int l, int i1, int j1) {
        this.method_167(enumskyblock, i, j, k, l, i1, j1, true);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_167(LightType enumskyblock, int i, int j, int k, int l, int i1, int j1, boolean flag) {
        if (this.dimension.fixedSpawnPos && enumskyblock == LightType.Sky) {
            return;
        }
        ++field_179;
        try {
            if (field_179 == 50) {
                return;
            }
            int k1 = (l + i) / 2;
            int l1 = (j1 + k) / 2;
            if (!this.isTileLoaded(k1, 64, l1)) {
                return;
            }
            if (this.getChunk(k1, l1).method_886()) {
                return;
            }
            int i2 = this.field_181.size();
            if (flag) {
                int j2 = 5;
                if (j2 > i2) {
                    j2 = i2;
                }
                for (int l2 = 0; l2 < j2; ++l2) {
                    LightCalculator metadatachunkblock = (LightCalculator) this.field_181.get(this.field_181.size() - l2 - 1);
                    if (metadatachunkblock.type != enumskyblock || !metadatachunkblock.propagateLight(i, j, k, l, i1, j1))
                        continue;
                    return;
                }
            }
            this.field_181.add(new LightCalculator(enumskyblock, i, j, k, l, i1, j1));
            int k2 = 1000000;
            if (this.field_181.size() > 1000000) {
                System.out.println("More than " + k2 + " updates, aborting lighting updates");
                this.field_181.clear();
            }
        } finally {
            --field_179;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_237() {
        int i = this.computeWeatherGradients(1.0f);
        if (i != this.field_202) {
            this.field_202 = i;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_196(boolean flag, boolean flag1) {
        this.field_192 = flag;
        this.field_193 = flag1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
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
        this.fogColorOverridden = this.properties.overrideFogColor;
        this.fogDensityOverridden = this.properties.overrideFogDensity;
        this.method_245();
        this.cache.method_1801();
        int i = this.computeWeatherGradients(1.0f);
        if (i != this.field_202) {
            this.field_202 = i;
            for (int j = 0; j < this.listeners.size(); ++j) {
                ((LevelListener) this.listeners.get(j)).method_1148();
            }
        }
        if ((l1 = this.properties.getTime() + 1L) % (long) this.field_212 == 0L) {
            this.saveLevel(false, null);
        }
        this.properties.setTime(l1);
        this.properties.addToTimeOfDay(this.properties.getTimeRate());
        this.method_194(false);
        this.method_248();
        if (this.properties.isRaining()) {
            this.DoSnowModUpdate();
        }
        this.script.wakeupScripts(l1);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void method_245() {
        if (this.dimension.fixedSpawnPos) {
            return;
        }
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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void DoSnowModUpdate() {
        if (this.isClient) {
            return;
        }
        boolean cw = false;
        if (this.coordOrder == null) {
            this.initCoordOrder();
        }
        for (int i = 0; i < this.players.size(); ++i) {
            Player entityplayer = (Player) this.players.get(i);
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
    @Overwrite()
    protected void method_248() {
        for (int i = 0; i < this.players.size(); ++i) {
            Player entityplayer = (Player) this.players.get(i);
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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
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
                this.method_184(new Lightning(this, i3, i5, i4));
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
            Tile.BY_ID[l6].onScheduledTick(this, k4 + coordX, j6, k5 + coordZ, this.rand);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
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
            Tile.BY_ID[k].onScheduledTick(this, nextticklistentry.field_1400, nextticklistentry.field_1401, nextticklistentry.field_1402, this.rand);
            Box.method_85();
        }
        return this.field_183.size() != 0;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_294(int i, int j, int k) {
        int byte0 = 16;
        Random random = new Random();
        for (int l = 0; l < 1000; ++l) {
            int k1;
            int j1;
            int i1 = i + this.rand.nextInt(byte0) - this.rand.nextInt(byte0);
            int l1 = this.getTileId(i1, j1 = j + this.rand.nextInt(byte0) - this.rand.nextInt(byte0), k1 = k + this.rand.nextInt(byte0) - this.rand.nextInt(byte0));
            if (l1 <= 0) continue;
            Tile.BY_ID[l1].randomDisplayTick(this, i1, j1, k1, random);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public List getEntities(net.minecraft.entity.Entity entity, Box axisalignedbb) {
        this.field_196.clear();
        int i = MathsHelper.floor((axisalignedbb.minX - 2.0) / 16.0);
        int j = MathsHelper.floor((axisalignedbb.maxX + 2.0) / 16.0);
        int k = MathsHelper.floor((axisalignedbb.minZ - 2.0) / 16.0);
        int l = MathsHelper.floor((axisalignedbb.maxZ + 2.0) / 16.0);
        for (int i1 = i; i1 <= j; ++i1) {
            for (int j1 = k; j1 <= l; ++j1) {
                if (!this.isChunkLoaded(i1, j1)) continue;
                this.getChunkFromCache(i1, j1).appendEntities(entity, axisalignedbb, this.field_196);
            }
        }
        return this.field_196;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public List getEntities(Class class1, Box axisalignedbb) {
        int i = MathsHelper.floor((axisalignedbb.minX - 2.0) / 16.0);
        int j = MathsHelper.floor((axisalignedbb.maxX + 2.0) / 16.0);
        int k = MathsHelper.floor((axisalignedbb.minZ - 2.0) / 16.0);
        int l = MathsHelper.floor((axisalignedbb.maxZ + 2.0) / 16.0);
        ArrayList arraylist = new ArrayList();
        for (int i1 = i; i1 <= j; ++i1) {
            for (int j1 = k; j1 <= l; ++j1) {
                if (!this.isChunkLoaded(i1, j1)) continue;
                this.getChunkFromCache(i1, j1).appendEntities(class1, axisalignedbb, arraylist);
            }
        }
        return arraylist;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_203(int i, int j, int k, TileEntity tileentity) {
        if (this.isTileLoaded(i, j, k)) {
            this.getChunk(i, k).method_885();
        }
        for (int l = 0; l < this.listeners.size(); ++l) {
            ((LevelListener) this.listeners.get(l)).method_1151(i, j, k, tileentity);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int method_174(Class class1) {
        int i = 0;
        for (int j = 0; j < this.entities.size(); ++j) {
            net.minecraft.entity.Entity entity = (net.minecraft.entity.Entity) this.entities.get(j);
            if (!class1.isAssignableFrom(entity.getClass())) continue;
            ++i;
        }
        return i;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void addEntities(List entities) {
        this.entities.addAll(entities);
        for (int i = 0; i < entities.size(); ++i) {
            this.onEntityAdded((net.minecraft.entity.Entity) entities.get(i));
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean canPlaceTile(int id, int x, int y, int z, boolean flag, int meta) {
        int j1 = this.getTileId(x, y, z);
        Tile block = Tile.BY_ID[j1];
        Tile block1 = Tile.BY_ID[id];
        Box axisalignedbb = block1.getCollisionShape(this, x, y, z);
        if (flag) {
            axisalignedbb = null;
        }
        if (axisalignedbb != null && !this.canSpawnEntity(axisalignedbb)) {
            return false;
        }
        if (block == Tile.FLOWING_WATER || block == Tile.STILL_WATER || block == Tile.FLOWING_LAVA || block == Tile.STILL_LAVA || block == Tile.FIRE || block == Tile.SNOW) {
            block = null;
        }
        return id > 0 && block == null && block1.canPlaceAt(this, x, y, z, meta);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public class_61 method_192(net.minecraft.entity.Entity entity, net.minecraft.entity.Entity entity1, float f) {
        int i = MathsHelper.floor(entity.x);
        int j = MathsHelper.floor(entity.y);
        int k = MathsHelper.floor(entity.z);
        int l = (int) (f + 16.0f);
        int i1 = i - l;
        int j1 = j - l;
        int k1 = k - l;
        int l1 = i + l;
        int i2 = j + l;
        int j2 = k + l;
        WorldPopulationRegion chunkcache = new WorldPopulationRegion(this, i1, j1, k1, l1, i2, j2);
        return new class_108(chunkcache).method_407(entity, entity1, f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public class_61 method_189(net.minecraft.entity.Entity entity, int i, int j, int k, float f) {
        int l = MathsHelper.floor(entity.x);
        int i1 = MathsHelper.floor(entity.y);
        int j1 = MathsHelper.floor(entity.z);
        int k1 = (int) (f + 8.0f);
        int l1 = l - k1;
        int i2 = i1 - k1;
        int j2 = j1 - k1;
        int k2 = l + k1;
        int l2 = i1 + k1;
        int i3 = j1 + k1;
        WorldPopulationRegion chunkcache = new WorldPopulationRegion(this, l1, i2, j2, k2, l2, i3);
        return new class_108(chunkcache).method_403(entity, i, j, k, f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_247(int i, int j, int k, int l) {
        int i1 = this.getTileId(i, j, k);
        if (i1 == 0) {
            return false;
        }
        return Tile.BY_ID[i1].method_1570(this, i, j, k, l);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_263(int i, int j, int k) {
        if (this.method_247(i, j - 1, k, 0)) {
            return true;
        }
        if (this.method_247(i, j + 1, k, 1)) {
            return true;
        }
        if (this.method_247(i, j, k - 1, 2)) {
            return true;
        }
        if (this.method_247(i, j, k + 1, 3)) {
            return true;
        }
        if (this.method_247(i - 1, j, k, 4)) {
            return true;
        }
        return this.method_247(i + 1, j, k, 5);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_250(int i, int j, int k, int l) {
        if (this.canSuffocate(i, j, k)) {
            return this.method_263(i, j, k);
        }
        int i1 = this.getTileId(i, j, k);
        if (i1 == 0) {
            return false;
        }
        return Tile.BY_ID[i1].method_1568(this, i, j, k, l);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean hasRedstonePower(int x, int y, int z) {
        if (this.method_250(x, y - 1, z, 0)) {
            return true;
        }
        if (this.method_250(x, y + 1, z, 1)) {
            return true;
        }
        if (this.method_250(x, y, z - 1, 2)) {
            return true;
        }
        if (this.method_250(x, y, z + 1, 3)) {
            return true;
        }
        if (this.method_250(x - 1, y, z, 4)) {
            return true;
        }
        return this.method_250(x + 1, y, z, 5);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public Player getClosestPlayerTo(net.minecraft.entity.Entity entity, double d) {
        return this.getClosestPlayer(entity.x, entity.y, entity.z, d);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public Player getClosestPlayer(double d, double d1, double d2, double d3) {
        double d4 = -1.0;
        Player entityplayer = null;
        for (int i = 0; i < this.players.size(); ++i) {
            Player entityplayer1 = (Player) this.players.get(i);
            double d5 = entityplayer1.squaredDistanceTo(d, d1, d2);
            if (!(d3 < 0.0) && !(d5 < d3 * d3) || d4 != -1.0 && !(d5 < d4)) continue;
            d4 = d5;
            entityplayer = entityplayer1;
        }
        return entityplayer;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public Player getPlayerByName(String s) {
        for (int i = 0; i < this.players.size(); ++i) {
            if (!s.equals(((Player) this.players.get(i)).name)) continue;
            return (Player) this.players.get(i);
        }
        return null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_275(int i, int j, int k, int l, int i1, int j1, byte[] abyte0) {
        int k1 = i >> 4;
        int l1 = k >> 4;
        int i2 = i + l - 1 >> 4;
        int j2 = k + j1 - 1 >> 4;
        int k2 = 0;
        int l2 = j;
        int i3 = j + i1;
        if (l2 < 0) {
            l2 = 0;
        }
        if (i3 > 128) {
            i3 = 128;
        }
        for (int j3 = k1; j3 <= i2; ++j3) {
            int k3 = i - j3 * 16;
            int l3 = i + l - j3 * 16;
            if (k3 < 0) {
                k3 = 0;
            }
            if (l3 > 16) {
                l3 = 16;
            }
            for (int i4 = l1; i4 <= j2; ++i4) {
                int j4 = k - i4 * 16;
                int k4 = k + j1 - i4 * 16;
                if (j4 < 0) {
                    j4 = 0;
                }
                if (k4 > 16) {
                    k4 = 16;
                }
                k2 = this.getChunkFromCache(j3, i4).method_891(abyte0, k3, l2, j4, l3, i3, k4, k2);
                this.updateRedstone(j3 * 16 + k3, l2, i4 * 16 + j4, j3 * 16 + l3, i3, i4 * 16 + k4);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void checkSessionLock() {
        if (this.dimensionData != null) {
            this.dimensionData.checkSessionLock();
        } else {
            this.mapHandler.checkSessionLock();
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setLevelTime(long time) {
        this.properties.setTime(time);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float getTimeOfDay() {
        return this.properties.getTimeOfDay();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setTimeOfDay(long l) {
        this.properties.setTimeOfDay(l);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setSpawnPosition(Vec3i chunkcoordinates) {
        this.properties.setSpawnPosition(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float getSpawnYaw() {
        return this.properties.spawnYaw;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setSpawnYaw(float y) {
        this.properties.spawnYaw = y;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_287(net.minecraft.entity.Entity entity) {
        int i = MathsHelper.floor(entity.x / 16.0);
        int j = MathsHelper.floor(entity.z / 16.0);
        int byte0 = 2;
        for (int k = i - byte0; k <= i + byte0; ++k) {
            for (int l = j - byte0; l <= j + byte0; ++l) {
                this.getChunkFromCache(k, l);
            }
        }
        if (!this.entities.contains((Object) entity)) {
            this.entities.add((Object) entity);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_185(net.minecraft.entity.Entity entity, byte byte0) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_295() {
        this.entities.removeAll((Collection) this.field_182);
        for (int i = 0; i < this.field_182.size(); ++i) {
            net.minecraft.entity.Entity entity = (net.minecraft.entity.Entity) this.field_182.get(i);
            int l = entity.chunkX;
            int j1 = entity.chunkZ;
            if (!entity.shouldTick || !this.isChunkLoaded(l, j1)) continue;
            this.getChunkFromCache(l, j1).removeEntity(entity);
        }
        for (int j = 0; j < this.field_182.size(); ++j) {
            this.onEntityRemoved((net.minecraft.entity.Entity) this.field_182.get(j));
        }
        this.field_182.clear();
        for (int k = 0; k < this.entities.size(); ++k) {
            net.minecraft.entity.Entity entity1 = (net.minecraft.entity.Entity) this.entities.get(k);
            if (entity1.vehicle != null) {
                if (!entity1.vehicle.removed && entity1.vehicle.passenger == entity1) continue;
                entity1.vehicle.passenger = null;
                entity1.vehicle = null;
            }
            if (!entity1.removed) continue;
            int i1 = entity1.chunkX;
            int k1 = entity1.chunkZ;
            if (entity1.shouldTick && this.isChunkLoaded(i1, k1)) {
                this.getChunkFromCache(i1, k1).removeEntity(entity1);
            }
            this.entities.remove(k--);
            this.onEntityRemoved(entity1);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_224(int i, int j, int k, int l, int i1) {
        int j1 = this.getTileId(i, j, k);
        if (j1 > 0) {
            Tile.BY_ID[j1].onTileAction(this, i, j, k, l, i1);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void areAllPlayersSleeping() {
        this.allPlayersSleeping = !this.players.isEmpty();
        for (Player entityplayer : this.players) {
            if (entityplayer.isSleeping()) continue;
            this.allPlayersSleeping = false;
            break;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void wakeUpSleepingPlayers() {
        this.allPlayersSleeping = false;
        for (Player entityplayer : this.players) {
            if (!entityplayer.isSleeping()) continue;
            entityplayer.wakeUp(false, false, true);
        }
        this.resetWeather();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean canSkipNight() {
        if (this.allPlayersSleeping && !this.isClient) {
            for (Player entityplayer : this.players) {
                if (entityplayer.hasSleptEnough()) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float getThunderGradient(float tickProgress) {
        return (this.prevThunderGradient + (this.thunderGradient - this.prevThunderGradient) * tickProgress) * this.getRainGradient(tickProgress);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float getRainGradient(float tickProgress) {
        return this.prevRainGradient + (this.rainGradient - this.prevRainGradient) * tickProgress;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setRainGradient(float gradient) {
        this.prevRainGradient = gradient;
        this.rainGradient = gradient;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean canRainAt(int x, int y, int z) {
        if (!this.raining()) {
            return false;
        }
        if (!this.isAboveGroundCached(x, y, z)) {
            return false;
        }
        if (this.getOceanFloorHeight(x, z) > y) {
            return false;
        }
        Biome biomegenbase = this.getBiomeSource().getBiome(x, z);
        if (biomegenbase.canSnow()) {
            return false;
        }
        return biomegenbase.canRain();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void putMapStorage(String name, MapStorageBase mapdatabase) {
        this.data.putMapStorage(name, mapdatabase);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MapStorageBase getOrCreateMapStorage(Class clazz, String name) {
        return this.data.getOrCreateMapStorage(clazz, name);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int method_208(String s) {
        return this.data.writeIdCount(s);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void playLevelEvent(int i, int j, int k, int l, int i1) {
        this.playLevelEvent(null, i, j, k, l, i1);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void playLevelEvent(Player entityplayer, int i, int j, int k, int l, int i1) {
        for (int j1 = 0; j1 < this.listeners.size(); ++j1) {
            ((LevelListener) this.listeners.get(j1)).playLevelEvent(entityplayer, i, j, k, l, i1);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public double getTemperatureValue(int x, int z) {
        if (x < -32000000 || z < -32000000 || x >= 32000000 || z > 32000000) {
            return 0.0;
        }
        return this.getChunkFromCache(x >> 4, z >> 4).getTemperatureValue(x & 0xF, z & 0xF) + this.properties.tempOffset;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setTemperatureValue(int x, int z, double temp) {
        if (x < -32000000 || z < -32000000 || x >= 32000000 || z > 32000000) {
            return;
        }
        Chunk c = this.getChunkFromCache(x >> 4, z >> 4);
        if (c.getTemperatureValue(x & 0xF, z & 0xF) == temp) {
            return;
        }
        c.setTemperatureValue(x & 0xF, z & 0xF, temp);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void resetCoordOrder() {
        this.coordOrder = null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void initCoordOrder() {
        int i;
        Random r = new Random();
        r.setSeed(this.getLevelTime());
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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
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
                if (this.getLightLevel(LightType.Block, x, height, z) > 11) {
                    return false;
                }
                this.setTile(x, height, z, Tile.SNOW.id);
            } else if (topBlock == Tile.FLOWING_WATER.id && this.getTileMeta(x, height - 1, z) == 0) {
                if (this.getLightLevel(LightType.Block, x, height, z) > 11) {
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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void loadMapMusic() {
        File musicDir = new File(this.levelDir, "music");
        if (musicDir.exists() && musicDir.isDirectory()) {
            String streamName;
            File[] musicFiles;
            int musicCount = 0;
            for (File musicFile : musicFiles = musicDir.listFiles()) {
                if (!musicFile.isFile() || !musicFile.getName().endsWith(".ogg")) continue;
                streamName = String.format("music/%s", musicFile.getName().toLowerCase());
                Minecraft.minecraftInstance.soundHelper.method_2016(streamName, musicFile);
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
            Minecraft.minecraftInstance.soundHelper.playMusicFromStreaming(this.properties.playingMusic, 0, 0);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void loadMapSounds() {
        File soundDir = new File(this.levelDir, "sound");
        if (soundDir.exists() && soundDir.isDirectory()) {
            String streamName;
            File[] soundFiles;
            int soundCount = 0;
            for (File soundFile : soundFiles = soundDir.listFiles()) {
                if (!soundFile.isFile() || !soundFile.getName().endsWith(".ogg")) continue;
                streamName = String.format("sound/%s", soundFile.getName().toLowerCase());
                Minecraft.minecraftInstance.soundHelper.method_2011(streamName, soundFile);
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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void loadSoundOverrides() {
        Minecraft.minecraftInstance.resourceDownloadThread.method_107();
        File soundDir = new File(this.levelDir, "soundOverrides");
        if (soundDir.exists()) {
            Minecraft.minecraftInstance.resourceDownloadThread.method_108(soundDir, "");
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void loadBrightness() {
        for (int i = 0; i < 16; ++i) {
            this.dimension.field_2178[i] = this.properties.brightness[i];
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void undo() {
        this.undoStack.undo(this);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void redo() {
        this.undoStack.redo(this);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public net.minecraft.entity.Entity getEntityByID(int entityID) {
        for (net.minecraft.entity.Entity e : this.entities) {
            if (e.id != entityID) continue;
            return e;
        }
        return null;
    }
}
