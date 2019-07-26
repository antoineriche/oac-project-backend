package com.gaminho.oacproject.model;

import com.fasterxml.jackson.annotation.JsonFilter;

import java.util.Date;

//@JsonIgnoreProperties(value = {"creationDate", "id"})
@JsonFilter("todoFilter")
public class Todo2 {

    private long id;
    private long userId;
    private String title;
    private boolean completed;
    private Date creationDate;

    public Todo2() {
    }

    public Todo2(long id, long userId, String title, boolean completed, Date creationDate) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.completed = completed;
        this.creationDate = creationDate;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                ", creationDate=" + creationDate +
                '}';
    }
}
