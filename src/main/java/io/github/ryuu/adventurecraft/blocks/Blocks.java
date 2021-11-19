package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.items.ItemSubtypes;
import io.github.ryuu.adventurecraft.items.Items;
import net.minecraft.item.ItemType;
import net.minecraft.item.PlaceableTileItem;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;

public class Blocks {
    public static final Tile lockedDoor = (new BlockLockedDoor(150, 208, Items.doorKey.id)).setTextureNum(3).hardness(5.0F).sounds(Tile.WOOD_SOUNDS).setName("lockedDoor");

    public static final Tile lockedBossDoor;

    public static final Tile newMobSpawner = (new BlockMobSpawner(151, 65)).hardness(5.0F).sounds(Tile.METAL_SOUNDS).setName("mobSpawner2");

    public static final Tile spawnBlock = (new BlockSpawn(152, 0)).setTextureNum(2).hardness(5.0F).sounds(Tile.METAL_SOUNDS).setName("spawn");

    public static final BlockTrigger triggerBlock = (BlockTrigger) (new BlockTrigger(153, 1)).setTextureNum(2).hardness(5.0F).sounds(Tile.METAL_SOUNDS).setName("trigger");

    public static final Tile triggerDoor = (new BlockTriggeredDoor(154)).setTextureNum(3).hardness(5.0F).sounds(Tile.WOOD_SOUNDS).setName("triggeredDoor");

    public static final Tile spikeBlock = (new BlockSpike(155)).setTextureNum(3).hardness(5.0F).sounds(Tile.METAL_SOUNDS).setName("spike");

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
        lockedBossDoor = (new BlockLockedDoor(156, 210, Items.bossKey.id)).setTextureNum(3).hardness(5.0F).sounds(Tile.METAL_SOUNDS).setName("lockedBossDoor");
        triggerInverter = (BlockTriggerInverter) (new BlockTriggerInverter(157, 2)).setTextureNum(2).hardness(5.0F).sounds(Tile.METAL_SOUNDS).setName("triggerInverter");
        triggerMemory = (BlockTriggerMemory) (new BlockTriggerMemory(158, 3)).setTextureNum(2).hardness(5.0F).sounds(Tile.METAL_SOUNDS).setName("triggerMemory");
        clipBlock = (BlockClip) (new BlockClip(159, 4, Material.AIR)).setTextureNum(2).hardness(5.0F).sounds(Tile.METAL_SOUNDS).setName("clip");
        redstoneTrigger = (BlockRedstoneTrigger) (new BlockRedstoneTrigger(160, 228)).setTextureNum(3).hardness(5.0F).sounds(Tile.METAL_SOUNDS).setName("redstoneTrigger");
        redstonePower = (BlockRedstonePower) (new BlockRedstonePower(161, 185)).hardness(5.0F).sounds(Tile.METAL_SOUNDS).setName("redstonePower");
        bombableCobblestone = (BlockBombable) (new BlockBombable(162, 166, Material.STONE)).hardness(2.0F).blastResistance(10.0F).sounds(Tile.PISTON_SOUNDS).setName("crackedStonebrick");
        bombableStone = (BlockBombable) (new BlockBombable(163, 167, Material.STONE)).hardness(1.5F).blastResistance(10.0F).sounds(Tile.PISTON_SOUNDS).setName("crackedStone");
        weather = (BlockWeather) (new BlockWeather(164, 5)).setTextureNum(2).hardness(1.5F).blastResistance(10.0F).sounds(Tile.PISTON_SOUNDS).setName("weather");
        musicTriggered = (BlockMusic) (new BlockMusic(165, 9)).setTextureNum(2).hardness(1.5F).blastResistance(10.0F).sounds(Tile.PISTON_SOUNDS).setName("music");
        pushableBlock = (new BlockPushable(166, 212, Material.STONE)).setTextureNum(3).hardness(2.0F).blastResistance(10.0F).sounds(Tile.PISTON_SOUNDS).setName("pushable");
        timer = (BlockTimer) (new BlockTimer(167, 8)).setTextureNum(2).hardness(5.0F).sounds(Tile.METAL_SOUNDS).setName("timer");
        message = (BlockMessage) (new BlockMessage(168, 7)).setTextureNum(2).hardness(5.0F).sounds(Tile.METAL_SOUNDS).setName("message");
        fan = (new BlockFan(169, 184, true)).hardness(5.0F).sounds(Tile.METAL_SOUNDS).setName("fan");
        camera = (new BlockCamera(170, 6)).setTextureNum(2).hardness(5.0F).sounds(Tile.METAL_SOUNDS).setName("camera");
        lightBulb = (new BlockLightBulb(171, 14)).setTextureNum(2).hardness(5.0F).sounds(Tile.METAL_SOUNDS).setName("lightBulb");
        fanOff = (new BlockFan(172, 200, false)).hardness(5.0F).sounds(Tile.METAL_SOUNDS).setName("fan");
        script = (new BlockScript(173, 15)).setTextureNum(2).hardness(5.0F).sounds(Tile.METAL_SOUNDS).setName("script");
        store = (new BlockStore(174, 49)).hardness(5.0F).sounds(Tile.GLASS_SOUNDS).setName("store");
        effect = (new BlockEffect(175, 244)).setTextureNum(3).hardness(5.0F).sounds(Tile.METAL_SOUNDS).setName("effect");
        url = (new BlockUrl(176, 245)).setTextureNum(3).hardness(5.0F).sounds(Tile.METAL_SOUNDS).setName("url");
        npcPath = (new BlockNpcPath(177, 247)).setTextureNum(3).hardness(5.0F).sounds(Tile.METAL_SOUNDS).setName("NPC Path Block");
        darkness = (new BlockDarkness(200, 10)).setTextureNum(2).hardness(5.0F).sounds(Tile.METAL_SOUNDS).setName("darkness");
        triggerPushable = (new BlockTriggerPushable(201, 213)).setTextureNum(3).hardness(2.0F).blastResistance(10.0F).sounds(Tile.PISTON_SOUNDS).setName("triggerPushable");
        storage = (new BlockStorage(202, 11)).setTextureNum(2).hardness(5.0F).sounds(Tile.METAL_SOUNDS).setName("storage");
        healDamage = (new BlockHealDamage(203, 12)).setTextureNum(2).hardness(5.0F).sounds(Tile.METAL_SOUNDS).setName("healDamage");
        teleport = (new BlockTeleport(204, 13)).setTextureNum(2).hardness(5.0F).sounds(Tile.METAL_SOUNDS).setName("teleport");
        pillarStone = (new BlockPillar(205, 32)).setTextureNum(2).hardness(5.0F).sounds(Tile.PISTON_SOUNDS).setName("pillarStone").setSubTypes(16);
        pillarMetal = (new BlockPillar(206, 80)).setTextureNum(2).hardness(5.0F).sounds(Tile.METAL_SOUNDS).setName("pillarMetal").setSubTypes(16);
        plant1 = (new BlockPlant(207, 112)).setTextureNum(2).hardness(5.0F).sounds(Tile.GRASS_SOUNDS).setName("flower").setSubTypes(16);
        trees = (new BlockTree(208, 128)).setTextureNum(2).hardness(5.0F).sounds(Tile.GRASS_SOUNDS).setName("sapling").setSubTypes(16);
        glassBlocks = (new BlockTransparent(209, 144)).setTextureNum(2).hardness(5.0F).sounds(Tile.GLASS_SOUNDS).setName("glass").setSubTypes(16);
        cageBlocks = (new BlockTransparent(210, 160)).setTextureNum(2).hardness(5.0F).sounds(Tile.GLASS_SOUNDS).setName("cage").setSubTypes(10);
        stoneBlocks1 = (new BlockSolid(211, 176)).setTextureNum(2).hardness(5.0F).sounds(Tile.PISTON_SOUNDS).setName("stone").setSubTypes(16);
        stoneBlocks2 = (new BlockSolid(212, 192)).setTextureNum(2).hardness(5.0F).sounds(Tile.PISTON_SOUNDS).setName("stone").setSubTypes(16);
        stoneBlocks3 = (new BlockSolid(213, 208)).setTextureNum(2).hardness(5.0F).sounds(Tile.PISTON_SOUNDS).setName("stone").setSubTypes(16);
        woodBlocks = (new BlockSolid(214, 224)).setTextureNum(2).hardness(5.0F).sounds(Tile.WOOD_SOUNDS).setName("wood").setSubTypes(16);
        halfSteps1 = (new BlockHalfStep(215, 240)).setTextureNum(2).hardness(5.0F).sounds(Tile.PISTON_SOUNDS).setName("halfStep").setSubTypes(16);
        halfSteps2 = (new BlockHalfStep(216, 0)).setTextureNum(3).hardness(5.0F).sounds(Tile.METAL_SOUNDS).setName("halfStep").setSubTypes(16);
        halfSteps3 = (new BlockHalfStep(217, 16)).setTextureNum(3).hardness(5.0F).sounds(Tile.WOOD_SOUNDS).setName("halfStepWood").setSubTypes(16);
        tableBlocks = (new BlockTable(218, 32)).setTextureNum(3).hardness(5.0F).sounds(Tile.WOOD_SOUNDS).setName("table").setSubTypes(16);
        chairBlocks1 = (new BlockChair(219, 64)).setTextureNum(3).hardness(5.0F).sounds(Tile.WOOD_SOUNDS).setName("chair").setSubTypes(16);
        chairBlocks2 = (new BlockChair(220, 68)).setTextureNum(3).hardness(5.0F).sounds(Tile.WOOD_SOUNDS).setName("chair").setSubTypes(16);
        chairBlocks3 = (new BlockChair(221, 64)).setTextureNum(3).hardness(5.0F).sounds(Tile.WOOD_SOUNDS).setName("chair").setSubTypes(16);
        chairBlocks4 = (new BlockChair(222, 64)).setTextureNum(3).hardness(5.0F).sounds(Tile.WOOD_SOUNDS).setName("chair").setSubTypes(16);
        ropes1 = (new BlockRope(223, 96)).setTextureNum(3).hardness(5.0F).sounds(Tile.METAL_SOUNDS).setName("rope").setSubTypes(15);
        ropes2 = (new BlockRope(224, 101)).setTextureNum(3).hardness(5.0F).sounds(Tile.METAL_SOUNDS).setName("rope").setSubTypes(15);
        chains = (new BlockChain(225, 106)).setTextureNum(3).hardness(5.0F).sounds(Tile.METAL_SOUNDS).setName("chain").setSubTypes(9);
        ladders1 = (new BlockLadderSubtypes(226, 112)).setTextureNum(3).hardness(5.0F).sounds(Tile.WOOD_SOUNDS).setName("ladder").setSubTypes(16);
        ladders2 = (new BlockLadderSubtypes(227, 116)).setTextureNum(3).hardness(5.0F).sounds(Tile.WOOD_SOUNDS).setName("ladder").setSubTypes(16);
        ladders3 = (new BlockLadderSubtypes(228, 120)).setTextureNum(3).hardness(5.0F).sounds(Tile.WOOD_SOUNDS).setName("ladder").setSubTypes(16);
        ladders4 = (new BlockLadderSubtypes(229, 124)).setTextureNum(3).hardness(5.0F).sounds(Tile.WOOD_SOUNDS).setName("ladder").setSubTypes(16);
        lights1 = (new BlockPlant(230, 128)).setTextureNum(3).hardness(5.0F).a(0.9375F).sounds(Tile.METAL_SOUNDS).setName("torch").setSubTypes(14);
        plant2 = (new BlockTree(231, 144)).setTextureNum(3).hardness(5.0F).sounds(Tile.GRASS_SOUNDS).setName("flower").setSubTypes(16);
        plant3 = (new BlockTree(232, 160)).setTextureNum(3).hardness(5.0F).sounds(Tile.GRASS_SOUNDS).setName("flower").setSubTypes(16);
        overlay1 = (new BlockOverlay(233, 176)).setTextureNum(3).hardness(5.0F).sounds(Tile.GRASS_SOUNDS).setName("overlay").setSubTypes(7);
        stairs1 = (new BlockStairMulti(234, Tile.WOOD, 192)).setTextureNum(3).setName("stairs").setSubTypes(4);
        stairs2 = (new BlockStairMulti(235, Tile.WOOD, 196)).setTextureNum(3).setName("stairs").setSubTypes(4);
        stairs3 = (new BlockStairMulti(236, Tile.COBBLESTONE, 200)).setTextureNum(3).setName("stairs").setSubTypes(4);
        stairs4 = (new BlockStairMulti(237, Tile.COBBLESTONE, 204)).setTextureNum(3).setName("stairs").setSubTypes(4);
        slopes1 = (new BlockSlope(238, Tile.WOOD, 192)).setTextureNum(3).setName("slopes").setSubTypes(4);
        slopes2 = (new BlockSlope(239, Tile.WOOD, 196)).setTextureNum(3).setName("slopes").setSubTypes(4);
        slopes3 = (new BlockSlope(240, Tile.COBBLESTONE, 200)).setTextureNum(3).setName("slopes").setSubTypes(4);
        slopes4 = (new BlockSlope(241, Tile.COBBLESTONE, 204)).setTextureNum(3).setName("slopes").setSubTypes(4);
        ItemType.byId[pillarStone.id] = (new ItemSubtypes(pillarStone.id - 256)).setName("pillarStone");
        ItemType.byId[pillarMetal.id] = (new ItemSubtypes(pillarMetal.id - 256)).setName("pillarMetal");
        ItemType.byId[plant1.id] = (new ItemSubtypes(plant1.id - 256)).setName("flower");
        ItemType.byId[trees.id] = (new ItemSubtypes(trees.id - 256)).setName("sapling");
        ItemType.byId[glassBlocks.id] = (new ItemSubtypes(glassBlocks.id - 256)).setName("glass");
        ItemType.byId[cageBlocks.id] = (new ItemSubtypes(cageBlocks.id - 256)).setName("cage");
        ItemType.byId[stoneBlocks1.id] = (new ItemSubtypes(stoneBlocks1.id - 256)).setName("stone");
        ItemType.byId[stoneBlocks2.id] = (new ItemSubtypes(stoneBlocks2.id - 256)).setName("stone");
        ItemType.byId[stoneBlocks3.id] = (new ItemSubtypes(stoneBlocks3.id - 256)).setName("stone");
        ItemType.byId[woodBlocks.id] = (new ItemSubtypes(woodBlocks.id - 256)).setName("wood");
        ItemType.byId[halfSteps1.id] = (new ItemSubtypes(halfSteps1.id - 256)).setName("halfStep");
        ItemType.byId[halfSteps2.id] = (new ItemSubtypes(halfSteps2.id - 256)).setName("halfStep");
        ItemType.byId[halfSteps3.id] = (new ItemSubtypes(halfSteps3.id - 256)).setName("halfStep");
        ItemType.byId[tableBlocks.id] = (new ItemSubtypes(tableBlocks.id - 256)).setName("table");
        ItemType.byId[chairBlocks1.id] = (new ItemSubtypes(chairBlocks1.id - 256)).setName("chair");
        ItemType.byId[chairBlocks2.id] = (new ItemSubtypes(chairBlocks2.id - 256)).setName("chair");
        ItemType.byId[ropes1.id] = (new ItemSubtypes(ropes1.id - 256)).setName("rope");
        ItemType.byId[ropes2.id] = (new ItemSubtypes(ropes2.id - 256)).setName("rope");
        ItemType.byId[chains.id] = (new ItemSubtypes(chains.id - 256)).setName("chain");
        ItemType.byId[ladders1.id] = (new ItemSubtypes(ladders1.id - 256)).setName("ladder");
        ItemType.byId[ladders2.id] = (new ItemSubtypes(ladders2.id - 256)).setName("ladder");
        ItemType.byId[ladders3.id] = (new ItemSubtypes(ladders3.id - 256)).setName("ladder");
        ItemType.byId[ladders4.id] = (new ItemSubtypes(ladders4.id - 256)).setName("ladder");
        ItemType.byId[lights1.id] = (new ItemSubtypes(lights1.id - 256)).setName("torch");
        ItemType.byId[plant2.id] = (new ItemSubtypes(plant2.id - 256)).setName("flower");
        ItemType.byId[plant3.id] = (new ItemSubtypes(plant3.id - 256)).setName("flower");
        ItemType.byId[overlay1.id] = (new ItemSubtypes(overlay1.id - 256)).setName("overlay");
        ItemType.byId[stairs1.id] = (new ItemSubtypes(stairs1.id - 256)).setName("stairs");
        ItemType.byId[stairs2.id] = (new ItemSubtypes(stairs2.id - 256)).setName("stairs");
        ItemType.byId[stairs3.id] = (new ItemSubtypes(stairs3.id - 256)).setName("stairs");
        ItemType.byId[stairs4.id] = (new ItemSubtypes(stairs4.id - 256)).setName("stairs");
        ItemType.byId[slopes1.id] = (new ItemSubtypes(slopes1.id - 256)).setName("slopes");
        ItemType.byId[slopes2.id] = (new ItemSubtypes(slopes2.id - 256)).setName("slopes");
        ItemType.byId[slopes3.id] = (new ItemSubtypes(slopes3.id - 256)).setName("slopes");
        ItemType.byId[slopes4.id] = (new ItemSubtypes(slopes4.id - 256)).setName("slopes");
        for (int i = 0; i < 256; i++) {
            if (Tile.BY_ID[i] != null && ItemType.byId[i] == null) {
                ItemType.byId[i] = new PlaceableTileItem(i - 256);
                Tile.BY_ID[i].afterTileItemCreated();
            }
        }
    }

    public static void convertACVersion(byte[] data) {
        if (data != null)
            for (int i = 0; i < data.length; i++) {
                int blockID = Chunk.translate256(data[i]);
                if (blockID >= 100 && blockID <= 122) {
                    data[i] = (byte) Chunk.translate128(blockID + 50);
                } else if (blockID >= 152 && blockID <= 155) {
                    data[i] = (byte) Chunk.translate128(blockID + 21);
                }
            }
    }
}