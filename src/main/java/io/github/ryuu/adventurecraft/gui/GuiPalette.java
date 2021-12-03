package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.extensions.client.ExClientInteractionManager;
import io.github.ryuu.adventurecraft.util.InventoryDebug;
import net.minecraft.client.gui.screen.container.DoubleChestScreen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.inventory.Inventory;
import org.lwjgl.opengl.GL11;

public class GuiPalette extends DoubleChestScreen {

    private final InventoryDebug palette;
    GuiSlider2 extraWidth;
    GuiSlider2 extraDepth;
    private Button a;

    public GuiPalette(Inventory iinventory, InventoryDebug p) {
        super(iinventory, p);
        this.palette = p;
    }

    @Override
    public void init() {
        super.init();
        float destroyExtraDepth = ((ExClientInteractionManager) this.minecraft.interactionManager).getDestroyExtraDepth();
        float destroyExtraWidth = ((ExClientInteractionManager) this.minecraft.interactionManager).getDestroyExtraWidth();
        this.extraDepth = new GuiSlider2(50, this.width / 2 + 2, 3, 10, String.format("Extra Depth: %d", (int) destroyExtraDepth), destroyExtraDepth / 16.0f);
        this.extraWidth = new GuiSlider2(50, this.width / 2 - 2 - this.extraDepth.getWidth(), 3, 10, String.format("Extra Width: %d", (int) destroyExtraWidth), destroyExtraWidth / 5.0f);
        this.buttons.add(this.extraDepth);
        this.buttons.add(this.extraWidth);
    }

    @Override
    protected void keyPressed(char character, int key) {
        super.keyPressed(character, key);
        if (key == 65 && this.palette.firstItem > 1) {
            this.palette.fillInventoryBackwards(this.palette.firstItem - 1);
        } else if (key == 66 && !this.palette.atEnd) {
            this.palette.fillInventory(this.palette.lastItem + 1);
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        super.render(mouseX, mouseY, delta);
        GL11.glPushMatrix();
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        for (Object button : this.buttons) {
            Button guibutton = (Button) button;
            guibutton.render(this.minecraft, mouseX, mouseY);
        }
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glPopMatrix();
        ExClientInteractionManager interactionManager = (ExClientInteractionManager) this.minecraft.interactionManager;
        interactionManager.setDestroyExtraDepth((int) Math.min(16.0f * this.extraDepth.sliderValue, 15.0f));
        interactionManager.setDestroyExtraWidth((int) Math.min(5.0f * this.extraWidth.sliderValue, 4.0f));
        this.extraWidth.text = String.format("Extra Width: %d", interactionManager.getDestroyExtraWidth());
        this.extraDepth.text = String.format("Extra Depth: %d", interactionManager.getDestroyExtraDepth());
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        if (button == 0) {
            for (Object o : this.buttons) {
                Button guibutton = (Button) o;
                if (!guibutton.isMouseOver(this.minecraft, mouseX, mouseY)) continue;
                this.a = guibutton;
                this.minecraft.soundHelper.playSound("random.click", 1.0f, 1.0f);
                this.buttonClicked(guibutton);
            }
        }
        super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int button) {
        if (this.a != null && button == 0) {
            this.a.mouseReleased(mouseX, mouseY);
            this.a = null;
        }
        super.mouseReleased(mouseX, mouseY, button);
    }
}
