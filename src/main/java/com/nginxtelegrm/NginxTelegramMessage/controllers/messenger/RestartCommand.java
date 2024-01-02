package com.nginxtelegrm.NginxTelegramMessage.controllers.messenger;

import com.nginxtelegrm.NginxTelegramMessage.controllers.messenger.role.AdminRole;
import com.nginxtelegrm.NginxTelegramMessage.core.controller.MessengerController;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.Message;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class RestartCommand extends MessengerController {

    public RestartCommand(@Autowired AdminRole adminRole) {

        super("/restart", adminRole, List.of());
    }

    @Override
    public Optional<Message> executeCommand(Message message){
        return Optional.of(newMessage(message,"Перезагрузка конфигурации выполнена."));

    }
}