package io.github.ryuu.adventurecraft.mixin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.minecraft.client.Minecraft;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MixinResourceDownloadThread extends Thread {
    private final Minecraft b;
    public File a;
    private boolean c;

    public MixinResourceDownloadThread(File file, Minecraft minecraft) {
        this.c = false;
        this.b = minecraft;
        setName("Resource download thread");
        setDaemon(true);
        this.a = new File(file, "resources/");
        if (!this.a.exists() && !this.a.mkdirs())
            throw new RuntimeException("The working directory could not be created: " + this.a);
    }

    public void run() {
        downloadResource("http://s3.amazonaws.com/MinecraftResources/");
        downloadResource("http://adventurecraft.org/resources/");
    }

    public void downloadResource(String urlString) {
        try {
            URL url = new URL(urlString);
            DocumentBuilderFactory documentbuilderfactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentbuilder = documentbuilderfactory.newDocumentBuilder();
            Document document = documentbuilder.parse(url.openStream());
            NodeList nodelist = document.getElementsByTagName("Contents");
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < nodelist.getLength(); j++) {
                    Node node = nodelist.item(j);
                    if (node.getNodeType() == 1) {
                        Element element = (Element) node;
                        String s = element.getElementsByTagName("Key").item(0).getChildNodes().item(0).getNodeValue();
                        long l = Long.parseLong(element.getElementsByTagName("Size").item(0).getChildNodes().item(0).getNodeValue());
                        if (l > 0L) {
                            a(url, s, l, i);
                            if (this.c)
                                return;
                        }
                    }
                }
            }
        } catch (Exception exception) {
            a(this.a, "");
            exception.printStackTrace();
        }
    }

    public void a() {
        a(this.a, "");
    }

    void a(File file, String s) {
        File[] afile = file.listFiles();
        for (int i = 0; i < afile.length; i++) {
            if (afile[i].isDirectory()) {
                a(afile[i], s + afile[i].getName() + "/");
            } else {
                try {
                    this.b.a(s + afile[i].getName(), afile[i]);
                } catch (Exception exception) {
                    System.out.println("Failed to add " + s + afile[i].getName());
                }
            }
        }
    }

    private void a(URL url, String s, long l, int i) {
        try {
            int j = s.indexOf("/");
            String s1 = s.substring(0, j);
            if (s1.equals("sound") || s1.equals("newsound")) {
                if (i != 0)
                    return;
            } else if (i != 1) {
                return;
            }
            File file = new File(this.a, s);
            if (!file.exists() || file.length() != l) {
                file.getParentFile().mkdirs();
                String s2 = s.replaceAll(" ", "%20");
                a(new URL(url, s2), file, l);
                if (this.c)
                    return;
            }
            this.b.a(s, file);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void a(URL url, File file, long l) throws IOException {
        byte[] abyte0 = new byte[4096];
        DataInputStream datainputstream = new DataInputStream(url.openStream());
        DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file));
        for (int i = 0; (i = datainputstream.read(abyte0)) >= 0; ) {
            dataoutputstream.write(abyte0, 0, i);
            if (this.c)
                return;
        }
        datainputstream.close();
        dataoutputstream.close();
    }

    public void b() {
        this.c = true;
    }
}
