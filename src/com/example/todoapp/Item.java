package com.example.todoapp;

import java.sql.Date;

public class Item {
    private long id;
    private String action;
    private Date dueDate;
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    
    @Override
    public String toString() {
        return action + ", " + dueDate;
    }
}
