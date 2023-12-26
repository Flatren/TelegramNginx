package com.nginxtelegrm.NginxTelegramMessage.core.controller;


import com.nginxtelegrm.NginxTelegramMessage.core.modeles.Intermediate.IntermediateCommand;
import com.nginxtelegrm.NginxTelegramMessage.core.role.TelegramRole;
import com.nginxtelegrm.NginxTelegramMessage.modeles.Message;
import com.nginxtelegrm.NginxTelegramMessage.core.collection.SendMessageCollection;
import com.nginxtelegrm.NginxTelegramMessage.services.Parser;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public abstract class MessengerController {

    private final String COMMAND_PREFIX;
    private final TelegramRole telegramRole;
    private final ArrayList<Type> typeParams;
    protected final Boolean isDownloadFiles=false;
    @Autowired
    protected SendMessageCollection sendMessageCollection;

    Logger logger = LoggerFactory.getLogger(MessengerController.class);

    public MessengerController(String COMMAND_PREFIX, TelegramRole telegramRole, List<Type> typeParams){
        this.COMMAND_PREFIX = COMMAND_PREFIX;
        this.telegramRole = telegramRole;
        this.typeParams = new ArrayList<Type>(typeParams);
    }

    protected Message newMessage(Message message, String text){
        Message sendMessage = new Message();
        /*SendMessage sendMessage = new SendMessage();
        sendMessage.enableHtml(true);
        sendMessage.setText(text);
        sendMessage.setChatId(message.getMessage().getChatId());
        sendMessage.setMessageThreadId(message.getMessage().getMessageThreadId());
        */
        sendMessage.setText(text);
        sendMessage.setIdTread(message.getIdTread());
        sendMessage.setIdChat(message.getIdChat());
        return sendMessage;

    }

    public boolean filter(Message message){
        return this.telegramRole.filter(message);
    }

    public void executeTry(Message message, IntermediateCommand intermediateCommand) {
        if (filter(message)){
            logger.info(message.getMessage().getText());
            try{
                if (intermediateCommand.getParams().size() == 0)
                    executeCommand(message)
                        .ifPresent(sendMessage -> sendMessageCollection.addMessage(sendMessage, true));
                else
                    executeCommandParams(
                            message,
                            Parser.parseToParams(intermediateCommand,  getTypeParams()))
                            .ifPresent(sendMessage -> sendMessageCollection.addMessage(sendMessage, true));
            }
            catch (Exception e) {
                logger.info(e.getMessage());
                sendMessageCollection.addMessage(newMessage(message, e.getMessage()), true);
            }
        }
    }

    public Optional<Message> executeCommand(Message message){
        return Optional.of(newMessage(message, "Неверные параметры команды."));
    }

    public Optional<Message> executeCommandWithFile(Message message, List<String> files){
        return Optional.of(newMessage(message, "Неверные параметры команды."));
    }

    public Optional<Message> executeCommandParams(Message message, ArrayList<Object> params){
        return Optional.of(newMessage(message, "Неверные параметры команды."));
    }

    public Optional<Message> executeCommandParamsWithFiles(Message message, ArrayList<Object> params, List<String> files){
        return Optional.of(newMessage(message, "Неверные параметры команды."));
    }
}
