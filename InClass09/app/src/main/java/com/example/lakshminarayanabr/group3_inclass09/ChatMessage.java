package com.example.lakshminarayanabr.group3_inclass09;

/**
 * Created by lakshminarayanabr on 10/31/16.
 */

public class ChatMessage {
    String status;

    public ChatMessage(){
        message = new ChatMessageDetail();
    }
    public ChatMessageDetail getMessage() {
        return message;
    }

    public void setMessage(ChatMessageDetail message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    ChatMessageDetail message;





    @Override
    public String toString() {
        return "status :"+ status+message.toString();
    }
}
