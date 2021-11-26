package io.github.ryuu.adventurecraft.util;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;

public class ClipboardHandler {

    ClipboardHandler() {
    }

    public static String getClipboard() {
        boolean hasTransferableText;
        String result = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(null);
        boolean bl = hasTransferableText = contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
        if (hasTransferableText) {
            try {
                result = (String) contents.getTransferData(DataFlavor.stringFlavor);
            } catch (UnsupportedFlavorException | IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static void setClipboard(String c) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection contents = new StringSelection(c);
        clipboard.setContents(contents, contents);
    }
}
