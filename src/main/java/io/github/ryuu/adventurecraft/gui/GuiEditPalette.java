package io.github.ryuu.adventurecraft.gui;

import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.render.RenderHelper;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.item.ItemInstance;
import net.minecraft.src.Block;
import net.minecraft.src.GuiButton;
import net.minecraft.tile.Tile;
import org.lwjgl.opengl.GL11;

class GuiEditPalette extends DrawableHelper {

    int numRows;

    List<GuiButton> controlList = new ArrayList();

    ArrayList<Block> blocks;

    Button selectedButton;

    ItemInstance item = new ItemInstance(0, 0, 0);

    private static ItemRenderer itemRenderer = new ItemRenderer();

    static int rows = 8;

    static int columns = 4;

    static int scrollHeight = 8;

    float scrollPosition = 0.0f;

    GuiEditPalette() {
        this.blocks = new ArrayList();
        this.filterBlocks(0);
    }

    void filterBlocks(int f) {
        this.blocks.clear();
        for (int i = 0; i < 255; ++i) {
            if (Tile.BY_ID[i] == null)
                continue;
            this.blocks.add((Object) Tile.BY_ID[i]);
        }
        this.numRows = (this.blocks.size() + columns - 1) / columns;
    }

    boolean mouseClicked(int x, int y, int buttonClicked, Minecraft mc, int width, int height) {
        if (buttonClicked == 0) {
            int pLeft = 0;
            int pTop = height / 2 - rows * 8;
            int columnClicked = (x - pLeft) / 16;
            int rowClicked = (y - pTop) / 16;
            if (rowClicked < rows && rowClicked >= 0) {
                if (columnClicked < columns && columnClicked >= 0) {
                    mc.soundHelper.playSound("random.click", 1.0f, 1.0f);
                    int i = columnClicked + rowClicked * columns;
                    if (i + this.getOffset() < this.blocks.size()) {
                        DebugMode.mapEditing.setBlock(((Tile) this.blocks.get((int) (i + this.getOffset()))).id, 0);
                        return true;
                    }
                } else if (columnClicked == columns && x % 16 < 4 && this.needScrollbar()) {
                    int yClicked = y - pTop - scrollHeight / 2;
                    this.scrollPosition = Math.max((float) Math.min((float) ((float) yClicked / ((float) rows * 16.0f - (float) scrollHeight)), (float) 1.0f), (float) 0.0f);
                    return true;
                }
            }
        }
        return false;
    }

    void drawPalette(Minecraft mc, TextRenderer fontRenderer, int width, int height) {
        int pLeft = 0;
        int pWidth = 16 * columns;
        int pTop = height / 2 - rows * 8;
        int pHeight = rows * 16;
        if (this.needScrollbar()) {
            pWidth += 4;
        }
        this.fill(pLeft, pTop, pLeft + pWidth, pTop + pHeight, Integer.MIN_VALUE);
        if (this.needScrollbar()) {
            int yOffset = (int) (this.scrollPosition * (float) (pHeight - scrollHeight));
            this.fill(pLeft + pWidth - 4, pTop + yOffset, pLeft + pWidth, pTop + yOffset + scrollHeight, -2130706433);
        }
        GL11.glPushMatrix();
        GL11.glRotatef((float) 180.0f, (float) 1.0f, (float) 0.0f, (float) 0.0f);
        RenderHelper.enableLighting();
        GL11.glPopMatrix();
        GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
        GL11.glEnable((int) 32826);
        int offset = this.getOffset();
        for (int i = 0; i < rows * columns && i + offset < this.blocks.size(); ++i) {
            this.item.itemId = ((Tile) this.blocks.get((int) (i + offset))).id;
            itemRenderer.renderItemInstance(fontRenderer, mc.textureManager, this.item, i % columns * 16, height / 2 - rows * 8 + 16 * (i / columns));
        }
        GL11.glDisable((int) 32826);
        RenderHelper.disableLighting();
    }

    private int getOffset() {
        return columns * (int) ((double) (this.scrollPosition * (float) (this.numRows - rows)) + 0.5);
    }

    private boolean needScrollbar() {
        return this.numRows > rows;
    }
}
