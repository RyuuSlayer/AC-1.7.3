package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.EntityNPC;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityNpcPath;
import io.github.ryuu.adventurecraft.items.ItemCursor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.gui.widgets.OptionButton;

public class GuiNpcPath extends Screen {
    private final TileEntityNpcPath path;

    public GuiNpcPath(TileEntityNpcPath npcPath) {
        this.path = npcPath;
    }

    @Override
    public void tick() {
    }

    @Override
    public void init() {
        String entityName = "<Unselected>";
        EntityNPC e = TileEntityNpcPath.lastEntity;
        if (e != null)
            entityName = e.npcName;
        this.buttons.add(new OptionButton(0, 4, 20, String.format("Set Path NPC: %s", entityName)));
        this.buttons.add(new OptionButton(1, 4, 80, "Use Current Selection"));
        this.buttons.add(new OptionButton(2, 4, 100, "Reset Target"));
    }

    @Override
    protected void buttonClicked(Button guibutton) {
        if (guibutton.id == 0) {
            this.path.setEntityToLastSelected();
        } else if (guibutton.id == 1) {
            this.path.minX = ItemCursor.minX;
            this.path.minY = ItemCursor.minY;
            this.path.minZ = ItemCursor.minZ;
            this.path.maxX = ItemCursor.maxX;
            this.path.maxY = ItemCursor.maxY;
            this.path.maxZ = ItemCursor.maxZ;
        } else if (guibutton.id == 2) {
            this.path.minX = 0;
            this.path.minY = 0;
            this.path.minZ = 0;
            this.path.maxX = 0;
            this.path.maxY = 0;
            this.path.maxZ = 0;
        }
        this.minecraft.level.getChunk(this.path.x, this.path.y).method_885(); // TODO: this should most likely use path.z for the second arg
    }

    @Override
    public void render(int i, int j, float f) {
        fill(0, 0, this.width, this.height, -2147483648);
        String entityName = "<Not Set>";
        EntityNPC e = this.path.getNPC();
        if (e != null)
            entityName = e.npcName;
        drawTextWithShadow(this.textManager, String.format("NPC: %s", entityName), 4, 4, 14737632);
        drawTextWithShadow(this.textManager, String.format("Min: (%d, %d, %d)", this.path.minX, this.path.minY, this.path.minZ), 4, 44, 14737632);
        drawTextWithShadow(this.textManager, String.format("Max: (%d, %d, %d)", this.path.maxX, this.path.maxY, this.path.maxZ), 4, 64, 14737632);
        super.render(i, j, f);
    }

    public static void showUI(TileEntityNpcPath npcPath) {
        Minecraft.minecraftInstance.a(new GuiNpcPath(npcPath));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}