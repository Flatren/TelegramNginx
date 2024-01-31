package com.nginxtelegrm.NginxTelegramMessage.util.map;

import com.nginxtelegrm.NginxTelegramMessage.model.dto.RuleResendOtherApplication.RulesResendOtherApplicationDto;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.AddressChat;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.RuleResendOtherApplication.InfoCommand;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.RuleResendOtherApplication.RuleResendOtherApplication;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.RuleResendOtherApplication.RulesResendOtherApplication;

import java.util.HashMap;
import java.util.List;

public class MapRulesResendOtherApplicationUtil {
    public static RulesResendOtherApplication map(RulesResendOtherApplicationDto rulesROAD){
        RulesResendOtherApplication rulesROA = new RulesResendOtherApplication();

        rulesROA.setName(rulesROAD.getName());
        rulesROA.setToAddress(rulesROAD.getToAddress());
        rulesROA.setAddressSurvey(rulesROAD.getAddressSurvey());
        rulesROA.setAddressCallBack(rulesROAD.getAddressCallBack());
        List<RuleResendOtherApplication> ruleResendOtherApplications = rulesROAD.getRules()
                .stream()
                .map(item->{
            RuleResendOtherApplication ruleResendOtherApplication = new RuleResendOtherApplication();

            ruleResendOtherApplication.setChats(item
                    .getChats()
                    .stream()
                    .map(address -> {
                try {
                    return new AddressChat(address);
                }catch (Exception e){
                    return null;
                }
            } ).toList());
            HashMap<String, InfoCommand> infoCommandHashMap = new HashMap<>();
            item.getInfoCommands().forEach((key, value)->{

                InfoCommand infoCommand = new InfoCommand();

                infoCommand.setDescription(value.getDescription());
                infoCommand.setParams(value.getParams());
                infoCommand.setTypes(value.getTypes());
                infoCommand.setContentFile(value.getContentFile());

                infoCommandHashMap.put(key, infoCommand);
            });

            ruleResendOtherApplication.setInfoCommands(infoCommandHashMap);

            return ruleResendOtherApplication;
        }).toList();
        rulesROA.setRules(ruleResendOtherApplications);
        return rulesROA;
    }
}
