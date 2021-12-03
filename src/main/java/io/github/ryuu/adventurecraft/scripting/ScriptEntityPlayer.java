package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.extensions.entity.player.ExPlayer;
import net.minecraft.entity.player.Player;

@SuppressWarnings("unused")
public class ScriptEntityPlayer extends ScriptEntityLiving {

    Player entityPlayer;

    public ScriptEntityPlayer(Player entity) {
        super(entity);
        this.entityPlayer = entity;
    }

    public ScriptInventoryPlayer getInventory() {
        return new ScriptInventoryPlayer(this.entityPlayer.inventory);
    }

    public String getCloak() {
        return ((ExPlayer) this.entityPlayer).getCloakTexture();
    }

    public void setCloak(String cloak) {
        ((ExPlayer) this.entityPlayer).setCloakTexture(cloak);
    }

    public void removeCloak() {
        ((ExPlayer) this.entityPlayer).setCloakTexture(null);
    }

    public void swingMainHand() {
        this.entityPlayer.swingHand();
    }

    public void swingOffHand() {
        ((ExPlayer) this.entityPlayer).swingOffhandItem();
    }
}
