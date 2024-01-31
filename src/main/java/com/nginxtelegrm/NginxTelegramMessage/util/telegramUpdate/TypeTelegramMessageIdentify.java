package com.nginxtelegrm.NginxTelegramMessage.util.telegramUpdate;

import com.nginxtelegrm.NginxTelegramMessage.enums.MessageType;
import liquibase.util.NetUtil;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TypeTelegramMessageIdentify {
    public static MessageType identify(Update update){
        if (update.getMessage() != null)
            return MessageType.MESSAGE;
        if (update.getCallbackQuery()!= null)
            return MessageType.CALLBACK;
        return null;
    }
}
