package io.github.ryuu.adventurecraft.mixin.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ResourceDownloadThread;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.URL;

@Mixin(ResourceDownloadThread.class)
public class MixinResourceDownloadThread extends Thread {

    private final Minecraft field_138;
    @Shadow()
    public File workingDirectory;
    private boolean field_139 = false;

    public MixinResourceDownloadThread(File file, Minecraft minecraft) {
        this.field_138 = minecraft;
        this.setName("Resource download thread");
        this.setDaemon(true);
        this.workingDirectory = new File(file, "resources/");
        if (!this.workingDirectory.exists() && !this.workingDirectory.mkdirs()) {
            throw new RuntimeException("The working directory could not be created: " + this.workingDirectory);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void run() {
        this.downloadResource("http://s3.amazonaws.com/MinecraftResources/");
        this.downloadResource("http://adventurecraft.org/resources/");
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void downloadResource(String urlString) {
        try {
            URL url = new URL(urlString);
            DocumentBuilderFactory documentbuilderfactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentbuilder = documentbuilderfactory.newDocumentBuilder();
            Document document = documentbuilder.parse(url.openStream());
            NodeList nodelist = document.getElementsByTagName("Contents");
            for (int i = 0; i < 2; ++i) {
                for (int j = 0; j < nodelist.getLength(); ++j) {
                    Node node = nodelist.item(j);
                    if (node.getNodeType() != 1) continue;
                    Element element = (Element) node;
                    String s = element.getElementsByTagName("Key").item(0).getChildNodes().item(0).getNodeValue();
                    long l = Long.parseLong(element.getElementsByTagName("Size").item(0).getChildNodes().item(0).getNodeValue());
                    if (l <= 0L) continue;
                    this.method_110(url, s, l, i);
                    if (!this.field_139) continue;
                    return;
                }
            }
        } catch (Exception exception) {
            this.method_108(this.workingDirectory, "");
            exception.printStackTrace();
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_107() {
        this.method_108(this.workingDirectory, "");
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    void method_108(File file, String s) {
        File[] afile = file.listFiles();
        for (int i = 0; i < afile.length; ++i) {
            if (afile[i].isDirectory()) {
                this.method_108(afile[i], s + afile[i].getName() + "/");
                continue;
            }
            try {
                this.field_138.loadSoundFromDir(s + afile[i].getName(), afile[i]);
                continue;
            } catch (Exception exception) {
                System.out.println("Failed to add " + s + afile[i].getName());
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_110(URL url, String s, long l, int i) {
        try {
            int j = s.indexOf("/");
            String s1 = s.substring(0, j);
            if (s1.equals("sound") || s1.equals("newsound") ? i != 0 : i != 1) {
                return;
            }
            File file = new File(this.workingDirectory, s);
            if (!file.exists() || file.length() != l) {
                file.getParentFile().mkdirs();
                String s2 = s.replaceAll(" ", "%20");
                this.method_109(new URL(url, s2), file, l);
                if (this.field_139) {
                    return;
                }
            }
            this.field_138.loadSoundFromDir(s, file);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_109(URL url, File file, long l) throws IOException {
        byte[] abyte0 = new byte[4096];
        DataInputStream datainputstream = new DataInputStream(url.openStream());
        DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file));
        int i = 0;
        while ((i = datainputstream.read(abyte0)) >= 0) {
            dataoutputstream.write(abyte0, 0, i);
            if (!this.field_139) continue;
            return;
        }
        datainputstream.close();
        dataoutputstream.close();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_111() {
        this.field_139 = true;
    }
}
