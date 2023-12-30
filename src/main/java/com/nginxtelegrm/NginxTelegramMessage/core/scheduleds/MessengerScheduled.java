package com.nginxtelegrm.NginxTelegramMessage.core.scheduleds;

import com.nginxtelegrm.NginxTelegramMessage.core.collection.SendMessageCollection;
import com.nginxtelegrm.NginxTelegramMessage.core.service.MessengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MessengerScheduled {

    @Autowired
    SendMessageCollection sendMessageCollection;
    @Autowired
    MessengerService messengerService;

    /*@Scheduled(fixedDelay = 1000)
    private void sendPacketMessage(){
        sendMessageCollection.tackSendMessage();
    }*/

    @Scheduled(fixedDelay = 1000)
    private void processingReadMessages(){
       messengerService.readAllMessageUnseen();
    }
}
