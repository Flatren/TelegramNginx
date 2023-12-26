package com.nginxtelegrm.NginxTelegramMessage.controllers.rest;

import com.nginxtelegrm.NginxTelegramMessage.modeles.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class ConfigController {

    @PostMapping("/update")
    public void update()
    {

    }
}
