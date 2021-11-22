package io.github.ryuu.adventurecraft.mixin.level.chunk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Chunk.class)
public class MixinChunk {

    @Shadow()
    public static boolean field_953;

    public byte[] tiles;

    public boolean field_955;

    public Level level;

    public ChunkSubData meta;

    public ChunkSubData skylight;

    public ChunkSubData blocklight;

    public byte[] heightmap;

    public int field_961;

    public final int x;

    public final int z;

    public Map tileEntities = new HashMap();

    public List[] entities = new List[8];

    public boolean decorated = false;

    public boolean shouldSave = false;

    public boolean field_968;

    public boolean field_969 = false;

    public long lastUpdate = 0L;

    public double[] temperatures;

    public long lastUpdated;

    public static boolean isNotPopulating;

    public MixinChunk(Level world, int i, int j, boolean createHeightMap) {
        this.level = world;
        this.x = i;
        this.z = j;
        if (createHeightMap) {
            this.heightmap = new byte[256];
        }
        for (int k = 0; k < this.entities.length; ++k) {
            this.entities[k] = new ArrayList();
        }
    }

    public MixinChunk(Level level, int x, int z) {
        this(level, x, z, true);
    }

    public MixinChunk(Level level, byte[] tiles, int x, int z) {
        this(level, x, z);
        this.tiles = tiles;
        this.meta = new ChunkSubData(tiles.length);
        this.skylight = new ChunkSubData(tiles.length);
        this.blocklight = new ChunkSubData(tiles.length);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean equals(int x, int z) {
        return x == this.x && z == this.z;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getHeight(int i, int j) {
        return this.heightmap[j << 4 | i] & 0xFF;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_892() {
        int i = 127;
        for (int j = 0; j < 16; ++j) {
            for (int k = 0; k < 16; ++k) {
                int l;
                int i1 = j << 11 | k << 7;
                for (l = 127; l > 0 && Tile.field_1941[Chunk.translate256(this.tiles[i1 + l - 1])] == 0; --l) {
                }
                this.heightmap[k << 4 | j] = (byte) l;
                if (l >= i)
                    continue;
                i = l;
            }
        }
        this.field_961 = i;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void generateHeightmap() {
        int i = 127;
        for (int j = 0; j < 16; ++j) {
            for (int l = 0; l < 16; ++l) {
                int j1;
                int k1 = j << 11 | l << 7;
                for (j1 = 127; j1 > 0 && Tile.field_1941[Chunk.translate256(this.tiles[k1 + j1 - 1])] == 0; --j1) {
                }
                this.heightmap[l << 4 | j] = (byte) j1;
                if (j1 < i) {
                    i = j1;
                }
                if (this.level.dimension.fixedSpawnPos)
                    continue;
                int l1 = 15;
                int i2 = 127;
                do {
                    if ((l1 -= Tile.field_1941[Chunk.translate256(this.tiles[k1 + i2])]) <= 0)
                        continue;
                    this.skylight.set(j, i2, l, l1);
                } while (--i2 > 0 && l1 > 0);
            }
        }
        this.field_961 = i;
        for (int k = 0; k < 16; ++k) {
            for (int i1 = 0; i1 < 16; ++i1) {
                this.method_887(k, i1);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_887(int i, int j) {
        int k = this.getHeight(i, j);
        int l = this.x * 16 + i;
        int i1 = this.z * 16 + j;
        this.method_888(l - 1, i1, k);
        this.method_888(l + 1, i1, k);
        this.method_888(l, i1 - 1, k);
        this.method_888(l, i1 + 1, k);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_888(int x, int y, int z) {
        int l = this.level.getHeight(x, y);
        if (l > z) {
            this.level.method_166(LightType.Sky, x, z, y, x, l, y);
        } else if (l < z) {
            this.level.method_166(LightType.Sky, x, l, y, x, z, y);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_889(int i, int j, int k) {
        int l;
        int i1 = l = this.heightmap[k << 4 | i] & 0xFF;
        if (j > l) {
            i1 = j;
        }
        int j1 = i << 11 | k << 7;
        while (i1 > 0 && Tile.field_1941[Chunk.translate256(this.tiles[j1 + i1 - 1])] == 0) {
            --i1;
        }
        if (i1 == l) {
            return;
        }
        this.level.method_240(i, k, i1, l);
        this.heightmap[k << 4 | i] = (byte) i1;
        if (i1 < this.field_961) {
            this.field_961 = i1;
        } else {
            int k1 = 127;
            for (int i2 = 0; i2 < 16; ++i2) {
                for (int k2 = 0; k2 < 16; ++k2) {
                    if ((this.heightmap[k2 << 4 | i2] & 0xFF) >= k1)
                        continue;
                    k1 = this.heightmap[k2 << 4 | i2] & 0xFF;
                }
            }
            this.field_961 = k1;
        }
        int l1 = this.x * 16 + i;
        int j2 = this.z * 16 + k;
        if (i1 < l) {
            for (int l2 = i1; l2 < l; ++l2) {
                this.skylight.set(i, l2, k, 15);
            }
        } else {
            this.level.method_166(LightType.Sky, l1, l, j2, l1, i1, j2);
            for (int i3 = l; i3 < i1; ++i3) {
                this.skylight.set(i, i3, k, 0);
            }
        }
        int j3 = 15;
        int k3 = i1;
        while (i1 > 0 && j3 > 0) {
            int l3;
            if ((l3 = Tile.field_1941[this.getTileId(i, --i1, k)]) == 0) {
                l3 = 1;
            }
            if ((j3 -= l3) < 0) {
                j3 = 0;
            }
            this.skylight.set(i, i1, k, j3);
        }
        while (i1 > 0 && Tile.field_1941[this.getTileId(i, i1 - 1, k)] == 0) {
            --i1;
        }
        if (i1 != k3) {
            this.level.method_166(LightType.Sky, l1 - 1, i1, j2 - 1, l1 + 1, k3, j2 + 1);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getTileId(int x, int y, int z) {
        return Chunk.translate256(this.tiles[x << 11 | z << 7 | y]);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean setTileWithMetadata(int x, int y, int z, int tileId, int meta) {
        if (this.level.undoStack.isRecording()) {
            int prevBlockID = this.getTileId(x, y, z);
            int prevMetadata = this.getMetadata(x, y, z);
            TileEntity te = this.getChunkBlockTileEntityDontCreate(x, y, z);
            CompoundTag prevTag = null;
            if (te != null) {
                prevTag = new CompoundTag();
                te.writeIdentifyingData(prevTag);
            }
            this.level.undoStack.recordChange(x + (this.x << 4), y, z + (this.z << 4), prevBlockID, prevMetadata, prevTag, tileId, meta, null);
        }
        int byte0 = Chunk.translate256(tileId);
        int j1 = this.heightmap[z << 4 | x] & 0xFF;
        int k1 = Chunk.translate256(this.tiles[x << 11 | z << 7 | y]) & 0xFF;
        if (k1 == tileId && this.meta.get(x, y, z) == meta) {
            return false;
        }
        int l1 = this.x * 16 + x;
        int i2 = this.z * 16 + z;
        this.tiles[x << 11 | z << 7 | y] = (byte) Chunk.translate128(byte0);
        if (k1 != 0 && !this.level.isClient) {
            Tile.BY_ID[k1].onTileRemoved(this.level, l1, y, i2);
        }
        this.meta.set(x, y, z, meta);
        if (!this.level.dimension.fixedSpawnPos) {
            if (Tile.field_1941[byte0 & 0xFF] != 0) {
                if (y >= j1) {
                    this.method_889(x, y + 1, z);
                }
            } else if (y == j1 - 1) {
                this.method_889(x, y, z);
            }
            this.level.method_166(LightType.Sky, l1, y, i2, l1, y, i2);
        }
        this.level.method_166(LightType.Block, l1, y, i2, l1, y, i2);
        this.method_887(x, z);
        this.meta.set(x, y, z, meta);
        if (tileId != 0) {
            Tile.BY_ID[tileId].method_1611(this.level, l1, y, i2);
        }
        if (isNotPopulating) {
            this.shouldSave = true;
        }
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean setBlockIDWithMetadataTemp(int i, int j, int k, int l, int i1) {
        int byte0 = Chunk.translate256(l);
        this.tiles[i << 11 | k << 7 | j] = (byte) Chunk.translate128(byte0);
        this.meta.set(i, j, k, i1);
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean setTile(int x, int y, int z, int tileId) {
        if (this.level.undoStack.isRecording()) {
            int prevBlockID = this.getTileId(x, y, z);
            int prevMetadata = this.getMetadata(x, y, z);
            TileEntity te = this.getChunkBlockTileEntityDontCreate(x, y, z);
            CompoundTag prevTag = null;
            if (te != null) {
                prevTag = new CompoundTag();
                te.writeIdentifyingData(prevTag);
            }
            this.level.undoStack.recordChange(x + (this.x << 4), y, z + (this.z << 4), prevBlockID, prevMetadata, prevTag, tileId, 0, null);
        }
        int byte0 = Chunk.translate256(tileId);
        int i1 = this.heightmap[z << 4 | x] & 0xFF;
        int j1 = Chunk.translate256(this.tiles[x << 11 | z << 7 | y]) & 0xFF;
        if (j1 == tileId) {
            return false;
        }
        int k1 = this.x * 16 + x;
        int l1 = this.z * 16 + z;
        this.tiles[x << 11 | z << 7 | y] = (byte) Chunk.translate128(byte0);
        if (j1 != 0) {
            Tile.BY_ID[j1].onTileRemoved(this.level, k1, y, l1);
        }
        this.meta.set(x, y, z, 0);
        if (Tile.field_1941[byte0 & 0xFF] != 0) {
            if (y >= i1) {
                this.method_889(x, y + 1, z);
            }
        } else if (y == i1 - 1) {
            this.method_889(x, y, z);
        }
        this.level.method_166(LightType.Sky, k1, y, l1, k1, y, l1);
        this.level.method_166(LightType.Block, k1, y, l1, k1, y, l1);
        this.method_887(x, z);
        if (tileId != 0 && !this.level.isClient) {
            Tile.BY_ID[tileId].method_1611(this.level, k1, y, l1);
        }
        if (isNotPopulating) {
            this.shouldSave = true;
        }
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getMetadata(int x, int y, int z) {
        return this.meta.get(x, y, z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setMetadata(int x, int y, int z, int metadata) {
        if (this.level.undoStack.isRecording()) {
            int prevBlockID = this.getTileId(x, y, z);
            int prevMetadata = this.getMetadata(x, y, z);
            TileEntity te = this.getChunkBlockTileEntityDontCreate(x, y, z);
            CompoundTag prevTag = null;
            if (te != null) {
                prevTag = new CompoundTag();
                te.writeIdentifyingData(prevTag);
            }
            this.level.undoStack.recordChange(x + (this.x << 4), y, z + (this.z << 4), prevBlockID, prevMetadata, prevTag, prevBlockID, metadata, null);
        }
        if (isNotPopulating) {
            this.shouldSave = true;
        }
        this.meta.set(x, y, z, metadata);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getLightLevel(LightType type, int x, int y, int z) {
        if (type == LightType.Sky) {
            return this.skylight.get(x, y, z);
        }
        if (type == LightType.Block) {
            return this.blocklight.get(x, y, z);
        }
        return 0;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setLightLevel(LightType type, int x, int y, int z, int light) {
        if (type == LightType.Sky) {
            this.skylight.set(x, y, z, light);
        } else if (type == LightType.Block) {
            this.blocklight.set(x, y, z, light);
        } else {
            return;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getAbsoluteLight(int x, int y, int z, int l) {
        int j1;
        int i1 = this.skylight.get(x, y, z);
        if (i1 > 0) {
            field_953 = true;
        }
        if ((j1 = this.blocklight.get(x, y, z)) > (i1 -= l)) {
            i1 = j1;
        }
        return i1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void addEntity(Entity entity) {
        int k;
        if (!(entity instanceof Player)) {
            this.field_969 = true;
        }
        int i = MathsHelper.floor(entity.x / 16.0);
        int j = MathsHelper.floor(entity.z / 16.0);
        if (i != this.x || j != this.z) {
            System.out.println("Wrong location! " + entity);
            Thread.dumpStack();
        }
        if ((k = MathsHelper.floor(entity.y / 16.0)) < 0) {
            k = 0;
        }
        if (k >= this.entities.length) {
            k = this.entities.length - 1;
        }
        entity.shouldTick = true;
        entity.chunkX = this.x;
        entity.chunkIndex = k;
        entity.chunkZ = this.z;
        this.entities[k].add((Object) entity);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void removeEntity(Entity entity, int i) {
        if (i < 0) {
            i = 0;
        }
        if (i >= this.entities.length) {
            i = this.entities.length - 1;
        }
        this.entities[i].remove((Object) entity);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isAboveGround(int localX, int y, int localZ) {
        return y >= (this.heightmap[localZ << 4 | localX] & 0xFF);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public TileEntity getTileEntity(int x, int y, int z) {
        TilePos chunkposition = new TilePos(x, y, z);
        TileEntity tileentity = (TileEntity) this.tileEntities.get((Object) chunkposition);
        if (tileentity == null) {
            int l = this.getTileId(x, y, z);
            if (!Tile.HAS_TILE_ENTITY[l]) {
                return null;
            }
            TileWithEntity blockcontainer = (TileWithEntity) Tile.BY_ID[l];
            blockcontainer.method_1611(this.level, this.x * 16 + x, y, this.z * 16 + z);
            tileentity = (TileEntity) this.tileEntities.get((Object) chunkposition);
        }
        if (tileentity != null && tileentity.isInvalid()) {
            this.tileEntities.remove((Object) chunkposition);
            return null;
        }
        return tileentity;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public TileEntity getChunkBlockTileEntityDontCreate(int i, int j, int k) {
        TilePos chunkposition = new TilePos(i, j, k);
        TileEntity tileentity = (TileEntity) this.tileEntities.get((Object) chunkposition);
        return tileentity;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void addTileEntity(TileEntity tileEntity) {
        int i = tileEntity.x - this.x * 16;
        int j = tileEntity.y;
        int k = tileEntity.z - this.z * 16;
        this.placeTileEntity(i, j, k, tileEntity);
        if (this.field_955) {
            this.level.tileEntities.add((Object) tileEntity);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void placeTileEntity(int x, int y, int z, TileEntity tileEntity) {
        TilePos chunkposition = new TilePos(x, y, z);
        tileEntity.level = this.level;
        tileEntity.x = this.x * 16 + x;
        tileEntity.y = y;
        tileEntity.z = this.z * 16 + z;
        if (this.getTileId(x, y, z) == 0 || !(Tile.BY_ID[this.getTileId(x, y, z)] instanceof TileWithEntity)) {
            System.out.printf("No container :( BlockID: %d Tile Entity: %s Coord: %d, %d, %d\n", new Object[] { this.getTileId(x, y, z), tileEntity.getClassName(), tileEntity.x, tileEntity.y, tileEntity.z });
            return;
        }
        tileEntity.validate();
        this.tileEntities.put((Object) chunkposition, (Object) tileEntity);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void removeTileEntity(int x, int y, int z) {
        TileEntity tileentity;
        TilePos chunkposition = new TilePos(x, y, z);
        if (this.field_955 && (tileentity = (TileEntity) this.tileEntities.remove((Object) chunkposition)) != null) {
            tileentity.invalidate();
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_881() {
        this.field_955 = true;
        this.level.method_181(this.tileEntities.values());
        for (int i = 0; i < this.entities.length; ++i) {
            this.level.addEntities(this.entities[i]);
        }
        this.temperatures = this.level.getBiomeSource().getTemperatures(this.temperatures, this.x * 16, this.z * 16, 16, 16);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_883() {
        this.field_955 = false;
        for (TileEntity tileentity : this.tileEntities.values()) {
            tileentity.killedFromSaving = true;
            tileentity.invalidate();
        }
        for (int i = 0; i < this.entities.length; ++i) {
            this.level.method_209(this.entities[i]);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void appendEntities(Entity except, Box box, List entities) {
        int i = MathsHelper.floor((box.minY - 2.0) / 16.0);
        int j = MathsHelper.floor((box.maxY + 2.0) / 16.0);
        if (i < 0) {
            i = 0;
        }
        if (j >= this.entities.length) {
            j = this.entities.length - 1;
        }
        for (int k = i; k <= j; ++k) {
            List list1 = this.entities[k];
            for (int l = 0; l < list1.size(); ++l) {
                Entity entity1 = (Entity) list1.get(l);
                if (entity1 == except || !entity1.boundingBox.intersects(box))
                    continue;
                entities.add((Object) entity1);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void appendEntities(Class entityClass, Box box, List entities) {
        int i = MathsHelper.floor((box.minY - 2.0) / 16.0);
        int j = MathsHelper.floor((box.maxY + 2.0) / 16.0);
        if (i < 0) {
            i = 0;
        }
        if (j >= this.entities.length) {
            j = this.entities.length - 1;
        }
        for (int k = i; k <= j; ++k) {
            List list1 = this.entities[k];
            for (int l = 0; l < list1.size(); ++l) {
                Entity entity = (Entity) list1.get(l);
                if (!entityClass.isAssignableFrom(entity.getClass()) || !entity.boundingBox.intersects(box))
                    continue;
                entities.add((Object) entity);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_871(boolean flag) {
        if (this.field_968) {
            return false;
        }
        if (flag ? this.field_969 && this.level.getLevelTime() != this.lastUpdate : this.field_969 && this.level.getLevelTime() >= this.lastUpdate + 600L) {
            return true;
        }
        return this.shouldSave;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int method_891(byte[] abyte0, int i, int j, int k, int l, int i1, int j1, int k1) {
        for (int l1 = i; l1 < l; ++l1) {
            for (int l2 = k; l2 < j1; ++l2) {
                int l3 = l1 << 11 | l2 << 7 | j;
                int l4 = i1 - j;
                System.arraycopy((Object) abyte0, (int) k1, (Object) this.tiles, (int) l3, (int) l4);
                k1 += l4;
            }
        }
        this.method_892();
        for (int i2 = i; i2 < l; ++i2) {
            for (int i3 = k; i3 < j1; ++i3) {
                int i4 = (i2 << 11 | i3 << 7 | j) >> 1;
                int i5 = (i1 - j) / 2;
                System.arraycopy((Object) abyte0, (int) k1, (Object) this.meta.data, (int) i4, (int) i5);
                k1 += i5;
            }
        }
        for (int j2 = i; j2 < l; ++j2) {
            for (int j3 = k; j3 < j1; ++j3) {
                int j4 = (j2 << 11 | j3 << 7 | j) >> 1;
                int j5 = (i1 - j) / 2;
                System.arraycopy((Object) abyte0, (int) k1, (Object) this.blocklight.data, (int) j4, (int) j5);
                k1 += j5;
            }
        }
        for (int k2 = i; k2 < l; ++k2) {
            for (int k3 = k; k3 < j1; ++k3) {
                int k4 = (k2 << 11 | k3 << 7 | j) >> 1;
                int k5 = (i1 - j) / 2;
                System.arraycopy((Object) abyte0, (int) k1, (Object) this.skylight.data, (int) k4, (int) k5);
                k1 += k5;
            }
        }
        return k1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public Random createRandom(long seed) {
        return new Random(this.level.getSeed() + (long) (this.x * this.x * 4987142) + (long) (this.x * 5947611) + (long) (this.z * this.z) * 4392871L + (long) (this.z * 389711) ^ seed);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public double getTemperatureValue(int x, int z) {
        if (this.temperatures == null) {
            this.temperatures = this.level.getBiomeSource().getTemperatures(this.temperatures, this.x * 16, this.z * 16, 16, 16);
        }
        return this.temperatures[z << 4 | x];
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setTemperatureValue(int x, int z, double temp) {
        this.temperatures[z << 4 | x] = temp;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static int translate128(int bID) {
        if (bID > 127) {
            return -129 + (bID - 127);
        }
        return bID;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static int translate256(int bID) {
        if (bID < 0) {
            return bID + 256;
        }
        return bID;
    }

    static {
        isNotPopulating = true;
    }
}
