package io.github.ryuu.adventurecraft.mixin.level.chunk;

import io.github.ryuu.adventurecraft.Main;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.extensions.level.chunk.ExChunk;
import io.github.ryuu.adventurecraft.extensions.tile.entity.ExTileEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.LightType;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.level.chunk.ChunkSubData;
import net.minecraft.tile.Tile;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.TilePos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(Chunk.class)
public abstract class MixinChunk implements ExChunk {

    @Final
    @Shadow
    public int x;

    @Final
    @Shadow
    public int z;

    @Shadow
    public byte[] tiles;

    @Shadow
    public Level level;

    @Shadow
    public ChunkSubData meta;

    @Shadow
    public ChunkSubData skylight;

    @Shadow
    public byte[] heightmap;

    @Shadow
    public int field_961;

    @Shadow
    public Map<TilePos, TileEntity> tileEntities;

    @Shadow
    public List<Entity>[] entities;

    @Shadow
    public boolean shouldSave;

    @Shadow
    public boolean field_969;

    public double[] temperatures;

    public long lastUpdated;

    @Shadow
    protected abstract void method_887(int i, int j);

    @Shadow
    public abstract int getMetadata(int i, int j, int k);

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Environment(EnvType.CLIENT)
    @Overwrite
    public void method_892() {
        int i = 127;
        for (int j = 0; j < 16; ++j) {
            for (int k = 0; k < 16; ++k) {
                int i1 = j << 11 | k << 7;
                int l = 127;
                while (l > 0 && Tile.field_1941[ExChunk.translate256(this.tiles[i1 + l - 1])] == 0) {
                    --l;
                }
                this.heightmap[k << 4 | j] = (byte) l;
                if (l >= i) continue;
                i = l;
            }
        }
        this.field_961 = i;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void generateHeightmap() {
        int i = 127;
        for (int j = 0; j < 16; ++j) {
            for (int l = 0; l < 16; ++l) {
                int k1 = j << 11 | l << 7;
                int j1 = 127;
                while (j1 > 0 && Tile.field_1941[ExChunk.translate256(this.tiles[k1 + j1 - 1])] == 0) {
                    --j1;
                }
                this.heightmap[l << 4 | j] = (byte) j1;
                if (j1 < i) {
                    i = j1;
                }
                if (this.level.dimension.fixedSpawnPos) continue;
                int l1 = 15;
                int i2 = 127;
                do {
                    if ((l1 -= Tile.field_1941[ExChunk.translate256(this.tiles[k1 + i2])]) <= 0) continue;
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

    @Redirect(method = "method_888", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/level/chunk/Chunk;shouldSave:Z"))
    private void disableShouldSaveOnMethod_888(Chunk instance, boolean value) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_889(int i, int j, int k) {
        int l = this.heightmap[k << 4 | i] & 0xFF;
        int i1 = Math.max(j, l);
        int j1 = i << 11 | k << 7;
        while (i1 > 0 && Tile.field_1941[ExChunk.translate256(this.tiles[j1 + i1 - 1])] == 0) {
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
                    if ((this.heightmap[k2 << 4 | i2] & 0xFF) >= k1) continue;
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
            this.level.method_166(LightType.SKY, l1, l, j2, l1, i1, j2);
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
            this.level.method_166(LightType.SKY, l1 - 1, i1, j2 - 1, l1 + 1, k3, j2 + 1);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public int getTileId(int x, int y, int z) {
        return ExChunk.translate256(this.tiles[x << 11 | z << 7 | y]);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public boolean setTileWithMetadata(int x, int y, int z, int tileId, int meta) {
        if (((ExLevel)this.level).getUndoStack().isRecording()) {
            int prevBlockID = this.getTileId(x, y, z);
            int prevMetadata = this.getMetadata(x, y, z);
            TileEntity te = this.getChunkBlockTileEntityDontCreate(x, y, z);
            CompoundTag prevTag = null;
            if (te != null) {
                prevTag = new CompoundTag();
                te.writeIdentifyingData(prevTag);
            }
            ((ExLevel)this.level).getUndoStack().recordChange(x + (this.x << 4), y, z + (this.z << 4), prevBlockID, prevMetadata, prevTag, tileId, meta, null);
        }
        int byte0 = ExChunk.translate256(tileId);
        int j1 = this.heightmap[z << 4 | x] & 0xFF;
        int k1 = ExChunk.translate256(this.tiles[x << 11 | z << 7 | y]) & 0xFF;
        if (k1 == tileId && this.meta.get(x, y, z) == meta) {
            return false;
        }
        int l1 = this.x * 16 + x;
        int i2 = this.z * 16 + z;
        this.tiles[x << 11 | z << 7 | y] = (byte) ExChunk.translate128(byte0);
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
            this.level.method_166(LightType.SKY, l1, y, i2, l1, y, i2);
        }
        this.level.method_166(LightType.BLOCK, l1, y, i2, l1, y, i2);
        this.method_887(x, z);
        this.meta.set(x, y, z, meta);
        if (tileId != 0) {
            Tile.BY_ID[tileId].method_1611(this.level, l1, y, i2);
        }
        if (Main.chunkIsNotPopulating) {
            this.shouldSave = true;
        }
        return true;
    }

    @Override
    public boolean setBlockIDWithMetadataTemp(int i, int j, int k, int l, int i1) {
        int byte0 = ExChunk.translate256(l);
        this.tiles[i << 11 | k << 7 | j] = (byte) ExChunk.translate128(byte0);
        this.meta.set(i, j, k, i1);
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public boolean setTile(int x, int y, int z, int tileId) {
        if (((ExLevel)this.level).getUndoStack().isRecording()) {
            int prevBlockID = this.getTileId(x, y, z);
            int prevMetadata = this.getMetadata(x, y, z);
            TileEntity te = this.getChunkBlockTileEntityDontCreate(x, y, z);
            CompoundTag prevTag = null;
            if (te != null) {
                prevTag = new CompoundTag();
                te.writeIdentifyingData(prevTag);
            }
            ((ExLevel)this.level).getUndoStack().recordChange(x + (this.x << 4), y, z + (this.z << 4), prevBlockID, prevMetadata, prevTag, tileId, 0, null);
        }
        int byte0 = ExChunk.translate256(tileId);
        int i1 = this.heightmap[z << 4 | x] & 0xFF;
        int j1 = ExChunk.translate256(this.tiles[x << 11 | z << 7 | y]) & 0xFF;
        if (j1 == tileId) {
            return false;
        }
        int k1 = this.x * 16 + x;
        int l1 = this.z * 16 + z;
        this.tiles[x << 11 | z << 7 | y] = (byte) ExChunk.translate128(byte0);
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
        this.level.method_166(LightType.SKY, k1, y, l1, k1, y, l1);
        this.level.method_166(LightType.BLOCK, k1, y, l1, k1, y, l1);
        this.method_887(x, z);
        if (tileId != 0 && !this.level.isClient) {
            Tile.BY_ID[tileId].method_1611(this.level, k1, y, l1);
        }
        if (Main.chunkIsNotPopulating) {
            this.shouldSave = true;
        }
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void setMetadata(int x, int y, int z, int metadata) {
        if (((ExLevel)this.level).getUndoStack().isRecording()) {
            int prevBlockID = this.getTileId(x, y, z);
            int prevMetadata = this.getMetadata(x, y, z);
            TileEntity te = this.getChunkBlockTileEntityDontCreate(x, y, z);
            CompoundTag prevTag = null;
            if (te != null) {
                prevTag = new CompoundTag();
                te.writeIdentifyingData(prevTag);
            }
            ((ExLevel)this.level).getUndoStack().recordChange(x + (this.x << 4), y, z + (this.z << 4), prevBlockID, prevMetadata, prevTag, prevBlockID, metadata, null);
        }
        if (Main.chunkIsNotPopulating) {
            this.shouldSave = true;
        }
        this.meta.set(x, y, z, metadata);
    }

    @Redirect(method = "setLightLevel", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/level/chunk/Chunk;shouldSave:Z"))
    private static void disableShouldSaveOnSetLightLevel(Chunk instance, boolean value) {
    }

    @Redirect(method = "addEntity", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/level/chunk/Chunk;field_969:Z"))
    private static void disableSetField969OnAddEntity(Chunk instance, boolean value) {
    }

    @Inject(method = "addEntity", at = @At("HEAD"))
    private void setField969OnAddEntity(Entity entity, CallbackInfo ci) {
        if (!(entity instanceof Player)) {
            this.field_969 = true;
        }
    }

    @Override
    public TileEntity getChunkBlockTileEntityDontCreate(int i, int j, int k) {
        TilePos chunkposition = new TilePos(i, j, k);
        TileEntity tileentity = this.tileEntities.get(chunkposition);
        return tileentity;
    }

    @Inject(method = "method_881", at = @At("TAIL"))
    private void setTemperaturesInMethod_881(CallbackInfo ci) {
        this.temperatures = this.level.getBiomeSource().getTemperatures(this.temperatures, this.x * 16, this.z * 16, 16, 16);
    }

    @Redirect(method = "method_883", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/tile/entity/TileEntity;invalidate()V"))
    private void setKilledFromSaving(TileEntity instance) {
        ((ExTileEntity) instance).setKilledFromSaving(true);
        instance.invalidate();
    }

    @Override
    public double getTemperatureValue(int x, int z) {
        if (this.temperatures == null) {
            this.temperatures = this.level.getBiomeSource().getTemperatures(this.temperatures, this.x * 16, this.z * 16, 16, 16);
        }
        return this.temperatures[z << 4 | x];
    }

    @Override
    public void setTemperatureValue(int x, int z, double temp) {
        this.temperatures[z << 4 | x] = temp;
    }

    @Override
    public long getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
