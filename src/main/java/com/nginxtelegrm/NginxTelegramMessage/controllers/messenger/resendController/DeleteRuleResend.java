package com.nginxtelegrm.NginxTelegramMessage.controllers.messenger.resendController;

import com.nginxtelegrm.NginxTelegramMessage.controllers.messenger.role.AdminRole;
import com.nginxtelegrm.NginxTelegramMessage.core.controller.MessengerController;
import com.nginxtelegrm.NginxTelegramMessage.core.utilites.map.Map;
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
public class DeleteRuleResend extends MessengerController {
    public DeleteRuleResend(@Autowired AdminRole adminRole) {
        super("/del", adminRole, List.of(String.class));
    }

    @Autowired
    RuleResendMessageRepository ruleResendMessageRepository;

    @Override
    public Optional<Message> executeCommandParams(Message message, ArrayList<Object> params){
        IntermediateRuleResendMessageChats intermediateRuleResendMessageChats =
                Parser.parseIntermediateRuleResendMessageChats((String) params.get(0));
        RuleResend ruleResend = Map.map(intermediateRuleResendMessageChats);
        if (ruleResendMessageRepository.delete(ruleResend))
            return Optional.of(newMessage(message, "Правило успешно удалено."));
        else
            return Optional.of(newMessage(message, "Правило с данным именем отсутствует."));
    }
}
