package com.pangu.mobile.client.domain;

/**
 * Created by Mark on 30/01/15.
 */
public class BaseConfigurationModel {
    public int id;
    public String name;

    public BaseConfigurationModel() {}

    public BaseConfigurationModel(String name) {
        this.name = name;
    }

    public BaseConfigurationModel(int id, String name) {
        this.id = id;
        this.name = name;
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
}
