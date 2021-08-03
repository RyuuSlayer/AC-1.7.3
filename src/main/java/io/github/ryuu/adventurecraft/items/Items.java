package io.github.ryuu.adventurecraft.items;

public class Items {
    public static gm boomerang = (new ItemBoomerang(200)).a("boomerang").h();

    public static gm doorKey = (new gm(201)).c(145).a("key");

    public static ItemHookshot hookshot = (ItemHookshot)(new ItemHookshot(202)).a("hookshot").h();

    public static gm heart = (new gm(203)).c(146).a("heart");

    public static gm heartContainer = (new gm(204)).c(147).a("heartContainer");

    public static gm woodenShield = (new gm(205)).c(148).a("woodenShield");

    public static gm bossKey = (new gm(209)).c(149).a("bossKey");

    public static gm bomb = (new ItemBomb(210)).a("bomb").h();

    public static gm bombArow = (new gm(211)).c(166).a("bombArrow");

    public static gm powerGlove = (new ItemPowerGlove(212)).c(177).a("powerGlove");

    public static gm heartPiece = (new gm(213)).c(176).a("heartPiece");

    public static gm umbrella = (new ItemUmbrella(214)).c(179).a("umbrella").h();

    public static gm lantern = (new ItemLantern(215)).c(180).a("lantern").e(1200).d(1);

    public static gm oil = (new gm(216)).c(181).a("oil");

    public static gm pistol = (new ItemPistol(217)).c(192).a("pistol").e(15).h();

    public static gm rifle = (new ItemRifle(218)).c(193).a("rifle").e(30).h();

    public static gm shotgun = (new ItemShotgun(219)).c(194).a("shotgun").e(7).h();

    public static gm pistolAmmo = (new gm(230)).c(208).a("pistolAmmo");

    public static gm rifleAmmo = (new gm(231)).c(209).a("rifleAmmo");

    public static gm shotgunAmmo = (new gm(232)).c(210).a("shotgunAmmo");

    public static gm harp = (new ItemInstrument(206, "note.harp")).c(160).a("harp").h();

    public static gm guitar = (new ItemInstrument(207, "note.bass")).c(161).a("guitar").h();

    public static gm snare = (new ItemInstrument(208, "note.snare")).c(162).a("snare").h();

    public static gm pegagusBoots = (new ItemPegasusBoots(240)).a("pegasusBoots");

    public static gm cursor = (new ItemCursor(300)).c(224).a("cursor").h();

    public static gm brush = (new ItemBrush(301)).c(225).a("brush").h();

    public static gm eraser = (new ItemEraser(302)).c(226).a("eraser").h();

    public static gm paintBucket = (new ItemPaintBucket(303)).c(227).a("paintBucket");

    public static gm hammer = (new ItemHammer(304)).c(228).a("hammer").h();

    public static gm wrench = (new ItemWrench(305)).c(230).a("wrench").h();

    public static gm npcStick = (new ItemNPCStick(306)).a("npcStick").h();

    public static gm triggerStick = (new ItemTriggerStick(307)).a("triggerStick").h();

    public static gm quill = (new ItemQuill(330)).c(229).a("quill");

    public static gm paste = (new ItemPaste(308)).c(231).a("paste");

    public static gm nudge = (new ItemNudge(309)).c(232).a("nudge");
}