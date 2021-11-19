package io.github.ryuu.adventurecraft.rendering;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.model.BipedModel;

public class RenderNPC extends RenderBipedScaledScripted {

    public RenderNPC(BipedModel modelbase) {
        super(modelbase);
    }

    @Override
    protected void method_821(MixinLivingEntity entityliving, double d, double d1, double d2) {
        EntityNPC npc = (EntityNPC) entityliving;
        if (Minecraft.isDebugHudEnabled()) {
            this.method_818(entityliving, String.format("%s - %d", new Object[]{npc.npcName, npc.id}), d, d1, d2, 64);
        } else if (npc.seesThePlayer()) {
            this.method_818(entityliving, npc.npcName, d, d1, d2, 64);
        }
    }
}
