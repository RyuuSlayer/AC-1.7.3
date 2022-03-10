package io.github.ryuu.adventurecraft.util;

import io.github.ryuu.adventurecraft.entities.EntityCamera;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ClientPlayer;
import net.minecraft.util.maths.Vec3d;
import org.lwjgl.opengl.GL11;

import java.util.LinkedList;
import java.util.List;

public class CutsceneCamera {

    long startTime;

    CutsceneCameraPoint curPoint = new CutsceneCameraPoint(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0);

    CutsceneCameraPoint prevPrevPoint;

    CutsceneCameraPoint prevPoint;

    public List<CutsceneCameraPoint> cameraPoints = new LinkedList<>();

    List<Vec3d> lineVecs = new LinkedList<>();

    public int startType = 2;

    public void addCameraPoint(float time, float posX, float posY, float posZ, float yaw, float pitch, int type) {
        int index = 0;
        for (CutsceneCameraPoint p : this.cameraPoints) {
            if (time < p.time) break;
            ++index;
        }
        this.cameraPoints.add(index, new CutsceneCameraPoint(time, posX, posY, posZ, yaw, pitch, type));
        this.fixYawPitch(0.0f, 0.0f);
    }

    public void loadCameraEntities() {
        for (Object obj : AccessMinecraft.getInstance().level.entities) {
            if (obj instanceof EntityCamera) {
                ((EntityCamera)obj).remove();
            }
        }
        for (CutsceneCameraPoint p : this.cameraPoints) {
            EntityCamera e = new EntityCamera(AccessMinecraft.getInstance().level, p.time, p.cameraBlendType, p.cameraID);
            e.method_1338(p.posX, p.posY, p.posZ, p.rotYaw, p.rotPitch);
            AccessMinecraft.getInstance().level.spawnEntity(e);
        }
        CutsceneCamera c = new CutsceneCamera();
        for (CutsceneCameraPoint p : this.cameraPoints) {
            c.addCameraPoint(p.time, p.posX, p.posY, p.posZ, p.rotYaw, p.rotPitch, p.cameraBlendType);
        }
        CutsceneCameraPoint prev = null;
        this.lineVecs.clear();
        for (CutsceneCameraPoint p : this.cameraPoints) {
            if (prev != null) {
                for (int i = 0; i < 25; ++i) {
                    float lerpValue = (float) (i + 1) / 25.0f;
                    float time = this.lerp(lerpValue, prev.time, p.time);
                    CutsceneCameraPoint point = c.getPoint(time);
                    Vec3d v = Vec3d.create(point.posX, point.posY, point.posZ);
                    this.lineVecs.add(v);
                }
            } else {
                this.lineVecs.add(Vec3d.create(p.posX, p.posY, p.posZ));
            }
            prev = p;
        }
    }

    public void drawLines(LivingEntity entityplayer, float f) {
        double offX = entityplayer.prevRenderX + (entityplayer.x - entityplayer.prevRenderX) * f;
        double offY = entityplayer.prevRenderY + (entityplayer.y - entityplayer.prevRenderY) * f;
        double offZ = entityplayer.prevRenderZ + (entityplayer.z - entityplayer.prevRenderZ) * f;
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0f, 0.2f, 0.0f, 1.0f);
        GL11.glLineWidth(5.0f);
        GL11.glDisable(3553);
        Tessellator tessellator = Tessellator.INSTANCE;
        tessellator.start(3);
        for (Vec3d v : this.lineVecs) {
            tessellator.pos(v.x - offX, v.y - offY, v.z - offZ);
        }
        tessellator.draw();
        GL11.glLineWidth(1.0f);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }

    private void fixYawPitch(float startYaw, float startPitch) {
        float yawOffset = 0.0f;
        float pitchOffset = 0.0f;
        CutsceneCameraPoint prevPoint = new CutsceneCameraPoint(0.0f, 0.0f, 0.0f, 0.0f, startYaw, startPitch, 0);
        for (CutsceneCameraPoint c : this.cameraPoints) {
            if (prevPoint != null) {
                float delta;
                c.rotYaw += yawOffset;
                boolean keepUpdating = true;
                do {
                    if ((delta = c.rotYaw - prevPoint.rotYaw) > 180.0f) {
                        yawOffset -= 360.0f;
                        c.rotYaw -= 360.0f;
                        continue;
                    }
                    if (delta < -180.0f) {
                        yawOffset += 360.0f;
                        c.rotYaw += 360.0f;
                        continue;
                    }
                    keepUpdating = false;
                } while (keepUpdating);
                c.rotPitch += pitchOffset;
                keepUpdating = true;
                do {
                    if ((delta = c.rotPitch - prevPoint.rotPitch) > 180.0f) {
                        pitchOffset -= 360.0f;
                        c.rotPitch -= 360.0f;
                        continue;
                    }
                    if (delta < -180.0f) {
                        pitchOffset += 360.0f;
                        c.rotPitch += 360.0f;
                        continue;
                    }
                    keepUpdating = false;
                } while (keepUpdating);
            }
            prevPoint = c;
        }
    }

    public void clearPoints() {
        this.prevPrevPoint = null;
        this.prevPoint = null;
        this.cameraPoints.clear();
    }

    public void startCamera() {
        this.prevPrevPoint = null;
        this.prevPoint = null;
        this.startTime = AccessMinecraft.getInstance().level.getLevelTime();
    }

    private float cubicInterpolation(float mu, float y0, float y1, float y2, float y3) {
        float mu2 = mu * mu;
        float a0 = -0.5f * y0 + 1.5f * y1 - 1.5f * y2 + 0.5f * y3;
        float a1 = y0 - 2.5f * y1 + 2.0f * y2 - 0.5f * y3;
        float a2 = -0.5f * y0 + 0.5f * y2;
        float a3 = y1;
        return a0 * mu * mu2 + a1 * mu2 + a2 * mu + a3;
    }

    private float lerp(float mu, float y0, float y1) {
        return (1.0f - mu) * y0 + mu * y1;
    }

    public boolean isEmpty() {
        return this.cameraPoints.isEmpty();
    }

    public float getLastTime() {
        return this.cameraPoints.get(this.cameraPoints.size() - 1).time;
    }

    public CutsceneCameraPoint getCurrentPoint(float partialTickTime) {
        float elapsed = ((float) (AccessMinecraft.getInstance().level.getLevelTime() - this.startTime) + partialTickTime) / 20.0f;
        return this.getPoint(elapsed);
    }

    public CutsceneCameraPoint getPoint(float elapsed) {
        CutsceneCameraPoint nextNextPoint;
        if (this.prevPoint == null) {
            if (this.cameraPoints.isEmpty()) {
                return this.curPoint;
            }
            if (this.startType != 0) {
                ClientPlayer player = AccessMinecraft.getInstance().player;
                this.prevPoint = new CutsceneCameraPoint(0.0f, (float) player.x, (float) player.y, (float) player.z, player.yaw, player.pitch, this.startType);
                this.fixYawPitch(player.yaw, player.pitch);
            } else {
                CutsceneCameraPoint start = this.cameraPoints.get(0);
                this.prevPoint = new CutsceneCameraPoint(0.0f, start.posX, start.posY, start.posZ, start.rotYaw, start.rotPitch, this.startType);
            }
        }
        if (this.prevPoint.time > elapsed || this.cameraPoints.isEmpty()) {
            return this.prevPoint;
        }
        CutsceneCameraPoint nextPoint = this.cameraPoints.get(0);
        while (nextPoint != null && nextPoint.time < elapsed && !this.cameraPoints.isEmpty()) {
            this.prevPrevPoint = this.prevPoint;
            this.prevPoint = this.cameraPoints.remove(0);
            nextPoint = null;
            if (this.cameraPoints.isEmpty()) continue;
            nextPoint = this.cameraPoints.get(0);
            if (this.prevPrevPoint == null) continue;
            float timeDiff1 = nextPoint.time - this.prevPoint.time;
            float timeDiff2 = this.prevPoint.time - this.prevPrevPoint.time;
            if (timeDiff2 > 0.0f) {
                float m = timeDiff1 / timeDiff2;
                this.prevPrevPoint = new CutsceneCameraPoint(0.0f, this.prevPoint.posX - m * (this.prevPoint.posX - this.prevPrevPoint.posX), this.prevPoint.posY - m * (this.prevPoint.posY - this.prevPrevPoint.posY), this.prevPoint.posZ - m * (this.prevPoint.posZ - this.prevPrevPoint.posZ), this.prevPoint.rotYaw - m * (this.prevPoint.rotYaw - this.prevPrevPoint.rotYaw), this.prevPoint.rotPitch - m * (this.prevPoint.rotPitch - this.prevPrevPoint.rotPitch), 0);
                continue;
            }
            this.prevPrevPoint = new CutsceneCameraPoint(0.0f, this.prevPoint.posX, this.prevPoint.posY, this.prevPoint.posZ, this.prevPoint.rotYaw, this.prevPoint.rotPitch, 0);
        }
        if (nextPoint == null) {
            return this.prevPoint;
        }
        if (this.prevPrevPoint == null) {
            this.prevPrevPoint = new CutsceneCameraPoint(0.0f, 2.0f * this.prevPoint.posX - nextPoint.posX, 2.0f * this.prevPoint.posY - nextPoint.posY, 2.0f * this.prevPoint.posZ - nextPoint.posZ, 2.0f * this.prevPoint.rotYaw - nextPoint.rotYaw, 2.0f * this.prevPoint.rotPitch - nextPoint.rotPitch, 0);
        }
        if (this.cameraPoints.size() > 1) {
            nextNextPoint = this.cameraPoints.get(1);
            float timeDiff1 = nextPoint.time - this.prevPoint.time;
            float timeDiff2 = nextNextPoint.time - nextPoint.time;
            if (timeDiff2 > 0.0f) {
                float m = timeDiff1 / timeDiff2;
                nextNextPoint = new CutsceneCameraPoint(0.0f, nextPoint.posX + m * (nextNextPoint.posX - nextPoint.posX), nextPoint.posY + m * (nextNextPoint.posY - nextPoint.posY), nextPoint.posZ + m * (nextNextPoint.posZ - nextPoint.posZ), nextPoint.rotYaw + m * (nextNextPoint.rotYaw - nextPoint.rotYaw), nextPoint.rotPitch + m * (nextNextPoint.rotPitch - nextPoint.rotPitch), 0);
            } else {
                nextNextPoint = new CutsceneCameraPoint(0.0f, nextPoint.posX, nextPoint.posY, nextPoint.posZ, nextPoint.rotYaw, nextPoint.rotPitch, 0);
            }
        } else {
            nextNextPoint = new CutsceneCameraPoint(0.0f, 2.0f * nextPoint.posX - this.prevPoint.posX, 2.0f * nextPoint.posY - this.prevPoint.posY, 2.0f * nextPoint.posZ - this.prevPoint.posZ, 2.0f * nextPoint.rotYaw - this.prevPoint.rotYaw, 2.0f * nextPoint.rotPitch - this.prevPoint.rotPitch, 0);
        }
        float lerp = (elapsed - this.prevPoint.time) / (nextPoint.time - this.prevPoint.time);
        this.curPoint.time = elapsed;
        switch (this.prevPoint.cameraBlendType) {
            case 1: {
                this.curPoint.posX = this.lerp(lerp, this.prevPoint.posX, nextPoint.posX);
                this.curPoint.posY = this.lerp(lerp, this.prevPoint.posY, nextPoint.posY);
                this.curPoint.posZ = this.lerp(lerp, this.prevPoint.posZ, nextPoint.posZ);
                this.curPoint.rotYaw = this.lerp(lerp, this.prevPoint.rotYaw, nextPoint.rotYaw);
                this.curPoint.rotPitch = this.lerp(lerp, this.prevPoint.rotPitch, nextPoint.rotPitch);
                break;
            }
            case 2: {
                this.curPoint.posX = this.cubicInterpolation(lerp, this.prevPrevPoint.posX, this.prevPoint.posX, nextPoint.posX, nextNextPoint.posX);
                this.curPoint.posY = this.cubicInterpolation(lerp, this.prevPrevPoint.posY, this.prevPoint.posY, nextPoint.posY, nextNextPoint.posY);
                this.curPoint.posZ = this.cubicInterpolation(lerp, this.prevPrevPoint.posZ, this.prevPoint.posZ, nextPoint.posZ, nextNextPoint.posZ);
                this.curPoint.rotYaw = this.cubicInterpolation(lerp, this.prevPrevPoint.rotYaw, this.prevPoint.rotYaw, nextPoint.rotYaw, nextNextPoint.rotYaw);
                this.curPoint.rotPitch = this.cubicInterpolation(lerp, this.prevPrevPoint.rotPitch, this.prevPoint.rotPitch, nextPoint.rotPitch, nextNextPoint.rotPitch);
                break;
            }
            default: {
                this.curPoint.posX = this.prevPoint.posX;
                this.curPoint.posY = this.prevPoint.posY;
                this.curPoint.posZ = this.prevPoint.posZ;
                this.curPoint.rotYaw = this.prevPoint.rotYaw;
                this.curPoint.rotPitch = this.prevPoint.rotPitch;
            }
        }
        return this.curPoint;
    }

    public void deletePoint(int id) {
        CutsceneCameraPoint deleting = null;
        for (CutsceneCameraPoint p : this.cameraPoints) {
            if (p.cameraID != id) continue;
            deleting = p;
            break;
        }
        if (deleting != null) {
            this.cameraPoints.remove(deleting);
        }
    }

    public void setPointType(int id, int type) {
        for (CutsceneCameraPoint p : this.cameraPoints) {
            if (p.cameraID != id) continue;
            p.cameraBlendType = type;
            this.loadCameraEntities();
            return;
        }
    }

    public void setTime(int id, float time) {
        for (CutsceneCameraPoint p : this.cameraPoints) {
            if (p.cameraID != id) continue;
            p.time = time;
            this.loadCameraEntities();
            return;
        }
    }
}
