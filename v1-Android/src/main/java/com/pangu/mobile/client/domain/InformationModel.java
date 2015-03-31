package com.pangu.mobile.client.domain;

/**
 * Created by Mark on 23/02/15.
 */
public class InformationModel extends BaseConfigurationModel{
    private String discoveredBy, discoveryDate, size, approximateMass, orbitalPeriod, description, comments;

    public InformationModel() {}
    public InformationModel(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDiscoveredBy() {
        return discoveredBy;
    }

    public void setDiscoveredBy(String discoveredBy) {
        this.discoveredBy = discoveredBy;
    }

    public String getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public void setOrbitalPeriod(String orbitalPeriod) {
        this.orbitalPeriod = orbitalPeriod;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApproximateMass() {
        return approximateMass;
    }

    public void setApproximateMass(String approximateMass) {
        this.approximateMass = approximateMass;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDiscoveryDate() {
        return discoveryDate;
    }

    public void setDiscoveryDate(String discoveryDate) {
        this.discoveryDate = discoveryDate;
    }
}
