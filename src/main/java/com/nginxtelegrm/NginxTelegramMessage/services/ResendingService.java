package com.nginxtelegrm.NginxTelegramMessage.services;
import com.nginxtelegrm.NginxTelegramMessage.core.collection.SendMessageCollection;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.AddressChat;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.Command;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.Intermediate.IntermediateCommand;

import com.nginxtelegrm.NginxTelegramMessage.modeles.Message;
import com.nginxtelegrm.NginxTelegramMessage.modeles.ResultCheckTypeParams;
import com.nginxtelegrm.NginxTelegramMessage.modeles.rule.InfoCommand;
import com.nginxtelegrm.NginxTelegramMessage.modeles.rule.Rule;
import com.nginxtelegrm.NginxTelegramMessage.repositoryes.RulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ResendingService {

    @Autowired
    RulesRepository rulesRepository;

    @Autowired
    SendMessageCollection sendMessageCollection;

    @Autowired
    SendMessageToBotsService sendMessageService;

    public void resendingCommand(Message message, IntermediateCommand intermediateCommand) {

        List<Rule> rules = rulesRepository.get(
                new AddressChat(message.getIdChat(), message.getIdTread())
        );

        for (Rule rule: rules){
            InfoCommand infoCommand = rule.getInfoCommands().get(intermediateCommand.getCommand());
            if (infoCommand != null){

                ResultCheckTypeParams resultCheckTypeParams =
                        CheckService.isTrueTypes(intermediateCommand , infoCommand);

                if (resultCheckTypeParams.isStatus()){

                    Command command = Map.mapToCommand(intermediateCommand, infoCommand);

                    sendMessageService.sendCommand(command, rule);

                }else{

                    Message sendMessage = new Message(message.getIdChat(),message.getIdTread());

                    StringBuilder stringBuilder = new StringBuilder();

                    resultCheckTypeParams.getErrors().forEach(error -> stringBuilder.append(error).append("\n"));

                    sendMessage.setText(stringBuilder.toString());

                    sendMessageCollection.addMessage(sendMessage, true);

                }
            }

        }
    }

    public void resendingMessage(Message message) {
        List<Rule> rules = rulesRepository.get(
                new AddressChat(message.getIdChat(), message.getIdTread())
        );

        for (Rule rule: rules){
            if (rule.getSimpleMessages() != null)
                sendMessageService.sendDefault(message.getText(), rule);
        }
    }
}
