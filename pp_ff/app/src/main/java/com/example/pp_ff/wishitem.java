package com.example.pp_ff;

public class wishitem {

    private String Name;
    private String Type;
    private String WID;
    private String UID;

    public wishitem() {
    }

    public wishitem(String Name,String Type, String WID,String UID) {

        this.Name = Name;
        this.Type = Type;
        this.WID = WID;
        this.UID = UID;

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getWID() {
        return WID;
    }

    public void setWID(String WID) {
        this.WID = WID;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}


