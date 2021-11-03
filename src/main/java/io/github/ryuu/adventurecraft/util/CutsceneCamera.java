package io.github.ryuu.adventurecraft.util;

import java.util.LinkedList;
import java.util.List;

import io.github.ryuu.adventurecraft.entities.EntityCamera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.lwjgl.opengl.GL11;

public class CutsceneCamera {
    long startTime;

    CutsceneCameraPoint curPoint = new CutsceneCameraPoint(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0);

    CutsceneCameraPoint prevPrevPoint;

    CutsceneCameraPoint prevPoint;

    public List<CutsceneCameraPoint> cameraPoints = new LinkedList<>();

    List<Vec3D> lineVecs = new LinkedList<Vec3D>();

    public void addCameraPoint(float time, float posX, float posY, float posZ, float yaw, float pitch, int type) {
        int index = 0;
        for (CutsceneCameraPoint p : this.cameraPoints) {
            if (time < p.time)
                break;
            index++;
        }
        this.cameraPoints.add(index, new CutsceneCameraPoint(time, posX, posY, posZ, yaw, pitch, type));
        fixYawPitch(0.0F, 0.0F);
    }

    public void loadCameraEntities() {
        for (Entity obj : Minecraft.minecraftInstance.f.b) {
            if (obj instanceof EntityCamera)
                ((Entity) obj).K();
        }
        for (CutsceneCameraPoint p : this.cameraPoints) {
            EntityCamera e = new EntityCamera(Minecraft.minecraftInstance.f, p.time, p.cameraBlendType, p.cameraID);
            e.b(p.posX, p.posY, p.posZ, p.rotYaw, p.rotPitch);
            Minecraft.minecraftInstance.f.b((Entity) e);
        }
        CutsceneCamera c = new CutsceneCamera();
        for (CutsceneCameraPoint p : this.cameraPoints)
            c.addCameraPoint(p.time, p.posX, p.posY, p.posZ, p.rotYaw, p.rotPitch, p.cameraBlendType);
        CutsceneCameraPoint prev = null;
        this.lineVecs.clear();
        for (CutsceneCameraPoint p : this.cameraPoints) {
            if (prev != null) {
                for (int i = 0; i < 25; i++) {
                    float lerpValue = (i + 1) / 25.0F;
                    float time = lerp(lerpValue, prev.time, p.time);
                    CutsceneCameraPoint point = c.getPoint(time);
                    bt v = bt.a(point.posX, point.posY, point.posZ);
                    this.lineVecs.add(v);
                }
            } else {
                this.lineVecs.add(bt.a(p.posX, p.posY, p.posZ));
            }
            prev = p;
        }
    }

    public void drawLines(LivingEntity entityplayer, float f) {
        double offX = entityplayer.bl + (entityplayer.aM - entityplayer.bl) * f;
        double offY = entityplayer.bm + (entityplayer.aN - entityplayer.bm) * f;
        double offZ = entityplayer.bn + (entityplayer.aO - entityplayer.bn) * f;
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0F, 0.2F, 0.0F, 1.0F);
        GL11.glLineWidth(5.0F);
        GL11.glDisable(3553);
        Tessellator tessellator = Tessellator.a;
        tessellator.a(3);
        for (bt v : this.lineVecs)
            tessellator.a(v.a - offX, v.b - offY, v.c - offZ);
        tessellator.a();
        GL11.glLineWidth(1.0F);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }

    private void fixYawPitch(float startYaw, float startPitch) {
        float yawOffset = 0.0F;
        float pitchOffset = 0.0F;
        CutsceneCameraPoint prevPoint = new CutsceneCameraPoint(0.0F, 0.0F, 0.0F, 0.0F, startYaw, startPitch, 0);
        for (CutsceneCameraPoint c : this.cameraPoints) {
            if (prevPoint != null) {
                c.rotYaw += yawOffset;
                boolean keepUpdating = true;
                do {
                    float delta = c.rotYaw - prevPoint.rotYaw;
                    if (delta > 180.0F) {
                        yawOffset -= 360.0F;
                        c.rotYaw -= 360.0F;
                    } else if (delta < -180.0F) {
                        yawOffset += 360.0F;
                        c.rotYaw += 360.0F;
                    } else {
                        keepUpdating = false;
                    }
                } while (keepUpdating);
                c.rotPitch += pitchOffset;
                keepUpdating = true;
                do {
                    float f = c.rotPitch - prevPoint.rotPitch;
                    if (f > 180.0F) {
                        pitchOffset -= 360.0F;
                        c.rotPitch -= 360.0F;
                    } else if (f < -180.0F) {
                        pitchOffset += 360.0F;
                        c.rotPitch += 360.0F;
                    } else {
                        keepUpdating = false;
                    }
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

    void startCamera() {
        this.prevPrevPoint = null;
        this.prevPoint = null;
        this.startTime = Minecraft.minecraftInstance.f.t();
    }

    private float cubicInterpolation(float mu, float y0, float y1, float y2, float y3) {
        float mu2 = mu * mu;
        float a0 = -0.5F * y0 + 1.5F * y1 - 1.5F * y2 + 0.5F * y3;
        float a1 = y0 - 2.5F * y1 + 2.0F * y2 - 0.5F * y3;
        float a2 = -0.5F * y0 + 0.5F * y2;
        float a3 = y1;
        return a0 * mu * mu2 + a1 * mu2 + a2 * mu + a3;
    }

    private float lerp(float mu, float y0, float y1) {
        return (1.0F - mu) * y0 + mu * y1;
    }

    boolean isEmpty() {
        return this.cameraPoints.isEmpty();
    }

    float getLastTime() {
        return this.cameraPoints.get(this.cameraPoints.size() - 1).time;
    }

    CutsceneCameraPoint getCurrentPoint(float partialTickTime) {
        float elapsed = ((float) (Minecraft.minecraftInstance.f.t() - this.startTime) + partialTickTime) / 20.0F;
        return getPoint(elapsed);
    }

    CutsceneCameraPoint getPoint(float elapsed) {
        CutsceneCameraPoint nextNextPoint;
        if (this.prevPoint == null) {
            if (this.cameraPoints.isEmpty())
                return this.curPoint;
            if (this.startType != 0) {
                Entity player = Minecraft.minecraftInstance.h;
                this.prevPoint = new CutsceneCameraPoint(0.0F, (float) player.aM, (float) player.aN, (float) player.aO, player.aS, player.aT, this.startType);
                fixYawPitch(player.aS, player.aT);
            } else {
                CutsceneCameraPoint start = this.cameraPoints.get(0);
                this.prevPoint = new CutsceneCameraPoint(0.0F, start.posX, start.posY, start.posZ, start.rotYaw, start.rotPitch, this.startType);
            }
        }
        if (this.prevPoint.time > elapsed || this.cameraPoints.isEmpty())
            return this.prevPoint;
        CutsceneCameraPoint nextPoint = this.cameraPoints.get(0);
        while (nextPoint != null && nextPoint.time < elapsed && !this.cameraPoints.isEmpty()) {
            this.prevPrevPoint = this.prevPoint;
            this.prevPoint = this.cameraPoints.remove(0);
            nextPoint = null;
            if (!this.cameraPoints.isEmpty()) {
                nextPoint = this.cameraPoints.get(0);
                if (this.prevPrevPoint != null) {
                    float timeDiff1 = nextPoint.time - this.prevPoint.time;
                    float timeDiff2 = this.prevPoint.time - this.prevPrevPoint.time;
                    if (timeDiff2 > 0.0F) {
                        float m = timeDiff1 / timeDiff2;
                        this.prevPrevPoint = new CutsceneCameraPoint(0.0F, this.prevPoint.posX - m * (this.prevPoint.posX - this.prevPrevPoint.posX), this.prevPoint.posY - m * (this.prevPoint.posY - this.prevPrevPoint.posY), this.prevPoint.posZ - m * (this.prevPoint.posZ - this.prevPrevPoint.posZ), this.prevPoint.rotYaw - m * (this.prevPoint.rotYaw - this.prevPrevPoint.rotYaw), this.prevPoint.rotPitch - m * (this.prevPoint.rotPitch - this.prevPrevPoint.rotPitch), 0);
                        continue;
                    }
                    this.prevPrevPoint = new CutsceneCameraPoint(0.0F, this.prevPoint.posX, this.prevPoint.posY, this.prevPoint.posZ, this.prevPoint.rotYaw, this.prevPoint.rotPitch, 0);
                }
            }
        }
        if (nextPoint == null)
            return this.prevPoint;
        if (this.prevPrevPoint == null)
            this.prevPrevPoint = new CutsceneCameraPoint(0.0F, 2.0F * this.prevPoint.posX - nextPoint.posX, 2.0F * this.prevPoint.posY - nextPoint.posY, 2.0F * this.prevPoint.posZ - nextPoint.posZ, 2.0F * this.prevPoint.rotYaw - nextPoint.rotYaw, 2.0F * this.prevPoint.rotPitch - nextPoint.rotPitch, 0);
        if (this.cameraPoints.size() > 1) {
            nextNextPoint = this.cameraPoints.get(1);
            float timeDiff1 = nextPoint.time - this.prevPoint.time;
            float timeDiff2 = nextNextPoint.time - nextPoint.time;
            if (timeDiff2 > 0.0F) {
                float m = timeDiff1 / timeDiff2;
                nextNextPoint = new CutsceneCameraPoint(0.0F, nextPoint.posX + m * (nextNextPoint.posX - nextPoint.posX), nextPoint.posY + m * (nextNextPoint.posY - nextPoint.posY), nextPoint.posZ + m * (nextNextPoint.posZ - nextPoint.posZ), nextPoint.rotYaw + m * (nextNextPoint.rotYaw - nextPoint.rotYaw), nextPoint.rotPitch + m * (nextNextPoint.rotPitch - nextPoint.rotPitch), 0);
            } else {
                nextNextPoint = new CutsceneCameraPoint(0.0F, nextPoint.posX, nextPoint.posY, nextPoint.posZ, nextPoint.rotYaw, nextPoint.rotPitch, 0);
            }
        } else {
            nextNextPoint = new CutsceneCameraPoint(0.0F, 2.0F * nextPoint.posX - this.prevPoint.posX, 2.0F * nextPoint.posY - this.prevPoint.posY, 2.0F * nextPoint.posZ - this.prevPoint.posZ, 2.0F * nextPoint.rotYaw - this.prevPoint.rotYaw, 2.0F * nextPoint.rotPitch - this.prevPoint.rotPitch, 0);
        }
        float lerp = (elapsed - this.prevPoint.time) / (nextPoint.time - this.prevPoint.time);
        this.curPoint.time = elapsed;
        switch (this.prevPoint.cameraBlendType) {
            case 1:
                this.curPoint.posX = lerp(lerp, this.prevPoint.posX, nextPoint.posX);
                this.curPoint.posY = lerp(lerp, this.prevPoint.posY, nextPoint.posY);
                this.curPoint.posZ = lerp(lerp, this.prevPoint.posZ, nextPoint.posZ);
                this.curPoint.rotYaw = lerp(lerp, this.prevPoint.rotYaw, nextPoint.rotYaw);
                this.curPoint.rotPitch = lerp(lerp, this.prevPoint.rotPitch, nextPoint.rotPitch);
                return this.curPoint;
            case 2:
                this.curPoint.posX = cubicInterpolation(lerp, this.prevPrevPoint.posX, this.prevPoint.posX, nextPoint.posX, nextNextPoint.posX);
                this.curPoint.posY = cubicInterpolation(lerp, this.prevPrevPoint.posY, this.prevPoint.posY, nextPoint.posY, nextNextPoint.posY);
                this.curPoint.posZ = cubicInterpolation(lerp, this.prevPrevPoint.posZ, this.prevPoint.posZ, nextPoint.posZ, nextNextPoint.posZ);
                this.curPoint.rotYaw = cubicInterpolation(lerp, this.prevPrevPoint.rotYaw, this.prevPoint.rotYaw, nextPoint.rotYaw, nextNextPoint.rotYaw);
                this.curPoint.rotPitch = cubicInterpolation(lerp, this.prevPrevPoint.rotPitch, this.prevPoint.rotPitch, nextPoint.rotPitch, nextNextPoint.rotPitch);
                return this.curPoint;
        }
        this.curPoint.posX = this.prevPoint.posX;
        this.curPoint.posY = this.prevPoint.posY;
        this.curPoint.posZ = this.prevPoint.posZ;
        this.curPoint.rotYaw = this.prevPoint.rotYaw;
        this.curPoint.rotPitch = this.prevPoint.rotPitch;
        return this.curPoint;
    }

    public void deletePoint(int id) {
        CutsceneCameraPoint deleting = null;
        for (CutsceneCameraPoint p : this.cameraPoints) {
            if (p.cameraID == id) {
                deleting = p;
                break;
            }
        }
        if (deleting != null)
            this.cameraPoints.remove(deleting);
    }

    void setPointType(int id, int type) {
        for (CutsceneCameraPoint p : this.cameraPoints) {
            if (p.cameraID == id) {
                p.cameraBlendType = type;
                loadCameraEntities();
                return;
            }
        }
    }

    void setTime(int id, float time) {
        for (CutsceneCameraPoint p : this.cameraPoints) {
            if (p.cameraID == id) {
                p.time = time;
                loadCameraEntities();
                return;
            }
        }
    }

    int startType = 2;
}
