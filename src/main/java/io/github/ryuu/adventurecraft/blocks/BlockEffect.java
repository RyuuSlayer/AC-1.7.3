package io.github.ryuu.adventurecraft.blocks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityEffect;
import io.github.ryuu.adventurecraft.gui.GuiEffect;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.overrides.FlowingLavaTextureBinder2;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.TerrainImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.colour.FoliageColour;
import net.minecraft.client.colour.GrassColour;
import net.minecraft.client.render.*;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockEffect extends TileWithEntity {
    protected BlockEffect(int i, int j) {
        super(i, j, Material.AIR);
    }

    @Override
    protected TileEntity createTileEntity() {
        return new TileEntityEffect();
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public Box getCollisionShape(Level world, int i, int j, int k) {
        return null;
    }

    public boolean shouldRender(TileView blockAccess, int i, int j, int k) {
        return DebugMode.active;
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }

    public boolean canBeTriggered() {
        return true;
    }

    public void onTriggerActivated(Level world, int i, int j, int k) {
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
        if (obj.setOverlay)
            world.properties.overlay = obj.overlay;
        if (obj.replaceTextures) {
            replaceTextures(world, obj.textureReplacement);
        } else if (obj.revertTextures) {
            revertTextures(world);
        }
    }

    public static void revertTextures(Level world) {
        Minecraft.minecraftInstance.p.revertTextures();
        if (needsReloadForRevert) {
            GrassColour.loadGrass("/misc/grasscolor.png");
            FoliageColour.loadFoliage("/misc/foliagecolor.png");
            TerrainImage.loadWaterMap(new File(world.levelDir, "watermap.png"));
            TerrainImage.loadBiomeMap(new File(world.levelDir, "biomemap.png"));
            Minecraft.minecraftInstance.g.e();
            needsReloadForRevert = false;
        }
        world.properties.revertTextures();
    }

    public void replaceTextures(Level world, String replacement) {
        boolean needsReload = false;
        File replacementFile = new File(world.levelDir, "textureReplacement/" + replacement);
        if (replacementFile.exists())
            try {
                BufferedReader reader = new BufferedReader(new FileReader(replacementFile));
                try {
                    while (reader.ready()) {
                        String line = reader.readLine();
                        String[] parts = line.split(",", 2);
                        if (parts.length == 2)
                            needsReload |= replaceTexture(world, parts[0], parts[1]);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        if (needsReload)
            Minecraft.minecraftInstance.g.e();
    }

    public static boolean replaceTexture(Level world, String textureToReplace, String replacementTexture) {
        String lTextureToReplace = textureToReplace.toLowerCase();
        if (!world.properties.addReplacementTexture(textureToReplace, replacementTexture))
            return false;
        if (lTextureToReplace.equals("/watermap.png")) {
            TerrainImage.loadWaterMap(new File(world.levelDir, replacementTexture));
            needsReloadForRevert = true;
            return true;
        }
        if (lTextureToReplace.equals("/biomemap.png")) {
            TerrainImage.loadBiomeMap(new File(world.levelDir, replacementTexture));
            needsReloadForRevert = true;
            return true;
        }
        if (lTextureToReplace.equals("/misc/grasscolor.png")) {
            GrassColour.loadGrass(replacementTexture);
            needsReloadForRevert = true;
            return true;
        }
        if (lTextureToReplace.equals("/misc/foliagecolor.png")) {
            FoliageColour.loadFoliage(replacementTexture);
            needsReloadForRevert = true;
            return true;
        }
        if (lTextureToReplace.equals("/custom_fire.png")) {
            FireTextureBinder.loadImage(replacementTexture);
            return true;
        }
        if (lTextureToReplace.equals("/custom_lava_flowing.png")) {
            FlowingLavaTextureBinder2.loadImage(replacementTexture);
            return true;
        }
        if (lTextureToReplace.equals("/custom_lava_still.png")) {
            FlowingLavaTextureBinder.loadImage(replacementTexture);
            return true;
        }
        if (lTextureToReplace.equals("/custom_portal.png")) {
            PortalTextureBinder.loadImage(replacementTexture);
            return true;
        }
        if (lTextureToReplace.equals("/custom_water_flowing.png")) {
            FlowingWaterTextureBinder.loadImage(replacementTexture);
            return true;
        }
        if (lTextureToReplace.equals("/custom_water_still.png")) {
            FlowingWaterTextureBinder2.loadImage(replacementTexture);
            return true;
        }
        Minecraft.minecraftInstance.p.replaceTexture(textureToReplace, replacementTexture);
        return false;
    }

    public void onTriggerDeactivated(Level world, int i, int j, int k) {
        TileEntityEffect obj = (TileEntityEffect) world.getTileEntity(i, j, k);
        obj.isActivated = false;
    }

    @Override
    public boolean activate(Level world, int i, int j, int k, Player entityplayer) {
        if (DebugMode.active && entityplayer.getHeldItem() != null && (entityplayer.getHeldItem()).itemId == Items.cursor.id) {
            TileEntityEffect obj = (TileEntityEffect) world.getTileEntity(i, j, k);
            GuiEffect.showUI(obj);
            return true;
        }
        return false;
    }

    static boolean needsReloadForRevert = true;
}