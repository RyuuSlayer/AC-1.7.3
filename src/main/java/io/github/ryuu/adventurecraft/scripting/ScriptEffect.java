package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.blocks.BlockEffect;
import io.github.ryuu.adventurecraft.extensions.client.ExMinecraft;
import io.github.ryuu.adventurecraft.extensions.client.render.ExWorldRenderer;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.extensions.level.ExLevelProperties;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import io.github.ryuu.adventurecraft.util.TextureAnimated;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.level.Level;

public class ScriptEffect {

    Level world;

    WorldRenderer renderGlobal;

    ScriptEffect(Level w, WorldRenderer rg) {
        this.world = w;
        this.renderGlobal = rg;
    }

    public ScriptEntity spawnParticle(String particleType, double x, double y, double z, double arg1, double arg2, double arg3) {
        return ScriptEntity.getEntityClass(((ExWorldRenderer)this.renderGlobal).spawnParticleR(particleType, x, y, z, arg1, arg2, arg3));
    }

    public boolean replaceTexture(String textureToReplace, String replacement) {
        return BlockEffect.replaceTexture(this.world, textureToReplace, replacement);
    }

    public String getReplaceTexture(String textureToReplace) {
        String replacement = (String) this.world.getProperties().replacementTextures.get((Object) textureToReplace);
        if (replacement == null) {
            return textureToReplace;
        }
        return replacement;
    }

    public void revertTextures() {
        BlockEffect.revertTextures(this.world);
    }

    public String getOverlay() {
        return ((ExLevelProperties)this.world.getProperties()).getOverlay();
    }

    public void setOverlay(String overlay) {
        ((ExLevelProperties)this.world.getProperties()).setOverlay(overlay);
    }

    public void clearOverlay() {
        setOverlay("");
    }

    public void setFogColor(float r, float g, float b) {
        this.world.getProperties().fogR = r;
        this.world.getProperties().fogG = g;
        this.world.getProperties().fogB = b;
        this.world.getProperties().overrideFogColor = true;
    }

    public void revertFogColor() {
        this.world.getProperties().overrideFogColor = false;
    }

    public void setFogDensity(float start, float end) {
        this.world.getProperties().fogStart = start;
        this.world.getProperties().fogEnd = end;
        this.world.getProperties().overrideFogDensity = true;
    }

    public void revertFogDensity() {
        this.world.getProperties().overrideFogDensity = false;
    }

    public float getLightRampValue(int i) {
        return ((ExLevelProperties)this.world.getProperties()).getBrightness()[i];
    }

    public void setLightRampValue(int i, float f) {
        ((ExLevelProperties)this.world.getProperties()).getBrightness()[i] = f;
        ((ExLevel)this.world).loadBrightness();
        ((ExWorldRenderer)AccessMinecraft.getInstance().worldRenderer).updateAllTheRenderers();
    }

    public void resetLightRampValues() {
        float f = 0.05f;
        for (int i = 0; i < 16; ++i) {
            float f1 = 1.0f - (float) i / 15.0f;
            ((ExLevelProperties)this.world.getProperties()).getBrightness()[i] = (1.0f - f1) / (f1 * 3.0f + 1.0f) * (1.0f - f) + f;
        }
        ((ExLevel)this.world).loadBrightness();
        ((ExWorldRenderer)AccessMinecraft.getInstance().worldRenderer).updateAllTheRenderers();
    }

    public void registerTextureAnimation(String animName, String texName, String animatedTex, int x, int y, int width, int height) {
        AccessMinecraft.getInstance().textureManager.registerTextureAnimation(animName, new TextureAnimated(texName, animatedTex, x, y, width, height));
    }

    public void unregisterTextureAnimation(String animName) {
        AccessMinecraft.getInstance().textureManager.unregisterTextureAnimation(animName);
    }

    public void explode(ScriptEntity owner, double x, double y, double z, float strength, boolean flaming) {
        this.world.createExplosion(owner.entity, x, y, z, strength, flaming);
    }

    public float getFovModifier() {
        return AccessMinecraft.getInstance().gameRenderer.field_2365;
    }

    public void setFovModifier(float f) {
        AccessMinecraft.getInstance().gameRenderer.field_2365 = f;
    }

    public void cancelCutscene() {
        ExMinecraft.getInstance().setCameraActive(false);
    }
}
