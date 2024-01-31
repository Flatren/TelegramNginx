package com.nginxtelegrm.NginxTelegramMessage.controller.web;

import com.nginxtelegrm.NginxTelegramMessage.model.dto.MessageDto;
import com.nginxtelegrm.NginxTelegramMessage.model.dto.RuleResendOtherApplication.RulesResendOtherApplicationDto;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.RuleResendOtherApplication.RulesResendOtherApplication;
import com.nginxtelegrm.NginxTelegramMessage.repository.RulesResendOtherApplicationRepository;
import com.nginxtelegrm.NginxTelegramMessage.service.MessengerLoggerService;
import com.nginxtelegrm.NginxTelegramMessage.util.map.MapRulesResendOtherApplicationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OtherApplicationController {

    @Autowired
    RulesResendOtherApplicationRepository rulesResendOtherApplicationRepository;
    @Autowired
    MessengerLoggerService messengerLoggerService;

    @PostMapping("/api/config/update")
    public String update(@RequestBody RulesResendOtherApplicationDto rulesResendOtherApplicationDto)
    {
        try {
            RulesResendOtherApplication rulesResendOtherApplication =
                    MapRulesResendOtherApplicationUtil.map(rulesResendOtherApplicationDto);
            if (rulesResendOtherApplicationRepository.exist(rulesResendOtherApplication)) {
                rulesResendOtherApplicationRepository.update(rulesResendOtherApplication);
                messengerLoggerService.info("Обновлена кофигурация приложения "+rulesResendOtherApplication.getName());
            }else{
                rulesResendOtherApplicationRepository.insert(rulesResendOtherApplication);
                messengerLoggerService.info("Добавлена конфигурация нового приложения "+rulesResendOtherApplication.getName());
            }

            return "OK";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    @PostMapping("/telegram/send")
    public void send(@RequestBody MessageDto messageDto)
    {

    }
}
