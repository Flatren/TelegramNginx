package com.nginxtelegrm.NginxTelegramMessage.core.repositoryes;

import com.nginxtelegrm.NginxTelegramMessage.core.store.StoreMessagesToSendOnChat;
import com.nginxtelegrm.NginxTelegramMessage.modeles.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MessageSendRepository {

    private final HashMap<Long, StoreMessagesToSendOnChat> dictChatMessages;
    private final Object lock;
    private final long timeInMsSendMessage;

    public MessageSendRepository(@Value("${bot.time_in_second_send_message}") long timeInSecondSendMessage){
        this.timeInMsSendMessage = timeInSecondSendMessage*1000;
        this.dictChatMessages = new HashMap<>();
        this.lock = new Object();
    }

    public void insert(Message sendMessage) {
        insert(sendMessage, false);
    }

    public void insert(Message sendMessage, boolean isFast){
        synchronized (lock){
            StoreMessagesToSendOnChat storeMessagesToSendOnChat = dictChatMessages.get(sendMessage.getIdChat());
            if (storeMessagesToSendOnChat == null){
                storeMessagesToSendOnChat = new StoreMessagesToSendOnChat();
                dictChatMessages.put(sendMessage.getIdChat(), storeMessagesToSendOnChat);
            }
            storeMessagesToSendOnChat.addMessage(sendMessage, isFast);
        }
    }

    public List<Message> getMessageToSend() {
        List<Message> messages = new ArrayList<>();
        synchronized (lock){
            dictChatMessages.values().forEach(item->{
                        item.getMessageSend(timeInMsSendMessage)
                                .ifPresent(messages::add);
                }
            );
        }
        return messages;
    }
}
