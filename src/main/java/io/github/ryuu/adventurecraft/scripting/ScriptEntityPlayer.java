package io.github.ryuu.adventurecraft.scripting;

import net.minecraft.entity.player.Player;

public class ScriptEntityPlayer extends ScriptEntityLiving {

    Player entityPlayer;

    ScriptEntityPlayer(Player e) {
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
