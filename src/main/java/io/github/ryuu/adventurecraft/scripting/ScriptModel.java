package io.github.ryuu.adventurecraft.scripting;

import java.nio.FloatBuffer;
import java.util.LinkedList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.level.Level;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

public class ScriptModel {
    private static final FloatBuffer modelview = BufferUtils.createFloatBuffer(16);
    private static final Matrix4f transform = new Matrix4f();
    private static final Vector4f v = new Vector4f();
    private static final Vector4f vr = new Vector4f();
    static LinkedList<ScriptModel> activeModels = new LinkedList<>();
    private final int textureWidth;
    private final int textureHeight;
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
    HashMap<String, ModelRenderer> boxes;

    public ScriptModel() {
        this(64, 32);
    }

    public ScriptModel(int texWidth, int texHeight) {
        this.boxes = new HashMap<String, ModelRenderer>();
        addToRendering();
        this.textureWidth = texWidth;
        this.textureHeight = texHeight;
    }

    public static void renderAll(float f) {
        GL11.glEnable(32826);
        GL11.glDisable(2884);
        GL11.glEnable(3008);
        for (ScriptModel m : activeModels)
            m.render(f);
        GL11.glEnable(2884);
        GL11.glDisable(32826);
    }

    public static void updateAll() {
        for (ScriptModel m : activeModels)
            m.update();
    }

    public static void clearAll() {
        activeModels.clear();
    }

    public void addBox(String boxName, float offsetX, float offsetY, float offsetZ, int width, int height, int depth, int u, int v) {
        addBoxExpanded(boxName, offsetX, offsetY, offsetZ, width, height, depth, u, v, 0.0F);
    }

    public void addBoxExpanded(String boxName, float offsetX, float offsetY, float offsetZ, int width, int height, int depth, int u, int v, float expand) {
        ModelPart r = new ModelPart(u, v, this.textureWidth, this.textureHeight);
        r.addBoxInverted(offsetX, offsetY, offsetZ, width, height, depth, expand);
        this.boxes.put((Object)boxName, (Object)r);
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
        double yawR = Math.toRadians(this.yaw);
        double pitchR = Math.toRadians(this.pitch);
        double rollR = Math.toRadians(this.roll);
        double t = moveX * Math.cos(yawR) + moveZ * Math.sin(yawR);
        moveZ = moveZ * Math.cos(yawR) - moveX * Math.sin(yawR);
        moveX = t;
        t = moveZ * Math.cos(pitchR) + moveY * Math.sin(pitchR);
        moveY = moveY * Math.cos(pitchR) - moveZ * Math.sin(pitchR);
        moveZ = t;
        t = moveY * Math.cos(rollR) + moveX * Math.sin(rollR);
        moveX = moveX * Math.cos(rollR) - moveY * Math.sin(rollR);
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
            GL11.glTranslated(pos.x, pos.y, pos.z);
            GL11.glRotatef((float) -rot.yaw, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef((float) rot.pitch, 1.0F, 0.0F, 0.0F);
        } else if (this.modelAttachment != null) {
            this.modelAttachment.transform(f);
        }
        float iF = 1.0F - f;
        double tX = f * this.x + iF * this.prevX;
        double tY = f * this.y + iF * this.prevY;
        double tZ = f * this.z + iF * this.prevZ;
        GL11.glTranslated(tX, tY, tZ);
        GL11.glRotatef(f * this.yaw + iF * this.prevYaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(f * this.pitch + iF * this.prevPitch, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(f * this.roll + iF * this.prevRoll, 0.0F, 0.0F, 1.0F);
    }

    private void render(float f) {
        Level w = Minecraft.minecraftInstance.level;
        TextureManager renderEngine = Minecraft.minecraftInstance.textureManager;
        if (this.texture != null && !this.texture.equals("")) {
            renderEngine.bindTexture(renderEngine.getTextureId(this.texture));
        }
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        this.transform(f);
        modelview.rewind();
        GL11.glGetFloat(2982, modelview);
        transform.load(modelview);
        GL11.glPopMatrix();
        v.set(0.0f, 0.0f, 0.0f, 1.0f);
        Matrix4f.transform(transform, v, vr);
        float b = w.getBrightness(Math.round(ScriptModel.vr.x), Math.round(ScriptModel.vr.y), Math.round(ScriptModel.vr.z));
        GL11.glColor3f(b, b, b);
        GL11.glPushMatrix();
        this.transform(f);
        for (ModelPart r : this.boxes.values()) {
            r.render(0.0625f);
        }
        GL11.glPopMatrix();
    }

    public void removeFromRendering() {
        activeModels.remove(this);
    }

    public void addToRendering() {
        activeModels.add(this);
    }
}