package com.gaminho.oacproject.model;

import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class MC {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String punchline;
    private String description;
    private String imageUrl;
    private Date addingDate;

    public MC() {
    }

    public MC(long id, String name, String punchline, String description, String imageUrl, Date addingDate) {
        this.id = id;
        this.name = name;
        this.punchline = punchline;
        this.description = description;
        this.imageUrl = imageUrl;
        this.addingDate = addingDate;
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

    public String getPunchline() {
        return punchline;
    }

    public void setPunchline(String punchline) {
        this.punchline = punchline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAddingDate() {
        return addingDate;
    }

    public void setAddingDate(Date addingDate) {
        this.addingDate = addingDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "MC{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", punchline='" + punchline + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", addingDate=" + addingDate +
                '}';
    }

    public static boolean isValid(MC mcToValidate){
        return mcToValidate != null
                && StringUtils.isNotEmpty(mcToValidate.getName());
    }

}
