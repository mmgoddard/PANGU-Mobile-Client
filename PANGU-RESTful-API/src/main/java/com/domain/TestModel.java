package com.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Mark on 23/01/15.
 */
@Entity
@Table(name = "test_model")
public class TestModel implements Serializable {
    @Id
    private long id;
    @Column
    private String content;

    public TestModel() {}

    public long getId() {
        return id;
    }
    public void setId(long id) { this.id = id; }

    public String getContent() {
        return content;
    }
    public void setContent(String content) { this.content = content; }

}
