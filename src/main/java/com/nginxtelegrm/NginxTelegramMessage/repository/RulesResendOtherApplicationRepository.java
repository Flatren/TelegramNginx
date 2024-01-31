package com.nginxtelegrm.NginxTelegramMessage.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.RuleResendOtherApplication.RulesResendOtherApplication;
import com.nginxtelegrm.NginxTelegramMessage.util.parse.JsonUtil;
import database.database.tables.ConfigApplication;
import jakarta.annotation.PostConstruct;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class RulesResendOtherApplicationRepository {

    HashMap<String, RulesResendOtherApplication> rulesResendOtherApplicationHashMap = new HashMap<>();

    @Autowired
    DSLContext dslContext;

    @PostConstruct
    public void postConstruct(){
        dslContext
                .selectFrom(ConfigApplication.CONFIG_APPLICATION)
                .fetch().map(item->JsonUtil.parseJson(item.getConfigJson(), new TypeReference<RulesResendOtherApplication>(){}))
                .forEach(item->{rulesResendOtherApplicationHashMap.put(item.getName(),item);});
    }

    public void insert(RulesResendOtherApplication rulesResendOtherApplication){
        dslContext.insertInto(ConfigApplication.CONFIG_APPLICATION)
                .set(ConfigApplication.CONFIG_APPLICATION.NAME, rulesResendOtherApplication.getName())
                .set(ConfigApplication.CONFIG_APPLICATION.CONFIG_JSON, JsonUtil.toJson(rulesResendOtherApplication))
                .execute();
        rulesResendOtherApplicationHashMap.put(rulesResendOtherApplication.getName(), rulesResendOtherApplication);
    }

    public boolean exist(RulesResendOtherApplication rulesResendOtherApplication) {
        return rulesResendOtherApplicationHashMap.containsKey(rulesResendOtherApplication.getName());
    }

    public void update(RulesResendOtherApplication rulesResendOtherApplication) {
        dslContext.update(ConfigApplication.CONFIG_APPLICATION)
                .set(ConfigApplication.CONFIG_APPLICATION.CONFIG_JSON, JsonUtil.toJson(rulesResendOtherApplication))
                .where(ConfigApplication.CONFIG_APPLICATION.NAME.eq(rulesResendOtherApplication.getName()))
                .execute();
        rulesResendOtherApplicationHashMap.replace(rulesResendOtherApplication.getName(),rulesResendOtherApplication);
    }

    public List<RulesResendOtherApplication> getAll(){
        return rulesResendOtherApplicationHashMap.values().stream().toList();
    }

    public RulesResendOtherApplication get(String name){
        return rulesResendOtherApplicationHashMap.get(name);
    }

    public void delete(RulesResendOtherApplication rulesResendOtherApplication) {
        dslContext.deleteFrom(ConfigApplication.CONFIG_APPLICATION)
                .where(ConfigApplication.CONFIG_APPLICATION.NAME.eq(rulesResendOtherApplication.getName()))
                .execute();
        rulesResendOtherApplicationHashMap.remove(rulesResendOtherApplication.getName());
    }
}
