package com.nginxtelegrm.NginxTelegramMessage.controllers.rest;

import org.springframework.web.bind.annotation.PostMapping;
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
