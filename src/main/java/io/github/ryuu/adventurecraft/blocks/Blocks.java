package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.items.ItemSubtypes;
import io.github.ryuu.adventurecraft.items.Items;
import net.minecraft.item.ItemType;
import net.minecraft.item.PlaceableTileItem;
import net.minecraft.tile.Tile;

public class Blocks {
    public static final Tile lockedDoor = (new BlockLockedDoor(150, 208, Items.doorKey.bf)).c(5.0F).a(Tile.e).a("lockedDoor").setTextureNum(3);

    public static final Tile lockedBossDoor;

    public static final Tile newMobSpawner = (new BlockMobSpawner(151, 65)).c(5.0F).a(Tile.i).a("mobSpawner2");

    public static final Tile spawnBlock = (new BlockSpawn(152, 0)).setTextureNum(2).c(5.0F).a(Tile.i).a("spawn");

    public static final BlockTrigger triggerBlock = (BlockTrigger) (new BlockTrigger(153, 1)).setTextureNum(2).c(5.0F).a(Tile.i).a("trigger");

    public static final Tile triggerDoor = (new BlockTriggeredDoor(154)).c(5.0F).a(Tile.e).a("triggeredDoor").setTextureNum(3);

    public static final Tile spikeBlock = (new BlockSpike(155)).c(5.0F).a(Tile.i).a("spike").setTextureNum(3);

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
        lockedBossDoor = (new BlockLockedDoor(156, 210, Items.bossKey.bf)).c(5.0F).a(Tile.i).a("lockedBossDoor").setTextureNum(3);
        triggerInverter = (BlockTriggerInverter) (new BlockTriggerInverter(157, 2)).setTextureNum(2).c(5.0F).a(Tile.i).a("triggerInverter");
        triggerMemory = (BlockTriggerMemory) (new BlockTriggerMemory(158, 3)).setTextureNum(2).c(5.0F).a(Tile.i).a("triggerMemory");
        clipBlock = (BlockClip) (new BlockClip(159, 4, ln.a)).setTextureNum(2).c(5.0F).a(Tile.i).a("clip");
        redstoneTrigger = (BlockRedstoneTrigger) (new BlockRedstoneTrigger(160, 228)).c(5.0F).a(Tile.i).a("redstoneTrigger").setTextureNum(3);
        redstonePower = (BlockRedstonePower) (new BlockRedstonePower(161, 185)).c(5.0F).a(Tile.i).a("redstonePower");
        bombableCobblestone = (BlockBombable) (new BlockBombable(162, 166, ln.e)).c(2.0F).b(10.0F).a(Tile.h).a("crackedStonebrick");
        bombableStone = (BlockBombable) (new BlockBombable(163, 167, ln.e)).c(1.5F).b(10.0F).a(Tile.h).a("crackedStone");
        weather = (BlockWeather) (new BlockWeather(164, 5)).setTextureNum(2).c(1.5F).b(10.0F).a(Tile.h).a("weather");
        musicTriggered = (BlockMusic) (new BlockMusic(165, 9)).setTextureNum(2).c(1.5F).b(10.0F).a(Tile.h).a("music");
        pushableBlock = (new BlockPushable(166, 212, ln.e)).c(2.0F).b(10.0F).a(Tile.h).a("pushable").setTextureNum(3);
        timer = (BlockTimer) (new BlockTimer(167, 8)).setTextureNum(2).c(5.0F).a(Tile.i).a("timer");
        message = (BlockMessage) (new BlockMessage(168, 7)).setTextureNum(2).c(5.0F).a(Tile.i).a("message");
        fan = (new BlockFan(169, 184, true)).c(5.0F).a(Tile.i).a("fan");
        camera = (new BlockCamera(170, 6)).setTextureNum(2).c(5.0F).a(Tile.i).a("camera");
        lightBulb = (new BlockLightBulb(171, 14)).setTextureNum(2).c(5.0F).a(Tile.i).a("lightBulb");
        fanOff = (new BlockFan(172, 200, false)).c(5.0F).a(Tile.i).a("fan");
        script = (new BlockScript(173, 15)).setTextureNum(2).c(5.0F).a(Tile.i).a("script");
        store = (new BlockStore(174, 49)).c(5.0F).a(uu.j).a("store");
        effect = (new BlockEffect(175, 244)).setTextureNum(3).c(5.0F).a(Tile.i).a("effect");
        url = (new BlockUrl(176, 245)).setTextureNum(3).c(5.0F).a(Tile.i).a("url");
        npcPath = (new BlockNpcPath(177, 247)).setTextureNum(3).c(5.0F).a(Tile.i).a("NPC Path Block");
        darkness = (new BlockDarkness(200, 10)).setTextureNum(2).c(5.0F).a(Tile.i).a("darkness");
        triggerPushable = (new BlockTriggerPushable(201, 213)).c(2.0F).b(10.0F).a(Tile.h).a("triggerPushable").setTextureNum(3);
        storage = (new BlockStorage(202, 11)).setTextureNum(2).c(5.0F).a(Tile.i).a("storage");
        healDamage = (new BlockHealDamage(203, 12)).setTextureNum(2).c(5.0F).a(Tile.i).a("healDamage");
        teleport = (new BlockTeleport(204, 13)).setTextureNum(2).c(5.0F).a(Tile.i).a("teleport");
        pillarStone = (new BlockPillar(205, 32)).setTextureNum(2).c(5.0F).a(Tile.h).a("pillarStone").setSubTypes(16);
        pillarMetal = (new BlockPillar(206, 80)).setTextureNum(2).c(5.0F).a(Tile.i).a("pillarMetal").setSubTypes(16);
        plant1 = (new BlockPlant(207, 112)).setTextureNum(2).c(5.0F).a(Tile.g).a("flower").setSubTypes(16);
        trees = (new BlockTree(208, 128)).setTextureNum(2).c(5.0F).a(Tile.g).a("sapling").setSubTypes(16);
        glassBlocks = (new BlockTransparent(209, 144)).setTextureNum(2).c(5.0F).a(Tile.j).a("glass").setSubTypes(16);
        cageBlocks = (new BlockTransparent(210, 160)).setTextureNum(2).c(5.0F).a(Tile.j).a("cage").setSubTypes(10);
        stoneBlocks1 = (new BlockSolid(211, 176)).setTextureNum(2).c(5.0F).a(Tile.h).a("stone").setSubTypes(16);
        stoneBlocks2 = (new BlockSolid(212, 192)).setTextureNum(2).c(5.0F).a(Tile.h).a("stone").setSubTypes(16);
        stoneBlocks3 = (new BlockSolid(213, 208)).setTextureNum(2).c(5.0F).a(Tile.h).a("stone").setSubTypes(16);
        woodBlocks = (new BlockSolid(214, 224)).setTextureNum(2).c(5.0F).a(Tile.e).a("wood").setSubTypes(16);
        halfSteps1 = (new BlockHalfStep(215, 240)).setTextureNum(2).c(5.0F).a(Tile.h).a("halfStep").setSubTypes(16);
        halfSteps2 = (new BlockHalfStep(216, 0)).setTextureNum(3).c(5.0F).a(Tile.i).a("halfStep").setSubTypes(16);
        halfSteps3 = (new BlockHalfStep(217, 16)).setTextureNum(3).c(5.0F).a(Tile.e).a("halfStepWood").setSubTypes(16);
        tableBlocks = (new BlockTable(218, 32)).setTextureNum(3).c(5.0F).a(Tile.e).a("table").setSubTypes(16);
        chairBlocks1 = (new BlockChair(219, 64)).setTextureNum(3).c(5.0F).a(Tile.e).a("chair").setSubTypes(16);
        chairBlocks2 = (new BlockChair(220, 68)).setTextureNum(3).c(5.0F).a(Tile.e).a("chair").setSubTypes(16);
        chairBlocks3 = (new BlockChair(221, 64)).setTextureNum(3).c(5.0F).a(Tile.e).a("chair").setSubTypes(16);
        chairBlocks4 = (new BlockChair(222, 64)).setTextureNum(3).c(5.0F).a(Tile.e).a("chair").setSubTypes(16);
        ropes1 = (new BlockRope(223, 96)).setTextureNum(3).c(5.0F).a(Tile.i).a("rope").setSubTypes(15);
        ropes2 = (new BlockRope(224, 101)).setTextureNum(3).c(5.0F).a(Tile.i).a("rope").setSubTypes(15);
        chains = (new BlockChain(225, 106)).setTextureNum(3).c(5.0F).a(Tile.i).a("chain").setSubTypes(9);
        ladders1 = (new BlockLadderSubtypes(226, 112)).setTextureNum(3).c(5.0F).a(Tile.e).a("ladder").setSubTypes(16);
        ladders2 = (new BlockLadderSubtypes(227, 116)).setTextureNum(3).c(5.0F).a(Tile.e).a("ladder").setSubTypes(16);
        ladders3 = (new BlockLadderSubtypes(228, 120)).setTextureNum(3).c(5.0F).a(Tile.e).a("ladder").setSubTypes(16);
        ladders4 = (new BlockLadderSubtypes(229, 124)).setTextureNum(3).c(5.0F).a(Tile.e).a("ladder").setSubTypes(16);
        lights1 = (new BlockPlant(230, 128)).setTextureNum(3).c(5.0F).a(0.9375F).a(Tile.i).a("torch").setSubTypes(14);
        plant2 = (new BlockTree(231, 144)).setTextureNum(3).c(5.0F).a(Tile.g).a("flower").setSubTypes(16);
        plant3 = (new BlockTree(232, 160)).setTextureNum(3).c(5.0F).a(Tile.g).a("flower").setSubTypes(16);
        overlay1 = (new BlockOverlay(233, 176)).setTextureNum(3).c(5.0F).a(Tile.g).a("overlay").setSubTypes(7);
        stairs1 = (new BlockStairMulti(234, Tile.y, 192)).setTextureNum(3).a("stairs").setSubTypes(4);
        stairs2 = (new BlockStairMulti(235, Tile.y, 196)).setTextureNum(3).a("stairs").setSubTypes(4);
        stairs3 = (new BlockStairMulti(236, Tile.x, 200)).setTextureNum(3).a("stairs").setSubTypes(4);
        stairs4 = (new BlockStairMulti(237, Tile.x, 204)).setTextureNum(3).a("stairs").setSubTypes(4);
        slopes1 = (new BlockSlope(238, Tile.y, 192)).setTextureNum(3).a("slopes").setSubTypes(4);
        slopes2 = (new BlockSlope(239, Tile.y, 196)).setTextureNum(3).a("slopes").setSubTypes(4);
        slopes3 = (new BlockSlope(240, Tile.x, 200)).setTextureNum(3).a("slopes").setSubTypes(4);
        slopes4 = (new BlockSlope(241, Tile.x, 204)).setTextureNum(3).a("slopes").setSubTypes(4);
        ItemType.c[pillarStone.bn] = (new ItemSubtypes(pillarStone.bn - 256)).a("pillarStone");
        ItemType.c[pillarMetal.bn] = (new ItemSubtypes(pillarMetal.bn - 256)).a("pillarMetal");
        ItemType.c[plant1.bn] = (new ItemSubtypes(plant1.bn - 256)).a("flower");
        ItemType.c[trees.bn] = (new ItemSubtypes(trees.bn - 256)).a("sapling");
        ItemType.c[glassBlocks.bn] = (new ItemSubtypes(glassBlocks.bn - 256)).a("glass");
        ItemType.c[cageBlocks.bn] = (new ItemSubtypes(cageBlocks.bn - 256)).a("cage");
        ItemType.c[stoneBlocks1.bn] = (new ItemSubtypes(stoneBlocks1.bn - 256)).a("stone");
        ItemType.c[stoneBlocks2.bn] = (new ItemSubtypes(stoneBlocks2.bn - 256)).a("stone");
        ItemType.c[stoneBlocks3.bn] = (new ItemSubtypes(stoneBlocks3.bn - 256)).a("stone");
        ItemType.c[woodBlocks.bn] = (new ItemSubtypes(woodBlocks.bn - 256)).a("wood");
        ItemType.c[halfSteps1.bn] = (new ItemSubtypes(halfSteps1.bn - 256)).a("halfStep");
        ItemType.c[halfSteps2.bn] = (new ItemSubtypes(halfSteps2.bn - 256)).a("halfStep");
        ItemType.c[halfSteps3.bn] = (new ItemSubtypes(halfSteps3.bn - 256)).a("halfStep");
        ItemType.c[tableBlocks.bn] = (new ItemSubtypes(tableBlocks.bn - 256)).a("table");
        ItemType.c[chairBlocks1.bn] = (new ItemSubtypes(chairBlocks1.bn - 256)).a("chair");
        ItemType.c[chairBlocks2.bn] = (new ItemSubtypes(chairBlocks2.bn - 256)).a("chair");
        ItemType.c[ropes1.bn] = (new ItemSubtypes(ropes1.bn - 256)).a("rope");
        ItemType.c[ropes2.bn] = (new ItemSubtypes(ropes2.bn - 256)).a("rope");
        ItemType.c[chains.bn] = (new ItemSubtypes(chains.bn - 256)).a("chain");
        ItemType.c[ladders1.bn] = (new ItemSubtypes(ladders1.bn - 256)).a("ladder");
        ItemType.c[ladders2.bn] = (new ItemSubtypes(ladders2.bn - 256)).a("ladder");
        ItemType.c[ladders3.bn] = (new ItemSubtypes(ladders3.bn - 256)).a("ladder");
        ItemType.c[ladders4.bn] = (new ItemSubtypes(ladders4.bn - 256)).a("ladder");
        ItemType.c[lights1.bn] = (new ItemSubtypes(lights1.bn - 256)).a("torch");
        ItemType.c[plant2.bn] = (new ItemSubtypes(plant2.bn - 256)).a("flower");
        ItemType.c[plant3.bn] = (new ItemSubtypes(plant3.bn - 256)).a("flower");
        ItemType.c[overlay1.bn] = (new ItemSubtypes(overlay1.bn - 256)).a("overlay");
        ItemType.c[stairs1.bn] = (new ItemSubtypes(stairs1.bn - 256)).a("stairs");
        ItemType.c[stairs2.bn] = (new ItemSubtypes(stairs2.bn - 256)).a("stairs");
        ItemType.c[stairs3.bn] = (new ItemSubtypes(stairs3.bn - 256)).a("stairs");
        ItemType.c[stairs4.bn] = (new ItemSubtypes(stairs4.bn - 256)).a("stairs");
        ItemType.c[slopes1.bn] = (new ItemSubtypes(slopes1.bn - 256)).a("slopes");
        ItemType.c[slopes2.bn] = (new ItemSubtypes(slopes2.bn - 256)).a("slopes");
        ItemType.c[slopes3.bn] = (new ItemSubtypes(slopes3.bn - 256)).a("slopes");
        ItemType.c[slopes4.bn] = (new ItemSubtypes(slopes4.bn - 256)).a("slopes");
        for (int i = 0; i < 256; i++) {
            if (Tile.m[i] != null && ItemType.c[i] == null) {
                ItemType.c[i] = new PlaceableTileItem(i - 256);
                Tile.m[i].k();
            }
        }
    }

    static void convertACVersion(byte[] data) {
        if (data != null)
            for (int i = 0; i < data.length; i++) {
                int blockID = lm.translate256(data[i]);
                if (blockID >= 100 && blockID <= 122) {
                    data[i] = (byte) lm.translate128(blockID + 50);
                } else if (blockID >= 152 && blockID <= 155) {
                    data[i] = (byte) lm.translate128(blockID + 21);
                }
            }
    }
}