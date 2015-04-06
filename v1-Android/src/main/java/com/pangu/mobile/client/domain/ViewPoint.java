package com.pangu.mobile.client.domain;

import uk.ac.dundee.spacetech.pangu.ClientLibrary.Vector3D;


/**
 * Created by Mark on 20/01/15.
 */
public class ViewPoint {
    private Vector3D vector3D;
    private double yawAngle = 0.0;
    private double pitchAngle = 0.0;
    private double rollAngle = 0.0;
    private double step = 0.0;

    public ViewPoint(Vector3D v, double y, double p, double r, double step) {
        this.vector3D = v;
        this.yawAngle = Math.toRadians(y);
        this.pitchAngle = Math.toRadians(p);
        this.rollAngle = Math.toRadians(r);
        normalise();
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
        return Math.toDegrees(yawAngle);
    }
    public void setYawAngle(double yawAngle) {
        this.yawAngle = Math.toRadians(yawAngle);
        normalise();
    }
    public void adjustYawAngle(double v) {
        yawAngle += Math.toRadians(v);
        normalise();
    }

    //PITCH ANGLE
    public double getPitchAngle() {
        return Math.toDegrees(pitchAngle);
    }
    public void setPitchAngle(double pitchAngle) {
        this.pitchAngle = Math.toRadians(pitchAngle);
        normalise();
    }
    public void adjustPitchAngle(double v) {
        pitchAngle += Math.toRadians(v);
        normalise();
    }

    //ROLL ANGLE
    public double getRollAngle() {
        return Math.toDegrees(rollAngle);
    }
    public void setRollAngle(double rollAngle) {
        this.rollAngle = Math.toRadians(rollAngle);
        normalise();
    }
    public void adjustRollAngle(double v) {
        rollAngle += Math.toRadians(v);
        normalise();
    }

    public double getStep() { return step; }
    public void setStep(double step) { this.step = step; }

    //ORGIN
    public void setOrigin(Vector3D v) { vector3D = v; }
    public Vector3D getOrigin() { return vector3D; }
    public void adjustOrigin(Vector3D v) { vector3D = vector3D.add(v); }

    private void normalise() {
        // Useful constants.
        double M_180_DEG = Math.PI;
        double M_360_DEG = 2*M_180_DEG;

        // Normalise the yaw angle to [0, 360)
        while (yawAngle < 0) yawAngle += M_360_DEG;
        while (yawAngle >= M_360_DEG) yawAngle -= M_360_DEG;

        // Normalise the pitch angle to (-180, 180]
        while (pitchAngle - M_180_DEG <= -M_360_DEG) pitchAngle += M_360_DEG;
        while (pitchAngle + M_180_DEG > M_360_DEG) pitchAngle -= M_360_DEG;

        // Normalise the roll angle to (-180, 180]
        while (rollAngle - M_180_DEG <= -M_360_DEG) rollAngle += M_360_DEG;
        while (rollAngle + M_180_DEG > M_360_DEG) rollAngle -= M_360_DEG;
    }
}