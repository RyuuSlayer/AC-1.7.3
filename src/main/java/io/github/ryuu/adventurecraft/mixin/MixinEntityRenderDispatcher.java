package io.github.ryuu.adventurecraft.mixin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.github.ryuu.adventurecraft.entities.*;
import io.github.ryuu.adventurecraft.models.ModelBat;
import io.github.ryuu.adventurecraft.models.ModelCamera;
import io.github.ryuu.adventurecraft.models.ModelRat;
import io.github.ryuu.adventurecraft.rendering.*;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.render.HandItemRenderer;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.Ghast;
import net.minecraft.entity.monster.Slime;
import net.minecraft.entity.monster.Zombie;
import net.minecraft.entity.player.Player;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.entity.projectile.Snowball;
import net.minecraft.entity.projectile.ThrownEgg;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.util.maths.MathsHelper;
import org.lwjgl.opengl.GL11;

public class MixinEntityRenderDispatcher {
    public static th a = new th();
    public static double b;
    public static double c;
    public static double d;
    private final Map o;
    public TextureManager e;
    public HandItemRenderer f;
    public Level g;
    public LivingEntity h;
    public float i;
    public float j;
    public GameOptions k;
    public double l;
    public double m;
    public double n;
    private TextRenderer p;

    private MixinEntityRenderDispatcher() {
        this.o = new HashMap<>();
        this.o.put(cn.class, new yx());
        this.o.put(wh.class, new me((ko) new eh(), (ko) new eh(0.5F), 0.7F));
        this.o.put(dl.class, new xy((ko) new mw(), (ko) new ea(), 0.7F));
        this.o.put(bx.class, new va((ko) new hh(), 0.7F));
        this.o.put(gi.class, new we((ko) new o(), 0.5F));
        this.o.put(ww.class, new ip((ko) new td(), 0.3F));
        this.o.put(gb.class, new m());
        this.o.put(fr.class, new v((fh) new lc(), 0.5F));
        this.o.put(EntitySkeletonBoss.class, new RenderBipedScaled((fh) new lc(), 0.5F, 2.5F));
        this.o.put(Zombie.class, new v((fh) new ej(), 0.5F));
        this.o.put(Slime.class, new mj((ko) new no(16), (ko) new no(0), 0.25F));
        this.o.put(Player.class, new PlayerRenderer());
        this.o.put(nt.class, new yg((ko) new ej(), 0.5F, 6.0F));
        this.o.put(Ghast.class, new pq());
        this.o.put(xt.class, new es((ko) new wn(), 0.7F));
        this.o.put(LivingEntity.class, new gv((ko) new fh(), 0.5F));
        this.o.put(Entity.class, new mb());
        this.o.put(Painting.class, new dy());
        this.o.put(Arrow.class, new mc());
        this.o.put(by.class, new dg(ItemType.aB.a(0)));
        this.o.put(ThrownEgg.class, new dg(ItemType.aN.a(0)));
        this.o.put(Snowball.class, new kl());
        this.o.put(ItemEntity.class, new ItemRenderer());
        this.o.put(qw.class, new on());
        this.o.put(FallingTile.class, new gn());
        this.o.put(yl.class, new tb());
        this.o.put(fz.class, new fe());
        this.o.put(lx.class, new pl());
        this.o.put(c.class, new pi());
        this.o.put(EntityBoomerang.class, new RenderBoomerang());
        this.o.put(EntityHookshot.class, new RenderHookshot());
        this.o.put(EntityBomb.class, new RenderBomb());
        this.o.put(EntityBat.class, new LivingEntityRenderer(new ModelBat(), 0.3F));
        this.o.put(EntityRat.class, new LivingEntityRenderer(new ModelRat(), 0.0F));
        this.o.put(EntityCamera.class, new RenderCamera(new ModelCamera(), 0.0F));
        this.o.put(EntityNPC.class, new RenderNPC(new fh()));
        this.o.put(EntityLivingScript.class, new RenderBipedScaledScripted(new fh()));
        for (Iterator<bw> iterator = this.o.values().iterator(); iterator.hasNext(); render.a(this))
            bw render = iterator.next();
    }

    public bw a(Class<Entity> class1) {
        bw render = (bw) this.o.get(class1);
        if (render == null && class1 != Entity.class) {
            render = a(class1.getSuperclass());
            this.o.put(class1, render);
        }
        return render;
    }

    public bw a(Entity entity) {
        return a(entity.getClass());
    }

    public void a(Level world, TextureManager renderengine, TextRenderer fontrenderer, LivingEntity entityliving, GameOptions gamesettings, float f) {
        this.g = world;
        this.e = renderengine;
        this.k = gamesettings;
        this.h = entityliving;
        this.p = fontrenderer;
        if (entityliving.N()) {
            int i = world.a(MathsHelper.b(entityliving.aM), MathsHelper.b(entityliving.aN), MathsHelper.b(entityliving.aO));
            if (i == Tile.T.bn) {
                int j = world.e(MathsHelper.b(entityliving.aM), MathsHelper.b(entityliving.aN), MathsHelper.b(entityliving.aO));
                int k = j & 0x3;
                this.i = (k * 90 + 180);
                this.j = 0.0F;
            }
        } else {
            this.i = entityliving.aU + (entityliving.aS - entityliving.aU) * f;
            this.j = entityliving.aV + (entityliving.aT - entityliving.aV) * f;
        }
        this.l = entityliving.bl + (entityliving.aM - entityliving.bl) * f;
        this.m = entityliving.bm + (entityliving.aN - entityliving.bm) * f;
        this.n = entityliving.bn + (entityliving.aO - entityliving.bn) * f;
    }

    public void a(Entity entity, float f) {
        double d = entity.bl + (entity.aM - entity.bl) * f;
        double d1 = entity.bm + (entity.aN - entity.bm) * f;
        double d2 = entity.bn + (entity.aO - entity.bn) * f;
        float f1 = entity.aU + (entity.aS - entity.aU) * f;
        float f2 = entity.a(f);
        GL11.glColor3f(f2, f2, f2);
        a(entity, d - b, d1 - c, d2 - th.d, f1, f);
    }

    public void a(Entity entity, double d, double d1, double d2, float f, float f1) {
        bw render = a(entity);
        if (render != null) {
            render.a(entity, d, d1, d2, f, f1);
            render.b(entity, d, d1, d2, f, f1);
        }
    }

    public void a(Level world) {
        this.g = world;
    }

    public double a(double d, double d1, double d2) {
        double d3 = d - this.l;
        double d4 = d1 - this.m;
        double d5 = d2 - this.n;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    public TextRenderer a() {
        return this.p;
    }
}