package com.nginxtelegrm.NginxTelegramMessage.core.role;

import com.nginxtelegrm.NginxTelegramMessage.core.modeles.Message;

public abstract class TelegramRole {

    public boolean filter(Message message){
        return true;
    }
}
