package com.nginxtelegrm.NginxTelegramMessage.core.repositoryes;

import com.nginxtelegrm.NginxTelegramMessage.core.modeles.AddressChat;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.RuleResendMessageInChats.RuleResend;
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

    HashMap<String, RuleResend> ruleResendHashMap = new HashMap<>();

    @PostConstruct
    public void PostConstruct(){
        rulesChatToChatRepository.getAll().forEach(
                ruleResend -> ruleResendHashMap.put(ruleResend.getNameRule(),ruleResend)
        );
    }

    public boolean insert(RuleResend ruleResend){
        if (ruleResendHashMap.containsKey(ruleResend.getNameRule()))
            return false;
        rulesChatToChatRepository.insert(ruleResend);
        ruleResendHashMap.put(ruleResend.getNameRule(), ruleResend);
        return true;
    }

    public boolean delete(RuleResend ruleResend){
        if (!ruleResendHashMap.containsKey(ruleResend.getNameRule()))
            return false;
        ruleResendHashMap.remove(ruleResend.getNameRule());
        rulesChatToChatRepository.delete(ruleResend.getNameRule());
        return true;
    }

    public boolean update(RuleResend ruleResend){
        if (!ruleResendHashMap.containsKey(ruleResend.getNameRule()))
            return false;
        RuleResend updateRuleResend = ruleResendHashMap.get(ruleResend.getNameRule());

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

    public RuleResend get(String nameRule){
        return ruleResendHashMap.get(nameRule);
    }

    public List<RuleResend> getAll(){
        return ruleResendHashMap.values().stream().toList();
    }

    public List<RuleResend> select(AddressChat addressChat, String text){
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
