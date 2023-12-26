package com.nginxtelegrm.NginxTelegramMessage.configurations;

import com.nginxtelegrm.NginxTelegramMessage.modeles.Bots;
import com.nginxtelegrm.NginxTelegramMessage.repositoryes.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.FileNotFoundException;

@Configuration
@EnableScheduling
public class AppConfiguration {


    @Autowired
    ConfigRepository configRepository;

    @Value("${config.file.name}")
    String configName;

    @Bean
    public Bots getBots() throws FileNotFoundException {
        return configRepository.getBots(configName);
    }
}
