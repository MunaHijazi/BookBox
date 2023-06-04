package com.example.pp_ff;

public class Resources {
    public String name;
    public String uid;
    public String type;
    public String year;
    public String instructor;
    public String ruid;

    public Resources(){

    }

    public Resources(String name, String uid, String type, String year, String instructor, String ruid) {
        this.name = name;
        this.uid = uid;
        this.ruid = ruid;
        this.type = type;
        this.year = year;
        this.instructor = instructor;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getuid() {
        return uid;
    }

    public void setuid(String uid) { this.uid = uid; }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getRuid() {
        return ruid;
    }

    public void setRuid(String ruid) {
        this.ruid = ruid;
    }
}
