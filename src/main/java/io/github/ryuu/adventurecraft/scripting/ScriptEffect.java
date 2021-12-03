package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.blocks.BlockEffect;
import io.github.ryuu.adventurecraft.extensions.client.ExMinecraft;
import io.github.ryuu.adventurecraft.extensions.client.render.ExWorldRenderer;
import io.github.ryuu.adventurecraft.extensions.client.texture.ExTextureManager;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.extensions.level.ExLevelProperties;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import io.github.ryuu.adventurecraft.mixin.client.render.AccessGameRenderer;
import io.github.ryuu.adventurecraft.util.TextureAnimated;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.level.Level;

@SuppressWarnings("unused")
public class ScriptEffect {

    Level level;

    WorldRenderer renderGlobal;

    public ScriptEffect(Level level, WorldRenderer worldRenderer) {
        this.level = level;
        this.renderGlobal = worldRenderer;
    }

    public ScriptEntity spawnParticle(String particleType, double x, double y, double z, double arg1, double arg2, double arg3) {
        return ScriptEntity.getEntityClass(((ExWorldRenderer) this.renderGlobal).spawnParticleR(particleType, x, y, z, arg1, arg2, arg3));
    }

    public boolean replaceTexture(String textureToReplace, String replacement) {
        return BlockEffect.replaceTexture(this.level, textureToReplace, replacement);
    }

    public String getReplaceTexture(String textureToReplace) {
        ExLevelProperties exProps = (ExLevelProperties) this.level.getProperties();
        String replacement = exProps.getReplacementTextures().get(textureToReplace);
        if (replacement == null) {
            return textureToReplace;
        }
        return replacement;
    }

    public void revertTextures() {
        BlockEffect.revertTextures(this.level);
    }

    public String getOverlay() {
        return ((ExLevelProperties) this.level.getProperties()).getOverlay();
    }

    public void setOverlay(String overlay) {
        ((ExLevelProperties) this.level.getProperties()).setOverlay(overlay);
    }

    public void clearOverlay() {
        setOverlay("");
    }

    public void setFogColor(float r, float g, float b) {
        ExLevelProperties exProps = (ExLevelProperties) this.level.getProperties();
        exProps.setFogR(r);
        exProps.setFogG(g);
        exProps.setFogB(b);
        exProps.setOverrideFogColor(true);
    }

    public void revertFogColor() {
        ExLevelProperties exProps = (ExLevelProperties) this.level.getProperties();
        exProps.setOverrideFogColor(false);
    }

    public void setFogDensity(float start, float end) {
        ExLevelProperties exProps = (ExLevelProperties) this.level.getProperties();
        exProps.setFogStart(start);
        exProps.setFogEnd(end);
        exProps.setOverrideFogDensity(true);
    }

    public void revertFogDensity() {
        ExLevelProperties exProps = (ExLevelProperties) this.level.getProperties();
        exProps.setOverrideFogDensity(false);
    }

    public float getLightRampValue(int i) {
        return ((ExLevelProperties) this.level.getProperties()).getBrightness()[i];
    }

    public void setLightRampValue(int i, float f) {
        ((ExLevelProperties) this.level.getProperties()).getBrightness()[i] = f;
        ((ExLevel) this.level).loadBrightness();
        ((ExWorldRenderer) AccessMinecraft.getInstance().worldRenderer).updateAllTheRenderers();
    }

    public void resetLightRampValues() {
        float f = 0.05f;
        for (int i = 0; i < 16; ++i) {
            float f1 = 1.0f - (float) i / 15.0f;
            ((ExLevelProperties) this.level.getProperties()).getBrightness()[i] = (1.0f - f1) / (f1 * 3.0f + 1.0f) * (1.0f - f) + f;
        }
        ((ExLevel) this.level).loadBrightness();
        ((ExWorldRenderer) AccessMinecraft.getInstance().worldRenderer).updateAllTheRenderers();
    }

    public void registerTextureAnimation(String animName, String texName, String animatedTex, int x, int y, int width, int height) {
        ((ExTextureManager) AccessMinecraft.getInstance().textureManager).registerTextureAnimation(animName, new TextureAnimated(texName, animatedTex, x, y, width, height));
    }

    public void unregisterTextureAnimation(String animName) {
        ((ExTextureManager) AccessMinecraft.getInstance().textureManager).unregisterTextureAnimation(animName);
    }

    public void explode(ScriptEntity owner, double x, double y, double z, float strength, boolean flaming) {
        this.level.createExplosion(owner.entity, x, y, z, strength, flaming);
    }

    public float getFovModifier() {
        return ((AccessGameRenderer) AccessMinecraft.getInstance().gameRenderer).getField_2365();
    }

    public void setFovModifier(float f) {
        ((AccessGameRenderer) AccessMinecraft.getInstance().gameRenderer).setField_2365(f);
    }

    public void cancelCutscene() {
        ExMinecraft.getInstance().setCameraActive(false);
    }
}
