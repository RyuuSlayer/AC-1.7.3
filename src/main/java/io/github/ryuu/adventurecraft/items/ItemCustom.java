package io.github.ryuu.adventurecraft.items;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import io.github.ryuu.adventurecraft.scripting.ScriptItem;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

public class ItemCustom extends ItemType {
    static ArrayList<Integer> loadedItemIDs = new ArrayList<>();
    String fileName;
    String onItemUsedScript;

    public ItemCustom(int itemID, String fName, Properties p) {
        super(itemID - 256);
        this.fileName = fName;
        String iconIndexS = p.getProperty("iconIndex");
        if (iconIndexS != null) {
            Integer iconIndexI = loadPropertyInt("iconIndex", iconIndexS);
            if (iconIndexI != null)
                c(iconIndexI.intValue());
        }
        String maxItemDamageS = p.getProperty("maxItemDamage");
        if (maxItemDamageS != null) {
            Integer maxItemDamageI = loadPropertyInt("maxItemDamage", maxItemDamageS);
            if (maxItemDamageI != null)
                this.a = maxItemDamageI.intValue();
        }
        String maxStackSizeS = p.getProperty("maxStackSize");
        if (maxStackSizeS != null) {
            Integer maxStackSizeI = loadPropertyInt("maxStackSize", maxStackSizeS);
            if (maxStackSizeI != null)
                this.bg = maxStackSizeI.intValue();
        }
        a(p.getProperty("name", "Unnamed"));
        this.onItemUsedScript = p.getProperty("onItemUsedScript", "");
        this.itemUseDelay = 1;
    }

    private static ItemCustom loadScript(File descFile) {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(descFile));
            int itemID = Integer.parseInt(p.getProperty("itemID", "-1"));
            if (itemID == -1) {
                Minecraft.minecraftInstance.v.a(String.format("ItemID for %s is unspecified", new Object[]{descFile.getName()}));
            } else if (itemID <= 0) {
                Minecraft.minecraftInstance.v.a(String.format("ItemID for %s specifies a negative itemID", new Object[]{descFile.getName()}));
            } else if (ItemType.c[itemID] != null) {
                Minecraft.minecraftInstance.v.a(String.format("ItemID (%d) for %s is already in use by %s", new Object[]{Integer.valueOf(itemID), descFile.getName()}));
            } else {
                return new ItemCustom(itemID, descFile.getName(), p);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            Minecraft.minecraftInstance.v.a(String.format("ItemID for %s is specified invalidly '%s'", new Object[]{descFile.getName(), p.getProperty("itemID")}));
        }
        return null;
    }

    static void loadItems(File itemFolder) {
        for (Integer i : loadedItemIDs)
            ItemType.c[i.intValue()] = null;
        loadedItemIDs.clear();
        if (!itemFolder.exists())
            return;
        for (File itemFile : itemFolder.listFiles()) {
            if (itemFile.isFile()) {
                ItemCustom item = loadScript(itemFile);
                if (item != null)
                    loadedItemIDs.add(Integer.valueOf(item.bf));
            }
        }
    }

    private Integer loadPropertyInt(String pName, String intString) {
        try {
            Integer i = Integer.valueOf(Integer.parseInt(intString));
            return i;
        } catch (NumberFormatException e) {
            Minecraft.minecraftInstance.v.a(String.format("Item File '%s' Property '%s' is specified invalidly '%s'", new Object[]{this.fileName, pName, intString}));
            return null;
        }
    }

    public ItemInstance a(ItemInstance itemstack, Level world, Player entityplayer) {
        if (!this.onItemUsedScript.equals("")) {
            ScriptItem item = new ScriptItem(itemstack);
            Object wrappedOut = Context.javaToJS(item, world.scope);
            ScriptableObject.putProperty(world.scope, "itemUsed", wrappedOut);
            world.scriptHandler.runScript(this.onItemUsedScript, world.scope);
        }
        return itemstack;
    }
}
