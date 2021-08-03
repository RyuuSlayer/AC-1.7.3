package io.github.ryuu.adventurecraft.items;

import net.minecraft.item.ItemType;

public class Items {
    public static ItemType boomerang = (new ItemBoomerang(200)).a("boomerang").h();

    public static ItemType doorKey = (new ItemType(201)).c(145).a("key");

    public static ItemHookshot hookshot = (ItemHookshot) (new ItemHookshot(202)).a("hookshot").h();

    public static ItemType heart = (new ItemType(203)).c(146).a("heart");

    public static ItemType heartContainer = (new ItemType(204)).c(147).a("heartContainer");

    public static ItemType woodenShield = (new ItemType(205)).c(148).a("woodenShield");

    public static ItemType bossKey = (new ItemType(209)).c(149).a("bossKey");

    public static ItemType bomb = (new ItemBomb(210)).a("bomb").h();

    public static ItemType bombArow = (new ItemType(211)).c(166).a("bombArrow");

    public static ItemType powerGlove = (new ItemPowerGlove(212)).c(177).a("powerGlove");

    public static ItemType heartPiece = (new ItemType(213)).c(176).a("heartPiece");

    public static ItemType umbrella = (new ItemUmbrella(214)).c(179).a("umbrella").h();

    public static ItemType lantern = (new ItemLantern(215)).c(180).a("lantern").e(1200).d(1);

    public static ItemType oil = (new ItemType(216)).c(181).a("oil");

    public static ItemType pistol = (new ItemPistol(217)).c(192).a("pistol").e(15).h();

    public static ItemType rifle = (new ItemRifle(218)).c(193).a("rifle").e(30).h();

    public static ItemType shotgun = (new ItemShotgun(219)).c(194).a("shotgun").e(7).h();

    public static ItemType pistolAmmo = (new ItemType(230)).c(208).a("pistolAmmo");

    public static ItemType rifleAmmo = (new ItemType(231)).c(209).a("rifleAmmo");

    public static ItemType shotgunAmmo = (new ItemType(232)).c(210).a("shotgunAmmo");

    public static ItemType harp = (new ItemInstrument(206, "note.harp")).c(160).a("harp").h();

    public static ItemType guitar = (new ItemInstrument(207, "note.bass")).c(161).a("guitar").h();

    public static ItemType snare = (new ItemInstrument(208, "note.snare")).c(162).a("snare").h();

    public static ItemType pegagusBoots = (new ItemPegasusBoots(240)).a("pegasusBoots");

    public static ItemType cursor = (new ItemCursor(300)).c(224).a("cursor").h();

    public static ItemType brush = (new ItemBrush(301)).c(225).a("brush").h();

    public static ItemType eraser = (new ItemEraser(302)).c(226).a("eraser").h();

    public static ItemType paintBucket = (new ItemPaintBucket(303)).c(227).a("paintBucket");

    public static ItemType hammer = (new ItemHammer(304)).c(228).a("hammer").h();

    public static ItemType wrench = (new ItemWrench(305)).c(230).a("wrench").h();

    public static ItemType npcStick = (new ItemNPCStick(306)).a("npcStick").h();

    public static ItemType triggerStick = (new ItemTriggerStick(307)).a("triggerStick").h();

    public static ItemType quill = (new ItemQuill(330)).c(229).a("quill");

    public static ItemType paste = (new ItemPaste(308)).c(231).a("paste");

    public static ItemType nudge = (new ItemNudge(309)).c(232).a("nudge");
}