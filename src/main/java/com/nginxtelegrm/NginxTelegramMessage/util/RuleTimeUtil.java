package com.nginxtelegrm.NginxTelegramMessage.util;

import com.nginxtelegrm.NginxTelegramMessage.model.dto.RuleTimeDto;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.RuleChatToChat.RuleTime;
import com.nginxtelegrm.NginxTelegramMessage.util.parse.DayOfWeekUtil;
import lombok.SneakyThrows;

import java.util.List;

public class RuleTimeUtil {

    @SneakyThrows
    public static RuleTime map(RuleTimeDto intermediateRuleTime){
        // TODO сделать проверку на корректность задаваемого времени
        if (intermediateRuleTime == null)
            return null;
        RuleTime ruleTime = new RuleTime();
        int minutes;
        String[] hourAndMinute;
        if (intermediateRuleTime.getBegin() == null)
            ruleTime.setBeginTimeMinutes(0);
        else{
            hourAndMinute = intermediateRuleTime.getBegin().split(":");
            minutes = Integer.parseInt(hourAndMinute[0]) * 60 + Integer.parseInt(hourAndMinute[1]);
            ruleTime.setBeginTimeMinutes(minutes);
        }
        if (intermediateRuleTime.getEnd() == null)
            ruleTime.setEndTimeMinutes(0);
        else{
            hourAndMinute = intermediateRuleTime.getEnd().split(":");
            minutes = Integer.parseInt(hourAndMinute[0]) * 60 + Integer.parseInt(hourAndMinute[1]);
            ruleTime.setEndTimeMinutes(minutes);
        }
        if (intermediateRuleTime.getDayOfWeeks() == null)
            ruleTime.setDayOfWeeks(List.of());
        else
            ruleTime.setDayOfWeeks(
                    intermediateRuleTime.getDayOfWeeks()
                            .stream()
                            .map(item-> {
                                try {
                                    return DayOfWeekUtil.parse(item);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            })
                            .toList()
            );
        return ruleTime;
    }
}
