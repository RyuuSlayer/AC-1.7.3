package io.github.ryuu.adventurecraft.util;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;

class ClipboardHandler {

    ClipboardHandler() {
    }

    static String getClipboard() {
        boolean hasTransferableText;
        String result = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(null);
        boolean bl = hasTransferableText = contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
        if (hasTransferableText) {
            try {
                result = (String) contents.getTransferData(DataFlavor.stringFlavor);
            } catch (UnsupportedFlavorException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    static void setClipboard(String c) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection contents = new StringSelection(c);
        clipboard.setContents(contents, contents);
    }
}
