package com.nginxtelegrm.NginxTelegramMessage.util.filter;

import com.nginxtelegrm.NginxTelegramMessage.model.pojo.RuleChatToChat.RuleChatToChat;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.RuleChatToChat.RuleTime;

import java.time.LocalDateTime;

public class FilterRuleChatToChatUtil {
    public static boolean filterTag(String input, String tag){
        if(tag == null)
            return true;
        return input.replace("\n"," ").matches(tag);
    }

    public static boolean filterDataTime(RuleChatToChat ruleChatToCha){
        //Если нет правил по времени и прошел по тегу, то оставляем
        if (ruleChatToCha.getRulesTime().size() == 0
                && ruleChatToCha.getKeyWord() != null)
            return true;

        LocalDateTime now = LocalDateTime.now();
        Integer nowMinutes = now.getHour() * 60 + now.getMinute();

        for (int i = 0; i < ruleChatToCha.getRulesTime().size(); i++) {
            RuleTime ruleTime = ruleChatToCha.getRulesTime().get(i);
            // если список не пуст и не содержит день недели,
            // то не вносим в список
            if (ruleTime.getDayOfWeeks().size()>0
                    && !ruleTime.getDayOfWeeks().contains(now.getDayOfWeek()))
                return false;
            // попадает ли он в промежуток времени

            if (ruleTime.getBeginTimeMinutes().equals(ruleTime.getEndTimeMinutes()))
                return true;

            if (ruleTime.getBeginTimeMinutes() <= ruleTime.getEndTimeMinutes()
                    && ruleTime.getBeginTimeMinutes() <= nowMinutes
                    && ruleTime.getEndTimeMinutes() >= nowMinutes)
                return true;

            if (ruleTime.getBeginTimeMinutes() >= ruleTime.getEndTimeMinutes()
                    && !(ruleTime.getBeginTimeMinutes() >= nowMinutes
                    && ruleTime.getEndTimeMinutes() <= nowMinutes))
                return true;
        }
        return false;
    }
}
