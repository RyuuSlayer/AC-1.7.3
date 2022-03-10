package io.github.ryuu.adventurecraft.mixin.entity.player;

import io.github.ryuu.adventurecraft.extensions.client.ExMinecraft;
import io.github.ryuu.adventurecraft.extensions.client.render.ExWorldRenderer;
import io.github.ryuu.adventurecraft.extensions.entity.ExEntity;
import io.github.ryuu.adventurecraft.extensions.entity.ExLivingEntity;
import io.github.ryuu.adventurecraft.extensions.entity.player.ExClientPlayer;
import io.github.ryuu.adventurecraft.extensions.entity.player.ExPlayer;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.extensions.level.ExLevelProperties;
import io.github.ryuu.adventurecraft.gui.GuiMapEditHUD;
import io.github.ryuu.adventurecraft.gui.GuiPalette;
import io.github.ryuu.adventurecraft.gui.GuiScriptStats;
import io.github.ryuu.adventurecraft.gui.GuiWorldConfig;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.InventoryDebug;
import io.github.ryuu.adventurecraft.util.JScriptInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.PlayerKeypressManager;
import net.minecraft.client.util.Session;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ClientPlayer;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayer.class)
public abstract class MixinClientPlayer extends Player implements ExClientPlayer {

    @Shadow
    public PlayerKeypressManager keypressManager;

    @Shadow
    protected Minecraft minecraft;

    public MixinClientPlayer(Level level) {
        super(level);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(Minecraft arg, Level arg1, Session i, int par4, CallbackInfo ci) {
        this.skinUrl = null;
        this.name = ((ExLevelProperties) level.getProperties()).getPlayerName();
        this.movementSpeed = 1.0f;
    }

    @Redirect(method = "tickHandSwing", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/PlayerKeypressManager;perpendicularMovement:F"))
    private float multiplyPerpendicularMovement(PlayerKeypressManager instance) {
        return this.movementSpeed * instance.perpendicularMovement;
    }

    @Redirect(method = "tickHandSwing", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/PlayerKeypressManager;parallelMovement:F"))
    private float multiplyParallelMovement(PlayerKeypressManager instance) {
        return this.movementSpeed * instance.parallelMovement;
    }

    @Redirect(method = "updateDespawnCounter", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/ClientPlayer;method_1372(DDD)Z"))
    private boolean disableMethod_1372(ClientPlayer instance, double d1, double d2, double v) {
        return false;
    }

    @Redirect(method = "updateDespawnCounter", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/level/Level;isClient:Z",
            ordinal = 1))
    private boolean disablePortalTravel(Level instance) {
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_136(int i, boolean flag) {
        if (((ExLevel) this.level).getScript().keyboard.processPlayerKeyPress(i, flag)) {
            this.keypressManager.onKeyPressed(i, flag);
        }
    }

    @Override
    public void displayGUIPalette() {
        InventoryDebug palette = new InventoryDebug("Palette", 54);
        palette.fillInventory(1);
        this.minecraft.openScreen(new GuiPalette(this.inventory, palette));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void sendChatMessage(String s) {
        String orig = s;
        ExMinecraft exMinecraft = (ExMinecraft) this.minecraft;
        if ((s = s.toLowerCase()).equals("/day")) {
            ((ExLevel) this.level).setTimeOfDay(0L);
        } else if (s.equals("/night")) {
            ((ExLevel) this.level).setTimeOfDay(12000L);
        } else if (s.equals("/fly")) {
            ((ExEntity) this).setFlying(!((ExEntity) this).isFlying());
            this.minecraft.overlay.addChatMessage(String.format("Flying: %b", ((ExEntity) this).isFlying()));
        } else if (s.equals("/health")) {
            this.health = 12;
            ((ExLivingEntity) this).setMaxHealth(12);
            ((ExPlayer) this).setHeartPieces(0);
        } else if (s.equals("/mapedit")) {
            DebugMode.levelEditing = !DebugMode.levelEditing;
        } else if (s.equals("/removemobs")) {
            for (Object obj : this.level.entities) {
                Entity e = (Entity) obj;
                if (!(e instanceof LivingEntity) || e instanceof Player) continue;
                e.removed = true;
            }
        } else if (s.equals("/noclip")) {
            this.field_1642 = !this.field_1642;
            if (this.field_1642) {
                ((ExEntity) this).setFlying(true);
            }
            this.minecraft.overlay.addChatMessage(String.format("NoClip: %b", this.field_1642));
        } else if (s.equals("/togglemelting")) {
            ExLevelProperties exProps = (ExLevelProperties) this.level.getProperties();
            exProps.setIceMelts(!exProps.getIceMelts());
            this.minecraft.overlay.addChatMessage(String.format("Ice Melts: %b", exProps.getIceMelts()));
        } else if (s.startsWith("/cameraadd")) {
            if (exMinecraft.getActiveCutsceneCamera() != null) {
                float t;
                try {
                    t = Float.parseFloat(s.substring(11));
                } catch (StringIndexOutOfBoundsException e) {
                    this.minecraft.overlay.addChatMessage("/cameraadd must have a time specified for the point");
                    return;
                } catch (NumberFormatException e) {
                    this.minecraft.overlay.addChatMessage("'" + s.substring(11) + "' is not a valid number");
                    return;
                }
                exMinecraft.getActiveCutsceneCamera().addCameraPoint(t, (float) this.x, (float) (this.y - this.standingEyeHeight + 1.62f), (float) this.z, this.yaw, this.pitch, 2);
                exMinecraft.getActiveCutsceneCamera().loadCameraEntities();
                this.minecraft.overlay.addChatMessage("Point Added");
            } else {
                this.minecraft.overlay.addChatMessage("Need to be editing a camera block");
            }
        } else if (s.equals("/cameraclear")) {
            if (exMinecraft.getActiveCutsceneCamera() != null) {
                exMinecraft.getActiveCutsceneCamera().clearPoints();
                this.minecraft.overlay.addChatMessage("Clearing Points");
                exMinecraft.getActiveCutsceneCamera().loadCameraEntities();
            } else {
                this.minecraft.overlay.addChatMessage("Need to be editing a camera block");
            }
        } else if (s.equals("/mobsburn")) {
            ExLevelProperties exProps = (ExLevelProperties) this.level.getProperties();
            exProps.setMobsBurn(!exProps.getMobsBurn());
            this.minecraft.overlay.addChatMessage(String.format("Mobs Burn in Daylight: %b", exProps.getMobsBurn()));
        } else if (s.equals("/config")) {
            this.minecraft.openScreen(new GuiWorldConfig(this.level));
        } else if (s.equals("/test")) {
            this.minecraft.openScreen(new GuiMapEditHUD(this.level));
        } else if (s.equals("/renderpaths")) {
            DebugMode.renderPaths = !DebugMode.renderPaths;
            this.minecraft.overlay.addChatMessage(String.format("Render Paths: %b", DebugMode.renderPaths));
        } else if (s.equals("/renderfov")) {
            DebugMode.renderFov = !DebugMode.renderFov;
            this.minecraft.overlay.addChatMessage(String.format("Render FOV: %b", DebugMode.renderFov));
        } else if (s.equals("/fullbright")) {
            for (int i = 0; i < 16; ++i) {
                this.level.dimension.brightnesses[i] = 1.0f;
            }
            ((ExWorldRenderer) this.minecraft.worldRenderer).updateAllTheRenderers();
        } else if (s.equals("/undo")) {
            ((ExLevel) this.level).undo();
        } else if (s.equals("/redo")) {
            ((ExLevel) this.level).redo();
        } else if (s.equals("/fluidcollision")) {
            DebugMode.isFluidHittable = !DebugMode.isFluidHittable;
        } else if (s.startsWith("/scriptstats")) {
            GuiScriptStats.showUI();
        } else if (s.startsWith("/scriptstatreset")) {
            for (JScriptInfo info : ((ExLevel) this.level).getScriptHandler().scripts.values()) {
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
            String result = ((ExLevel) this.level).getScript().runString(orig);
            if (result != null) {
                this.minecraft.overlay.addChatMessage(result);
            }
        }
    }
}
