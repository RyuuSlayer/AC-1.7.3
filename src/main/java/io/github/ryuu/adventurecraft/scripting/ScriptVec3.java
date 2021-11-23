package io.github.ryuu.adventurecraft.scripting;

public class ScriptVec3 {

    public double x;

    public double y;

    public double z;

    public ScriptVec3(double setX, double setY, double setZ) {
        this.x = setX;
        this.y = setY;
        this.z = setZ;
    }

    public ScriptVec3(float setX, float setY, float setZ) {
        this.x = setX;
        this.y = setY;
        this.z = setZ;
    }

    public ScriptVec3 add(ScriptVec3 other) {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
        return this;
    }

    public ScriptVec3 subtract(ScriptVec3 other) {
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
        return this;
    }

    public double length() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public double distance(ScriptVec3 other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        double dz = this.z - other.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public ScriptVec3 scale(double s) {
        this.x *= s;
        this.y *= s;
        this.z *= s;
        return this;
    }
}
