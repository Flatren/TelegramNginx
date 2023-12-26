package com.nginxtelegrm.NginxTelegramMessage.controllers.rest;

import com.nginxtelegrm.NginxTelegramMessage.modeles.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class SendController {

    @PostMapping("/send")
    public void send(@RequestBody Message message)
    {

    }

}
