package io.github.ryuu.adventurecraft.util;

import io.github.ryuu.adventurecraft.extensions.client.render.ExTileRenderer;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import io.github.ryuu.adventurecraft.items.ItemCursor;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import io.github.ryuu.adventurecraft.mixin.client.render.AccessTileRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TileRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitType;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.Vec3d;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.util.glu.GLU;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class MapEditing {

    private final TileRenderer renderBlocks;
    Minecraft mc;
    Level level;
    HitResult cursor;
    int selectedBlockID;
    int selectedMetadata;

    public MapEditing(Minecraft mcInstance, Level level) {
        this.mc = mcInstance;
        this.level = level;
        this.renderBlocks = new TileRenderer(level);
    }

    public void updateWorld(Level level) {
        this.level = level;
        ((AccessTileRenderer) this.renderBlocks).setField_82(level);
    }

    public void updateCursor(LivingEntity camera, float fov, float time) {
        if (this.mc.field_2778) {
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
        GLU.gluUnProject(x, y, 1.0f, modelview, projection, viewport, position);
        Vec3d pos = camera.getPos(time);
        Vec3d mouseLoc = pos.add(position.get(0) * 1024.0f, position.get(1) * 1024.0f, position.get(2) * 1024.0f);
        this.cursor = this.level.raycast(pos, mouseLoc);
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
        if (this.cursor.tileId == 4) {
            return -1;
        }
        if (this.cursor.tileId == 5) {
            return 1;
        }
        return 0;
    }

    int getCursorYOffset() {
        if (this.cursor.tileId == 0) {
            return -1;
        }
        if (this.cursor.tileId == 1) {
            return 1;
        }
        return 0;
    }

    int getCursorZOffset() {
        if (this.cursor.tileId == 2) {
            return -1;
        }
        if (this.cursor.tileId == 3) {
            return 1;
        }
        return 0;
    }

    private void setBlock(int x, int y, int z, int blockID, int metadata) {
        if (y >= 0 && y < 128) {
            this.level.method_201(x, y, z, blockID, metadata);
        }
    }

    public void render(float time) {
        LivingEntity camera = AccessMinecraft.getInstance().cameraEntity;
        if (!this.mc.field_2778) {
            this.drawCursor(camera, time);
            if (this.cursor != null) {
                float x = (float) (camera.prevRenderX + (camera.x - camera.prevRenderX) * (double) time);
                float y = (float) (camera.prevRenderY + (camera.y - camera.prevRenderY) * (double) time);
                float z = (float) (camera.prevRenderZ + (camera.z - camera.prevRenderZ) * (double) time);
                GL11.glPushMatrix();
                GL11.glTranslatef(-x, -y, -z);
                GL14.glBlendColor(1.0f, 1.0f, 1.0f, 0.4f);
                GL11.glEnable(3042);
                GL11.glBlendFunc(32771, 32772);
                this.mc.textureManager.bindTexture(this.mc.textureManager.getTextureId("/terrain.png"));
                ((ExTileRenderer) this.renderBlocks).startRenderingBlocks(this.level);
                this.drawBlock(this.cursor.x + this.getCursorXOffset(), this.cursor.y + this.getCursorYOffset(), this.cursor.z + this.getCursorZOffset(), this.selectedBlockID, this.selectedMetadata);
                ((ExTileRenderer) this.renderBlocks).stopRenderingBlocks();
                GL11.glDisable(3042);
                GL11.glPopMatrix();
            }
        }
    }

    public void renderSelection(float time) {
        if (ItemCursor.bothSet) {
            LivingEntity camera = AccessMinecraft.getInstance().cameraEntity;
            float x = (float) (camera.prevRenderX + (camera.x - camera.prevRenderX) * (double) time);
            float y = (float) (camera.prevRenderY + (camera.y - camera.prevRenderY) * (double) time);
            float z = (float) (camera.prevRenderZ + (camera.z - camera.prevRenderZ) * (double) time);
            GL11.glPushMatrix();
            GL11.glTranslatef(-x, -y, -z);
            GL14.glBlendColor(1.0f, 1.0f, 1.0f, 0.4f);
            GL11.glEnable(3042);
            GL11.glBlendFunc(32771, 32772);
            Vec3d lookDir = camera.getRotation();
            int xOffset = (int) (camera.x + DebugMode.reachDistance * lookDir.x) - ItemCursor.minX;
            int yOffset = (int) (camera.y + DebugMode.reachDistance * lookDir.y) - ItemCursor.minY;
            int zOffset = (int) (camera.z + DebugMode.reachDistance * lookDir.z) - ItemCursor.minZ;
            for (int texNum = 0; texNum <= 3; ++texNum) {
                if (texNum == 0) {
                    this.mc.textureManager.bindTexture(this.mc.textureManager.getTextureId("/terrain.png"));
                } else {
                    this.mc.textureManager.bindTexture(this.mc.textureManager.getTextureId(String.format("/terrain%d.png", texNum)));
                }
                ((ExTileRenderer) this.renderBlocks).startRenderingBlocks(this.level);
                for (int i = ItemCursor.minX; i <= ItemCursor.maxX; ++i) {
                    for (int j = ItemCursor.minY; j <= ItemCursor.maxY; ++j) {
                        for (int k = ItemCursor.minZ; k <= ItemCursor.maxZ; ++k) {
                            int blockID = this.mc.level.getTileId(i, j, k);
                            if (Tile.BY_ID[blockID] == null || ((ExTile) Tile.BY_ID[blockID]).getTextureNum() != texNum)
                                continue;
                            int metadata = this.mc.level.getTileMeta(i, j, k);
                            this.drawBlock(i + xOffset, j + yOffset, k + zOffset, blockID, metadata);
                        }
                    }
                }
                ((ExTileRenderer) this.renderBlocks).stopRenderingBlocks();
            }
            GL11.glDisable(3042);
            GL11.glPopMatrix();
        }
    }

    private void drawBlock(int x, int y, int z, int blockID, int metadata) {
        Tile block = Tile.BY_ID[blockID];
        if (block != null) {
            int prevBlockID = this.level.getTileId(x, y, z);
            int prevMetadata = this.level.getTileMeta(x, y, z);
            ((ExLevel) this.level).setBlockAndMetadataTemp(x, y, z, blockID, metadata);
            this.renderBlocks.method_57(block, x, y, z);
            ((ExLevel) this.level).setBlockAndMetadataTemp(x, y, z, prevBlockID, prevMetadata);
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
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.4f);
            GL11.glLineWidth(2.0f);
            GL11.glDisable(3553);
            GL11.glDepthMask(false);
            float f1 = 0.002f;
            int j = this.level.getTileId(this.cursor.x, this.cursor.y, this.cursor.z);
            if (j > 0) {
                Tile.BY_ID[j].method_1616(this.level, this.cursor.x, this.cursor.y, this.cursor.z);
                double d = camera.prevRenderX + (camera.x - camera.prevRenderX) * (double) time;
                double d1 = camera.prevRenderY + (camera.y - camera.prevRenderY) * (double) time;
                double d2 = camera.prevRenderZ + (camera.z - camera.prevRenderZ) * (double) time;
                Box box = Tile.BY_ID[j].getOutlineShape(this.level, this.cursor.x, this.cursor.y, this.cursor.z).expand(f1, f1, f1).move(-d, -d1, -d2);
                this.drawBox(box);
                GL11.glColor4f(1.0f, 0.0f, 0.0f, 0.4f);
                GL11.glLineWidth(4.0f);
                Tessellator tessellator = Tessellator.INSTANCE;
                tessellator.start(3);
                if (this.cursor.tileId == 0) {
                    tessellator.pos(box.minX, box.minY, box.minZ);
                    tessellator.pos(box.maxX, box.minY, box.minZ);
                    tessellator.pos(box.maxX, box.minY, box.maxZ);
                    tessellator.pos(box.minX, box.minY, box.maxZ);
                    tessellator.pos(box.minX, box.minY, box.minZ);
                } else if (this.cursor.tileId == 1) {
                    tessellator.pos(box.minX, box.maxY, box.minZ);
                    tessellator.pos(box.maxX, box.maxY, box.minZ);
                    tessellator.pos(box.maxX, box.maxY, box.maxZ);
                    tessellator.pos(box.minX, box.maxY, box.maxZ);
                    tessellator.pos(box.minX, box.maxY, box.minZ);
                } else if (this.cursor.tileId == 2) {
                    tessellator.pos(box.minX, box.minY, box.minZ);
                    tessellator.pos(box.maxX, box.minY, box.minZ);
                    tessellator.pos(box.maxX, box.maxY, box.minZ);
                    tessellator.pos(box.minX, box.maxY, box.minZ);
                    tessellator.pos(box.minX, box.minY, box.minZ);
                } else if (this.cursor.tileId == 3) {
                    tessellator.pos(box.minX, box.minY, box.maxZ);
                    tessellator.pos(box.maxX, box.minY, box.maxZ);
                    tessellator.pos(box.maxX, box.maxY, box.maxZ);
                    tessellator.pos(box.minX, box.maxY, box.maxZ);
                    tessellator.pos(box.minX, box.minY, box.maxZ);
                } else if (this.cursor.tileId == 4) {
                    tessellator.pos(box.minX, box.minY, box.minZ);
                    tessellator.pos(box.minX, box.maxY, box.minZ);
                    tessellator.pos(box.minX, box.maxY, box.maxZ);
                    tessellator.pos(box.minX, box.minY, box.maxZ);
                    tessellator.pos(box.minX, box.minY, box.minZ);
                } else if (this.cursor.tileId == 5) {
                    tessellator.pos(box.maxX, box.minY, box.minZ);
                    tessellator.pos(box.maxX, box.maxY, box.minZ);
                    tessellator.pos(box.maxX, box.maxY, box.maxZ);
                    tessellator.pos(box.maxX, box.minY, box.maxZ);
                    tessellator.pos(box.maxX, box.minY, box.minZ);
                }
                tessellator.draw();
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
