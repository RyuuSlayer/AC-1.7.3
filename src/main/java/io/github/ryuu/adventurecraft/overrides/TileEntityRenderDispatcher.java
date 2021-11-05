package io.github.ryuu.adventurecraft.overrides;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.github.ryuu.adventurecraft.entities.tile.*;
import net.minecraft.client.render.entity.tile.TileEntityRenderer;
import net.minecraft.level.Level;
import org.lwjgl.opengl.GL11;

public class TileEntityRenderDispatcher {
    public static TileEntityRenderDispatcher a = new TileEntityRenderDispatcher();
    public static double b;
    public static double c;
    public static double d;
    private final Map m;
    public TextureManager e;
    public Level f;
    public LivingEntity g;
    public float h;
    public float i;
    public double j;
    public double k;
    public double l;
    private TextRenderer n;

    private TileEntityRenderDispatcher() {
        this.m = new HashMap<>();
        this.m.put(Sign.class, new po());
        this.m.put(cy.class, new ag());
        this.m.put(uk.class, new PistonRenderer());
        this.m.put(TileEntityTrigger.class, new TileEntityMinMaxRenderer(1.0F, 0.5882F, 0.0F));
        this.m.put(TileEntityTriggerInverter.class, new TileEntityMinMaxRenderer(1.0F, 1.0F, 0.0F));
        this.m.put(TileEntityTriggerMemory.class, new TileEntityMinMaxRenderer(0.0F, 1.0F, 0.0F));
        this.m.put(TileEntityTimer.class, new TileEntityMinMaxRenderer(0.4F, 0.17647F, 0.56863F));
        this.m.put(TileEntityRedstoneTrigger.class, new TileEntityMinMaxRenderer(1.0F, 0.0F, 0.0F));
        this.m.put(TileEntityMobSpawner.class, new TileEntityMobSpawnerRenderer());
        this.m.put(TileEntityStore.class, new TileEntityStoreRenderer());
        this.m.put(TileEntityEffect.class, new TileEntityEffectRenderer());
        for (Iterator<TileEntityRenderer> iterator = this.m.values().iterator(); iterator.hasNext(); tileentityspecialrenderer.a(this))
            TileEntityRenderer tileentityspecialrenderer = iterator.next();
    }

    public TileEntityRenderer a(Class<TileEntity> class1) {
        TileEntityRenderer tileentityspecialrenderer = (TileEntityRenderer) this.m.get(class1);
        if (tileentityspecialrenderer == null && class1 != TileEntity.class) {
            tileentityspecialrenderer = a(class1.getSuperclass());
            this.m.put(class1, tileentityspecialrenderer);
        }
        return tileentityspecialrenderer;
    }

    public boolean a(TileEntity tileentity) {
        return (b(tileentity) != null);
    }

    public je b(TileEntity tileentity) {
        if (tileentity == null)
            return null;
        return a(tileentity.getClass());
    }

    public void a(Level world, TextureManager renderengine, TextRenderer fontrenderer, LivingEntity entityliving, float f) {
        if (this.f != world)
            a(world);
        this.e = renderengine;
        this.g = entityliving;
        this.n = fontrenderer;
        this.h = entityliving.aU + (entityliving.aS - entityliving.aU) * f;
        this.i = entityliving.aV + (entityliving.aT - entityliving.aV) * f;
        this.j = entityliving.bl + (entityliving.aM - entityliving.bl) * f;
        this.k = entityliving.bm + (entityliving.aN - entityliving.bm) * f;
        this.l = entityliving.bn + (entityliving.aO - entityliving.bn) * f;
    }

    public void a(TileEntity tileentity, float f) {
        if (tileentity.a(this.j, this.k, this.l) < 4096.0D) {
            float f1 = this.f.c(tileentity.e, tileentity.f, tileentity.g);
            GL11.glColor3f(f1, f1, f1);
            a(tileentity, tileentity.e - b, tileentity.f - c, tileentity.g - d, f);
        }
    }

    public void a(TileEntity tileentity, double d, double d1, double d2, float f) {
        TileEntityRenderer tileentityspecialrenderer = b(tileentity);
        if (tileentityspecialrenderer != null)
            tileentityspecialrenderer.a(tileentity, d, d1, d2, f);
    }

    public void a(Level world) {
        this.f = world;
        Iterator<TileEntityRenderer> iterator = this.m.values().iterator();
        while (iterator.hasNext()) {
            TileEntityRenderer tileentityspecialrenderer = iterator.next();
            if (tileentityspecialrenderer != null)
                tileentityspecialrenderer.a(world);
        }
    }

    public TextRenderer a() {
        return this.n;
    }
}
