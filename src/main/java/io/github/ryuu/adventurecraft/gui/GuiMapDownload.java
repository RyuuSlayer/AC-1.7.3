package io.github.ryuu.adventurecraft.gui;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.SwingUtilities;

import io.github.ryuu.adventurecraft.scripting.ScriptUIContainer;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

class GuiMapDownload extends da {

    //TODO: We will want to get rid of the ingame Map Download in favor for the Launcher.

    public ScriptUIContainer ui = new ScriptUIContainer(0.0F, 26.0F, null);

    private ArrayList<GuiMapElement> maps = new ArrayList<GuiMapElement>();

    private File mapDownloadFolder = new File("./mapDownloads/");

    GuiMapDownload(da guiscreen) {
        if (!this.mapDownloadFolder.exists())
            this.mapDownloadFolder.mkdirs();
        this.rand = new Random();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GuiMapDownload.this.downloadAndLoadMapInfo();
            }
        });
        this.mouseOver = null;
        this.parentScreen = guiscreen;
        this.mapName = null;
    }

    public void b() {
        this.e.add(new ke(0, 2, 2, 50, 20, "Back"));
    }

    protected void a(ke guibutton) {
        if (guibutton.f == 0)
            this.b.a(this.parentScreen);
    }

    protected void a(char c, int i) {
        if (!this.downloading)
            super.a(c, i);
    }

    protected void a(int i, int j, int k) {
        if (!this.downloading) {
            super.a(i, j, k);
            if (k == 0) {
                if (j > 24) {
                    GuiMapElement map = getMapAtCoord(i, j);
                    if (map != null)
                        if (j - map.curY - this.ui.curY > 85.0F) {
                            map.ratingClicked((int)(i - map.curX - this.ui.curX), (int)(j - map.curY - this.ui.curY));
                        } else {
                            this.mapUrl = map.mapURL;
                            this.mapName = map.mapName;
                            this.downloadingMapElement = map;
                            this.downloadSize = 0;
                            this.downloading = true;
                            this.downloadingMap = true;
                            this.downloadingMapName = this.mapName;
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    GuiMapDownload.this.downloadMap();
                                }
                            });
                        }
                }
                if (this.maxOffset < 26 && i >= this.scrollBarX && i <= this.scrollBarX + 8 && j > 26) {
                    float yOffset = Math.max(Math.min((j - 8 - 26) / (this.d - 26.0F - 16.0F - 32.0F), 1.0F), 0.0F);
                    this.ui.curY = (26 - (int)(yOffset * (26 - this.maxOffset)));
                    this.scrolling = true;
                }
            } else if (k == 1) {
                this.rightClickDown = true;
                this.mouseY = j;
            }
        }
    }

    protected void b(int i, int j, int k) {
        if (!this.downloading) {
            if (this.rightClickDown && j != this.mouseY) {
                this.ui.curY += (j - this.mouseY);
                this.mouseY = j;
                if (this.ui.curY > 26.0F) {
                    this.ui.curY = 26.0F;
                } else if (this.ui.curY < this.maxOffset) {
                    this.ui.curY = this.maxOffset;
                }
            } else if (this.scrolling) {
                float yOffset = Math.max(Math.min((j - 8 - 26) / (this.d - 26.0F - 16.0F - 32.0F), 1.0F), 0.0F);
                this.ui.curY = (26 - (int)(yOffset * (26 - this.maxOffset)));
            }
            this.ui.onUpdate();
            if (k == 0) {
                this.scrolling = false;
            } else if (k == 1) {
                this.rightClickDown = false;
            }
            for (GuiMapElement m : this.maps)
                m.mouseMoved((int)(i - m.curX - this.ui.curX), (int)(j - m.curY - this.ui.curY));
        }
        super.b(i, j, k);
    }

    public void a(int i, int j, float f) {
        i();
        if (!this.downloading) {
            if (this.mapName != null) {
                startMap();
                return;
            }
            a(0, 24, this.c + 32, this.d + 32, 1073741824);
            a(0, 24, this.c + 32, 32, -16777216, 0);
            a(0, this.d - 32 - 8, this.c + 32, this.d - 32, 0, -16777216);
            GuiMapElement newMap = getMapAtCoord(i, j);
            if (newMap != this.mouseOver) {
                if (newMap != null)
                    newMap.fadeDescriptionIn();
                if (this.mouseOver != null)
                    this.mouseOver.fadeDescriptionOut();
            }
            this.mouseOver = newMap;
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3008);
            GL11.glDisable(2929);
            this.ui.render(this.g, this.b.p, f);
            GL11.glEnable(2929);
            if (this.maxOffset < 26) {
                a(this.scrollBarX, 26, this.scrollBarX + 8, this.d - 32, -2147483648);
                float yOffset = 1.0F - (this.ui.curY - this.maxOffset) / (26.0F - this.maxOffset);
                int y = (int)((this.d - 26 - 16 - 32) * yOffset);
                a(this.scrollBarX, 26 + y, this.scrollBarX + 8, 26 + y + 16, -1325400065);
            }
            drawBackground(0, 24, 255, 255);
            drawBackground(this.d - 32, this.d, 255, 255);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3008);
            GL11.glDisable(2929);
            int w = this.g.a("Maps Available For Download");
            this.g.b("Maps Available For Download", this.c / 2 - w / 2, 8, 16777215);
            String s = "Additional maps can be found on the AdventureCraft Wiki";
            w = this.g.a(s);
            this.g.b(s, this.c / 2 - w / 2, this.d - 26, 16777215);
            s = "http://adventurecraft.wikkii.com/";
            w = this.g.a(s);
            this.g.b(s, this.c / 2 - w / 2, this.d - 14, 16777215);
            GL11.glDisable(3042);
            GL11.glEnable(3008);
            GL11.glEnable(3553);
            super.a(i, j, f);
        } else if (!this.downloadingMap) {
            drawStatus("Downloading Map Info", this.mapImagesDownloaded, this.totalMaps);
        } else {
            drawStatus("Downloading Map: " + this.mapName, this.downloadedAmount, this.downloadSize);
            if (this.downloadSize != 0) {
                float downloadedAmountMegs = this.downloadedAmount / 1024.0F / 1024.0F;
                float downloadSizeMegs = this.downloadSize / 1024.0F / 1024.0F;
                String downloaded = String.format("Downloaded %.2f/%.2f MBs", new Object[] { Float.valueOf(downloadedAmountMegs), Float.valueOf(downloadSizeMegs) });
                int w = this.g.a(downloaded);
                this.g.b(downloaded, this.c / 2 - w / 2, this.d / 2 + 15, 16777215);
            }
        }
    }

    private void drawStatus(String s, int cur, int total) {
        int w = this.g.a(s);
        this.g.b(s, this.c / 2 - w / 2, this.d / 2 - 4, 16777215);
        a(this.c / 2 - 50, this.d / 2 + 5, this.c / 2 + 50, this.d / 2 + 13, -2147483648);
        if (total > 0) {
            int xOffset = (int)(100.0D * cur / total - 50.0D);
            a(this.c / 2 - 50, this.d / 2 + 5, this.c / 2 + xOffset, this.d / 2 + 13, -15675632);
        }
    }

    public void a(Minecraft minecraft, int i, int j) {
        super.a(minecraft, i, j);
        this.ui.setX((i / 2 - 152));
        this.scrollBarX = 1 + this.c / 2 + 152;
        this.maxOffset = Math.min(this.d - 32 - 102 * (this.maps.size() + 2) / 3, 26);
        if (this.ui.curY < this.maxOffset)
            this.ui.setY(this.maxOffset);
    }

    private GuiMapElement addMap(String title, String topDescription, String description, String texture, String mURL, int mapID, int totalRating, int numRatings) {
        int i = this.maps.size();
        GuiMapElement r = new GuiMapElement(102 * i % 3, 102 * i / 3, this.ui, title, topDescription, description, texture, mURL, mapID, totalRating, numRatings);
        this.maps.add(r);
        this.maxOffset = Math.min(this.d - 32 - 102 * (this.maps.size() + 2) / 3, 26);
        return r;
    }

    private GuiMapElement getMapAtCoord(int x, int y) {
        for (GuiMapElement m : this.maps) {
            int dX = (int)(x - m.curX + this.ui.curX);
            int dY = (int)(y - m.curY + this.ui.curY);
            if (dX >= 0 && dX < 100 && dY >= 0 && dY < 100)
                return m;
        }
        return null;
    }

    private void downloadAndLoadMapInfo() {
        File mapInfo = new File(this.mapDownloadFolder, "mapInfo.txt");
        if (mapInfo.exists())
            try {
                BufferedReader input = new BufferedReader(new FileReader(mapInfo));
                ArrayList<String> lines = new ArrayList<String>();
                while (input.ready()) {
                    String line = input.readLine();
                    line = line.replace("\\n", "\n");
                    lines.add(line);
                }
                this.totalMaps = lines.size();
                for (String line : lines) {
                    String[] parts = line.split(", ");
                    if (parts.length >= 13) {
                        int mapID = (new Integer(parts[9])).intValue();
                        String mapName = parts[0];
                        String mapDescription = parts[4];
                        String mapTexture = "http://www.adventurecraft.org/mapThumbnails/" + parts[0].replace(" ", "%20") + ".png";
                        String mapURL = parts[12];
                        mapTexture = mapTexture.replace(" ", "%20");
                        boolean first = true;
                        String topDescription = "";
                        if (!parts[2].equals("")) {
                            topDescription = topDescription + "by " + parts[2];
                            first = false;
                        }
                        if (!parts[8].equals("")) {
                            if (!first)
                                topDescription = topDescription + "\n";
                            topDescription = topDescription + "Downloads: " + parts[8];
                            first = false;
                        }
                        int totalRating = (new Integer(parts[10])).intValue();
                        int numRatings = (new Integer(parts[11])).intValue();
                        GuiMapElement map = addMap(mapName, topDescription, mapDescription, "./mapDownloads/" + mapName + ".png", mapURL, mapID, totalRating, numRatings);
                        File mFolder = new File("./maps/" + map.mapName);
                        if (mFolder.exists())
                            map.setAsDownloaded();
                    }
                    this.mapImagesDownloaded++;
                }
            } catch (FileNotFoundException e) {

            } catch (IOException e) {}
        this.downloading = false;
    }

    private void deleteFilesInFolder(File dir) {
        for (File f : dir.listFiles()) {
            if (f.isDirectory())
                deleteFilesInFolder(f);
            f.delete();
        }
    }

    private void downloadMap() {
        String[] dropboxIDs = { "51083669", "51083634", "51083701", "51083780" };
        downloadFile(this.mapUrl.replace("51083780", dropboxIDs[this.rand.nextInt(4)]), "./mapDownloads/map.zip");
        File mapZip = new File(this.mapDownloadFolder, "map.zip");
        File mapDir = new File("./maps/" + this.mapName);
        if (mapDir.exists()) {
            if (mapDir.isDirectory())
                deleteFilesInFolder(mapDir);
            mapDir.delete();
        }
        mapDir.mkdir();
        ZipInputStream zipinputstream = null;
        try {
            byte[] buf = new byte[8192];
            zipinputstream = new ZipInputStream(new FileInputStream(mapZip));
            ZipEntry zipentry = zipinputstream.getNextEntry();
            while (zipentry != null) {
                String entryName = zipentry.getName();
                File f = new File(mapDir, entryName);
                if (!zipentry.isDirectory()) {
                    try {
                        f.createNewFile();
                        FileOutputStream fileoutputstream = new FileOutputStream(f);
                        int n;
                        while ((n = zipinputstream.read(buf, 0, 8192)) > -1)
                            fileoutputstream.write(buf, 0, n);
                        fileoutputstream.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    f.mkdirs();
                }
                zipinputstream.closeEntry();
                zipentry = zipinputstream.getNextEntry();
            }
        } catch (FileNotFoundException e) {
            this.mapName = null;
        } catch (IOException e) {
            this.mapName = null;
        }
        mapZip.delete();
        this.downloadingMapElement.setAsDownloaded();
        this.downloading = false;
        this.downloadingMap = false;
    }

    private void startMap() {
        String saveName = "";
        File mcDir = Minecraft.b();
        File saveDir = new File(mcDir, "saves");
        int i = 1;
        while (true) {
            saveName = String.format("%s - Save %d", new Object[] { this.mapName, Integer.valueOf(i) });
            File worldDir = new File(saveDir, saveName);
            i++;
            if (!worldDir.exists()) {
                this.b.saveMapUsed(saveName, this.mapName);
                this.b.c = new os(this.b);
                this.b.startWorld(saveName, saveName, 0L, this.mapName);
                return;
            }
        }
    }

    private boolean downloadFile(String downloadSite, String outputFileName) {
        try {
            URL url = new URL(downloadSite);
            URLConnection urlconnection = url.openConnection();
            urlconnection.connect();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            File output = new File(outputFileName);
            output.mkdirs();
            if (output.exists())
                output.delete();
            output.createNewFile();
            FileOutputStream fos = new FileOutputStream(output);
            bis = new BufferedInputStream(urlconnection.getInputStream());
            bos = new BufferedOutputStream(fos);
            this.downloadedAmount = 0;
            this.downloadSize = urlconnection.getContentLength();
            byte[] buffer = new byte[65536];
            int buffersize;
            while ((buffersize = bis.read(buffer, 0, 65536)) != -1) {
                bos.write(buffer, 0, buffersize);
                this.downloadedAmount += buffersize;
            }
            bos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void drawBackground(int top, int bottom, int topAlpha, int bottomAlpha) {
        nw tessellator = nw.a;
        GL11.glBindTexture(3553, this.b.p.b("/gui/background.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float f = 32.0F;
        tessellator.b();
        tessellator.a(4210752, bottomAlpha);
        tessellator.a(0.0D, bottom, 0.0D, 0.0D, (bottom / f));
        tessellator.a(this.c, bottom, 0.0D, (this.c / f), (bottom / f));
        tessellator.a(4210752, topAlpha);
        tessellator.a(this.c, top, 0.0D, (this.c / f), (top / f));
        tessellator.a(0.0D, top, 0.0D, 0.0D, (top / f));
        tessellator.a();
    }

    private boolean downloading = true;

    private int mapImagesDownloaded = 0;

    private int totalMaps = 0;

    private boolean downloadingMap = false;

    private boolean rightClickDown = false;

    private boolean scrolling = false;

    static final String url = "http://www.adventurecraft.gq/";

    protected da parentScreen;

    private GuiMapElement mouseOver;

    private String downloadingMapName;

    private int downloadedAmount;

    private int downloadSize;

    private String mapUrl;

    private String mapName;

    private GuiMapElement downloadingMapElement;

    private int mouseY;

    private int maxOffset;

    private int scrollBarX;

    private Random rand;
}
