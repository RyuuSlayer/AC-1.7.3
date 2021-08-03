package io.github.ryuu.adventurecraft.rendering;

import io.github.ryuu.adventurecraft.entities.EntityNPC;
import net.minecraft.client.Minecraft;

public class RenderNPC extends RenderBipedScaledScripted {
    public RenderNPC(fh modelbase) {
        super(modelbase);
    }

    protected void a(ls entityliving, double d, double d1, double d2) {
        EntityNPC npc = (EntityNPC) entityliving;
        if (Minecraft.w()) {
            a(entityliving, String.format("%s - %d", new Object[]{npc.npcName, Integer.valueOf(npc.aD)}), d, d1, d2, 64);
        } else if (npc.seesThePlayer()) {
            a(entityliving, npc.npcName, d, d1, d2, 64);
        }
    }
}
