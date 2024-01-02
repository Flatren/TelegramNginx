package com.nginxtelegrm.NginxTelegramMessage.core.repositoryes;

import com.nginxtelegrm.NginxTelegramMessage.core.modeles.AddressChat;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.rule.Rule;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Repository
public class RulesRepository {

    HashMap<String,List<Rule>> rules;

    public RulesRepository(){
        rules = new HashMap<>();
    }

    public void clear(){
        rules.clear();
    }

    public void insert(String key, Rule rule){
        if (rules.containsKey(key)){
            rules.get(key).add(rule);
        }else{
            rules.put(key, List.of(rule));
        }
    }


    public List<Rule> get(AddressChat addressChat){

        List<Rule> rulesResult = new ArrayList<>();

        this.rules.values().forEach(ruleList->{
                    for (Rule rule : ruleList) {
                        if (rule.getChats().size() == 0)
                            rulesResult.add(rule);
                        else {
                            for (AddressChat address : rule.getChats()) {
                                if (Objects.equals(address.getIdChat(), addressChat.getIdChat()) &&
                                        Objects.equals(address.getIdThread(), addressChat.getIdThread())) {
                                    rulesResult.add(rule);
                                    break;
                                }
                            }
                        }
                    }
                }
        );

        return rulesResult;
    }

}
        /*Rule rule = new Rule();

        rule.setIdChat(374569353L);
        rule.setIdThread(0);
        rule.setDefaultSendAllMessage("default");
        rule.setToAddress("http://127.0.0.1:5000");
        rule.setDefaultSendContentFile(false);
        rule.setInfoCommands( new HashMap<>());
        rule.getInfoCommands().put("/help", new InfoCommand());
        rule.getInfoCommands().get("/help").setParams(List.of("param1"));
        rule.getInfoCommands().get("/help").setTypes(List.of("int"));
        rule.getInfoCommands().get("/help").setContentFile(false);
        rule.getInfoCommands().get("/help").setDescription("none");
*/