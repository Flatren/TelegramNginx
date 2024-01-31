package com.nginxtelegrm.NginxTelegramMessage.core.abstracts;


import com.nginxtelegrm.NginxTelegramMessage.core.interfaces.ITelegramRole;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.IntermediateCommand;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.message.MessageRequest;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.message.MessageResponse;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Getter
public abstract class MessengerController {

    private final ITelegramRole telegramRoleImpl;

    Logger logger = LoggerFactory.getLogger(MessengerController.class);

    public MessengerController(
                               ITelegramRole telegramRoleImpl){
        this.telegramRoleImpl = telegramRoleImpl;
    }

    public List<String> getCommands(){
        return List.of();
    }



    public boolean filter(MessageRequest messageRequest){
        return telegramRoleImpl.filter(messageRequest);
    }

    public Optional<MessageResponse> sendToTheMethod(IntermediateCommand messageRequest){
        return Optional.empty();
    }


}
