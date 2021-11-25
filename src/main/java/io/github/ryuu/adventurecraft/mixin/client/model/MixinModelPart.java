package io.github.ryuu.adventurecraft.mixin.client.model;

import net.minecraft.class_290;
import net.minecraft.class_552;
import net.minecraft.client.model.ModelPart;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ModelPart.class)
public class MixinModelPart {

    private final int xTexOffset;
    private final int yTexOffset;
    private final boolean compiled = false;
    private final int list = 0;
    private final int tWidth;
    private final int tHeight;
    public float pivotX;
    public float pivotY;
    public float pivotZ;
    public float pitch;
    public float yaw;
    public float roll;
    public boolean mirror = false;
    public boolean visible = true;
    public boolean hidden = false;
    @Shadow()
    private class_290[] vertices;
    private class_552[] field_2302;

    public MixinModelPart(int xTexOffset, int yTexOffset) {
        this(xTexOffset, yTexOffset, 64, 32);
    }

    public MixinModelPart(int i, int j, int w, int h) {
        this.xTexOffset = i;
        this.yTexOffset = j;
        this.tWidth = w;
        this.tHeight = h;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void addCuboid(float f, float f1, float f2, int i, int j, int k) {
        this.addCuboid(f, f1, f2, i, j, k, 0.0f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void addCuboid(float f, float f1, float f2, int i, int j, int k, float f3) {
        this.vertices = new class_290[8];
        this.field_2302 = new class_552[6];
        float f4 = f + (float) i;
        float f5 = f1 + (float) j;
        float f6 = f2 + (float) k;
        f -= f3;
        f1 -= f3;
        f2 -= f3;
        f4 += f3;
        f5 += f3;
        f6 += f3;
        if (this.mirror) {
            float f7 = f4;
            f4 = f;
            f = f7;
        }
        class_290 positiontexturevertex = new class_290(f, f1, f2, 0.0f, 0.0f);
        class_290 positiontexturevertex1 = new class_290(f4, f1, f2, 0.0f, 8.0f);
        class_290 positiontexturevertex2 = new class_290(f4, f5, f2, 8.0f, 8.0f);
        class_290 positiontexturevertex3 = new class_290(f, f5, f2, 8.0f, 0.0f);
        class_290 positiontexturevertex4 = new class_290(f, f1, f6, 0.0f, 0.0f);
        class_290 positiontexturevertex5 = new class_290(f4, f1, f6, 0.0f, 8.0f);
        class_290 positiontexturevertex6 = new class_290(f4, f5, f6, 8.0f, 8.0f);
        class_290 positiontexturevertex7 = new class_290(f, f5, f6, 8.0f, 0.0f);
        this.vertices[0] = positiontexturevertex;
        this.vertices[1] = positiontexturevertex1;
        this.vertices[2] = positiontexturevertex2;
        this.vertices[3] = positiontexturevertex3;
        this.vertices[4] = positiontexturevertex4;
        this.vertices[5] = positiontexturevertex5;
        this.vertices[6] = positiontexturevertex6;
        this.vertices[7] = positiontexturevertex7;
        this.field_2302[0] = new class_552(new class_290[]{positiontexturevertex5, positiontexturevertex1, positiontexturevertex2, positiontexturevertex6}, this.xTexOffset + k + i, this.yTexOffset + k, this.xTexOffset + k + i + k, this.yTexOffset + k + j, this.tWidth, this.tHeight);
        this.field_2302[1] = new class_552(new class_290[]{positiontexturevertex, positiontexturevertex4, positiontexturevertex7, positiontexturevertex3}, this.xTexOffset + 0, this.yTexOffset + k, this.xTexOffset + k, this.yTexOffset + k + j, this.tWidth, this.tHeight);
        this.field_2302[2] = new class_552(new class_290[]{positiontexturevertex5, positiontexturevertex4, positiontexturevertex, positiontexturevertex1}, this.xTexOffset + k, this.yTexOffset + 0, this.xTexOffset + k + i, this.yTexOffset + k, this.tWidth, this.tHeight);
        this.field_2302[3] = new class_552(new class_290[]{positiontexturevertex2, positiontexturevertex3, positiontexturevertex7, positiontexturevertex6}, this.xTexOffset + k + i, this.yTexOffset + 0, this.xTexOffset + k + i + i, this.yTexOffset + k, this.tWidth, this.tHeight);
        this.field_2302[4] = new class_552(new class_290[]{positiontexturevertex1, positiontexturevertex, positiontexturevertex3, positiontexturevertex2}, this.xTexOffset + k, this.yTexOffset + k, this.xTexOffset + k + i, this.yTexOffset + k + j, this.tWidth, this.tHeight);
        this.field_2302[5] = new class_552(new class_290[]{positiontexturevertex4, positiontexturevertex5, positiontexturevertex6, positiontexturevertex7}, this.xTexOffset + k + i + k, this.yTexOffset + k, this.xTexOffset + k + i + k + i, this.yTexOffset + k + j, this.tWidth, this.tHeight);
        if (this.mirror) {
            for (net.minecraft.class_552 class_552 : this.field_2302) {
                class_552.method_1925();
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void addBoxInverted(float f, float f1, float f2, int i, int j, int k, float f3) {
        this.vertices = new class_290[8];
        this.field_2302 = new class_552[6];
        float f4 = f + (float) i;
        float f5 = f1 + (float) j;
        float f6 = f2 + (float) k;
        f -= f3;
        f1 -= f3;
        f2 -= f3;
        f4 += f3;
        f5 += f3;
        f6 += f3;
        if (this.mirror) {
            float f7 = f4;
            f4 = f;
            f = f7;
        }
        class_290 positiontexturevertex = new class_290(f, f1, f2, 0.0f, 0.0f);
        class_290 positiontexturevertex1 = new class_290(f4, f1, f2, 0.0f, 8.0f);
        class_290 positiontexturevertex2 = new class_290(f4, f5, f2, 8.0f, 8.0f);
        class_290 positiontexturevertex3 = new class_290(f, f5, f2, 8.0f, 0.0f);
        class_290 positiontexturevertex4 = new class_290(f, f1, f6, 0.0f, 0.0f);
        class_290 positiontexturevertex5 = new class_290(f4, f1, f6, 0.0f, 8.0f);
        class_290 positiontexturevertex6 = new class_290(f4, f5, f6, 8.0f, 8.0f);
        class_290 positiontexturevertex7 = new class_290(f, f5, f6, 8.0f, 0.0f);
        this.vertices[0] = positiontexturevertex;
        this.vertices[1] = positiontexturevertex1;
        this.vertices[2] = positiontexturevertex2;
        this.vertices[3] = positiontexturevertex3;
        this.vertices[4] = positiontexturevertex4;
        this.vertices[5] = positiontexturevertex5;
        this.vertices[6] = positiontexturevertex6;
        this.vertices[7] = positiontexturevertex7;
        this.field_2302[0] = new class_552(new class_290[]{positiontexturevertex5, positiontexturevertex1, positiontexturevertex2, positiontexturevertex6}, this.xTexOffset + k + i + k, this.yTexOffset + k + j, this.xTexOffset + k + i + 0, this.yTexOffset + k, this.tWidth, this.tHeight);
        this.field_2302[1] = new class_552(new class_290[]{positiontexturevertex, positiontexturevertex4, positiontexturevertex7, positiontexturevertex3}, this.xTexOffset + k, this.yTexOffset + k + j, this.xTexOffset + 0, this.yTexOffset + k, this.tWidth, this.tHeight);
        this.field_2302[2] = new class_552(new class_290[]{positiontexturevertex5, positiontexturevertex4, positiontexturevertex, positiontexturevertex1}, this.xTexOffset + k + i + i, this.yTexOffset + 0, this.xTexOffset + k + i, this.yTexOffset + k, this.tWidth, this.tHeight);
        this.field_2302[3] = new class_552(new class_290[]{positiontexturevertex2, positiontexturevertex3, positiontexturevertex7, positiontexturevertex6}, this.xTexOffset + k + i, this.yTexOffset + 0, this.xTexOffset + k, this.yTexOffset + k, this.tWidth, this.tHeight);
        this.field_2302[4] = new class_552(new class_290[]{positiontexturevertex1, positiontexturevertex, positiontexturevertex3, positiontexturevertex2}, this.xTexOffset + k + i + k + i, this.yTexOffset + k + j, this.xTexOffset + k + i + k, this.yTexOffset + k, this.tWidth, this.tHeight);
        this.field_2302[5] = new class_552(new class_290[]{positiontexturevertex4, positiontexturevertex5, positiontexturevertex6, positiontexturevertex7}, this.xTexOffset + k + i, this.yTexOffset + k + j, this.xTexOffset + k, this.yTexOffset + k, this.tWidth, this.tHeight);
        if (this.mirror) {
            for (net.minecraft.class_552 class_552 : this.field_2302) {
                class_552.method_1925();
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setPivot(float f, float f1, float f2) {
        this.pivotX = f;
        this.pivotY = f1;
        this.pivotZ = f2;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void render(float f) {
        if (this.hidden) {
            return;
        }
        if (!this.visible) {
            return;
        }
        if (!this.compiled) {
            this.compile(f);
        }
        if (this.pitch != 0.0f || this.yaw != 0.0f || this.roll != 0.0f) {
            GL11.glPushMatrix();
            GL11.glTranslatef(this.pivotX * f, this.pivotY * f, this.pivotZ * f);
            if (this.roll != 0.0f) {
                GL11.glRotatef(this.roll * 57.29578f, 0.0f, 0.0f, 1.0f);
            }
            if (this.yaw != 0.0f) {
                GL11.glRotatef(this.yaw * 57.29578f, 0.0f, 1.0f, 0.0f);
            }
            if (this.pitch != 0.0f) {
                GL11.glRotatef(this.pitch * 57.29578f, 1.0f, 0.0f, 0.0f);
            }
            GL11.glCallList(this.list);
            GL11.glPopMatrix();
        } else if (this.pivotX != 0.0f || this.pivotY != 0.0f || this.pivotZ != 0.0f) {
            GL11.glTranslatef(this.pivotX * f, this.pivotY * f, this.pivotZ * f);
            GL11.glCallList(this.list);
            GL11.glTranslatef(-this.pivotX * f, -this.pivotY * f, -this.pivotZ * f);
        } else {
            GL11.glCallList(this.list);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1819(float f) {
        if (this.hidden) {
            return;
        }
        if (!this.visible) {
            return;
        }
        if (!this.compiled) {
            this.compile(f);
        }
        GL11.glPushMatrix();
        GL11.glTranslatef(this.pivotX * f, this.pivotY * f, this.pivotZ * f);
        if (this.yaw != 0.0f) {
            GL11.glRotatef(this.yaw * 57.29578f, 0.0f, 1.0f, 0.0f);
        }
        if (this.pitch != 0.0f) {
            GL11.glRotatef(this.pitch * 57.29578f, 1.0f, 0.0f, 0.0f);
        }
        if (this.roll != 0.0f) {
            GL11.glRotatef(this.roll * 57.29578f, 0.0f, 0.0f, 1.0f);
        }
        GL11.glCallList(this.list);
        GL11.glPopMatrix();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1820(float f) {
        if (this.hidden) {
            return;
        }
        if (!this.visible) {
            return;
        }
        if (!this.compiled) {
            this.compile(f);
        }
        if (this.pitch != 0.0f || this.yaw != 0.0f || this.roll != 0.0f) {
            GL11.glTranslatef(this.pivotX * f, this.pivotY * f, this.pivotZ * f);
            if (this.roll != 0.0f) {
                GL11.glRotatef(this.roll * 57.29578f, 0.0f, 0.0f, 1.0f);
            }
            if (this.yaw != 0.0f) {
                GL11.glRotatef(this.yaw * 57.29578f, 0.0f, 1.0f, 0.0f);
            }
            if (this.pitch != 0.0f) {
                GL11.glRotatef(this.pitch * 57.29578f, 1.0f, 0.0f, 0.0f);
            }
        } else if (this.pivotX != 0.0f || this.pivotY != 0.0f || this.pivotZ != 0.0f) {
            GL11.glTranslatef(this.pivotX * f, this.pivotY * f, this.pivotZ * f);
        }
    }
}
