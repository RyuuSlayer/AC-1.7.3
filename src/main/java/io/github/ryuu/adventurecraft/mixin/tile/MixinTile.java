package io.github.ryuu.adventurecraft.mixin.tile;

import io.github.ryuu.adventurecraft.blocks.*;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import io.github.ryuu.adventurecraft.items.ItemSubtypes;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.GrassTile;
import net.minecraft.tile.StoneTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.TileSounds;
import net.minecraft.tile.material.Material;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Tile.class)
public abstract class MixinTile implements AccessTile, ExTile {

    @Mutable
    @Shadow
    @Final
    public static Tile STONE;

    @Shadow
    protected abstract Tile hardness(float f);

    @Shadow
    protected abstract Tile blastResistance(float f);

    @Shadow
    @Final
    public static TileSounds PISTON_SOUNDS;

    @Mutable
    @Shadow
    @Final
    public static GrassTile GRASS;

    @Shadow
    @Final
    public static TileSounds GRASS_SOUNDS;

    @Final
    @Shadow
    public int id;

    @Final
    @Shadow
    public Material material;

    @Shadow
    public int tex;

    @Shadow
    public double minX;

    @Shadow
    public double minY;

    @Shadow
    public double minZ;

    @Shadow
    public double maxX;

    @Shadow
    public double maxY;

    @Shadow
    public double maxZ;

    @Shadow
    @Final
    public static Tile[] BY_ID;

    @Shadow
    protected abstract Tile sounds(TileSounds arg);

    @Mutable
    @Shadow
    @Final
    public static Tile COBBLESTONE;

    @Shadow
    protected abstract Tile method_1590(int i);

    @Shadow
    protected abstract Tile nonOpaque();

    @Shadow
    protected abstract Tile multipleStates();

    @Mutable
    @Shadow
    @Final
    public static Tile FLOWING_WATER;

    @Mutable
    @Shadow
    @Final
    public static Tile STILL_WATER;

    @Mutable
    @Shadow
    @Final
    public static Tile FLOWING_LAVA;

    @Shadow
    protected abstract Tile luminance(float f);

    @Mutable
    @Shadow
    @Final
    public static Tile STILL_LAVA;

    @Mutable
    @Shadow
    @Final
    public static Tile SAND;

    @Shadow
    @Final
    public static TileSounds SAND_SOUNDS;

    public int textureNum = 0;

    static {
        BY_ID[1] = null;
        STONE = ((MixinTile) (Object) ((MixinTile) (Object) ((MixinTile) (Object) new StoneTile(1, 1)).hardness(1.5f)).blastResistance(10.0f)).sounds(PISTON_SOUNDS).name("stone");
        BY_ID[2] = null;
        GRASS = (GrassTile) ((ExTile) ((MixinTile) (Object) ((MixinTile) (Object) AccessGrassTile.newGrassTile(2)).hardness(0.6f)).sounds(GRASS_SOUNDS).name("grass")).setSubTypes(5);
        BY_ID[4] = null;
        COBBLESTONE = ((MixinTile) (Object) ((MixinTile) (Object) ((MixinTile) (Object) new BlockColor(4, 214, Material.STONE)).hardness(2.0f)).blastResistance(10.0f)).sounds(PISTON_SOUNDS).name("stonebrick");
        BY_ID[8] = null;
        FLOWING_WATER = ((MixinTile) (Object) ((MixinTile) (Object) ((MixinTile) (Object) ((MixinTile) (Object) AccessFlowingFluidTile.newFlowingFluidTile(8, Material.WATER)).hardness(0.5f)).method_1590(3).name("water")).nonOpaque()).multipleStates();
        BY_ID[9] = null;
        STILL_WATER = ((MixinTile) (Object) ((MixinTile) (Object) ((MixinTile) (Object) ((MixinTile) (Object) AccessStillFluidTile.newStillFluidTile(9, Material.WATER)).hardness(0.5f)).method_1590(3).name("water")).nonOpaque()).multipleStates();
        BY_ID[10] = null;
        FLOWING_LAVA = ((MixinTile) (Object) ((MixinTile) (Object) ((MixinTile) (Object) ((MixinTile) (Object) ((MixinTile) (Object) AccessFlowingFluidTile.newFlowingFluidTile(10, Material.LAVA)).hardness(0.5f)).luminance(1.0f)).method_1590(255).name("lava")).nonOpaque()).multipleStates();
        BY_ID[11] = null;
        STILL_LAVA = ((MixinTile) (Object) ((MixinTile) (Object) ((MixinTile) (Object) ((MixinTile) (Object) ((MixinTile) (Object) AccessStillFluidTile.newStillFluidTile(11, Material.LAVA)).hardness(0.5f)).luminance(1.0f)).method_1590(255).name("lava")).nonOpaque()).multipleStates();
        BY_ID[12] = null;
        SAND = ((MixinTile) (Object) ((MixinTile) (Object) ((MixinTile) (Object) AccessSandTile.newSandTile(12, 18)).hardness(0.5f)).sounds(SAND_SOUNDS).name("sand")).setSubTypes(4);
        ItemType.byId[Tile.GRASS.id] = new ItemSubtypes(Tile.GRASS.id - 256).setName("grass");
        ItemType.byId[Tile.SAND.id] = new ItemSubtypes(Tile.SAND.id - 256).setName("sand");
        ItemType.byId[Tile.TALLGRASS.id] = new ItemSubtypes(Tile.TALLGRASS.id - 256).setName("tallgrass");
    }

    @Inject(method = "onTileRemoved", at = @At("HEAD"))
    private void onTileRemoved(Level level, int x, int y, int z, CallbackInfo ci) {
        ((ExLevel) level).getTriggerManager().removeArea(x, y, z);
    }

    @Redirect(method = "beforeDestroyedByExplosion", at = @At(value = "FIELD", target = "Lnet/minecraft/level/Level;isClient:Z"))
    private boolean beforeDestroyedByExplosion(Level level) {
        return false;
    }

    @Redirect(method = "dropItem", at = @At(value = "FIELD", target = "Lnet/minecraft/level/Level;isClient:Z"))
    private boolean dropItem(Level level) {
        return false;
    }

    @Override
    public int getTextureNum() {
        return this.textureNum;
    }

    @Override
    public Tile setTextureNum(int t) {
        this.textureNum = t;
        return ((Tile) (Object) this);
    }

    @Override
    public Tile setSubTypes(int i) {
        subTypes[this.id] = i;
        return ((Tile) (Object) this);
    }

    @Override
    public int alwaysUseClick(Level world, int i, int j, int k) {
        return -1;
    }

    @Override
    public int getBlockLightValue(TileView iblockaccess, int i, int j, int k) {
        return 0;
    }

    @Override
    public boolean shouldRender(TileView blockAccess, int i, int j, int k) {
        return true;
    }

    @Override
    public void reset(Level world, int i, int j, int k, boolean death) {
    }

    @Override
    public void addTriggerActivation(Level world, int i, int j, int k) {
    }

    @Override
    public void removeTriggerActivation(Level world, int i, int j, int k) {
    }

    @Override
    public boolean canBeTriggered() {
        return false;
    }

    @Override
    public void onTriggerActivated(Level world, int i, int j, int k) {
    }

    @Override
    public void onTriggerDeactivated(Level world, int i, int j, int k) {
    }
}
