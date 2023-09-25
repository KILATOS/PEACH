package org.peach.app.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class User {

    private long id;
    @NotEmpty(message =  "Name shouldn't be empty")
    @Size(min = 2, max = 30, message = "Invalid size of name")
    private String name;

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public User() {
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
}
