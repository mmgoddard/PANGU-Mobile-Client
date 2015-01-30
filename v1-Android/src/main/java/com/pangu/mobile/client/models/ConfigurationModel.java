package com.pangu.mobile.client.models;

/**
 * Created by Mark on 30/01/15.
 */
public class ConfigurationModel {
    private int id;
    private String name;
    private String ipAddress;
    private String portNum;

    public ConfigurationModel(int id, String name, String ipAddress, String portNum) {
        this.id = id;
        this.name = name;
        this.ipAddress = ipAddress;
        this.portNum = portNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
