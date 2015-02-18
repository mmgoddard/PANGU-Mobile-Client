package com.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Mark on 23/01/15.
 */
@Entity
@Table(name = "pangu_model")
public class PanguModel implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Column
    private String name;
    @Column
    private String discoveredBy;
    @Column
    private String discoveryDate;
    @Column
    private String size;
    @Column
    private String approximateMass;
    @Column
    private String orbitalPeriod;
    @Column
    private String description;
    @Column
    private String comments;
    @Column
    private int port;

    public PanguModel() {}

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
