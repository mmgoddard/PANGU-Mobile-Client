package com.pangu.mobile.client.domain;

import uk.ac.dundee.spacetech.pangu.ClientLibrary.Vector3D;


/**
 * Created by Mark on 20/01/15.
 */
public class ViewPointModel {
    private Vector3D vector3D;
    private double yawAngle = 0.0;
    private double pitchAngle = 0.0;
    private double rollAngle = 0.0;
    private int step = 0;

    public ViewPointModel(Vector3D v, double yaw, double pitch, double roll, int step) {
        this.vector3D = v;
        this.yawAngle = yaw;
        this.pitchAngle = pitch;
        this.rollAngle = roll;
        this.step = step;
    }

    //VECTOR 3D
    public Vector3D getVector3D() {
        return vector3D;
    }
    public void setVector3D(Vector3D vector3D) {
        this.vector3D = vector3D;
    }

    //YAW ANGLE
    public double getYawAngle() {
        return yawAngle;
    }
    public void setYawAngle(double yawAngle) {
        this.yawAngle = yawAngle;
    }
    public void adjustYawAngle(double v) {
        yawAngle -= v;
    }

    //PITCH ANGLE
    public double getPitchAngle() {
        return pitchAngle;
    }
    public void setPitchAngle(double pitchAngle) {
        this.pitchAngle = pitchAngle;
    }
    public void adjustPitchAngle(double v) {
        pitchAngle += v;
    }

    //ROLL ANGLE
    public double getRollAngle() {
        return rollAngle;
    }
    public void setRollAngle(double rollAngle) {
        this.rollAngle = rollAngle;
    }
    public void adjustRollAngle(double v) {
        rollAngle += v;
    }

    public int getStep() { return step; }
    public void setStep(int step) { this.step = step; }

    //ORGIN
    public void setOrigin(Vector3D v) { vector3D = v; }
    public Vector3D getOrigin() { return vector3D; }
    public void adjustOrigin(Vector3D v) { vector3D = vector3D.add(v); }
}