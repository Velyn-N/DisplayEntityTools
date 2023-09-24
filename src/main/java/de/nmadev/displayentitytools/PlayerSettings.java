package de.nmadev.displayentitytools;

import de.nmadev.displayentitytools.position.PositionModificationType;

public class PlayerSettings {

    private double xMod = 1;
    private double yMod = 1;
    private double zMod = 1;
    private double rotationMod = 5;
    private double tiltMod = 1;

    public double getModByModType(PositionModificationType type) {
        return switch (type) {
            case X_AXIS -> xMod;
            case Y_AXIS -> yMod;
            case Z_AXIS -> zMod;
            case ROTATION -> rotationMod;
            case TILT -> tiltMod;
        };
    }

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

    public double getTiltMod() {
        return tiltMod;
    }

    public void setTiltMod(double tiltMod) {
        this.tiltMod = tiltMod;
    }
}
