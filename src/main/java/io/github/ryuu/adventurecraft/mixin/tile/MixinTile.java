package io.github.ryuu.adventurecraft.mixin.tile;

import io.github.ryuu.adventurecraft.blocks.*;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import io.github.ryuu.adventurecraft.items.ItemSubtypes;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.*;
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

    @Mutable
    @Shadow
    @Final
    public static GrassTile GRASS;

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

    @Mutable
    @Shadow
    @Final
    public static Tile COBBLESTONE;

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

    @Mutable
    @Shadow
    @Final
    public static Tile STILL_LAVA;

    @Mutable
    @Shadow
    @Final
    public static Tile SAND;

    public int textureNum = 0;

    @Shadow
    protected abstract Tile blastResistance(float f);

    @Shadow
    protected abstract Tile hardness(float f);

    @Shadow
    protected abstract Tile sounds(TileSounds s);

    @Inject(method = "<clinit>", at = @At(
            value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/item/WoolItem;setName(Ljava/lang/String;)Lnet/minecraft/item/ItemType;",
            shift = At.Shift.BEFORE))
    private static void initTiles(CallbackInfo ci) {
        BY_ID[1] = null;
        STONE = new StoneTile(1, 215).name("stone");
        ((MixinTile) (Object) STONE).hardness(1.5f);
        ((MixinTile) (Object) STONE).blastResistance(10.0f);
        ((MixinTile) (Object) STONE).sounds(Tile.PISTON_SOUNDS);

        ((ExTile) GRASS).setSubTypes(5);

        BY_ID[4] = null;
        COBBLESTONE = new BlockColor(4, 214, Material.STONE).name("stonebrick");
        ((MixinTile) (Object) COBBLESTONE).hardness(2.0f);
        ((MixinTile) (Object) COBBLESTONE).blastResistance(10.0f);
        ((MixinTile) (Object) COBBLESTONE).sounds(Tile.PISTON_SOUNDS);

        ((MixinTile) (Object) FLOWING_WATER).hardness(0.5f);
        ((MixinTile) (Object) STILL_WATER).hardness(0.5f);
        ((MixinTile) (Object) FLOWING_LAVA).hardness(0.5f);
        ((MixinTile) (Object) STILL_LAVA).hardness(0.5f);

        ((ExTile) SAND).setSubTypes(4);
    }

    @Inject(method = "<clinit>", at = @At(
            value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/item/WoolItem;setName(Ljava/lang/String;)Lnet/minecraft/item/ItemType;",
            shift = At.Shift.AFTER))
    private static void initItemTypes(CallbackInfo ci) {
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
