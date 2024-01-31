package com.nginxtelegrm.NginxTelegramMessage.service;

import com.nginxtelegrm.NginxTelegramMessage.model.pojo.AddressChat;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.message.MessageResponse;
import com.nginxtelegrm.NginxTelegramMessage.repository.MessageRepository;
import com.nginxtelegrm.NginxTelegramMessage.util.GenerateHtmlTextUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessengerLoggerService {

    AddressChat addressChat;

    @Autowired
    MessageRepository messageRepository;
    @SneakyThrows
    public MessengerLoggerService(@Value("${log.chat}") String chat){
        addressChat = new AddressChat(chat);
    }

    public void info(String text){
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setText(GenerateHtmlTextUtil.b("INFO\n")+GenerateHtmlTextUtil.escape(text));
        messageResponse.setIdTread(addressChat.getIdThread());
        messageResponse.setIdChat(addressChat.getIdChat());
        messageResponse.setIsLogMessage(true);
        messageRepository.insert(messageResponse);
    }

    public void error(String text){
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setText(GenerateHtmlTextUtil.b("ERROR\n")+GenerateHtmlTextUtil.escape(text));
        messageResponse.setIdTread(addressChat.getIdThread());
        messageResponse.setIdChat(addressChat.getIdChat());
        messageResponse.setIsLogMessage(true);
        messageRepository.insert(messageResponse);
    }
}
