package com.pangu.mobile.client.domain;

import android.os.Parcel;
import android.os.Parcelable;
import uk.ac.dundee.spacetech.pangu.ClientLibrary.Vector3D;

/**
 * Created by Mark on 30/01/15.
 */
public class ConfigurationModel extends BaseConfigurationModel implements Parcelable {
    private String ipAddress;
    private String portNum;
    private ViewPoint viewPoint;
    private String saved;

    public ConfigurationModel(String name, String ipAddress, String portNum, ViewPoint viewPoint, String saved) {
        this.name = name;
        this.ipAddress = ipAddress;
        this.portNum = portNum;
        this.viewPoint = viewPoint;
        this.saved = saved;
    }

    public ConfigurationModel(int id, String name, String ipAddress, String portNum) {
        this.id = id;
        this.name = name;
        this.ipAddress = ipAddress;
        this.portNum = portNum;
    }

    public ConfigurationModel(int id, String name, String ipAddress, String portNum, ViewPoint viewPoint, String saved) {
        this.id = id;
        this.name = name;
        this.ipAddress = ipAddress;
        this.portNum = portNum;
        this.viewPoint = viewPoint;
        this.saved = saved;
    }

    public String getIpAddress() {
        return ipAddress;
    }
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPortNum() {
        return portNum;
    }
    public void setPortNum(String portNum) {
        this.portNum = portNum;
    }

    public ViewPoint getViewPoint() { return viewPoint; }
    public void setViewPoint(ViewPoint viewPoint) { this.viewPoint = viewPoint; }

    public String getSaved() { return saved; }
    public void setSaved(String saved) { this.saved = saved; }

    //Define Parcelable Interface
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(name);
        out.writeString(ipAddress);
        out.writeString(portNum);
        out.writeDouble(viewPoint.getVector3D().i);
        out.writeDouble(viewPoint.getVector3D().j);
        out.writeDouble(viewPoint.getVector3D().k);
        out.writeDouble(viewPoint.getYawAngle());
        out.writeDouble(viewPoint.getPitchAngle());
        out.writeDouble(viewPoint.getRollAngle());
        out.writeDouble(viewPoint.getStep());
        out.writeString(saved);
    }

    public static final Parcelable.Creator<ConfigurationModel> CREATOR
            = new Parcelable.Creator<ConfigurationModel>() {
        public ConfigurationModel createFromParcel(Parcel in) {
            return new ConfigurationModel(in);
        }

        public ConfigurationModel[] newArray(int size) {
            return new ConfigurationModel[size];
        }
    };

    private ConfigurationModel(Parcel in){
        this.id = in.readInt();
        this.name = in.readString();
        this.ipAddress = in.readString();
        this.portNum = in.readString();
        double i = in.readDouble();
        double j = in.readDouble();
        double k = in.readDouble();
        double yaw = in.readDouble();
        double pitch = in.readDouble();
        double roll = in.readDouble();
        double step = in.readDouble();
        Vector3D vec3 = new Vector3D(i, j, k);
        this.viewPoint = new ViewPoint(vec3, yaw, pitch, roll, step);
        this.saved = in.readString();
    }
}
