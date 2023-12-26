package com.nginxtelegrm.NginxTelegramMessage.controllers.messenger.role;

import com.nginxtelegrm.NginxTelegramMessage.core.role.TelegramRole;
import com.nginxtelegrm.NginxTelegramMessage.modeles.Message;
import org.springframework.stereotype.Component;

@Component
public class CommonRole extends TelegramRole {
    @Override
    public boolean filter(Message message)
    {
        return true;
    }
}
