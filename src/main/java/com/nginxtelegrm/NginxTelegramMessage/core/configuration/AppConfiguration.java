package com.nginxtelegrm.NginxTelegramMessage.core.configuration;

import com.nginxtelegrm.NginxTelegramMessage.core.interfaces.FSRepository;
import com.nginxtelegrm.NginxTelegramMessage.repository.LocalFSRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
    @Value("${repository.type}")
    String repositoryType;

    @Bean
    public FSRepository beanFSRepository(){

        if (repositoryType.equals("local"))
            return new LocalFSRepository();

        if (repositoryType.equals("cloud"))
            return new LocalFSRepository();
        return null;
    }
}
