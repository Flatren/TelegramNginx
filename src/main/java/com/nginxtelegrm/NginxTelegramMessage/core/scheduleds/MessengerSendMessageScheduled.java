package com.nginxtelegrm.NginxTelegramMessage.core.scheduleds;

import com.nginxtelegrm.NginxTelegramMessage.Telegram;
import com.nginxtelegrm.NginxTelegramMessage.core.collection.SendMessageCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MessengerSendMessageScheduled {

    @Autowired
    SendMessageCollection sendMessageCollection;

    @Scheduled(fixedDelay = 1000)
    private void sendPacketMessage(){
        sendMessageCollection.tackSendMessage();
    }
}
