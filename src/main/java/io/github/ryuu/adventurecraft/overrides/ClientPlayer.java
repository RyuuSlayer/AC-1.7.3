package io.github.ryuu.adventurecraft.overrides;

import io.github.ryuu.adventurecraft.gui.GuiMapEditHUD;
import io.github.ryuu.adventurecraft.gui.GuiPalette;
import io.github.ryuu.adventurecraft.gui.GuiScriptStats;
import io.github.ryuu.adventurecraft.gui.GuiWorldConfig;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.InventoryDebug;
import io.github.ryuu.adventurecraft.util.JScriptInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class ClientPlayer extends gs {
    public uo a;

    protected Minecraft b;

    private final cu bN;

    private final cu bO;

    private final cu bP;

    public ClientPlayer(Minecraft minecraft, Level world, gr session, int i) {
        super(world);
        this.bN = new cu();
        this.bO = new cu();
        this.bP = new cu();
        this.b = minecraft;
        this.m = i;
        this.l = world.x.playerName;
        this.aB = 1.0F;
    }

    public void b(double d, double d1, double d2) {
        super.b(d, d1, d2);
    }

    public void f_() {
        super.f_();
        this.aw = this.aB * this.a.a;
        this.ax = this.aB * this.a.b;
        this.az = this.a.d;
    }

    public void o() {
        if (!this.b.I.a(ep.f))
            this.b.u.b(ep.f);
        this.C = this.B;
        if (this.A) {
            if (!this.aI.B && this.aH != null)
                i(null);
            if (this.b.r != null)
                this.b.a(null);
            if (this.B == 0.0F)
                this.b.B.a("portal.trigger", 1.0F, this.bs.nextFloat() * 0.4F + 0.8F);
            this.B += 0.0125F;
            if (this.B >= 1.0F) {
                this.B = 1.0F;
                if (!this.aI.B) ;
            }
            this.A = false;
        } else {
            if (this.B > 0.0F)
                this.B -= 0.05F;
            if (this.B < 0.0F)
                this.B = 0.0F;
        }
        if (this.z > 0)
            this.z--;
        this.a.a(this);
        if (this.a.e && this.bo < 0.2F)
            this.bo = 0.2F;
        super.o();
    }

    public void o_() {
        this.a.a();
    }

    public void a(int i, boolean flag) {
        if (this.aI.script.keyboard.processPlayerKeyPress(i, flag))
            this.a.a(i, flag);
    }

    public void b(nu nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Score", this.g);
    }

    public void a(nu nbttagcompound) {
        super.a(nbttagcompound);
        this.g = nbttagcompound.e("Score");
    }

    public void r() {
        super.r();
        this.b.a(null);
    }

    public void a(yk tileentitysign) {
        this.b.a((Screen) new yc(tileentitysign));
    }

    public void a(lw iinventory) {
        this.b.a((Screen) new hp(this.c, iinventory));
    }

    public void displayGUIPalette() {
        InventoryDebug palette = new InventoryDebug("Palette", 54);
        palette.fillInventory(1);
        this.b.a((Screen) new GuiPalette(this.c, palette));
    }

    public void a(int i, int j, int k) {
        this.b.a((Screen) new oo(this.c, this.aI, i, j, k));
    }

    public void a(sk tileentityfurnace) {
        this.b.a((Screen) new ov(this.c, tileentityfurnace));
    }

    public void a(Dispenser tileentitydispenser) {
        this.b.a((Screen) new gq(this.c, tileentitydispenser));
    }

    public void b(sn entity, int i) {
        this.b.j.a(new em(this.b.f, entity, this, -0.5F));
    }

    public int s() {
        return this.c.f();
    }

    public void a(String s) {
        String orig = s;
        s = s.toLowerCase();
        if (s.equals("/day")) {
            this.aI.setTimeOfDay(0L);
        } else if (s.equals("/night")) {
            this.aI.setTimeOfDay(12000L);
        } else if (s.equals("/fly")) {
            this.isFlying = !this.isFlying;
            this.b.v.a(String.format("Flying: %b", new Object[]{Boolean.valueOf(this.isFlying)}));
        } else if (s.equals("/health")) {
            this.Y = 12;
            this.maxHealth = 12;
            this.numHeartPieces = 0;
        } else if (s.equals("/mapedit")) {
            DebugMode.levelEditing = !DebugMode.levelEditing;
        } else if (s.equals("/removemobs")) {
            for (Entity obj : this.aI.b) {
                sn e = (sn) obj;
                if (e instanceof ls && !(e instanceof gs))
                    e.be = true;
            }
        } else if (s.equals("/noclip")) {
            this.bq = !this.bq;
            if (this.bq)
                this.isFlying = true;
            this.b.v.a(String.format("NoClip: %b", new Object[]{Boolean.valueOf(this.bq)}));
        } else if (s.equals("/togglemelting")) {
            this.aI.x.iceMelts = !this.aI.x.iceMelts;
            this.b.v.a(String.format("Ice Melts: %b", new Object[]{Boolean.valueOf(this.aI.x.iceMelts)}));
        } else if (s.startsWith("/cameraadd")) {
            if (this.b.activeCutsceneCamera != null) {
                float t;
                try {
                    t = Float.valueOf(s.substring(11)).floatValue();
                } catch (StringIndexOutOfBoundsException e) {
                    this.b.v.a("/cameraadd must have a time specified for the point");
                    return;
                } catch (NumberFormatException e) {
                    this.b.v.a("'" + s.substring(11) + "' is not a valid number");
                    return;
                }
                this.b.activeCutsceneCamera.addCameraPoint(t, (float) this.aM, (float) (this.aN - this.bf + 1.6200000047683716D), (float) this.aO, this.aS, this.aT, 2);
                this.b.activeCutsceneCamera.loadCameraEntities();
                this.b.v.a("Point Added");
            } else {
                this.b.v.a("Need to be editing a camera block");
            }
        } else if (s.equals("/cameraclear")) {
            if (this.b.activeCutsceneCamera != null) {
                this.b.activeCutsceneCamera.clearPoints();
                this.b.v.a("Clearing Points");
                this.b.activeCutsceneCamera.loadCameraEntities();
            } else {
                this.b.v.a("Need to be editing a camera block");
            }
        } else if (s.equals("/mobsburn")) {
            this.aI.x.mobsBurn = !this.aI.x.mobsBurn;
            this.b.v.a(String.format("Mobs Burn in Daylight: %b", new Object[]{Boolean.valueOf(this.aI.x.mobsBurn)}));
        } else if (s.equals("/config")) {
            this.b.a((Screen) new GuiWorldConfig(this.aI));
        } else if (s.equals("/test")) {
            this.b.a((Screen) new GuiMapEditHUD(this.aI));
        } else if (s.equals("/renderpaths")) {
            DebugMode.renderPaths = !DebugMode.renderPaths;
            this.b.v.a(String.format("Render Paths: %b", new Object[]{Boolean.valueOf(DebugMode.renderPaths)}));
        } else if (s.equals("/renderfov")) {
            DebugMode.renderFov = !DebugMode.renderFov;
            this.b.v.a(String.format("Render FOV: %b", new Object[]{Boolean.valueOf(DebugMode.renderFov)}));
        } else if (s.equals("/fullbright")) {
            for (int i = 0; i < 16; i++)
                this.aI.t.f[i] = 1.0F;
            this.b.g.updateAllTheRenderers();
        } else if (s.equals("/undo")) {
            this.aI.undo();
        } else if (s.equals("/redo")) {
            this.aI.redo();
        } else if (s.equals("/fluidcollision")) {
            rp.isHittable = !rp.isHittable;
        } else if (s.startsWith("/scriptstats")) {
            GuiScriptStats.showUI();
        } else if (s.startsWith("/scriptstatreset")) {
            for (JScriptInfo info : this.aI.scriptHandler.scripts.values()) {
                info.maxTime = 0L;
                info.count = 0;
                info.totalTime = 0L;
            }
        } else if (s.startsWith("/help")) {
            this.b.v.a("AdventureCraft Commands");
            this.b.v.a("/config - Allows the world to be configed");
            this.b.v.a("/day - Changes time to daytime");
            this.b.v.a("/night - Changes time to nighttime");
            this.b.v.a("/fly - Toggles flying");
            this.b.v.a("/noclip - Toggles no clip");
            this.b.v.a("/health - Sets health to 3 heart containers");
            this.b.v.a("/mapedit - Toggles map editing mode");
            this.b.v.a("/mobsburn - Toggles mobs burning in daylight");
            this.b.v.a("/removemobs - Sets all mobs except the player as dead");
            this.b.v.a("/togglemelting - Toggles ice melting");
        } else {
            String result = this.aI.script.runString(orig);
            if (result != null)
                this.b.v.a(result);
        }
    }

    public boolean t() {
        return (this.a.e && !this.u);
    }

    public void d_(int i) {
        int j = this.Y - i;
        if (j <= 0) {
            this.Y = i;
            if (j < 0)
                this.by = this.E / 2;
        } else {
            this.au = j;
            this.Z = this.Y;
            this.by = this.E;
            b(j);
            this.aa = this.ab = 10;
        }
    }

    public void p_() {
        this.b.a(false, 0);
    }

    public void v() {
    }

    public void b(String s) {
        this.b.v.c(s);
    }

    public void a(vr statbase, int i) {
        if (statbase == null)
            return;
        if (statbase.d()) {
            ny achievement = (ny) statbase;
            if (achievement.c == null || this.b.I.a(achievement.c)) {
                if (!this.b.I.a(achievement))
                    this.b.u.a(achievement);
                this.b.I.a(statbase, i);
            }
        } else {
            this.b.I.a(statbase, i);
        }
    }

    private boolean d(int i, int j, int k) {
        return this.aI.h(i, j, k);
    }

    protected boolean c(double d, double d1, double d2) {
        int i = in.b(d);
        int j = in.b(d1);
        int k = in.b(d2);
        double d3 = d - i;
        double d4 = d2 - k;
        if (d(i, j, k) || d(i, j + 1, k)) {
            boolean flag = (!d(i - 1, j, k) && !d(i - 1, j + 1, k));
            boolean flag1 = (!d(i + 1, j, k) && !d(i + 1, j + 1, k));
            boolean flag2 = (!d(i, j, k - 1) && !d(i, j + 1, k - 1));
            boolean flag3 = (!d(i, j, k + 1) && !d(i, j + 1, k + 1));
            byte byte0 = -1;
            double d5 = 9999.0D;
            if (flag && d3 < d5) {
                d5 = d3;
                byte0 = 0;
            }
            if (flag1 && 1.0D - d3 < d5) {
                d5 = 1.0D - d3;
                byte0 = 1;
            }
            if (flag2 && d4 < d5) {
                d5 = d4;
                byte0 = 4;
            }
            if (flag3 && 1.0D - d4 < d5) {
                double d6 = 1.0D - d4;
                byte0 = 5;
            }
            float f = 0.1F;
            if (byte0 == 0)
                this.aP = -f;
            if (byte0 == 1)
                this.aP = f;
            if (byte0 == 4)
                this.aR = -f;
            if (byte0 == 5)
                this.aR = f;
        }
        return false;
    }
}