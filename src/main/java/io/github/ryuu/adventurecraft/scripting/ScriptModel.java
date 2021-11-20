package io.github.ryuu.adventurecraft.scripting;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.LinkedList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.level.Level;
import net.minecraft.script.ScriptEntity;
import net.minecraft.script.ScriptVec3;
import net.minecraft.script.ScriptVecRot;
import net.minecraft.src.ModelRenderer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

public class ScriptModel {

    HashMap<String, ModelRenderer> boxes = new HashMap();

    public ScriptEntity attachedTo;

    public ScriptModel modelAttachment;

    public String texture;

    public double prevX;

    public double prevY;

    public double prevZ;

    public float prevYaw;

    public float prevPitch;

    public float prevRoll;

    public double x;

    public double y;

    public double z;

    public float yaw;

    public float pitch;

    public float roll;

    private int textureWidth;

    private int textureHeight;

    private static FloatBuffer modelview = BufferUtils.createFloatBuffer((int) 16);

    private static Matrix4f transform = new Matrix4f();

    private static Vector4f v = new Vector4f();

    private static Vector4f vr = new Vector4f();

    static LinkedList<ScriptModel> activeModels = new LinkedList();

    public ScriptModel() {
        this(64, 32);
    }

    public ScriptModel(int texWidth, int texHeight) {
        this.addToRendering();
        this.textureWidth = texWidth;
        this.textureHeight = texHeight;
    }

    public void addBox(String boxName, float offsetX, float offsetY, float offsetZ, int width, int height, int depth, int u, int v) {
        this.addBoxExpanded(boxName, offsetX, offsetY, offsetZ, width, height, depth, u, v, 0.0f);
    }

    public void addBoxExpanded(String boxName, float offsetX, float offsetY, float offsetZ, int width, int height, int depth, int u, int v, float expand) {
        MixinModelPart r = new MixinModelPart(u, v, this.textureWidth, this.textureHeight);
        r.addBoxInverted(offsetX, offsetY, offsetZ, width, height, depth, expand);
        this.boxes.put((Object) boxName, (Object) r);
    }

    public void setPosition(double newX, double newY, double newZ) {
        this.prevX = this.x = newX;
        this.prevY = this.y = newY;
        this.prevZ = this.z = newZ;
    }

    public void setRotation(float newYaw, float newPitch, float newRoll) {
        this.prevYaw = this.yaw = newYaw;
        this.prevPitch = this.pitch = newPitch;
        this.prevRoll = this.roll = newRoll;
    }

    public void moveTo(double newX, double newY, double newZ) {
        this.x = newX;
        this.y = newY;
        this.z = newZ;
    }

    public void moveBy(double moveX, double moveY, double moveZ) {
        double yawR = Math.toRadians((double) this.yaw);
        double pitchR = Math.toRadians((double) this.pitch);
        double rollR = Math.toRadians((double) this.roll);
        double t = moveX * Math.cos((double) yawR) + moveZ * Math.sin((double) yawR);
        moveZ = moveZ * Math.cos((double) yawR) - moveX * Math.sin((double) yawR);
        moveX = t;
        t = moveZ * Math.cos((double) pitchR) + moveY * Math.sin((double) pitchR);
        moveY = moveY * Math.cos((double) pitchR) - moveZ * Math.sin((double) pitchR);
        moveZ = t;
        t = moveY * Math.cos((double) rollR) + moveX * Math.sin((double) rollR);
        moveX = moveX * Math.cos((double) rollR) - moveY * Math.sin((double) rollR);
        moveY = t;
        this.x += moveX;
        this.y += moveY;
        this.z += moveZ;
    }

    public void rotateTo(float newYaw, float newPitch, float newRoll) {
        this.yaw = newYaw;
        this.pitch = newPitch;
        this.roll = newRoll;
    }

    public void rotateBy(float rYaw, float rPitch, float rRoll) {
        this.yaw += rYaw;
        this.pitch += rPitch;
        this.roll += rRoll;
    }

    private void update() {
        this.prevX = this.x;
        this.prevY = this.y;
        this.prevZ = this.z;
        this.prevYaw = this.yaw;
        this.prevPitch = this.pitch;
        this.prevRoll = this.roll;
    }

    private void transform(float f) {
        if (this.attachedTo != null) {
            ScriptVec3 pos = this.attachedTo.getPosition(f);
            ScriptVecRot rot = this.attachedTo.getRotation(f);
            GL11.glTranslated((double) pos.x, (double) pos.y, (double) pos.z);
            GL11.glRotatef((float) ((float) (-rot.yaw)), (float) 0.0f, (float) 1.0f, (float) 0.0f);
            GL11.glRotatef((float) ((float) rot.pitch), (float) 1.0f, (float) 0.0f, (float) 0.0f);
        } else if (this.modelAttachment != null) {
            this.modelAttachment.transform(f);
        }
        float iF = 1.0f - f;
        double tX = (double) f * this.x + (double) iF * this.prevX;
        double tY = (double) f * this.y + (double) iF * this.prevY;
        double tZ = (double) f * this.z + (double) iF * this.prevZ;
        GL11.glTranslated((double) tX, (double) tY, (double) tZ);
        GL11.glRotatef((float) (f * this.yaw + iF * this.prevYaw), (float) 0.0f, (float) 1.0f, (float) 0.0f);
        GL11.glRotatef((float) (f * this.pitch + iF * this.prevPitch), (float) 1.0f, (float) 0.0f, (float) 0.0f);
        GL11.glRotatef((float) (f * this.roll + iF * this.prevRoll), (float) 0.0f, (float) 0.0f, (float) 1.0f);
    }

    private void render(float f) {
        MixinLevel w = Minecraft.minecraftInstance.level;
        MixinTextureManager renderEngine = Minecraft.minecraftInstance.textureManager;
        if (this.texture != null && !this.texture.equals((Object) "")) {
            renderEngine.bindTexture(renderEngine.getTextureId(this.texture));
        }
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        this.transform(f);
        modelview.rewind();
        GL11.glGetFloat((int) 2982, (FloatBuffer) modelview);
        transform.load(modelview);
        GL11.glPopMatrix();
        v.set(0.0f, 0.0f, 0.0f, 1.0f);
        Matrix4f.transform((Matrix4f) transform, (Vector4f) v, (Vector4f) vr);
        float b = w.getBrightness(Math.round((float) ScriptModel.vr.x), Math.round((float) ScriptModel.vr.y), Math.round((float) ScriptModel.vr.z));
        GL11.glColor3f((float) b, (float) b, (float) b);
        GL11.glPushMatrix();
        this.transform(f);
        for (MixinModelPart r : this.boxes.values()) {
            r.render(0.0625f);
        }
        GL11.glPopMatrix();
    }

    public void removeFromRendering() {
        activeModels.remove((Object) this);
    }

    public void addToRendering() {
        activeModels.add((Object) this);
    }

    public static void renderAll(float f) {
        GL11.glEnable((int) 32826);
        GL11.glDisable((int) 2884);
        GL11.glEnable((int) 3008);
        for (ScriptModel m : activeModels) {
            m.render(f);
        }
        GL11.glEnable((int) 2884);
        GL11.glDisable((int) 32826);
    }

    public static void updateAll() {
        for (ScriptModel m : activeModels) {
            m.update();
        }
    }

    public static void clearAll() {
        activeModels.clear();
    }
}
