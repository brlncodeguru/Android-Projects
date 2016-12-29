package com.example.lakshminarayanabr.group3_inclass09;

/**
 * Created by lakshminarayanabr on 11/1/16.
 */

public class AddTextorImageToList {


    String status;

    AddMessageDetail message;


    @Override
    public String toString() {
        return "AddTextorImageToList{" +
                "status='" + status + '\'' +
                ", message=" + message +
                '}';
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AddMessageDetail getMessage() {
        return message;
    }

    public void setMessage(AddMessageDetail message) {
        this.message = message;
    }
}
