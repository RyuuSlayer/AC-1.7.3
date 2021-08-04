package io.github.ryuu.adventurecraft.gui;

import java.util.ArrayList;
import java.util.List;

import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.render.RenderHelper;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.item.ItemInstance;
import net.minecraft.tile.Tile;
import org.lwjgl.opengl.GL11;

class GuiEditPalette extends DrawableHelper {
    int numRows;

    List<Button> controlList;

    ArrayList<Tile> blocks;

    Button selectedButton;

    ItemInstance item;

    public GuiEditPalette() {
        this.scrollPosition = 0.0F;
        this.controlList = new ArrayList<Button>();
        this.item = new ItemInstance(0, 0, 0);
        this.blocks = new ArrayList<>();
        filterBlocks(0);
    }

    public void filterBlocks(int f) {
        this.blocks.clear();
        for (int i = 0; i < 255; i++) {
            if (Tile.BY_ID[i] != null)
                this.blocks.add(Tile.BY_ID[i]);
        }
        this.numRows = (this.blocks.size() + columns - 1) / columns;
    }

    public boolean mouseClicked(int x, int y, int buttonClicked, Minecraft mc, int width, int height) {
        if (buttonClicked == 0) {
            int pLeft = 0;
            int pTop = height / 2 - rows * 8;
            int columnClicked = (x - pLeft) / 16;
            int rowClicked = (y - pTop) / 16;
            if (rowClicked < rows && rowClicked >= 0)
                if (columnClicked < columns && columnClicked >= 0) {
                    mc.soundHelper.playSound("random.click", 1.0F, 1.0F);
                    int i = columnClicked + rowClicked * columns;
                    if (i + getOffset() < this.blocks.size()) {
                        DebugMode.mapEditing.setBlock(this.blocks.get(i + getOffset()).id, 0);
                        return true;
                    }
                } else if (columnClicked == columns && x % 16 < 4 && needScrollbar()) {
                    int yClicked = y - pTop - scrollHeight / 2;
                    this.scrollPosition = Math.max(Math.min(yClicked / (rows * 16.0F - scrollHeight), 1.0F), 0.0F);
                    return true;
                }
        }
        return false;
    }

    public void drawPalette(Minecraft mc, TextRenderer fontRenderer, int width, int height) {
        int pLeft = 0;
        int pWidth = 16 * columns;
        int pTop = height / 2 - rows * 8;
        int pHeight = rows * 16;
        if (needScrollbar())
            pWidth += 4;
        fill(pLeft, pTop, pLeft + pWidth, pTop + pHeight, -2147483648);
        if (needScrollbar()) {
            int yOffset = (int) (this.scrollPosition * (pHeight - scrollHeight));
            fill(pLeft + pWidth - 4, pTop + yOffset, pLeft + pWidth, pTop + yOffset + scrollHeight, -2130706433);
        }
        GL11.glPushMatrix();
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        RenderHelper.enableLighting();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(32826);
        int offset = getOffset();
        for (int i = 0; i < rows * columns; i++) {
            if (i + offset >= this.blocks.size())
                break;
            this.item.itemId = this.blocks.get(i + offset).id;
            itemRenderer.renderItemInstance(fontRenderer, mc.textureManager, this.item, i % columns * 16, height / 2 - rows * 8 + 16 * i / columns);
        }
        GL11.glDisable(32826);
        RenderHelper.disableLighting();
    }

    private int getOffset() {
        return columns * (int) ((this.scrollPosition * (this.numRows - rows)) + 0.5D);
    }

    private boolean needScrollbar() {
        return (this.numRows > rows);
    }

    private static final ItemRenderer itemRenderer = new ItemRenderer();

    static int rows = 8;

    static int columns = 4;

    static int scrollHeight = 8;

    float scrollPosition;
}
