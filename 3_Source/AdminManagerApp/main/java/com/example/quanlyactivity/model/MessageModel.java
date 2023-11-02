package com.example.quanlyactivity.model;

public class MessageModel {
    boolean success;
    String message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess(){
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }



}
