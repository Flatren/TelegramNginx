package com.nginxtelegrm.NginxTelegramMessage.services;

import com.nginxtelegrm.NginxTelegramMessage.core.modeles.Command;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.Intermediate.IntermediateCommand;
import com.nginxtelegrm.NginxTelegramMessage.modeles.Message;
import com.nginxtelegrm.NginxTelegramMessage.modeles.rule.InfoCommand;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

public class Map {

    public static Command mapToCommand(IntermediateCommand intermediateCommand, InfoCommand infoCommand){

        Command command = new Command();

        command.setCommand(intermediateCommand.getCommand());

        HashMap<String, String> nameAndValue = new HashMap<>();

        int countParams = intermediateCommand.getParams().size();

        for (int i = 0; i < countParams; i++){
            nameAndValue.put(
                    infoCommand.getParams().get(i),
                    intermediateCommand.getParams().get(i)
            );
        }

        command.setParams(nameAndValue);

        return command;
    }

    private static String getTextFromMessage(org.telegram.telegrambots.meta.api.objects.Message message){
        String messageText = "";

        if (message.getText() != null && !message.getText().isEmpty())
            messageText = message.getText();

        if (messageText.isEmpty() && message.getCaption()!= null)
            messageText = message.getCaption();

        return messageText;
    }

    public static Message mapToMessageMessenger(Update update){

        Message message = new Message();
        message.setIdMessage(update.getMessage().getMessageId());
        message.setMessage(update.getMessage());

        message.setText(getTextFromMessage(update.getMessage()));

        message.setIdChat(update.getMessage().getChatId());

        message.setIdTread(update.getMessage().getMessageThreadId()!=null?
                update.getMessage().getMessageThreadId():0);

        message.setIdMessage(update.getMessage().getMessageId());
        message.setNameChat(update.getMessage().getChat().getTitle());

        if (update.getMessage().getChat().getIsForum() == null)
            message.setForum(false);
        else
            message.setForum(update.getMessage().getChat().getIsForum());

        message.setUserId(update.getMessage().getFrom().getId());
        message.setUserName(update.getMessage().getFrom().getUserName());

        return message;
    }

}
