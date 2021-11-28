package io.github.ryuu.adventurecraft.mixin.tile;

import io.github.ryuu.adventurecraft.blocks.BlockColor;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import io.github.ryuu.adventurecraft.items.ItemSubtypes;
import net.minecraft.entity.player.Player;
import net.minecraft.item.*;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.stat.Stats;
import net.minecraft.tile.*;
import net.minecraft.tile.entity.Sign;
import net.minecraft.tile.material.Material;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.maths.Vec3f;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Tile.class)
public abstract class MixinTile implements ExTile {

    @Mutable
    @Shadow @Final public static Tile STONE;

    @Shadow protected abstract Tile hardness(float f);

    @Shadow protected abstract Tile setUnbreakable();

    @Shadow protected abstract Tile blastResistance(float f);

    @Shadow @Final public static TileSounds PISTON_SOUNDS;

    @Mutable
    @Shadow @Final public static GrassTile GRASS;

    @Shadow @Final public static TileSounds GRASS_SOUNDS;

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
    public TileSounds sounds;

    @Shadow
    public float field_1927;

    @Shadow
    public float field_1901;

    @Shadow
    protected float hardness;

    @Shadow
    protected float resistance;

    @Shadow
    protected boolean field_1918;

    @Shadow
    protected boolean opaque;

    @Shadow
    private String name;

    @Shadow @Final public static Tile[] BY_ID;

    @Shadow protected abstract void afterTileItemCreated();

    @Shadow protected abstract Tile sounds(TileSounds arg);

    @Mutable
    @Shadow @Final public static Tile COBBLESTONE;

    @Shadow protected abstract Tile method_1590(int i);

    @Shadow protected abstract Tile nonOpaque();

    @Shadow protected abstract Tile multipleStates();

    @Mutable
    @Shadow @Final public static Tile FLOWING_WATER;

    @Mutable
    @Shadow @Final public static Tile STILL_WATER;

    @Mutable
    @Shadow @Final public static Tile FLOWING_LAVA;

    @Shadow protected abstract Tile luminance(float f);

    @Mutable
    @Shadow @Final public static Tile STILL_LAVA;
    private static final int[] subTypes;
    public int textureNum = 0;

    static {
        subTypes = new int[256];
        BY_ID[1] = null;
        STONE = ((MixinTile) (Object) ((MixinTile) (Object) ((MixinTile) (Object) new StoneTile(1, 1)).hardness(1.5f)).blastResistance(10.0f)).sounds(PISTON_SOUNDS).name("stone");
        BY_ID[2] = null;
        GRASS = (GrassTile) ((ExTile) ((MixinTile) (Object) ((MixinTile) (Object) AccessGrassTile.newGrassTile(2)).hardness(0.6f)).sounds(GRASS_SOUNDS).name("grass")).adventurecraft$setSubTypes(5);
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

        SAND = new SandTile(12, 18).hardness(0.5f).sounds(SAND_SOUNDS).name("sand").setSubTypes(4);
        GRAVEL = new GravelTile(13, 19).hardness(0.6f).sounds(GRAVEL_SOUNDS).name("gravel");
        GOLD_ORE = new OreTile(14, 32).hardness(3.0f).blastResistance(5.0f).sounds(PISTON_SOUNDS).name("oreGold");
        IRON_ORE = new OreTile(15, 33).hardness(3.0f).blastResistance(5.0f).sounds(PISTON_SOUNDS).name("oreIron");
        COAL_ORE = new OreTile(16, 34).hardness(3.0f).blastResistance(5.0f).sounds(PISTON_SOUNDS).name("oreCoal");
        LOG = new LogTile(17).hardness(2.0f).sounds(WOOD_SOUNDS).name("log").multipleStates();
        LEAVES = (LeavesTile) new LeavesTile(18, 52).hardness(0.2f).method_1590(1).sounds(GRASS_SOUNDS).name("leaves").nonOpaque().multipleStates();
        SPONGE = new SpongeTile(19).hardness(0.6f).sounds(GRASS_SOUNDS).name("sponge");
        GLASS = new GlassTile(20, 49, Material.GLASS, false).hardness(0.3f).sounds(GLASS_SOUNDS).name("glass");
        LAPIS_LAZULI_ORE = new OreTile(21, 160).hardness(3.0f).blastResistance(5.0f).sounds(PISTON_SOUNDS).name("oreLapis");
        LAPIS_LAZULI_BLOCK = new Tile(22, 144, Material.STONE).hardness(3.0f).blastResistance(5.0f).sounds(PISTON_SOUNDS).name("blockLapis");
        DISPENSER = new DispenserTile(23).hardness(3.5f).sounds(PISTON_SOUNDS).name("dispenser").multipleStates();
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
        BRICK = new Tile(45, 7, Material.STONE).hardness(2.0f).blastResistance(10.0f).sounds(PISTON_SOUNDS).name("brick");
        TNT = new TntTile(46, 8).hardness(0.0f).sounds(GRASS_SOUNDS).name("tnt");
        BOOKSHELF = new BookshelfTile(47, 35).hardness(1.5f).sounds(WOOD_SOUNDS).name("bookshelf");
        MOSSY_COBBLESTONE = new Tile(48, 36, Material.STONE).hardness(2.0f).blastResistance(10.0f).sounds(PISTON_SOUNDS).name("stoneMoss");
        OBSIDIAN = new ObsidianTile(49, 37).hardness(10.0f).blastResistance(2000.0f).sounds(PISTON_SOUNDS).name("obsidian");
        TORCH = new TorchTile(50, 80).hardness(0.0f).luminance(0.9375f).sounds(WOOD_SOUNDS).name("torch").multipleStates();
        FIRE = (FireTile) new FireTile(51, 31).hardness(0.0f).luminance(1.0f).sounds(WOOD_SOUNDS).name("fire").nonOpaque().multipleStates();
        MOB_SPAWNER = new MobSpawnerTile(52, 65).hardness(5.0f).sounds(METAL_SOUNDS).name("mobSpawner").nonOpaque();
        STAIRS_WOOD = new StairsTile(53, WOOD).name("stairsWood").multipleStates();
        CHEST = new ChestTile(54).hardness(2.5f).sounds(WOOD_SOUNDS).name("chest").multipleStates();
        REDSTONE_DUST = new RedstoneDustTile(55, 164).hardness(0.0f).sounds(STONE_SOUNDS).name("redstoneDust").nonOpaque().multipleStates();
        DIAMOND_ORE = new OreTile(56, 50).hardness(3.0f).blastResistance(5.0f).sounds(PISTON_SOUNDS).name("oreDiamond");
        BLOCK_DIAMOND = new PreciousBlockTile(57, 24).hardness(5.0f).blastResistance(10.0f).sounds(METAL_SOUNDS).name("blockDiamond");
        WORKBENCH = new WorkbenchTile(58).hardness(2.5f).sounds(WOOD_SOUNDS).name("workbench");
        CROPS = new CropsTile(59, 88).hardness(0.0f).sounds(GRASS_SOUNDS).name("crops").nonOpaque().multipleStates();
        FARMLAND = new FarmlandTile(60).hardness(0.6f).sounds(GRAVEL_SOUNDS).name("farmland");
        FURNACE = new FurnaceTile(61, false).hardness(3.5f).sounds(PISTON_SOUNDS).name("furnace").multipleStates();
        FURNACE_LIT = new FurnaceTile(62, true).hardness(3.5f).sounds(PISTON_SOUNDS).luminance(0.875f).name("furnace").multipleStates();
        STANDING_SIGN = new SignTile(63, Sign.class, true).hardness(1.0f).sounds(WOOD_SOUNDS).name("sign").nonOpaque().multipleStates();
        DOOR_WOOD = new DoorTile(64, Material.WOOD).hardness(3.0f).sounds(WOOD_SOUNDS).name("doorWood").nonOpaque().multipleStates();
        LADDER = new LadderTile(65, 83).hardness(0.4f).sounds(WOOD_SOUNDS).name("ladder").multipleStates();
        RAIL = new RailTile(66, 128, false).hardness(0.7f).sounds(METAL_SOUNDS).name("rail").multipleStates();
        STAIRS_STONE = new StairsTile(67, COBBLESTONE).name("stairsStone").multipleStates();
        WALL_SIGN = new SignTile(68, Sign.class, false).hardness(1.0f).sounds(WOOD_SOUNDS).name("sign").nonOpaque().multipleStates();
        LEVER = new LeverTile(69, 96).hardness(0.5f).sounds(WOOD_SOUNDS).name("lever").multipleStates();
        WOODEN_PRESSURE_PLATE = new PressurePlateTile(70, Tile.STONE.tex, PressurePlateTrigger.mobs, Material.STONE).hardness(0.5f).sounds(PISTON_SOUNDS).name("pressurePlate").multipleStates();
        DOOR_IRON = new DoorTile(71, Material.METAL).hardness(5.0f).sounds(METAL_SOUNDS).name("doorIron").nonOpaque().multipleStates();
        STONE_PRESSURE_PLATE = new PressurePlateTile(72, Tile.WOOD.tex, PressurePlateTrigger.everything, Material.WOOD).hardness(0.5f).sounds(WOOD_SOUNDS).name("pressurePlate").multipleStates();
        REDSTONE_ORE = new RedstoneOreTile(73, 51, false).hardness(3.0f).blastResistance(5.0f).sounds(PISTON_SOUNDS).name("oreRedstone").multipleStates();
        REDSTONE_ORE_LIT = new RedstoneOreTile(74, 51, true).luminance(0.625f).hardness(3.0f).blastResistance(5.0f).sounds(PISTON_SOUNDS).name("oreRedstone").multipleStates();
        REDSTONE_TORCH = new RedstoneTorchTile(75, 115, false).hardness(0.0f).sounds(WOOD_SOUNDS).name("notGate").multipleStates();
        REDSTONE_TORCH_LIT = new RedstoneTorchTile(76, 99, true).hardness(0.0f).luminance(0.5f).sounds(WOOD_SOUNDS).name("notGate").multipleStates();
        BUTTON = new ButtonTile(77, Tile.STONE.tex).hardness(0.5f).sounds(PISTON_SOUNDS).name("button").multipleStates();
        SNOW = new SnowLayerTile(78, 66).hardness(0.1f).sounds(WOOL_SOUNDS).name("snow");
        ICE = new IceTile(79, 67).hardness(0.5f).method_1590(3).sounds(GLASS_SOUNDS).name("ice");
        SNOW_BLOCK = new SnowBlockTile(80, 66).hardness(0.2f).sounds(WOOL_SOUNDS).name("snow");
        CACTUS = new CactusTile(81, 70).hardness(0.4f).sounds(WOOL_SOUNDS).name("cactus");
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
            ItemType.byId[i] = new PlaceableTileItem(i - 256);
            ((MixinTile) (Object) BY_ID[i]).afterTileItemCreated();
        }
        Tile.IS_AIR[0] = true;
        Stats.method_753();
    }



    // TODO Remove all uses of resetArea because it's unnecessary


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
    public int adventurecraft$alwaysUseClick(Level world, int i, int j, int k) {
        return -1;
    }

    @Override
    public int adventurecraft$getTextureNum() {
        return this.textureNum;
    }

    @Override
    public Tile adventurecraft$setTextureNum(int t) {
        this.textureNum = t;
        return ((Tile) (Object) this);
    }

    @Override
    public boolean adventurecraft$shouldRender(TileView blockAccess, int i, int j, int k) {
        return true;
    }

    @Override
    public Tile adventurecraft$setSubTypes(int i) {
        subTypes[this.id] = i;
        return ((Tile) (Object) this);
    }
}
