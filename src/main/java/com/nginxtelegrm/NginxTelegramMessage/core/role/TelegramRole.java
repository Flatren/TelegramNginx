package com.nginxtelegrm.NginxTelegramMessage.core.role;

import com.nginxtelegrm.NginxTelegramMessage.modeles.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class TelegramRole {

    public boolean filter(Message message){
        return true;
    }
}
