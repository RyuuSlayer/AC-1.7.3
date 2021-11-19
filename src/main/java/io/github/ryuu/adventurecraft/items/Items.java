package io.github.ryuu.adventurecraft.items;

public class Items {

    public static MixinItemType boomerang = new ItemBoomerang(200).setName("boomerang").method_466();

    public static MixinItemType doorKey = new MixinItemType(201).setTexturePosition(145).setName("key");

    public static ItemHookshot hookshot = (ItemHookshot) new ItemHookshot(202).setName("hookshot").method_466();

    public static MixinItemType heart = new MixinItemType(203).setTexturePosition(146).setName("heart");

    public static MixinItemType heartContainer = new MixinItemType(204).setTexturePosition(147).setName("heartContainer");

    public static MixinItemType woodenShield = new MixinItemType(205).setTexturePosition(148).setName("woodenShield");

    public static MixinItemType bossKey = new MixinItemType(209).setTexturePosition(149).setName("bossKey");

    public static MixinItemType bomb = new ItemBomb(210).setName("bomb").method_466();

    public static MixinItemType bombArow = new MixinItemType(211).setTexturePosition(166).setName("bombArrow");

    public static MixinItemType powerGlove = new ItemPowerGlove(212).setTexturePosition(177).setName("powerGlove");

    public static MixinItemType heartPiece = new MixinItemType(213).setTexturePosition(176).setName("heartPiece");

    public static MixinItemType umbrella = new ItemUmbrella(214).setTexturePosition(179).setName("umbrella").method_466();

    public static MixinItemType lantern = new ItemLantern(215).setTexturePosition(180).setName("lantern").setDurability(1200).setMaxStackSize(1);

    public static MixinItemType oil = new MixinItemType(216).setTexturePosition(181).setName("oil");

    public static MixinItemType pistol = new ItemPistol(217).setTexturePosition(192).setName("pistol").setDurability(15).method_466();

    public static MixinItemType rifle = new ItemRifle(218).setTexturePosition(193).setName("rifle").setDurability(30).method_466();

    public static MixinItemType shotgun = new ItemShotgun(219).setTexturePosition(194).setName("shotgun").setDurability(7).method_466();

    public static MixinItemType pistolAmmo = new MixinItemType(230).setTexturePosition(208).setName("pistolAmmo");

    public static MixinItemType rifleAmmo = new MixinItemType(231).setTexturePosition(209).setName("rifleAmmo");

    public static MixinItemType shotgunAmmo = new MixinItemType(232).setTexturePosition(210).setName("shotgunAmmo");

    public static MixinItemType harp = new ItemInstrument(206, "note.harp").setTexturePosition(160).setName("harp").method_466();

    public static MixinItemType guitar = new ItemInstrument(207, "note.bass").setTexturePosition(161).setName("guitar").method_466();

    public static MixinItemType snare = new ItemInstrument(208, "note.snare").setTexturePosition(162).setName("snare").method_466();

    public static MixinItemType pegagusBoots = new ItemPegasusBoots(240).setName("pegasusBoots");

    public static MixinItemType cursor = new ItemCursor(300).setTexturePosition(224).setName("cursor").method_466();

    public static MixinItemType brush = new ItemBrush(301).setTexturePosition(225).setName("brush").method_466();

    public static MixinItemType eraser = new ItemEraser(302).setTexturePosition(226).setName("eraser").method_466();

    public static MixinItemType paintBucket = new ItemPaintBucket(303).setTexturePosition(227).setName("paintBucket");

    public static MixinItemType hammer = new ItemHammer(304).setTexturePosition(228).setName("hammer").method_466();

    public static MixinItemType wrench = new ItemWrench(305).setTexturePosition(230).setName("wrench").method_466();

    public static MixinItemType npcStick = new ItemNPCStick(306).setName("npcStick").method_466();

    public static MixinItemType triggerStick = new ItemTriggerStick(307).setName("triggerStick").method_466();

    public static MixinItemType quill = new ItemQuill(330).setTexturePosition(229).setName("quill");

    public static MixinItemType paste = new ItemPaste(308).setTexturePosition(231).setName("paste");

    public static MixinItemType nudge = new ItemNudge(309).setTexturePosition(232).setName("nudge");

    private Items() {
    }
}
