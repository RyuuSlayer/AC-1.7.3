package io.github.ryuu.adventurecraft;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.gui.GuiMapSelect;
import io.github.ryuu.adventurecraft.gui.GuiStore;
import io.github.ryuu.adventurecraft.overrides.ItemType;
import io.github.ryuu.adventurecraft.scripting.ScriptEntity;
import io.github.ryuu.adventurecraft.scripting.ScriptItem;
import io.github.ryuu.adventurecraft.scripting.ScriptVec3;
import bt;
import cv;
import cx;
import cz;
import dk;
import ep;
import eq;
import gc;
import ge;
import gt;
import hj;
import ht;
import ia;
import ik;
import in;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import io.github.ryuu.adventurecraft.util.*;
import jg;
import jh;
import kh;
import kj;
import kn;
import kp;
import kq;
import kv;
import lr;
import n;
import net.minecraft.client.*;
import net.minecraft.client.colour.WaterColour;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.model.BipedModel;
import net.minecraft.client.sound.SoundHelper;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.ScreenScaler;
import net.minecraft.client.util.ScreenshotManager;
import net.minecraft.client.util.Session;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ClientPlayer;
import net.minecraft.entity.player.MultiplayerClientPlayer;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.level.chunk.ClientChunkCache;
import net.minecraft.level.dimension.DimensionData;
import net.minecraft.level.source.LevelSource;
import net.minecraft.level.storage.LevelStorage;
import net.minecraft.level.storage.McRegionLevelStorage;
import net.minecraft.level.storage.SessionLockException;
import net.minecraft.network.Connection;
import net.minecraft.stat.StatManager;
import net.minecraft.tile.Tile;
import net.minecraft.util.ProgressListenerError;
import net.minecraft.util.ProgressListenerImpl;
import net.minecraft.util.Vec3i;
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
import org.mozilla.javascript.ScriptableObject;
import ue;
import uo;
import uq;
import ur;
import vf;
import vx;
import vy;
import x;
import xa;
import yb;

public abstract class Minecraft implements Runnable {
    public Minecraft(Component component, Canvas canvas, MinecraftApplet minecraftapplet, int i, int j, boolean flag) {
        this.cameraPause = true;
        this.gcTime = 0;
        this.Q = false;
        this.R = false;
        this.T = new Timer(20.0F);
        this.k = null;
        this.n = true;
        this.o = false;
        this.r = null;
        this.s = new ProgressListenerImpl(this);
        this.V = 0;
        this.W = 0;
        this.u = new ToastManager(this);
        this.w = false;
        this.x = new BipedModel(0.0F);
        this.y = null;
        this.B = new SoundHelper();
        this.ad = new FlowingWaterTextureBinder2();
        this.ae = new FlowingLavaTextureBinder();
        this.J = true;
        this.K = "";
        this.L = false;
        this.M = -1L;
        this.N = false;
        this.ag = 0;
        this.O = false;
        this.P = System.currentTimeMillis();
        this.ah = 0;
        Connection.LOCK;
        this.Y = j;
        this.Q = flag;
        this.A = minecraftapplet;
        new net.minecraft.client.Minecraft.TimerHackThread(this, "Timer hack thread");
        this.m = canvas;
        this.d = i;
        this.e = j;
        this.Q = flag;
        if (minecraftapplet == null || "true".equals(minecraftapplet.getParameter("stand-alone")))
            this.n = false;
        a = this;
        minecraftInstance = this;
        this.tFrameTimes = new long[60];
        this.cutsceneCamera = new CutsceneCamera();
        this.storeGUI = new GuiStore();
    }

    public void b(GameStartupError unexpectedthrowable) {
        this.R = true;
        a(unexpectedthrowable);
    }

    public abstract void a(GameStartupError parammh);

    public void a(String s, int i) {
        this.ab = s;
        this.ac = i;
    }

    public void a() throws LWJGLException {
        if (this.m != null) {
            Graphics g = this.m.getGraphics();
            if (g != null) {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, this.d, this.e);
                g.dispose();
            }
            Display.setParent(this.m);
        } else if (this.Q) {
            Display.setFullscreen(true);
            this.d = Display.getDisplayMode().getWidth();
            this.e = Display.getDisplayMode().getHeight();
            if (this.d <= 0)
                this.d = 1;
            if (this.e <= 0)
                this.e = 1;
        } else {
            Display.setDisplayMode(new DisplayMode(this.d, this.e));
        }
        Display.setTitle(Version.version);
        try {
            Display.create();
        } catch (LWJGLException lwjglexception) {
            lwjglexception.printStackTrace();
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException interruptedexception) {
            }
            Display.create();
        }
        this.Z = b();
        this.aa = new McRegionLevelStorage(new File(this.Z, "saves"));
        this.z = new kv(this, this.Z);
        this.D = new ik(this, this.Z);
        this.mapList = new MapList(this.Z);
        this.p = new TextureManager(this.D, this.z);
        this.q = new TextRenderer(this.z, "/font/default.png", this.p);
        WaterColour.a(this.p.a("/misc/watercolor.png"));
        ia.a(this.p.a("/misc/grasscolor.png"));
        jh.a(this.p.a("/misc/foliagecolor.png"));
        this.t = new GameRenderer(this);
        EntityRenderDispatcher.a.f = new HandItemRenderer(this);
        this.I = new StatManager(this.k, this.Z);
        ep.f.a((gt) new kh(this));
        x();
        Keyboard.create();
        Mouse.create();
        this.C = new vy(this.m);
        try {
            Controllers.create();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        c("Pre startup");
        GL11.glEnable(3553);
        GL11.glShadeModel(7425);
        GL11.glClearDepth(1.0D);
        GL11.glEnable(2929);
        GL11.glDepthFunc(515);
        GL11.glEnable(3008);
        GL11.glAlphaFunc(516, 0.1F);
        GL11.glCullFace(1029);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(5888);
        c("Startup");
        this.S = new cx();
        this.B.a(this.z);
        this.p.a((TextureBinder) this.ae);
        this.p.a((TextureBinder) this.ad);
        this.p.a((TextureBinder) new PortalTextureBinder());
        this.p.a((TextureBinder) new CompassTextureBinder(this));
        this.p.a((TextureBinder) new ClockTextureBinder(this));
        this.p.a((TextureBinder) new FlowingWaterTextureBinder());
        this.p.a((TextureBinder) new FlowingLavaTextureBinder2());
        this.p.a((TextureBinder) new FireTextureBinder(0));
        this.p.a((TextureBinder) new FireTextureBinder(1));
        this.p.a((TextureBinder) new TextureFanFX());
        this.g = new n(this, this.p);
        GL11.glViewport(0, 0, this.d, this.e);
        this.j = new ParticleManager(this.f, this.p);
        try {
            this.U = new cz(this.Z, this);
            this.U.start();
            this.U.a();
        } catch (Exception exception1) {
        }
        c("Post startup");
        this.v = new uq(this);
        if (this.ab != null) {
            a((Screen) new vx(this, this.ab, this.ac));
        } else {
            a(new TitleScreen());
        }
    }

    private void x() throws LWJGLException {
        ScreenScaler scaledresolution = new ScreenScaler(this.z, this.d, this.e);
        GL11.glClear(16640);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, scaledresolution.a, scaledresolution.b, 0.0D, 1000.0D, 3000.0D);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
        GL11.glViewport(0, 0, this.d, this.e);
        GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        Tessellator tessellator = Tessellator.a;
        GL11.glDisable(2896);
        GL11.glEnable(3553);
        GL11.glDisable(2912);
        GL11.glBindTexture(3553, this.p.b("/title/mojang.png"));
        tessellator.b();
        tessellator.b(16777215);
        tessellator.a(0.0D, this.e, 0.0D, 0.0D, 0.0D);
        tessellator.a(this.d, this.e, 0.0D, 0.0D, 0.0D);
        tessellator.a(this.d, 0.0D, 0.0D, 0.0D, 0.0D);
        tessellator.a(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
        tessellator.a();
        char c = ';
        char c1 = ';
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        tessellator.b(16777215);
        a((scaledresolution.a() - c) / 2, (scaledresolution.b() - c1) / 2, 0, 0, c, c1);
        GL11.glDisable(2896);
        GL11.glDisable(2912);
        GL11.glEnable(3008);
        GL11.glAlphaFunc(516, 0.1F);
        Display.swapBuffers();
    }

    public void a(int i, int j, int k, int l, int i1, int j1) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.a;
        tessellator.b();
        tessellator.a((i + 0), (j + j1), 0.0D, ((k + 0) * f), ((l + j1) * f1));
        tessellator.a((i + i1), (j + j1), 0.0D, ((k + i1) * f), ((l + j1) * f1));
        tessellator.a((i + i1), (j + 0), 0.0D, ((k + i1) * f), ((l + 0) * f1));
        tessellator.a((i + 0), (j + 0), 0.0D, ((k + 0) * f), ((l + 0) * f1));
        tessellator.a();
    }

    public static File b() {
        if (af == null)
            af = a("minecraft");
        return af;
    }

    public static File a(String s) {
        File file;
        String s2, s1 = System.getProperty("user.home", ".");
        switch (kn.a[y().ordinal()]) {
            case 1:
            case 2:
                file = new File(s1, '.' + s + '/');
                break;
            case 3:
                s2 = System.getenv("APPDATA");
                if (s2 != null) {
                    file = new File(s2, "." + s + '/');
                    break;
                }
                file = new File(s1, '.' + s + '/');
                break;
            case 4:
                file = new File(s1, "Library/Application Support/" + s);
                break;
            default:
                file = new File(s1, s + '/');
                break;
        }
        if (!file.exists() && !file.mkdirs())
            throw new RuntimeException("The working directory could not be created: " + file);
        return file;
    }

    private static OperatingSystem y() {
        String s = System.getProperty("os.name").toLowerCase();
        if (s.contains("win"))
            return OperatingSystem.c;
        if (s.contains("mac"))
            return OperatingSystem.d;
        if (s.contains("solaris"))
            return OperatingSystem.b;
        if (s.contains("sunos"))
            return OperatingSystem.b;
        if (s.contains("linux"))
            return OperatingSystem.a;
        if (s.contains("unix"))
            return OperatingSystem.a;
        return OperatingSystem.e;
    }

    public LevelStorage c() {
        return this.aa;
    }

    public void a(Screen guiscreen) {
        TitleScreen TitleScreen;
        DeathScreen DeathScreen;
        if (this.r instanceof ce)
            return;
        if (this.r != null)
            this.r.h();
        if (guiscreen instanceof TitleScreen)
            this.I.b();
        this.I.c();
        if (guiscreen == null && this.f == null) {
            TitleScreen = new TitleScreen();
        } else if (TitleScreen == null && this.h.Y <= 0) {
            DeathScreen = new DeathScreen();
        }
        if (DeathScreen instanceof TitleScreen)
            this.v.b();
        this.r = DeathScreen;
        if (DeathScreen != null) {
            h();
            ScreenScaler scaledresolution = new ScreenScaler(this.z, this.d, this.e);
            int i = scaledresolution.a();
            int j = scaledresolution.b();
            DeathScreen.a(this, i, j);
            this.w = false;
        } else {
            g();
        }
    }

    private void c(String s) {
        int i = GL11.glGetError();
        if (i != 0) {
            String s1 = GLU.gluErrorString(i);
            System.out.println("########## GL ERROR ##########");
            System.out.println("@ " + s);
            System.out.println(i + ": " + s1);
        }
    }

    public void d() {
        try {
            this.I.b();
            this.I.c();
            if (this.A != null)
                this.A.c();
            try {
                if (this.U != null)
                    this.U.b();
            } catch (Exception exception) {
            }
            System.out.println("Stopping!");
            try {
                a((Level) null);
            } catch (Throwable throwable) {
            }
            try {
                ge.a();
            } catch (Throwable throwable1) {
            }
            this.B.b();
            Mouse.destroy();
            Keyboard.destroy();
        } finally {
            Display.destroy();
            if (!this.R)
                System.exit(0);
        }
        System.gc();
    }

    public long getAvgFrameTime() {
        if (this.tFrameTimes[this.nextFrameTime] != 0L)
            return (this.prevFrameTimeForAvg - this.tFrameTimes[this.nextFrameTime]) / 60L;
        return 23333333L;
    }

    public void run() {
        this.J = true;
        try {
            a();
        } catch (Exception exception) {
            exception.printStackTrace();
            b(new GameStartupError("Failed to start game", exception));
            return;
        }
        try {
            this.p.b("/terrain.png");
            this.p.b("/terrain2.png");
            this.p.b("/terrain3.png");
            ContextFactory.initGlobal(new ContextFactory());
            long l = System.currentTimeMillis();
            int i = 0;
            while (this.J) {
                try {
                    if (this.A != null && !this.A.isActive())
                        break;
                    eq.b();
                    bt.b();
                    if (this.m == null && Display.isCloseRequested())
                        f();
                    if (this.o && this.f != null) {
                        float f = this.T.c;
                        this.T.a();
                        this.T.c = f;
                    } else {
                        this.T.a();
                    }
                    long l1 = System.nanoTime();
                    for (int j = 0; j < this.T.b; j++) {
                        this.V++;
                        try {
                            k();
                        } catch (SessionLockException minecraftexception1) {
                            this.f = null;
                            a((Level) null);
                            a(new LevelSaveConflictScreen());
                        }
                    }
                    long l2 = System.nanoTime() - l1;
                    c("Pre render");
                    cv.a = this.z.j;
                    if (!this.cameraActive) {
                        this.B.a((LivingEntity) this.h, this.T.c);
                    } else {
                        this.B.a(this.cutsceneCameraEntity, this.T.c);
                    }
                    GL11.glEnable(3553);
                    if (this.f != null)
                        this.f.j();
                    if (!Keyboard.isKeyDown(65))
                        Display.update();
                    if (this.h != null && this.h.L())
                        this.z.A = false;
                    if (!this.w) {
                        if (this.c != null)
                            this.c.a(this.T.c);
                        this.t.b(this.T.c);
                    }
                    if (!Display.isActive()) {
                        if (this.Q)
                            j();
                        Thread.sleep(10L);
                    }
                    if (this.z.B) {
                        a(l2);
                    } else {
                        this.M = System.nanoTime();
                    }
                    Thread.yield();
                    if (Keyboard.isKeyDown(65))
                        Display.update();
                    z();
                    if (this.m != null && !this.Q && (this.m.getWidth() != this.d || this.m.getHeight() != this.e)) {
                        this.d = this.m.getWidth();
                        this.e = this.m.getHeight();
                        if (this.d <= 0)
                            this.d = 1;
                        if (this.e <= 0)
                            this.e = 1;
                        a(this.d, this.e);
                    }
                    c("Post render");
                    i++;
                    this.o = (!l() && this.r != null && this.r.c());
                    while (System.currentTimeMillis() >= l + 1000L) {
                        this.K = i + " fps, " + dk.b + " chunk updates";
                        dk.b = 0;
                        l += 1000L;
                        i = 0;
                    }
                    this.prevFrameTimeForAvg = System.nanoTime();
                    this.tFrameTimes[this.nextFrameTime] = this.prevFrameTimeForAvg;
                    this.nextFrameTime = (this.nextFrameTime + 1) % 60;
                } catch (SessionLockException minecraftexception) {
                    this.f = null;
                    a((Level) null);
                    a(new LevelSaveConflictScreen());
                } catch (OutOfMemoryError outofmemoryerror) {
                    outofmemoryerror.printStackTrace();
                    e();
                    a((Screen) new x());
                    System.gc();
                }
            }
        } catch (ProgressListenerError minecrafterror) {

        } catch (Throwable throwable) {
            e();
            throwable.printStackTrace();
            b(new GameStartupError("Unexpected error", throwable));
        } finally {
            ContextFactory.getGlobal().enterContext();
            Context.exit();
            d();
        }
    }

    public void e() {
        try {
            b = new byte[0];
            this.g.f();
        } catch (Throwable throwable) {
        }
        try {
            System.gc();
            eq.a();
            bt.a();
        } catch (Throwable throwable1) {
        }
        try {
            System.gc();
            a((Level) null);
        } catch (Throwable throwable2) {
        }
        System.gc();
    }

    private void z() {
        if (Keyboard.isKeyDown(60)) {
            if (!this.L) {
                this.L = true;
                this.v.a(ScreenshotManager.takeScreenshot(af, this.d, this.e));
            }
        } else {
            this.L = false;
        }
    }

    private void a(long l) {
        long l1 = 16666666L;
        if (this.M == -1L)
            this.M = System.nanoTime();
        long l2 = System.nanoTime();
        F[G & E.length - 1] = l;
        updateTimes[G & updateTimes.length - 1] = updateRendererTime;
        E[G++ & E.length - 1] = l2 - this.M;
        this.M = l2;
        GL11.glClear(256);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, this.d, this.e, 0.0D, 1000.0D, 3000.0D);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
        GL11.glLineWidth(1.0F);
        GL11.glDisable(3553);
        Tessellator tessellator = Tessellator.a;
        tessellator.a(7);
        int i = (int) (l1 / 200000L);
        tessellator.b(536870912);
        tessellator.a(0.0D, (this.e - i), 0.0D);
        tessellator.a(0.0D, this.e, 0.0D);
        tessellator.a(E.length, this.e, 0.0D);
        tessellator.a(E.length, (this.e - i), 0.0D);
        tessellator.b(538968064);
        tessellator.a(0.0D, (this.e - i * 2), 0.0D);
        tessellator.a(0.0D, (this.e - i), 0.0D);
        tessellator.a(E.length, (this.e - i), 0.0D);
        tessellator.a(E.length, (this.e - i * 2), 0.0D);
        tessellator.a();
        long l3 = 0L;
        for (int j = 0; j < E.length; j++)
            l3 += E[j];
        int k = (int) (l3 / 200000L / E.length);
        tessellator.a(7);
        tessellator.b(541065216);
        tessellator.a(0.0D, (this.e - k), 0.0D);
        tessellator.a(0.0D, this.e, 0.0D);
        tessellator.a(E.length, this.e, 0.0D);
        tessellator.a(E.length, (this.e - k), 0.0D);
        tessellator.a();
        tessellator.a(1);
        for (int i1 = 0; i1 < E.length; i1++) {
            int j1 = (i1 - G & E.length - 1) * 255 / E.length;
            int k1 = j1 * j1 / 255;
            k1 = k1 * k1 / 255;
            int i2 = k1 * k1 / 255;
            i2 = i2 * i2 / 255;
            if (E[i1] > l1) {
                tessellator.b(-16777216 + k1 * 65536);
            } else {
                tessellator.b(-16777216 + k1 * 256);
            }
            long uTime = updateTimes[i1] / 200000L;
            long l4 = E[i1] / 200000L;
            long l5 = F[i1] / 200000L;
            tessellator.a((i1 + 0.5F), ((float) (this.e - l4) + 0.5F), 0.0D);
            tessellator.a((i1 + 0.5F), (this.e + 0.5F), 0.0D);
            tessellator.b(k1 * 1);
            tessellator.a((i1 + 0.5F), ((float) (this.e - l4 - l5) + 0.5F), 0.0D);
            tessellator.a((i1 + 0.5F), ((float) (this.e - l4 - l5 - uTime) + 0.5F), 0.0D);
            tessellator.b(-16777216 + k1 * 65536 + k1 * 256 + k1 * 1);
            tessellator.a((i1 + 0.5F), ((float) (this.e - l4) + 0.5F), 0.0D);
            tessellator.a((i1 + 0.5F), ((float) (this.e - l4 - l5) + 0.5F), 0.0D);
        }
        tessellator.a();
        GL11.glEnable(3553);
    }

    public void f() {
        this.J = false;
    }

    public void g() {
        if (!Display.isActive())
            return;
        if (this.N)
            return;
        this.N = true;
        this.C.a();
        a((Screen) null);
        this.ag = this.V + 10000;
    }

    public void h() {
        if (!this.N)
            return;
        if (this.h != null)
            this.h.o_();
        this.N = false;
        this.C.b();
    }

    public void i() {
        if (this.r != null)
            return;
        a(new PauseScreen());
    }

    private void a(int i, boolean flag) {
        if (this.c.b)
            return;
        if (i == 0 && this.W > 0)
            return;
        if (flag && this.y != null && this.y.a == jg.a && i == 0) {
            int j = this.y.b;
            int k = this.y.c;
            int l = this.y.d;
            this.c.c(j, k, l, this.y.e);
            this.j.a(j, k, l, this.y.e);
        } else {
            this.c.a();
        }
    }

    private void a(int i) {
        if (i == 0 && this.W > 0)
            return;
        if (DebugMode.active)
            this.f.undoStack.startRecording();
        boolean swapItemBack = false;
        ItemInstance itemUsing = this.h.c.b();
        HandItemRenderer itemRenderer = this.t.c;
        if (!DebugMode.active) {
            if (i == 0) {
                itemUsing = this.h.c.getOffhandItem();
                itemRenderer = this.t.offHandItemRenderer;
                this.h.c.swapOffhandWithMain();
                swapItemBack = true;
                this.h.swappedItems = true;
            }
            int itemUseDelay = 5;
            if (itemUsing != null)
                itemUseDelay = (ItemType.c[itemUsing.c]).itemUseDelay;
            if (i == 0) {
                this.ag = this.V + itemUseDelay;
            } else {
                this.rightMouseTicksRan = this.V + itemUseDelay;
            }
            if (itemUsing != null && ItemType.c[itemUsing.c].mainActionLeftClick()) {
                i = 0;
            } else {
                i = 1;
            }
        } else {
            this.ag = this.V + 5;
            this.rightMouseTicksRan = this.V + 5;
        }
        if (i == 0)
            this.h.J();
        boolean flag = true;
        if (this.y == null) {
            if (i == 0 && !(this.c instanceof pj))
                this.W = 10;
        } else if (this.y.a == jg.b) {
            if (i == 0)
                this.c.b((Player) this.h, this.y.g);
            if (i == 1)
                this.c.a((Player) this.h, this.y.g);
        } else if (this.y.a == jg.a) {
            int j = this.y.b;
            int k = this.y.c;
            int l = this.y.d;
            int i1 = this.y.e;
            Tile block = Tile.m[this.f.a(j, k, l)];
            if (!DebugMode.active && (block.bn == Tile.av.bn || block.bn == Blocks.store.bn))
                i = 1;
            if (block != null) {
                if (!DebugMode.active) {
                    int alwaysClick = block.alwaysUseClick(this.f, j, k, l);
                    if (alwaysClick != -1)
                        i = alwaysClick;
                }
                if (i == 0) {
                    this.c.a(j, k, l, this.y.e);
                    if (itemUsing != null)
                        itemUsing.useItemLeftClick((Player) this.h, this.f, j, k, l, i1);
                } else {
                    int j1 = (itemUsing == null) ? 0 : itemUsing.a;
                    if (this.c.a((Player) this.h, this.f, itemUsing, j, k, l, i1)) {
                        flag = false;
                        this.h.J();
                    }
                    if (itemUsing == null) {
                        if (swapItemBack) {
                            this.h.c.swapOffhandWithMain();
                            this.h.swappedItems = false;
                        }
                        if (DebugMode.active)
                            this.f.undoStack.stopRecording();
                        return;
                    }
                    if (itemUsing.a == 0 && itemUsing == this.h.c.a[this.h.c.c]) {
                        this.h.c.a[this.h.c.c] = null;
                    } else if (itemUsing.a != j1) {
                        this.t.c.b();
                    }
                }
            }
        }
        if (flag && i == 0)
            if (itemUsing != null && ItemType.c[itemUsing.c] != null)
                ItemType.c[itemUsing.c].onItemLeftClick(itemUsing, this.f, (Player) this.h);
        if (flag && i == 1)
            if (itemUsing != null && this.c.a((Player) this.h, this.f, itemUsing))
                this.t.c.c();
        if (itemUsing != null) {
            if (this.lastItemUsed != itemUsing) {
                Object wrappedOut = Context.javaToJS(new ScriptItem(itemUsing), this.f.script.globalScope);
                ScriptableObject.putProperty(this.f.script.globalScope, "lastItemUsed", wrappedOut);
                this.lastItemUsed = itemUsing;
            }
            if (this.y == null) {
                if (this.lastEntityHit != null) {
                    this.lastEntityHit = null;
                    Object wrappedOut = Context.javaToJS(null, this.f.script.globalScope);
                    ScriptableObject.putProperty(this.f.script.globalScope, "hitEntity", wrappedOut);
                }
                if (this.lastBlockHit != null) {
                    this.lastBlockHit = null;
                    Object wrappedOut = Context.javaToJS(null, this.f.script.globalScope);
                    ScriptableObject.putProperty(this.f.script.globalScope, "hitBlock", wrappedOut);
                }
            } else if (this.y.a == jg.b) {
                if (this.lastEntityHit != this.y.g) {
                    this.lastEntityHit = this.y.g;
                    Object wrappedOut = Context.javaToJS(ScriptEntity.getEntityClass(this.y.g), this.f.script.globalScope);
                    ScriptableObject.putProperty(this.f.script.globalScope, "hitEntity", wrappedOut);
                }
                if (this.lastBlockHit != null) {
                    this.lastBlockHit = null;
                    Object wrappedOut = Context.javaToJS(null, this.f.script.globalScope);
                    ScriptableObject.putProperty(this.f.script.globalScope, "hitBlock", wrappedOut);
                }
            } else if (this.y.a == jg.a) {
                if (this.lastBlockHit == null || this.lastBlockHit.x != this.y.b || this.lastBlockHit.y != this.y.c || this.lastBlockHit.z != this.y.d) {
                    this.lastBlockHit = new ScriptVec3(this.y.b, this.y.c, this.y.d);
                    Object wrappedOut = Context.javaToJS(this.lastBlockHit, this.f.script.globalScope);
                    ScriptableObject.putProperty(this.f.script.globalScope, "hitBlock", wrappedOut);
                }
                if (this.lastEntityHit != null) {
                    this.lastEntityHit = null;
                    Object wrappedOut = Context.javaToJS(null, this.f.script.globalScope);
                    ScriptableObject.putProperty(this.f.script.globalScope, "hitEntity", wrappedOut);
                }
            } else {
                if (this.lastEntityHit != null) {
                    this.lastEntityHit = null;
                    Object wrappedOut = Context.javaToJS(null, this.f.script.globalScope);
                    ScriptableObject.putProperty(this.f.script.globalScope, "hitEntity", wrappedOut);
                }
                if (this.lastBlockHit != null) {
                    this.lastBlockHit = null;
                    Object wrappedOut = Context.javaToJS(null, this.f.script.globalScope);
                    ScriptableObject.putProperty(this.f.script.globalScope, "hitBlock", wrappedOut);
                }
            }
            if (itemUsing.f()) {
                this.f.scriptHandler.runScript(String.format("item_%d_%d.js", new Object[]{Integer.valueOf(itemUsing.c), Integer.valueOf(itemUsing.i())}), this.f.scope, false);
            } else {
                this.f.scriptHandler.runScript(String.format("item_%d.js", new Object[]{Integer.valueOf(itemUsing.c)}), this.f.scope, false);
            }
        }
        if (swapItemBack) {
            this.h.c.swapOffhandWithMain();
            this.h.swappedItems = false;
        }
        if (DebugMode.active)
            this.f.undoStack.stopRecording();
    }

    public void j() {
        try {
            this.Q = !this.Q;
            if (this.Q) {
                Display.setDisplayMode(Display.getDesktopDisplayMode());
                this.d = Display.getDisplayMode().getWidth();
                this.e = Display.getDisplayMode().getHeight();
                if (this.d <= 0)
                    this.d = 1;
                if (this.e <= 0)
                    this.e = 1;
            } else {
                if (this.m != null) {
                    this.d = this.m.getWidth();
                    this.e = this.m.getHeight();
                } else {
                    this.d = this.X;
                    this.e = this.Y;
                }
                if (this.d <= 0)
                    this.d = 1;
                if (this.e <= 0)
                    this.e = 1;
            }
            if (this.r != null)
                a(this.d, this.e);
            Display.setFullscreen(this.Q);
            Display.update();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void a(int i, int j) {
        if (i <= 0)
            i = 1;
        if (j <= 0)
            j = 1;
        this.d = i;
        this.e = j;
        ScreenScaler scaledresolution = new ScreenScaler(this.z, i, j);
        int k = scaledresolution.a();
        int l = scaledresolution.b();
        if (this.r != null)
            this.r.a(this, k, l);
        this.storeGUI.a(this, k, l);
    }

    private void A() {
        if (this.y != null) {
            int i = this.f.a(this.y.b, this.y.c, this.y.d);
            if (i == Tile.v.bn)
                i = Tile.w.bn;
            if (i == Tile.ak.bn)
                i = Tile.al.bn;
            if (i == Tile.A.bn)
                i = Tile.u.bn;
            this.h.c.a(i, this.c instanceof pj);
        }
    }

    private void B() {
        (new kp(this)).start();
    }

    public void k() {
        if (this.V == 6000)
            B();
        this.v.a();
        this.t.a(1.0F);
        if (this.h != null) {
            LevelSource ichunkprovider = this.f.w();
            if (ichunkprovider instanceof ClientChunkCache) {
                ClientChunkCache chunkproviderloadorgenerate = (ClientChunkCache) ichunkprovider;
                int j = in.d((int) this.h.aM) >> 4;
                int i1 = in.d((int) this.h.aO) >> 4;
                chunkproviderloadorgenerate.d(j, i1);
            }
        }
        if (!this.o && this.f != null)
            this.c.c();
        GL11.glBindTexture(3553, this.p.b("/terrain.png"));
        if (!this.o)
            this.p.a();
        if (this.r == null && this.h != null) {
            if (this.h.Y <= 0) {
                a((Screen) null);
            } else if (this.h.N() && this.f != null && this.f.B) {
                a(new SleepingChatScreen());
            }
        } else if (this.r != null && this.r instanceof SleepingChatScreen && !this.h.N()) {
            a((Screen) null);
        }
        if (this.r != null && !this.r.disableInputGrabbing)
            this.ag = this.V + 10000;
        if (this.r != null && !this.r.disableInputGrabbing) {
            this.r.e();
            if (this.r != null) {
                this.r.h.a();
                this.r.a();
            }
        }
        if (this.r == null || this.r.f || this.r.disableInputGrabbing) {
            while (Mouse.next()) {
                long l = System.currentTimeMillis() - this.P;
                if (l <= 200L) {
                    int k = Mouse.getEventDWheel();
                    if (k != 0) {
                        boolean ctrlDown = (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157));
                        boolean altDown = (Keyboard.isKeyDown(56) || Keyboard.isKeyDown(184));
                        if (k > 0)
                            k = 1;
                        if (k < 0)
                            k = -1;
                        if (DebugMode.active && altDown) {
                            DebugMode.reachDistance += k;
                            DebugMode.reachDistance = Math.min(Math.max(DebugMode.reachDistance, 2), 100);
                            this.v.a(String.format("Reach Changed to %d", new Object[]{Integer.valueOf(DebugMode.reachDistance)}));
                        } else {
                            if (ctrlDown) {
                                int t = this.h.c.c;
                                this.h.c.c = this.h.c.offhandItem;
                                this.h.c.offhandItem = t;
                            }
                            this.h.c.b(k);
                            if (ctrlDown) {
                                int t = this.h.c.c;
                                this.h.c.c = this.h.c.offhandItem;
                                this.h.c.offhandItem = t;
                            }
                            if (this.z.D)
                                this.z.G += k * 0.25F;
                        }
                    }
                    if (this.r == null || this.r.disableInputGrabbing) {
                        if (!this.N && Mouse.getEventButtonState()) {
                            g();
                            continue;
                        }
                        if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState())
                            a(0);
                        if (Mouse.getEventButton() == 1 && Mouse.getEventButtonState())
                            a(1);
                        if (Mouse.getEventButton() == 2 && Mouse.getEventButtonState())
                            A();
                        continue;
                    }
                    if (this.r != null)
                        this.r.f();
                }
            }
            if (this.W > 0)
                this.W--;
            while (Keyboard.next()) {
                this.h.a(Keyboard.getEventKey(), Keyboard.getEventKeyState());
                if (Keyboard.getEventKeyState()) {
                    if (Keyboard.getEventKey() == 87) {
                        j();
                    } else {
                        if (this.r != null && !this.r.disableInputGrabbing) {
                            this.r.g();
                        } else {
                            if (Keyboard.getEventKey() == 1)
                                i();
                            if (Keyboard.getEventKey() == 31 && Keyboard.isKeyDown(61))
                                C();
                            if (Keyboard.getEventKey() == 59)
                                this.z.z = !this.z.z;
                            if (Keyboard.getEventKey() == 61)
                                this.z.B = !this.z.B;
                            if (Keyboard.getEventKey() == 62) {
                                DebugMode.active = !DebugMode.active;
                                if (DebugMode.active) {
                                    this.v.a("Debug Mode Active");
                                } else {
                                    this.v.a("Debug Mode Deactivated");
                                    this.f.loadBrightness();
                                }
                                this.g.updateAllTheRenderers();
                            }
                            if (Keyboard.getEventKey() == 63)
                                this.z.A = !this.z.A;
                            if (Keyboard.getEventKey() == 64) {
                                this.g.resetAll();
                                this.v.a("Resetting all blocks in loaded chunks");
                            }
                            if (Keyboard.getEventKey() == 65)
                                this.h.displayGUIPalette();
                            if (Keyboard.getEventKey() == this.z.r.b)
                                a((Screen) new ue((Player) this.h));
                            if (Keyboard.getEventKey() == this.z.s.b)
                                this.h.D();
                            if ((l() || DebugMode.active) && Keyboard.getEventKey() == this.z.t.b)
                                a((Screen) new gc());
                            if (DebugMode.active && (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)))
                                if (Keyboard.getEventKey() == 44) {
                                    this.f.undo();
                                } else if (Keyboard.getEventKey() == 21) {
                                    this.f.redo();
                                }
                        }
                        for (int i = 0; i < 9; i++) {
                            if (Keyboard.getEventKey() == 2 + i)
                                if (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)) {
                                    if (i == this.h.c.c)
                                        this.h.c.c = this.h.c.offhandItem;
                                    this.h.c.offhandItem = i;
                                } else {
                                    if (i == this.h.c.offhandItem)
                                        this.h.c.offhandItem = this.h.c.c;
                                    this.h.c.c = i;
                                }
                        }
                        if (Keyboard.getEventKey() == this.z.u.b)
                            this.z.a(ht.e, (!Keyboard.isKeyDown(42) && !Keyboard.isKeyDown(54)) ? 1 : -1);
                    }
                    if (this.f != null)
                        this.f.script.keyboard.processKeyPress(Keyboard.getEventKey());
                }
            }
            if (this.r == null || this.r.disableInputGrabbing) {
                if (Mouse.isButtonDown(0) && (this.V - this.ag) >= 0.0F && this.N)
                    a(0);
                if (Mouse.isButtonDown(1) && (this.V - this.rightMouseTicksRan) >= 0.0F && this.N)
                    a(1);
            }
            a(0, ((this.r == null || this.r.disableInputGrabbing) && Mouse.isButtonDown(0) && this.N));
        }
        if (this.f != null) {
            if (this.h != null) {
                this.ah++;
                if (this.ah == 30) {
                    this.ah = 0;
                    this.f.g((Entity) this.h);
                }
            }
            this.f.q = this.z.y;
            if (this.f.B)
                this.f.q = 3;
            if (!this.o)
                this.t.a();
            if (!this.o)
                this.g.d();
            if (!this.o) {
                if (this.f.n > 0)
                    this.f.n--;
                this.f.g();
            }
            if (!this.o || l()) {
                this.f.a((this.z.y > 0), true);
                this.f.l();
            }
            if (!this.o && this.f != null)
                this.f.q(in.b(this.h.aM), in.b(this.h.aN), in.b(this.h.aO));
            if (!this.o)
                this.j.a();
        }
        this.P = System.currentTimeMillis();
        if (++this.gcTime > 1800) {
            this.gcTime = 0;
            System.gc();
        }
    }

    private void C() {
        System.out.println("FORCING RELOAD!");
        this.B = new SoundHelper();
        this.B.a(this.z);
        this.U.a();
    }

    public boolean l() {
        return (this.f != null && this.f.B);
    }

    public String getMapUsed(String s) {
        File mcDir = b();
        File saveDir = new File(mcDir, "saves");
        File worldDir = new File(saveDir, s);
        File mapTxt = new File(worldDir, "map.txt");
        if (mapTxt.exists())
            try {
                BufferedReader input = new BufferedReader(new FileReader(mapTxt));
                String result = input.readLine();
                input.close();
                return result;
            } catch (FileNotFoundException e) {

            } catch (IOException e) {
            }
        return null;
    }

    public void saveMapUsed(String s, String mapName) {
        File mcDir = b();
        File saveDir = new File(mcDir, "saves");
        File worldDir = new File(saveDir, s);
        worldDir.mkdirs();
        File mapTxt = new File(worldDir, "map.txt");
        try {
            if (mapTxt.exists())
                mapTxt.delete();
            mapTxt.createNewFile();
            BufferedWriter output = new BufferedWriter(new FileWriter(mapTxt));
            output.write(mapName);
            output.close();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
        }
    }

    public void a(String s, String s1, long l) {
        String mapName = getMapUsed(s);
        if (in.a(mapName)) {
            a(new GuiMapSelect(null, s));
        } else {
            startWorld(s, s1, l, mapName);
        }
    }

    public void startWorld(String s, String s1, long l, String mapName) {
        a((Level) null);
        System.gc();
        if (s != null && this.aa.a(s)) {
            b(s, s1);
        } else {
            DebugMode.active = false;
            DebugMode.levelEditing = false;
            DimensionData isavehandler = null;
            if (s != null)
                isavehandler = this.aa.a(s, false);
            if (s1 == null)
                s1 = "Map Editing";
            Level world = new Level(mapName, isavehandler, s1, l);
            if (world.s) {
                this.I.a(Connection.lock, 1);
                this.I.a(Connection.f, 1);
                a(world, "Generating level");
            } else {
                this.I.a(Connection.socket, 1);
                this.I.a(Connection.f, 1);
                a(world, "Loading level");
            }
        }
        a((Screen) null);
    }

    public Level getWorld(String saveName, long l, String mapName) {
        a((Level) null);
        System.gc();
        DimensionData isavehandler = this.aa.a(saveName, false);
        Level world = new Level(mapName, isavehandler, saveName, l);
        return world;
    }

    public void m() {
        System.out.println("Toggling dimension!!");
        if (this.h.m == -1) {
            this.h.m = 0;
        } else {
            this.h.m = -1;
        }
        this.f.e((Entity) this.h);
        this.h.be = false;
        double d = this.h.aM;
        double d1 = this.h.aO;
        double d2 = 8.0D;
        if (this.h.m == -1) {
            d /= d2;
            d1 /= d2;
            this.h.c(d, this.h.aN, d1, this.h.aS, this.h.aT);
            if (this.h.W())
                this.f.a((Entity) this.h, false);
            Level world = null;
            world = new Level(this.f, xa.a(-1));
            a(world, "Entering the Nether", (Player) this.h);
        } else {
            d *= d2;
            d1 *= d2;
            this.h.c(d, this.h.aN, d1, this.h.aS, this.h.aT);
            if (this.h.W())
                this.f.a((Entity) this.h, false);
            Level world1 = null;
            world1 = new Level(this.f, xa.a(0));
            a(world1, "Leaving the Nether", (Player) this.h);
        }
        this.h.aI = this.f;
        if (this.h.W()) {
            this.h.c(d, this.h.aN, d1, this.h.aS, this.h.aT);
            this.f.a((Entity) this.h, false);
            (new ur()).a(this.f, (Entity) this.h);
        }
    }

    public void a(Level world) {
        a(world, "");
    }

    public void a(Level world, String s) {
        a(world, s, null);
    }

    public void a(Level world, String s, Player entityplayer) {
        this.I.b();
        this.I.c();
        this.i = null;
        this.s.a(s);
        this.s.d("");
        this.B.a(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        if (this.f != null)
            this.f.a((yb) this.s);
        this.f = world;
        if (world != null) {
            this.f.loadMapTextures();
            this.c.a(world);
            if (!l()) {
                if (entityplayer == null)
                    this.h = (ClientPlayer) world.a(ClientPlayer.class);
            } else if (this.h != null) {
                this.h.t_();
                if (world != null)
                    world.b((Entity) this.h);
            }
            if (!world.B)
                d(s);
            if (this.h == null) {
                this.h = (ClientPlayer) this.c.b(world);
                this.h.t_();
                this.c.a((Player) this.h);
                this.cutsceneCameraEntity = (LivingEntity) this.c.b(world);
                this.f.script.initPlayer();
            }
            this.h.a = (uo) new lr(this.z);
            if (this.g != null)
                this.g.a(world);
            if (this.j != null)
                this.j.a(world);
            this.c.b((Player) this.h);
            if (entityplayer != null)
                world.e();
            LevelSource ichunkprovider = world.w();
            if (ichunkprovider instanceof ClientChunkCache) {
                ClientChunkCache chunkproviderloadorgenerate = (ClientChunkCache) ichunkprovider;
                int i = in.d((int) this.h.aM) >> 4;
                int j = in.d((int) this.h.aO) >> 4;
                chunkproviderloadorgenerate.d(i, j);
            }
            world.a((Player) this.h);
            if (world.s)
                world.a((yb) this.s);
            this.i = this.h;
        } else {
            this.h = null;
        }
        System.gc();
        this.P = 0L;
    }

    private void b(String s, String s1) {
        this.s.a("Converting World to " + this.aa.a());
        this.s.d("This may take a while :)");
        this.aa.a(s, (yb) this.s);
        a(s, s1, 0L);
    }

    private void d(String s) {
        this.s.a(s);
        this.s.d("Building terrain");
        char c = ';
        int i = 0;
        int j = c * 2 / 16 + 1;
        j *= j;
        LevelSource ichunkprovider = this.f.w();
        Vec3i chunkcoordinates = this.f.u();
        if (this.h != null) {
            chunkcoordinates.a = (int) this.h.aM;
            chunkcoordinates.c = (int) this.h.aO;
        }
        if (ichunkprovider instanceof ClientChunkCache) {
            ClientChunkCache chunkproviderloadorgenerate = (ClientChunkCache) ichunkprovider;
            chunkproviderloadorgenerate.d(chunkcoordinates.a >> 4, chunkcoordinates.c >> 4);
        }
        for (int k = -c; k <= c; k += 16) {
            for (int l = -c; l <= c; l += 16) {
                this.s.a(i++ * 100 / j);
                this.f.a(chunkcoordinates.a + k, 64, chunkcoordinates.c + l);
                while (this.f.j()) ;
            }
        }
        this.s.d("Simulating world for a bit");
        j = 2000;
        this.f.p();
    }

    public void a(String s, File file) {
        int i = s.indexOf("/");
        String s1 = s.substring(0, i);
        s = s.substring(i + 1);
        if (s1.equalsIgnoreCase("sound")) {
            this.B.a(s, file);
        } else if (s1.equalsIgnoreCase("newsound")) {
            this.B.a(s, file);
        } else if (s1.equalsIgnoreCase("streaming")) {
            this.B.b(s, file);
        } else if (s1.equalsIgnoreCase("music")) {
            this.B.c(s, file);
        } else if (s1.equalsIgnoreCase("newmusic")) {
            this.B.c(s, file);
        }
    }

    public cx n() {
        return this.S;
    }

    public String o() {
        return this.g.b();
    }

    public String p() {
        return this.g.c();
    }

    public String q() {
        return this.f.i();
    }

    public String r() {
        return "P: " + this.j.b() + ". T: " + this.f.h();
    }

    public void a(boolean flag, int i) {
        if (!this.f.B && !this.f.t.f())
            m();
        Vec3i chunkcoordinates = this.f.u();
        LevelSource ichunkprovider = this.f.w();
        if (ichunkprovider instanceof ClientChunkCache) {
            ClientChunkCache chunkproviderloadorgenerate = (ClientChunkCache) ichunkprovider;
            chunkproviderloadorgenerate.d(chunkcoordinates.a >> 4, chunkcoordinates.c >> 4);
        }
        this.f.v();
        int entID = 0;
        if (this.h != null) {
            entID = this.h.aD;
            this.f.e((Entity) this.h);
        } else {
            this.h = (ClientPlayer) this.c.b(this.f);
            this.f.script.initPlayer();
        }
        this.g.resetForDeath();
        Vec3i spawnCoords = this.f.u();
        this.h.t_();
        this.h.c(spawnCoords.a + 0.5D, spawnCoords.b, spawnCoords.c + 0.5D, 0.0F, 0.0F);
        this.i = this.h;
        this.h.t_();
        this.c.a((Player) this.h);
        this.f.a((Player) this.h);
        this.h.a = (uo) new lr(this.z);
        this.h.aD = entID;
        this.h.v();
        this.h.c(this.f.getSpawnYaw(), 0.0F);
        this.c.b((Player) this.h);
        d("Respawning");
        if (this.r instanceof DeathScreen)
            a((Screen) null);
    }

    public static void a(String s, String s1) {
        a(s, s1, null);
    }

    public static void a(String s, String s1, String s2) {
        boolean flag = false;
        String s3 = s;
        Frame frame = new Frame("Minecraft");
        Canvas canvas = new Canvas();
        frame.setLayout(new BorderLayout());
        frame.add(canvas, "Center");
        canvas.setPreferredSize(new Dimension(854, 480));
        frame.pack();
        frame.setLocationRelativeTo(null);
        kq minecraftimpl = new kq(frame, canvas, null, 854, 480, flag, frame);
        Thread thread = new Thread((Runnable) minecraftimpl, "Minecraft main thread");
        thread.setPriority(10);
        minecraftimpl.l = "www.minecraft.net";
        if (s3 != null && s1 != null) {
            minecraftimpl.k = new Session(s3, s1);
        } else {
            minecraftimpl.k = new Session("ACPlayer", "");
        }
        if (s2 != null) {
            String[] as = s2.split(":");
            minecraftimpl.a(as[0], Integer.parseInt(as[1]));
        }
        frame.setVisible(true);
        frame.addWindowListener((WindowListener) new kj((Minecraft) minecraftimpl, thread));
        thread.start();
    }

    public ClientPlayNetworkHandler s() {
        if (this.h instanceof MultiplayerClientPlayer)
            return ((MultiplayerClientPlayer) this.h).bN;
        return null;
    }

    public static void main(String[] args) {
        String s = "ACPlayer";
        if (args.length > 0)
            s = args[0];
        String s1 = "-";
        if (args.length > 1)
            s1 = args[1];
        a(s, s1);
    }

    public static boolean t() {
        return (a == null || !a.z.z);
    }

    public static boolean u() {
        return (a != null && a.z.j);
    }

    public static boolean v() {
        return (a != null && a.z.k);
    }

    public static boolean w() {
        return (a != null && a.z.B);
    }

    public boolean b(String s) {
        if (!s.startsWith("/")) ;
        return false;
    }

    public static byte[] b = new byte[10485760];

    private static Minecraft a;

    public ClientInteractionManager c;

    private boolean Q;

    private boolean R;

    public int d;

    public int e;

    private cx S;

    private final Timer T;

    public Level f;

    public n g;

    public ClientPlayer h;

    public LivingEntity i;

    public ParticleManager j;

    public Session k;

    public String l;

    public Canvas m;

    public boolean n;

    public volatile boolean o;

    public TextureManager p;

    public TextRenderer q;

    public Screen r;

    public ProgressListenerImpl s;

    public GameRenderer t;

    public cz U;

    private int V;

    private int W;

    private int X;

    private final int Y;

    public ToastManager u;

    public uq v;

    public boolean w;

    public BipedModel x;

    public vf y;

    public kv z;

    protected MinecraftApplet A;

    public SoundHelper B;

    public vy C;

    public ik D;

    private File Z;

    private LevelStorage aa;

    public static long[] E = new long[512];

    public static long[] F = new long[512];

    public static int G = 0;

    public static long H = 0L;

    public StatManager I;

    private String ab;

    private int ac;

    private final FlowingWaterTextureBinder2 ad;

    private final FlowingLavaTextureBinder ae;

    private static File af = null;

    public volatile boolean J;

    public String K;

    boolean L;

    long M;

    public boolean N;

    private int ag;

    private int rightMouseTicksRan;

    public boolean O;

    long P;

    private int ah;

    public static Minecraft minecraftInstance;

    public static long[] updateTimes = new long[512];

    public static long updateRendererTime;

    public MapList mapList;

    public int nextFrameTime;

    public long prevFrameTimeForAvg;

    public long[] tFrameTimes;

    public CutsceneCamera cutsceneCamera;

    public CutsceneCamera activeCutsceneCamera;

    public boolean cameraActive;

    public boolean cameraPause;

    public LivingEntity cutsceneCameraEntity;

    public GuiStore storeGUI;

    ItemInstance lastItemUsed;

    Entity lastEntityHit;

    ScriptVec3 lastBlockHit;

    int gcTime;

    public void updateStoreGUI() {
        ScreenScaler scaledresolution = new ScreenScaler(this.z, this.d, this.e);
        int i = scaledresolution.a();
        int j = scaledresolution.b();
        this.storeGUI.a(this, i, j);
    }
}