package com.nginxtelegrm.NginxTelegramMessage.controllers.messenger.resendController;

import com.nginxtelegrm.NginxTelegramMessage.controllers.messenger.role.AdminRole;
import com.nginxtelegrm.NginxTelegramMessage.core.controller.MessengerController;
import com.nginxtelegrm.NginxTelegramMessage.core.map.Map;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.Intermediate.IntermediateRuleResendMessageChats;
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
public class AddNewRuleResend extends MessengerController {

    @Autowired
    RuleResendMessageRepository ruleResendMessageRepository;

    public AddNewRuleResend(@Autowired AdminRole adminRole) {
        super("/add", adminRole,
                List.of(String.class));
    }

    @Override
    public Optional<Message> executeCommandParams(Message message, ArrayList<Object> params){
        IntermediateRuleResendMessageChats intermediateRuleResendMessageChats =
            Parser.parseIntermediateRuleResendMessageChats((String) params.get(0));
        RuleResend ruleResend = Map.map(intermediateRuleResendMessageChats);
        if (ruleResendMessageRepository.insert(ruleResend))
            return Optional.of(newMessage(message, "Правило успешно добавлено"));
        else
            return Optional.of(newMessage(message, "Правило с данным именем уже есть"));
    }
}
