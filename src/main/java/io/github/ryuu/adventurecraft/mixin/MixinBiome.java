package io.github.ryuu.adventurecraft.mixin;

// import io.github.ryuu.adventurecraft.overrides.Tile;
// import net.fabricmc.api.EnvType;
// import net.fabricmc.api.Environment;
// import net.minecraft.entity.EntityType;
// import net.minecraft.level.biome.*;
// import net.minecraft.level.structure.Feature;
// import net.minecraft.level.structure.LargeOak;
// import net.minecraft.level.structure.OakTree;
// import org.spongepowered.asm.mixin.Mixin;
//
// import java.awt.*;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Random;

// TODO: This class is identical to the vanilla class so no mixin required, this can be deleted after decompilation.

// @Mixin(Biome.class)
public class MixinBiome {
//     public static final Biome RAINFOREST = (new Rainforest()).setGrassColour(588342).setName("Rainforest").setFoliageColour(2094168);
//     public static final Biome SWAMPLAND = (new Swampland()).setGrassColour(522674).setName("Swampland").setFoliageColour(9154376);
//     public static final Biome SEASONAL_FOREST = (new Biome()).setGrassColour(10215459).setName("Seasonal Forest");
//     public static final Biome FOREST = (new Forest()).setGrassColour(353825).setName("Forest").setFoliageColour(5159473);
//     public static final Biome SAVANNA = (new SparseBiome()).setGrassColour(14278691).setName("Savanna");
//     public static final Biome SHRUBLAND = (new Biome()).setGrassColour(10595616).setName("Shrubland");
//     public static final Biome TAIGA = (new Taiga()).setGrassColour(3060051).setName("Taiga").setSnowy().setFoliageColour(8107825);
//     public static final Biome DESERT = (new SparseBiome()).setGrassColour(16421912).setName("Desert").setRainless();
//     public static final Biome PLAINS = (new SparseBiome()).setGrassColour(16767248).setName("Plains");
//     public static final Biome ICE_DESERT = (new SparseBiome()).setGrassColour(16772499).setName("Ice Desert").setSnowy().setRainless().setFoliageColour(12899129);
//     public static final Biome TUNDRA = (new Biome()).setGrassColour(5762041).setName("Tundra").setSnowy().setFoliageColour(12899129);
//     public static final Biome NETHER = (new Hell()).setGrassColour(16711680).setName("Hell").setRainless();
//     public static final Biome SKY = (new Sky()).setGrassColour(8421631).setName("Sky").setRainless();
//     private static final Biome[] biomes = new Biome[4096];
//
//     static {
//         createBiomeArray();
//     }
//
//     public byte topTileId = (byte) net.minecraft.tile.Tile.GRASS.id;
//     public byte underTileId = (byte) net.minecraft.tile.Tile.DIRT.id;
//     public int foliageColour = 5169201;
//     public String biomeName;
//     public int grassColour;
//     protected List monsters = new ArrayList();
//     protected List creatures = new ArrayList();
//     protected List waterCreatures = new ArrayList();
//     private boolean precipitates = true;
//     private boolean snows;
//
//     public static void createBiomeArray() {
//         for (int var0 = 0; var0 < 64; ++var0) {
//             for (int var1 = 0; var1 < 64; ++var1) {
//                 biomes[var0 + var1 * 64] = getClimateBiome((float) var0 / 63.0F, (float) var1 / 63.0F);
//             }
//         }
//
//         DESERT.topTileId = DESERT.underTileId = (byte) Tile.SAND.id;
//         ICE_DESERT.topTileId = ICE_DESERT.underTileId = (byte) Tile.SAND.id;
//
//     }
//
//     public static net.minecraft.level.biome.Biome getBiome(double d, double d1) {
//         int var4 = (int) (d * 63.0D);
//         int var5 = (int) (d1 * 63.0D);
//         return biomes[var4 + var5 * 64];
//     }
//
//     public static net.minecraft.level.biome.Biome getClimateBiome(float f, float f1) {
//         f1 = f1 * f;
//         if (f < 0.1F) {
//             return TUNDRA;
//         } else if (f1 < 0.2F) {
//             if (f < 0.5F) {
//                 return TUNDRA;
//             } else {
//                 return f < 0.95F ? SAVANNA : DESERT;
//             }
//         } else if (f1 > 0.5F && f < 0.7F) {
//             return SWAMPLAND;
//         } else if (f < 0.5F) {
//             return TAIGA;
//         } else if (f < 0.97F) {
//             return f1 < 0.35F ? SHRUBLAND : FOREST;
//         } else if (f1 < 0.45F) {
//             return PLAINS;
//         } else {
//             return f1 < 0.9F ? SEASONAL_FOREST : RAINFOREST;
//         }
//     }
//
//     private Biome setRainless() {
//         this.precipitates = false;
//         return this;
//     }
//
//     public Feature getTree(Random random) {
//         return random.nextInt(10) == 0 ? new LargeOak() : new OakTree();
//     }
//
//     protected net.minecraft.level.biome.Biome setSnowy() {
//         this.snows = true;
//         return this;
//     }
//
//     protected net.minecraft.level.biome.Biome setName(String string) {
//         this.biomeName = string;
//         return this;
//     }
//
//     protected net.minecraft.level.biome.Biome setFoliageColour(int i) {
//         this.foliageColour = i;
//         return this;
//     }
//
//     protected net.minecraft.level.biome.Biome setGrassColour(int i) {
//         this.grassColour = i;
//         return this;
//     }
//
//     @Environment(EnvType.CLIENT)
//     public int getSkyColour(float f) {
//         f = f / 3.0F;
//         if (f < -1.0F) {
//             f = -1.0F;
//         }
//
//         if (f > 1.0F) {
//             f = 1.0F;
//         }
//
//         return Color.getHSBColor(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F).getRGB();
//     }
//
//     public List getSpawnList(EntityType arg) {
//         if (arg == EntityType.MONSTER) {
//             return this.monsters;
//         } else if (arg == EntityType.CREATURE) {
//             return this.creatures;
//         } else {
//             return arg == EntityType.WATER_CREATURE ? this.waterCreatures : null;
//         }
//     }
//
//     public boolean canSnow() {
//         return this.snows;
//     }
//
//     public boolean canRain() {
//         return !this.snows && this.precipitates;
//     }
}