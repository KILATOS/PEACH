package org.peach.app.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class User {

    private long id;
    @NotEmpty(message =  "Name shouldn't be empty")
    @Size(min = 2, max = 30, message = "Invalid size of name")
    private String name;
    @NotEmpty (message =  "Address shouldn't be empty")
    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}", message = "Your address should be like this: \"Country, City, 000000\"")
    private String address;

    public User(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.address = email;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
