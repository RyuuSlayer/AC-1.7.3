package io.github.ryuu.adventurecraft.mixin.entity.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.achievement.Achievement;
import net.minecraft.achievement.Achievements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.PlayerKeypressManager;
import net.minecraft.client.gui.screen.EditSignScreen;
import net.minecraft.client.gui.screen.container.CraftingScreen;
import net.minecraft.client.gui.screen.container.DispenserScreen;
import net.minecraft.client.gui.screen.container.DoubleChestScreen;
import net.minecraft.client.gui.screen.container.FurnaceScreen;
import net.minecraft.client.particle.EntityCollisionParticle;
import net.minecraft.client.util.Session;
import net.minecraft.client.util.Smoother;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FurnaceEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.inventory.Inventory;
import net.minecraft.level.Level;
import net.minecraft.stat.Stat;
import net.minecraft.tile.FluidTile;
import net.minecraft.tile.entity.Dispenser;
import net.minecraft.tile.entity.Sign;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.MathsHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPlayer.class)
public class MixinClientPlayer extends Player {

    @Shadow()
    public PlayerKeypressManager keypressManager;

    protected Minecraft minecraft;

    private Smoother field_163 = new Smoother();

    private Smoother field_164 = new Smoother();

    private Smoother field_165 = new Smoother();

    public MixinClientPlayer(Minecraft minecraft, Level level, Session session, int dimensionId) {
        super(level);
        this.minecraft = minecraft;
        this.dimensionId = dimensionId;
        this.name = level.properties.playerName;
        this.movementSpeed = 1.0f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void move(double d, double d1, double d2) {
        super.move(d, d1, d2);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void tickHandSwing() {
        super.tickHandSwing();
        this.perpendicularMovement = this.movementSpeed * this.keypressManager.perpendicularMovement;
        this.parallelMovement = this.movementSpeed * this.keypressManager.parallelMovement;
        this.jumping = this.keypressManager.jump;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void updateDespawnCounter() {
        if (!this.minecraft.statManager.hasAchievement(Achievements.OPEN_INVENTORY)) {
            this.minecraft.toastManager.set(Achievements.OPEN_INVENTORY);
        }
        this.field_505 = this.field_504;
        if (this.field_512) {
            if (!this.level.isClient && this.vehicle != null) {
                this.startRiding(null);
            }
            if (this.minecraft.currentScreen != null) {
                this.minecraft.openScreen(null);
            }
            if (this.field_504 == 0.0f) {
                this.minecraft.soundHelper.playSound("portal.trigger", 1.0f, this.rand.nextFloat() * 0.4f + 0.8f);
            }
            this.field_504 += 0.0125f;
            if (this.field_504 >= 1.0f) {
                this.field_504 = 1.0f;
                if (!this.level.isClient) {
                }
            }
            this.field_512 = false;
        } else {
            if (this.field_504 > 0.0f) {
                this.field_504 -= 0.05f;
            }
            if (this.field_504 < 0.0f) {
                this.field_504 = 0.0f;
            }
        }
        if (this.field_511 > 0) {
            --this.field_511;
        }
        this.keypressManager.updatePlayer(this);
        if (this.keypressManager.sneak && this.field_1640 < 0.2f) {
            this.field_1640 = 0.2f;
        }
        super.updateDespawnCounter();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_136(int i, boolean flag) {
        if (this.level.script.keyboard.processPlayerKeyPress(i, flag)) {
            this.keypressManager.onKeyPressed(i, flag);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.put("Score", this.score);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.score = tag.getInt("Score");
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void openSignScreen(Sign tileentitysign) {
        this.minecraft.openScreen(new EditSignScreen(tileentitysign));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void openChestScreen(Inventory iinventory) {
        this.minecraft.openScreen(new DoubleChestScreen(this.inventory, iinventory));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void displayGUIPalette() {
        InventoryDebug palette = new InventoryDebug("Palette", 54);
        palette.fillInventory(1);
        this.minecraft.openScreen(new GuiPalette((Inventory) this.inventory, palette));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void openCraftingScreen(int i, int j, int k) {
        this.minecraft.openScreen(new CraftingScreen(this.inventory, this.level, i, j, k));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void openFurnaceScreen(FurnaceEntity tileentityfurnace) {
        this.minecraft.openScreen(new FurnaceScreen(this.inventory, tileentityfurnace));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void openDispenserScreen(Dispenser tileentitydispenser) {
        this.minecraft.openScreen(new DispenserScreen(this.inventory, tileentitydispenser));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void sendChatMessage(String s) {
        String orig = s;
        if ((s = s.toLowerCase()).equals((Object) "/day")) {
            this.level.setTimeOfDay(0L);
        } else if (s.equals((Object) "/night")) {
            this.level.setTimeOfDay(12000L);
        } else if (s.equals((Object) "/fly")) {
            this.isFlying = !this.isFlying;
            this.minecraft.overlay.addChatMessage(String.format((String) "Flying: %b", (Object[]) new Object[] { this.isFlying }));
        } else if (s.equals((Object) "/health")) {
            this.health = 12;
            this.maxHealth = 12;
            this.numHeartPieces = 0;
        } else if (s.equals((Object) "/mapedit")) {
            DebugMode.levelEditing = !DebugMode.levelEditing;
        } else if (s.equals((Object) "/removemobs")) {
            for (Entity obj : this.level.entities) {
                Entity e = obj;
                if (!(e instanceof LivingEntity) || e instanceof Player)
                    continue;
                e.removed = true;
            }
        } else if (s.equals((Object) "/noclip")) {
            boolean bl = this.field_1642 = !this.field_1642;
            if (this.field_1642) {
                this.isFlying = true;
            }
            this.minecraft.overlay.addChatMessage(String.format((String) "NoClip: %b", (Object[]) new Object[] { this.field_1642 }));
        } else if (s.equals((Object) "/togglemelting")) {
            this.level.properties.iceMelts = !this.level.properties.iceMelts;
            this.minecraft.overlay.addChatMessage(String.format((String) "Ice Melts: %b", (Object[]) new Object[] { this.level.properties.iceMelts }));
        } else if (s.startsWith("/cameraadd")) {
            if (this.minecraft.activeCutsceneCamera != null) {
                float t;
                try {
                    t = Float.valueOf((String) s.substring(11)).floatValue();
                } catch (StringIndexOutOfBoundsException e) {
                    this.minecraft.overlay.addChatMessage("/cameraadd must have a time specified for the point");
                    return;
                } catch (NumberFormatException e) {
                    this.minecraft.overlay.addChatMessage("'" + s.substring(11) + "' is not a valid number");
                    return;
                }
                this.minecraft.activeCutsceneCamera.addCameraPoint(t, (float) this.x, (float) (this.y - (double) this.standingEyeHeight + (double) 1.62f), (float) this.z, this.yaw, this.pitch, 2);
                this.minecraft.activeCutsceneCamera.loadCameraEntities();
                this.minecraft.overlay.addChatMessage("Point Added");
            } else {
                this.minecraft.overlay.addChatMessage("Need to be editing a camera block");
            }
        } else if (s.equals((Object) "/cameraclear")) {
            if (this.minecraft.activeCutsceneCamera != null) {
                this.minecraft.activeCutsceneCamera.clearPoints();
                this.minecraft.overlay.addChatMessage("Clearing Points");
                this.minecraft.activeCutsceneCamera.loadCameraEntities();
            } else {
                this.minecraft.overlay.addChatMessage("Need to be editing a camera block");
            }
        } else if (s.equals((Object) "/mobsburn")) {
            this.level.properties.mobsBurn = !this.level.properties.mobsBurn;
            this.minecraft.overlay.addChatMessage(String.format((String) "Mobs Burn in Daylight: %b", (Object[]) new Object[] { this.level.properties.mobsBurn }));
        } else if (s.equals((Object) "/config")) {
            this.minecraft.openScreen(new GuiWorldConfig(this.level));
        } else if (s.equals((Object) "/test")) {
            this.minecraft.openScreen(new GuiMapEditHUD(this.level));
        } else if (s.equals((Object) "/renderpaths")) {
            DebugMode.renderPaths = !DebugMode.renderPaths;
            this.minecraft.overlay.addChatMessage(String.format((String) "Render Paths: %b", (Object[]) new Object[] { DebugMode.renderPaths }));
        } else if (s.equals((Object) "/renderfov")) {
            DebugMode.renderFov = !DebugMode.renderFov;
            this.minecraft.overlay.addChatMessage(String.format((String) "Render FOV: %b", (Object[]) new Object[] { DebugMode.renderFov }));
        } else if (s.equals((Object) "/fullbright")) {
            for (int i = 0; i < 16; ++i) {
                this.level.dimension.field_2178[i] = 1.0f;
            }
            this.minecraft.worldRenderer.updateAllTheRenderers();
        } else if (s.equals((Object) "/undo")) {
            this.level.undo();
        } else if (s.equals((Object) "/redo")) {
            this.level.redo();
        } else if (s.equals((Object) "/fluidcollision")) {
            FluidTile.isHittable = !FluidTile.isHittable;
        } else if (s.startsWith("/scriptstats")) {
            GuiScriptStats.showUI();
        } else if (s.startsWith("/scriptstatreset")) {
            for (JScriptInfo info : this.level.scriptHandler.scripts.values()) {
                info.maxTime = 0L;
                info.count = 0;
                info.totalTime = 0L;
            }
        } else if (s.startsWith("/help")) {
            this.minecraft.overlay.addChatMessage("AdventureCraft Commands");
            this.minecraft.overlay.addChatMessage("/config - Allows the world to be configed");
            this.minecraft.overlay.addChatMessage("/day - Changes time to daytime");
            this.minecraft.overlay.addChatMessage("/night - Changes time to nighttime");
            this.minecraft.overlay.addChatMessage("/fly - Toggles flying");
            this.minecraft.overlay.addChatMessage("/noclip - Toggles no clip");
            this.minecraft.overlay.addChatMessage("/health - Sets health to 3 heart containers");
            this.minecraft.overlay.addChatMessage("/mapedit - Toggles map editing mode");
            this.minecraft.overlay.addChatMessage("/mobsburn - Toggles mobs burning in daylight");
            this.minecraft.overlay.addChatMessage("/removemobs - Sets all mobs except the player as dead");
            this.minecraft.overlay.addChatMessage("/togglemelting - Toggles ice melting");
        } else {
            String result = this.level.script.runString(orig);
            if (result != null) {
                this.minecraft.overlay.addChatMessage(result);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void updateHealth(int i) {
        int j = this.health - i;
        if (j <= 0) {
            this.health = i;
            if (j < 0) {
                this.field_1613 = this.field_1009 / 2;
            }
        } else {
            this.field_1058 = j;
            this.field_1037 = this.health;
            this.field_1613 = this.field_1009;
            this.applyDamage(j);
            this.field_1039 = 10;
            this.hurtTime = 10;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void sendTranslatedMessage(String key) {
        this.minecraft.overlay.method_1953(key);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void increaseStat(Stat state, int amount) {
        if (state == null) {
            return;
        }
        if (state.isAchievement()) {
            Achievement achievement = (Achievement) state;
            if (achievement.parent == null || this.minecraft.statManager.hasAchievement(achievement.parent)) {
                if (!this.minecraft.statManager.hasAchievement(achievement)) {
                    this.minecraft.toastManager.setWithoutDescription(achievement);
                }
                this.minecraft.statManager.incrementStat(state, amount);
            }
        } else {
            this.minecraft.statManager.incrementStat(state, amount);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private boolean method_138(int i, int j, int k) {
        return this.level.canSuffocate(i, j, k);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected boolean method_1372(double d, double d1, double d2) {
        int i = MathsHelper.floor(d);
        int j = MathsHelper.floor(d1);
        int k = MathsHelper.floor(d2);
        double d3 = d - (double) i;
        double d4 = d2 - (double) k;
        if (this.method_138(i, j, k) || this.method_138(i, j + 1, k)) {
            boolean flag = !this.method_138(i - 1, j, k) && !this.method_138(i - 1, j + 1, k);
            boolean flag1 = !this.method_138(i + 1, j, k) && !this.method_138(i + 1, j + 1, k);
            boolean flag2 = !this.method_138(i, j, k - 1) && !this.method_138(i, j + 1, k - 1);
            boolean flag3 = !this.method_138(i, j, k + 1) && !this.method_138(i, j + 1, k + 1);
            int byte0 = -1;
            double d5 = 9999.0;
            if (flag && d3 < d5) {
                d5 = d3;
                byte0 = 0;
            }
            if (flag1 && 1.0 - d3 < d5) {
                d5 = 1.0 - d3;
                byte0 = 1;
            }
            if (flag2 && d4 < d5) {
                d5 = d4;
                byte0 = 4;
            }
            if (flag3 && 1.0 - d4 < d5) {
                double d6 = 1.0 - d4;
                byte0 = 5;
            }
            float f = 0.1f;
            if (byte0 == 0) {
                this.velocityX = -f;
            }
            if (byte0 == 1) {
                this.velocityX = f;
            }
            if (byte0 == 4) {
                this.velocityZ = -f;
            }
            if (byte0 == 5) {
                this.velocityZ = f;
            }
        }
        return false;
    }
}
