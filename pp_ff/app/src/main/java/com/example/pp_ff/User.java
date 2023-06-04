package com.example.pp_ff;

public class User {
    private String Name;
    private String Password;
    private String Email;
    private String Uid;

    public User() {
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public User(String email, String password) {
        this.Email = email;
        this.Password = password;
    }

    public User(String name, String password, String email) {
        Name = name;
        Password = password;
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}