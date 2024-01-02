package com.nginxtelegrm.NginxTelegramMessage.controllers.messenger.role;

import com.nginxtelegrm.NginxTelegramMessage.core.role.TelegramRole;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class AdminRole extends TelegramRole {

    Long idChat;
    Integer idThread;
    List<Long> whiteList;
    public AdminRole(@Value("${admin.chat}") String chat,
                     @Value("${admin.whitelist}") String whiteList){
        String[] chatAndThread = chat.split(":");
        idChat = Long.parseLong(chatAndThread[0]);
        idThread = Integer.parseInt(chatAndThread[1]);
        this.whiteList = Arrays.stream(whiteList.split(",")).map(Long::parseLong).toList();

    }

    @Override
    public boolean filter(Message message)
    {

        return whiteList.contains(message.getUserId()) &&
                message.getMessage().getChatId().equals(idChat) &&
                idThread.equals(message.getMessage().getMessageThreadId()==null?0:message.getMessage().getMessageThreadId());
    }
}
