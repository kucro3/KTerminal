package org.kucro3.kterminal.util;

import java.awt.*;
import java.awt.datatransfer.*;

public class ClipboardUtil {
    private ClipboardUtil()
    {
    }

    public static String getString()
    {
        Transferable trans = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);

        if(trans == null)
            return null;

        if(!trans.isDataFlavorSupported(DataFlavor.stringFlavor))
            return null;

        try {
            return (String) trans.getTransferData(DataFlavor.stringFlavor);
        } catch (Throwable e) {
            return null;
        }
    }

    public static void putString(String str)
    {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(str), null);
    }
}
