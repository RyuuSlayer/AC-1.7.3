package io.github.ryuu.adventurecraft.mixin.client.util;

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
public abstract class MixinResourceDownloadThread extends Thread {

    @Shadow
    private boolean field_139;

    @Shadow
    public File workingDirectory;

    @Shadow
    protected abstract void method_108(File file, String string);

    @Shadow
    protected abstract void method_110(URL uRL, String string, long l, int i);

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void run() {
        this.downloadResource("http://s3.amazonaws.com/MinecraftResources/");
        this.downloadResource("http://adventurecraft.org/resources/");
    }

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
                    if (l > 0L) {
                        this.method_110(url, s, l, i);
                        if (!this.field_139) continue;
                        return;
                    }
                }
            }
        } catch (Exception exception) {
            this.method_108(this.workingDirectory, "");
            exception.printStackTrace();
        }
    }
}
