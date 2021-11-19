package io.github.ryuu.adventurecraft.mixin.tile;

import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.*;
import net.minecraft.level.TileView;
import net.minecraft.stat.Stats;
import net.minecraft.tile.*;
import net.minecraft.tile.material.Material;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.Random;

@Mixin(Tile.class)
public class MixinTile {

    public static final TileSounds STONE_SOUNDS;
    public static final TileSounds WOOD_SOUNDS;
    public static final TileSounds GRAVEL_SOUNDS;
    public static final TileSounds GRASS_SOUNDS;
    public static final TileSounds PISTON_SOUNDS;
    public static final TileSounds METAL_SOUNDS;
    public static final TileSounds GLASS_SOUNDS;
    public static final TileSounds WOOL_SOUNDS;
    public static final TileSounds SAND_SOUNDS;
    public static final MixinTile[] BY_ID;
    public static final boolean[] TICKS_RANDOMLY;
    public static final boolean[] FULL_OPAQUE;
    public static final boolean[] HAS_TILE_ENTITY;
    public static final int[] field_1941;
    public static final int[] subTypes;
    public static final boolean[] IS_AIR;
    public static final int[] LUMINANCES;
    public static final boolean[] MULTIPLE_STATES;
    public static final MixinTile STONE;
    public static final MixinGrassTile GRASS;
    public static final MixinTile DIRT;
    public static final MixinTile COBBLESTONE;
    public static final MixinTile WOOD;
    public static final MixinTile SAPLING;
    public static final MixinTile BEDROCK;
    public static final MixinTile FLOWING_WATER;
    public static final MixinTile STILL_WATER;
    public static final MixinTile FLOWING_LAVA;
    public static final MixinTile STILL_LAVA;
    public static final MixinTile SAND;
    public static final MixinTile GRAVEL;
    public static final MixinTile GOLD_ORE;
    public static final MixinTile IRON_ORE;
    public static final MixinTile COAL_ORE;
    public static final MixinTile LOG;
    public static final MixinLeavesTile LEAVES;
    public static final MixinTile SPONGE;
    public static final MixinTile GLASS;
    public static final MixinTile LAPIS_LAZULI_ORE;
    public static final MixinTile LAPIS_LAZULI_BLOCK;
    public static final MixinTile DISPENSER;
    public static final MixinTile SANDSTONE;
    public static final MixinTile NOTEBLOCK;
    public static final MixinTile BED;
    public static final MixinTile GOLDEN_RAIL;
    public static final MixinTile DETECTOR_RAIL;
    public static final MixinTile STICKY_PISTON;
    public static final MixinTile WEB;
    public static final TallGrassTile TALLGRASS;
    public static final DeadBushTile DEADBUSH;
    public static final MixinTile PISTON;
    public static final PistonHead PISTON_HEAD;
    public static final MixinTile WOOL;
    public static final MovingPistonTile MOVING_PISTON;
    public static final PlantTile DANDELION;
    public static final PlantTile ROSE;
    public static final PlantTile BROWN_MUSHROOM;
    public static final PlantTile RED_MUSHROOM;
    public static final MixinTile BLOCK_GOLD;
    public static final MixinTile BLOCK_IRON;
    public static final MixinTile DOUBLE_STONE_SLAB;
    public static final MixinTile STONE_SLAB;
    public static final MixinTile BRICK;
    public static final MixinTile TNT;
    public static final MixinTile BOOKSHELF;
    public static final MixinTile MOSSY_COBBLESTONE;
    public static final MixinTile OBSIDIAN;
    public static final MixinTile TORCH;
    public static final MixinFireTile FIRE;
    public static final MixinTile MOB_SPAWNER;
    public static final MixinTile STAIRS_WOOD;
    public static final MixinTile CHEST;
    public static final MixinTile REDSTONE_DUST;
    public static final MixinTile DIAMOND_ORE;
    public static final MixinTile BLOCK_DIAMOND;
    public static final MixinTile WORKBENCH;
    public static final MixinTile CROPS;
    public static final MixinTile FARMLAND;
    public static final MixinTile FURNACE;
    public static final MixinTile FURNACE_LIT;
    public static final MixinTile STANDING_SIGN;
    public static final MixinTile DOOR_WOOD;
    public static final MixinTile LADDER;
    public static final MixinTile RAIL;
    public static final MixinTile STAIRS_STONE;
    public static final MixinTile WALL_SIGN;
    public static final MixinTile LEVER;
    public static final MixinTile WOODEN_PRESSURE_PLATE;
    public static final MixinTile DOOR_IRON;
    public static final MixinTile STONE_PRESSURE_PLATE;
    public static final MixinTile REDSTONE_ORE;
    public static final MixinTile REDSTONE_ORE_LIT;
    public static final MixinTile REDSTONE_TORCH;
    public static final MixinTile REDSTONE_TORCH_LIT;
    public static final MixinTile BUTTON;
    public static final MixinTile SNOW;
    public static final MixinTile ICE;
    public static final MixinTile SNOW_BLOCK;
    public static final MixinTile CACTUS;
    public static final MixinTile CLAY;
    public static final MixinTile REEDS;
    public static final MixinTile JUKEBOX;
    public static final MixinTile FENCE;
    public static final MixinTile PUMPKIN;
    public static final MixinTile NETHERRACK;
    public static final MixinTile SOUL_SAND;
    public static final MixinTile GLOWSTONE;
    public static final PortalTile PORTAL;
    public static final MixinTile LIT_PUMPKIN;
    public static final MixinTile CAKE;
    public static final MixinTile REDSTONE_REPEATER;
    public static final MixinTile REDSTONE_REPEATER_LIT;
    public static final MixinTile LOCKED_CHEST;
    public static final MixinTile TRAPDOOR;
    @Shadow()
    public static boolean resetActive;

    static {
        TICKS_RANDOMLY = new boolean[256];
        FULL_OPAQUE = new boolean[256];
        HAS_TILE_ENTITY = new boolean[256];
        field_1941 = new int[256];
        subTypes = new int[256];
        LUMINANCES = new int[256];
        MULTIPLE_STATES = new boolean[256];
        STONE_SOUNDS = new TileSounds("stone", 1.0f, 1.0f);
        WOOD_SOUNDS = new TileSounds("wood", 1.0f, 1.0f);
        GRAVEL_SOUNDS = new TileSounds("gravel", 1.0f, 1.0f);
        GRASS_SOUNDS = new TileSounds("grass", 1.0f, 1.0f);
        PISTON_SOUNDS = new TileSounds("stone", 1.0f, 1.0f);
        METAL_SOUNDS = new TileSounds("stone", 1.0f, 1.5f);
        GLASS_SOUNDS = new GlassTileSounds("stone", 1.0f, 1.0f);
        WOOL_SOUNDS = new TileSounds("cloth", 1.0f, 1.0f);
        SAND_SOUNDS = new SandTileSounds("sand", 1.0f, 1.0f);
        BY_ID = new MixinTile[256];
        IS_AIR = new boolean[256];
        STONE = new MixinStoneTile(1, 215).hardness(1.5f).blastResistance(10.0f).sounds(PISTON_SOUNDS).name("stone");
        GRASS = (MixinGrassTile) new MixinGrassTile(2).hardness(0.6f).sounds(GRASS_SOUNDS).name("grass").setSubTypes(5);
        DIRT = new DirtTile(3, 2).hardness(0.5f).sounds(GRAVEL_SOUNDS).name("dirt");
        COBBLESTONE = new BlockColor(4, 214, Material.STONE).hardness(2.0f).blastResistance(10.0f).sounds(PISTON_SOUNDS).name("stonebrick");
        WOOD = new MixinTile(5, 4, Material.WOOD).hardness(2.0f).blastResistance(5.0f).sounds(WOOD_SOUNDS).name("wood").multipleStates();
        SAPLING = new SaplingTile(6, 15).hardness(0.0f).sounds(GRASS_SOUNDS).name("sapling").multipleStates();
        BEDROCK = new MixinTile(7, 17, Material.STONE).setUnbreakable().blastResistance(6000000.0f).sounds(PISTON_SOUNDS).name("bedrock").nonOpaque();
        FLOWING_WATER = new MixinFlowingFluidTile(8, Material.WATER).hardness(0.5f).method_1590(3).name("water").nonOpaque().multipleStates();
        STILL_WATER = new StillFluidTile(9, Material.WATER).hardness(0.5f).method_1590(3).name("water").nonOpaque().multipleStates();
        FLOWING_LAVA = new MixinFlowingFluidTile(10, Material.LAVA).hardness(0.5f).luminance(1.0f).method_1590(255).name("lava").nonOpaque().multipleStates();
        STILL_LAVA = new StillFluidTile(11, Material.LAVA).hardness(0.5f).luminance(1.0f).method_1590(255).name("lava").nonOpaque().multipleStates();
        SAND = new MixinSandTile(12, 18).hardness(0.5f).sounds(SAND_SOUNDS).name("sand").setSubTypes(4);
        GRAVEL = new GravelTile(13, 19).hardness(0.6f).sounds(GRAVEL_SOUNDS).name("gravel");
        GOLD_ORE = new OreTile(14, 32).hardness(3.0f).blastResistance(5.0f).sounds(PISTON_SOUNDS).name("oreGold");
        IRON_ORE = new OreTile(15, 33).hardness(3.0f).blastResistance(5.0f).sounds(PISTON_SOUNDS).name("oreIron");
        COAL_ORE = new OreTile(16, 34).hardness(3.0f).blastResistance(5.0f).sounds(PISTON_SOUNDS).name("oreCoal");
        LOG = new LogTile(17).hardness(2.0f).sounds(WOOD_SOUNDS).name("log").multipleStates();
        LEAVES = (MixinLeavesTile) new MixinLeavesTile(18, 52).hardness(0.2f).method_1590(1).sounds(GRASS_SOUNDS).name("leaves").nonOpaque().multipleStates();
        SPONGE = new SpongeTile(19).hardness(0.6f).sounds(GRASS_SOUNDS).name("sponge");
        GLASS = new MixinGlassTile(20, 49, Material.GLASS, false).hardness(0.3f).sounds(GLASS_SOUNDS).name("glass");
        LAPIS_LAZULI_ORE = new OreTile(21, 160).hardness(3.0f).blastResistance(5.0f).sounds(PISTON_SOUNDS).name("oreLapis");
        LAPIS_LAZULI_BLOCK = new MixinTile(22, 144, Material.STONE).hardness(3.0f).blastResistance(5.0f).sounds(PISTON_SOUNDS).name("blockLapis");
        DISPENSER = new MixinDispenserTile(23).hardness(3.5f).sounds(PISTON_SOUNDS).name("dispenser").multipleStates();
        SANDSTONE = new SandstoneTile(24).sounds(PISTON_SOUNDS).hardness(0.8f).name("sandStone");
        NOTEBLOCK = new NoteblockTile(25).hardness(0.8f).name("musicBlock").multipleStates();
        BED = new BedTile(26).hardness(0.2f).name("bed").nonOpaque().multipleStates();
        GOLDEN_RAIL = new RailTile(27, 179, true).hardness(0.7f).sounds(METAL_SOUNDS).name("goldenRail").multipleStates();
        DETECTOR_RAIL = new DetectorRailTile(28, 195).hardness(0.7f).sounds(METAL_SOUNDS).name("detectorRail").multipleStates();
        STICKY_PISTON = new PistonTile(29, 106, true).name("pistonStickyBase").multipleStates();
        WEB = new CobwebTile(30, 11).method_1590(1).hardness(4.0f).name("web");
        TALLGRASS = (TallGrassTile) new TallGrassTile(31, 39).hardness(0.0f).sounds(GRASS_SOUNDS).name("tallgrass");
        DEADBUSH = (DeadBushTile) new DeadBushTile(32, 55).hardness(0.0f).sounds(GRASS_SOUNDS).name("deadbush");
        PISTON = new PistonTile(33, 107, false).name("pistonBase").multipleStates();
        PISTON_HEAD = (PistonHead) new PistonHead(34, 107).multipleStates();
        WOOL = new WoolTile().hardness(0.8f).sounds(WOOL_SOUNDS).name("cloth").multipleStates();
        MOVING_PISTON = new MovingPistonTile(36);
        DANDELION = (PlantTile) new PlantTile(37, 13).hardness(0.0f).sounds(GRASS_SOUNDS).name("flower");
        ROSE = (PlantTile) new PlantTile(38, 12).hardness(0.0f).sounds(GRASS_SOUNDS).name("rose");
        BROWN_MUSHROOM = (PlantTile) new MushroomTile(39, 29).hardness(0.0f).sounds(GRASS_SOUNDS).luminance(0.125f).name("mushroom");
        RED_MUSHROOM = (PlantTile) new MushroomTile(40, 28).hardness(0.0f).sounds(GRASS_SOUNDS).name("mushroom");
        BLOCK_GOLD = new PreciousBlockTile(41, 23).hardness(3.0f).blastResistance(10.0f).sounds(METAL_SOUNDS).name("blockGold");
        BLOCK_IRON = new PreciousBlockTile(42, 22).hardness(5.0f).blastResistance(10.0f).sounds(METAL_SOUNDS).name("blockIron");
        DOUBLE_STONE_SLAB = new StoneSlabTile(43, true).hardness(2.0f).blastResistance(10.0f).sounds(PISTON_SOUNDS).name("stoneSlab");
        STONE_SLAB = new StoneSlabTile(44, false).hardness(2.0f).blastResistance(10.0f).sounds(PISTON_SOUNDS).name("stoneSlab");
        BRICK = new MixinTile(45, 7, Material.STONE).hardness(2.0f).blastResistance(10.0f).sounds(PISTON_SOUNDS).name("brick");
        TNT = new TntTile(46, 8).hardness(0.0f).sounds(GRASS_SOUNDS).name("tnt");
        BOOKSHELF = new BookshelfTile(47, 35).hardness(1.5f).sounds(WOOD_SOUNDS).name("bookshelf");
        MOSSY_COBBLESTONE = new MixinTile(48, 36, Material.STONE).hardness(2.0f).blastResistance(10.0f).sounds(PISTON_SOUNDS).name("stoneMoss");
        OBSIDIAN = new ObsidianTile(49, 37).hardness(10.0f).blastResistance(2000.0f).sounds(PISTON_SOUNDS).name("obsidian");
        TORCH = new TorchTile(50, 80).hardness(0.0f).luminance(0.9375f).sounds(WOOD_SOUNDS).name("torch").multipleStates();
        FIRE = (MixinFireTile) new MixinFireTile(51, 31).hardness(0.0f).luminance(1.0f).sounds(WOOD_SOUNDS).name("fire").nonOpaque().multipleStates();
        MOB_SPAWNER = new MobSpawnerTile(52, 65).hardness(5.0f).sounds(METAL_SOUNDS).name("mobSpawner").nonOpaque();
        STAIRS_WOOD = new MixinStairsTile(53, WOOD).name("stairsWood").multipleStates();
        CHEST = new ChestTile(54).hardness(2.5f).sounds(WOOD_SOUNDS).name("chest").multipleStates();
        REDSTONE_DUST = new RedstoneDustTile(55, 164).hardness(0.0f).sounds(STONE_SOUNDS).name("redstoneDust").nonOpaque().multipleStates();
        DIAMOND_ORE = new OreTile(56, 50).hardness(3.0f).blastResistance(5.0f).sounds(PISTON_SOUNDS).name("oreDiamond");
        BLOCK_DIAMOND = new PreciousBlockTile(57, 24).hardness(5.0f).blastResistance(10.0f).sounds(METAL_SOUNDS).name("blockDiamond");
        WORKBENCH = new WorkbenchTile(58).hardness(2.5f).sounds(WOOD_SOUNDS).name("workbench");
        CROPS = new CropsTile(59, 88).hardness(0.0f).sounds(GRASS_SOUNDS).name("crops").nonOpaque().multipleStates();
        FARMLAND = new FarmlandTile(60).hardness(0.6f).sounds(GRAVEL_SOUNDS).name("farmland");
        FURNACE = new MixinFurnaceTile(61, false).hardness(3.5f).sounds(PISTON_SOUNDS).name("furnace").multipleStates();
        FURNACE_LIT = new MixinFurnaceTile(62, true).hardness(3.5f).sounds(PISTON_SOUNDS).luminance(0.875f).name("furnace").multipleStates();
        STANDING_SIGN = new SignTile(63, MixinSign.class, true).hardness(1.0f).sounds(WOOD_SOUNDS).name("sign").nonOpaque().multipleStates();
        DOOR_WOOD = new MixinDoorTile(64, Material.WOOD).hardness(3.0f).sounds(WOOD_SOUNDS).name("doorWood").nonOpaque().multipleStates();
        LADDER = new MixinLadderTile(65, 83).hardness(0.4f).sounds(WOOD_SOUNDS).name("ladder").multipleStates();
        RAIL = new RailTile(66, 128, false).hardness(0.7f).sounds(METAL_SOUNDS).name("rail").multipleStates();
        STAIRS_STONE = new MixinStairsTile(67, COBBLESTONE).name("stairsStone").multipleStates();
        WALL_SIGN = new SignTile(68, MixinSign.class, false).hardness(1.0f).sounds(WOOD_SOUNDS).name("sign").nonOpaque().multipleStates();
        LEVER = new LeverTile(69, 96).hardness(0.5f).sounds(WOOD_SOUNDS).name("lever").multipleStates();
        WOODEN_PRESSURE_PLATE = new PressurePlateTile(70, Tile.STONE.tex, PressurePlateTrigger.mobs, Material.STONE).hardness(0.5f).sounds(PISTON_SOUNDS).name("pressurePlate").multipleStates();
        DOOR_IRON = new MixinDoorTile(71, Material.METAL).hardness(5.0f).sounds(METAL_SOUNDS).name("doorIron").nonOpaque().multipleStates();
        STONE_PRESSURE_PLATE = new PressurePlateTile(72, Tile.WOOD.tex, PressurePlateTrigger.everything, Material.WOOD).hardness(0.5f).sounds(WOOD_SOUNDS).name("pressurePlate").multipleStates();
        REDSTONE_ORE = new RedstoneOreTile(73, 51, false).hardness(3.0f).blastResistance(5.0f).sounds(PISTON_SOUNDS).name("oreRedstone").multipleStates();
        REDSTONE_ORE_LIT = new RedstoneOreTile(74, 51, true).luminance(0.625f).hardness(3.0f).blastResistance(5.0f).sounds(PISTON_SOUNDS).name("oreRedstone").multipleStates();
        REDSTONE_TORCH = new RedstoneTorchTile(75, 115, false).hardness(0.0f).sounds(WOOD_SOUNDS).name("notGate").multipleStates();
        REDSTONE_TORCH_LIT = new RedstoneTorchTile(76, 99, true).hardness(0.0f).luminance(0.5f).sounds(WOOD_SOUNDS).name("notGate").multipleStates();
        BUTTON = new ButtonTile(77, Tile.STONE.tex).hardness(0.5f).sounds(PISTON_SOUNDS).name("button").multipleStates();
        SNOW = new SnowLayerTile(78, 66).hardness(0.1f).sounds(WOOL_SOUNDS).name("snow");
        ICE = new MixinIceTile(79, 67).hardness(0.5f).method_1590(3).sounds(GLASS_SOUNDS).name("ice");
        SNOW_BLOCK = new SnowBlockTile(80, 66).hardness(0.2f).sounds(WOOL_SOUNDS).name("snow");
        CACTUS = new MixinCactusTile(81, 70).hardness(0.4f).sounds(WOOL_SOUNDS).name("cactus");
        CLAY = new ClayTile(82, 72).hardness(0.6f).sounds(GRAVEL_SOUNDS).name("clay");
        REEDS = new SugarCane(83, 73).hardness(0.0f).sounds(GRASS_SOUNDS).name("reeds").nonOpaque();
        JUKEBOX = new JukeboxTile(84, 74).hardness(2.0f).blastResistance(10.0f).sounds(PISTON_SOUNDS).name("jukebox").multipleStates();
        FENCE = new FenceTile(85, 4).hardness(2.0f).blastResistance(5.0f).sounds(WOOD_SOUNDS).name("fence").multipleStates();
        PUMPKIN = new PumpkinTile(86, 102, false).hardness(1.0f).sounds(WOOD_SOUNDS).name("pumpkin").multipleStates();
        NETHERRACK = new NetherrackTile(87, 103).hardness(0.4f).sounds(PISTON_SOUNDS).name("hellrock");
        SOUL_SAND = new SoulSandTile(88, 104).hardness(0.5f).sounds(SAND_SOUNDS).name("hellsand");
        GLOWSTONE = new GlowstoneTile(89, 105, Material.STONE).hardness(0.3f).sounds(GLASS_SOUNDS).luminance(1.0f).name("lightgem");
        PORTAL = (PortalTile) new PortalTile(90, 14).hardness(-1.0f).sounds(GLASS_SOUNDS).luminance(0.75f).name("portal");
        LIT_PUMPKIN = new PumpkinTile(91, 102, true).hardness(1.0f).sounds(WOOD_SOUNDS).luminance(1.0f).name("litpumpkin").multipleStates();
        CAKE = new CakeTile(92, 121).hardness(0.5f).sounds(WOOL_SOUNDS).name("cake").nonOpaque().multipleStates();
        REDSTONE_REPEATER = new RedstoneRepeaterTile(93, false).hardness(0.0f).sounds(WOOD_SOUNDS).name("diode").nonOpaque().multipleStates();
        REDSTONE_REPEATER_LIT = new RedstoneRepeaterTile(94, true).hardness(0.0f).luminance(0.625f).sounds(WOOD_SOUNDS).name("diode").nonOpaque().multipleStates();
        LOCKED_CHEST = new LockedChestTile(95).hardness(0.0f).luminance(1.0f).sounds(WOOD_SOUNDS).name("lockedchest").setTicksRandomly(true).multipleStates();
        TRAPDOOR = new TrapdoorTile(96, Material.WOOD).hardness(3.0f).sounds(WOOD_SOUNDS).name("trapdoor").nonOpaque().multipleStates();
        ItemType.byId[Tile.WOOL.id] = new WoolItem(Tile.WOOL.id - 256).setName("cloth");
        ItemType.byId[Tile.LOG.id] = new LogItem(Tile.LOG.id - 256).setName("log");
        ItemType.byId[Tile.GRASS.id] = new ItemSubtypes(Tile.GRASS.id - 256).setName("grass");
        ItemType.byId[Tile.SAND.id] = new ItemSubtypes(Tile.SAND.id - 256).setName("sand");
        ItemType.byId[Tile.TALLGRASS.id] = new ItemSubtypes(Tile.TALLGRASS.id - 256).setName("tallgrass");
        ItemType.byId[Tile.STONE_SLAB.id] = new StoneSlabItem(Tile.STONE_SLAB.id - 256).setName("stoneSlab");
        ItemType.byId[Tile.SAPLING.id] = new SaplingItem(Tile.SAPLING.id - 256).setName("sapling");
        ItemType.byId[Tile.LEAVES.id] = new LeavesItem(Tile.LEAVES.id - 256).setName("leaves");
        ItemType.byId[Tile.PISTON.id] = new PistonItem(Tile.PISTON.id - 256);
        ItemType.byId[Tile.STICKY_PISTON.id] = new PistonItem(Tile.STICKY_PISTON.id - 256);
        for (int i = 0; i < 256; ++i) {
            if (BY_ID[i] == null || ItemType.byId[i] != null) continue;
            ItemType.byId[i] = new MixinPlaceableTileItem(i - 256);
            BY_ID[i].afterTileItemCreated();
        }
        Tile.IS_AIR[0] = true;
        Stats.method_753();
    }

    public final int id;
    public final Material material;
    public int tex;
    public double minX;
    public double minY;
    public double minZ;
    public double maxX;
    public double maxY;
    public double maxZ;
    public TileSounds sounds = STONE_SOUNDS;
    public float field_1927 = 1.0f;
    public float field_1901 = 0.6f;
    public int textureNum = 0;
    protected float hardness;
    protected float resistance;
    protected boolean field_1918 = true;
    protected boolean opaque = true;
    private String name;

    protected MixinTile(int id, Material material) {
        if (BY_ID[id] != null) {
            throw new IllegalArgumentException("Slot " + id + " is already occupied by " + BY_ID[id] + " when adding " + this);
        }
        this.material = material;
        Tile.BY_ID[id] = this;
        this.id = id;
        this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        Tile.FULL_OPAQUE[id] = this.isFullOpaque();
        Tile.field_1941[id] = this.isFullOpaque() ? 255 : 0;
        Tile.IS_AIR[id] = !material.method_906();
        Tile.HAS_TILE_ENTITY[id] = false;
    }

    protected MixinTile(int id, int tex, Material material) {
        this(id, material);
        this.tex = tex;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static void resetArea(MixinLevel world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        boolean oldResetActive = resetActive;
        resetActive = true;
        for (int x = minX; x <= maxX; ++x) {
            for (int y = minY; y <= maxY; ++y) {
                for (int z = minZ; z <= maxZ; ++z) {
                    int blockID = world.getTileId(x, y, z);
                    if (blockID == 0) continue;
                    BY_ID[blockID].reset(world, x, y, z, false);
                }
            }
        }
        resetActive = oldResetActive;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    static Class _mthclass$(String s) {
        try {
            return Class.forName(s);
        } catch (ClassNotFoundException classnotfoundexception) {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected MixinTile multipleStates() {
        Tile.MULTIPLE_STATES[this.id] = true;
        return this;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void afterTileItemCreated() {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected MixinTile sounds(TileSounds sounds) {
        this.sounds = sounds;
        return this;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected MixinTile method_1590(int i) {
        Tile.field_1941[this.id] = i;
        return this;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected MixinTile setSubTypes(int i) {
        Tile.subTypes[this.id] = i;
        return this;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected MixinTile luminance(float luminance) {
        Tile.LUMINANCES[this.id] = (int) (15.0f * luminance);
        return this;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected MixinTile blastResistance(float resistance) {
        this.resistance = resistance * 3.0f;
        return this;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isFullCube() {
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int method_1621() {
        return 0;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean shouldRender(TileView blockAccess, int i, int j, int k) {
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected MixinTile hardness(float hardness) {
        this.hardness = hardness;
        if (this.resistance < hardness * 5.0f) {
            this.resistance = hardness * 5.0f;
        }
        return this;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected MixinTile setUnbreakable() {
        this.hardness(-1.0f);
        return this;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float getHardness() {
        return this.hardness;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected MixinTile setTicksRandomly(boolean ticksRandomly) {
        Tile.TICKS_RANDOMLY[this.id] = ticksRandomly;
        return this;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setBoundingBox(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float method_1604(TileView iblockaccess, int i, int j, int k) {
        return iblockaccess.method_1784(i, j, k, this.getBlockLightValue(iblockaccess, i, j, k));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getBlockLightValue(TileView iblockaccess, int i, int j, int k) {
        return LUMINANCES[this.id];
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_1618(TileView iblockaccess, int i, int j, int k, int l) {
        if (l == 0 && this.minY > 0.0) {
            return true;
        }
        if (l == 1 && this.maxY < 1.0) {
            return true;
        }
        if (l == 2 && this.minZ > 0.0) {
            return true;
        }
        if (l == 3 && this.maxZ < 1.0) {
            return true;
        }
        if (l == 4 && this.minX > 0.0) {
            return true;
        }
        if (l == 5 && this.maxX < 1.0) {
            return true;
        }
        return !iblockaccess.isFullOpaque(i, j, k);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_1573(TileView iblockaccess, int i, int j, int k, int l) {
        return iblockaccess.getMaterial(i, j, k).isSolid();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int method_1626(TileView iblockaccess, int i, int j, int k, int l) {
        return this.getTextureForSide(l, iblockaccess.getTileMeta(i, j, k));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getTextureForSide(int side, int meta) {
        return this.getTextureForSide(side);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getTextureForSide(int side) {
        return this.tex;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public Box getOutlineShape(MixinLevel level, int x, int y, int z) {
        return Box.getOrCreate((double) x + this.minX, (double) y + this.minY, (double) z + this.minZ, (double) x + this.maxX, (double) y + this.maxY, (double) z + this.maxZ);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void intersectsInLevel(MixinLevel world, int i, int j, int k, Box axisalignedbb, ArrayList intersections) {
        Box axisalignedbb1 = this.getCollisionShape(world, i, j, k);
        if (axisalignedbb1 != null && axisalignedbb.intersects(axisalignedbb1)) {
            intersections.add(axisalignedbb1);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public Box getCollisionShape(MixinLevel level, int x, int y, int z) {
        return Box.getOrCreate((double) x + this.minX, (double) y + this.minY, (double) z + this.minZ, (double) x + this.maxX, (double) y + this.maxY, (double) z + this.maxZ);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isFullOpaque() {
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_1571(int i, boolean flag) {
        return this.method_1576();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_1576() {
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void onScheduledTick(MixinLevel level, int x, int y, int z, Random rand) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void randomDisplayTick(MixinLevel level, int x, int y, int z, Random rand) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1612(MixinLevel world, int i, int j, int k, int l) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1609(MixinLevel level, int x, int y, int z, int id) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getTickrate() {
        return 10;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1611(MixinLevel level, int x, int y, int z) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void onTileRemoved(MixinLevel level, int x, int y, int z) {
        level.triggerManager.removeArea(x, y, z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getDropCount(Random rand) {
        return 1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getDropId(int meta, Random rand) {
        return this.id;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float method_1582(MixinPlayer entityplayer) {
        if (this.hardness < 0.0f) {
            return 0.0f;
        }
        if (!entityplayer.method_514(this)) {
            return 1.0f / this.hardness / 100.0f;
        }
        return entityplayer.method_511(this) / this.hardness / 30.0f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void drop(MixinLevel level, int x, int y, int z, int meta) {
        this.beforeDestroyedByExplosion(level, x, y, z, meta, 1.0f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void beforeDestroyedByExplosion(MixinLevel level, int x, int y, int z, int meta, float dropChance) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void dropItem(MixinLevel level, int x, int y, int z, MixinItemInstance itemstack) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected int getDropMeta(int i) {
        return 0;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float method_1575(MixinEntity entity) {
        return this.resistance / 5.0f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public HitResult raycast(MixinLevel world, int x, int y, int z, Vec3f vec3d, Vec3f vec3d1) {
        this.method_1616(world, x, y, z);
        vec3d = vec3d.method_1301(-x, -y, -z);
        vec3d1 = vec3d1.method_1301(-x, -y, -z);
        Vec3f vec3d2 = vec3d.method_1295(vec3d1, this.minX);
        Vec3f vec3d3 = vec3d.method_1295(vec3d1, this.maxX);
        Vec3f vec3d4 = vec3d.method_1299(vec3d1, this.minY);
        Vec3f vec3d5 = vec3d.method_1299(vec3d1, this.maxY);
        Vec3f vec3d6 = vec3d.method_1302(vec3d1, this.minZ);
        Vec3f vec3d7 = vec3d.method_1302(vec3d1, this.maxZ);
        if (!this.method_1579(vec3d2)) {
            vec3d2 = null;
        }
        if (!this.method_1579(vec3d3)) {
            vec3d3 = null;
        }
        if (!this.method_1586(vec3d4)) {
            vec3d4 = null;
        }
        if (!this.method_1586(vec3d5)) {
            vec3d5 = null;
        }
        if (!this.method_1588(vec3d6)) {
            vec3d6 = null;
        }
        if (!this.method_1588(vec3d7)) {
            vec3d7 = null;
        }
        Vec3f vec3d8 = null;
        if (vec3d2 != null && (vec3d8 == null || vec3d.method_1294(vec3d2) < vec3d.method_1294(vec3d8))) {
            vec3d8 = vec3d2;
        }
        if (vec3d3 != null && (vec3d8 == null || vec3d.method_1294(vec3d3) < vec3d.method_1294(vec3d8))) {
            vec3d8 = vec3d3;
        }
        if (vec3d4 != null && (vec3d8 == null || vec3d.method_1294(vec3d4) < vec3d.method_1294(vec3d8))) {
            vec3d8 = vec3d4;
        }
        if (vec3d5 != null && (vec3d8 == null || vec3d.method_1294(vec3d5) < vec3d.method_1294(vec3d8))) {
            vec3d8 = vec3d5;
        }
        if (vec3d6 != null && (vec3d8 == null || vec3d.method_1294(vec3d6) < vec3d.method_1294(vec3d8))) {
            vec3d8 = vec3d6;
        }
        if (vec3d7 != null && (vec3d8 == null || vec3d.method_1294(vec3d7) < vec3d.method_1294(vec3d8))) {
            vec3d8 = vec3d7;
        }
        if (vec3d8 == null) {
            return null;
        }
        int byte0 = -1;
        if (vec3d8 == vec3d2) {
            byte0 = 4;
        }
        if (vec3d8 == vec3d3) {
            byte0 = 5;
        }
        if (vec3d8 == vec3d4) {
            byte0 = 0;
        }
        if (vec3d8 == vec3d5) {
            byte0 = 1;
        }
        if (vec3d8 == vec3d6) {
            byte0 = 2;
        }
        if (vec3d8 == vec3d7) {
            byte0 = 3;
        }
        return new HitResult(x, y, z, byte0, vec3d8.method_1301(x, y, z));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected boolean method_1579(Vec3f vec3d) {
        if (vec3d == null) {
            return false;
        }
        return vec3d.y >= this.minY && vec3d.y <= this.maxY && vec3d.z >= this.minZ && vec3d.z <= this.maxZ;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected boolean method_1586(Vec3f vec3d) {
        if (vec3d == null) {
            return false;
        }
        return vec3d.x >= this.minX && vec3d.x <= this.maxX && vec3d.z >= this.minZ && vec3d.z <= this.maxZ;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected boolean method_1588(Vec3f vec3d) {
        if (vec3d == null) {
            return false;
        }
        return vec3d.x >= this.minX && vec3d.x <= this.maxX && vec3d.y >= this.minY && vec3d.y <= this.maxY;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void onDestroyedByExplosion(MixinLevel level, int x, int y, int z) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int method_1619() {
        return 0;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean canPlaceAt(MixinLevel level, int x, int y, int z, int meta) {
        return this.canPlaceAt(level, x, y, z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean canPlaceAt(MixinLevel level, int x, int y, int z) {
        int l = level.getTileId(x, y, z);
        return l == 0 || Tile.BY_ID[l].material.isReplaceable();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean activate(MixinLevel level, int x, int y, int z, MixinPlayer player) {
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1560(MixinLevel world, int i, int j, int k, MixinEntity entity) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void onPlaced(MixinLevel level, int x, int y, int z, int facing) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void onPunched(MixinLevel level, int x, int y, int z, MixinPlayer player) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1572(MixinLevel world, int i, int j, int k, MixinEntity entity, Vec3f vec3d) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1616(TileView iblockaccess, int i, int j, int k) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int method_1589(int i) {
        return 0xFFFFFF;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getTint(TileView iblockaccess, int i, int j, int k) {
        return 0xFFFFFF;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_1568(TileView iblockaccess, int i, int j, int k, int l) {
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean emitsRedstonePower() {
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void onEntityCollision(MixinLevel world, int i, int j, int k, MixinEntity entity) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_1570(MixinLevel world, int i, int j, int k, int l) {
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1605() {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void afterBreak(MixinLevel world, MixinPlayer entityplayer, int i, int j, int k, int l) {
        entityplayer.increaseStat(Stats.mineBlock[this.id], 1);
        this.drop(world, i, j, k, l);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isValidPosition(MixinLevel world, int i, int j, int k) {
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void afterPlaced(MixinLevel world, int i, int j, int k, MixinLivingEntity entityliving) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinTile name(String s) {
        this.name = "tile." + s;
        return this;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public String getName() {
        return I18n.translate(this.getTranslationKey() + ".name");
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public String getTranslationKey() {
        return this.name;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void onTileAction(MixinLevel world, int x, int y, int z, int blockMeta, int i1) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isOpaque() {
        return this.opaque;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected MixinTile nonOpaque() {
        this.opaque = false;
        return this;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getPistonPushMode() {
        return this.material.getPistonPushMode();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean canBeTriggered() {
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void addTriggerActivation(MixinLevel world, int i, int j, int k) {
        if (this.canBeTriggered()) {
            int metadata = Math.min((int) (world.getTileMeta(i, j, k) + 1), 15);
            world.method_223(i, j, k, metadata);
            if (metadata == 1) {
                this.onTriggerActivated(world, i, j, k);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void removeTriggerActivation(MixinLevel world, int i, int j, int k) {
        if (this.canBeTriggered()) {
            int metadata = world.getTileMeta(i, j, k) - 1;
            world.method_223(i, j, k, Math.max(metadata, 0));
            if (metadata == 0) {
                this.onTriggerDeactivated(world, i, j, k);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void onTriggerActivated(MixinLevel world, int i, int j, int k) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void onTriggerDeactivated(MixinLevel world, int i, int j, int k) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void reset(MixinLevel world, int i, int j, int k, boolean death) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int alwaysUseClick(MixinLevel world, int i, int j, int k) {
        return -1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getTextureNum() {
        return this.textureNum;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinTile setTextureNum(int t) {
        this.textureNum = t;
        return this;
    }
}
