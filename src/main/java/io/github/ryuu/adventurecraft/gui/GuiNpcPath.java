package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.EntityNPC;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityNpcPath;
import io.github.ryuu.adventurecraft.items.ItemCursor;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.gui.widgets.OptionButton;

import java.util.List;

public class GuiNpcPath extends Screen {

    private final TileEntityNpcPath path;

    public GuiNpcPath(TileEntityNpcPath npcPath) {
        this.path = npcPath;
    }

    public static void showUI(TileEntityNpcPath npcPath) {
        AccessMinecraft.getInstance().openScreen(new GuiNpcPath(npcPath));
    }

    @Override
    public void tick() {
    }

    @Override
    public void init() {
        List<Button> buttons = (List<Button>) this.buttons;
        String entityName = "<Unselected>";
        EntityNPC e = TileEntityNpcPath.lastEntity;
        if (e != null) {
            entityName = e.npcName;
        }
        buttons.add(new OptionButton(0, 4, 20, String.format("Set Path NPC: %s", entityName)));
        buttons.add(new OptionButton(1, 4, 80, "Use Current Selection"));
        buttons.add(new OptionButton(2, 4, 100, "Reset Target"));
    }

    @Override
    protected void buttonClicked(Button button) {
        if (button.id == 0) {
            this.path.setEntityToLastSelected();
        } else if (button.id == 1) {
            this.path.minX = ItemCursor.minX;
            this.path.minY = ItemCursor.minY;
            this.path.minZ = ItemCursor.minZ;
            this.path.maxX = ItemCursor.maxX;
            this.path.maxY = ItemCursor.maxY;
            this.path.maxZ = ItemCursor.maxZ;
        } else if (button.id == 2) {
            this.path.minX = 0;
            this.path.minY = 0;
            this.path.minZ = 0;
            this.path.maxX = 0;
            this.path.maxY = 0;
            this.path.maxZ = 0;
        }
        this.minecraft.level.getChunk(this.path.x, this.path.y).markDirty();
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.fill(0, 0, this.width, this.height, Integer.MIN_VALUE);
        String entityName = "<Not Set>";
        EntityNPC e = this.path.getNPC();
        if (e != null) {
            entityName = e.npcName;
        }
        this.drawTextWithShadow(this.textManager, String.format("NPC: %s", entityName), 4, 4, 0xE0E0E0);
        this.drawTextWithShadow(this.textManager, String.format("Min: (%d, %d, %d)", this.path.minX, this.path.minY, this.path.minZ), 4, 44, 0xE0E0E0);
        this.drawTextWithShadow(this.textManager, String.format("Max: (%d, %d, %d)", this.path.maxX, this.path.maxY, this.path.maxZ), 4, 64, 0xE0E0E0);
        super.render(mouseX, mouseY, delta);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
