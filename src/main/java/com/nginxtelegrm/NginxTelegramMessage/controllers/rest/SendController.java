package com.nginxtelegrm.NginxTelegramMessage.controllers.rest;

import com.nginxtelegrm.NginxTelegramMessage.core.modeles.Message;
import com.nginxtelegrm.NginxTelegramMessage.core.repositoryes.MessageToSendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class SendController {

    @Autowired
    MessageToSendRepository messageToSendRepository;

    @PostMapping("/send")
    public void send(@RequestBody Message message)
    {
        messageToSendRepository.insert(message);
    }

}
