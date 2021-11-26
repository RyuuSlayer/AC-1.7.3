package io.github.ryuu.adventurecraft.gui;

import net.minecraft.class_520;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.render.Tessellator;
import net.minecraft.script.ScriptUIContainer;
import org.lwjgl.opengl.GL11;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class GuiMapDownload extends Screen {

    static final String url = "http://www.adventurecraft.org/";
    private final File mapDownloadFolder;
    private final ArrayList<net.minecraft.src.GuiMapElement> maps = new ArrayList();
    private final Random rand;
    public ScriptUIContainer ui = new ScriptUIContainer(0.0f, 26.0f, null);
    protected Screen parentScreen;
    private GuiMapElement mouseOver;
    private boolean downloading = true;
    private int mapImagesDownloaded = 0;
    private int totalMaps = 0;
    private boolean downloadingMap = false;
    private String downloadingMapName;
    private int downloadedAmount;
    private int downloadSize;
    private String mapUrl;
    private String mapName;
    private GuiMapElement downloadingMapElement;
    private boolean rightClickDown = false;
    private int mouseY;
    private int maxOffset;
    private int scrollBarX;
    private boolean scrolling = false;

    public GuiMapDownload(Screen guiscreen) {
        this.mapDownloadFolder = new File("./mapDownloads/");
        if (!this.mapDownloadFolder.exists()) {
            this.mapDownloadFolder.mkdirs();
        }
        this.rand = new Random();
        SwingUtilities.invokeLater(GuiMapDownload.this::downloadAndLoadMapInfo);
        this.mouseOver = null;
        this.parentScreen = guiscreen;
        this.mapName = null;
    }

    @Override
    public void init() {
        this.buttons.add(new Button(0, 2, 2, 50, 20, "Back"));
    }

    @Override
    protected void buttonClicked(Button button) {
        if (button.id == 0) {
            this.minecraft.openScreen(this.parentScreen);
        }
    }

    @Override
    protected void keyPressed(char character, int key) {
        if (!this.downloading) {
            super.keyPressed(character, key);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        if (!this.downloading) {
            super.mouseClicked(mouseX, mouseY, button);
            if (button == 0) {
                GuiMapElement map;
                if (mouseY > 24 && (map = this.getMapAtCoord(mouseX, mouseY)) != null) {
                    if ((float) mouseY - map.curY - this.ui.curY > 85.0f) {
                        map.ratingClicked((int) ((float) mouseX - map.curX - this.ui.curX), (int) ((float) mouseY - map.curY - this.ui.curY));
                    } else {
                        this.mapUrl = map.mapURL;
                        this.mapName = map.mapName;
                        this.downloadingMapElement = map;
                        this.downloadSize = 0;
                        this.downloading = true;
                        this.downloadingMap = true;
                        this.downloadingMapName = this.mapName;
                        SwingUtilities.invokeLater(GuiMapDownload.this::downloadMap);
                    }
                }
                if (this.maxOffset < 26 && mouseX >= this.scrollBarX && mouseX <= this.scrollBarX + 8 && mouseY > 26) {
                    float yOffset = Math.max(Math.min((float) (mouseY - 8 - 26) / ((float) this.height - 26.0f - 16.0f - 32.0f), 1.0f), 0.0f);
                    this.ui.curY = 26 - (int) (yOffset * (float) (26 - this.maxOffset));
                    this.scrolling = true;
                }
            } else if (button == 1) {
                this.rightClickDown = true;
                this.mouseY = mouseY;
            }
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int button) {
        if (!this.downloading) {
            if (this.rightClickDown && mouseY != this.mouseY) {
                this.ui.curY += (float) (mouseY - this.mouseY);
                this.mouseY = mouseY;
                if (this.ui.curY > 26.0f) {
                    this.ui.curY = 26.0f;
                } else if (this.ui.curY < (float) this.maxOffset) {
                    this.ui.curY = this.maxOffset;
                }
            } else if (this.scrolling) {
                float yOffset = Math.max(Math.min((float) (mouseY - 8 - 26) / ((float) this.height - 26.0f - 16.0f - 32.0f), 1.0f), 0.0f);
                this.ui.curY = 26 - (int) (yOffset * (float) (26 - this.maxOffset));
            }
            this.ui.onUpdate();
            if (button == 0) {
                this.scrolling = false;
            } else if (button == 1) {
                this.rightClickDown = false;
            }
            for (GuiMapElement m : this.maps) {
                m.mouseMoved((int) ((float) mouseX - m.curX - this.ui.curX), (int) ((float) mouseY - m.curY - this.ui.curY));
            }
        }
        super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        if (!this.downloading) {
            if (this.mapName != null) {
                this.startMap();
                return;
            }
            this.fill(0, 24, this.width + 32, this.height + 32, 0x40000000);
            this.fillGradient(0, 24, this.width + 32, 32, -16777216, 0);
            this.fillGradient(0, this.height - 32 - 8, this.width + 32, this.height - 32, 0, -16777216);
            GuiMapElement newMap = this.getMapAtCoord(mouseX, mouseY);
            if (newMap != this.mouseOver) {
                if (newMap != null) {
                    newMap.fadeDescriptionIn();
                }
                if (this.mouseOver != null) {
                    this.mouseOver.fadeDescriptionOut();
                }
            }
            this.mouseOver = newMap;
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3008);
            GL11.glDisable(2929);
            this.ui.render(this.textManager, this.minecraft.textureManager, delta);
            GL11.glEnable(2929);
            if (this.maxOffset < 26) {
                this.fill(this.scrollBarX, 26, this.scrollBarX + 8, this.height - 32, Integer.MIN_VALUE);
                float yOffset = 1.0f - (this.ui.curY - (float) this.maxOffset) / (26.0f - (float) this.maxOffset);
                int y = (int) ((float) (this.height - 26 - 16 - 32) * yOffset);
                this.fill(this.scrollBarX, 26 + y, this.scrollBarX + 8, 26 + y + 16, -1325400065);
            }
            this.drawBackground(0, 24, 255, 255);
            this.drawBackground(this.height - 32, this.height, 255, 255);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3008);
            GL11.glDisable(2929);
            int w = this.textManager.getTextWidth("Maps Available For Download");
            this.textManager.drawTextWithoutShadow("Maps Available For Download", this.width / 2 - w / 2, 8, 0xFFFFFF);
            String s = "Additional maps can be found on the AdventureCraft Wiki";
            w = this.textManager.getTextWidth(s);
            this.textManager.drawTextWithoutShadow(s, this.width / 2 - w / 2, this.height - 26, 0xFFFFFF);
            s = "http://adventurecraft.wikkii.com/";
            w = this.textManager.getTextWidth(s);
            this.textManager.drawTextWithoutShadow(s, this.width / 2 - w / 2, this.height - 14, 0xFFFFFF);
            GL11.glDisable(3042);
            GL11.glEnable(3008);
            GL11.glEnable(3553);
            super.render(mouseX, mouseY, delta);
        } else if (!this.downloadingMap) {
            this.drawStatus("Downloading Map Info", this.mapImagesDownloaded, this.totalMaps);
        } else {
            this.drawStatus("Downloading Map: " + this.mapName, this.downloadedAmount, this.downloadSize);
            if (this.downloadSize != 0) {
                float downloadedAmountMegs = (float) this.downloadedAmount / 1024.0f / 1024.0f;
                float downloadSizeMegs = (float) this.downloadSize / 1024.0f / 1024.0f;
                String downloaded = String.format("Downloaded %.2f/%.2f MBs", downloadedAmountMegs, downloadSizeMegs);
                int w = this.textManager.getTextWidth(downloaded);
                this.textManager.drawTextWithoutShadow(downloaded, this.width / 2 - w / 2, this.height / 2 + 15, 0xFFFFFF);
            }
        }
    }

    private void drawStatus(String s, int cur, int total) {
        int w = this.textManager.getTextWidth(s);
        this.textManager.drawTextWithoutShadow(s, this.width / 2 - w / 2, this.height / 2 - 4, 0xFFFFFF);
        this.fill(this.width / 2 - 50, this.height / 2 + 5, this.width / 2 + 50, this.height / 2 + 13, Integer.MIN_VALUE);
        if (total > 0) {
            int xOffset = (int) (100.0 * (double) cur / (double) total - 50.0);
            this.fill(this.width / 2 - 50, this.height / 2 + 5, this.width / 2 + xOffset, this.height / 2 + 13, -15675632);
        }
    }

    @Override
    public void init(Minecraft minecraft, int width, int height) {
        super.init(minecraft, width, height);
        this.ui.setX(width / 2 - 152);
        this.scrollBarX = 1 + this.width / 2 + 152;
        this.maxOffset = Math.min(this.height - 32 - 102 * (this.maps.size() + 2) / 3, 26);
        if (this.ui.curY < (float) this.maxOffset) {
            this.ui.setY(this.maxOffset);
        }
    }

    private GuiMapElement addMap(String title, String topDescription, String description, String texture, String mURL, int mapID, int totalRating, int numRatings) {
        int i = this.maps.size();
        GuiMapElement r = new GuiMapElement(102 * (i % 3), 102 * (i / 3), this.ui, title, topDescription, description, texture, mURL, mapID, totalRating, numRatings);
        this.maps.add((Object) r);
        this.maxOffset = Math.min(this.height - 32 - 102 * (this.maps.size() + 2) / 3, 26);
        return r;
    }

    private GuiMapElement getMapAtCoord(int x, int y) {
        for (GuiMapElement m : this.maps) {
            int dX = (int) ((float) x - (m.curX + this.ui.curX));
            int dY = (int) ((float) y - (m.curY + this.ui.curY));
            if (dX < 0 || dX >= 100 || dY < 0 || dY >= 100) continue;
            return m;
        }
        return null;
    }

    private void downloadAndLoadMapInfo() {
        File mapInfo = new File(this.mapDownloadFolder, "mapInfo.txt");
        if (mapInfo.exists()) {
            try {
                BufferedReader input = new BufferedReader(new FileReader(mapInfo));
                ArrayList lines = new ArrayList();
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
                            if (!first) {
                                topDescription = topDescription + "\n";
                            }
                            topDescription = topDescription + "Downloads: " + parts[8];
                            first = false;
                        }
                        int totalRating = new Integer(parts[10]);
                        int numRatings = new Integer(parts[11]);
                        GuiMapElement map = this.addMap(mapName, topDescription, mapDescription, "./mapDownloads/" + mapName + ".png", mapURL, mapID, totalRating, numRatings);
                        File mFolder = new File("./maps/" + map.mapName);
                        if (mFolder.exists()) {
                            map.setAsDownloaded();
                        }
                    }
                    ++this.mapImagesDownloaded;
                }
            } catch (IOException e) {
            }
        }
        this.downloading = false;
    }

    private void deleteFilesInFolder(File dir) {
        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
                this.deleteFilesInFolder(f);
            }
            f.delete();
        }
    }

    private void downloadMap() {
        String[] dropboxIDs = new String[]{"51083669", "51083634", "51083701", "51083780"};
        this.downloadFile(this.mapUrl.replace("51083780", dropboxIDs[this.rand.nextInt(4)]), "./mapDownloads/map.zip");
        File mapZip = new File(this.mapDownloadFolder, "map.zip");
        File mapDir = new File("./maps/" + this.mapName);
        if (mapDir.exists()) {
            if (mapDir.isDirectory()) {
                this.deleteFilesInFolder(mapDir);
            }
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
                        int n;
                        f.createNewFile();
                        FileOutputStream fileoutputstream = new FileOutputStream(f);
                        while ((n = zipinputstream.read(buf, 0, 8192)) > -1) {
                            fileoutputstream.write(buf, 0, n);
                        }
                        fileoutputstream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    f.mkdirs();
                }
                zipinputstream.closeEntry();
                zipentry = zipinputstream.getNextEntry();
            }
        } catch (IOException e) {
            this.mapName = null;
        }
        mapZip.delete();
        this.downloadingMapElement.setAsDownloaded();
        this.downloading = false;
        this.downloadingMap = false;
    }

    private void startMap() {
        File worldDir;
        String saveName = "";
        File mcDir = Minecraft.getGameDirectory();
        File saveDir = new File(mcDir, "saves");
        int i = 1;
        do {
            saveName = String.format("%s - Save %d", this.mapName, i);
            worldDir = new File(saveDir, saveName);
            ++i;
        } while (worldDir.exists());
        this.minecraft.saveMapUsed(saveName, this.mapName);
        this.minecraft.interactionManager = new class_520(this.minecraft);
        this.minecraft.startWorld(saveName, saveName, 0L, this.mapName);
    }

    private boolean downloadFile(String downloadSite, String outputFileName) {
        try {
            int buffersize;
            URL url = new URL(downloadSite);
            URLConnection urlconnection = url.openConnection();
            urlconnection.connect();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            File output = new File(outputFileName);
            output.mkdirs();
            if (output.exists()) {
                output.delete();
            }
            output.createNewFile();
            FileOutputStream fos = new FileOutputStream(output);
            bis = new BufferedInputStream(urlconnection.getInputStream());
            bos = new BufferedOutputStream(fos);
            this.downloadedAmount = 0;
            this.downloadSize = urlconnection.getContentLength();
            byte[] buffer = new byte[65536];
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
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        float f = 32.0f;
        tessellator.start();
        tessellator.colour(0x404040, bottomAlpha);
        tessellator.vertex(0.0, bottom, 0.0, 0.0, (float) bottom / f);
        tessellator.vertex(this.width, bottom, 0.0, (float) this.width / f, (float) bottom / f);
        tessellator.colour(0x404040, topAlpha);
        tessellator.vertex(this.width, top, 0.0, (float) this.width / f, (float) top / f);
        tessellator.vertex(0.0, top, 0.0, 0.0, (float) top / f);
        tessellator.draw();
    }
}
