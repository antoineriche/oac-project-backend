package com.gaminho.oacproject.model;

import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Song {

    @Id
    @GeneratedValue
    private long id;
    private String title;
    private Date creationDate;

    public Song() {
    }

    public Song(long id, String title, Date creationDate) {
        this.id = id;
        this.title = title;
        this.creationDate = creationDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }

    public static boolean isValid(Song songToValidate){
        return songToValidate != null
                && StringUtils.isNotEmpty(songToValidate.getTitle());
    }
}
