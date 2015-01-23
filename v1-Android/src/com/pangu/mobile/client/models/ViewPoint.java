package com.pangu.mobile.client.models;

import uk.ac.dundee.spacetech.pangu.ClientLibrary.Vector3D;


/**
 * Created by Mark on 20/01/15.
 */
public class ViewPoint {
    private Vector3D vector3D;
    private double yawAngle;
    private double pitchAngle;
    private double rollAngle;

    public ViewPoint(Vector3D vector3D, double yawAngle, double pitchAngle, double rollAngle) {
        this.vector3D = vector3D;
        this.yawAngle = yawAngle;
        this.pitchAngle = pitchAngle;
        this.rollAngle = rollAngle;
    }

    public Vector3D getVector3D() {
        return vector3D;
    }

    public void setVector3D(Vector3D vector3D) {
        this.vector3D = vector3D;
    }

    public double getYawAngle() {
        return yawAngle;
    }

    public void setYawAngle(double yawAngle) {
        this.yawAngle = yawAngle;
    }

    public double getPitchAngle() {
        return pitchAngle;
    }

    public void setPitchAngle(double pitchAngle) {
        this.pitchAngle = pitchAngle;
    }

    public double getRollAngle() {
        return rollAngle;
    }

    public void setRollAngle(double rollAngle) {
        this.rollAngle = rollAngle;
    }
}
