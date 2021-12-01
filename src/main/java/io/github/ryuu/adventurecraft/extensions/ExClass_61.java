package io.github.ryuu.adventurecraft.extensions;

import net.minecraft.class_108;
import net.minecraft.entity.Entity;
import net.minecraft.util.maths.Vec3i;

public interface ExClass_61 {

    class_108 getP();

    void setP(class_108 p);

    Vec3i getClearSize();

    void setClearSize(Vec3i clearSize);

    boolean needNewPath(Entity entity);
}
