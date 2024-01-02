package com.nginxtelegrm.NginxTelegramMessage.core.interfaces.repository;

import com.nginxtelegrm.NginxTelegramMessage.core.modeles.Message;

import java.util.List;

public interface IMessageToSendRepository {

    void insert(Message message);

    void insert(Message message, boolean isFast);

    void update(Message message);

    List<Message> getMessageToSend();
}
