package com.nginxtelegrm.NginxTelegramMessage.controllers.messenger.role;

import com.nginxtelegrm.NginxTelegramMessage.core.role.TelegramRole;
import com.nginxtelegrm.NginxTelegramMessage.modeles.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AdminRole extends TelegramRole {

    @Value("${admin.chat}")
    Long idChat;
    @Value("${admin.thread}")
    Integer idThread;

    @Override
    public boolean filter(Message message)
    {
        return message.getMessage().getChatId().equals(idChat) &&
                idThread.equals(message.getMessage().getMessageThreadId()==null?0:message.getMessage().getMessageThreadId());
    }
}
