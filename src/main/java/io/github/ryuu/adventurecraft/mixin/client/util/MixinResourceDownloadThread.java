package io.github.ryuu.adventurecraft.mixin.client.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ResourceDownloadThread.class)
public class MixinResourceDownloadThread extends Thread {

    @Shadow()
    public File workingDirectory;

    private Minecraft field_138;

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
                    if (node.getNodeType() != 1)
                        continue;
                    Element element = (Element) node;
                    String s = ((Element) element.getElementsByTagName("Key").item(0)).getChildNodes().item(0).getNodeValue();
                    long l = Long.parseLong((String) ((Element) element.getElementsByTagName("Size").item(0)).getChildNodes().item(0).getNodeValue());
                    if (l <= 0L)
                        continue;
                    this.method_110(url, s, l, i);
                    if (!this.field_139)
                        continue;
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
            if (s1.equals((Object) "sound") || s1.equals((Object) "newsound") ? i != 0 : i != 1) {
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
        DataOutputStream dataoutputstream = new DataOutputStream((OutputStream) new FileOutputStream(file));
        int i = 0;
        while ((i = datainputstream.read(abyte0)) >= 0) {
            dataoutputstream.write(abyte0, 0, i);
            if (!this.field_139)
                continue;
            return;
        }
        datainputstream.close();
        dataoutputstream.close();
    }
}
