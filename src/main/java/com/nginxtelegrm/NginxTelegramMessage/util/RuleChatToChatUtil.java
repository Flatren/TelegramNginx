package com.nginxtelegrm.NginxTelegramMessage.util;

import com.nginxtelegrm.NginxTelegramMessage.model.pojo.RuleChatToChat.RuleChatToChat;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.AddressChat;
import com.nginxtelegrm.NginxTelegramMessage.model.dto.RuleChatToChatDto;
import lombok.SneakyThrows;

import java.util.List;

public class RuleChatToChatUtil {

    @SneakyThrows
    public static RuleChatToChat map(RuleChatToChatDto intermediateRuleResendMessageChats){
        if (intermediateRuleResendMessageChats.getFrom() == null)
            throw new Exception("Не задан from");
        if (intermediateRuleResendMessageChats.getFrom().size() == 0)
            throw new Exception("Не задан from");
        if (intermediateRuleResendMessageChats.getTo() == null)
            throw new Exception("Не задан to");
        if (intermediateRuleResendMessageChats.getTo().size() == 0)
            throw new Exception("Не задан to");

        RuleChatToChat ruleResend = new RuleChatToChat();
        ruleResend.setInfo(intermediateRuleResendMessageChats.getInfo());
        ruleResend.setKeyWord(intermediateRuleResendMessageChats.getKeyWord());
        ruleResend.setNameRule(intermediateRuleResendMessageChats.getNameRule());

        ruleResend.setFrom(
                intermediateRuleResendMessageChats.getFrom()
                        .stream()
                        .map(address -> {
                            try {
                                return new AddressChat(address);
                            }catch (Exception e){
                                return null;
                            }
                        } ).toList());

        ruleResend.setTo(
                intermediateRuleResendMessageChats.getTo()
                        .stream()
                        .map(address -> {
                            try {
                                return new AddressChat(address);
                            }catch (Exception e){
                                return null;
                            }
                        } ).toList());

        if (intermediateRuleResendMessageChats.getRulesTime() != null)
            ruleResend.setRulesTime(
                    intermediateRuleResendMessageChats.getRulesTime().stream().map(RuleTimeUtil::map).toList()
            );
        else
            ruleResend.setRulesTime(List.of());

        return ruleResend;
    }
}
