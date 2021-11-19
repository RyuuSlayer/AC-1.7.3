package io.github.ryuu.adventurecraft.util;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.nio.FloatBuffer
 *  java.nio.IntBuffer
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GL14
 *  org.lwjgl.util.glu.GLU
 */
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

class MapEditing {

    Minecraft mc;

    Level world;

    HitResult cursor;

    private TileRenderer renderBlocks;

    int selectedBlockID;

    int selectedMetadata;

    MapEditing(Minecraft mcInstance, Level w) {
        this.mc = mcInstance;
        this.world = w;
        this.renderBlocks = new TileRenderer(w);
    }

    public void updateWorld(Level w) {
        this.world = w;
        this.renderBlocks.field_82 = w;
    }

    public void updateCursor(LivingEntity camera, float fov, float time) {
        if (this.mc.field_2778) {
            this.cursor = null;
            return;
        }
        int x = Mouse.getX();
        int y = Mouse.getY();
        IntBuffer viewport = BufferUtils.createIntBuffer((int) 16);
        FloatBuffer modelview = BufferUtils.createFloatBuffer((int) 16);
        FloatBuffer projection = BufferUtils.createFloatBuffer((int) 16);
        FloatBuffer position = BufferUtils.createFloatBuffer((int) 3);
        GL11.glGetFloat((int) 2982, (FloatBuffer) modelview);
        GL11.glGetFloat((int) 2983, (FloatBuffer) projection);
        GL11.glGetInteger((int) 2978, (IntBuffer) viewport);
        float winX = x;
        float winY = y;
        GLU.gluUnProject((float) winX, (float) winY, (float) 1.0f, (FloatBuffer) modelview, (FloatBuffer) projection, (IntBuffer) viewport, (FloatBuffer) position);
        Vec3f pos = camera.method_931(time);
        Vec3f mouseLoc = pos.method_1301(position.get(0) * 1024.0f, position.get(1) * 1024.0f, position.get(2) * 1024.0f);
        this.cursor = this.world.raycast(pos, mouseLoc);
    }

    public void paint() {
        if (this.cursor != null && this.cursor.type == HitType.TILE) {
            int x = this.cursor.x;
            int y = this.cursor.y;
            int z = this.cursor.z;
            this.setBlock(x + this.getCursorXOffset(), y + this.getCursorYOffset(), z + this.getCursorZOffset(), this.selectedBlockID, this.selectedMetadata);
        }
    }

    int getCursorXOffset() {
        if (this.cursor.field_1987 == 4) {
            return -1;
        }
        if (this.cursor.field_1987 == 5) {
            return 1;
        }
        return 0;
    }

    int getCursorYOffset() {
        if (this.cursor.field_1987 == 0) {
            return -1;
        }
        if (this.cursor.field_1987 == 1) {
            return 1;
        }
        return 0;
    }

    int getCursorZOffset() {
        if (this.cursor.field_1987 == 2) {
            return -1;
        }
        if (this.cursor.field_1987 == 3) {
            return 1;
        }
        return 0;
    }

    private void setBlock(int x, int y, int z, int blockID, int metadata) {
        if (y >= 0 && y < 128) {
            this.world.method_201(x, y, z, blockID, metadata);
        }
    }

    public void render(float time) {
        LivingEntity camera = Minecraft.minecraftInstance.field_2807;
        if (!this.mc.field_2778) {
            this.drawCursor(camera, time);
            if (this.cursor != null) {
                float x = (float) (camera.prevRenderX + (camera.x - camera.prevRenderX) * (double) time);
                float y = (float) (camera.prevRenderY + (camera.y - camera.prevRenderY) * (double) time);
                float z = (float) (camera.prevRenderZ + (camera.z - camera.prevRenderZ) * (double) time);
                GL11.glPushMatrix();
                GL11.glTranslatef((float) (-x), (float) (-y), (float) (-z));
                GL14.glBlendColor((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 0.4f);
                GL11.glEnable((int) 3042);
                GL11.glBlendFunc((int) 32771, (int) 32772);
                this.mc.textureManager.bindTexture(this.mc.textureManager.getTextureId("/terrain.png"));
                this.renderBlocks.startRenderingBlocks(this.world);
                this.drawBlock(this.cursor.x + this.getCursorXOffset(), this.cursor.y + this.getCursorYOffset(), this.cursor.z + this.getCursorZOffset(), this.selectedBlockID, this.selectedMetadata);
                this.renderBlocks.stopRenderingBlocks();
                GL11.glDisable((int) 3042);
                GL11.glPopMatrix();
            }
        }
    }

    public void renderSelection(float time) {
        if (ItemCursor.bothSet) {
            LivingEntity camera = Minecraft.minecraftInstance.field_2807;
            float x = (float) (camera.prevRenderX + (camera.x - camera.prevRenderX) * (double) time);
            float y = (float) (camera.prevRenderY + (camera.y - camera.prevRenderY) * (double) time);
            float z = (float) (camera.prevRenderZ + (camera.z - camera.prevRenderZ) * (double) time);
            GL11.glPushMatrix();
            GL11.glTranslatef((float) (-x), (float) (-y), (float) (-z));
            GL14.glBlendColor((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 0.4f);
            GL11.glEnable((int) 3042);
            GL11.glBlendFunc((int) 32771, (int) 32772);
            Vec3f lookDir = camera.method_1320();
            int xOffset = (int) (camera.x + (double) DebugMode.reachDistance * lookDir.x) - ItemCursor.minX;
            int yOffset = (int) (camera.y + (double) DebugMode.reachDistance * lookDir.y) - ItemCursor.minY;
            int zOffset = (int) (camera.z + (double) DebugMode.reachDistance * lookDir.z) - ItemCursor.minZ;
            for (int texNum = 0; texNum <= 3; ++texNum) {
                if (texNum == 0) {
                    this.mc.textureManager.bindTexture(this.mc.textureManager.getTextureId("/terrain.png"));
                } else {
                    this.mc.textureManager.bindTexture(this.mc.textureManager.getTextureId(String.format((String) "/terrain%d.png", (Object[]) new Object[] { texNum })));
                }
                this.renderBlocks.startRenderingBlocks(this.world);
                for (int i = ItemCursor.minX; i <= ItemCursor.maxX; ++i) {
                    for (int j = ItemCursor.minY; j <= ItemCursor.maxY; ++j) {
                        for (int k = ItemCursor.minZ; k <= ItemCursor.maxZ; ++k) {
                            int blockID = this.mc.level.getTileId(i, j, k);
                            if (Tile.BY_ID[blockID] == null || Tile.BY_ID[blockID].getTextureNum() != texNum)
                                continue;
                            int metadata = this.mc.level.getTileMeta(i, j, k);
                            this.drawBlock(i + xOffset, j + yOffset, k + zOffset, blockID, metadata);
                        }
                    }
                }
                this.renderBlocks.stopRenderingBlocks();
            }
            GL11.glDisable((int) 3042);
            GL11.glPopMatrix();
        }
    }

    private void drawBlock(int x, int y, int z, int blockID, int metadata) {
        Tile block = Tile.BY_ID[blockID];
        if (block != null) {
            int prevBlockID = this.world.getTileId(x, y, z);
            int prevMetadata = this.world.getTileMeta(x, y, z);
            this.world.setBlockAndMetadataTemp(x, y, z, blockID, metadata);
            this.renderBlocks.method_57(block, x, y, z);
            this.world.setBlockAndMetadataTemp(x, y, z, prevBlockID, prevMetadata);
        }
    }

    private void drawBox(Box axisalignedbb) {
        Tessellator tessellator = Tessellator.INSTANCE;
        tessellator.start(3);
        tessellator.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.draw();
        tessellator.start(3);
        tessellator.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.draw();
        tessellator.start(1);
        tessellator.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.draw();
    }

    private void drawCursor(LivingEntity camera, float time) {
        if (this.cursor != null && this.cursor.type == HitType.TILE) {
            GL11.glEnable((int) 3042);
            GL11.glBlendFunc((int) 770, (int) 771);
            GL11.glColor4f((float) 0.0f, (float) 0.0f, (float) 0.0f, (float) 0.4f);
            GL11.glLineWidth((float) 2.0f);
            GL11.glDisable((int) 3553);
            GL11.glDepthMask((boolean) false);
            float f1 = 0.002f;
            int j = this.world.getTileId(this.cursor.x, this.cursor.y, this.cursor.z);
            if (j > 0) {
                Tile.BY_ID[j].method_1616(this.world, this.cursor.x, this.cursor.y, this.cursor.z);
                double d = camera.prevRenderX + (camera.x - camera.prevRenderX) * (double) time;
                double d1 = camera.prevRenderY + (camera.y - camera.prevRenderY) * (double) time;
                double d2 = camera.prevRenderZ + (camera.z - camera.prevRenderZ) * (double) time;
                Box box = Tile.BY_ID[j].getOutlineShape(this.world, this.cursor.x, this.cursor.y, this.cursor.z).expand(f1, f1, f1).move(-d, -d1, -d2);
                this.drawBox(box);
                GL11.glColor4f((float) 1.0f, (float) 0.0f, (float) 0.0f, (float) 0.4f);
                GL11.glLineWidth((float) 4.0f);
                Tessellator tessellator = Tessellator.INSTANCE;
                tessellator.start(3);
                if (this.cursor.field_1987 == 0) {
                    tessellator.pos(box.minX, box.minY, box.minZ);
                    tessellator.pos(box.maxX, box.minY, box.minZ);
                    tessellator.pos(box.maxX, box.minY, box.maxZ);
                    tessellator.pos(box.minX, box.minY, box.maxZ);
                    tessellator.pos(box.minX, box.minY, box.minZ);
                } else if (this.cursor.field_1987 == 1) {
                    tessellator.pos(box.minX, box.maxY, box.minZ);
                    tessellator.pos(box.maxX, box.maxY, box.minZ);
                    tessellator.pos(box.maxX, box.maxY, box.maxZ);
                    tessellator.pos(box.minX, box.maxY, box.maxZ);
                    tessellator.pos(box.minX, box.maxY, box.minZ);
                } else if (this.cursor.field_1987 == 2) {
                    tessellator.pos(box.minX, box.minY, box.minZ);
                    tessellator.pos(box.maxX, box.minY, box.minZ);
                    tessellator.pos(box.maxX, box.maxY, box.minZ);
                    tessellator.pos(box.minX, box.maxY, box.minZ);
                    tessellator.pos(box.minX, box.minY, box.minZ);
                } else if (this.cursor.field_1987 == 3) {
                    tessellator.pos(box.minX, box.minY, box.maxZ);
                    tessellator.pos(box.maxX, box.minY, box.maxZ);
                    tessellator.pos(box.maxX, box.maxY, box.maxZ);
                    tessellator.pos(box.minX, box.maxY, box.maxZ);
                    tessellator.pos(box.minX, box.minY, box.maxZ);
                } else if (this.cursor.field_1987 == 4) {
                    tessellator.pos(box.minX, box.minY, box.minZ);
                    tessellator.pos(box.minX, box.maxY, box.minZ);
                    tessellator.pos(box.minX, box.maxY, box.maxZ);
                    tessellator.pos(box.minX, box.minY, box.maxZ);
                    tessellator.pos(box.minX, box.minY, box.minZ);
                } else if (this.cursor.field_1987 == 5) {
                    tessellator.pos(box.maxX, box.minY, box.minZ);
                    tessellator.pos(box.maxX, box.maxY, box.minZ);
                    tessellator.pos(box.maxX, box.maxY, box.maxZ);
                    tessellator.pos(box.maxX, box.minY, box.maxZ);
                    tessellator.pos(box.maxX, box.minY, box.minZ);
                }
                tessellator.draw();
            }
            GL11.glDepthMask((boolean) true);
            GL11.glEnable((int) 3553);
            GL11.glDisable((int) 3042);
        }
    }

    void setBlock(int bID, int m) {
        this.selectedBlockID = bID;
        this.selectedMetadata = m;
    }
}
