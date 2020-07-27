package com.bala.pranks;

public class User {
    public User() {
    }

    public User(String name, String email) {
        Name = name;
        Email = email;
    }

    private String Name, Email;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
