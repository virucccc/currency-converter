package com.virucccc.converter.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "valutes")
public class Valute {
    @Id
    @Column(nullable=false)
    private Integer id;
    private String fullname;
    @Column(length = 3)
    private String shortname;
    @Transient
    private float value;

    public Valute() {
    }

    public Valute(Integer id, String fullname, String shortname) {
        this.id = id;
        this.fullname = fullname;
        this.shortname = shortname;
    }

    public Valute(Integer id, String fullname, String shortname, float value) {
        this.id = id;
        this.fullname = fullname;
        this.shortname = shortname;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getName() {
        return shortname + " (" + fullname + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Valute valute = (Valute) o;
        return Objects.equals(id, valute.id) &&
                Objects.equals(fullname, valute.fullname) &&
                Objects.equals(shortname, valute.shortname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullname, shortname);
    }
}
