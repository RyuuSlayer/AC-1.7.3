package io.github.ryuu.adventurecraft.util;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import io.github.ryuu.adventurecraft.items.ItemCursor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TileRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitType;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.Vec3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.util.glu.GLU;

public class MapEditing {
    Minecraft mc;

    Level world;

    HitResult cursor;

    private final TileRenderer renderBlocks;

    int selectedBlockID;

    int selectedMetadata;

    public MapEditing(Minecraft mcInstance, Level w) {
        this.mc = mcInstance;
        this.world = w;
        this.renderBlocks = new TileRenderer(w);
    }

    public void updateWorld(Level w) {
        this.world = w;
        this.renderBlocks.c = w;
    }

    public void updateCursor(LivingEntity camera, float fov, float time) {
        if (this.mc.N) {
            this.cursor = null;
            return;
        }
        int x = Mouse.getX();
        int y = Mouse.getY();
        IntBuffer viewport = BufferUtils.createIntBuffer(16);
        FloatBuffer modelview = BufferUtils.createFloatBuffer(16);
        FloatBuffer projection = BufferUtils.createFloatBuffer(16);
        FloatBuffer position = BufferUtils.createFloatBuffer(3);
        GL11.glGetFloat(2982, modelview);
        GL11.glGetFloat(2983, projection);
        GL11.glGetInteger(2978, viewport);
        float winX = x;
        float winY = y;
        GLU.gluUnProject(winX, winY, 1.0F, modelview, projection, viewport, position);
        Vec3f pos = camera.e(time);
        Vec3f mouseLoc = pos.c((position.get(0) * 1024.0F), (position.get(1) * 1024.0F), (position.get(2) * 1024.0F));
        this.cursor = this.world.a(pos, mouseLoc);
    }

    public void paint() {
        if (this.cursor != null && this.cursor.a == HitType.a) {
            int x = this.cursor.b;
            int y = this.cursor.c;
            int z = this.cursor.d;
            setBlock(x + getCursorXOffset(), y + getCursorYOffset(), z + getCursorZOffset(), this.selectedBlockID, this.selectedMetadata);
        }
    }

    int getCursorXOffset() {
        if (this.cursor.e == 4)
            return -1;
        if (this.cursor.e == 5)
            return 1;
        return 0;
    }

    int getCursorYOffset() {
        if (this.cursor.e == 0)
            return -1;
        if (this.cursor.e == 1)
            return 1;
        return 0;
    }

    int getCursorZOffset() {
        if (this.cursor.e == 2)
            return -1;
        if (this.cursor.e == 3)
            return 1;
        return 0;
    }

    private void setBlock(int x, int y, int z, int blockID, int metadata) {
        if (y >= 0 && y < 128)
            this.world.b(x, y, z, blockID, metadata);
    }

    public void render(float time) {
        LivingEntity camera = Minecraft.minecraftInstance.i;
        if (!this.mc.N) {
            drawCursor(camera, time);
            if (this.cursor != null) {
                float x = (float) (camera.bl + (camera.aM - camera.bl) * time);
                float y = (float) (camera.bm + (camera.aN - camera.bm) * time);
                float z = (float) (camera.bn + (camera.aO - camera.bn) * time);
                GL11.glPushMatrix();
                GL11.glTranslatef(-x, -y, -z);
                GL14.glBlendColor(1.0F, 1.0F, 1.0F, 0.4F);
                GL11.glEnable(3042);
                GL11.glBlendFunc(32771, 32772);
                this.mc.p.b(this.mc.p.b("/terrain.png"));
                this.renderBlocks.startRenderingBlocks(this.world);
                drawBlock(this.cursor.b + getCursorXOffset(), this.cursor.c + getCursorYOffset(), this.cursor.d + getCursorZOffset(), this.selectedBlockID, this.selectedMetadata);
                this.renderBlocks.stopRenderingBlocks();
                GL11.glDisable(3042);
                GL11.glPopMatrix();
            }
        }
    }

    public void renderSelection(float time) {
        if (ItemCursor.bothSet) {
            LivingEntity camera = Minecraft.minecraftInstance.i;
            float x = (float) (camera.bl + (camera.aM - camera.bl) * time);
            float y = (float) (camera.bm + (camera.aN - camera.bm) * time);
            float z = (float) (camera.bn + (camera.aO - camera.bn) * time);
            GL11.glPushMatrix();
            GL11.glTranslatef(-x, -y, -z);
            GL14.glBlendColor(1.0F, 1.0F, 1.0F, 0.4F);
            GL11.glEnable(3042);
            GL11.glBlendFunc(32771, 32772);
            Vec3f lookDir = camera.ac();
            int xOffset = (int) (camera.aM + DebugMode.reachDistance * lookDir.a) - ItemCursor.minX;
            int yOffset = (int) (camera.aN + DebugMode.reachDistance * lookDir.b) - ItemCursor.minY;
            int zOffset = (int) (camera.aO + DebugMode.reachDistance * lookDir.c) - ItemCursor.minZ;
            for (int texNum = 0; texNum <= 3; texNum++) {
                if (texNum == 0) {
                    this.mc.p.b(this.mc.p.b("/terrain.png"));
                } else {
                    this.mc.p.b(this.mc.p.b(String.format("/terrain%d.png", new Object[]{Integer.valueOf(texNum)})));
                }
                this.renderBlocks.startRenderingBlocks(this.world);
                for (int i = ItemCursor.minX; i <= ItemCursor.maxX; i++) {
                    for (int j = ItemCursor.minY; j <= ItemCursor.maxY; j++) {
                        for (int k = ItemCursor.minZ; k <= ItemCursor.maxZ; k++) {
                            int blockID = this.mc.f.a(i, j, k);
                            if (Tile.m[blockID] != null && Tile.m[blockID].getTextureNum() == texNum) {
                                int metadata = this.mc.f.e(i, j, k);
                                drawBlock(i + xOffset, j + yOffset, k + zOffset, blockID, metadata);
                            }
                        }
                    }
                }
                this.renderBlocks.stopRenderingBlocks();
            }
            GL11.glDisable(3042);
            GL11.glPopMatrix();
        }
    }

    private void drawBlock(int x, int y, int z, int blockID, int metadata) {
        Tile block = Tile.m[blockID];
        if (block != null) {
            int prevBlockID = this.world.a(x, y, z);
            int prevMetadata = this.world.e(x, y, z);
            this.world.setBlockAndMetadataTemp(x, y, z, blockID, metadata);
            this.renderBlocks.b(block, x, y, z);
            this.world.setBlockAndMetadataTemp(x, y, z, prevBlockID, prevMetadata);
        }
    }

    private void drawBox(Box axisalignedbb) {
        Tessellator tessellator = Tessellator.a;
        tessellator.a(3);
        tessellator.a(axisalignedbb.a, axisalignedbb.b, axisalignedbb.c);
        tessellator.a(axisalignedbb.d, axisalignedbb.b, axisalignedbb.c);
        tessellator.a(axisalignedbb.d, axisalignedbb.b, axisalignedbb.f);
        tessellator.a(axisalignedbb.a, axisalignedbb.b, axisalignedbb.f);
        tessellator.a(axisalignedbb.a, axisalignedbb.b, axisalignedbb.c);
        tessellator.a();
        tessellator.a(3);
        tessellator.a(axisalignedbb.a, axisalignedbb.e, axisalignedbb.c);
        tessellator.a(axisalignedbb.d, axisalignedbb.e, axisalignedbb.c);
        tessellator.a(axisalignedbb.d, axisalignedbb.e, axisalignedbb.f);
        tessellator.a(axisalignedbb.a, axisalignedbb.e, axisalignedbb.f);
        tessellator.a(axisalignedbb.a, axisalignedbb.e, axisalignedbb.c);
        tessellator.a();
        tessellator.a(1);
        tessellator.a(axisalignedbb.a, axisalignedbb.b, axisalignedbb.c);
        tessellator.a(axisalignedbb.a, axisalignedbb.e, axisalignedbb.c);
        tessellator.a(axisalignedbb.d, axisalignedbb.b, axisalignedbb.c);
        tessellator.a(axisalignedbb.d, axisalignedbb.e, axisalignedbb.c);
        tessellator.a(axisalignedbb.d, axisalignedbb.b, axisalignedbb.f);
        tessellator.a(axisalignedbb.d, axisalignedbb.e, axisalignedbb.f);
        tessellator.a(axisalignedbb.a, axisalignedbb.b, axisalignedbb.f);
        tessellator.a(axisalignedbb.a, axisalignedbb.e, axisalignedbb.f);
        tessellator.a();
    }

    private void drawCursor(LivingEntity camera, float time) {
        if (this.cursor != null && this.cursor.a == HitType.a) {
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
            GL11.glLineWidth(2.0F);
            GL11.glDisable(3553);
            GL11.glDepthMask(false);
            float f1 = 0.002F;
            int j = this.world.a(this.cursor.b, this.cursor.c, this.cursor.d);
            if (j > 0) {
                Tile.m[j].a(this.world, this.cursor.b, this.cursor.c, this.cursor.d);
                double d = camera.bl + (camera.aM - camera.bl) * time;
                double d1 = camera.bm + (camera.aN - camera.bm) * time;
                double d2 = camera.bn + (camera.aO - camera.bn) * time;
                Box box = Tile.m[j].f(this.world, this.cursor.b, this.cursor.c, this.cursor.d).b(f1, f1, f1).c(-d, -d1, -d2);
                drawBox(box);
                GL11.glColor4f(1.0F, 0.0F, 0.0F, 0.4F);
                GL11.glLineWidth(4.0F);
                Tessellator tessellator = Tessellator.a;
                tessellator.a(3);
                if (this.cursor.e == 0) {
                    tessellator.a(box.a, box.b, box.c);
                    tessellator.a(box.d, box.b, box.c);
                    tessellator.a(box.d, box.b, box.f);
                    tessellator.a(box.a, box.b, box.f);
                    tessellator.a(box.a, box.b, box.c);
                } else if (this.cursor.e == 1) {
                    tessellator.a(box.a, box.e, box.c);
                    tessellator.a(box.d, box.e, box.c);
                    tessellator.a(box.d, box.e, box.f);
                    tessellator.a(box.a, box.e, box.f);
                    tessellator.a(box.a, box.e, box.c);
                } else if (this.cursor.e == 2) {
                    tessellator.a(box.a, box.b, box.c);
                    tessellator.a(box.d, box.b, box.c);
                    tessellator.a(box.d, box.e, box.c);
                    tessellator.a(box.a, box.e, box.c);
                    tessellator.a(box.a, box.b, box.c);
                } else if (this.cursor.e == 3) {
                    tessellator.a(box.a, box.b, box.f);
                    tessellator.a(box.d, box.b, box.f);
                    tessellator.a(box.d, box.e, box.f);
                    tessellator.a(box.a, box.e, box.f);
                    tessellator.a(box.a, box.b, box.f);
                } else if (this.cursor.e == 4) {
                    tessellator.a(box.a, box.b, box.c);
                    tessellator.a(box.a, box.e, box.c);
                    tessellator.a(box.a, box.e, box.f);
                    tessellator.a(box.a, box.b, box.f);
                    tessellator.a(box.a, box.b, box.c);
                } else if (this.cursor.e == 5) {
                    tessellator.a(box.d, box.b, box.c);
                    tessellator.a(box.d, box.e, box.c);
                    tessellator.a(box.d, box.e, box.f);
                    tessellator.a(box.d, box.b, box.f);
                    tessellator.a(box.d, box.b, box.c);
                }
                tessellator.a();
            }
            GL11.glDepthMask(true);
            GL11.glEnable(3553);
            GL11.glDisable(3042);
        }
    }

    public void setBlock(int bID, int m) {
        this.selectedBlockID = bID;
        this.selectedMetadata = m;
    }
}