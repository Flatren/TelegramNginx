package com.nginxtelegrm.NginxTelegramMessage.controllers.messenger.resendController;

import com.nginxtelegrm.NginxTelegramMessage.controllers.messenger.role.AdminRole;
import com.nginxtelegrm.NginxTelegramMessage.core.controller.MessengerController;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.RuleResendMessageInChats.RuleResend;
import com.nginxtelegrm.NginxTelegramMessage.core.repositoryes.RuleResendMessageRepository;
import com.nginxtelegrm.NginxTelegramMessage.modeles.Message;
import com.nginxtelegrm.NginxTelegramMessage.services.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Component
public class ShowRuleResend extends MessengerController {
    public ShowRuleResend(@Autowired AdminRole adminRole) {
        super("/show", adminRole, List.of(String.class));
    }
    @Autowired
    RuleResendMessageRepository ruleResendMessageRepository;

    @Override
    public Optional<Message> executeCommandParams(Message message, ArrayList<Object> params){
        String name = (String) params.get(0);
        RuleResend ruleResend = ruleResendMessageRepository.get(name);
        if (ruleResend!=null)
            return Optional.of(newMessage(message, Parser.dumpYaml(ruleResend, RuleResend.class)));
        else
            return Optional.of(newMessage(message, "Правило с данным именем отсутствует."));
    }

    @Override
    public Optional<Message> executeCommand(Message message){
       List<RuleResend> ruleResends = ruleResendMessageRepository.getAll();

        if (ruleResends.size() != 0)
            return Optional.of(newMessage(message, ruleResends.stream().map(RuleResend::getNameRule).toString()));
        else
            return Optional.of(newMessage(message, "Правила отсутствуют."));
    }
}
