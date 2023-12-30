package com.nginxtelegrm.NginxTelegramMessage.core.repositoryes;

import com.nginxtelegrm.NginxTelegramMessage.core.modeles.RuleResendMessageInChats.RuleResend;
import com.nginxtelegrm.NginxTelegramMessage.core.utilites.Mappers;
import database.database.tables.RulesChatToChat;
import database.database.tables.records.RulesChatToChatRecord;
import lombok.SneakyThrows;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RulesChatToChatRepository {
    @Autowired
    private DSLContext dsl;


    @SneakyThrows
     public RuleResend map(RulesChatToChatRecord rulesChatToChatRecord) {
        return Mappers.getObjectJson(rulesChatToChatRecord.getDataJson(), RuleResend.class);
    }
    @SneakyThrows
     public RulesChatToChatRecord map(RuleResend ruleResend){
        RulesChatToChatRecord rulesChatToChatRecord = new RulesChatToChatRecord();
        rulesChatToChatRecord.setRuleName(ruleResend.getNameRule());
        rulesChatToChatRecord.setDataJson(Mappers.getStringJson(ruleResend));
        return rulesChatToChatRecord;
    }
    @SneakyThrows
    public List<RuleResend> getAll(){
        return dsl.selectFrom(RulesChatToChat.RULES_CHAT_TO_CHAT)
                .fetch(this::map);

    }
    @SneakyThrows
    public void insert(RuleResend ruleResend){
        dsl.insertInto(RulesChatToChat.RULES_CHAT_TO_CHAT)
                .set(map(ruleResend)).execute();
    }
    @SneakyThrows
    public void update(RuleResend ruleResend){
        dsl.update(RulesChatToChat.RULES_CHAT_TO_CHAT)
                .set(map(ruleResend)).execute();
    }
    @SneakyThrows
    public void delete(String name){
        dsl.deleteFrom(RulesChatToChat.RULES_CHAT_TO_CHAT)
                .where(RulesChatToChat.RULES_CHAT_TO_CHAT.RULE_NAME.eq(name))
                .execute();
    }
}
