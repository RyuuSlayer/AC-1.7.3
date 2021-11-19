package io.github.ryuu.adventurecraft.rendering;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
            this.method_818(entityliving, String.format((String) "%s - %d", (Object[]) new Object[] { npc.npcName, npc.id }), d, d1, d2, 64);
        } else if (npc.seesThePlayer()) {
            this.method_818(entityliving, npc.npcName, d, d1, d2, 64);
        }
    }
}
