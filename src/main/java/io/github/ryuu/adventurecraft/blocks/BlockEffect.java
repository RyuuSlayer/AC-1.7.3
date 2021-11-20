package io.github.ryuu.adventurecraft.blocks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.colour.FoliageColour;
import net.minecraft.client.colour.GrassColour;
import net.minecraft.client.render.FireTextureBinder;
import net.minecraft.client.render.FlowingLavaTextureBinder;
import net.minecraft.client.render.FlowingLavaTextureBinder2;
import net.minecraft.client.render.FlowingWaterTextureBinder;
import net.minecraft.client.render.FlowingWaterTextureBinder2;
import net.minecraft.client.render.PortalTextureBinder;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockEffect extends TileWithEntity {

    static boolean needsReloadForRevert = true;

    protected BlockEffect(int i, int j) {
        super(i, j, Material.AIR);
    }

    @Override
    protected MixinTileEntity createTileEntity() {
        return new TileEntityEffect();
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public Box getCollisionShape(MixinLevel level, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean shouldRender(TileView blockAccess, int i, int j, int k) {
        return DebugMode.active;
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }

    @Override
    public boolean canBeTriggered() {
        return true;
    }

    @Override
    public void onTriggerActivated(MixinLevel world, int i, int j, int k) {
        TileEntityEffect obj = (TileEntityEffect) world.getTileEntity(i, j, k);
        obj.isActivated = true;
        obj.ticksBeforeParticle = 0;
        if (obj.changeFogColor == 1) {
            world.properties.overrideFogColor = true;
            world.properties.fogR = obj.fogR;
            world.properties.fogG = obj.fogG;
            world.properties.fogB = obj.fogB;
        } else if (obj.changeFogColor == 2) {
            world.properties.overrideFogColor = false;
        }
        if (obj.changeFogDensity == 1) {
            world.properties.overrideFogDensity = true;
            world.properties.fogStart = obj.fogStart;
            world.properties.fogEnd = obj.fogEnd;
        } else if (obj.changeFogDensity == 2) {
            world.properties.overrideFogDensity = false;
        }
        if (obj.setOverlay) {
            world.properties.overlay = obj.overlay;
        }
        if (obj.replaceTextures) {
            this.replaceTextures(world, obj.textureReplacement);
        } else if (obj.revertTextures) {
            BlockEffect.revertTextures(world);
        }
    }

    public static void revertTextures(MixinLevel world) {
        Minecraft.minecraftInstance.textureManager.revertTextures();
        if (needsReloadForRevert) {
            GrassColour.loadGrass("/misc/grasscolor.png");
            FoliageColour.loadFoliage("/misc/foliagecolor.png");
            TerrainImage.loadWaterMap(new File(world.levelDir, "watermap.png"));
            TerrainImage.loadBiomeMap(new File(world.levelDir, "biomemap.png"));
            Minecraft.minecraftInstance.worldRenderer.method_1148();
            needsReloadForRevert = false;
        }
        world.properties.revertTextures();
    }

    public void replaceTextures(MixinLevel world, String replacement) {
        boolean needsReload = false;
        File replacementFile = new File(world.levelDir, "textureReplacement/" + replacement);
        if (replacementFile.exists()) {
            try {
                BufferedReader reader = new BufferedReader((Reader) new FileReader(replacementFile));
                try {
                    while (reader.ready()) {
                        String line = reader.readLine();
                        String[] parts = line.split(",", 2);
                        if (parts.length != 2)
                            continue;
                        needsReload |= BlockEffect.replaceTexture(world, parts[0], parts[1]);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
        if (needsReload) {
            Minecraft.minecraftInstance.worldRenderer.method_1148();
        }
    }

    public static boolean replaceTexture(MixinLevel world, String textureToReplace, String replacementTexture) {
        String lTextureToReplace = textureToReplace.toLowerCase();
        if (!world.properties.addReplacementTexture(textureToReplace, replacementTexture)) {
            return false;
        }
        if (lTextureToReplace.equals((Object) "/watermap.png")) {
            TerrainImage.loadWaterMap(new File(world.levelDir, replacementTexture));
            needsReloadForRevert = true;
            return true;
        }
        if (lTextureToReplace.equals((Object) "/biomemap.png")) {
            TerrainImage.loadBiomeMap(new File(world.levelDir, replacementTexture));
            needsReloadForRevert = true;
            return true;
        }
        if (lTextureToReplace.equals((Object) "/misc/grasscolor.png")) {
            GrassColour.loadGrass(replacementTexture);
            needsReloadForRevert = true;
            return true;
        }
        if (lTextureToReplace.equals((Object) "/misc/foliagecolor.png")) {
            FoliageColour.loadFoliage(replacementTexture);
            needsReloadForRevert = true;
            return true;
        }
        if (lTextureToReplace.equals((Object) "/custom_fire.png")) {
            FireTextureBinder.loadImage(replacementTexture);
            return true;
        }
        if (lTextureToReplace.equals((Object) "/custom_lava_flowing.png")) {
            FlowingLavaTextureBinder2.loadImage(replacementTexture);
            return true;
        }
        if (lTextureToReplace.equals((Object) "/custom_lava_still.png")) {
            FlowingLavaTextureBinder.loadImage(replacementTexture);
            return true;
        }
        if (lTextureToReplace.equals((Object) "/custom_portal.png")) {
            PortalTextureBinder.loadImage(replacementTexture);
            return true;
        }
        if (lTextureToReplace.equals((Object) "/custom_water_flowing.png")) {
            FlowingWaterTextureBinder.loadImage(replacementTexture);
            return true;
        }
        if (lTextureToReplace.equals((Object) "/custom_water_still.png")) {
            FlowingWaterTextureBinder2.loadImage(replacementTexture);
            return true;
        }
        Minecraft.minecraftInstance.textureManager.replaceTexture(textureToReplace, replacementTexture);
        return false;
    }

    @Override
    public void onTriggerDeactivated(MixinLevel world, int i, int j, int k) {
        TileEntityEffect obj = (TileEntityEffect) world.getTileEntity(i, j, k);
        obj.isActivated = false;
    }

    @Override
    public boolean activate(MixinLevel level, int x, int y, int z, MixinPlayer player) {
        if (DebugMode.active && player.getHeldItem() != null && player.getHeldItem().itemId == Items.cursor.id) {
            TileEntityEffect obj = (TileEntityEffect) level.getTileEntity(x, y, z);
            GuiEffect.showUI(obj);
            return true;
        }
        return false;
    }
}
