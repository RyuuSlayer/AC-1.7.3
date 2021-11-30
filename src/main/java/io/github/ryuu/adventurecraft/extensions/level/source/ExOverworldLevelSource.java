package io.github.ryuu.adventurecraft.extensions.level.source;

import net.minecraft.level.source.LevelSource;

public interface ExOverworldLevelSource extends LevelSource {

    double getMapSize();

    void setMapSize(double mapSize);

    int getWaterLevel();

    void setWaterLevel(int waterLevel);

    double getFractureHorizontal();

    void setFractureHorizontal(double fractureHorizontal);

    double getFractureVertical();

    void setFractureVertical(double fractureVertical);

    double getMaxAvgDepth();

    void setMaxAvgDepth(double maxAvgDepth);

    double getMaxAvgHeight();

    void setMaxAvgHeight(double maxAvgHeight);

    double getVolatility1();

    void setVolatility1(double volatility1);

    double getVolatility2();

    void setVolatility2(double volatility2);

    double getVolatilityWeight1();

    void setVolatilityWeight1(double volatilityWeight1);

    double getVolatilityWeight2();

    void setVolatilityWeight2(double volatilityWeight2);
}
