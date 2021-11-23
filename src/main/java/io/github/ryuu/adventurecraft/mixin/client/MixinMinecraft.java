package io.github.ryuu.adventurecraft.mixin.client;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.gui.GuiMapSelect;
import io.github.ryuu.adventurecraft.gui.GuiStore;
import io.github.ryuu.adventurecraft.mixin.client.gui.ScreenInputGrab;
import io.github.ryuu.adventurecraft.scripting.ScriptItem;
import io.github.ryuu.adventurecraft.scripting.ScriptVec3;
import io.github.ryuu.adventurecraft.util.*;
import net.minecraft.achievement.Achievements;
import net.minecraft.*;
import net.minecraft.client.*;
import net.minecraft.client.colour.FoliageColour;
import net.minecraft.client.colour.GrassColour;
import net.minecraft.client.colour.WaterColour;
import net.minecraft.client.gui.Overlay;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.screen.container.PlayerInventoryScreen;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.model.BipedModel;
import net.minecraft.client.sound.SoundHelper;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.OcclusionQueryTester;
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
import net.minecraft.level.dimension.Dimension;
import net.minecraft.level.dimension.DimensionData;
import net.minecraft.level.source.LevelSource;
import net.minecraft.level.storage.LevelStorage;
import net.minecraft.level.storage.McRegionLevelStorage;
import net.minecraft.level.storage.SessionLockException;
import net.minecraft.script.ScriptEntity;
import net.minecraft.script.ScriptItem;
import net.minecraft.script.ScriptVec3;
import net.minecraft.stat.StatManager;
import net.minecraft.stat.Stats;
import net.minecraft.tile.Tile;
import net.minecraft.util.FormattedString;
import net.minecraft.util.ProgressListenerError;
import net.minecraft.util.ProgressListenerImpl;
import net.minecraft.util.Vec3i;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitType;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathsHelper;
import net.minecraft.util.maths.Vec3f;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.io.*;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft implements Runnable {

    private static final File gameDirectory;
    @Shadow()
    public static byte[] field_2800 = new byte[0xA00000];
    public static long[] field_2769;
    public static long[] field_2770;
    public static int field_2771;
    public static long field_2772;
    public static Minecraft minecraftInstance;
    public static long[] updateTimes;
    public static long updateRendererTime;
    private static Minecraft instance;

    static {
        field_2769 = new long[512];
        field_2770 = new long[512];
        field_2771 = 0;
        field_2772 = 0L;
        gameDirectory = null;
        updateTimes = new long[512];
    }

    private final Timer tickTimer = new Timer(20.0f);
    private final int height;
    private final FlowingWaterTextureBinder2 waterTextureBinder = new FlowingWaterTextureBinder2();
    private final FlowingLavaTextureBinder lavaTextureBinder = new FlowingLavaTextureBinder();
    public ClientInteractionManager interactionManager;
    public int actualWidth;
    public int actualHeight;
    public Level level;
    public WorldRenderer worldRenderer;
    public ClientPlayer player;
    public LivingEntity field_2807;
    public ParticleManager particleManager;
    public Session session = null;
    public String field_2810;
    public Canvas canvas;
    public boolean isApplet = true;
    public volatile boolean paused = false;
    public TextureManager textureManager;
    public TextRenderer textRenderer;
    public Screen currentScreen = null;
    public ProgressListenerImpl progressListener = new ProgressListenerImpl(this);
    public GameRenderer gameRenderer;
    public ResourceDownloadThread resourceDownloadThread;
    public ToastManager toastManager = new ToastManager(this);
    public Overlay overlay;
    public boolean skipGameRender = false;
    public BipedModel field_2822 = new BipedModel(0.0f);
    public HitResult hitResult = null;
    public GameOptions options;
    public SoundHelper soundHelper = new SoundHelper();
    public class_596 field_2767;
    public TexturePackManager texturePackManager;
    public StatManager statManager;
    public volatile boolean running = true;
    public String fpsDebugString = "";
    public boolean field_2778 = false;
    public boolean field_2779 = false;
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
    protected MinecraftApplet minecraftApplet;
    boolean field_2776 = false;
    long field_2777 = -1L;
    long lastTickTime = System.currentTimeMillis();
    ItemInstance lastItemUsed;
    Entity lastEntityHit;
    ScriptVec3 lastBlockHit;
    int gcTime = 0;
    private boolean isFullscreen = false;
    private boolean field_2782 = false;
    private OcclusionQueryTester occlusionQueryTester;
    private int field_2786 = 0;
    private int attackCooldown = 0;
    private int width;
    private File gameDir;
    private LevelStorage levelStorage;
    private String serverIp;
    private int serverPort;
    private int field_2798 = 0;
    private int rightMouseTicksRan;
    private int field_2799 = 0;

    public MixinMinecraft(Component component, Canvas canvas, MinecraftApplet applet, int actualWidth, int actualHeight, boolean isFullscreen) {
        Stats.method_748();
        this.height = actualHeight;
        this.isFullscreen = isFullscreen;
        this.minecraftApplet = applet;
        new TimerHackThread("Timer hack thread");
        this.canvas = canvas;
        this.actualWidth = actualWidth;
        this.actualHeight = actualHeight;
        this.isFullscreen = isFullscreen;
        if (applet == null || "true".equals(applet.getParameter("stand-alone"))) {
            this.isApplet = false;
        }
        instance = this;
        minecraftInstance = this;
        this.tFrameTimes = new long[60];
        this.cutsceneCamera = new CutsceneCamera();
        this.storeGUI = new GuiStore();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static void start(String playerName, String s1, String s2) {
        boolean flag = false;
        String s3 = playerName;
        Frame frame = new Frame("Minecraft");
        Canvas canvas = new Canvas();
        frame.setLayout(new BorderLayout());
        frame.add(canvas, "Center");
        canvas.setPreferredSize(new java.awt.Dimension(854, 480));
        frame.pack();
        frame.setLocationRelativeTo(null);
        class_640 minecraftimpl = new class_640((Component) frame, canvas, null, 854, 480, flag, frame);
        Thread thread = new Thread(minecraftimpl, "Minecraft main thread");
        thread.setPriority(10);
        minecraftimpl.field_2810 = "www.minecraft.net";
        minecraftimpl.session = s3 != null && s1 != null ? new Session(s3, s1) : new Session("ACPlayer", "");
        if (s2 != null) {
            String[] as = s2.split(":");
            minecraftimpl.setConnectionInfo(as[0], Integer.parseInt(as[1]));
        }
        frame.setVisible(true);
        frame.addWindowListener(minecraftimpl.new MinecraftWindowAdapter(thread));
        thread.start();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void onGameStartupFailure(GameStartupError unexpectedthrowable) {
        this.field_2782 = true;
        this.showGameStartupError(unexpectedthrowable);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setConnectionInfo(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void init() throws LWJGLException {
        if (this.canvas != null) {
            Graphics g = this.canvas.getGraphics();
            if (g != null) {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, this.actualWidth, this.actualHeight);
                g.dispose();
            }
            Display.setParent(this.canvas);
        } else if (this.isFullscreen) {
            Display.setFullscreen(true);
            this.actualWidth = Display.getDisplayMode().getWidth();
            this.actualHeight = Display.getDisplayMode().getHeight();
            if (this.actualWidth <= 0) {
                this.actualWidth = 1;
            }
            if (this.actualHeight <= 0) {
                this.actualHeight = 1;
            }
        } else {
            Display.setDisplayMode(new DisplayMode(this.actualWidth, this.actualHeight));
        }
        Display.setTitle(Version.version);
        try {
            Display.create();
        } catch (LWJGLException lwjglexception) {
            lwjglexception.printStackTrace();
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException interruptedException) {
            }
            Display.create();
        }
        this.gameDir = Minecraft.getGameDirectory();
        this.levelStorage = new McRegionLevelStorage(new File(this.gameDir, "saves"));
        this.options = new GameOptions((Minecraft)(Object)this, this.gameDir);
        this.texturePackManager = new TexturePackManager((Minecraft)(Object)this, this.gameDir);
        this.mapList = new MapList(this.gameDir);
        this.textureManager = new TextureManager(this.texturePackManager, this.options);
        this.textRenderer = new TextRenderer(this.options, "/font/default.png", this.textureManager);
        WaterColour.set(this.textureManager.getImageGrid("/misc/watercolor.png"));
        GrassColour.set(this.textureManager.getImageGrid("/misc/grasscolor.png"));
        FoliageColour.set(this.textureManager.getImageGrid("/misc/foliagecolor.png"));
        this.gameRenderer = new GameRenderer((Minecraft)(Object)this);
        EntityRenderDispatcher.INSTANCE.field_2494 = new HandItemRenderer((Minecraft)(Object)this);
        this.statManager = new StatManager(this.session, this.gameDir);
        Achievements.OPEN_INVENTORY.formatter(new class_637());
        this.method_2150();
        Keyboard.create();
        Mouse.create();
        this.field_2767 = new class_596(this.canvas);
        try {
            Controllers.create();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        this.printOpenGLError("Pre startup");
        GL11.glEnable(3553);
        GL11.glShadeModel(7425);
        GL11.glClearDepth(1.0);
        GL11.glEnable(2929);
        GL11.glDepthFunc(515);
        GL11.glEnable(3008);
        GL11.glAlphaFunc(516, 0.1f);
        GL11.glCullFace(1029);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(5888);
        this.printOpenGLError("Startup");
        this.occlusionQueryTester = new OcclusionQueryTester();
        this.soundHelper.acceptOptions(this.options);
        this.textureManager.add(this.lavaTextureBinder);
        this.textureManager.add(this.waterTextureBinder);
        this.textureManager.add(new PortalTextureBinder());
        this.textureManager.add(new CompassTextureBinder(this));
        this.textureManager.add(new ClockTextureBinder(this));
        this.textureManager.add(new FlowingWaterTextureBinder());
        this.textureManager.add(new FlowingLavaTextureBinder2());
        this.textureManager.add(new FireTextureBinder(0));
        this.textureManager.add(new FireTextureBinder(1));
        this.textureManager.add(new TextureFanFX());
        this.worldRenderer = new WorldRenderer((Minecraft)(Object)this, this.textureManager);
        GL11.glViewport(0, 0, this.actualWidth, this.actualHeight);
        this.particleManager = new ParticleManager(this.level, this.textureManager);
        try {
            this.resourceDownloadThread = new ResourceDownloadThread(this.gameDir, (Minecraft)(Object)this);
            this.resourceDownloadThread.start();
            this.resourceDownloadThread.method_107();
        } catch (Exception exception) {
        }
        this.printOpenGLError("Post startup");
        this.overlay = new Overlay((Minecraft)(Object)this);
        if (this.serverIp != null) {
            this.openScreen(new ServerConnectingScreen((Minecraft)(Object)this, this.serverIp, this.serverPort));
        } else {
            this.openScreen(new TitleScreen());
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_2150() throws LWJGLException {
        ScreenScaler scaledresolution = new ScreenScaler(this.options, this.actualWidth, this.actualHeight);
        GL11.glClear(16640);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, scaledresolution.scaledWidth, scaledresolution.scaledHeight, 0.0, 1000.0, 3000.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
        GL11.glViewport(0, 0, this.actualWidth, this.actualHeight);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        Tessellator tessellator = Tessellator.INSTANCE;
        GL11.glDisable(2896);
        GL11.glEnable(3553);
        GL11.glDisable(2912);
        GL11.glBindTexture(3553, this.textureManager.getTextureId("/title/mojang.png"));
        tessellator.start();
        tessellator.colour(0xFFFFFF);
        tessellator.vertex(0.0, this.actualHeight, 0.0, 0.0, 0.0);
        tessellator.vertex(this.actualWidth, this.actualHeight, 0.0, 0.0, 0.0);
        tessellator.vertex(this.actualWidth, 0.0, 0.0, 0.0, 0.0);
        tessellator.vertex(0.0, 0.0, 0.0, 0.0, 0.0);
        tessellator.draw();
        int c = 256;
        int c1 = 256;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        tessellator.colour(0xFFFFFF);
        this.method_2109((scaledresolution.getScaledWidth() - c) / 2, (scaledresolution.getScaledHeight() - c1) / 2, 0, 0, c, c1);
        GL11.glDisable(2896);
        GL11.glDisable(2912);
        GL11.glEnable(3008);
        GL11.glAlphaFunc(516, 0.1f);
        Display.swapBuffers();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void openScreen(Screen screen) {
        if (this.currentScreen instanceof class_625) {
            return;
        }
        if (this.currentScreen != null) {
            this.currentScreen.onClose();
        }
        if (screen instanceof TitleScreen) {
            this.statManager.method_1991();
        }
        this.statManager.sync();
        if (screen == null && this.level == null) {
            screen = new TitleScreen();
        } else if (screen == null && this.player.health <= 0) {
            screen = new DeathScreen();
        }
        if (screen instanceof TitleScreen) {
            this.overlay.method_1950();
        }
        this.currentScreen = screen;
        if (screen != null) {
            this.method_2134();
            ScreenScaler scaledresolution = new ScreenScaler(this.options, this.actualWidth, this.actualHeight);
            int i = scaledresolution.getScaledWidth();
            int j = scaledresolution.getScaledHeight();
            screen.init((Minecraft)(Object)this, i, j);
            this.skipGameRender = false;
        } else {
            this.lockCursor();
        }
    }

    public long getAvgFrameTime() {
        if (this.tFrameTimes[this.nextFrameTime] != 0L) {
            return (this.prevFrameTimeForAvg - this.tFrameTimes[this.nextFrameTime]) / 60L;
        }
        return 23333333L;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void run() {
        this.running = true;
        try {
            this.init();
        } catch (Exception exception) {
            exception.printStackTrace();
            this.onGameStartupFailure(new GameStartupError("Failed to start game", exception));
            return;
        }
        try {
            this.textureManager.getTextureId("/terrain.png");
            this.textureManager.getTextureId("/terrain2.png");
            this.textureManager.getTextureId("/terrain3.png");
            ContextFactory.initGlobal(new ContextFactory());
            long l = System.currentTimeMillis();
            int i = 0;
            while (this.running) {
                try {
                    if (this.minecraftApplet != null && !this.minecraftApplet.isActive()) {
                        break;
                    }
                    Box.method_85();
                    Vec3f.method_1292();
                    if (this.canvas == null && Display.isCloseRequested()) {
                        this.scheduleStop();
                    }
                    if (this.paused && this.level != null) {
                        float f = this.tickTimer.field_2370;
                        this.tickTimer.method_1853();
                        this.tickTimer.field_2370 = f;
                    } else {
                        this.tickTimer.method_1853();
                    }
                    long l1 = System.nanoTime();
                    for (int j = 0; j < this.tickTimer.field_2369; ++j) {
                        ++this.field_2786;
                        try {
                            this.tick();
                            continue;
                        } catch (SessionLockException minecraftexception1) {
                            this.level = null;
                            this.setLevel(null);
                            this.openScreen(new LevelSaveConflictScreen());
                        }
                    }
                    long l2 = System.nanoTime() - l1;
                    this.printOpenGLError("Pre render");
                    TileRenderer.field_67 = this.options.fancyGraphics;
                    if (!this.cameraActive) {
                        this.soundHelper.setSoundPosition(this.player, this.tickTimer.field_2370);
                    } else {
                        this.soundHelper.setSoundPosition(this.cutsceneCameraEntity, this.tickTimer.field_2370);
                    }
                    GL11.glEnable(3553);
                    if (this.level != null) {
                        this.level.method_232();
                    }
                    if (!Keyboard.isKeyDown(65)) {
                        Display.update();
                    }
                    if (this.player != null && this.player.isInsideWall()) {
                        this.options.thirdPerson = false;
                    }
                    if (!this.skipGameRender) {
                        if (this.interactionManager != null) {
                            this.interactionManager.method_1706(this.tickTimer.field_2370);
                        }
                        this.gameRenderer.method_1844(this.tickTimer.field_2370);
                    }
                    if (!Display.isActive()) {
                        if (this.isFullscreen) {
                            this.toggleFullscreen();
                        }
                        Thread.sleep(10L);
                    }
                    if (this.options.debugHud) {
                        this.method_2111(l2);
                    } else {
                        this.field_2777 = System.nanoTime();
                    }
                    Thread.yield();
                    if (Keyboard.isKeyDown(65)) {
                        Display.update();
                    }
                    this.method_2152();
                    if (!(this.canvas == null || this.isFullscreen || this.canvas.getWidth() == this.actualWidth && this.canvas.getHeight() == this.actualHeight)) {
                        this.actualWidth = this.canvas.getWidth();
                        this.actualHeight = this.canvas.getHeight();
                        if (this.actualWidth <= 0) {
                            this.actualWidth = 1;
                        }
                        if (this.actualHeight <= 0) {
                            this.actualHeight = 1;
                        }
                        this.updateScreenResolution(this.actualWidth, this.actualHeight);
                    }
                    this.printOpenGLError("Post render");
                    ++i;
                    boolean bl = this.paused = !this.isConnectedToServer() && this.currentScreen != null && this.currentScreen.isPauseScreen();
                    while (System.currentTimeMillis() >= l + 1000L) {
                        this.fpsDebugString = i + " fps, " + class_66.field_230 + " chunk updates";
                        class_66.field_230 = 0;
                        l += 1000L;
                        i = 0;
                    }
                    this.tFrameTimes[this.nextFrameTime] = this.prevFrameTimeForAvg = System.nanoTime();
                    this.nextFrameTime = (this.nextFrameTime + 1) % 60;
                } catch (SessionLockException minecraftexception) {
                    this.level = null;
                    this.setLevel(null);
                    this.openScreen(new LevelSaveConflictScreen());
                } catch (OutOfMemoryError outofmemoryerror) {
                    outofmemoryerror.printStackTrace();
                    this.method_2131();
                    this.openScreen(new OutOfMemoryScreen());
                    System.gc();
                }
            }
        } catch (ProgressListenerError minecrafterror) {
        } catch (Throwable throwable) {
            this.method_2131();
            throwable.printStackTrace();
            this.onGameStartupFailure(new GameStartupError("Unexpected error", throwable));
        } finally {
            ContextFactory.getGlobal().enterContext();
            Context.exit();
            this.stop();
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_2111(long l) {
        long l1 = 16666666L;
        if (this.field_2777 == -1L) {
            this.field_2777 = System.nanoTime();
        }
        long l2 = System.nanoTime();
        Minecraft.field_2770[Minecraft.field_2771 & Minecraft.field_2769.length - 1] = l;
        Minecraft.updateTimes[Minecraft.field_2771 & Minecraft.updateTimes.length - 1] = updateRendererTime;
        Minecraft.field_2769[Minecraft.field_2771++ & Minecraft.field_2769.length - 1] = l2 - this.field_2777;
        this.field_2777 = l2;
        GL11.glClear(256);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, this.actualWidth, this.actualHeight, 0.0, 1000.0, 3000.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(3553);
        Tessellator tessellator = Tessellator.INSTANCE;
        tessellator.start(7);
        int i = (int) (l1 / 200000L);
        tessellator.colour(0x20000000);
        tessellator.pos(0.0, this.actualHeight - i, 0.0);
        tessellator.pos(0.0, this.actualHeight, 0.0);
        tessellator.pos(field_2769.length, this.actualHeight, 0.0);
        tessellator.pos(field_2769.length, this.actualHeight - i, 0.0);
        tessellator.colour(0x20200000);
        tessellator.pos(0.0, this.actualHeight - i * 2, 0.0);
        tessellator.pos(0.0, this.actualHeight - i, 0.0);
        tessellator.pos(field_2769.length, this.actualHeight - i, 0.0);
        tessellator.pos(field_2769.length, this.actualHeight - i * 2, 0.0);
        tessellator.draw();
        long l3 = 0L;
        for (int j = 0; j < field_2769.length; ++j) {
            l3 += field_2769[j];
        }
        int k = (int) (l3 / 200000L / (long) field_2769.length);
        tessellator.start(7);
        tessellator.colour(0x20400000);
        tessellator.pos(0.0, this.actualHeight - k, 0.0);
        tessellator.pos(0.0, this.actualHeight, 0.0);
        tessellator.pos(field_2769.length, this.actualHeight, 0.0);
        tessellator.pos(field_2769.length, this.actualHeight - k, 0.0);
        tessellator.draw();
        tessellator.start(1);
        for (int i1 = 0; i1 < field_2769.length; ++i1) {
            int j1 = (i1 - field_2771 & field_2769.length - 1) * 255 / field_2769.length;
            int k1 = j1 * j1 / 255;
            k1 = k1 * k1 / 255;
            int i2 = k1 * k1 / 255;
            i2 = i2 * i2 / 255;
            if (field_2769[i1] > l1) {
                tessellator.colour(-16777216 + k1 * 65536);
            } else {
                tessellator.colour(-16777216 + k1 * 256);
            }
            long uTime = updateTimes[i1] / 200000L;
            long l4 = field_2769[i1] / 200000L;
            long l5 = field_2770[i1] / 200000L;
            tessellator.pos((float) i1 + 0.5f, (float) ((long) this.actualHeight - l4) + 0.5f, 0.0);
            tessellator.pos((float) i1 + 0.5f, (float) this.actualHeight + 0.5f, 0.0);
            tessellator.colour(k1 * 1);
            tessellator.pos((float) i1 + 0.5f, (float) ((long) this.actualHeight - (l4 - l5)) + 0.5f, 0.0);
            tessellator.pos((float) i1 + 0.5f, (float) ((long) this.actualHeight - (l4 - l5 - uTime)) + 0.5f, 0.0);
            tessellator.colour(-16777216 + k1 * 65536 + k1 * 256 + k1 * 1);
            tessellator.pos((float) i1 + 0.5f, (float) ((long) this.actualHeight - l4) + 0.5f, 0.0);
            tessellator.pos((float) i1 + 0.5f, (float) ((long) this.actualHeight - (l4 - l5)) + 0.5f, 0.0);
        }
        tessellator.draw();
        GL11.glEnable(3553);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void lockCursor() {
        if (Display.isActive()) {
            if (!this.field_2778) {
                this.field_2778 = true;
                this.field_2767.method_1970();
                this.openScreen(null);
                this.field_2798 = this.field_2786 + 10000;
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_2110(int i, boolean flag) {
        if (!this.interactionManager.field_2105) {
            return;
        }
        if (i == 0 && this.attackCooldown > 0) {
            return;
        }
        if (flag && this.hitResult != null && this.hitResult.type == HitType.TILE && i == 0) {
            int j = this.hitResult.x;
            int k = this.hitResult.y;
            int l = this.hitResult.z;
            this.interactionManager.method_1721(j, k, l, this.hitResult.field_1987);
            this.particleManager.addTileClickParticle(j, k, l, this.hitResult.field_1987);
        } else {
            this.interactionManager.method_1705();
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_2107(int i) {
        if (i == 0 && this.attackCooldown > 0) {
            return;
        }
        if (DebugMode.active) {
            this.level.undoStack.startRecording();
        }
        boolean swapItemBack = false;
        ItemInstance itemUsing = this.player.inventory.getHeldItem();
        HandItemRenderer itemRenderer = this.gameRenderer.handItemRenderer;
        if (!DebugMode.active) {
            if (i == 0) {
                itemUsing = this.player.inventory.getOffhandItem();
                itemRenderer = this.gameRenderer.offHandItemRenderer;
                this.player.inventory.swapOffhandWithMain();
                swapItemBack = true;
                this.player.swappedItems = true;
            }
            int itemUseDelay = 5;
            if (itemUsing != null) {
                itemUseDelay = ItemType.byId[itemUsing.itemId].itemUseDelay;
            }
            if (i == 0) {
                this.field_2798 = this.field_2786 + itemUseDelay;
            } else {
                this.rightMouseTicksRan = this.field_2786 + itemUseDelay;
            }
            i = itemUsing != null && ItemType.byId[itemUsing.itemId].mainActionLeftClick() ? 0 : 1;
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
                this.interactionManager.attack(this.player, this.hitResult.field_1989);
            }
            if (i == 1) {
                this.interactionManager.interactWith(this.player, this.hitResult.field_1989);
            }
        } else if (this.hitResult.type == HitType.TILE) {
            int j = this.hitResult.x;
            int k = this.hitResult.y;
            int l = this.hitResult.z;
            int i1 = this.hitResult.field_1987;
            Tile block = Tile.BY_ID[this.level.getTileId(j, k, l)];
            if (!(DebugMode.active || block.id != Tile.CHEST.id && block.id != Blocks.store.id)) {
                i = 1;
            }
            if (block != null) {
                int alwaysClick;
                if (!DebugMode.active && (alwaysClick = block.alwaysUseClick(this.level, j, k, l)) != -1) {
                    i = alwaysClick;
                }
                if (i == 0) {
                    this.interactionManager.method_1707(j, k, l, this.hitResult.field_1987);
                    if (itemUsing != null) {
                        itemUsing.useItemLeftClick(this.player, this.level, j, k, l, i1);
                    }
                } else {
                    int j1;
                    int n = j1 = itemUsing == null ? 0 : itemUsing.count;
                    if (this.interactionManager.activateTile(this.player, this.level, itemUsing, j, k, l, i1)) {
                        flag = false;
                        this.player.swingHand();
                    }
                    if (itemUsing == null) {
                        if (swapItemBack) {
                            this.player.inventory.swapOffhandWithMain();
                            this.player.swappedItems = false;
                        }
                        if (DebugMode.active) {
                            this.level.undoStack.stopRecording();
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
            ItemType.byId[itemUsing.itemId].onItemLeftClick(itemUsing, this.level, this.player);
        }
        if (flag && i == 1 && itemUsing != null && this.interactionManager.useItem(this.player, this.level, itemUsing)) {
            this.gameRenderer.handItemRenderer.method_1865();
        }
        if (itemUsing != null) {
            if (this.lastItemUsed != itemUsing) {
                Object wrappedOut = Context.javaToJS((Object) new ScriptItem(itemUsing), (Scriptable) this.level.script.globalScope);
                ScriptableObject.putProperty((Scriptable) this.level.script.globalScope, "lastItemUsed", wrappedOut);
                this.lastItemUsed = itemUsing;
            }
            if (this.hitResult == null) {
                if (this.lastEntityHit != null) {
                    this.lastEntityHit = null;
                    Object wrappedOut = Context.javaToJS(null, (Scriptable) this.level.script.globalScope);
                    ScriptableObject.putProperty((Scriptable) this.level.script.globalScope, "hitEntity", wrappedOut);
                }
                if (this.lastBlockHit != null) {
                    this.lastBlockHit = null;
                    Object wrappedOut = Context.javaToJS(null, (Scriptable) this.level.script.globalScope);
                    ScriptableObject.putProperty((Scriptable) this.level.script.globalScope, "hitBlock", wrappedOut);
                }
            } else if (this.hitResult.type == HitType.ENTITY) {
                if (this.lastEntityHit != this.hitResult.field_1989) {
                    this.lastEntityHit = this.hitResult.field_1989;
                    Object wrappedOut = Context.javaToJS((Object) ScriptEntity.getEntityClass(this.hitResult.field_1989), (Scriptable) this.level.script.globalScope);
                    ScriptableObject.putProperty((Scriptable) this.level.script.globalScope, "hitEntity", wrappedOut);
                }
                if (this.lastBlockHit != null) {
                    this.lastBlockHit = null;
                    Object wrappedOut = Context.javaToJS(null, (Scriptable) this.level.script.globalScope);
                    ScriptableObject.putProperty((Scriptable) this.level.script.globalScope, "hitBlock", wrappedOut);
                }
            } else if (this.hitResult.type == HitType.TILE) {
                if (this.lastBlockHit == null || this.lastBlockHit.x != (double) this.hitResult.x || this.lastBlockHit.y != (double) this.hitResult.y || this.lastBlockHit.z != (double) this.hitResult.z) {
                    this.lastBlockHit = new ScriptVec3(this.hitResult.x, this.hitResult.y, this.hitResult.z);
                    Object wrappedOut = Context.javaToJS((Object) this.lastBlockHit, (Scriptable) this.level.script.globalScope);
                    ScriptableObject.putProperty((Scriptable) this.level.script.globalScope, "hitBlock", wrappedOut);
                }
                if (this.lastEntityHit != null) {
                    this.lastEntityHit = null;
                    Object wrappedOut = Context.javaToJS(null, (Scriptable) this.level.script.globalScope);
                    ScriptableObject.putProperty((Scriptable) this.level.script.globalScope, "hitEntity", wrappedOut);
                }
            } else {
                if (this.lastEntityHit != null) {
                    this.lastEntityHit = null;
                    Object wrappedOut = Context.javaToJS(null, (Scriptable) this.level.script.globalScope);
                    ScriptableObject.putProperty((Scriptable) this.level.script.globalScope, "hitEntity", wrappedOut);
                }
                if (this.lastBlockHit != null) {
                    this.lastBlockHit = null;
                    Object wrappedOut = Context.javaToJS(null, (Scriptable) this.level.script.globalScope);
                    ScriptableObject.putProperty((Scriptable) this.level.script.globalScope, "hitBlock", wrappedOut);
                }
            }
            if (itemUsing.method_719()) {
                this.level.scriptHandler.runScript(String.format("item_%d_%d.js", new Object[]{itemUsing.itemId, itemUsing.getDamage()}), this.level.scope, false);
            } else {
                this.level.scriptHandler.runScript(String.format("item_%d.js", new Object[]{itemUsing.itemId}), this.level.scope, false);
            }
        }
        if (swapItemBack) {
            this.player.inventory.swapOffhandWithMain();
            this.player.swappedItems = false;
        }
        if (DebugMode.active) {
            this.level.undoStack.stopRecording();
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void updateScreenResolution(int actualWidth, int actualHeight) {
        if (actualWidth <= 0) {
            actualWidth = 1;
        }
        if (actualHeight <= 0) {
            actualHeight = 1;
        }
        this.actualWidth = actualWidth;
        this.actualHeight = actualHeight;
        ScreenScaler scaledresolution = new ScreenScaler(this.options, actualWidth, actualHeight);
        int k = scaledresolution.getScaledWidth();
        int l = scaledresolution.getScaledHeight();
        if (this.currentScreen != null) {
            this.currentScreen.init(this, k, l);
        }
        this.storeGUI.init(this, k, l);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void tick() {
        LevelSource ichunkprovider;
        if (this.field_2786 == 6000) {
            this.startLoginThread();
        }
        this.overlay.method_1944();
        this.gameRenderer.method_1838(1.0f);
        if (this.player != null && (ichunkprovider = this.level.getLevelSource()) instanceof ClientChunkCache) {
            ClientChunkCache chunkproviderloadorgenerate = (ClientChunkCache) ichunkprovider;
            int j = MathsHelper.floor((int) this.player.x) >> 4;
            int i1 = MathsHelper.floor((int) this.player.z) >> 4;
            chunkproviderloadorgenerate.setSpawnChunk(j, i1);
        }
        if (!this.paused && this.level != null) {
            this.interactionManager.tick();
        }
        GL11.glBindTexture(3553, this.textureManager.getTextureId("/terrain.png"));
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
        if (this.currentScreen != null && !((ScreenInputGrab)this.currentScreen).getDisableInputGrabbing()) {
            this.field_2798 = this.field_2786 + 10000;
        }
        if (this.currentScreen != null && !((ScreenInputGrab)this.currentScreen).getDisableInputGrabbing()) {
            this.currentScreen.method_130();
            if (this.currentScreen != null) {
                this.currentScreen.smokeRenderer.method_351();
                this.currentScreen.tick();
            }
        }
        if (this.currentScreen == null || this.currentScreen.passEvents || ((ScreenInputGrab)this.currentScreen).getDisableInputGrabbing()) {
            while (Mouse.next()) {
                long l = System.currentTimeMillis() - this.lastTickTime;
                if (l > 200L) continue;
                int k = Mouse.getEventDWheel();
                if (k != 0) {
                    boolean altDown;
                    boolean ctrlDown = Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
                    boolean bl = altDown = Keyboard.isKeyDown(56) || Keyboard.isKeyDown(184);
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
                        int t;
                        if (ctrlDown) {
                            t = this.player.inventory.selectedHotbarSlot;
                            this.player.inventory.selectedHotbarSlot = this.player.inventory.offhandItem;
                            this.player.inventory.offhandItem = t;
                        }
                        this.player.inventory.scrollInHotbar(k);
                        if (ctrlDown) {
                            t = this.player.inventory.selectedHotbarSlot;
                            this.player.inventory.selectedHotbarSlot = this.player.inventory.offhandItem;
                            this.player.inventory.offhandItem = t;
                        }
                        if (this.options.field_1445) {
                            this.options.field_1448 += (float) k * 0.25f;
                        }
                    }
                }
                if (this.currentScreen == null || ((ScreenInputGrab)this.currentScreen).getDisableInputGrabbing()) {
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
                    if (this.currentScreen != null && !((ScreenInputGrab)this.currentScreen).getDisableInputGrabbing()) {
                        this.currentScreen.onKeyboardEvent();
                    } else {
                        if (Keyboard.getEventKey() == 1) {
                            this.method_2135();
                        }
                        if (Keyboard.getEventKey() == 31 && Keyboard.isKeyDown(61)) {
                            this.method_2105();
                        }
                        if (Keyboard.getEventKey() == 59) {
                            boolean bl = this.options.hideHud = !this.options.hideHud;
                        }
                        if (Keyboard.getEventKey() == 61) {
                            boolean bl = this.options.debugHud = !this.options.debugHud;
                        }
                        if (Keyboard.getEventKey() == 62) {
                            boolean bl = DebugMode.active = !DebugMode.active;
                            if (DebugMode.active) {
                                this.overlay.addChatMessage("Debug Mode Active");
                            } else {
                                this.overlay.addChatMessage("Debug Mode Deactivated");
                                this.level.loadBrightness();
                            }
                            this.worldRenderer.updateAllTheRenderers();
                        }
                        if (Keyboard.getEventKey() == 63) {
                            boolean bl = this.options.thirdPerson = !this.options.thirdPerson;
                        }
                        if (Keyboard.getEventKey() == 64) {
                            this.worldRenderer.resetAll();
                            this.overlay.addChatMessage("Resetting all blocks in loaded chunks");
                        }
                        if (Keyboard.getEventKey() == 65) {
                            this.player.displayGUIPalette();
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
                                this.level.undo();
                            } else if (Keyboard.getEventKey() == 21) {
                                this.level.redo();
                            }
                        }
                    }
                    for (int i = 0; i < 9; ++i) {
                        if (Keyboard.getEventKey() != 2 + i) continue;
                        if (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)) {
                            if (i == this.player.inventory.selectedHotbarSlot) {
                                this.player.inventory.selectedHotbarSlot = this.player.inventory.offhandItem;
                            }
                            this.player.inventory.offhandItem = i;
                            continue;
                        }
                        if (i == this.player.inventory.offhandItem) {
                            this.player.inventory.offhandItem = this.player.inventory.selectedHotbarSlot;
                        }
                        this.player.inventory.selectedHotbarSlot = i;
                    }
                    if (Keyboard.getEventKey() == this.options.fogKey.key) {
                        this.options.changeOption(Option.RENDER_DISTANCE, !Keyboard.isKeyDown(42) && !Keyboard.isKeyDown(54) ? 1 : -1);
                    }
                }
                if (this.level == null) continue;
                this.level.script.keyboard.processKeyPress(Keyboard.getEventKey());
            }
            if (this.currentScreen == null || this.currentScreen.disableInputGrabbing) {
                if (Mouse.isButtonDown(0) && (float) (this.field_2786 - this.field_2798) >= 0.0f && this.field_2778) {
                    this.method_2107(0);
                }
                if (Mouse.isButtonDown(1) && (float) (this.field_2786 - this.rightMouseTicksRan) >= 0.0f && this.field_2778) {
                    this.method_2107(1);
                }
            }
            this.method_2110(0, (this.currentScreen == null || this.currentScreen.disableInputGrabbing) && Mouse.isButtonDown(0) && this.field_2778);
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
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }
        }
        return null;
    }

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
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
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
            Level world = new Level(mapName, isavehandler, s1, l);
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

    public Level getWorld(String saveName, long l, String mapName) {
        this.setLevel(null);
        System.gc();
        DimensionData isavehandler = this.levelStorage.createDimensionFile(saveName, false);
        Level world = new Level(mapName, isavehandler, saveName, l);
        return world;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void switchDimension() {
        System.out.println("Toggling dimension!!");
        this.player.dimensionId = this.player.dimensionId == -1 ? 0 : -1;
        this.level.removeEntity(this.player);
        this.player.removed = false;
        double d = this.player.x;
        double d1 = this.player.z;
        double d2 = 8.0;
        if (this.player.dimensionId == -1) {
            this.player.setPositionAndAngles(d /= d2, this.player.y, d1 /= d2, this.player.yaw, this.player.pitch);
            if (this.player.isAlive()) {
                this.level.updatePosition(this.player, false);
            }
            Level world = null;
            world = new Level(this.level, Dimension.getByID(-1));
            this.method_2115(world, "Entering the Nether", this.player);
        } else {
            this.player.setPositionAndAngles(d *= d2, this.player.y, d1 *= d2, this.player.yaw, this.player.pitch);
            if (this.player.isAlive()) {
                this.level.updatePosition(this.player, false);
            }
            Level world1 = null;
            world1 = new Level(this.level, Dimension.getByID(0));
            this.method_2115(world1, "Leaving the Nether", this.player);
        }
        this.player.level = this.level;
        if (this.player.isAlive()) {
            this.player.setPositionAndAngles(d, this.player.y, d1, this.player.yaw, this.player.pitch);
            this.level.updatePosition(this.player, false);
            new class_467().method_1530(this.level, this.player);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_2115(Level world, String s, Player entityplayer) {
        this.statManager.method_1991();
        this.statManager.sync();
        this.field_2807 = null;
        this.progressListener.notifyWithGameRunning(s);
        this.progressListener.notifySubMessage("");
        this.soundHelper.method_2010(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        if (this.level != null) {
            this.level.method_280(this.progressListener);
        }
        this.level = world;
        if (world != null) {
            LevelSource ichunkprovider;
            this.level.loadMapTextures();
            this.interactionManager.method_1710(world);
            if (!this.isConnectedToServer()) {
                if (entityplayer == null) {
                    this.player = (ClientPlayer) world.method_278(ClientPlayer.class);
                }
            } else if (this.player != null) {
                this.player.afterSpawn();
                if (world != null) {
                    world.spawnEntity(this.player);
                }
            }
            if (!world.isClient) {
                this.method_2130(s);
            }
            if (this.player == null) {
                this.player = (ClientPlayer) this.interactionManager.createPlayer(world);
                this.player.afterSpawn();
                this.interactionManager.method_1711(this.player);
                this.cutsceneCameraEntity = this.interactionManager.createPlayer(world);
                this.level.script.initPlayer();
            }
            this.player.keypressManager = new MovementManager(this.options);
            if (this.worldRenderer != null) {
                this.worldRenderer.method_1546(world);
            }
            if (this.particleManager != null) {
                this.particleManager.method_323(world);
            }
            this.interactionManager.method_1718(this.player);
            if (entityplayer != null) {
                world.method_285();
            }
            if ((ichunkprovider = world.getLevelSource()) instanceof ClientChunkCache) {
                ClientChunkCache chunkproviderloadorgenerate = (ClientChunkCache) ichunkprovider;
                int i = MathsHelper.floor((int) this.player.x) >> 4;
                int j = MathsHelper.floor((int) this.player.z) >> 4;
                chunkproviderloadorgenerate.setSpawnChunk(i, j);
            }
            world.addPlayer(this.player);
            if (world.generating) {
                world.method_280(this.progressListener);
            }
            this.field_2807 = this.player;
        } else {
            this.player = null;
        }
        System.gc();
        this.lastTickTime = 0L;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_2130(String s) {
        this.progressListener.notifyWithGameRunning(s);
        this.progressListener.notifySubMessage("Building terrain");
        int c = 128;
        int i = 0;
        int j = c * 2 / 16 + 1;
        j *= j;
        LevelSource ichunkprovider = this.level.getLevelSource();
        Vec3i chunkcoordinates = this.level.getSpawnPosition();
        if (this.player != null) {
            chunkcoordinates.x = (int) this.player.x;
            chunkcoordinates.z = (int) this.player.z;
        }
        if (ichunkprovider instanceof ClientChunkCache) {
            ClientChunkCache chunkproviderloadorgenerate = (ClientChunkCache) ichunkprovider;
            chunkproviderloadorgenerate.setSpawnChunk(chunkcoordinates.x >> 4, chunkcoordinates.z >> 4);
        }
        for (int k = -c; k <= c; k += 16) {
            for (int l = -c; l <= c; l += 16) {
                this.progressListener.progressStagePercentage(i++ * 100 / j);
                this.level.getTileId(chunkcoordinates.x + k, 64, chunkcoordinates.z + l);
                while (this.level.method_232()) {
                }
            }
        }
        this.progressListener.notifySubMessage("Simulating world for a bit");
        j = 2000;
        this.level.method_292();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
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
            this.level.script.initPlayer();
        }
        this.worldRenderer.resetForDeath();
        Vec3i spawnCoords = this.level.getSpawnPosition();
        this.player.afterSpawn();
        this.player.setPositionAndAngles((double) spawnCoords.x + 0.5, spawnCoords.y, (double) spawnCoords.z + 0.5, 0.0f, 0.0f);
        this.field_2807 = this.player;
        this.player.afterSpawn();
        this.interactionManager.method_1711(this.player);
        this.level.addPlayer(this.player);
        this.player.keypressManager = new MovementManager(this.options);
        this.player.id = entID;
        this.player.method_494();
        this.player.setRotation(this.level.getSpawnYaw(), 0.0f);
        this.interactionManager.method_1718(this.player);
        this.method_2130("Respawning");
        if (this.currentScreen instanceof DeathScreen) {
            this.openScreen(null);
        }
    }

    public void updateStoreGUI() {
        ScreenScaler scaledresolution = new ScreenScaler(this.options, this.actualWidth, this.actualHeight);
        int i = scaledresolution.getScaledWidth();
        int j = scaledresolution.getScaledHeight();
        this.storeGUI.init((Minecraft)(Object)this, i, j);
    }
}
