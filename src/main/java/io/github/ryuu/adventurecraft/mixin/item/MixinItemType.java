package io.github.ryuu.adventurecraft.mixin.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.*;
import net.minecraft.item.food.CookieItem;
import net.minecraft.item.food.FoodItem;
import net.minecraft.item.food.MushroomStewItem;
import net.minecraft.item.tool.*;
import net.minecraft.stat.Stats;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import org.mozilla.javascript.Scriptable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(ItemType.class)
public class MixinItemType {

    public static MixinItemType[] byId = new MixinItemType[32000];
    public static MixinItemType shovelIron;
    public static MixinItemType pickaxeIron;
    public static MixinItemType hatchetIron;
    public static MixinItemType flintAndSteel;
    public static MixinItemType apple;
    public static MixinItemType bow;
    public static MixinItemType arrow;
    public static MixinItemType coal;
    public static MixinItemType diamond;
    public static MixinItemType ingotIron;
    public static MixinItemType ingotGold;
    public static MixinItemType swordIron;
    public static MixinItemType swordWood;
    public static MixinItemType shovelWood;
    public static MixinItemType pickaxeWood;
    public static MixinItemType hatchetWood;
    public static MixinItemType swordStone;
    public static MixinItemType shovelStone;
    public static MixinItemType pickaxeStone;
    public static MixinItemType hatchetStone;
    public static MixinItemType swordDiamond;
    public static MixinItemType shovelDiamond;
    public static MixinItemType pickaxeDiamond;
    public static MixinItemType hatchetDiamond;
    public static MixinItemType stick;
    public static MixinItemType bowl;
    public static MixinItemType mushroomStew;
    public static MixinItemType swordGold;
    public static MixinItemType shovelGold;
    public static MixinItemType pickaxeGold;
    public static MixinItemType hatchetGold;
    public static MixinItemType string;
    public static MixinItemType feather;
    public static MixinItemType sulphur;
    public static MixinItemType hoeWood;
    public static MixinItemType hoeStone;
    public static MixinItemType hoeIron;
    public static MixinItemType hoeDiamond;
    public static MixinItemType hoeGold;
    public static MixinItemType seeds;
    public static MixinItemType wheat;
    public static MixinItemType bread;
    public static MixinItemType helmetCloth;
    public static MixinItemType chestplateCloth;
    public static MixinItemType leggingsCloth;
    public static MixinItemType bootsCloth;
    public static MixinItemType helmetChain;
    public static MixinItemType chestplateChain;
    public static MixinItemType leggingsChain;
    public static MixinItemType bootsChain;
    public static MixinItemType helmetIron;
    public static MixinItemType chestplateIron;
    public static MixinItemType leggingsIron;
    public static MixinItemType bootsIron;
    public static MixinItemType helmetDiamond;
    public static MixinItemType chestplateDiamond;
    public static MixinItemType leggingsDiamond;
    public static MixinItemType bootsDiamond;
    public static MixinItemType helmetGold;
    public static MixinItemType chestplateGold;
    public static MixinItemType leggingsGold;
    public static MixinItemType bootsGold;
    public static MixinItemType flint;
    public static MixinItemType porkchopRaw;
    public static MixinItemType porkchopCooked;
    public static MixinItemType painting;
    public static MixinItemType appleGold;
    public static MixinItemType sign;
    public static MixinItemType doorWood;
    public static MixinItemType bucket;
    public static MixinItemType bucketWater;
    public static MixinItemType bucketLava;
    public static MixinItemType minecart;
    public static MixinItemType saddle;
    public static MixinItemType doorIron;
    public static MixinItemType redstone;
    public static MixinItemType snowball;
    public static MixinItemType boat;
    public static MixinItemType leather;
    public static MixinItemType milk;
    public static MixinItemType brick;
    public static MixinItemType clay;
    public static MixinItemType reeds;
    public static MixinItemType paper;
    public static MixinItemType book;
    public static MixinItemType slimeball;
    public static MixinItemType minecartChest;
    public static MixinItemType minecartFurnace;
    public static MixinItemType egg;
    public static MixinItemType compass;
    public static MixinItemType fishingRod;
    public static MixinItemType clock;
    public static MixinItemType yellowDust;
    public static MixinItemType fishRaw;
    public static MixinItemType fishCooked;
    public static MixinItemType dyePowder;
    public static MixinItemType bone;
    public static MixinItemType sugar;
    public static MixinItemType cake;
    public static MixinItemType bed;
    public static MixinItemType diode;
    public static MixinItemType cookie;
    public static MapItem map;
    public static Shears shears;
    public static MixinItemType record_13;
    public static MixinItemType record_cat;
    @Shadow()
    protected static Random rand = new Random();

    static {
        flintAndSteel = new FlintAndSteelItem(3).setTexturePosition(5, 0).setName("flintAndSteel");
        apple = new FoodItem(4, 4, false).setTexturePosition(10, 0).setName("apple");
        bow = new MixinBowItem(5).setTexturePosition(5, 1).setName("bow");
        arrow = new MixinItemType(6).setTexturePosition(5, 2).setName("arrow");
        coal = new CoalItem(7).setTexturePosition(7, 0).setName("coal");
        diamond = new MixinItemType(8).setTexturePosition(7, 3).setName("emerald");
        ingotIron = new MixinItemType(9).setTexturePosition(7, 1).setName("ingotIron");
        ingotGold = new MixinItemType(10).setTexturePosition(7, 2).setName("ingotGold");
        stick = new MixinItemType(24).setTexturePosition(5, 3).method_466().setName("stick");
        bowl = new MixinItemType(25).setTexturePosition(7, 4).setName("bowl");
        mushroomStew = new MushroomStewItem(26, 10).setTexturePosition(8, 4).setName("mushroomStew");
        string = new MixinItemType(31).setTexturePosition(8, 0).setName("string");
        feather = new MixinItemType(32).setTexturePosition(8, 1).setName("feather");
        sulphur = new MixinItemType(33).setTexturePosition(8, 2).setName("sulphur");
        wheat = new MixinItemType(40).setTexturePosition(9, 1).setName("wheat");
        bread = new FoodItem(41, 5, false).setTexturePosition(9, 2).setName("bread");
        helmetCloth = new MixinArmourItem(42, 0, 0, 0).setTexturePosition(0, 0).setName("helmetCloth");
        chestplateCloth = new MixinArmourItem(43, 0, 0, 1).setTexturePosition(0, 1).setName("chestplateCloth");
        leggingsCloth = new MixinArmourItem(44, 0, 0, 2).setTexturePosition(0, 2).setName("leggingsCloth");
        bootsCloth = new MixinArmourItem(45, 0, 0, 3).setTexturePosition(0, 3).setName("bootsCloth");
        helmetChain = new MixinArmourItem(46, 1, 1, 0).setTexturePosition(1, 0).setName("helmetChain");
        chestplateChain = new MixinArmourItem(47, 1, 1, 1).setTexturePosition(1, 1).setName("chestplateChain");
        leggingsChain = new MixinArmourItem(48, 1, 1, 2).setTexturePosition(1, 2).setName("leggingsChain");
        bootsChain = new MixinArmourItem(49, 1, 1, 3).setTexturePosition(1, 3).setName("bootsChain");
        helmetIron = new MixinArmourItem(50, 2, 2, 0).setTexturePosition(2, 0).setName("helmetIron");
        chestplateIron = new MixinArmourItem(51, 2, 2, 1).setTexturePosition(2, 1).setName("chestplateIron");
        leggingsIron = new MixinArmourItem(52, 2, 2, 2).setTexturePosition(2, 2).setName("leggingsIron");
        bootsIron = new MixinArmourItem(53, 2, 2, 3).setTexturePosition(2, 3).setName("bootsIron");
        helmetDiamond = new MixinArmourItem(54, 3, 3, 0).setTexturePosition(3, 0).setName("helmetDiamond");
        chestplateDiamond = new MixinArmourItem(55, 3, 3, 1).setTexturePosition(3, 1).setName("chestplateDiamond");
        leggingsDiamond = new MixinArmourItem(56, 3, 3, 2).setTexturePosition(3, 2).setName("leggingsDiamond");
        bootsDiamond = new MixinArmourItem(57, 3, 3, 3).setTexturePosition(3, 3).setName("bootsDiamond");
        helmetGold = new MixinArmourItem(58, 1, 4, 0).setTexturePosition(4, 0).setName("helmetGold");
        chestplateGold = new MixinArmourItem(59, 1, 4, 1).setTexturePosition(4, 1).setName("chestplateGold");
        leggingsGold = new MixinArmourItem(60, 1, 4, 2).setTexturePosition(4, 2).setName("leggingsGold");
        bootsGold = new MixinArmourItem(61, 1, 4, 3).setTexturePosition(4, 3).setName("bootsGold");
        flint = new MixinItemType(62).setTexturePosition(6, 0).setName("flint");
        porkchopRaw = new FoodItem(63, 3, true).setTexturePosition(7, 5).setName("porkchopRaw");
        porkchopCooked = new FoodItem(64, 8, true).setTexturePosition(8, 5).setName("porkchopCooked");
        painting = new PaintingItem(65).setTexturePosition(10, 1).setName("painting");
        appleGold = new FoodItem(66, 42, false).setTexturePosition(11, 0).setName("appleGold");
        sign = new SignItem(67).setTexturePosition(10, 2).setName("sign");
        minecart = new MinecartItem(72, 0).setTexturePosition(7, 8).setName("minecart");
        saddle = new SaddleItem(73).setTexturePosition(8, 6).setName("saddle");
        redstone = new MixinRedstoneItem(75).setTexturePosition(8, 3).setName("redstone");
        snowball = new SnowballItem(76).setTexturePosition(14, 0).setName("snowball");
        boat = new BoatItem(77).setTexturePosition(8, 8).setName("boat");
        leather = new MixinItemType(78).setTexturePosition(7, 6).setName("leather");
        brick = new MixinItemType(80).setTexturePosition(6, 1).setName("brick");
        clay = new MixinItemType(81).setTexturePosition(9, 3).setName("clay");
        paper = new MixinItemType(83).setTexturePosition(10, 3).setName("paper");
        book = new MixinItemType(84).setTexturePosition(11, 3).setName("book");
        slimeball = new MixinItemType(85).setTexturePosition(14, 1).setName("slimeball");
        minecartChest = new MinecartItem(86, 1).setTexturePosition(7, 9).setName("minecartChest");
        minecartFurnace = new MinecartItem(87, 2).setTexturePosition(7, 10).setName("minecartFurnace");
        egg = new EggItem(88).setTexturePosition(12, 0).setName("egg");
        compass = new MixinItemType(89).setTexturePosition(6, 3).setName("compass");
        fishingRod = new FishingRodItem(90).setTexturePosition(5, 4).setName("fishingRod");
        clock = new MixinItemType(91).setTexturePosition(6, 4).setName("clock");
        yellowDust = new MixinItemType(92).setTexturePosition(9, 4).setName("yellowDust");
        fishRaw = new FoodItem(93, 2, false).setTexturePosition(9, 5).setName("fishRaw");
        fishCooked = new FoodItem(94, 5, false).setTexturePosition(10, 5).setName("fishCooked");
        dyePowder = new DyeItem(95).setTexturePosition(14, 4).setName("dyePowder");
        bone = new MixinItemType(96).setTexturePosition(12, 1).setName("bone").method_466();
        sugar = new MixinItemType(97).setTexturePosition(13, 0).setName("sugar").method_466();
        bed = new BedItem(99).setMaxStackSize(1).setTexturePosition(13, 2).setName("bed");
        cookie = new CookieItem(101, 1, false, 8).setTexturePosition(12, 5).setName("cookie");
        map = (MapItem) new MapItem(102).setTexturePosition(12, 3).setName("map");
        shears = (Shears) new Shears(103).setTexturePosition(13, 5).setName("shears");
        record_13 = new RecordItem(2000, "13").setTexturePosition(0, 15).setName("record");
        record_cat = new RecordItem(2001, "cat").setTexturePosition(1, 15).setName("record");
        shovelIron = new ShovelItem(0, ToolMaterial.IRON).setTexturePosition(2, 5).setName("shovelIron");
        pickaxeIron = new PickaxeItem(1, ToolMaterial.IRON).setTexturePosition(2, 6).setName("pickaxeIron");
        hatchetIron = new HatchetItem(2, ToolMaterial.IRON).setTexturePosition(2, 7).setName("hatchetIron");
        swordIron = new MixinSwordItem(11, ToolMaterial.IRON).setTexturePosition(2, 4).setName("swordIron");
        swordWood = new MixinSwordItem(12, ToolMaterial.WOOD).setTexturePosition(0, 4).setName("swordWood");
        shovelWood = new ShovelItem(13, ToolMaterial.WOOD).setTexturePosition(0, 5).setName("shovelWood");
        pickaxeWood = new PickaxeItem(14, ToolMaterial.WOOD).setTexturePosition(0, 6).setName("pickaxeWood");
        hatchetWood = new HatchetItem(15, ToolMaterial.WOOD).setTexturePosition(0, 7).setName("hatchetWood");
        swordStone = new MixinSwordItem(16, ToolMaterial.STONE).setTexturePosition(1, 4).setName("swordStone");
        shovelStone = new ShovelItem(17, ToolMaterial.STONE).setTexturePosition(1, 5).setName("shovelStone");
        pickaxeStone = new PickaxeItem(18, ToolMaterial.STONE).setTexturePosition(1, 6).setName("pickaxeStone");
        hatchetStone = new HatchetItem(19, ToolMaterial.STONE).setTexturePosition(1, 7).setName("hatchetStone");
        swordDiamond = new MixinSwordItem(20, ToolMaterial.EMERALD).setTexturePosition(3, 4).setName("swordDiamond");
        shovelDiamond = new ShovelItem(21, ToolMaterial.EMERALD).setTexturePosition(3, 5).setName("shovelDiamond");
        pickaxeDiamond = new PickaxeItem(22, ToolMaterial.EMERALD).setTexturePosition(3, 6).setName("pickaxeDiamond");
        hatchetDiamond = new HatchetItem(23, ToolMaterial.EMERALD).setTexturePosition(3, 7).setName("hatchetDiamond");
        swordGold = new MixinSwordItem(27, ToolMaterial.GOLD).setTexturePosition(4, 4).setName("swordGold");
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

    public final int id;
    public boolean decrementDamage;
    public int itemUseDelay = 5;
    protected int maxStackSize = 64;
    protected int field_402 = 0;
    protected int texturePosition;
    protected boolean field_464 = false;
    protected boolean field_465 = false;
    private MixinItemType containerItemType = null;
    private String translationKey;

    protected MixinItemType(int id) {
        this.id = 256 + id;
        if (byId[256 + id] != null) {
            System.out.println("CONFLICT @ " + id);
        }
        ItemType.byId[256 + id] = this;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinItemType setTexturePosition(int texturePosition) {
        this.texturePosition = texturePosition;
        return this;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinItemType setMaxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
        return this;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinItemType setTexturePosition(int x, int y) {
        this.texturePosition = x + y * 16;
        return this;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getTexturePosition(int damage) {
        return this.texturePosition;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public final int getTexturePosition(MixinItemInstance item) {
        int damage = 0;
        if (item != null) {
            damage = item.getDamage();
        }
        return this.getTexturePosition(damage);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean useOnTile(MixinItemInstance item, MixinPlayer player, MixinLevel level, int x, int y, int z, int facing) {
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean onItemUseLeftClick(MixinItemInstance itemstack, MixinPlayer entityplayer, MixinLevel world, int i, int j, int k, int l) {
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float method_438(MixinItemInstance item, MixinTile tile) {
        return 1.0f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void onItemLeftClick(MixinItemInstance itemstack, MixinLevel world, MixinPlayer entityplayer) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinItemInstance use(MixinItemInstance item, MixinLevel level, MixinPlayer player) {
        return item;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int method_459() {
        return this.maxStackSize;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int method_470(int i) {
        return 0;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_462() {
        return this.field_465;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected MixinItemType method_457(boolean flag) {
        this.field_465 = flag;
        return this;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int method_464() {
        return this.field_402;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected MixinItemType setDurability(int i) {
        this.field_402 = i;
        return this;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_465() {
        return this.field_402 > 0 && !this.field_465;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean postHit(MixinItemInstance itemstack, MixinLivingEntity entityliving, MixinLivingEntity entityliving1) {
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean postMine(MixinItemInstance itemstack, int i, int j, int k, int l, MixinLivingEntity entityliving) {
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int method_447(MixinEntity entity) {
        return 1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isEffectiveOn(MixinTile tile) {
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void interactWithEntity(MixinItemInstance itemstack, MixinLivingEntity entityliving) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinItemType method_466() {
        this.field_464 = true;
        return this;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean shouldRenderLikeStick() {
        return this.field_464;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean shouldRotate180() {
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinItemType setName(String s) {
        this.translationKey = "item." + s;
        return this;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public String getTranslationKey() {
        return this.translationKey;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public String getTranslationKey(MixinItemInstance item) {
        return this.translationKey;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinItemType setContainerItem(MixinItemType itemType) {
        if (this.maxStackSize > 1) {
            throw new IllegalArgumentException("Max stack size must be 1 for items with crafting results");
        }
        this.containerItemType = itemType;
        return this;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinItemType getContainerItemType() {
        return this.containerItemType;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean hasContainerItemType() {
        return this.containerItemType != null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public String getTranslatedName() {
        return I18n.translate(this.getTranslationKey() + ".name");
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getNameColour(int i) {
        return 0xFFFFFF;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void inventoryTick(MixinItemInstance itemstack, MixinLevel world, MixinEntity entity, int i, boolean flag) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_461(MixinItemInstance itemstack, MixinLevel world, MixinPlayer entityplayer) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean mainActionLeftClick() {
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isLighting(MixinItemInstance itemstack) {
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isMuzzleFlash(MixinItemInstance itemstack) {
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void onAddToSlot(MixinPlayer entityPlayer, int slot, int damage) {
        Scriptable scope = Minecraft.minecraftInstance.level.scope;
        scope.put("slotID", scope, new Integer(slot));
        if (this.method_462()) {
            Minecraft.minecraftInstance.level.scriptHandler.runScript(String.format("item_onAddToSlot_%d_%d.js", new Object[]{this.id, damage}), scope, false);
        } else {
            Minecraft.minecraftInstance.level.scriptHandler.runScript(String.format("item_onAddToSlot_%d.js", new Object[]{this.id}), scope, false);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void onRemovedFromSlot(MixinPlayer entityPlayer, int slot, int damage) {
        Scriptable scope = Minecraft.minecraftInstance.level.scope;
        scope.put("slotID", scope, new Integer(slot));
        if (this.method_462()) {
            Minecraft.minecraftInstance.level.scriptHandler.runScript(String.format("item_onRemovedFromSlot_%d_%d.js", new Object[]{this.id, damage}), scope, false);
        } else {
            Minecraft.minecraftInstance.level.scriptHandler.runScript(String.format("item_onRemovedFromSlot_%d.js", new Object[]{this.id}), scope, false);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_445() {
        return false;
    }
}
