package com.nginxtelegrm.NginxTelegramMessage.core.store;

import com.nginxtelegrm.NginxTelegramMessage.core.modeles.Message;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

public class StoreMessagesToSendOnChat {
    ConcurrentLinkedQueue<Message> sendMessageConcurrentLinkedQueue;
    ConcurrentLinkedQueue<Message> fastSendMessageConcurrentLinkedQueue;
    long lastTimeSendMessage;

    public StoreMessagesToSendOnChat(){
        sendMessageConcurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        fastSendMessageConcurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        lastTimeSendMessage = System.currentTimeMillis() - 3000;
    }

    public Optional<Message> getMessageSend(long offsetTimeMs){
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

    public void addMessage(Message sendMessage, boolean isFast){
        if (isFast){
            fastSendMessageConcurrentLinkedQueue.add(sendMessage);
        }
        else {
            sendMessageConcurrentLinkedQueue.add(sendMessage);
        }
    }
}
