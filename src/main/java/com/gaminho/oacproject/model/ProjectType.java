package com.gaminho.oacproject.model;

import org.apache.commons.lang.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class ProjectType {

    @Id
    @GeneratedValue
    private long id;

    //FIXME bug not unique
    @Column(nullable = false, unique = true)
    private String label;

    public ProjectType() {
    }

    public ProjectType(long id, String label) {
        this.id = id;
        this.label = label;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "ProjectType{" +
                "id=" + id +
                ", label='" + label + '\'' +
                '}';
    }

    public static boolean isValid(ProjectType typeToValidate){
        return typeToValidate != null
                && StringUtils.isNotEmpty(typeToValidate.getLabel());
    }

}
