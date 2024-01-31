package com.nginxtelegrm.NginxTelegramMessage.repository;

import com.nginxtelegrm.NginxTelegramMessage.model.pojo.Message;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ChatMessagesRepository {
    private final ConcurrentLinkedQueue<Message> sendMessageConcurrentLinkedQueue;
    private final ConcurrentLinkedQueue<Message> fastSendMessageConcurrentLinkedQueue;

    private long lastTimeSendMessage;

    public ChatMessagesRepository(){
        sendMessageConcurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        fastSendMessageConcurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        lastTimeSendMessage = System.currentTimeMillis() - 3000;
    }

    public Optional<Message> getMessageResponse(long offsetTimeMs){
        if (System.currentTimeMillis() - lastTimeSendMessage > offsetTimeMs) {
            if (!fastSendMessageConcurrentLinkedQueue.isEmpty()) {
                return Optional.ofNullable(fastSendMessageConcurrentLinkedQueue.poll());
            }
            if (!sendMessageConcurrentLinkedQueue.isEmpty()) {
                return Optional.ofNullable(sendMessageConcurrentLinkedQueue.poll());
            }
            lastTimeSendMessage = System.currentTimeMillis();
        }
        return Optional.empty();
    }

    public void addMessageResponse(Message message){
        switch (message.getMessage().getPriorityMessage()){
            case USUAL -> sendMessageConcurrentLinkedQueue.add(message);
            case IMPORTANT -> fastSendMessageConcurrentLinkedQueue.add(message);
        }
    }
}
