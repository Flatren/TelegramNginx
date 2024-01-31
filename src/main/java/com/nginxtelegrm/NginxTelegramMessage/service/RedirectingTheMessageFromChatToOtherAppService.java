package com.nginxtelegrm.NginxTelegramMessage.service;

import com.nginxtelegrm.NginxTelegramMessage.model.param.messageApplication.OutMessageAppParam;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.AddressChat;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.IntermediateCommand;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.RuleResendOtherApplication.InfoCommand;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.RuleResendOtherApplication.RuleResendOtherApplication;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.RuleResendOtherApplication.RulesResendOtherApplication;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.message.MessageCallBackRequest;
import com.nginxtelegrm.NginxTelegramMessage.repository.MessageApplicationRepository;
import com.nginxtelegrm.NginxTelegramMessage.repository.MessageRepository;
import com.nginxtelegrm.NginxTelegramMessage.repository.RulesResendOtherApplicationRepository;
import com.nginxtelegrm.NginxTelegramMessage.util.build.MessageResponseBuild;
import com.nginxtelegrm.NginxTelegramMessage.util.parse.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;

@Service
public class RedirectingTheMessageFromChatToOtherAppService {
    @Autowired
    RulesResendOtherApplicationRepository rulesResendOtherApplicationRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    MessageApplicationRepository messageApplicationRepository;

    private HashMap<String, String> generateHashMapParams(InfoCommand infoCommand, IntermediateCommand intermediateCommand) throws Exception {

        if (intermediateCommand.getParams().size() != infoCommand.getParams().size()){
            throw new Exception("Количество параметров не совпадает.");
        }
        HashMap<String,String> params = new HashMap<>();
        for(int i=0;i<infoCommand.getParams().size();i++){
            if(!ConvertUtil.isTrueType(intermediateCommand.getParams().get(i),infoCommand.getTypes().get(i))){
                throw new Exception("Параметр " + i + " имеет тип:" + infoCommand.getTypes().get(i));
            }
            params.put(infoCommand.getParams().get(i),intermediateCommand.getParams().get(i));
        }
        return params;
    }

    public void direct(IntermediateCommand intermediateCommand){

        AddressChat addressChat =
                new AddressChat(intermediateCommand.getMessageRequest().getIdChat(),
                                intermediateCommand.getMessageRequest().getIdTread());

        rulesResendOtherApplicationRepository.getAll()
                .forEach(rules->{
                    rules.getRules().stream()
                            .filter(rule->rule.getChats().contains(addressChat))
                            .forEach(rule->{
                                InfoCommand infoCommand = rule.getInfoCommands().get(intermediateCommand.getCommand());
                                if (infoCommand != null){
                                    try {
                                        OutMessageAppParam outMessageAppParam = new OutMessageAppParam();
                                        outMessageAppParam.setUrl(new URL(rules.getToAddress() + intermediateCommand.getCommand()));
                                        //параметры команды
                                        outMessageAppParam.setParams(generateHashMapParams(infoCommand, intermediateCommand));
                                        //информацию о файле
                                        if (infoCommand.getContentFile() && intermediateCommand.getMessageRequest().getFile()!=null)
                                            outMessageAppParam.setLinkFileTelegram(intermediateCommand.getMessageRequest().getFile());

                                        //параметры кординации
                                        outMessageAppParam.setUser(intermediateCommand.getMessageRequest().getUserName());
                                        outMessageAppParam.setIdUser(intermediateCommand.getMessageRequest().getUserId().toString());
                                        outMessageAppParam.setIdMessage(intermediateCommand.getMessageRequest().getIdMessage().toString());
                                        outMessageAppParam.setChat(addressChat.ToString());
                                        messageApplicationRepository.insert(outMessageAppParam);

                                    }catch (Exception e){
                                        messageRepository
                                                .insert(
                                                        MessageResponseBuild
                                                                .logMessage(addressChat,e.getMessage())
                                                );
                                    }
                                }
                            });
                });
    }


}
