package de.nmadev.displayentitytools;

public class PlayerSettings {

    private double xMod = 1;
    private double yMod = 1;
    private double zMod = 1;
    private double rotationMod = 1;

    public double getXMod() {
        return xMod;
    }

    public void setXMod(double xMod) {
        this.xMod = xMod;
    }

    public double getYMod() {
        return yMod;
    }

    public void setYMod(double yMod) {
        this.yMod = yMod;
    }

    public double getZMod() {
        return zMod;
    }

    public void setZMod(double zMod) {
        this.zMod = zMod;
    }

    public double getRotationMod() {
        return rotationMod;
    }

    public void setRotationMod(double rotationMod) {
        this.rotationMod = rotationMod;
    }
}
