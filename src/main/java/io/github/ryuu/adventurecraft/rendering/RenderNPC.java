package io.github.ryuu.adventurecraft.rendering;

import io.github.ryuu.adventurecraft.entities.EntityNPC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;

public class RenderNPC extends RenderBipedScaledScripted {

    public RenderNPC(BipedModel modelbase) {
        super(modelbase);
    }

    @Override
    protected void method_821(LivingEntity entityliving, double d, double d1, double d2) {
        EntityNPC npc = (EntityNPC) entityliving;
        if (Minecraft.isDebugHudEnabled()) {
            this.method_818(entityliving, String.format("%s - %d", npc.npcName, npc.id), d, d1, d2, 64);
        } else if (npc.seesThePlayer()) {
            this.method_818(entityliving, npc.npcName, d, d1, d2, 64);
        }
    }
}
