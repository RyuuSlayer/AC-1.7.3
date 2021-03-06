package io.github.ryuu.adventurecraft.gui;

import net.minecraft.client.gui.Screen;
import net.minecraft.client.render.RenderHelper;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import org.lwjgl.opengl.GL11;

public class GuiStore extends Screen {

    private static final ItemRenderer itemRenderer = new ItemRenderer();
    ItemInstance buyItem = new ItemInstance(0, 0, 0);
    ItemInstance sellItem = new ItemInstance(0, 0, 0);
    int supplyLeft;

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        TranslationStorage t = TranslationStorage.getInstance();
        int yOffset = 64;
        if (this.supplyLeft < 0) {
            this.fill(this.width / 2 - 38, this.height / 2 - 10 - 12 - yOffset, this.width / 2 + 38, this.height / 2 + 10 - yOffset, Integer.MIN_VALUE);
        } else {
            this.fill(this.width / 2 - 38, this.height / 2 - 10 - 12 - yOffset, this.width / 2 + 38, this.height / 2 + 10 + 12 - yOffset, Integer.MIN_VALUE);
        }
        if (this.buyItem.itemId != 0 && this.sellItem.itemId != 0) {
            this.drawTextWithShadowCentred(this.textManager, t.translate("store.trade"), this.width / 2, this.height / 2 - 19 - yOffset, 0xFFFFFF);
            this.drawTextWithShadowCentred(this.textManager, t.translate("store.for"), this.width / 2, this.height / 2 - 4 - yOffset, 0xFFFFFF);
        } else if (this.buyItem.itemId != 0) {
            this.drawTextWithShadowCentred(this.textManager, t.translate("store.receive"), this.width / 2, this.height / 2 - 19 - yOffset, 0xFFFFFF);
        } else if (this.sellItem.itemId != 0) {
            this.drawTextWithShadowCentred(this.textManager, t.translate("store.insert"), this.width / 2, this.height / 2 - 19 - yOffset, 0xFFFFFF);
        }
        if (this.supplyLeft > 0) {
            this.drawTextWithShadowCentred(this.textManager, String.format("%s: %d", t.translate("store.tradesLeft"), this.supplyLeft), this.width / 2, this.height / 2 + 11 - yOffset, 0xFFFFFF);
        }
        if (this.buyItem.itemId != 0 && this.sellItem.itemId != 0) {
            this.fill(this.width / 2 + 11, this.height / 2 - 9 - yOffset, this.width / 2 + 29, this.height / 2 + 9 - yOffset, -1433695349);
            this.fill(this.width / 2 - 29, this.height / 2 - 9 - yOffset, this.width / 2 - 11, this.height / 2 + 9 - yOffset, -1433695349);
            GL11.glPushMatrix();
            GL11.glRotatef(120.0f, 1.0f, 0.0f, 0.0f);
            RenderHelper.enableLighting();
            GL11.glPopMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glEnable(32826);
            if (ItemType.byId[this.buyItem.itemId] != null) {
                itemRenderer.renderItemInstance(this.textManager, this.minecraft.textureManager, this.buyItem, this.width / 2 + 12, this.height / 2 - 8 - yOffset);
                itemRenderer.method_1488(this.textManager, this.minecraft.textureManager, this.buyItem, this.width / 2 + 12, this.height / 2 - 8 - yOffset);
            }
            if (ItemType.byId[this.sellItem.itemId] != null) {
                itemRenderer.renderItemInstance(this.textManager, this.minecraft.textureManager, this.sellItem, this.width / 2 - 28, this.height / 2 - 8 - yOffset);
                itemRenderer.method_1488(this.textManager, this.minecraft.textureManager, this.sellItem, this.width / 2 - 28, this.height / 2 - 8 - yOffset);
            }
            GL11.glDisable(32826);
            RenderHelper.disableLighting();
            GL11.glDisable(2896);
            GL11.glDisable(2929);
        } else if (this.buyItem.itemId != 0) {
            this.fill(this.width / 2 - 9, this.height / 2 - 9 - yOffset, this.width / 2 + 9, this.height / 2 + 9 - yOffset, -1433695349);
            if (ItemType.byId[this.buyItem.itemId] != null) {
                GL11.glPushMatrix();
                GL11.glRotatef(120.0f, 1.0f, 0.0f, 0.0f);
                RenderHelper.enableLighting();
                GL11.glPopMatrix();
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glEnable(32826);
                itemRenderer.renderItemInstance(this.textManager, this.minecraft.textureManager, this.buyItem, this.width / 2 - 8, this.height / 2 - 8 - yOffset);
                itemRenderer.method_1488(this.textManager, this.minecraft.textureManager, this.buyItem, this.width / 2 - 8, this.height / 2 - 8 - yOffset);
                GL11.glDisable(32826);
                RenderHelper.disableLighting();
                GL11.glDisable(2896);
                GL11.glDisable(2929);
            }
        } else if (this.sellItem.itemId != 0) {
            this.fill(this.width / 2 - 9, this.height / 2 - 9 - yOffset, this.width / 2 + 9, this.height / 2 + 9 - yOffset, -1433695349);
            if (ItemType.byId[this.sellItem.itemId] != null) {
                GL11.glPushMatrix();
                GL11.glRotatef(120.0f, 1.0f, 0.0f, 0.0f);
                RenderHelper.enableLighting();
                GL11.glPopMatrix();
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glEnable(32826);
                itemRenderer.renderItemInstance(this.textManager, this.minecraft.textureManager, this.sellItem, this.width / 2 - 8, this.height / 2 - 8 - yOffset);
                itemRenderer.method_1488(this.textManager, this.minecraft.textureManager, this.sellItem, this.width / 2 - 8, this.height / 2 - 8 - yOffset);
                GL11.glDisable(32826);
                RenderHelper.disableLighting();
                GL11.glDisable(2896);
                GL11.glDisable(2929);
            }
        }
    }

    public void setBuyItem(int itemID, int amount, int damage) {
        this.buyItem.itemId = itemID;
        this.buyItem.count = amount;
        this.buyItem.setDamage(damage);
    }

    public void setSellItem(int itemID, int amount, int damage) {
        this.sellItem.itemId = itemID;
        this.sellItem.count = amount;
        this.sellItem.setDamage(damage);
    }

    public void setSupplyLeft(int s) {
        this.supplyLeft = s;
    }
}
