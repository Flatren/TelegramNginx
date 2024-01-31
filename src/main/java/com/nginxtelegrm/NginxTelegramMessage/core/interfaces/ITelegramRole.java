package com.nginxtelegrm.NginxTelegramMessage.core.interfaces;

import com.nginxtelegrm.NginxTelegramMessage.model.pojo.message.MessageRequest;

public interface ITelegramRole {
    boolean filter(MessageRequest messageRequest);
}