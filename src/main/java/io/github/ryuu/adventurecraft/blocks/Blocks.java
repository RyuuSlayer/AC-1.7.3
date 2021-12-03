package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.extensions.level.chunk.ExChunk;
import io.github.ryuu.adventurecraft.items.ItemSubtypes;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.mixin.tile.AccessTile;
import net.minecraft.item.ItemType;
import net.minecraft.item.PlaceableTileItem;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;

public class Blocks {

    public static final Tile lockedDoor;
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

    static {
        newMobSpawner = new BlockMobSpawner(151, 65).name("mobSpawner2");
        spawnBlock = new BlockSpawn(152, 0).name("spawn");
        triggerBlock = (BlockTrigger) new BlockTrigger(153, 1).name("trigger");
        triggerDoor = new BlockTriggeredDoor(154).name("triggeredDoor");
        spikeBlock = new BlockSpike(155).name("spike");
        lockedDoor = new BlockLockedDoor(150, 208, Items.doorKey.id, Tile.WOOD_SOUNDS).name("lockedDoor");
        lockedBossDoor = new BlockLockedDoor(156, 210, Items.bossKey.id, Tile.METAL_SOUNDS).name("lockedBossDoor");
        triggerInverter = (BlockTriggerInverter) new BlockTriggerInverter(157, 2).name("triggerInverter");
        triggerMemory = (BlockTriggerMemory) new BlockTriggerMemory(158, 3).name("triggerMemory");
        clipBlock = (BlockClip) new BlockClip(159, 4, Material.AIR).name("clip");
        redstoneTrigger = (BlockRedstoneTrigger) new BlockRedstoneTrigger(160, 228).name("redstoneTrigger");
        redstonePower = (BlockRedstonePower) new BlockRedstonePower(161, 185).name("redstonePower");
        bombableCobblestone = (BlockBombable) new BlockBombable(162, 166, Material.STONE, 2.0f).name("crackedStonebrick");
        bombableStone = (BlockBombable) new BlockBombable(163, 167, Material.STONE, 1.5f).name("crackedStone");
        weather = (BlockWeather) new BlockWeather(164, 5).name("weather");
        musicTriggered = (BlockMusic) new BlockMusic(165, 9).name("music");
        pushableBlock = new BlockPushable(166, 212, Material.STONE).name("pushable");
        timer = (BlockTimer) new BlockTimer(167, 8).name("timer");
        message = (BlockMessage) new BlockMessage(168, 7).name("message");
        fan = new BlockFan(169, 184, true).name("fan");
        camera = new BlockCamera(170, 6).name("camera");
        lightBulb = new BlockLightBulb(171, 14).name("lightBulb");
        fanOff = new BlockFan(172, 200, false).name("fan");
        script = new BlockScript(173, 15).name("script");
        store = new BlockStore(174, 49).name("store");
        effect = new BlockEffect(175, 244).name("effect");
        url = new BlockUrl(176, 245).name("url");
        npcPath = new BlockNpcPath(177, 247).name("NPC Path Block");
        darkness = new BlockDarkness(200, 10).name("darkness");
        triggerPushable = new BlockTriggerPushable(201, 213).name("triggerPushable");
        storage = new BlockStorage(202, 11).name("storage");
        healDamage = new BlockHealDamage(203, 12).name("healDamage");
        teleport = new BlockTeleport(204, 13).name("teleport");
        pillarStone = new BlockPillar(205, 32, Tile.PISTON_SOUNDS).name("pillarStone");
        pillarMetal = new BlockPillar(206, 80, Tile.METAL_SOUNDS).name("pillarMetal");
        plant1 = new BlockPlant(207, 112, Tile.GRASS_SOUNDS, 0.0f, 16, 2).name("flower");
        trees = new BlockTree(208, 128, 2).name("sapling");
        glassBlocks = new BlockTransparent(209, 144, 16).name("glass");
        cageBlocks = new BlockTransparent(210, 160, 10) .name("cage") ;
        stoneBlocks1 = new BlockSolid(211, 176, Tile.PISTON_SOUNDS).name("stone");
        stoneBlocks2 = new BlockSolid(212, 192, Tile.PISTON_SOUNDS).name("stone");
        stoneBlocks3 = new BlockSolid(213, 208, Tile.PISTON_SOUNDS).name("stone");
        woodBlocks = new BlockSolid(214, 224, Tile.WOOD_SOUNDS).name("wood");
        halfSteps1 = new BlockHalfStep(215, 240, Tile.PISTON_SOUNDS, 2).name("halfStep");
        halfSteps2 = new BlockHalfStep(216, 0, Tile.METAL_SOUNDS, 3).name("halfStep");
        halfSteps3 = new BlockHalfStep(217, 16, Tile.WOOD_SOUNDS, 3).name("halfStepWood");
        tableBlocks = new BlockTable(218, 32).name("table");
        chairBlocks1 = new BlockChair(219, 64).name("chair");
        chairBlocks2 = new BlockChair(220, 68).name("chair");
        chairBlocks3 = new BlockChair(221, 64).name("chair");
        chairBlocks4 = new BlockChair(222, 64).name("chair");
        ropes1 = new BlockRope(223, 96).name("rope");
        ropes2 = new BlockRope(224, 101).name("rope");
        chains = new BlockChain(225, 106).name("chain");
        ladders1 = new BlockLadderSubtypes(226, 112);
        ladders2 = new BlockLadderSubtypes(227, 116);
        ladders3 = new BlockLadderSubtypes(228, 120);
        ladders4 = new BlockLadderSubtypes(229, 124);
        lights1 = new BlockPlant(230, 128, Tile.METAL_SOUNDS, 0.9375f, 14, 3).name("torch");
        plant2 = new BlockTree(231, 144, 3).name("flower");
        plant3 = new BlockTree(232, 160, 3).name("flower");
        overlay1 = new BlockOverlay(233, 176).name("overlay");
        stairs1 = new BlockStairMulti(234, Tile.WOOD, 192).name("stairs");
        stairs2 = new BlockStairMulti(235, Tile.WOOD, 196).name("stairs");
        stairs3 = new BlockStairMulti(236, Tile.COBBLESTONE, 200).name("stairs");
        stairs4 = new BlockStairMulti(237, Tile.COBBLESTONE, 204).name("stairs");
        slopes1 = new BlockSlope(238, Tile.WOOD, 192).name("slopes");
        slopes2 = new BlockSlope(239, Tile.WOOD, 196).name("slopes");
        slopes3 = new BlockSlope(240, Tile.COBBLESTONE, 200).name("slopes");
        slopes4 = new BlockSlope(241, Tile.COBBLESTONE, 204).name("slopes");

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
            if (Tile.BY_ID[i] != null && ItemType.byId[i] == null) {
                ItemType.byId[i] = new PlaceableTileItem(i - 256);
                ((AccessTile) Tile.BY_ID[i]).invokeAfterTileItemCreated();
            }
        }
    }

    private Blocks() {
    }

    public static void convertACVersion(byte[] data) {
        if (data != null) {
            for (int i = 0; i < data.length; ++i) {
                int blockID = ExChunk.translate256(data[i]);
                if (blockID >= 100 && blockID <= 122) {
                    data[i] = (byte) ExChunk.translate128(blockID + 50);
                    continue;
                }
                if (blockID < 152 || blockID > 155) continue;
                data[i] = (byte) ExChunk.translate128(blockID + 21);
            }
        }
    }
}
