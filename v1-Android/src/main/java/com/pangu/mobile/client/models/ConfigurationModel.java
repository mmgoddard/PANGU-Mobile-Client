package com.pangu.mobile.client.models;

/**
 * Created by Mark on 30/01/15.
 */
public class ConfigurationModel extends BaseConfigurationModel {
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
}
