package io.github.ryuu.adventurecraft.mixin.client;

import io.github.ryuu.adventurecraft.Main;
import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.extensions.client.ExMinecraft;
import io.github.ryuu.adventurecraft.extensions.client.gui.ExScreen;
import io.github.ryuu.adventurecraft.extensions.client.render.ExGameRenderer;
import io.github.ryuu.adventurecraft.extensions.client.render.ExWorldRenderer;
import io.github.ryuu.adventurecraft.extensions.entity.player.ExClientPlayer;
import io.github.ryuu.adventurecraft.extensions.entity.player.ExPlayer;
import io.github.ryuu.adventurecraft.extensions.entity.player.ExPlayerInventory;
import io.github.ryuu.adventurecraft.extensions.items.ExItemInstance;
import io.github.ryuu.adventurecraft.extensions.items.ExItemType;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import io.github.ryuu.adventurecraft.gui.GuiMapSelect;
import io.github.ryuu.adventurecraft.gui.GuiStore;
import io.github.ryuu.adventurecraft.mixin.entity.AccessEntity;
import io.github.ryuu.adventurecraft.scripting.Script;
import io.github.ryuu.adventurecraft.scripting.ScriptEntity;
import io.github.ryuu.adventurecraft.scripting.ScriptItem;
import io.github.ryuu.adventurecraft.scripting.ScriptVec3;
import io.github.ryuu.adventurecraft.util.CutsceneCamera;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.MapList;
import io.github.ryuu.adventurecraft.util.TextureFanFX;
import net.minecraft.class_537;
import net.minecraft.client.*;
import net.minecraft.client.gui.Overlay;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.SleepingChatScreen;
import net.minecraft.client.gui.screen.container.PlayerInventoryScreen;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.render.*;
import net.minecraft.client.sound.SoundHelper;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.ResourceDownloadThread;
import net.minecraft.client.util.ScreenScaler;
import net.minecraft.client.util.Session;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ClientPlayer;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.level.chunk.ClientChunkCache;
import net.minecraft.level.dimension.DimensionData;
import net.minecraft.level.source.LevelSource;
import net.minecraft.level.storage.LevelStorage;
import net.minecraft.stat.StatManager;
import net.minecraft.stat.Stats;
import net.minecraft.tile.Tile;
import net.minecraft.util.Vec3i;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitType;
import net.minecraft.util.maths.MathsHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.ScriptableObject;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.awt.*;
import java.io.*;

@Mixin(value = Minecraft.class, priority = 999)
public abstract class MixinMinecraft implements Runnable, AccessMinecraft, ExMinecraft {

    @Shadow
    public static int field_2771;

    @Shadow
    private Timer tickTimer;
    @Shadow
    public ClientInteractionManager interactionManager;
    @Shadow
    public int actualWidth;
    @Shadow
    public int actualHeight;
    @Shadow
    public Level level;
    @Shadow
    public WorldRenderer worldRenderer;
    @Shadow
    public ClientPlayer player;
    @Shadow
    public LivingEntity cameraEntity;
    @Shadow
    public ParticleManager particleManager;
    @Shadow
    public volatile boolean paused;
    @Shadow
    public TextureManager textureManager;
    @Shadow
    public TextRenderer textRenderer;
    @Shadow
    public Screen currentScreen;
    @Shadow
    public GameRenderer gameRenderer;
    @Shadow
    private ResourceDownloadThread resourceDownloadThread;
    @Shadow
    public Overlay overlay;
    @Shadow
    public HitResult hitResult;
    @Shadow
    public GameOptions options;
    @Shadow
    public SoundHelper soundHelper;
    @Shadow
    private File gameDir;
    @Shadow
    private LevelStorage levelStorage;
    @Shadow
    public StatManager statManager;
    @Shadow
    public boolean field_2778;
    @Shadow
    long lastTickTime;
    @Shadow
    private int field_2786;
    @Shadow
    private int attackCooldown;
    @Shadow
    private int field_2798;
    @Shadow
    private int field_2799;

    public MapList mapList;
    public int nextFrameTime;
    public long prevFrameTimeForAvg;
    public long[] tFrameTimes;
    public CutsceneCamera cutsceneCamera;
    public CutsceneCamera activeCutsceneCamera;
    public boolean cameraActive;
    public boolean cameraPause = true;
    public LivingEntity cutsceneCameraEntity;
    public GuiStore storeGUI;
    private ItemInstance lastItemUsed;
    private Entity lastEntityHit;
    private ScriptVec3 lastBlockHit;
    private int gcTime = 0;
    private int rightMouseTicksRan;

    @Shadow
    public abstract boolean isConnectedToServer();

    @Shadow
    public abstract void lockCursor();

    @Shadow
    public abstract void method_2135();

    @Shadow
    public abstract void notifyStatus(Level arg, String string);

    @Shadow
    public abstract void openScreen(Screen arg);

    @Shadow
    public abstract void setLevel(Level arg);

    @Shadow
    public abstract void switchDimension();

    @Shadow
    public abstract void toggleFullscreen();

    @Shadow
    protected abstract void method_2103();

    @Shadow
    protected abstract void method_2105();

    @Shadow
    protected abstract void method_2110(int i, boolean flag);

    @Shadow
    protected abstract void method_2130(String string);

    @Shadow
    protected abstract void convertWorldFormat(String string, String string1);

    @Shadow
    protected abstract void startLoginThread();

    @Inject(at = @At("TAIL"), method = "<init>")
    private void constructor(Component component, Canvas canvas, MinecraftApplet minecraftApplet, int i, int j, boolean flag, CallbackInfo info) {
        this.tFrameTimes = new long[60];
        this.cutsceneCamera = new CutsceneCamera();
        this.storeGUI = new GuiStore();
    }

    //@Redirect(method = "start(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", at = @At(
    //        value = "NEW",
    //        target = "Lnet/minecraft/client/util/Session;<init>(Ljava/lang/String;Ljava/lang/String;)V",
    //        remap = false))
    //private static Session redirectSessionPlayerName(String username, String s) {
    //    if (s == null)
    //        return new Session("ACPlayer", "");
    //    return new Session(username, s);
    //}

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite(remap = false)
    public static void main(String[] args) {
        String s = "ACPlayer";
        if (args.length > 0) {
            s = args[0];
        }
        String s1 = "-";
        if (args.length > 1) {
            s1 = args[1];
        }
        Minecraft.start(s, s1);
    }

    @Inject(method = "init", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/texture/TextureManager;add(Lnet/minecraft/client/render/TextureBinder;)V",
            shift = At.Shift.AFTER,
            ordinal = 0))
    private void initFanTextureBinder(CallbackInfo ci) {
        this.textureManager.add(new TextureFanFX());
    }

    @Inject(method = "init", at = @At(
            value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/client/Minecraft;getGameDirectory()Ljava/io/File;",
            shift = At.Shift.AFTER))
    private void initMapList(CallbackInfo ci) {
        this.mapList = new MapList(this.gameDir);
    }

    @Inject(method = "init", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/util/ResourceDownloadThread;start()V",
            shift = At.Shift.AFTER))
    private void initResourceDownloadThread(CallbackInfo ci) {
        this.resourceDownloadThread.method_107();
    }

    @Inject(method = "run", at = @At(
            value = "INVOKE_ASSIGN",
            target = "Ljava/lang/System;currentTimeMillis()J",
            shift = At.Shift.BEFORE,
            ordinal = 0),
            remap = false)
    private void initTerrainTextureAndJsContext(CallbackInfo ci) {
        this.textureManager.getTextureId("/assets/terrain.png");
        this.textureManager.getTextureId("/assets/terrain2.png");
        this.textureManager.getTextureId("/assets/terrain3.png");
        ContextFactory.initGlobal(new ContextFactory());
    }

    @Redirect(method = "run", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/sound/SoundHelper;setSoundPosition(Lnet/minecraft/entity/LivingEntity;F)V"))
    private void redirectSetSoundPosition(SoundHelper instance, LivingEntity f, float v) {
        if (!this.cameraActive) {
            this.soundHelper.setSoundPosition(this.player, this.tickTimer.field_2370);
        } else {
            this.soundHelper.setSoundPosition(this.cutsceneCameraEntity, this.tickTimer.field_2370);
        }
    }

    @Redirect(method = "run", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/ToastManager;render()V"))
    private void removeToastRender(ToastManager instance) {
    }

    @Inject(method = "run", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/Minecraft;printOpenGLError(Ljava/lang/String;)V",
            ordinal = 1))
    private void updateFrameTimes(CallbackInfo ci) {
        this.tFrameTimes[this.nextFrameTime] = this.prevFrameTimeForAvg = System.nanoTime();
        this.nextFrameTime = (this.nextFrameTime + 1) % 60;
    }

    @Inject(method = "run", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/Minecraft;stop()V",
            shift = At.Shift.BEFORE))
    private void exitGlobalJsContext(CallbackInfo ci) {
        ContextFactory.getGlobal().enterContext();
        Context.exit();
    }

    //@Inject(method = "run", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(
    //        value = "INVOKE",
    //        target = "Lnet/minecraft/client/Minecraft;method_2131()V",
    //        ordinal = 0,
    //        shift = At.Shift.BEFORE))
    //private void printOoMStackTrace(CallbackInfo ci, OutOfMemoryError var19) {
    //    var19.printStackTrace();
    //}

    @Inject(method = "method_2111", at = @At(
            value = "INVOKE_ASSIGN",
            target = "Ljava/lang/System;nanoTime()J",
            ordinal = 1))
    private void updateLastRendererTime(long par1, CallbackInfo ci) {
        Main.updateTimes[field_2771 & Main.updateTimes.length - 1] = Main.updateRendererTime;
    }

    @Inject(method = "method_2111", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/Tessellator;colour(I)V",
            ordinal = 5,
            shift = At.Shift.BEFORE))
    private void drawUpdateTime(long par1, CallbackInfo ci, long var3, long var5, Tessellator var7, int var8, long var9, int var11, int var12, int var13, int var14, int var15, long var16, long var18) {
        long uTime = Main.updateTimes[var12] / 200000L;

        var7.colour(var14);
        var7.pos((float) var12 + 0.5f, (float) ((long) this.actualHeight - (var16 - var18)) + 0.5f, 0.0);
        var7.pos((float) var12 + 0.5f, (float) ((long) this.actualHeight - (var16 - var18 - uTime)) + 0.5f, 0.0);
    }

    @Redirect(method = "method_2110", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/Minecraft;attackCooldown:I",
            opcode = Opcodes.PUTFIELD))
    public void removeAttackCooldownOnMethod_2110(Minecraft instance, int value) {
    }

    @Redirect(method = "lockCursor", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/Minecraft;attackCooldown:I",
            opcode = Opcodes.PUTFIELD))
    public void removeAttackCooldownOnLockCursor(Minecraft instance, int value) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    private void method_2107(int i) {
        if (i == 0 && this.attackCooldown > 0) {
            return;
        }
        if (DebugMode.active) {
            ((ExLevel) this.level).getUndoStack().startRecording();
        }
        boolean swapItemBack = false;
        ItemInstance itemUsing = this.player.inventory.getHeldItem();
        HandItemRenderer itemRenderer = this.gameRenderer.handItemRenderer;
        if (!DebugMode.active) {
            if (i == 0) {
                itemUsing = ((ExPlayerInventory) this.player.inventory).getOffhandItem();
                itemRenderer = ((ExGameRenderer) this.gameRenderer).getOffHandItemRenderer();
                ((ExPlayerInventory) this.player.inventory).swapOffhandWithMain();
                swapItemBack = true;
                ((ExPlayer) this.player).setSwappedItems(true);
            }
            int itemUseDelay = 5;
            if (itemUsing != null) {
                itemUseDelay = ((ExItemType) ItemType.byId[itemUsing.itemId]).getItemUseDelay();
            }
            if (i == 0) {
                this.field_2798 = this.field_2786 + itemUseDelay;
            } else {
                this.rightMouseTicksRan = this.field_2786 + itemUseDelay;
            }
            i = itemUsing != null && ((ExItemType) ItemType.byId[itemUsing.itemId]).mainActionLeftClick() ? 0 : 1;
        } else {
            this.field_2798 = this.field_2786 + 5;
            this.rightMouseTicksRan = this.field_2786 + 5;
        }
        if (i == 0) {
            this.player.swingHand();
        }
        boolean flag = true;
        if (this.hitResult == null) {
            if (i == 0 && !(this.interactionManager instanceof class_537)) {
                this.attackCooldown = 10;
            }
        } else if (this.hitResult.type == HitType.ENTITY) {
            if (i == 0) {
                this.interactionManager.attack(this.player, this.hitResult.entity);
            }
            if (i == 1) {
                this.interactionManager.interactWith(this.player, this.hitResult.entity);
            }
        } else if (this.hitResult.type == HitType.TILE) {
            int j = this.hitResult.x;
            int k = this.hitResult.y;
            int l = this.hitResult.z;
            int i1 = this.hitResult.tileId;
            Tile block = Tile.BY_ID[this.level.getTileId(j, k, l)];
            if (!(DebugMode.active || block.id != Tile.CHEST.id && block.id != Blocks.store.id)) {
                i = 1;
            }
            if (block != null) {
                int alwaysClick;
                if (!DebugMode.active && (alwaysClick = ((ExTile) block).alwaysUseClick(this.level, j, k, l)) != -1) {
                    i = alwaysClick;
                }
                if (i == 0) {
                    this.interactionManager.method_1707(j, k, l, this.hitResult.tileId);
                    if (itemUsing != null) {
                        ((ExItemInstance) itemUsing).useItemLeftClick(this.player, this.level, j, k, l, i1);
                    }
                } else {
                    int j1 = itemUsing == null ? 0 : itemUsing.count;
                    if (this.interactionManager.activateTile(this.player, this.level, itemUsing, j, k, l, i1)) {
                        flag = false;
                        this.player.swingHand();
                    }
                    if (itemUsing == null) {
                        if (swapItemBack) {
                            ((ExPlayerInventory) this.player.inventory).swapOffhandWithMain();
                            ((ExPlayer) this.player).setSwappedItems(false);
                        }
                        if (DebugMode.active) {
                            ((ExLevel) this.level).getUndoStack().stopRecording();
                        }
                        return;
                    }
                    if (itemUsing.count == 0 && itemUsing == this.player.inventory.main[this.player.inventory.selectedHotbarSlot]) {
                        this.player.inventory.main[this.player.inventory.selectedHotbarSlot] = null;
                    } else if (itemUsing.count != j1) {
                        this.gameRenderer.handItemRenderer.method_1863();
                    }
                }
            }
        }
        if (flag && i == 0 && itemUsing != null && ItemType.byId[itemUsing.itemId] != null) {
            ((ExItemType) ItemType.byId[itemUsing.itemId]).onItemLeftClick(itemUsing, this.level, this.player);
        }
        if (flag && i == 1 && itemUsing != null && this.interactionManager.useItem(this.player, this.level, itemUsing)) {
            this.gameRenderer.handItemRenderer.method_1865();
        }
        if (itemUsing != null) {
            Script script = ((ExLevel) this.level).getScript();
            if (this.lastItemUsed != itemUsing) {
                Object wrappedOut = Context.javaToJS(new ScriptItem(itemUsing), script.globalScope);
                ScriptableObject.putProperty(script.globalScope, "lastItemUsed", wrappedOut);
                this.lastItemUsed = itemUsing;
            }
            if (this.hitResult == null) {
                if (this.lastEntityHit != null) {
                    this.lastEntityHit = null;
                    Object wrappedOut = Context.javaToJS(null, script.globalScope);
                    ScriptableObject.putProperty(script.globalScope, "hitEntity", wrappedOut);
                }
                if (this.lastBlockHit != null) {
                    this.lastBlockHit = null;
                    Object wrappedOut = Context.javaToJS(null, script.globalScope);
                    ScriptableObject.putProperty(script.globalScope, "hitBlock", wrappedOut);
                }
            } else if (this.hitResult.type == HitType.ENTITY) {
                if (this.lastEntityHit != this.hitResult.entity) {
                    this.lastEntityHit = this.hitResult.entity;
                    Object wrappedOut = Context.javaToJS(ScriptEntity.getEntityClass(this.hitResult.entity), script.globalScope);
                    ScriptableObject.putProperty(script.globalScope, "hitEntity", wrappedOut);
                }
                if (this.lastBlockHit != null) {
                    this.lastBlockHit = null;
                    Object wrappedOut = Context.javaToJS(null, script.globalScope);
                    ScriptableObject.putProperty(script.globalScope, "hitBlock", wrappedOut);
                }
            } else if (this.hitResult.type == HitType.TILE) {
                if (this.lastBlockHit == null || this.lastBlockHit.x != (double) this.hitResult.x || this.lastBlockHit.y != (double) this.hitResult.y || this.lastBlockHit.z != (double) this.hitResult.z) {
                    this.lastBlockHit = new ScriptVec3(this.hitResult.x, this.hitResult.y, this.hitResult.z);
                    Object wrappedOut = Context.javaToJS(this.lastBlockHit, script.globalScope);
                    ScriptableObject.putProperty(script.globalScope, "hitBlock", wrappedOut);
                }
                if (this.lastEntityHit != null) {
                    this.lastEntityHit = null;
                    Object wrappedOut = Context.javaToJS(null, script.globalScope);
                    ScriptableObject.putProperty(script.globalScope, "hitEntity", wrappedOut);
                }
            } else {
                if (this.lastEntityHit != null) {
                    this.lastEntityHit = null;
                    Object wrappedOut = Context.javaToJS(null, script.globalScope);
                    ScriptableObject.putProperty(script.globalScope, "hitEntity", wrappedOut);
                }
                if (this.lastBlockHit != null) {
                    this.lastBlockHit = null;
                    Object wrappedOut = Context.javaToJS(null, script.globalScope);
                    ScriptableObject.putProperty(script.globalScope, "hitBlock", wrappedOut);
                }
            }
            if (itemUsing.method_719()) {
                ((ExLevel) this.level).getScriptHandler().runScript(String.format("item_%d_%d.js", itemUsing.itemId, itemUsing.getDamage()), ((ExLevel) this.level).getScope(), false);
            } else {
                ((ExLevel) this.level).getScriptHandler().runScript(String.format("item_%d.js", itemUsing.itemId), ((ExLevel) this.level).getScope(), false);
            }
        }
        if (swapItemBack) {
            ((ExPlayerInventory) this.player.inventory).swapOffhandWithMain();
            ((ExPlayer) this.player).setSwappedItems(false);
        }
        if (DebugMode.active) {
            ((ExLevel) this.level).getUndoStack().stopRecording();
        }
    }

    @Inject(method = "updateScreenResolution", at = @At(value = "TAIL"))
    private void reinitStoreGui(int w, int h, CallbackInfo ci) {
        ScreenScaler scaledresolution = new ScreenScaler(this.options, this.actualWidth, this.actualHeight);
        int k = scaledresolution.getScaledWidth();
        int l = scaledresolution.getScaledHeight();
        this.storeGUI.init((Minecraft) (Object) this, k, l);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void tick() {
        if (this.field_2786 == 6000) {
            this.startLoginThread();
        }

        this.overlay.method_1944();
        this.gameRenderer.method_1838(1.0f);

        if (this.player != null) {
            LevelSource ichunkprovider = this.level.getLevelSource();
            if (ichunkprovider instanceof ClientChunkCache) {
                ClientChunkCache chunkproviderloadorgenerate = (ClientChunkCache) ichunkprovider;
                int j = MathsHelper.floor((int) this.player.x) >> 4;
                int i1 = MathsHelper.floor((int) this.player.z) >> 4;
                chunkproviderloadorgenerate.setSpawnChunk(j, i1);
            }
        }
        if (!this.paused && this.level != null) {
            this.interactionManager.tick();
        }
        GL11.glBindTexture(3553, this.textureManager.getTextureId("/assets/terrain.png"));
        if (!this.paused) {
            this.textureManager.tick();
        }

        if (this.currentScreen == null && this.player != null) {
            if (this.player.health <= 0) {
                this.openScreen(null);
            } else if (this.player.isSleeping() && this.level != null && this.level.isClient) {
                this.openScreen(new SleepingChatScreen());
            }
        } else if (this.currentScreen != null && this.currentScreen instanceof SleepingChatScreen && !this.player.isSleeping()) {
            this.openScreen(null);
        }
        if (this.currentScreen != null && !((ExScreen) this.currentScreen).getDisableInputGrabbing()) {
            this.field_2798 = this.field_2786 + 10000;
        }
        if (this.currentScreen != null && !((ExScreen) this.currentScreen).getDisableInputGrabbing()) {
            this.currentScreen.method_130();
            if (this.currentScreen != null) {
                this.currentScreen.smokeRenderer.method_351();
                this.currentScreen.tick();
            }
        }
        if (this.currentScreen == null || this.currentScreen.passEvents || ((ExScreen) this.currentScreen).getDisableInputGrabbing()) {
            while (Mouse.next()) {
                long l = System.currentTimeMillis() - this.lastTickTime;
                if (l > 200L) continue;
                int k = Mouse.getEventDWheel();
                if (k != 0) {
                    boolean ctrlDown = Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
                    boolean altDown = Keyboard.isKeyDown(56) || Keyboard.isKeyDown(184);
                    if (k > 0) {
                        k = 1;
                    }
                    if (k < 0) {
                        k = -1;
                    }
                    if (DebugMode.active && altDown) {
                        DebugMode.reachDistance += k;
                        DebugMode.reachDistance = Math.min(Math.max(DebugMode.reachDistance, 2), 100);
                        this.overlay.addChatMessage(String.format("Reach Changed to %d", DebugMode.reachDistance));
                    } else {
                        ExPlayerInventory inv = (ExPlayerInventory) this.player.inventory;
                        if (ctrlDown) {
                            int t = this.player.inventory.selectedHotbarSlot;
                            this.player.inventory.selectedHotbarSlot = inv.getOffhandSlot();
                            inv.setOffhandSlot(t);
                        }
                        this.player.inventory.scrollInHotbar(k);
                        if (ctrlDown) {
                            int t = this.player.inventory.selectedHotbarSlot;
                            this.player.inventory.selectedHotbarSlot = inv.getOffhandSlot();
                            inv.setOffhandSlot(t);
                        }
                        if (this.options.field_1445) {
                            this.options.field_1448 += (float) k * 0.25f;
                        }
                    }
                }
                if (this.currentScreen == null || ((ExScreen) this.currentScreen).getDisableInputGrabbing()) {
                    if (!this.field_2778 && Mouse.getEventButtonState()) {
                        this.lockCursor();
                        continue;
                    }
                    if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) {
                        this.method_2107(0);
                    }
                    if (Mouse.getEventButton() == 1 && Mouse.getEventButtonState()) {
                        this.method_2107(1);
                    }
                    if (Mouse.getEventButton() != 2 || !Mouse.getEventButtonState()) continue;
                    this.method_2103();
                    continue;
                }
                if (this.currentScreen == null) continue;
                this.currentScreen.onMouseEvent();
            }
            if (this.attackCooldown > 0) {
                --this.attackCooldown;
            }
            while (Keyboard.next()) {
                this.player.method_136(Keyboard.getEventKey(), Keyboard.getEventKeyState());
                if (!Keyboard.getEventKeyState()) continue;
                if (Keyboard.getEventKey() == 87) {
                    this.toggleFullscreen();
                } else {
                    if (this.currentScreen != null && !((ExScreen) this.currentScreen).getDisableInputGrabbing()) {
                        this.currentScreen.onKeyboardEvent();
                    } else {
                        if (Keyboard.getEventKey() == 1) {
                            this.method_2135();
                        }
                        if (Keyboard.getEventKey() == 31 && Keyboard.isKeyDown(61)) {
                            this.method_2105();
                        }
                        if (Keyboard.getEventKey() == 59) {
                            this.options.hideHud = !this.options.hideHud;
                        }
                        if (Keyboard.getEventKey() == 61) {
                            this.options.debugHud = !this.options.debugHud;
                        }
                        if (Keyboard.getEventKey() == 62) {
                            DebugMode.active = !DebugMode.active;
                            if (DebugMode.active) {
                                this.overlay.addChatMessage("Debug Mode Active");
                            } else {
                                this.overlay.addChatMessage("Debug Mode Deactivated");
                                ((ExLevel) this.level).loadBrightness();
                            }
                            ((ExWorldRenderer) this.worldRenderer).updateAllTheRenderers();
                        }
                        if (Keyboard.getEventKey() == 63) {
                            this.options.thirdPerson = !this.options.thirdPerson;
                        }
                        if (Keyboard.getEventKey() == 64) {
                            ((ExWorldRenderer) this.worldRenderer).resetAll();
                            this.overlay.addChatMessage("Resetting all blocks in loaded chunks");
                        }
                        if (Keyboard.getEventKey() == 65) {
                            ((ExClientPlayer) this.player).displayGUIPalette();
                        }
                        if (Keyboard.getEventKey() == this.options.inventoryKey.key) {
                            this.openScreen(new PlayerInventoryScreen(this.player));
                        }
                        if (Keyboard.getEventKey() == this.options.dropKey.key) {
                            this.player.dropSelectedItem();
                        }
                        if ((this.isConnectedToServer() || DebugMode.active) && Keyboard.getEventKey() == this.options.chatKey.key) {
                            this.openScreen(new ChatScreen());
                        }
                        if (DebugMode.active && (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157))) {
                            if (Keyboard.getEventKey() == 44) {
                                ((ExLevel) this.level).undo();
                            } else if (Keyboard.getEventKey() == 21) {
                                ((ExLevel) this.level).redo();
                            }
                        }
                    }
                    for (int i = 0; i < 9; ++i) {
                        if (Keyboard.getEventKey() != 2 + i) continue;
                        ExPlayerInventory inv = (ExPlayerInventory) this.player.inventory;
                        if (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)) {
                            if (i == this.player.inventory.selectedHotbarSlot) {
                                this.player.inventory.selectedHotbarSlot = inv.getOffhandSlot();
                            }
                            inv.setOffhandSlot(i);
                            continue;
                        }
                        if (i == inv.getOffhandSlot()) {
                            inv.setOffhandSlot(this.player.inventory.selectedHotbarSlot);
                        }
                        this.player.inventory.selectedHotbarSlot = i;
                    }
                    if (Keyboard.getEventKey() == this.options.fogKey.key) {
                        this.options.changeOption(Option.RENDER_DISTANCE, !Keyboard.isKeyDown(42) && !Keyboard.isKeyDown(54) ? 1 : -1);
                    }
                }
                if (this.level == null) continue;
                ((ExLevel) this.level).getScript().keyboard.processKeyPress(Keyboard.getEventKey());
            }
            if (this.currentScreen == null || ((ExScreen) this.currentScreen).getDisableInputGrabbing()) {
                if (Mouse.isButtonDown(0) && (float) (this.field_2786 - this.field_2798) >= 0.0f && this.field_2778) {
                    this.method_2107(0);
                }
                if (Mouse.isButtonDown(1) && (float) (this.field_2786 - this.rightMouseTicksRan) >= 0.0f && this.field_2778) {
                    this.method_2107(1);
                }
            }
            this.method_2110(0, (this.currentScreen == null || ((ExScreen) this.currentScreen).getDisableInputGrabbing()) && Mouse.isButtonDown(0) && this.field_2778);
        }
        if (this.level != null) {
            if (this.player != null) {
                ++this.field_2799;
                if (this.field_2799 == 30) {
                    this.field_2799 = 0;
                    this.level.method_287(this.player);
                }
            }
            this.level.difficulty = this.options.difficulty;
            if (this.level.isClient) {
                this.level.difficulty = 3;
            }
            if (!this.paused) {
                this.gameRenderer.method_1837();
            }
            if (!this.paused) {
                this.worldRenderer.method_1557();
            }
            if (!this.paused) {
                if (this.level.field_210 > 0) {
                    --this.level.field_210;
                }
                this.level.method_227();
            }
            if (!this.paused || this.isConnectedToServer()) {
                this.level.method_196(this.options.difficulty > 0, true);
                this.level.method_242();
            }
            if (!this.paused && this.level != null) {
                this.level.method_294(MathsHelper.floor(this.player.x), MathsHelper.floor(this.player.y), MathsHelper.floor(this.player.z));
            }
            if (!this.paused) {
                this.particleManager.method_320();
            }
        }
        this.lastTickTime = System.currentTimeMillis();
        if (++this.gcTime > 1800) {
            this.gcTime = 0;
            System.gc();
        }
    }

    public String getMapUsed(String s) {
        File mcDir = Minecraft.getGameDirectory();
        File saveDir = new File(mcDir, "saves");
        File worldDir = new File(saveDir, s);
        File mapTxt = new File(worldDir, "map.txt");
        if (mapTxt.exists()) {
            try {
                BufferedReader input = new BufferedReader(new FileReader(mapTxt));
                String result = input.readLine();
                input.close();
                return result;
            } catch (IOException e) {
            }
        }
        return null;
    }

    @Override
    public void saveMapUsed(String s, String mapName) {
        File mcDir = Minecraft.getGameDirectory();
        File saveDir = new File(mcDir, "saves");
        File worldDir = new File(saveDir, s);
        worldDir.mkdirs();
        File mapTxt = new File(worldDir, "map.txt");
        try {
            if (mapTxt.exists()) {
                mapTxt.delete();
            }
            mapTxt.createNewFile();
            BufferedWriter output = new BufferedWriter(new FileWriter(mapTxt));
            output.write(mapName);
            output.close();
        } catch (IOException e) {
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void createOrLoadWorld(String s, String s1, long l) {
        String mapName = this.getMapUsed(s);
        if (MathsHelper.isStringEmpty(mapName)) {
            this.openScreen(new GuiMapSelect(null, s));
        } else {
            this.startWorld(s, s1, l, mapName);
        }
    }

    public void startWorld(String s, String s1, long l, String mapName) {
        this.setLevel(null);
        System.gc();
        if (s != null && this.levelStorage.isOld(s)) {
            this.convertWorldFormat(s, s1);
        } else {
            DebugMode.active = false;
            DebugMode.levelEditing = false;
            DimensionData isavehandler = null;
            if (s != null) {
                isavehandler = this.levelStorage.createDimensionFile(s, false);
            }
            if (s1 == null) {
                s1 = "Map Editing";
            }
            Level world = null;
            try {
                world = ExLevel.createLevel(mapName, isavehandler, s1, l, null);
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            if (world.generating) {
                this.statManager.incrementStat(Stats.createWorld, 1);
                this.statManager.incrementStat(Stats.startGame, 1);
                this.notifyStatus(world, "Generating level");
            } else {
                this.statManager.incrementStat(Stats.loadWorld, 1);
                this.statManager.incrementStat(Stats.startGame, 1);
                this.notifyStatus(world, "Loading level");
            }
        }
        this.openScreen(null);
    }

    @Override
    public Level getWorld(String saveName, long l, String mapName) {
        this.setLevel(null);
        System.gc();
        DimensionData isavehandler = this.levelStorage.createDimensionFile(saveName, false);
        Level world = null;
        try {
            world = ExLevel.createLevel(mapName, isavehandler, saveName, l, null);
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return world;
    }

    @Inject(method = "method_2115", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/ClientInteractionManager;method_1710(Lnet/minecraft/level/Level;)V",
            shift = At.Shift.AFTER))
    private void loadLevelTextures(Level world, String s, Player entityplayer, CallbackInfo ci) {
        ((ExLevel) world).loadMapTextures();
    }

    @Inject(method = "method_2115", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/ClientInteractionManager;method_1711(Lnet/minecraft/entity/player/Player;)V",
            shift = At.Shift.AFTER))
    private void initPlayer(Level world, String s, Player entityplayer, CallbackInfo ci) {
        this.cutsceneCameraEntity = this.interactionManager.createPlayer(world);
        ((ExLevel) world).getScript().initPlayer();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void respawn(boolean flag, int i) {
        if (!this.level.isClient && !this.level.dimension.canPlayerSleep()) {
            this.switchDimension();
        }

        Vec3i chunkcoordinates = this.level.getSpawnPosition();
        LevelSource ichunkprovider = this.level.getLevelSource();
        if (ichunkprovider instanceof ClientChunkCache) {
            ClientChunkCache chunkproviderloadorgenerate = (ClientChunkCache) ichunkprovider;
            chunkproviderloadorgenerate.setSpawnChunk(chunkcoordinates.x >> 4, chunkcoordinates.z >> 4);
        }

        this.level.method_295();
        int entID = 0;
        if (this.player != null) {
            entID = this.player.id;
            this.level.removeEntity(this.player);
        } else {
            this.player = (ClientPlayer) this.interactionManager.createPlayer(this.level);
            ((ExLevel) this.level).getScript().initPlayer();
        }
        ((ExWorldRenderer) this.worldRenderer).resetForDeath();
        Vec3i spawnCoords = this.level.getSpawnPosition();
        this.player.afterSpawn();
        this.player.setPositionAndAngles((double) spawnCoords.x + 0.5, spawnCoords.y, (double) spawnCoords.z + 0.5, 0.0f, 0.0f);
        this.cameraEntity = this.player;
        this.player.afterSpawn();

        this.interactionManager.method_1711(this.player);
        this.level.addPlayer(this.player);
        this.player.keypressManager = new MovementManager(this.options);
        this.player.id = entID;
        this.player.method_494();
        ((AccessEntity) this.player).invokeSetRotation(((ExLevel) this.level).getSpawnYaw(), 0.0f);
        this.interactionManager.method_1718(this.player);
        this.method_2130("Respawning");
        if (this.currentScreen instanceof DeathScreen) {
            this.openScreen(null);
        }
    }

    @Override
    public long getAvgFrameTime() {
        if (this.tFrameTimes[this.nextFrameTime] != 0L) {
            return (this.prevFrameTimeForAvg - this.tFrameTimes[this.nextFrameTime]) / 60L;
        }
        return 23333333L;
    }

    @Override
    public void updateStoreGUI() {
        ScreenScaler scaledresolution = new ScreenScaler(this.options, this.actualWidth, this.actualHeight);
        int i = scaledresolution.getScaledWidth();
        int j = scaledresolution.getScaledHeight();
        this.storeGUI.init((Minecraft) (Object) this, i, j);
    }

    @Override
    public GuiStore getStoreGUI() {
        return this.storeGUI;
    }

    @Override
    public boolean isCameraActive() {
        return this.cameraActive;
    }

    @Override
    public CutsceneCamera getCutsceneCamera() {
        return this.cutsceneCamera;
    }

    @Override
    public CutsceneCamera getActiveCutsceneCamera() {
        return this.activeCutsceneCamera;
    }

    @Override
    public LivingEntity getCutsceneCameraEntity() {
        return this.cutsceneCameraEntity;
    }

    @Override
    public void setActiveCutsceneCamera(CutsceneCamera cutsceneCamera) {
        this.activeCutsceneCamera = cutsceneCamera;
    }

    @Override
    public void setCameraActive(boolean cameraActive) {
        this.cameraActive = cameraActive;
    }

    @Override
    public boolean isCameraPause() {
        return this.cameraPause;
    }

    @Override
    public void setCameraPause(boolean cameraPause) {
        this.cameraPause = cameraPause;
    }

    @Override
    public MapList getMapList() {
        return this.mapList;
    }
}
