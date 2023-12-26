package com.nginxtelegrm.NginxTelegramMessage.services;

import com.nginxtelegrm.NginxTelegramMessage.modeles.Bots;
import com.nginxtelegrm.NginxTelegramMessage.modeles.ConfigRules;
import com.nginxtelegrm.NginxTelegramMessage.repositoryes.RulesRepository;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ConnectException;

@Service
public class UpdateRuleService {

    @Autowired
    Bots bots;

    @Autowired
    SendMessageToBotsService sendMessageToBotsService;

    @Autowired
    RulesRepository rulesRepository;
    Logger logger = LoggerFactory.getLogger(UpdateRuleService.class);

    @PostConstruct
    public void PostConstruct(){
        update();
    }
    @SneakyThrows
    public void update(){

        bots.getBots().keySet().forEach(key->{
                    String address = bots.getBots().get(key);
                    try {
                        String configText = sendMessageToBotsService.getConfig(address);
                        ConfigRules configRules = Parser.parseToConfigRules(configText);
                        configRules.getRules().forEach(item->{
                            rulesRepository.insert(key, item);
                        });
                    } catch (Exception connectException){
                        logger.error("Ошибка соединения по адресу: " + address);
                    }

                }
        );
    }

}
