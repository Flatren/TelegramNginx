package com.nginxtelegrm.NginxTelegramMessage.core.collection;

import com.nginxtelegrm.NginxTelegramMessage.modeles.Message;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;


public class ChatMessagesOnSend {

    ConcurrentLinkedQueue<Message> sendMessageConcurrentLinkedQueue;
    ConcurrentLinkedQueue<Message> fastSendMessageConcurrentLinkedQueue;
    long lastTimeSendMessage;

    public ChatMessagesOnSend(){
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
