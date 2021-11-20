package io.github.ryuu.adventurecraft.mixin.level.biome;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.level.biome.Forest;
import net.minecraft.level.biome.Hell;
import net.minecraft.level.biome.Rainforest;
import net.minecraft.level.biome.Sky;
import net.minecraft.level.biome.SparseBiome;
import net.minecraft.level.biome.Swampland;
import net.minecraft.level.biome.Taiga;
import net.minecraft.level.structure.Feature;
import net.minecraft.level.structure.LargeOak;
import net.minecraft.level.structure.OakTree;
import net.minecraft.tile.Tile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Biome.class)
public class MixinBiome {

    @Shadow()
    public static final Biome RAINFOREST = new Rainforest().setGrassColour(588342).setName("Rainforest").setFoliageColour(2094168);

    public static final Biome SWAMPLAND = new Swampland().setGrassColour(522674).setName("Swampland").setFoliageColour(9154376);

    public static final Biome SEASONAL_FOREST = new Biome().setGrassColour(10215459).setName("Seasonal Forest");

    public static final Biome FOREST = new Forest().setGrassColour(353825).setName("Forest").setFoliageColour(5159473);

    public static final Biome SAVANNA = new SparseBiome().setGrassColour(14278691).setName("Savanna");

    public static final Biome SHRUBLAND = new Biome().setGrassColour(10595616).setName("Shrubland");

    public static final Biome TAIGA = new Taiga().setGrassColour(3060051).setName("Taiga").setSnowy().setFoliageColour(8107825);

    public static final Biome DESERT = new SparseBiome().setGrassColour(16421912).setName("Desert").setRainless();

    public static final Biome PLAINS = new SparseBiome().setGrassColour(16767248).setName("Plains");

    public static final Biome ICE_DESERT = new SparseBiome().setGrassColour(16772499).setName("Ice Desert").setSnowy().setRainless().setFoliageColour(12899129);

    public static final Biome TUNDRA = new Biome().setGrassColour(5762041).setName("Tundra").setSnowy().setFoliageColour(12899129);

    public static final Biome NETHER = new Hell().setGrassColour(0xFF0000).setName("Hell").setRainless();

    public static final Biome SKY = new Sky().setGrassColour(0x8080FF).setName("Sky").setRainless();

    public String biomeName;

    public int grassColour;

    public byte topTileId;

    public byte underTileId;

    public int foliageColour;

    protected List monsters;

    protected List creatures;

    protected List waterCreatures;

    private boolean snows;

    private boolean precipitates;

    private static Biome[] biomes = new Biome[4096];

    protected MixinBiome() {
        this.topTileId = (byte) Tile.GRASS.id;
        this.underTileId = (byte) Tile.DIRT.id;
        this.foliageColour = 5169201;
        this.monsters = new ArrayList();
        this.creatures = new ArrayList();
        this.waterCreatures = new ArrayList();
        this.precipitates = true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public Feature getTree(Random rand) {
        if (rand.nextInt(10) == 0) {
            return new LargeOak();
        }
        return new OakTree();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected Biome setName(String name) {
        this.biomeName = name;
        return this;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected Biome setFoliageColour(int colour) {
        this.foliageColour = colour;
        return this;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected Biome setGrassColour(int colour) {
        this.grassColour = colour;
        return this;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static Biome getBiome(double temperature, double d1) {
        int i = (int) (temperature * 63.0);
        int j = (int) (d1 * 63.0);
        return biomes[i + j * 64];
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static Biome getClimateBiome(float temperature, float rainfall) {
        rainfall *= temperature;
        if (temperature < 0.1f) {
            return TUNDRA;
        }
        if (rainfall < 0.2f) {
            if (temperature < 0.5f) {
                return TUNDRA;
            }
            if (temperature < 0.95f) {
                return SAVANNA;
            }
            return DESERT;
        }
        if (rainfall > 0.5f && temperature < 0.7f) {
            return SWAMPLAND;
        }
        if (temperature < 0.5f) {
            return TAIGA;
        }
        if (temperature < 0.97f) {
            if (rainfall < 0.35f) {
                return SHRUBLAND;
            }
            return FOREST;
        }
        if (rainfall < 0.45f) {
            return PLAINS;
        }
        if (rainfall < 0.9f) {
            return SEASONAL_FOREST;
        }
        return RAINFOREST;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getSkyColour(float temperature) {
        if ((temperature /= 3.0f) < -1.0f) {
            temperature = -1.0f;
        }
        if (temperature > 1.0f) {
            temperature = 1.0f;
        }
        return Color.getHSBColor((float) (0.6222222f - temperature * 0.05f), (float) (0.5f + temperature * 0.1f), (float) 1.0f).getRGB();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public List getSpawnList(EntityType type) {
        if (type == EntityType.monster) {
            return this.monsters;
        }
        if (type == EntityType.creature) {
            return this.creatures;
        }
        if (type == EntityType.waterCreature) {
            return this.waterCreatures;
        }
        return null;
    }

    static {
        Biome.createBiomeArray();
    }
}
