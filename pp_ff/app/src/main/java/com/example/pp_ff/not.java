package com.example.pp_ff;

public class not {
    private String uid;
    private String sender;
    private String receiver;
    private String num;
    private String bookname;
    private String booktype;
    private String ruid;


    public not(){

    }

    public not(String uid, String sender, String receiver, String num, String bookname, String booktype, String ruid) {
        this.uid = uid;
        this.sender = sender;
        this.receiver = receiver;
        this.num = num;
        this.bookname = bookname;
        this.booktype = booktype;
        this.ruid = ruid;

    }



    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getBooktype() {
        return booktype;
    }

    public void setBooktype(String booktype) {
        this.booktype = booktype;
    }

    public String getRuid() {
        return ruid;
    }
    public void setRuid(String ruid) {
        this.ruid = ruid;
    }




}