package com.nginxtelegrm.NginxTelegramMessage.scheduled;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nginxtelegrm.NginxTelegramMessage.model.dto.responce.SurveyResponseDto;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.RuleResendOtherApplication.RulesResendOtherApplication;
import com.nginxtelegrm.NginxTelegramMessage.repository.RulesResendOtherApplicationRepository;
import com.nginxtelegrm.NginxTelegramMessage.service.MessengerLoggerService;
import com.nginxtelegrm.NginxTelegramMessage.util.http.HttpRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ApplicationSurveyScheduled {

    @Autowired
    RulesResendOtherApplicationRepository rulesResendOtherApplicationRepository;
    @Autowired
    MessengerLoggerService messengerLoggerService;

    Logger logger =  LoggerFactory.getLogger(ApplicationSurveyScheduled.class);

    @Async
    @Scheduled(cron = "*/5 * * * * *")
    void schedule(){
        List<RulesResendOtherApplication> rules = rulesResendOtherApplicationRepository.getAll();
        rules.forEach(rulesResendOtherApplication -> {
            try{
                SurveyResponseDto ob = HttpRequestUtil.sendRequest(rulesResendOtherApplication.getAddressSurvey(),
                        new TypeReference<SurveyResponseDto>(){});
            }catch (Exception e){
                rulesResendOtherApplicationRepository.delete(rulesResendOtherApplication);
                messengerLoggerService.error("Соединение с приложением "+rulesResendOtherApplication.getName()+" разорвано");
                logger.error(e.getMessage());
            }
        });
    }
}
