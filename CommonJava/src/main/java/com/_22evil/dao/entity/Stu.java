package com._22evil.dao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Table(name = "stu")
public class Stu implements Serializable {
    private static final long serialVersionUID = -1L;
    private long id;
    private String name;

    
    public void setId(long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false, length = 36)
    public long getId() {
        return id;
    }

    @Column(name = "name", length = 100)
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}