package com.example.lakshminarayanabr.group3_inclass09;

import java.util.List;

/**
 * Created by lakshminarayanabr on 10/31/16.
 */

public class Messages {
    List<ChatMessageDetail> messages;

    public List<ChatMessageDetail> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessageDetail> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Messages{" +
                "messages=" + messages +
                '}';
    }
}
