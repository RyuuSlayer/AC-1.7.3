package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.blocks.BlockEffect;
import io.github.ryuu.adventurecraft.util.TextureAnimated;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.level.Level;

public class ScriptEffect {
    Level worldObj;

    WorldRenderer renderGlobal;

    ScriptEffect(Level w, WorldRenderer rg) {
        this.worldObj = w;
        this.renderGlobal = rg;
    }

    public ScriptEntity spawnParticle(String particleType, double x, double y, double z, double arg1, double arg2, double arg3) {
        return ScriptEntity.getEntityClass((Entity) this.renderGlobal.spawnParticleR(particleType, x, y, z, arg1, arg2, arg3));
    }

    public boolean replaceTexture(String textureToReplace, String replacement) {
        return BlockEffect.replaceTexture(this.worldObj, textureToReplace, replacement);
    }

    public String getReplaceTexture(String textureToReplace) {
        String replacement = (String)this.worldObj.x.replacementTextures.get(textureToReplace);
        if (replacement == null)
            return textureToReplace;
        return replacement;
    }

    public void revertTextures() {
        BlockEffect.revertTextures(this.worldObj);
    }

    public String getOverlay() {
        return this.worldObj.x.overlay;
    }

    public void setOverlay(String overlay) {
        this.worldObj.x.overlay = overlay;
    }

    public void clearOverlay() {
        this.worldObj.x.overlay = "";
    }

    public void setFogColor(float r, float g, float b) {
        this.worldObj.x.fogR = r;
        this.worldObj.x.fogG = g;
        this.worldObj.x.fogB = b;
        this.worldObj.x.overrideFogColor = true;
    }

    public void revertFogColor() {
        this.worldObj.x.overrideFogColor = false;
    }

    public void setFogDensity(float start, float end) {
        this.worldObj.x.fogStart = start;
        this.worldObj.x.fogEnd = end;
        this.worldObj.x.overrideFogDensity = true;
    }

    public void revertFogDensity() {
        this.worldObj.x.overrideFogDensity = false;
    }

    public float getLightRampValue(int i) {
        return this.worldObj.x.brightness[i];
    }

    public void setLightRampValue(int i, float f) {
        this.worldObj.x.brightness[i] = f;
        this.worldObj.loadBrightness();
        Minecraft.minecraftInstance.g.updateAllTheRenderers();
    }

    public void resetLightRampValues() {
        float f = 0.05F;
        for (int i = 0; i < 16; i++) {
            float f1 = 1.0F - i / 15.0F;
            this.worldObj.x.brightness[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
        }
        this.worldObj.loadBrightness();
        Minecraft.minecraftInstance.g.updateAllTheRenderers();
    }

    public void registerTextureAnimation(String animName, String texName, String animatedTex, int x, int y, int width, int height) {
        Minecraft.minecraftInstance.p.registerTextureAnimation(animName, new TextureAnimated(texName, animatedTex, x, y, width, height));
    }

    public void unregisterTextureAnimation(String animName) {
        Minecraft.minecraftInstance.p.unregisterTextureAnimation(animName);
    }

    public void explode(ScriptEntity owner, double x, double y, double z, float strength, boolean flaming) {
        this.worldObj.a(owner.entity, x, y, z, strength, flaming);
    }

    public float getFovModifier() {
        return Minecraft.minecraftInstance.t.z;
    }

    public void setFovModifier(float f) {
        Minecraft.minecraftInstance.t.z = f;
    }

    public void cancelCutscene() {
        Minecraft.minecraftInstance.cameraActive = false;
    }
}
