package com.nginxtelegrm.NginxTelegramMessage.core.collection;

import com.nginxtelegrm.NginxTelegramMessage.core.service.MessengerService;
import com.nginxtelegrm.NginxTelegramMessage.modeles.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.util.*;


@Component
public class SendMessageCollection {

     MessengerService messengerService;

    private final Dictionary<Long, ChatMessagesOnSend> dictChatMessages;
    private final Object lock;
    private final long timeInMsSendMessage;

    public void setMessengerService(MessengerService messengerService) {
        this.messengerService = messengerService;
    }

    public SendMessageCollection(@Value("${bot.time_in_second_send_message}") long timeInSecondSendMessage){
        this.timeInMsSendMessage = timeInSecondSendMessage*1000;
        dictChatMessages = new Hashtable<>();
        lock = new Object();
    }

    public void addMessage(Message sendMessage, boolean isFast){
        synchronized (lock){
            ChatMessagesOnSend chatMessagesOnSend = dictChatMessages.get(sendMessage.getIdChat());
            if (chatMessagesOnSend == null){
                chatMessagesOnSend = new ChatMessagesOnSend();
                dictChatMessages.put(sendMessage.getIdChat(), chatMessagesOnSend);
            }
            chatMessagesOnSend.addMessage(sendMessage, isFast);
        }
    }


    public void tackSendMessage() {
        Enumeration<ChatMessagesOnSend> iterator = dictChatMessages.elements();
        ChatMessagesOnSend chatMessagesOnSend;
        while (iterator.hasMoreElements()){
            chatMessagesOnSend = iterator.nextElement();
            chatMessagesOnSend.getMessageSend(timeInMsSendMessage).ifPresent(sendMessage -> {
                messengerService.send(sendMessage);
            });
        }
    }
}
