package io.github.ryuu.adventurecraft.mixin.client.particle;

import io.github.ryuu.adventurecraft.extensions.client.particle.ExParticleManager;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathsHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(ParticleManager.class)
public abstract class MixinParticleManager implements ExParticleManager {

    @Shadow
    private List<Entity>[] particles;

    @Shadow
    private TextureManager textureManager;

    @Shadow
    protected Level level;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void changeArraySize(Level arg1, TextureManager par2, CallbackInfo ci) {
        particles = Arrays.copyOf(particles, particles.length + 2);
        particles[particles.length - 2] = new ArrayList<>();
        particles[particles.length - 1] = new ArrayList<>();
    }

    @Override
    public List<Entity> getEffectsWithinAABB(Box axisalignedbb) {
        ArrayList<Entity> arraylist = new ArrayList<>();
        for (int i = 0; i < 6; ++i) {
            for (Entity p : this.particles[i]) {
                if (axisalignedbb.minX <= p.x && axisalignedbb.maxX >= p.x && axisalignedbb.minY <= p.y && axisalignedbb.maxY >= p.y && axisalignedbb.minZ <= p.z && axisalignedbb.maxZ >= p.z) {
                    arraylist.add(p);
                }
            }
        }
        return arraylist;
    }

    @ModifyArg(method = "method_324", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/TextureManager;getTextureId(Ljava/lang/String;)I", ordinal = 0))
    private String changeTexturesPath(String path) {
        return "/particles.png";
    }

    @ModifyArg(method = "method_324", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/TextureManager;getTextureId(Ljava/lang/String;)I", ordinal = 1))
    private String changeTexturesPath1(String path) {
        return "/terrain.png";
    }

    @ModifyArg(method = "method_324", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/TextureManager;getTextureId(Ljava/lang/String;)I", ordinal = 2))
    private String changeTexturesPath2(String path) {
        return "/gui/items.png";
    }

    @Inject(method = "method_324", at = @At("TAIL"))
    private void addACTerrains(Entity entity, float f, CallbackInfo ci) {
        float f1 = MathsHelper.cos(entity.yaw * 3.141593f / 180.0f);
        float f2 = MathsHelper.sin(entity.yaw * 3.141593f / 180.0f);
        float f3 = -f2 * MathsHelper.sin(entity.pitch * 3.141593f / 180.0f);
        float f4 = f1 * MathsHelper.sin(entity.pitch * 3.141593f / 180.0f);
        float f5 = MathsHelper.cos(entity.pitch * 3.141593f / 180.0f);
        for (int i = 3; i < 5; ++i) {
            if (this.particles[i].size() == 0) continue;
            int j = 0;
            if (i == 3) {
                j = this.textureManager.getTextureId("/terrain2.png");
            }
            if (i == 4) {
                j = this.textureManager.getTextureId("/terrain3.png");
            }
            GL11.glBindTexture(3553, j);
            Tessellator tessellator = Tessellator.INSTANCE;
            tessellator.start();
            for (int k = 0; k < this.particles[i].size(); ++k) {
                Particle entityfx = (Particle) this.particles[i].get(k);
                entityfx.method_2002(tessellator, f, f1, f5, f2, f3, f4);
            }
            tessellator.draw();
        }
    }

    @ModifyConstant(method = "method_327", constant = @Constant(intValue = 3))
    private int changeByteValueToTextureAmount(int i) {
        return 5;
    }

    @ModifyConstant(method = "method_323", constant = @Constant(intValue = 4))
    private int changeLoopAmount1(int i) {
        return 6;
    }
}
