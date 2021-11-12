package io.github.ryuu.adventurecraft.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import org.mozilla.javascript.Scriptable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemType.class)
public class MixinItemType {
    protected MixinItemType(int i) {
        this.itemUseDelay = 5;
        this.bg = 64;
        this.a = 0;
        this.bi = false;
        this.bj = false;
        this.bk = null;
        this.bf = 256 + i;
        if (c[256 + i] != null)
            System.out.println("CONFLICT @ " + i);
        c[256 + i] = this;
    }

    public MixinItemType c(int i) {
        this.bh = i;
        return this;
    }

    public MixinItemType d(int i) {
        this.bg = i;
        return this;
    }

    public MixinItemType a(int i, int j) {
        this.bh = i + j * 16;
        return this;
    }

    public int a(int i) {
        return this.bh;
    }

    public final int b(ItemInstance itemstack) {
        int damage = 0;
        if (itemstack != null)
            damage = itemstack.i();
        return a(damage);
    }

    public boolean a(ItemInstance itemstack, Player entityplayer, Level world, int i, int j, int k, int l) {
        return false;
    }

    public boolean onItemUseLeftClick(ItemInstance itemstack, Player entityplayer, Level world, int i, int j, int k, int l) {
        return false;
    }

    public float a(ItemInstance itemstack, Tile block) {
        return 1.0F;
    }

    public void onItemLeftClick(ItemInstance itemstack, Level world, Player entityplayer) {
    }

    public ItemInstance a(ItemInstance itemstack, Level world, Player entityplayer) {
        return itemstack;
    }

    public int d() {
        return this.bg;
    }

    public int b(int i) {
        return 0;
    }

    public boolean e() {
        return this.bj;
    }

    protected MixinItemType a(boolean flag) {
        this.bj = flag;
        return this;
    }

    public int f() {
        return this.a;
    }

    protected MixinItemType e(int i) {
        this.a = i;
        return this;
    }

    public boolean g() {
        return (this.a > 0 && !this.bj);
    }

    public boolean a(ItemInstance itemstack, LivingEntity entityliving, LivingEntity entityliving1) {
        return false;
    }

    public boolean a(ItemInstance itemstack, int i, int j, int k, int l, LivingEntity entityliving) {
        return false;
    }

    public int a(Entity entity) {
        return 1;
    }

    public boolean a(Tile block) {
        return false;
    }

    public void a(ItemInstance itemstack, LivingEntity entityliving) {
    }

    public MixinItemType h() {
        this.bi = true;
        return this;
    }

    public boolean b() {
        return this.bi;
    }

    public boolean c() {
        return false;
    }

    public MixinItemType a(String s) {
        this.bl = "item." + s;
        return this;
    }

    public String a() {
        return this.bl;
    }

    public String a(ItemInstance itemstack) {
        return this.bl;
    }

    public MixinItemType a(MixinItemType item) {
        if (this.bg > 1)
            throw new IllegalArgumentException("Max stack size must be 1 for items with crafting results");
        this.bk = item;
        return this;
    }

    public MixinItemType i() {
        return this.bk;
    }

    public boolean j() {
        return (this.bk != null);
    }

    public String k() {
        return do.a(a() + ".name");
    }

    public int f(int i) {
        return 16777215;
    }

    public void a(ItemInstance itemstack, Level world, Entity entity, int i, boolean flag) {
    }

    public void b(ItemInstance itemstack, Level world, Player entityplayer) {
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
        Scriptable scope = Minecraft.minecraftInstance.f.scope;
        scope.put("slotID", scope, new Integer(slot));
        if (e()) {
            Minecraft.minecraftInstance.f.scriptHandler.runScript(String.format("item_onAddToSlot_%d_%d.js", new Object[]{Integer.valueOf(this.bf), Integer.valueOf(damage)}), scope, false);
        } else {
            Minecraft.minecraftInstance.f.scriptHandler.runScript(String.format("item_onAddToSlot_%d.js", new Object[]{Integer.valueOf(this.bf)}), scope, false);
        }
    }

    public void onRemovedFromSlot(Player entityPlayer, int slot, int damage) {
        Scriptable scope = Minecraft.minecraftInstance.f.scope;
        scope.put("slotID", scope, new Integer(slot));
        if (e()) {
            Minecraft.minecraftInstance.f.scriptHandler.runScript(String.format("item_onRemovedFromSlot_%d_%d.js", new Object[]{Integer.valueOf(this.bf), Integer.valueOf(damage)}), scope, false);
        } else {
            Minecraft.minecraftInstance.f.scriptHandler.runScript(String.format("item_onRemovedFromSlot_%d.js", new Object[]{Integer.valueOf(this.bf)}), scope, false);
        }
    }

    // TODO: Vanilla Code, no need for mixin.
    // TODO: Make sure Ryuu is not dumb.

    // protected static Random rand = new Random();
    // public static ItemType[] byId = new ItemType[32000];
    // public static ItemType shovelIron = (new ShovelItem(0, ToolMaterial.IRON)).setTexturePosition(2, 5).setName("shovelIron");
    // public static ItemType pickaxeIron = (new PickaxeItem(1, ToolMaterial.IRON)).setTexturePosition(2, 6).setName("pickaxeIron");
    // public static ItemType hatchetIron = (new HatchetItem(2, ToolMaterial.IRON)).setTexturePosition(2, 7).setName("hatchetIron");
    // public static ItemType flintAndSteel = (new FlintAndSteelItem(3)).setTexturePosition(5, 0).setName("flintAndSteel");
    // public static ItemType apple = (new FoodItem(4, 4, false)).setTexturePosition(10, 0).setName("apple");
    // public static ItemType bow = (new BowItem(5)).setTexturePosition(5, 1).setName("bow");
    // public static ItemType arrow = (new ItemType(6)).setTexturePosition(5, 2).setName("arrow");
    // public static ItemType coal = (new CoalItem(7)).setTexturePosition(7, 0).setName("coal");
    // public static ItemType diamond = (new ItemType(8)).setTexturePosition(7, 3).setName("emerald");
    // public static ItemType ingotIron = (new ItemType(9)).setTexturePosition(7, 1).setName("ingotIron");
    // public static ItemType ingotGold = (new ItemType(10)).setTexturePosition(7, 2).setName("ingotGold");
    // public static ItemType swordIron = (new SwordItem(11, ToolMaterial.IRON)).setTexturePosition(2, 4).setName("swordIron");
    // public static ItemType swordWood = (new SwordItem(12, ToolMaterial.WOOD)).setTexturePosition(0, 4).setName("swordWood");
    // public static ItemType shovelWood = (new ShovelItem(13, ToolMaterial.WOOD)).setTexturePosition(0, 5).setName("shovelWood");
    // public static ItemType pickaxeWood = (new PickaxeItem(14, ToolMaterial.WOOD)).setTexturePosition(0, 6).setName("pickaxeWood");
    // public static ItemType hatchetWood = (new HatchetItem(15, ToolMaterial.WOOD)).setTexturePosition(0, 7).setName("hatchetWood");
    // public static ItemType swordStone = (new SwordItem(16, ToolMaterial.STONE)).setTexturePosition(1, 4).setName("swordStone");
    // public static ItemType shovelStone = (new ShovelItem(17, ToolMaterial.STONE)).setTexturePosition(1, 5).setName("shovelStone");
    // public static ItemType pickaxeStone = (new PickaxeItem(18, ToolMaterial.STONE)).setTexturePosition(1, 6).setName("pickaxeStone");
    // public static ItemType hatchetStone = (new HatchetItem(19, ToolMaterial.STONE)).setTexturePosition(1, 7).setName("hatchetStone");
    // public static ItemType swordDiamond = (new SwordItem(20, ToolMaterial.EMERALD)).setTexturePosition(3, 4).setName("swordDiamond");
    // public static ItemType shovelDiamond = (new ShovelItem(21, ToolMaterial.EMERALD)).setTexturePosition(3, 5).setName("shovelDiamond");
    // public static ItemType pickaxeDiamond = (new PickaxeItem(22, ToolMaterial.EMERALD)).setTexturePosition(3, 6).setName("pickaxeDiamond");
    // public static ItemType stick = (new ItemType(24)).setTexturePosition(5, 3).method_466().setName("stick");
    // public static ItemType bowl = (new ItemType(25)).setTexturePosition(7, 4).setName("bowl");
    // public static ItemType mushroomStew = (new MushroomStewItem(26, 10)).setTexturePosition(8, 4).setName("mushroomStew");
    // public static ItemType swordGold = (new SwordItem(27, ToolMaterial.GOLD)).setTexturePosition(4, 4).setName("swordGold");
    // public static ItemType shovelGold = (new ShovelItem(28, ToolMaterial.GOLD)).setTexturePosition(4, 5).setName("shovelGold");
    // public static ItemType pickaxeGold = (new PickaxeItem(29, ToolMaterial.GOLD)).setTexturePosition(4, 6).setName("pickaxeGold");
    // public static ItemType hatchetGold = (new HatchetItem(30, ToolMaterial.GOLD)).setTexturePosition(4, 7).setName("hatchetGold");
    // public static ItemType string = (new ItemType(31)).setTexturePosition(8, 0).setName("string");
    // public static ItemType feather = (new ItemType(32)).setTexturePosition(8, 1).setName("feather");
    // public static ItemType sulphur = (new ItemType(33)).setTexturePosition(8, 2).setName("sulphur");
    // public static ItemType hoeWood = (new HoeItem(34, ToolMaterial.WOOD)).setTexturePosition(0, 8).setName("hoeWood");
    // public static ItemType hoeStone = (new HoeItem(35, ToolMaterial.STONE)).setTexturePosition(1, 8).setName("hoeStone");
    // public static ItemType hoeIron = (new HoeItem(36, ToolMaterial.IRON)).setTexturePosition(2, 8).setName("hoeIron");
    // public static ItemType hoeDiamond = (new HoeItem(37, ToolMaterial.EMERALD)).setTexturePosition(3, 8).setName("hoeDiamond");
    // public static ItemType hoeGold = (new HoeItem(38, ToolMaterial.GOLD)).setTexturePosition(4, 8).setName("hoeGold");
    // public static ItemType seeds = (new SeedsItem(39, net.minecraft.tile.Tile.CROPS.id)).setTexturePosition(9, 0).setName("seeds");
    // public static ItemType wheat = (new ItemType(40)).setTexturePosition(9, 1).setName("wheat");
    // public static ItemType bread = (new FoodItem(41, 5, false)).setTexturePosition(9, 2).setName("bread");
    // public static ItemType helmetCloth = (new ArmourItem(42, 0, 0, 0)).setTexturePosition(0, 0).setName("helmetCloth");
    // public static ItemType chestplateCloth = (new ArmourItem(43, 0, 0, 1)).setTexturePosition(0, 1).setName("chestplateCloth");
    // public static ItemType leggingsCloth = (new ArmourItem(44, 0, 0, 2)).setTexturePosition(0, 2).setName("leggingsCloth");
    // public static ItemType bootsCloth = (new ArmourItem(45, 0, 0, 3)).setTexturePosition(0, 3).setName("bootsCloth");
    // public static ItemType helmetChain = (new ArmourItem(46, 1, 1, 0)).setTexturePosition(1, 0).setName("helmetChain");
    // public static ItemType chestplateChain = (new ArmourItem(47, 1, 1, 1)).setTexturePosition(1, 1).setName("chestplateChain");
    // public static ItemType leggingsChain = (new ArmourItem(48, 1, 1, 2)).setTexturePosition(1, 2).setName("leggingsChain");
    // public static ItemType bootsChain = (new ArmourItem(49, 1, 1, 3)).setTexturePosition(1, 3).setName("bootsChain");
    // public static ItemType helmetIron = (new ArmourItem(50, 2, 2, 0)).setTexturePosition(2, 0).setName("helmetIron");
    // public static ItemType chestplateIron = (new ArmourItem(51, 2, 2, 1)).setTexturePosition(2, 1).setName("chestplateIron");
    // public static ItemType leggingsIron = (new ArmourItem(52, 2, 2, 2)).setTexturePosition(2, 2).setName("leggingsIron");
    // public static ItemType bootsIron = (new ArmourItem(53, 2, 2, 3)).setTexturePosition(2, 3).setName("bootsIron");
    // public static ItemType helmetDiamond = (new ArmourItem(54, 3, 3, 0)).setTexturePosition(3, 0).setName("helmetDiamond");
    // public static ItemType chestplateDiamond = (new ArmourItem(55, 3, 3, 1)).setTexturePosition(3, 1).setName("chestplateDiamond");
    // public static ItemType leggingsDiamond = (new ArmourItem(56, 3, 3, 2)).setTexturePosition(3, 2).setName("leggingsDiamond");
    // public static ItemType bootsDiamond = (new ArmourItem(57, 3, 3, 3)).setTexturePosition(3, 3).setName("bootsDiamond");
    // public static ItemType helmetGold = (new ArmourItem(58, 1, 4, 0)).setTexturePosition(4, 0).setName("helmetGold");
    // public static ItemType chestplateGold = (new ArmourItem(59, 1, 4, 1)).setTexturePosition(4, 1).setName("chestplateGold");
    // public static ItemType leggingsGold = (new ArmourItem(60, 1, 4, 2)).setTexturePosition(4, 2).setName("leggingsGold");
    // public static ItemType bootsGold = (new ArmourItem(61, 1, 4, 3)).setTexturePosition(4, 3).setName("bootsGold");
    // public static ItemType flint = (new ItemType(62)).setTexturePosition(6, 0).setName("flint");
    // public static ItemType porkchopRaw = (new FoodItem(63, 3, true)).setTexturePosition(7, 5).setName("porkchopRaw");
    // public static ItemType porkchopCooked = (new FoodItem(64, 8, true)).setTexturePosition(8, 5).setName("porkchopCooked");
    // public static ItemType painting = (new PaintingItem(65)).setTexturePosition(10, 1).setName("painting");
    // public static ItemType appleGold = (new FoodItem(66, 42, false)).setTexturePosition(11, 0).setName("appleGold");
    // public static ItemType sign = (new SignItem(67)).setTexturePosition(10, 2).setName("sign");
    // public static ItemType doorWood = (new DoorItem(68, Material.WOOD)).setTexturePosition(11, 2).setName("doorWood");
    // public static ItemType bucket = (new BucketItem(69, 0)).setTexturePosition(10, 4).setName("bucket");
    // public static ItemType bucketWater = (new BucketItem(70, net.minecraft.tile.Tile.FLOWING_WATER.id)).setTexturePosition(11, 4).setName("bucketWater").setContainerItem(bucket);
    // public static ItemType bucketLava = (new BucketItem(71, net.minecraft.tile.Tile.FLOWING_LAVA.id)).setTexturePosition(12, 4).setName("bucketLava").setContainerItem(bucket);
    // public static ItemType minecart = (new MinecartItem(72, 0)).setTexturePosition(7, 8).setName("minecart");
    // public static ItemType saddle = (new SaddleItem(73)).setTexturePosition(8, 6).setName("saddle");
    // public static ItemType doorIron = (new DoorItem(74, Material.METAL)).setTexturePosition(12, 2).setName("doorIron");
    // public static ItemType redstone = (new RedstoneItem(75)).setTexturePosition(8, 3).setName("redstone");
    // public static ItemType snowball = (new SnowballItem(76)).setTexturePosition(14, 0).setName("snowball");
    // public static ItemType boat = (new BoatItem(77)).setTexturePosition(8, 8).setName("boat");
    // public static ItemType leather = (new ItemType(78)).setTexturePosition(7, 6).setName("leather");
    // public static ItemType milk = (new BucketItem(79, -1)).setTexturePosition(13, 4).setName("milk").setContainerItem(bucket);
    // public static ItemType brick = (new ItemType(80)).setTexturePosition(6, 1).setName("brick");
    // public static ItemType clay = (new ItemType(81)).setTexturePosition(9, 3).setName("clay");
    // public static ItemType reeds = (new TileItem(82, net.minecraft.tile.Tile.REEDS)).setTexturePosition(11, 1).setName("reeds");
    // public static ItemType paper = (new ItemType(83)).setTexturePosition(10, 3).setName("paper");
    // public static ItemType book = (new ItemType(84)).setTexturePosition(11, 3).setName("book");
    // public static ItemType slimeball = (new ItemType(85)).setTexturePosition(14, 1).setName("slimeball");
    // public static ItemType minecartChest = (new MinecartItem(86, 1)).setTexturePosition(7, 9).setName("minecartChest");
    // public static ItemType minecartFurnace = (new MinecartItem(87, 2)).setTexturePosition(7, 10).setName("minecartFurnace");
    // public static ItemType egg = (new EggItem(88)).setTexturePosition(12, 0).setName("egg");
    // public static ItemType compass = (new ItemType(89)).setTexturePosition(6, 3).setName("compass");
    // public static ItemType fishingRod = (new FishingRodItem(90)).setTexturePosition(5, 4).setName("fishingRod");
    // public static ItemType clock = (new ItemType(91)).setTexturePosition(6, 4).setName("clock");
    // public static ItemType yellowDust = (new ItemType(92)).setTexturePosition(9, 4).setName("yellowDust");
    // public static ItemType fishRaw = (new FoodItem(93, 2, false)).setTexturePosition(9, 5).setName("fishRaw");
    // public static ItemType fishCooked = (new FoodItem(94, 5, false)).setTexturePosition(10, 5).setName("fishCooked");
    // public static ItemType dyePowder = (new DyeItem(95)).setTexturePosition(14, 4).setName("dyePowder");
    // public static ItemType bone = (new ItemType(96)).setTexturePosition(12, 1).setName("bone").method_466();
    // public static ItemType sugar = (new ItemType(97)).setTexturePosition(13, 0).setName("sugar").method_466();
    // public static ItemType cake = (new TileItem(98, net.minecraft.tile.Tile.CAKE)).setMaxStackSize(1).setTexturePosition(13, 1).setName("cake");
    // public static ItemType bed = (new BedItem(99)).setMaxStackSize(1).setTexturePosition(13, 2).setName("bed");
    // public static ItemType diode = (new TileItem(100, net.minecraft.tile.Tile.REDSTONE_REPEATER)).setTexturePosition(6, 5).setName("diode");
    // public static ItemType cookie = (new CookieItem(101, 1, false, 8)).setTexturePosition(12, 5).setName("cookie");
    // public static MapItem map = (MapItem)(new MapItem(102)).setTexturePosition(12, 3).setName("map");
    // public static Shears shears = (Shears)(new Shears(103)).setTexturePosition(13, 5).setName("shears");
    // public static ItemType record_13 = (new RecordItem(2000, "13")).setTexturePosition(0, 15).setName("record");
    // public static ItemType record_cat = (new RecordItem(2001, "cat")).setTexturePosition(1, 15).setName("record");
    // public final int id;
    // protected int maxStackSize = 64;
    // private int field_402 = 0;
    // protected int texturePosition;
    // protected boolean field_464 = false;
    // protected boolean field_465 = false;
    // private ItemType containerItemType = null;
    // private String translationKey;

    @Shadow
    public boolean decrementDamage;

    @Shadow
    public int itemUseDelay;

    static {
        d = (new wc(0, bu.c)).a(2, 5).a("shovelIron");
        e = (new au(1, bu.c)).a(2, 6).a("pickaxeIron");
        f = (new ta(2, bu.c)).a(2, 7).a("hatchetIron");
        o = (new qd(11, bu.c)).a(2, 4).a("swordIron");
        p = (new qd(12, bu.a)).a(0, 4).a("swordWood");
        q = (new wc(13, bu.a)).a(0, 5).a("shovelWood");
        r = (new au(14, bu.a)).a(0, 6).a("pickaxeWood");
        s = (new ta(15, bu.a)).a(0, 7).a("hatchetWood");
        t = (new qd(16, bu.b)).a(1, 4).a("swordStone");
        u = (new wc(17, bu.b)).a(1, 5).a("shovelStone");
        v = (new au(18, bu.b)).a(1, 6).a("pickaxeStone");
        w = (new ta(19, bu.b)).a(1, 7).a("hatchetStone");
        x = (new qd(20, bu.d)).a(3, 4).a("swordDiamond");
        y = (new wc(21, bu.d)).a(3, 5).a("shovelDiamond");
        z = (new au(22, bu.d)).a(3, 6).a("pickaxeDiamond");
        A = (new ta(23, bu.d)).a(3, 7).a("hatchetDiamond");
        E = (new qd(27, bu.e)).a(4, 4).a("swordGold");
        F = (new wc(28, bu.e)).a(4, 5).a("shovelGold");
        G = (new au(29, bu.e)).a(4, 6).a("pickaxeGold");
        H = (new ta(30, bu.e)).a(4, 7).a("hatchetGold");
        L = (new la(34, bu.a)).a(0, 8).a("hoeWood");
        M = (new la(35, bu.b)).a(1, 8).a("hoeStone");
        N = (new la(36, bu.c)).a(2, 8).a("hoeIron");
        O = (new la(37, bu.d)).a(3, 8).a("hoeDiamond");
        P = (new la(38, bu.e)).a(4, 8).a("hoeGold");
        Q = (new rk(39, Tile.aA.bn)).a(9, 0).a("seeds");
        at = (new hu(68, ln.d)).a(11, 2).a("doorWood");
        au = (new bc(69, 0)).a(10, 4).a("bucket");
        av = (new bc(70, Tile.B.bn)).a(11, 4).a("bucketWater").a(au);
        aw = (new bc(71, Tile.D.bn)).a(12, 4).a("bucketLava").a(au);
        az = (new hu(74, ln.f)).a(12, 2).a("doorIron");
        aE = (new bc(79, -1)).a(13, 4).a("milk").a(au);
        aH = (new lv(82, Tile.aY)).a(11, 1).a("reeds");
        aX = (new lv(98, Tile.bh)).d(1).a(13, 1).a("cake");
        aZ = (new lv(100, Tile.bi)).a(6, 5).a("diode");
        jl.c();
    }
}