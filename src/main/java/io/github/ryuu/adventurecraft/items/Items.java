package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.mixin.item.AccessorItemType;
import net.minecraft.item.ItemType;

public class Items {

    public static ItemType boomerang = new ItemBoomerang(200).setName("boomerang").method_466();

    public static ItemType doorKey = new ACItemType(201).setTexturePosition(145).setName("key");

    public static ItemHookshot hookshot = (ItemHookshot) new ItemHookshot(202).setName("hookshot").method_466();

    public static ItemType heart = new ACItemType(203).setTexturePosition(146).setName("heart");

    public static ItemType heartContainer = new ACItemType(204).setTexturePosition(147).setName("heartContainer");

    public static ItemType woodenShield = new ACItemType(205).setTexturePosition(148).setName("woodenShield");

    public static ItemType bossKey = new ACItemType(209).setTexturePosition(149).setName("bossKey");

    public static ItemType bomb = new ItemBomb(210).setName("bomb").method_466();

    public static ItemType bombArow = new ACItemType(211).setTexturePosition(166).setName("bombArrow");

    public static ItemType powerGlove = new ItemPowerGlove(212).setTexturePosition(177).setName("powerGlove");

    public static ItemType heartPiece = new ACItemType(213).setTexturePosition(176).setName("heartPiece");

    public static ItemType umbrella = new ItemUmbrella(214).setTexturePosition(179).setName("umbrella").method_466();

    public static ItemType lantern = new ItemLantern(215).setTexturePosition(180).setName("lantern").setMaxStackSize(1);

    public static ItemType oil = new ACItemType(216).setTexturePosition(181).setName("oil");

    public static ItemType pistol = new ItemPistol(217).setTexturePosition(192).setName("pistol").method_466();

    public static ItemType rifle = new ItemRifle(218).setTexturePosition(193).setName("rifle").method_466();

    public static ItemType shotgun = new ItemShotgun(219).setTexturePosition(194).setName("shotgun").method_466();

    public static ItemType pistolAmmo = new ACItemType(230).setTexturePosition(208).setName("pistolAmmo");

    public static ItemType rifleAmmo = new ACItemType(231).setTexturePosition(209).setName("rifleAmmo");

    public static ItemType shotgunAmmo = new ACItemType(232).setTexturePosition(210).setName("shotgunAmmo");

    public static ItemType harp = new ItemInstrument(206, "note.harp").setTexturePosition(160).setName("harp").method_466();

    public static ItemType guitar = new ItemInstrument(207, "note.bass").setTexturePosition(161).setName("guitar").method_466();

    public static ItemType snare = new ItemInstrument(208, "note.snare").setTexturePosition(162).setName("snare").method_466();

    public static ItemType pegagusBoots = new ItemPegasusBoots(240).setName("pegasusBoots");

    public static ItemType cursor = new ItemCursor(300).setTexturePosition(224).setName("cursor").method_466();

    public static ItemType brush = new ItemBrush(301).setTexturePosition(225).setName("brush").method_466();

    public static ItemType eraser = new ItemEraser(302).setTexturePosition(226).setName("eraser").method_466();

    public static ItemType paintBucket = new ItemPaintBucket(303).setTexturePosition(227).setName("paintBucket");

    public static ItemType hammer = new ItemHammer(304).setTexturePosition(228).setName("hammer").method_466();

    public static ItemType wrench = new ItemWrench(305).setTexturePosition(230).setName("wrench").method_466();

    public static ItemType npcStick = new ItemNPCStick(306).setName("npcStick").method_466();

    public static ItemType triggerStick = new ItemTriggerStick(307).setName("triggerStick").method_466();

    public static ItemType quill = new ItemQuill(330).setTexturePosition(229).setName("quill");

    public static ItemType paste = new ItemPaste(308).setTexturePosition(231).setName("paste");

    public static ItemType nudge = new ItemNudge(309).setTexturePosition(232).setName("nudge");

    static {
        ((AccessorItemType) lantern).invokeSetDurability(1200);
        ((AccessorItemType) pistol).invokeSetDurability(15);
        ((AccessorItemType) rifle).invokeSetDurability(30);
        ((AccessorItemType) shotgun).invokeSetDurability(7);
    }
}
