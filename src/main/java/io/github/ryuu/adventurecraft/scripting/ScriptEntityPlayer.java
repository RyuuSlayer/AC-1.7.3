/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package io.github.ryuu.adventurecraft.scripting;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.Player;
import net.minecraft.script.ScriptEntityLiving;
import net.minecraft.script.ScriptInventoryPlayer;
import io.github.ryuu.adventurecraft.mixin.item.MixinPlayer;

public class ScriptEntityPlayer extends ScriptEntityLiving {

    MixinPlayer entityPlayer;

    ScriptEntityPlayer(MixinPlayer e) {
        super(e);
        this.entityPlayer = e;
    }

    public ScriptInventoryPlayer getInventory() {
        return new ScriptInventoryPlayer(this.entityPlayer.inventory);
    }

    public String getCloak() {
        return this.entityPlayer.cloakTexture;
    }

    public void setCloak(String cloak) {
        this.entityPlayer.cloakTexture = cloak;
    }

    public void removeCloak() {
        this.entityPlayer.cloakTexture = null;
    }

    public void swingMainHand() {
        this.entityPlayer.swingHand();
    }

    public void swingOffHand() {
        this.entityPlayer.swingOffhandItem();
    }
}
