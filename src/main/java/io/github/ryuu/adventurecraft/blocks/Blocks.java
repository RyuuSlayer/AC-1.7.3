package io.github.ryuu.adventurecraft.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.items.ItemSubtypes;

public class Blocks {

    public static final Tile lockedDoor = new BlockLockedDoor(150, 208, Items.doorKey.id).hardness(5.0f).sounds(Tile.WOOD_SOUNDS).name("lockedDoor").setTextureNum(3);

    public static final Tile lockedBossDoor;

    public static final Tile newMobSpawner;

    public static final Tile spawnBlock;

    public static final BlockTrigger triggerBlock;

    public static final Tile triggerDoor;

    public static final Tile spikeBlock;

    public static final BlockTriggerInverter triggerInverter;

    public static final BlockTriggerMemory triggerMemory;

    public static final BlockClip clipBlock;

    public static final BlockRedstoneTrigger redstoneTrigger;

    public static final BlockRedstonePower redstonePower;

    public static final BlockBombable bombableCobblestone;

    public static final BlockBombable bombableStone;

    public static final BlockWeather weather;

    public static final BlockMusic musicTriggered;

    public static final Tile pushableBlock;

    public static final BlockTimer timer;

    public static final BlockMessage message;

    public static final Tile fan;

    public static final Tile camera;

    public static final Tile lightBulb;

    public static final Tile fanOff;

    public static final Tile script;

    public static final Tile store;

    public static final Tile effect;

    public static final Tile darkness;

    public static final Tile triggerPushable;

    public static final Tile storage;

    public static final Tile healDamage;

    public static final Tile teleport;

    public static final Tile url;

    public static final Tile npcPath;

    public static final Tile pillarStone;

    public static final Tile pillarMetal;

    public static final Tile plant1;

    public static final Tile trees;

    public static final Tile glassBlocks;

    public static final Tile cageBlocks;

    public static final Tile stoneBlocks1;

    public static final Tile stoneBlocks2;

    public static final Tile stoneBlocks3;

    public static final Tile woodBlocks;

    public static final Tile halfSteps1;

    public static final Tile halfSteps2;

    public static final Tile halfSteps3;

    public static final Tile tableBlocks;

    public static final Tile chairBlocks1;

    public static final Tile chairBlocks2;

    public static final Tile chairBlocks3;

    public static final Tile chairBlocks4;

    public static final Tile ropes1;

    public static final Tile ropes2;

    public static final Tile chains;

    public static final Tile ladders1;

    public static final Tile ladders2;

    public static final Tile ladders3;

    public static final Tile ladders4;

    public static final Tile lights1;

    public static final Tile plant2;

    public static final Tile plant3;

    public static final Tile overlay1;

    public static final Tile stairs1;

    public static final Tile stairs2;

    public static final Tile stairs3;

    public static final Tile stairs4;

    public static final Tile slopes1;

    public static final Tile slopes2;

    public static final Tile slopes3;

    public static final Tile slopes4;

    private Blocks() {
    }

    static void convertACVersion(byte[] data) {
        if (data != null) {
            for (int i = 0; i < data.length; ++i) {
                int blockID = Chunk.translate256(data[i]);
                if (blockID >= 100 && blockID <= 122) {
                    data[i] = (byte) Chunk.translate128(blockID + 50);
                    continue;
                }
                if (blockID < 152 || blockID > 155)
                    continue;
                data[i] = (byte) Chunk.translate128(blockID + 21);
            }
        }
    }

    static {
        newMobSpawner = new BlockMobSpawner(151, 65).hardness(5.0f).sounds(Tile.METAL_SOUNDS).name("mobSpawner2");
        spawnBlock = new BlockSpawn(152, 0).setTextureNum(2).hardness(5.0f).sounds(Tile.METAL_SOUNDS).name("spawn");
        triggerBlock = (BlockTrigger) new BlockTrigger(153, 1).setTextureNum(2).hardness(5.0f).sounds(Tile.METAL_SOUNDS).name("trigger");
        triggerDoor = new BlockTriggeredDoor(154).hardness(5.0f).sounds(Tile.WOOD_SOUNDS).name("triggeredDoor").setTextureNum(3);
        spikeBlock = new BlockSpike(155).hardness(5.0f).sounds(Tile.METAL_SOUNDS).name("spike").setTextureNum(3);
        lockedBossDoor = new BlockLockedDoor(156, 210, Items.bossKey.id).hardness(5.0f).sounds(Tile.METAL_SOUNDS).name("lockedBossDoor").setTextureNum(3);
        triggerInverter = (BlockTriggerInverter) new BlockTriggerInverter(157, 2).setTextureNum(2).hardness(5.0f).sounds(Tile.METAL_SOUNDS).name("triggerInverter");
        triggerMemory = (BlockTriggerMemory) new BlockTriggerMemory(158, 3).setTextureNum(2).hardness(5.0f).sounds(Tile.METAL_SOUNDS).name("triggerMemory");
        clipBlock = (BlockClip) new BlockClip(159, 4, Material.AIR).setTextureNum(2).hardness(5.0f).sounds(Tile.METAL_SOUNDS).name("clip");
        redstoneTrigger = (BlockRedstoneTrigger) new BlockRedstoneTrigger(160, 228).hardness(5.0f).sounds(Tile.METAL_SOUNDS).name("redstoneTrigger").setTextureNum(3);
        redstonePower = (BlockRedstonePower) new BlockRedstonePower(161, 185).hardness(5.0f).sounds(Tile.METAL_SOUNDS).name("redstonePower");
        bombableCobblestone = (BlockBombable) new BlockBombable(162, 166, Material.STONE).hardness(2.0f).blastResistance(10.0f).sounds(Tile.PISTON_SOUNDS).name("crackedStonebrick");
        bombableStone = (BlockBombable) new BlockBombable(163, 167, Material.STONE).hardness(1.5f).blastResistance(10.0f).sounds(Tile.PISTON_SOUNDS).name("crackedStone");
        weather = (BlockWeather) new BlockWeather(164, 5).setTextureNum(2).hardness(1.5f).blastResistance(10.0f).sounds(Tile.PISTON_SOUNDS).name("weather");
        musicTriggered = (BlockMusic) new BlockMusic(165, 9).setTextureNum(2).hardness(1.5f).blastResistance(10.0f).sounds(Tile.PISTON_SOUNDS).name("music");
        pushableBlock = new BlockPushable(166, 212, Material.STONE).hardness(2.0f).blastResistance(10.0f).sounds(Tile.PISTON_SOUNDS).name("pushable").setTextureNum(3);
        timer = (BlockTimer) new BlockTimer(167, 8).setTextureNum(2).hardness(5.0f).sounds(Tile.METAL_SOUNDS).name("timer");
        message = (BlockMessage) new BlockMessage(168, 7).setTextureNum(2).hardness(5.0f).sounds(Tile.METAL_SOUNDS).name("message");
        fan = new BlockFan(169, 184, true).hardness(5.0f).sounds(Tile.METAL_SOUNDS).name("fan");
        camera = new BlockCamera(170, 6).setTextureNum(2).hardness(5.0f).sounds(Tile.METAL_SOUNDS).name("camera");
        lightBulb = new BlockLightBulb(171, 14).setTextureNum(2).hardness(5.0f).sounds(Tile.METAL_SOUNDS).name("lightBulb");
        fanOff = new BlockFan(172, 200, false).hardness(5.0f).sounds(Tile.METAL_SOUNDS).name("fan");
        script = new BlockScript(173, 15).setTextureNum(2).hardness(5.0f).sounds(Tile.METAL_SOUNDS).name("script");
        store = new BlockStore(174, 49).hardness(5.0f).sounds(Tile.GLASS_SOUNDS).name("store");
        effect = new BlockEffect(175, 244).setTextureNum(3).hardness(5.0f).sounds(Tile.METAL_SOUNDS).name("effect");
        url = new BlockUrl(176, 245).setTextureNum(3).hardness(5.0f).sounds(Tile.METAL_SOUNDS).name("url");
        npcPath = new BlockNpcPath(177, 247).setTextureNum(3).hardness(5.0f).sounds(Tile.METAL_SOUNDS).name("NPC Path Block");
        darkness = new BlockDarkness(200, 10).setTextureNum(2).hardness(5.0f).sounds(Tile.METAL_SOUNDS).name("darkness");
        triggerPushable = new BlockTriggerPushable(201, 213).hardness(2.0f).blastResistance(10.0f).sounds(Tile.PISTON_SOUNDS).name("triggerPushable").setTextureNum(3);
        storage = new BlockStorage(202, 11).setTextureNum(2).hardness(5.0f).sounds(Tile.METAL_SOUNDS).name("storage");
        healDamage = new BlockHealDamage(203, 12).setTextureNum(2).hardness(5.0f).sounds(Tile.METAL_SOUNDS).name("healDamage");
        teleport = new BlockTeleport(204, 13).setTextureNum(2).hardness(5.0f).sounds(Tile.METAL_SOUNDS).name("teleport");
        pillarStone = new BlockPillar(205, 32).setTextureNum(2).hardness(5.0f).sounds(Tile.PISTON_SOUNDS).name("pillarStone").setSubTypes(16);
        pillarMetal = new BlockPillar(206, 80).setTextureNum(2).hardness(5.0f).sounds(Tile.METAL_SOUNDS).name("pillarMetal").setSubTypes(16);
        plant1 = new BlockPlant(207, 112).setTextureNum(2).hardness(5.0f).sounds(Tile.GRASS_SOUNDS).name("flower").setSubTypes(16);
        trees = new BlockTree(208, 128).setTextureNum(2).hardness(5.0f).sounds(Tile.GRASS_SOUNDS).name("sapling").setSubTypes(16);
        glassBlocks = new BlockTransparent(209, 144).setTextureNum(2).hardness(5.0f).sounds(Tile.GLASS_SOUNDS).name("glass").setSubTypes(16);
        cageBlocks = new BlockTransparent(210, 160).setTextureNum(2).hardness(5.0f).sounds(Tile.GLASS_SOUNDS).name("cage").setSubTypes(10);
        stoneBlocks1 = new BlockSolid(211, 176).setTextureNum(2).hardness(5.0f).sounds(Tile.PISTON_SOUNDS).name("stone").setSubTypes(16);
        stoneBlocks2 = new BlockSolid(212, 192).setTextureNum(2).hardness(5.0f).sounds(Tile.PISTON_SOUNDS).name("stone").setSubTypes(16);
        stoneBlocks3 = new BlockSolid(213, 208).setTextureNum(2).hardness(5.0f).sounds(Tile.PISTON_SOUNDS).name("stone").setSubTypes(16);
        woodBlocks = new BlockSolid(214, 224).setTextureNum(2).hardness(5.0f).sounds(Tile.WOOD_SOUNDS).name("wood").setSubTypes(16);
        halfSteps1 = new BlockHalfStep(215, 240).setTextureNum(2).hardness(5.0f).sounds(Tile.PISTON_SOUNDS).name("halfStep").setSubTypes(16);
        halfSteps2 = new BlockHalfStep(216, 0).setTextureNum(3).hardness(5.0f).sounds(Tile.METAL_SOUNDS).name("halfStep").setSubTypes(16);
        halfSteps3 = new BlockHalfStep(217, 16).setTextureNum(3).hardness(5.0f).sounds(Tile.WOOD_SOUNDS).name("halfStepWood").setSubTypes(16);
        tableBlocks = new BlockTable(218, 32).setTextureNum(3).hardness(5.0f).sounds(Tile.WOOD_SOUNDS).name("table").setSubTypes(16);
        chairBlocks1 = new BlockChair(219, 64).setTextureNum(3).hardness(5.0f).sounds(Tile.WOOD_SOUNDS).name("chair").setSubTypes(16);
        chairBlocks2 = new BlockChair(220, 68).setTextureNum(3).hardness(5.0f).sounds(Tile.WOOD_SOUNDS).name("chair").setSubTypes(16);
        chairBlocks3 = new BlockChair(221, 64).setTextureNum(3).hardness(5.0f).sounds(Tile.WOOD_SOUNDS).name("chair").setSubTypes(16);
        chairBlocks4 = new BlockChair(222, 64).setTextureNum(3).hardness(5.0f).sounds(Tile.WOOD_SOUNDS).name("chair").setSubTypes(16);
        ropes1 = new BlockRope(223, 96).setTextureNum(3).hardness(5.0f).sounds(Tile.METAL_SOUNDS).name("rope").setSubTypes(15);
        ropes2 = new BlockRope(224, 101).setTextureNum(3).hardness(5.0f).sounds(Tile.METAL_SOUNDS).name("rope").setSubTypes(15);
        chains = new BlockChain(225, 106).setTextureNum(3).hardness(5.0f).sounds(Tile.METAL_SOUNDS).name("chain").setSubTypes(9);
        ladders1 = new BlockLadderSubtypes(226, 112).setTextureNum(3).hardness(5.0f).sounds(Tile.WOOD_SOUNDS).name("ladder").setSubTypes(16);
        ladders2 = new BlockLadderSubtypes(227, 116).setTextureNum(3).hardness(5.0f).sounds(Tile.WOOD_SOUNDS).name("ladder").setSubTypes(16);
        ladders3 = new BlockLadderSubtypes(228, 120).setTextureNum(3).hardness(5.0f).sounds(Tile.WOOD_SOUNDS).name("ladder").setSubTypes(16);
        ladders4 = new BlockLadderSubtypes(229, 124).setTextureNum(3).hardness(5.0f).sounds(Tile.WOOD_SOUNDS).name("ladder").setSubTypes(16);
        lights1 = new BlockPlant(230, 128).setTextureNum(3).hardness(5.0f).luminance(0.9375f).sounds(Tile.METAL_SOUNDS).name("torch").setSubTypes(14);
        plant2 = new BlockTree(231, 144).setTextureNum(3).hardness(5.0f).sounds(Tile.GRASS_SOUNDS).name("flower").setSubTypes(16);
        plant3 = new BlockTree(232, 160).setTextureNum(3).hardness(5.0f).sounds(Tile.GRASS_SOUNDS).name("flower").setSubTypes(16);
        overlay1 = new BlockOverlay(233, 176).setTextureNum(3).hardness(5.0f).sounds(Tile.GRASS_SOUNDS).name("overlay").setSubTypes(7);
        stairs1 = new BlockStairMulti(234, Tile.WOOD, 192).setTextureNum(3).name("stairs").setSubTypes(4);
        stairs2 = new BlockStairMulti(235, Tile.WOOD, 196).setTextureNum(3).name("stairs").setSubTypes(4);
        stairs3 = new BlockStairMulti(236, Tile.COBBLESTONE, 200).setTextureNum(3).name("stairs").setSubTypes(4);
        stairs4 = new BlockStairMulti(237, Tile.COBBLESTONE, 204).setTextureNum(3).name("stairs").setSubTypes(4);
        slopes1 = new BlockSlope(238, Tile.WOOD, 192).setTextureNum(3).name("slopes").setSubTypes(4);
        slopes2 = new BlockSlope(239, Tile.WOOD, 196).setTextureNum(3).name("slopes").setSubTypes(4);
        slopes3 = new BlockSlope(240, Tile.COBBLESTONE, 200).setTextureNum(3).name("slopes").setSubTypes(4);
        slopes4 = new BlockSlope(241, Tile.COBBLESTONE, 204).setTextureNum(3).name("slopes").setSubTypes(4);
        ItemType.byId[Blocks.pillarStone.id] = new ItemSubtypes(Blocks.pillarStone.id - 256).setName("pillarStone");
        ItemType.byId[Blocks.pillarMetal.id] = new ItemSubtypes(Blocks.pillarMetal.id - 256).setName("pillarMetal");
        ItemType.byId[Blocks.plant1.id] = new ItemSubtypes(Blocks.plant1.id - 256).setName("flower");
        ItemType.byId[Blocks.trees.id] = new ItemSubtypes(Blocks.trees.id - 256).setName("sapling");
        ItemType.byId[Blocks.glassBlocks.id] = new ItemSubtypes(Blocks.glassBlocks.id - 256).setName("glass");
        ItemType.byId[Blocks.cageBlocks.id] = new ItemSubtypes(Blocks.cageBlocks.id - 256).setName("cage");
        ItemType.byId[Blocks.stoneBlocks1.id] = new ItemSubtypes(Blocks.stoneBlocks1.id - 256).setName("stone");
        ItemType.byId[Blocks.stoneBlocks2.id] = new ItemSubtypes(Blocks.stoneBlocks2.id - 256).setName("stone");
        ItemType.byId[Blocks.stoneBlocks3.id] = new ItemSubtypes(Blocks.stoneBlocks3.id - 256).setName("stone");
        ItemType.byId[Blocks.woodBlocks.id] = new ItemSubtypes(Blocks.woodBlocks.id - 256).setName("wood");
        ItemType.byId[Blocks.halfSteps1.id] = new ItemSubtypes(Blocks.halfSteps1.id - 256).setName("halfStep");
        ItemType.byId[Blocks.halfSteps2.id] = new ItemSubtypes(Blocks.halfSteps2.id - 256).setName("halfStep");
        ItemType.byId[Blocks.halfSteps3.id] = new ItemSubtypes(Blocks.halfSteps3.id - 256).setName("halfStep");
        ItemType.byId[Blocks.tableBlocks.id] = new ItemSubtypes(Blocks.tableBlocks.id - 256).setName("table");
        ItemType.byId[Blocks.chairBlocks1.id] = new ItemSubtypes(Blocks.chairBlocks1.id - 256).setName("chair");
        ItemType.byId[Blocks.chairBlocks2.id] = new ItemSubtypes(Blocks.chairBlocks2.id - 256).setName("chair");
        ItemType.byId[Blocks.ropes1.id] = new ItemSubtypes(Blocks.ropes1.id - 256).setName("rope");
        ItemType.byId[Blocks.ropes2.id] = new ItemSubtypes(Blocks.ropes2.id - 256).setName("rope");
        ItemType.byId[Blocks.chains.id] = new ItemSubtypes(Blocks.chains.id - 256).setName("chain");
        ItemType.byId[Blocks.ladders1.id] = new ItemSubtypes(Blocks.ladders1.id - 256).setName("ladder");
        ItemType.byId[Blocks.ladders2.id] = new ItemSubtypes(Blocks.ladders2.id - 256).setName("ladder");
        ItemType.byId[Blocks.ladders3.id] = new ItemSubtypes(Blocks.ladders3.id - 256).setName("ladder");
        ItemType.byId[Blocks.ladders4.id] = new ItemSubtypes(Blocks.ladders4.id - 256).setName("ladder");
        ItemType.byId[Blocks.lights1.id] = new ItemSubtypes(Blocks.lights1.id - 256).setName("torch");
        ItemType.byId[Blocks.plant2.id] = new ItemSubtypes(Blocks.plant2.id - 256).setName("flower");
        ItemType.byId[Blocks.plant3.id] = new ItemSubtypes(Blocks.plant3.id - 256).setName("flower");
        ItemType.byId[Blocks.overlay1.id] = new ItemSubtypes(Blocks.overlay1.id - 256).setName("overlay");
        ItemType.byId[Blocks.stairs1.id] = new ItemSubtypes(Blocks.stairs1.id - 256).setName("stairs");
        ItemType.byId[Blocks.stairs2.id] = new ItemSubtypes(Blocks.stairs2.id - 256).setName("stairs");
        ItemType.byId[Blocks.stairs3.id] = new ItemSubtypes(Blocks.stairs3.id - 256).setName("stairs");
        ItemType.byId[Blocks.stairs4.id] = new ItemSubtypes(Blocks.stairs4.id - 256).setName("stairs");
        ItemType.byId[Blocks.slopes1.id] = new ItemSubtypes(Blocks.slopes1.id - 256).setName("slopes");
        ItemType.byId[Blocks.slopes2.id] = new ItemSubtypes(Blocks.slopes2.id - 256).setName("slopes");
        ItemType.byId[Blocks.slopes3.id] = new ItemSubtypes(Blocks.slopes3.id - 256).setName("slopes");
        ItemType.byId[Blocks.slopes4.id] = new ItemSubtypes(Blocks.slopes4.id - 256).setName("slopes");
        for (int i = 0; i < 256; ++i) {
            if (Tile.BY_ID[i] == null || ItemType.byId[i] != null)
                continue;
            ItemType.byId[i] = new PlaceableTileItem(i - 256);
            Tile.BY_ID[i].afterTileItemCreated();
        }
    }
}
