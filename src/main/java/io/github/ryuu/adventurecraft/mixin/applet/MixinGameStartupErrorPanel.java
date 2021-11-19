package io.github.ryuu.adventurecraft.mixin.applet;

import net.minecraft.applet.GameStartupErrorPanel;
import net.minecraft.applet.LogoCanvas;
import net.minecraft.applet.SquareCanvas;
import net.minecraft.client.GameStartupError;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;

import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mixin(GameStartupErrorPanel.class)
public class MixinGameStartupErrorPanel extends Panel {

    public MixinGameStartupErrorPanel(GameStartupError unexpectedthrowable) {
        this.setBackground(new Color(3028036));
        this.setLayout(new BorderLayout());
        StringWriter stringwriter = new StringWriter();
        unexpectedthrowable.field_1705.printStackTrace(new PrintWriter(stringwriter));
        String s = stringwriter.toString();
        String s1 = "";
        String s2 = "";
        try {
            s2 = s2 + "Generated " + new SimpleDateFormat().format(new Date()) + "\n";
            s2 = s2 + "\n";
            s2 = s2 + Version.version;
            s2 = s2 + "OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version") + "\n";
            s2 = s2 + "Java: " + System.getProperty("java.version") + ", " + System.getProperty("java.vendor") + "\n";
            s2 = s2 + "VM: " + System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor") + "\n";
            s2 = s2 + "LWJGL: " + Sys.getVersion() + "\n";
            s1 = GL11.glGetString(7936);
            s2 = s2 + "OpenGL: " + GL11.glGetString(7937) + " version " + GL11.glGetString(7938) + ", " + GL11.glGetString(7936) + "\n";
        } catch (Throwable throwable) {
            s2 = s2 + "[failed to get system properties (" + throwable + ")]\n";
        }
        s2 = s2 + "\n";
        s2 = s2 + s;
        String s3 = "";
        s3 = s3 + "\n";
        s3 = s3 + "\n";
        if (s.contains("Pixel format not accelerated")) {
            s3 = s3 + "      Bad video card drivers!      \n";
            s3 = s3 + "      -----------------------      \n";
            s3 = s3 + "\n";
            s3 = s3 + "Minecraft was unable to start because it failed to find an accelerated OpenGL mode.\n";
            s3 = s3 + "This can usually be fixed by updating the video card drivers.\n";
            if (s1.toLowerCase().contains("nvidia")) {
                s3 = s3 + "\n";
                s3 = s3 + "You might be able to find drivers for your video card here:\n";
                s3 = s3 + "  http://www.nvidia.com/\n";
            } else if (s1.toLowerCase().contains("ati")) {
                s3 = s3 + "\n";
                s3 = s3 + "You might be able to find drivers for your video card here:\n";
                s3 = s3 + "  http://www.amd.com/\n";
            }
        } else {
            s3 = s3 + "      Minecraft has crashed!      \n";
            s3 = s3 + "      ----------------------      \n";
            s3 = s3 + "\n";
            s3 = s3 + "Minecraft has stopped running because it encountered a problem.\n";
            s3 = s3 + "\n";
            s3 = s3 + "If you wish to report this, please copy this entire text and email it to cryect@gmail.com.\n";
            s3 = s3 + "Please include a description of what you did when the error occured.\n";
        }
        s3 = s3 + "\n";
        s3 = s3 + "\n";
        s3 = s3 + "\n";
        s3 = s3 + "--- BEGIN ERROR REPORT " + Integer.toHexString(s3.hashCode()) + " --------\n";
        s3 = s3 + s2;
        s3 = s3 + "--- END ERROR REPORT " + Integer.toHexString(s3.hashCode()) + " ----------\n";
        s3 = s3 + "\n";
        s3 = s3 + "\n";
        TextArea textarea = new TextArea(s3, 0, 0, 1);
        textarea.setFont(new Font("Monospaced", 0, 12));
        this.add(new LogoCanvas(), "North");
        this.add(new SquareCanvas(80), "East");
        this.add(new SquareCanvas(80), "West");
        this.add(new SquareCanvas(100), "South");
        this.add(textarea, "Center");
    }
}
