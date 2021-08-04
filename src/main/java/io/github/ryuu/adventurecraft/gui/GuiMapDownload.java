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
import net.minecraft.class_520;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.render.Tessellator;
import org.lwjgl.opengl.GL11;

class GuiMapDownload extends Screen {

    //TODO: We will want to get rid of the ingame Map Download in favor for the Launcher.

    public ScriptUIContainer ui = new ScriptUIContainer(0.0F, 26.0F, null);

    private final ArrayList<GuiMapElement> maps = new ArrayList<GuiMapElement>();

    private final File mapDownloadFolder = new File("./mapDownloads/");

    public GuiMapDownload(Screen guiscreen) {
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

    @Override
    public void init() {
        this.buttons.add(new Button(0, 2, 2, 50, 20, "Back"));
    }

    @Override
    protected void buttonClicked(Button guibutton) {
        if (guibutton.id == 0)
            this.minecraft.openScreen(this.parentScreen);
    }

    @Override
    protected void keyPressed(char c, int i) {
        if (!this.downloading)
            super.keyPressed(c, i);
    }

    @Override
    protected void mouseClicked(int i, int j, int k) {
        if (!this.downloading) {
            super.mouseClicked(i, j, k);
            if (k == 0) {
                if (j > 24) {
                    GuiMapElement map = getMapAtCoord(i, j);
                    if (map != null)
                        if (j - map.curY - this.ui.curY > 85.0F) {
                            map.ratingClicked((int) (i - map.curX - this.ui.curX), (int) (j - map.curY - this.ui.curY));
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
                    float yOffset = Math.max(Math.min((j - 8 - 26) / (this.height - 26.0F - 16.0F - 32.0F), 1.0F), 0.0F);
                    this.ui.curY = (26 - (int) (yOffset * (26 - this.maxOffset)));
                    this.scrolling = true;
                }
            } else if (k == 1) {
                this.rightClickDown = true;
                this.mouseY = j;
            }
        }
    }

    @Override
    protected void mouseReleased(int i, int j, int k) {
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
                float yOffset = Math.max(Math.min((j - 8 - 26) / (this.height - 26.0F - 16.0F - 32.0F), 1.0F), 0.0F);
                this.ui.curY = (26 - (int) (yOffset * (26 - this.maxOffset)));
            }
            this.ui.onUpdate();
            if (k == 0) {
                this.scrolling = false;
            } else if (k == 1) {
                this.rightClickDown = false;
            }
            for (GuiMapElement m : this.maps)
                m.mouseMoved((int) (i - m.curX - this.ui.curX), (int) (j - m.curY - this.ui.curY));
        }
        super.mouseReleased(i, j, k);
    }

    @Override
    public void render(int i, int j, float f) {
        renderBackground();
        if (!this.downloading) {
            if (this.mapName != null) {
                startMap();
                return;
            }
            fill(0, 24, this.width + 32, this.height + 32, 1073741824);
            fillGradient(0, 24, this.width + 32, 32, -16777216, 0);
            fillGradient(0, this.height - 32 - 8, this.width + 32, this.height - 32, 0, -16777216);
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
            this.ui.render(this.textManager, this.minecraft.textureManager, f);
            GL11.glEnable(2929);
            if (this.maxOffset < 26) {
                fill(this.scrollBarX, 26, this.scrollBarX + 8, this.height - 32, -2147483648);
                float yOffset = 1.0F - (this.ui.curY - this.maxOffset) / (26.0F - this.maxOffset);
                int y = (int) ((this.height - 26 - 16 - 32) * yOffset);
                fill(this.scrollBarX, 26 + y, this.scrollBarX + 8, 26 + y + 16, -1325400065);
            }
            drawBackground(0, 24, 255, 255);
            drawBackground(this.height - 32, this.height, 255, 255);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3008);
            GL11.glDisable(2929);
            int w = this.textManager.getTextWidth("Maps Available For Download");
            this.textManager.drawText("Maps Available For Download", this.width / 2 - w / 2, 8, 16777215);
            String s = "Additional maps can be found on the AdventureCraft Wiki";
            w = this.textManager.getTextWidth(s);
            this.textManager.drawText(s, this.width / 2 - w / 2, this.height - 26, 16777215);
            s = "http://adventurecraft.wikkii.com/";
            w = this.textManager.getTextWidth(s);
            this.textManager.drawText(s, this.width / 2 - w / 2, this.height - 14, 16777215);
            GL11.glDisable(3042);
            GL11.glEnable(3008);
            GL11.glEnable(3553);
            super.render(i, j, f);
        } else if (!this.downloadingMap) {
            drawStatus("Downloading Map Info", this.mapImagesDownloaded, this.totalMaps);
        } else {
            drawStatus("Downloading Map: " + this.mapName, this.downloadedAmount, this.downloadSize);
            if (this.downloadSize != 0) {
                float downloadedAmountMegs = this.downloadedAmount / 1024.0F / 1024.0F;
                float downloadSizeMegs = this.downloadSize / 1024.0F / 1024.0F;
                String downloaded = String.format("Downloaded %.2f/%.2f MBs", downloadedAmountMegs, downloadSizeMegs);
                int w = this.textManager.getTextWidth(downloaded);
                this.textManager.drawText(downloaded, this.width / 2 - w / 2, this.height / 2 + 15, 16777215);
            }
        }
    }

    private void drawStatus(String s, int cur, int total) {
        int w = this.textManager.getTextWidth(s);
        this.textManager.drawText(s, this.width / 2 - w / 2, this.height / 2 - 4, 16777215);
        fill(this.width / 2 - 50, this.height / 2 + 5, this.width / 2 + 50, this.height / 2 + 13, -2147483648);
        if (total > 0) {
            int xOffset = (int) (100.0D * cur / total - 50.0D);
            fill(this.width / 2 - 50, this.height / 2 + 5, this.width / 2 + xOffset, this.height / 2 + 13, -15675632);
        }
    }

    @Override
    public void init(Minecraft minecraft, int i, int j) {
        super.init(minecraft, i, j);
        this.ui.setX((i / 2 - 152));
        this.scrollBarX = 1 + this.width / 2 + 152;
        this.maxOffset = Math.min(this.height - 32 - 102 * (this.maps.size() + 2) / 3, 26);
        if (this.ui.curY < this.maxOffset)
            this.ui.setY(this.maxOffset);
    }

    private GuiMapElement addMap(String title, String topDescription, String description, String texture, String mURL, int mapID, int totalRating, int numRatings) {
        int i = this.maps.size();
        GuiMapElement r = new GuiMapElement(102 * i % 3, 102 * i / 3, this.ui, title, topDescription, description, texture, mURL, mapID, totalRating, numRatings);
        this.maps.add(r);
        this.maxOffset = Math.min(this.height - 32 - 102 * (this.maps.size() + 2) / 3, 26);
        return r;
    }

    private GuiMapElement getMapAtCoord(int x, int y) {
        for (GuiMapElement m : this.maps) {
            int dX = (int) (x - m.curX + this.ui.curX);
            int dY = (int) (y - m.curY + this.ui.curY);
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
                        int mapID = new Integer(parts[9]);
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
                        int totalRating = new Integer(parts[10]);
                        int numRatings = new Integer(parts[11]);
                        GuiMapElement map = addMap(mapName, topDescription, mapDescription, "./mapDownloads/" + mapName + ".png", mapURL, mapID, totalRating, numRatings);
                        File mFolder = new File("./maps/" + map.mapName);
                        if (mFolder.exists())
                            map.setAsDownloaded();
                    }
                    this.mapImagesDownloaded++;
                }
            } catch (FileNotFoundException e) {

            } catch (IOException e) {
            }
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
        String[] dropboxIDs = {"51083669", "51083634", "51083701", "51083780"};
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
        File mcDir = Minecraft.getGameDirectory();
        File saveDir = new File(mcDir, "saves");
        int i = 1;
        while (true) {
            saveName = String.format("%s - Save %d", this.mapName, i);
            File worldDir = new File(saveDir, saveName);
            i++;
            if (!worldDir.exists()) {
                this.minecraft.saveMapUsed(saveName, this.mapName);
                this.minecraft.interactionManager = new class_520(this.minecraft);
                this.minecraft.startWorld(saveName, saveName, 0L, this.mapName);
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
        Tessellator tessellator = Tessellator.INSTANCE;
        GL11.glBindTexture(3553, this.minecraft.textureManager.getTextureId("/gui/background.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float f = 32.0F;
        tessellator.start();
        tessellator.colour(4210752, bottomAlpha);
        tessellator.vertex(0.0D, bottom, 0.0D, 0.0D, (bottom / f));
        tessellator.vertex(this.width, bottom, 0.0D, (this.width / f), (bottom / f));
        tessellator.colour(4210752, topAlpha);
        tessellator.vertex(this.width, top, 0.0D, (this.width / f), (top / f));
        tessellator.vertex(0.0D, top, 0.0D, 0.0D, (top / f));
        tessellator.draw();
    }

    private boolean downloading = true;

    private int mapImagesDownloaded = 0;

    private int totalMaps = 0;

    private boolean downloadingMap = false;

    private boolean rightClickDown = false;

    private boolean scrolling = false;

    static final String url = "http://www.adventurecraft.gq/";

    protected Screen parentScreen;

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

    private final Random rand;
}
