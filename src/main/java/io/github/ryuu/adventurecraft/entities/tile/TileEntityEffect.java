package io.github.ryuu.adventurecraft.entities.tile;

import net.minecraft.tile.entity.TileEntity;

import java.util.Random;

public class TileEntityEffect extends TileEntity {
    public void a(nu nbttagcompound) {
        super.a(nbttagcompound);
        this.particleType = nbttagcompound.i("particleType");
        this.particlesPerSpawn = nbttagcompound.e("particlesPerSpawn");
        this.ticksBetweenParticles = nbttagcompound.e("ticksBetweenParticles");
        this.isActivated = nbttagcompound.m("isActivated");
        this.offsetX = nbttagcompound.g("offsetX");
        this.offsetY = nbttagcompound.g("offsetY");
        this.offsetZ = nbttagcompound.g("offsetZ");
        this.randX = nbttagcompound.g("randX");
        this.randY = nbttagcompound.g("randY");
        this.randZ = nbttagcompound.g("randZ");
        this.floatArg1 = nbttagcompound.g("floatArg1");
        this.floatArg2 = nbttagcompound.g("floatArg2");
        this.floatArg3 = nbttagcompound.g("floatArg3");
        this.floatRand1 = nbttagcompound.g("floatRand1");
        this.floatRand2 = nbttagcompound.g("floatRand2");
        this.floatRand3 = nbttagcompound.g("floatRand3");
        this.changeFogColor = nbttagcompound.e("changeFogColor");
        this.fogR = nbttagcompound.g("fogR");
        this.fogG = nbttagcompound.g("fogG");
        this.fogB = nbttagcompound.g("fogB");
        this.changeFogDensity = nbttagcompound.e("changeFogDensity");
        this.fogStart = nbttagcompound.g("fogStart");
        this.fogEnd = nbttagcompound.g("fogEnd");
        this.setOverlay = nbttagcompound.m("setOverlay");
        this.overlay = nbttagcompound.i("overlay");
        this.revertTextures = nbttagcompound.m("revertTextures");
        this.replaceTextures = nbttagcompound.m("replaceTextures");
        this.textureReplacement = nbttagcompound.i("textureReplacement");
    }

    public void b(nu nbttagcompound) {
        super.b(nbttagcompound);
        if (!this.particleType.equals(""))
            nbttagcompound.a("particleType", this.particleType);
        nbttagcompound.a("particlesPerSpawn", this.particlesPerSpawn);
        nbttagcompound.a("ticksBetweenParticles", this.ticksBetweenParticles);
        nbttagcompound.a("isActivated", this.isActivated);
        nbttagcompound.a("offsetX", this.offsetX);
        nbttagcompound.a("offsetY", this.offsetY);
        nbttagcompound.a("offsetZ", this.offsetZ);
        nbttagcompound.a("randX", this.randX);
        nbttagcompound.a("randY", this.randY);
        nbttagcompound.a("randZ", this.randZ);
        nbttagcompound.a("floatArg1", this.floatArg1);
        nbttagcompound.a("floatArg2", this.floatArg2);
        nbttagcompound.a("floatArg3", this.floatArg3);
        nbttagcompound.a("floatRand1", this.floatRand1);
        nbttagcompound.a("floatRand2", this.floatRand2);
        nbttagcompound.a("floatRand3", this.floatRand3);
        nbttagcompound.a("changeFogColor", this.changeFogColor);
        nbttagcompound.a("fogR", this.fogR);
        nbttagcompound.a("fogG", this.fogG);
        nbttagcompound.a("fogB", this.fogB);
        nbttagcompound.a("changeFogDensity", this.changeFogDensity);
        nbttagcompound.a("fogStart", this.fogStart);
        nbttagcompound.a("fogEnd", this.fogEnd);
        nbttagcompound.a("setOverlay", this.setOverlay);
        nbttagcompound.a("overlay", this.overlay);
        nbttagcompound.a("revertTextures", this.revertTextures);
        nbttagcompound.a("replaceTextures", this.replaceTextures);
        nbttagcompound.a("textureReplacement", this.textureReplacement);
    }

    public void n_() {
        if (this.checkTrigger) {
            this.isActivated = this.d.triggerManager.isActivated(this.e, this.f, this.g);
            this.checkTrigger = false;
        }
        if (this.isActivated)
            if (this.ticksBeforeParticle > 0) {
                this.ticksBeforeParticle--;
            } else {
                for (int i = 0; i < this.particlesPerSpawn; i++) {
                    this.d.a(this.particleType, this.e + 0.5D + this.randX * (2.0D * rand.nextDouble() - 1.0D) + this.offsetX, this.f + 0.5D + this.randY * (2.0D * rand.nextDouble() - 1.0D) + this.offsetY, this.g + 0.5D + this.randZ * (2.0D * rand.nextDouble() - 1.0D) + this.offsetZ, this.floatArg1 + this.floatRand1 * (2.0D * rand.nextDouble() - 1.0D), this.floatArg2 + this.floatRand2 * (2.0D * rand.nextDouble() - 1.0D), this.floatArg3 + this.floatRand3 * (2.0D * rand.nextDouble() - 1.0D));
                    this.ticksBeforeParticle = this.ticksBetweenParticles;
                }
            }
    }

    public boolean checkTrigger = true;

    public String particleType = "heart";

    public int particlesPerSpawn = 1;

    public int ticksBetweenParticles = 1;

    public boolean isActivated = false;

    public int ticksBeforeParticle = 0;

    public float offsetX = 0.0F;

    public float offsetY = 0.0F;

    public float offsetZ = 0.0F;

    public float randX = 0.0F;

    public float randY = 0.0F;

    public float randZ = 0.0F;

    public float floatArg1 = 0.0F;

    public float floatArg2 = 0.0F;

    public float floatArg3 = 0.0F;

    public float floatRand1 = 0.0F;

    public float floatRand2 = 0.0F;

    public float floatRand3 = 0.0F;

    public int changeFogColor;

    public float fogR;

    public float fogG;

    public float fogB;

    public int changeFogDensity;

    public float fogStart;

    public float fogEnd;

    public boolean setOverlay = false;

    public String overlay = "";

    public boolean revertTextures = false;

    public boolean replaceTextures = false;

    public String textureReplacement = "";

    private static Random rand = new Random();
}
