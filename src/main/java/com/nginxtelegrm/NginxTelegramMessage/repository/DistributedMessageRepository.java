package com.nginxtelegrm.NginxTelegramMessage.repository;

import com.nginxtelegrm.NginxTelegramMessage.model.pojo.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class DistributedMessageRepository {

    private final HashMap<Long, ChatMessagesRepository> dictChatMessages;
    private final Object lock;
    private final long timeInMsSendMessage;

    public DistributedMessageRepository(@Value("${bot.time_in_second_send_message}") double timeInSecondSendMessage){
        this.timeInMsSendMessage = (long) (timeInSecondSendMessage * 1000.0);
        this.dictChatMessages = new HashMap<>();
        this.lock = new Object();
    }

    public void add(Message message){
        synchronized (lock){
            ChatMessagesRepository chatMessagesRepository = dictChatMessages.get(message.getMessage().getIdChat());
            if (chatMessagesRepository == null){
                chatMessagesRepository = new ChatMessagesRepository();
                dictChatMessages.put(message.getMessage().getIdChat(), chatMessagesRepository);
            }
            chatMessagesRepository.addMessageResponse(message);
        }
    }

    public List<Message> get() {
        List<Message> messages = new ArrayList<>();
        synchronized (lock){
            dictChatMessages.values().forEach(item->{
                        item.getMessageResponse(timeInMsSendMessage)
                                .ifPresent(messages::add);
                    }
            );
        }
        return messages;
    }
}
