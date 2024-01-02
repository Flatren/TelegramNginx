package com.nginxtelegrm.NginxTelegramMessage.core.scheduleds;

import com.nginxtelegrm.NginxTelegramMessage.core.services.MessengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessengerScheduled {


    @Autowired
    MessengerService messengerService;

    /*@Scheduled(fixedDelay = 1000)
    private void sendPacketMessage(){
        messageSendRepository.tackSendMessage();
    }*/


}
