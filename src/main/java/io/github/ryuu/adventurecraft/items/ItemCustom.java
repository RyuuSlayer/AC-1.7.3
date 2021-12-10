package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.extensions.items.ExItemType;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import io.github.ryuu.adventurecraft.mixin.item.AccessItemType;
import io.github.ryuu.adventurecraft.scripting.ScriptItem;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class ItemCustom extends ACItemType implements ExItemType {

    static ArrayList<Integer> loadedItemIDs = new ArrayList<>();
    String fileName;
    String onItemUsedScript;

    public ItemCustom(int itemID, String fName, Properties p) {
        super(itemID - 256);
        Integer maxStackSizeI;
        String maxStackSizeS;
        Integer maxItemDamageI;
        String maxItemDamageS;
        Integer iconIndexI;
        this.fileName = fName;
        String iconIndexS = p.getProperty("iconIndex");
        if (iconIndexS != null && (iconIndexI = this.loadPropertyInt("iconIndex", iconIndexS)) != null) {
            this.setTexturePosition(iconIndexI);
        }
        if ((maxItemDamageS = p.getProperty("maxItemDamage")) != null && (maxItemDamageI = this.loadPropertyInt("maxItemDamage", maxItemDamageS)) != null) {
            ((AccessItemType) this).setField_402(maxItemDamageI);
        }
        if ((maxStackSizeS = p.getProperty("maxStackSize")) != null && (maxStackSizeI = this.loadPropertyInt("maxStackSize", maxStackSizeS)) != null) {
            this.maxStackSize = maxStackSizeI;
        }
        this.setName(p.getProperty("name", "Unnamed"));
        this.onItemUsedScript = p.getProperty("onItemUsedScript", "");
    }

    @Override
    public int getItemUseDelay() {
        return 1;
    }

    private static ItemCustom loadScript(File descFile) {
        block7:
        {
            Properties p = new Properties();
            try {
                p.load(new FileInputStream(descFile));
                int itemID = Integer.parseInt(p.getProperty("itemID", "-1"));
                if (itemID == -1) {
                    AccessMinecraft.getInstance().overlay.addChatMessage(String.format("ItemID for %s is unspecified", descFile.getName()));
                    break block7;
                }
                if (itemID <= 0) {
                    AccessMinecraft.getInstance().overlay.addChatMessage(String.format("ItemID for %s specifies a negative itemID", descFile.getName()));
                    break block7;
                }
                if (ItemType.byId[itemID] != null) {
                    AccessMinecraft.getInstance().overlay.addChatMessage(String.format("ItemID (%d) for %s is already in use by %s", itemID, descFile.getName()));
                    break block7;
                }
                return new ItemCustom(itemID, descFile.getName(), p);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                AccessMinecraft.getInstance().overlay.addChatMessage(String.format("ItemID for %s is specified invalidly '%s'", descFile.getName(), p.getProperty("itemID")));
            }
        }
        return null;
    }

    public static void loadItems(File itemFolder) {
        for (Integer i : loadedItemIDs) {
            ItemType.byId[i] = null;
        }
        loadedItemIDs.clear();
        if (!itemFolder.exists()) {
            return;
        }
        for (File itemFile : itemFolder.listFiles()) {
            ItemCustom item;
            if (!itemFile.isFile() || (item = ItemCustom.loadScript(itemFile)) == null) continue;
            loadedItemIDs.add(item.id);
        }
    }

    private Integer loadPropertyInt(String pName, String intString) {
        try {
            Integer i = Integer.parseInt(intString);
            return i;
        } catch (NumberFormatException e) {
            AccessMinecraft.getInstance().overlay.addChatMessage(String.format("Item File '%s' Property '%s' is specified invalidly '%s'", this.fileName, pName, intString));
            return null;
        }
    }

    @Override
    public ItemInstance use(ItemInstance item, Level level, Player player) {
        if (!this.onItemUsedScript.equals("")) {
            ScriptItem item2 = new ScriptItem(item);
            ExLevel exLevel = (ExLevel) level;
            Object wrappedOut = Context.javaToJS(item2, exLevel.getScope());
            ScriptableObject.putProperty(exLevel.getScope(), "itemUsed", wrappedOut);
            exLevel.getScriptHandler().runScript(this.onItemUsedScript, exLevel.getScope());
        }
        return item;
    }
}
