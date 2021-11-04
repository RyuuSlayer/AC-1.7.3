package io.github.ryuu.adventurecraft.mixin;

import io.github.ryuu.adventurecraft.gui.GuiMapEditHUD;
import io.github.ryuu.adventurecraft.gui.GuiPalette;
import io.github.ryuu.adventurecraft.gui.GuiScriptStats;
import io.github.ryuu.adventurecraft.gui.GuiWorldConfig;
import io.github.ryuu.adventurecraft.mixin.Level;
import io.github.ryuu.adventurecraft.overrides.*;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.InventoryDebug;
import io.github.ryuu.adventurecraft.util.JScriptInfo;
import net.minecraft.achievement.Achievement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.Session;
import net.minecraft.client.util.Smoother;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ClientPlayer;
import net.minecraft.level.Level;
import net.minecraft.stat.Stat;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientPlayer.class)
public class MixinClientPlayer extends Player {
    // public PlayerKeypressManager a;
    // protected Minecraft b;
    // private Smoother field_163 = new Smoother();
    // private Smoother field_164 = new Smoother();
    // private Smoother field_165 = new Smoother();

    public MixinClientPlayer(Minecraft minecraft, Level world, Session session, int i) {
        super(world);
        this.bN = new Smoother();
        this.bO = new Smoother();
        this.bP = new Smoother();
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

    // public void method_140() {
    //     this.keypressManager.resetKeys();
    // }

    // public void method_136(int i, boolean flag) {
    //     this.keypressManager.onKeyPressed(i, (boolean)flag);
    // }

    // public void writeCustomDataToTag(CompoundTag arg) {
    //     super.writeCustomDataToTag(arg);
    //     arg.put("Score", this.score);
    // }

    // public void readCustomDataFromTag(CompoundTag arg) {
    //     super.readCustomDataFromTag(arg);
    //     this.score = arg.getInt("Score");
    // }

    // public void closeContainer() {
    //     super.closeContainer();
    //     this.minecraft.openScreen((net.minecraft.client.gui.Screen)null);
    // }

    // public void openSignScreen(Sign arg) {
    //     this.minecraft.openScreen(new EditSignScreen(arg));
    // }
    // public void openChestScreen(Inventory arg) {
    //     this.minecraft.openScreen(new DoubleChestScreen(this.inventory, arg));
    // }

    //TODO: Mixin AC class.

    public void displayGUIPalette() {
        InventoryDebug palette = new InventoryDebug("Palette", 54);
        palette.fillInventory(1);
        this.b.a((Screen) new GuiPalette(this.c, palette));
    }

    // public void openCraftingScreen(int i, int j, int k)
    //     this.minecraft.openScreen(new CraftingScreen(this.inventory, this.level, i, j, k))
    // }

    //  public void openFurnaceScreen(FurnaceEntity arg) {
    //      this.minecraft.openScreen(new FurnaceScreen(this.inventory, arg));
    //  }

    //  public void openDispenserScreen(Dispenser arg) {
    //      this.minecraft.openScreen(new DispenserScreen(this.inventory, arg));
    //  }

    //  public void onEntityCollision(Entity arg, int i) {
    //      this.minecraft.particleManager.addParticle(new EntityCollisionParticle(this.minecraft.level, arg, this, -0.5F));
    //  }

    // public int method_141() {
    //     return this.inventory.method_687();
    // }

    //TODO: These two were missing in the AC class but are in Vanilla.

    ////////////////////////////////
    public void sendChatMessage(String string) {
    }

    public boolean method_1373() {
        return this.keypressManager.sneak && !this.sleeping;
    }
    ////////////////////////////////

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
                Entity e = obj;
                if (e instanceof LivingEntity && !(e instanceof Player))
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

    // public boolean method_1373() {
    //     return this.keypressManager.sneak && !this.sleeping;
    // }

    // public void updateHealth(int i) {
    //     int var2 = this.health - i;
    //     if (var2 <= 0) {
    //         this.health = i;
    //         if (var2 < 0) {
    //             this.field_1613 = this.field_1009 / 2;
    //         }
    //     } else {
    //         this.field_1058 = var2;
    //         this.field_1037 = this.health;
    //         this.field_1613 = this.field_1009;
    //         this.applyDamage(var2);
    //         this.hurtTime = this.field_1039 = 10;
    //     }
    // }

    // public void respawn() {
    //     this.minecraft.respawn(false, 0);
    // }

    // public void method_494() {
    // }

    // public void sendTranslatedMessage(String string) {
    //     this.minecraft.overlay.method_1953(string);
    // }

    //TODO: This one seems different than Vanilla, not sure if its because of decompilation or AC changes.

    public void a(Stat statbase, int i) {
        if (statbase == null)
            return;
        if (statbase.d()) {
            Achievement achievement = (Achievement) statbase;
            if (achievement.c == null || this.b.I.a(achievement.c)) {
                if (!this.b.I.a(achievement))
                    this.b.u.a(achievement);
                this.b.I.a(statbase, i);
            }
        } else {
            this.b.I.a(statbase, i);
        }
    }

    // private boolean method_138(int i, int j, int k) {
    //     return this.level.canSuffocate(i, j, k);
    // }

    // protected boolean method_1372(double d, double d1, double d2) {
    //     int var7 = MathsHelper.floor(d);
    //     int var8 = MathsHelper.floor(d1);
    //     int var9 = MathsHelper.floor(d2);
    //     double var10 = d - (double)var7;
    //     double var12 = d2 - (double)var9;
    //     if (this.method_138(var7, var8, var9) || this.method_138(var7, var8 + 1, var9)) {
    //         int var14 = !this.method_138(var7 - 1, var8, var9) && !this.method_138(var7 - 1, var8 + 1, var9) ? 1 : 0;
    //         int var15 = !this.method_138(var7 + 1, var8, var9) && !this.method_138(var7 + 1, var8 + 1, var9) ? 1 : 0;
    //         int var16 = !this.method_138(var7, var8, var9 - 1) && !this.method_138(var7, var8 + 1, var9 - 1) ? 1 : 0;
    //         int var17 = !this.method_138(var7, var8, var9 + 1) && !this.method_138(var7, var8 + 1, var9 + 1) ? 1 : 0;
    //         int var18 = -1;
    //         double var19 = 9999.0D;
    //         if (var14 != 0 && var10 < var19) {
    //             var19 = var10;
    //             var18 = 0;
    //         }
    //
    //         if (var15 != 0 && 1.0D - var10 < var19) {
    //             var19 = 1.0D - var10;
    //             var18 = 1;
    //         }
    //
    //         if (var16 != 0 && var12 < var19) {
    //             var19 = var12;
    //             var18 = 4;
    //         }
    //
    //         if (var17 != 0 && 1.0D - var12 < var19) {
    //             var19 = 1.0D - var12;
    //             var18 = 5;
    //         }
    //
    //         float var21 = 0.1F;
    //         if (var18 == 0) {
    //             this.velocityX = (double)(-var21);
    //         }
    //
    //         if (var18 == 1) {
    //             this.velocityX = (double)var21;
    //         }
    //
    //         if (var18 == 4) {
    //             this.velocityZ = (double)(-var21);
    //         }
    //
    //         if (var18 == 5) {
    //             this.velocityZ = (double)var21;
    //         }
    //     }
    //
    //     return false;
    // }
}