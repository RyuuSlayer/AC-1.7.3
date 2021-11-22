package io.github.ryuu.adventurecraft.scripting;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import io.github.ryuu.adventurecraft.blocks.BlockEffect;
import io.github.ryuu.adventurecraft.util.TextureAnimated;

public class ScriptEffect {

    Level worldObj;

    WorldRenderer renderGlobal;

    ScriptEffect(Level w, WorldRenderer rg) {
        this.worldObj = w;
        this.renderGlobal = rg;
    }

    public ScriptEntity spawnParticle(String particleType, double x, double y, double z, double arg1, double arg2, double arg3) {
        return ScriptEntity.getEntityClass(this.renderGlobal.spawnParticleR(particleType, x, y, z, arg1, arg2, arg3));
    }

    public boolean replaceTexture(String textureToReplace, String replacement) {
        return BlockEffect.replaceTexture(this.worldObj, textureToReplace, replacement);
    }

    public String getReplaceTexture(String textureToReplace) {
        String replacement = (String) this.worldObj.properties.replacementTextures.get((Object) textureToReplace);
        if (replacement == null) {
            return textureToReplace;
        }
        return replacement;
    }

    public void revertTextures() {
        BlockEffect.revertTextures(this.worldObj);
    }

    public String getOverlay() {
        return this.worldObj.properties.overlay;
    }

    public void setOverlay(String overlay) {
        this.worldObj.properties.overlay = overlay;
    }

    public void clearOverlay() {
        this.worldObj.properties.overlay = "";
    }

    public void setFogColor(float r, float g, float b) {
        this.worldObj.properties.fogR = r;
        this.worldObj.properties.fogG = g;
        this.worldObj.properties.fogB = b;
        this.worldObj.properties.overrideFogColor = true;
    }

    public void revertFogColor() {
        this.worldObj.properties.overrideFogColor = false;
    }

    public void setFogDensity(float start, float end) {
        this.worldObj.properties.fogStart = start;
        this.worldObj.properties.fogEnd = end;
        this.worldObj.properties.overrideFogDensity = true;
    }

    public void revertFogDensity() {
        this.worldObj.properties.overrideFogDensity = false;
    }

    public float getLightRampValue(int i) {
        return this.worldObj.properties.brightness[i];
    }

    public void setLightRampValue(int i, float f) {
        this.worldObj.properties.brightness[i] = f;
        this.worldObj.loadBrightness();
        Minecraft.minecraftInstance.worldRenderer.updateAllTheRenderers();
    }

    public void resetLightRampValues() {
        float f = 0.05f;
        for (int i = 0; i < 16; ++i) {
            float f1 = 1.0f - (float) i / 15.0f;
            this.worldObj.properties.brightness[i] = (1.0f - f1) / (f1 * 3.0f + 1.0f) * (1.0f - f) + f;
        }
        this.worldObj.loadBrightness();
        Minecraft.minecraftInstance.worldRenderer.updateAllTheRenderers();
    }

    public void registerTextureAnimation(String animName, String texName, String animatedTex, int x, int y, int width, int height) {
        Minecraft.minecraftInstance.textureManager.registerTextureAnimation(animName, new TextureAnimated(texName, animatedTex, x, y, width, height));
    }

    public void unregisterTextureAnimation(String animName) {
        Minecraft.minecraftInstance.textureManager.unregisterTextureAnimation(animName);
    }

    public void explode(ScriptEntity owner, double x, double y, double z, float strength, boolean flaming) {
        this.worldObj.createExplosion(owner.entity, x, y, z, strength, flaming);
    }

    public float getFovModifier() {
        return Minecraft.minecraftInstance.gameRenderer.field_2365;
    }

    public void setFovModifier(float f) {
        Minecraft.minecraftInstance.gameRenderer.field_2365 = f;
    }

    public void cancelCutscene() {
        Minecraft.minecraftInstance.cameraActive = false;
    }
}
