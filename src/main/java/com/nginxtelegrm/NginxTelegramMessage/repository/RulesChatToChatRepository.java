package com.nginxtelegrm.NginxTelegramMessage.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.RuleChatToChat.RuleChatToChat;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.AddressChat;
import com.nginxtelegrm.NginxTelegramMessage.util.filter.FilterRuleChatToChatUtil;
import com.nginxtelegrm.NginxTelegramMessage.util.parse.JsonUtil;
import database.database.tables.RulesChatToChat;
import database.database.tables.records.RulesChatToChatRecord;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class RulesChatToChatRepository {

    @Autowired
    private DSLContext dsl;
    HashMap<String, RuleChatToChat> rulesChatToChatHashMap = new HashMap<>();

    @PostConstruct
    public void PostConstruct(){
        getBD().forEach(
                ruleResend -> rulesChatToChatHashMap.put(ruleResend.getNameRule(),ruleResend)
        );
    }

    public RuleChatToChat map(RulesChatToChatRecord rulesChatToChatRecord) {
        return JsonUtil.parseJson(rulesChatToChatRecord.getDataJson(), new TypeReference<RuleChatToChat>() {});
    }

    public RulesChatToChatRecord map(RuleChatToChat ruleChatToChat){
        RulesChatToChatRecord rulesChatToChatRecord = new RulesChatToChatRecord();
        rulesChatToChatRecord.setRuleName(ruleChatToChat.getNameRule());
        rulesChatToChatRecord.setDataJson(JsonUtil.toJson(ruleChatToChat));
        return rulesChatToChatRecord;
    }

    private List<RuleChatToChat> getBD(){
        return dsl.selectFrom(RulesChatToChat.RULES_CHAT_TO_CHAT)
                .fetch(this::map);
    }

    public List<RuleChatToChat> get(){
        return rulesChatToChatHashMap.values().stream().toList();
    }

    public List<RuleChatToChat> get(String name){
        return dsl.selectFrom(RulesChatToChat.RULES_CHAT_TO_CHAT)
                .where(RulesChatToChat.RULES_CHAT_TO_CHAT.RULE_NAME.eq(name))
                .fetch(this::map);
    }

    public List<RuleChatToChat> get(AddressChat addressChat, String text){
        return rulesChatToChatHashMap.values().stream()
                .filter(ruleResend -> ruleResend.getFrom().contains(addressChat))
                .filter(ruleResend -> FilterRuleChatToChatUtil.filterTag(text, ruleResend.getKeyWord()))
                .filter(FilterRuleChatToChatUtil::filterDataTime)
                .toList();
    }

    @SneakyThrows
    public void insert(RuleChatToChat ruleChatToChat){
        if (rulesChatToChatHashMap.containsKey(ruleChatToChat.getNameRule()))
            throw new Exception("Имя занято.");
        dsl.insertInto(RulesChatToChat.RULES_CHAT_TO_CHAT)
                .set(map(ruleChatToChat)).execute();
        rulesChatToChatHashMap.put(ruleChatToChat.getNameRule(), ruleChatToChat);
    }

    @SneakyThrows
    public void update(RuleChatToChat ruleChatToChat){

        if (!rulesChatToChatHashMap.containsKey(ruleChatToChat.getNameRule()))
            throw new Exception("Правило не найдено.");

        RuleChatToChat updateRuleResend = rulesChatToChatHashMap.get(ruleChatToChat.getNameRule());

        if (ruleChatToChat.getInfo() != null)
            updateRuleResend.setInfo(ruleChatToChat.getInfo());

        if (ruleChatToChat.getRulesTime() != null)
            updateRuleResend.setRulesTime(ruleChatToChat.getRulesTime());

        if (ruleChatToChat.getFrom() != null)
            updateRuleResend.setFrom(ruleChatToChat.getFrom());

        if (ruleChatToChat.getTo() != null)
            updateRuleResend.setTo(ruleChatToChat.getTo());

        if (ruleChatToChat.getKeyWord() != null)
            updateRuleResend.setKeyWord(ruleChatToChat.getKeyWord());

        dsl.update(RulesChatToChat.RULES_CHAT_TO_CHAT)
                .set(map(ruleChatToChat)).execute();
    }

    @SneakyThrows
    public void delete(String name){
        if (!rulesChatToChatHashMap.containsKey(name))
            throw new Exception("Правило не найдено.");

        dsl.deleteFrom(RulesChatToChat.RULES_CHAT_TO_CHAT)
                .where(RulesChatToChat.RULES_CHAT_TO_CHAT.RULE_NAME.eq(name))
                .execute();

        rulesChatToChatHashMap.remove(name);
    }
}
