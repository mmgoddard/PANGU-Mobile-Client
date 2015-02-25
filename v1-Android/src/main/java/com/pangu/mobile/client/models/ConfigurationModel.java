package com.pangu.mobile.client.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mark on 30/01/15.
 */
public class ConfigurationModel extends BaseConfigurationModel implements Parcelable {
    private String ipAddress;
    private String portNum;

    public ConfigurationModel(String name, String ipAddress, String portNum) {
        this.name = name;
        this.ipAddress = ipAddress;
        this.portNum = portNum;
    }

    public ConfigurationModel(int id, String name, String ipAddress, String portNum) {
        this.id = id;
        this.name = name;
        this.ipAddress = ipAddress;
        this.portNum = portNum;
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

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(name);
        out.writeString(ipAddress);
        out.writeString(portNum);
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
    }
}
