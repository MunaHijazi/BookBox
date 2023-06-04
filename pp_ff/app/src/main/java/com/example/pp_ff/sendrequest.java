package com.example.pp_ff;

public class sendrequest {
    String resourceId,receiverId,senderId,requestType,uid;

    public sendrequest(){

    }

    public sendrequest(String resourceId, String receiverId, String senderId, String requestType,String uid) {
        this.resourceId = resourceId;
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.requestType = requestType;
        this.uid=uid;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getRecciverId() {
        return receiverId;
    }

    public void setRecciverId(String recciverId) {
        this.receiverId = recciverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
