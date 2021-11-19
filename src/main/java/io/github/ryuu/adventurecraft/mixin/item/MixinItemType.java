package io.github.ryuu.adventurecraft.mixin.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.*;
import net.minecraft.item.armour.ArmourItem;
import net.minecraft.item.food.CookieItem;
import net.minecraft.item.food.FoodItem;
import net.minecraft.item.food.MushroomStewItem;
import net.minecraft.item.tool.*;
import net.minecraft.level.Level;
import net.minecraft.stat.Stats;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import org.mozilla.javascript.Scriptable;

import java.util.Random;

public class MixinItemType {
    protected static Random rand = new Random();
    public static ItemType[] byId = new ItemType[32000];
    public static ItemType shovelIron;
    public static ItemType pickaxeIron;
    public static ItemType hatchetIron;
    public static ItemType flintAndSteel;
    public static ItemType apple;
    public static ItemType bow;
    public static ItemType arrow;
    public static ItemType coal;
    public static ItemType diamond;
    public static ItemType ingotIron;
    public static ItemType ingotGold;
    public static ItemType swordIron;
    public static ItemType swordWood;
    public static ItemType shovelWood;
    public static ItemType pickaxeWood;
    public static ItemType hatchetWood;
    public static ItemType swordStone;
    public static ItemType shovelStone;
    public static ItemType pickaxeStone;
    public static ItemType hatchetStone;
    public static ItemType swordDiamond;
    public static ItemType shovelDiamond;
    public static ItemType pickaxeDiamond;
    public static ItemType hatchetDiamond;
    public static ItemType stick;
    public static ItemType bowl;
    public static ItemType mushroomStew;
    public static ItemType swordGold;
    public static ItemType shovelGold;
    public static ItemType pickaxeGold;
    public static ItemType hatchetGold;
    public static ItemType string;
    public static ItemType feather;
    public static ItemType sulphur;
    public static ItemType hoeWood;
    public static ItemType hoeStone;
    public static ItemType hoeIron;
    public static ItemType hoeDiamond;
    public static ItemType hoeGold;
    public static ItemType seeds;
    public static ItemType wheat;
    public static ItemType bread;
    public static ItemType helmetCloth;
    public static ItemType chestplateCloth;
    public static ItemType leggingsCloth;
    public static ItemType bootsCloth;
    public static ItemType helmetChain;
    public static ItemType chestplateChain;
    public static ItemType leggingsChain;
    public static ItemType bootsChain;
    public static ItemType helmetIron;
    public static ItemType chestplateIron;
    public static ItemType leggingsIron;
    public static ItemType bootsIron;
    public static ItemType helmetDiamond;
    public static ItemType chestplateDiamond;
    public static ItemType leggingsDiamond;
    public static ItemType bootsDiamond;
    public static ItemType helmetGold;
    public static ItemType chestplateGold;
    public static ItemType leggingsGold;
    public static ItemType bootsGold;
    public static ItemType flint;
    public static ItemType porkchopRaw;
    public static ItemType porkchopCooked;
    public static ItemType painting;
    public static ItemType appleGold;
    public static ItemType sign;
    public static ItemType doorWood;
    public static ItemType bucket;
    public static ItemType bucketWater;
    public static ItemType bucketLava;
    public static ItemType minecart;
    public static ItemType saddle;
    public static ItemType doorIron;
    public static ItemType redstone;
    public static ItemType snowball;
    public static ItemType boat;
    public static ItemType leather;
    public static ItemType milk;
    public static ItemType brick;
    public static ItemType clay;
    public static ItemType reeds;
    public static ItemType paper;
    public static ItemType book;
    public static ItemType slimeball;
    public static ItemType minecartChest;
    public static ItemType minecartFurnace;
    public static ItemType egg;
    public static ItemType compass;
    public static ItemType fishingRod;
    public static ItemType clock;
    public static ItemType yellowDust;
    public static ItemType fishRaw;
    public static ItemType fishCooked;
    public static ItemType dyePowder;
    public static ItemType bone;
    public static ItemType sugar;
    public static ItemType cake;
    public static ItemType bed;
    public static ItemType diode;
    public static ItemType cookie;
    public static MapItem map;
    public static Shears shears;
    public static ItemType record_13;
    public static ItemType record_cat;
    public final int id;
    protected int maxStackSize = 64;
    protected int field_402 = 0;
    protected int texturePosition;
    protected boolean field_464 = false;
    protected boolean field_465 = false;
    private ItemType containerItemType = null;
    private String translationKey;
    public boolean decrementDamage;
    public int itemUseDelay = 5;

    protected ItemType(int id) {
        this.id = 256 + id;
        if (byId[256 + id] != null) {
            System.out.println("CONFLICT @ " + id);
        }
        ItemType.byId[256 + id] = this;
    }

    public ItemType setTexturePosition(int texturePosition) {
        this.texturePosition = texturePosition;
        return this;
    }

    public ItemType setMaxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
        return this;
    }

    public ItemType setTexturePosition(int x, int y) {
        this.texturePosition = x + y * 16;
        return this;
    }

    public int getTexturePosition(int damage) {
        return this.texturePosition;
    }

    public final int getTexturePosition(ItemInstance item) {
        int damage = 0;
        if (item != null) {
            damage = item.getDamage();
        }
        return this.getTexturePosition(damage);
    }

    public boolean useOnTile(ItemInstance item, Player player, Level level, int x, int y, int z, int facing) {
        return false;
    }

    public boolean onItemUseLeftClick(ItemInstance itemstack, Player entityplayer, Level world, int i, int j, int k, int l) {
        return false;
    }

    public float method_438(ItemInstance item, Tile tile) {
        return 1.0f;
    }

    public void onItemLeftClick(ItemInstance itemstack, Level world, Player entityplayer) {
    }

    public ItemInstance use(ItemInstance item, Level level, Player player) {
        return item;
    }

    public int method_459() {
        return this.maxStackSize;
    }

    public int method_470(int i) {
        return 0;
    }

    public boolean method_462() {
        return this.field_465;
    }

    protected ItemType method_457(boolean flag) {
        this.field_465 = flag;
        return this;
    }

    public int method_464() {
        return this.field_402;
    }

    protected ItemType setDurability(int i) {
        this.field_402 = i;
        return this;
    }

    public boolean method_465() {
        return this.field_402 > 0 && !this.field_465;
    }

    public boolean postHit(ItemInstance itemstack, LivingEntity entityliving, LivingEntity entityliving1) {
        return false;
    }

    public boolean postMine(ItemInstance itemstack, int i, int j, int k, int l, LivingEntity entityliving) {
        return false;
    }

    public int method_447(Entity entity) {
        return 1;
    }

    public boolean isEffectiveOn(Tile tile) {
        return false;
    }

    public void interactWithEntity(ItemInstance itemstack, LivingEntity entityliving) {
    }

    public ItemType method_466() {
        this.field_464 = true;
        return this;
    }

    public boolean shouldRenderLikeStick() {
        return this.field_464;
    }

    public boolean shouldRotate180() {
        return false;
    }

    public ItemType setName(String s) {
        this.translationKey = "item." + s;
        return this;
    }

    public String getTranslationKey() {
        return this.translationKey;
    }

    public String getTranslationKey(ItemInstance item) {
        return this.translationKey;
    }

    public ItemType setContainerItem(ItemType itemType) {
        if (this.maxStackSize > 1) {
            throw new IllegalArgumentException("Max stack size must be 1 for items with crafting results");
        }
        this.containerItemType = itemType;
        return this;
    }

    public ItemType getContainerItemType() {
        return this.containerItemType;
    }

    public boolean hasContainerItemType() {
        return this.containerItemType != null;
    }

    public String getTranslatedName() {
        return I18n.translate(this.getTranslationKey() + ".name");
    }

    public int getNameColour(int i) {
        return 0xFFFFFF;
    }

    public void inventoryTick(ItemInstance itemstack, Level world, Entity entity, int i, boolean flag) {
    }

    public void method_461(ItemInstance itemstack, Level world, Player entityplayer) {
    }

    public boolean mainActionLeftClick() {
        return false;
    }

    public boolean isLighting(ItemInstance itemstack) {
        return false;
    }

    public boolean isMuzzleFlash(ItemInstance itemstack) {
        return false;
    }

    public void onAddToSlot(Player entityPlayer, int slot, int damage) {
        Scriptable scope = Minecraft.minecraftInstance.level.scope;
        scope.put("slotID", scope, new Integer(slot));
        if (this.method_462()) {
            Minecraft.minecraftInstance.level.scriptHandler.runScript(String.format("item_onAddToSlot_%d_%d.js", new Object[]{this.id, damage}), scope, false);
        } else {
            Minecraft.minecraftInstance.level.scriptHandler.runScript(String.format("item_onAddToSlot_%d.js", new Object[]{this.id}), scope, false);
        }
    }

    public void onRemovedFromSlot(Player entityPlayer, int slot, int damage) {
        Scriptable scope = Minecraft.minecraftInstance.level.scope;
        scope.put("slotID", scope, new Integer(slot));
        if (this.method_462()) {
            Minecraft.minecraftInstance.level.scriptHandler.runScript(String.format("item_onRemovedFromSlot_%d_%d.js", new Object[]{this.id, damage}), scope, false);
        } else {
            Minecraft.minecraftInstance.level.scriptHandler.runScript(String.format("item_onRemovedFromSlot_%d.js", new Object[]{this.id}), scope, false);
        }
    }

    static {
        flintAndSteel = new FlintAndSteelItem(3).setTexturePosition(5, 0).setName("flintAndSteel");
        apple = new FoodItem(4, 4, false).setTexturePosition(10, 0).setName("apple");
        bow = new BowItem(5).setTexturePosition(5, 1).setName("bow");
        arrow = new ItemType(6).setTexturePosition(5, 2).setName("arrow");
        coal = new CoalItem(7).setTexturePosition(7, 0).setName("coal");
        diamond = new ItemType(8).setTexturePosition(7, 3).setName("emerald");
        ingotIron = new ItemType(9).setTexturePosition(7, 1).setName("ingotIron");
        ingotGold = new ItemType(10).setTexturePosition(7, 2).setName("ingotGold");
        stick = new ItemType(24).setTexturePosition(5, 3).method_466().setName("stick");
        bowl = new ItemType(25).setTexturePosition(7, 4).setName("bowl");
        mushroomStew = new MushroomStewItem(26, 10).setTexturePosition(8, 4).setName("mushroomStew");
        string = new ItemType(31).setTexturePosition(8, 0).setName("string");
        feather = new ItemType(32).setTexturePosition(8, 1).setName("feather");
        sulphur = new ItemType(33).setTexturePosition(8, 2).setName("sulphur");
        wheat = new ItemType(40).setTexturePosition(9, 1).setName("wheat");
        bread = new FoodItem(41, 5, false).setTexturePosition(9, 2).setName("bread");
        helmetCloth = new ArmourItem(42, 0, 0, 0).setTexturePosition(0, 0).setName("helmetCloth");
        chestplateCloth = new ArmourItem(43, 0, 0, 1).setTexturePosition(0, 1).setName("chestplateCloth");
        leggingsCloth = new ArmourItem(44, 0, 0, 2).setTexturePosition(0, 2).setName("leggingsCloth");
        bootsCloth = new ArmourItem(45, 0, 0, 3).setTexturePosition(0, 3).setName("bootsCloth");
        helmetChain = new ArmourItem(46, 1, 1, 0).setTexturePosition(1, 0).setName("helmetChain");
        chestplateChain = new ArmourItem(47, 1, 1, 1).setTexturePosition(1, 1).setName("chestplateChain");
        leggingsChain = new ArmourItem(48, 1, 1, 2).setTexturePosition(1, 2).setName("leggingsChain");
        bootsChain = new ArmourItem(49, 1, 1, 3).setTexturePosition(1, 3).setName("bootsChain");
        helmetIron = new ArmourItem(50, 2, 2, 0).setTexturePosition(2, 0).setName("helmetIron");
        chestplateIron = new ArmourItem(51, 2, 2, 1).setTexturePosition(2, 1).setName("chestplateIron");
        leggingsIron = new ArmourItem(52, 2, 2, 2).setTexturePosition(2, 2).setName("leggingsIron");
        bootsIron = new ArmourItem(53, 2, 2, 3).setTexturePosition(2, 3).setName("bootsIron");
        helmetDiamond = new ArmourItem(54, 3, 3, 0).setTexturePosition(3, 0).setName("helmetDiamond");
        chestplateDiamond = new ArmourItem(55, 3, 3, 1).setTexturePosition(3, 1).setName("chestplateDiamond");
        leggingsDiamond = new ArmourItem(56, 3, 3, 2).setTexturePosition(3, 2).setName("leggingsDiamond");
        bootsDiamond = new ArmourItem(57, 3, 3, 3).setTexturePosition(3, 3).setName("bootsDiamond");
        helmetGold = new ArmourItem(58, 1, 4, 0).setTexturePosition(4, 0).setName("helmetGold");
        chestplateGold = new ArmourItem(59, 1, 4, 1).setTexturePosition(4, 1).setName("chestplateGold");
        leggingsGold = new ArmourItem(60, 1, 4, 2).setTexturePosition(4, 2).setName("leggingsGold");
        bootsGold = new ArmourItem(61, 1, 4, 3).setTexturePosition(4, 3).setName("bootsGold");
        flint = new ItemType(62).setTexturePosition(6, 0).setName("flint");
        porkchopRaw = new FoodItem(63, 3, true).setTexturePosition(7, 5).setName("porkchopRaw");
        porkchopCooked = new FoodItem(64, 8, true).setTexturePosition(8, 5).setName("porkchopCooked");
        painting = new PaintingItem(65).setTexturePosition(10, 1).setName("painting");
        appleGold = new FoodItem(66, 42, false).setTexturePosition(11, 0).setName("appleGold");
        sign = new SignItem(67).setTexturePosition(10, 2).setName("sign");
        minecart = new MinecartItem(72, 0).setTexturePosition(7, 8).setName("minecart");
        saddle = new SaddleItem(73).setTexturePosition(8, 6).setName("saddle");
        redstone = new RedstoneItem(75).setTexturePosition(8, 3).setName("redstone");
        snowball = new SnowballItem(76).setTexturePosition(14, 0).setName("snowball");
        boat = new BoatItem(77).setTexturePosition(8, 8).setName("boat");
        leather = new ItemType(78).setTexturePosition(7, 6).setName("leather");
        brick = new ItemType(80).setTexturePosition(6, 1).setName("brick");
        clay = new ItemType(81).setTexturePosition(9, 3).setName("clay");
        paper = new ItemType(83).setTexturePosition(10, 3).setName("paper");
        book = new ItemType(84).setTexturePosition(11, 3).setName("book");
        slimeball = new ItemType(85).setTexturePosition(14, 1).setName("slimeball");
        minecartChest = new MinecartItem(86, 1).setTexturePosition(7, 9).setName("minecartChest");
        minecartFurnace = new MinecartItem(87, 2).setTexturePosition(7, 10).setName("minecartFurnace");
        egg = new EggItem(88).setTexturePosition(12, 0).setName("egg");
        compass = new ItemType(89).setTexturePosition(6, 3).setName("compass");
        fishingRod = new FishingRodItem(90).setTexturePosition(5, 4).setName("fishingRod");
        clock = new ItemType(91).setTexturePosition(6, 4).setName("clock");
        yellowDust = new ItemType(92).setTexturePosition(9, 4).setName("yellowDust");
        fishRaw = new FoodItem(93, 2, false).setTexturePosition(9, 5).setName("fishRaw");
        fishCooked = new FoodItem(94, 5, false).setTexturePosition(10, 5).setName("fishCooked");
        dyePowder = new DyeItem(95).setTexturePosition(14, 4).setName("dyePowder");
        bone = new ItemType(96).setTexturePosition(12, 1).setName("bone").method_466();
        sugar = new ItemType(97).setTexturePosition(13, 0).setName("sugar").method_466();
        bed = new BedItem(99).setMaxStackSize(1).setTexturePosition(13, 2).setName("bed");
        cookie = new CookieItem(101, 1, false, 8).setTexturePosition(12, 5).setName("cookie");
        map = (MapItem)new MapItem(102).setTexturePosition(12, 3).setName("map");
        shears = (Shears)new Shears(103).setTexturePosition(13, 5).setName("shears");
        record_13 = new RecordItem(2000, "13").setTexturePosition(0, 15).setName("record");
        record_cat = new RecordItem(2001, "cat").setTexturePosition(1, 15).setName("record");
        shovelIron = new ShovelItem(0, ToolMaterial.IRON).setTexturePosition(2, 5).setName("shovelIron");
        pickaxeIron = new PickaxeItem(1, ToolMaterial.IRON).setTexturePosition(2, 6).setName("pickaxeIron");
        hatchetIron = new HatchetItem(2, ToolMaterial.IRON).setTexturePosition(2, 7).setName("hatchetIron");
        swordIron = new SwordItem(11, ToolMaterial.IRON).setTexturePosition(2, 4).setName("swordIron");
        swordWood = new SwordItem(12, ToolMaterial.WOOD).setTexturePosition(0, 4).setName("swordWood");
        shovelWood = new ShovelItem(13, ToolMaterial.WOOD).setTexturePosition(0, 5).setName("shovelWood");
        pickaxeWood = new PickaxeItem(14, ToolMaterial.WOOD).setTexturePosition(0, 6).setName("pickaxeWood");
        hatchetWood = new HatchetItem(15, ToolMaterial.WOOD).setTexturePosition(0, 7).setName("hatchetWood");
        swordStone = new SwordItem(16, ToolMaterial.STONE).setTexturePosition(1, 4).setName("swordStone");
        shovelStone = new ShovelItem(17, ToolMaterial.STONE).setTexturePosition(1, 5).setName("shovelStone");
        pickaxeStone = new PickaxeItem(18, ToolMaterial.STONE).setTexturePosition(1, 6).setName("pickaxeStone");
        hatchetStone = new HatchetItem(19, ToolMaterial.STONE).setTexturePosition(1, 7).setName("hatchetStone");
        swordDiamond = new SwordItem(20, ToolMaterial.EMERALD).setTexturePosition(3, 4).setName("swordDiamond");
        shovelDiamond = new ShovelItem(21, ToolMaterial.EMERALD).setTexturePosition(3, 5).setName("shovelDiamond");
        pickaxeDiamond = new PickaxeItem(22, ToolMaterial.EMERALD).setTexturePosition(3, 6).setName("pickaxeDiamond");
        hatchetDiamond = new HatchetItem(23, ToolMaterial.EMERALD).setTexturePosition(3, 7).setName("hatchetDiamond");
        swordGold = new SwordItem(27, ToolMaterial.GOLD).setTexturePosition(4, 4).setName("swordGold");
        shovelGold = new ShovelItem(28, ToolMaterial.GOLD).setTexturePosition(4, 5).setName("shovelGold");
        pickaxeGold = new PickaxeItem(29, ToolMaterial.GOLD).setTexturePosition(4, 6).setName("pickaxeGold");
        hatchetGold = new HatchetItem(30, ToolMaterial.GOLD).setTexturePosition(4, 7).setName("hatchetGold");
        hoeWood = new HoeItem(34, ToolMaterial.WOOD).setTexturePosition(0, 8).setName("hoeWood");
        hoeStone = new HoeItem(35, ToolMaterial.STONE).setTexturePosition(1, 8).setName("hoeStone");
        hoeIron = new HoeItem(36, ToolMaterial.IRON).setTexturePosition(2, 8).setName("hoeIron");
        hoeDiamond = new HoeItem(37, ToolMaterial.EMERALD).setTexturePosition(3, 8).setName("hoeDiamond");
        hoeGold = new HoeItem(38, ToolMaterial.GOLD).setTexturePosition(4, 8).setName("hoeGold");
        seeds = new SeedsItem(39, Tile.CROPS.id).setTexturePosition(9, 0).setName("seeds");
        doorWood = new DoorItem(68, Material.WOOD).setTexturePosition(11, 2).setName("doorWood");
        bucket = new BucketItem(69, 0).setTexturePosition(10, 4).setName("bucket");
        bucketWater = new BucketItem(70, Tile.FLOWING_WATER.id).setTexturePosition(11, 4).setName("bucketWater").setContainerItem(bucket);
        bucketLava = new BucketItem(71, Tile.FLOWING_LAVA.id).setTexturePosition(12, 4).setName("bucketLava").setContainerItem(bucket);
        doorIron = new DoorItem(74, Material.METAL).setTexturePosition(12, 2).setName("doorIron");
        milk = new BucketItem(79, -1).setTexturePosition(13, 4).setName("milk").setContainerItem(bucket);
        reeds = new TileItem(82, Tile.REEDS).setTexturePosition(11, 1).setName("reeds");
        cake = new TileItem(98, Tile.CAKE).setMaxStackSize(1).setTexturePosition(13, 1).setName("cake");
        diode = new TileItem(100, Tile.REDSTONE_REPEATER).setTexturePosition(6, 5).setName("diode");
        Stats.onItemsRegistered();
    }
}