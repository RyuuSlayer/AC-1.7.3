package io.github.ryuu.adventurecraft.gui;

import java.util.ArrayList;
import java.util.List;

import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.GuiButton;
import org.lwjgl.opengl.GL11;

class GuiEditPalette extends ub {
    int numRows;

    List<GuiButton> controlList;

    ArrayList<Block> blocks;

    ke selectedButton;

    iz item;

    GuiEditPalette() {
        this.scrollPosition = 0.0F;
        this.controlList = new ArrayList<GuiButton>();
        this.item = new iz(0, 0, 0);
        this.blocks = new ArrayList<Block>();
        filterBlocks(0);
    }

    void filterBlocks(int f) {
        this.blocks.clear();
        for (int i = 0; i < 255; i++) {
            if (uu.m[i] != null)
                this.blocks.add(uu.m[i]);
        }
        this.numRows = (this.blocks.size() + columns - 1) / columns;
    }

    boolean mouseClicked(int x, int y, int buttonClicked, Minecraft mc, int width, int height) {
        if (buttonClicked == 0) {
            int pLeft = 0;
            int pTop = height / 2 - rows * 8;
            int columnClicked = (x - pLeft) / 16;
            int rowClicked = (y - pTop) / 16;
            if (rowClicked < rows && rowClicked >= 0)
                if (columnClicked < columns && columnClicked >= 0) {
                    mc.B.a("random.click", 1.0F, 1.0F);
                    int i = columnClicked + rowClicked * columns;
                    if (i + getOffset() < this.blocks.size()) {
                        DebugMode.mapEditing.setBlock(((uu)this.blocks.get(i + getOffset())).bn, 0);
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

    void drawPalette(Minecraft mc, sj fontRenderer, int width, int height) {
        int pLeft = 0;
        int pWidth = 16 * columns;
        int pTop = height / 2 - rows * 8;
        int pHeight = rows * 16;
        if (needScrollbar())
            pWidth += 4;
        a(pLeft, pTop, pLeft + pWidth, pTop + pHeight, -2147483648);
        if (needScrollbar()) {
            int yOffset = (int)(this.scrollPosition * (pHeight - scrollHeight));
            a(pLeft + pWidth - 4, pTop + yOffset, pLeft + pWidth, pTop + yOffset + scrollHeight, -2130706433);
        }
        GL11.glPushMatrix();
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        u.b();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(32826);
        int offset = getOffset();
        for (int i = 0; i < rows * columns; i++) {
            if (i + offset >= this.blocks.size())
                break;
            this.item.c = ((uu)this.blocks.get(i + offset)).bn;
            itemRenderer.a(fontRenderer, mc.p, this.item, i % columns * 16, height / 2 - rows * 8 + 16 * i / columns);
        }
        GL11.glDisable(32826);
        u.a();
    }

    private int getOffset() {
        return columns * (int)((this.scrollPosition * (this.numRows - rows)) + 0.5D);
    }

    private boolean needScrollbar() {
        return (this.numRows > rows);
    }

    private static bb itemRenderer = new bb();

    static int rows = 8;

    static int columns = 4;

    static int scrollHeight = 8;

    float scrollPosition;
}
