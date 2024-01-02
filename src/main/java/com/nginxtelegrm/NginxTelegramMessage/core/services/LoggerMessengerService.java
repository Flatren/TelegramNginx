package com.nginxtelegrm.NginxTelegramMessage.core.services;

import com.nginxtelegrm.NginxTelegramMessage.core.modeles.Message;
import com.nginxtelegrm.NginxTelegramMessage.core.repositoryes.MessageToSendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LoggerMessengerService {

    Long idChat;
    Integer idThread;

    @Autowired
    MessageToSendRepository messageToSendRepository;

    public LoggerMessengerService(@Value("${log.chat}") String chat){
        String[] chatAndThread = chat.split(":");
        idChat = Long.parseLong(chatAndThread[0]);
        idThread = Integer.parseInt(chatAndThread[1]);
    }

    public void INFO(String text){
        Message message = new Message(idChat,idThread);
        message.setText(text);
        message.setIsLogMessage(true);
        messageToSendRepository.insert(message);
    }

    public void ERROR(String text){
        Message message = new Message(idChat,idThread);
        message.setText(text);
        message.setIsLogMessage(true);
        messageToSendRepository.insert(message);
    }

}
