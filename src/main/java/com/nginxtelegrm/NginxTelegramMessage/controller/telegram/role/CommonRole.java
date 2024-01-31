package com.nginxtelegrm.NginxTelegramMessage.controller.telegram.role;

import com.nginxtelegrm.NginxTelegramMessage.core.interfaces.ITelegramRole;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.message.MessageRequest;
import org.springframework.stereotype.Component;

@Component
public class CommonRole implements ITelegramRole {
    @Override
    public boolean filter(MessageRequest messageRequest) {
        return true;
    }
}
