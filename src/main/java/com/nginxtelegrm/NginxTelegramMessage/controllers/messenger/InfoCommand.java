package com.nginxtelegrm.NginxTelegramMessage.controllers.messenger;

import com.nginxtelegrm.NginxTelegramMessage.controllers.messenger.role.CommonRole;
import com.nginxtelegrm.NginxTelegramMessage.core.controller.MessengerController;
import com.nginxtelegrm.NginxTelegramMessage.modeles.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class InfoCommand extends MessengerController {

    public InfoCommand(@Autowired CommonRole commonRole) {
        super("/info", commonRole, List.of());
    }

    @Override
    public Optional<Message> executeCommand(Message message){
        return Optional.of(newMessage(message,
                "Id chat: " + message.getMessage().getChatId() +
                     "\n" +
                     "Id thread: " + message.getMessage().getMessageThreadId()
                )
        );

    }
}
