package com.nginxtelegrm.NginxTelegramMessage.core.repositoryes;

import com.nginxtelegrm.NginxTelegramMessage.core.modeles.AddressChat;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.RuleResendMessageInChats.RuleChatToChat;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.RuleResendMessageInChats.RuleTime;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.List;

@Repository
public class RuleResendMessageRepository {
    @Autowired
    RulesChatToChatRepository rulesChatToChatRepository;

    HashMap<String, RuleChatToChat> ruleResendHashMap = new HashMap<>();

    @PostConstruct
    public void PostConstruct(){
        rulesChatToChatRepository.getAll().forEach(
                ruleResend -> ruleResendHashMap.put(ruleResend.getNameRule(),ruleResend)
        );
    }

    public boolean insert(RuleChatToChat ruleResend){
        if (ruleResendHashMap.containsKey(ruleResend.getNameRule()))
            return false;
        rulesChatToChatRepository.insert(ruleResend);
        ruleResendHashMap.put(ruleResend.getNameRule(), ruleResend);
        return true;
    }

    public boolean delete(String nameRule){
        if (!ruleResendHashMap.containsKey(nameRule))
            return false;
        ruleResendHashMap.remove(nameRule);
        rulesChatToChatRepository.delete(nameRule);
        return true;
    }

    public boolean update(RuleChatToChat ruleResend){
        if (!ruleResendHashMap.containsKey(ruleResend.getNameRule()))
            return false;
        RuleChatToChat updateRuleResend = ruleResendHashMap.get(ruleResend.getNameRule());

        if (ruleResend.getInfo() != null)
            updateRuleResend.setInfo(ruleResend.getInfo());

        if (ruleResend.getRulesTime() != null)
            updateRuleResend.setRulesTime(ruleResend.getRulesTime());

        if (ruleResend.getFrom() != null)
            updateRuleResend.setFrom(ruleResend.getFrom());

        if (ruleResend.getTo() != null)
            updateRuleResend.setTo(ruleResend.getTo());

        if (ruleResend.getKeyWord() != null)
            updateRuleResend.setKeyWord(ruleResend.getKeyWord());
        rulesChatToChatRepository.update(updateRuleResend);
        return true;
    }

    public RuleChatToChat get(String nameRule){
        return ruleResendHashMap.get(nameRule);
    }

    public List<RuleChatToChat> getAll(){
        return ruleResendHashMap.values().stream().toList();
    }

    public List<RuleChatToChat> select(AddressChat addressChat, String text){
        return ruleResendHashMap.values().stream()
                .filter(ruleResend -> ruleResend.getFrom().contains(addressChat))
                .filter(ruleResend -> {
                    if(ruleResend.getKeyWord() == null)
                        return true;
                    return text.matches(ruleResend.getKeyWord());
                })
                .filter(ruleResend -> {
                    //Если нет правил по времени и прошел по тегу, то оставляем
                    if (ruleResend.getRulesTime().size() == 0
                            && ruleResend.getKeyWord() != null)
                        return true;
                    LocalDateTime now = LocalDateTime.now();
                    Integer nowMinutes = now.getHour() * 60 + now.getMinute();

                    for (int i = 0; i < ruleResend.getRulesTime().size(); i++) {
                        RuleTime ruleTime = ruleResend.getRulesTime().get(i);
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
                })
                .toList();
    }
}
