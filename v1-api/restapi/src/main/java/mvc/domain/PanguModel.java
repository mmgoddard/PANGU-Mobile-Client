package mvc.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Mark on 23/01/15.
 */
@Entity
@Table(name = "pangu_model")
public class PanguModel implements Serializable {
    @Id
    @GeneratedValue
    private int id;
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

    public void setId(int id) {
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

    public void setDiscoveredBy(String discovered_by) {
        this.discoveredBy = discovered_by;
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

    public void setApproximateMass(String approximate_mass) {
        this.approximateMass = approximate_mass;
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

    public void setDiscoveryDate(String discovery_date) {
        this.discoveryDate = discovery_date;
    }
}