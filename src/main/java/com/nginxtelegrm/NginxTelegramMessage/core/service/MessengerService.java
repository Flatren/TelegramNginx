package com.nginxtelegrm.NginxTelegramMessage.core.service;

import com.nginxtelegrm.NginxTelegramMessage.Telegram;
import com.nginxtelegrm.NginxTelegramMessage.core.collection.SendMessageCollection;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.Intermediate.IntermediateCommand;
import com.nginxtelegrm.NginxTelegramMessage.modeles.Message;
import com.nginxtelegrm.NginxTelegramMessage.services.CheckService;
import com.nginxtelegrm.NginxTelegramMessage.services.Parser;
import com.nginxtelegrm.NginxTelegramMessage.services.ResendingService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class MessengerService {
    @Autowired
    private MeterRegistry meterRegistry;
    @Autowired
    Telegram telegram;
    @Autowired
    SendMessageCollection sendMessageCollection;
    @Autowired
    ResendingService resendingService;
    @Autowired
    ControllersService controllersService;
    @Autowired
    ResendMessageService resendMessageService;
    Logger logger = LoggerFactory.getLogger(MessengerService.class);

    private Counter counterMessages;
    private Counter counterSendMessage;
    private Counter counterErrorSendMessage;

    @PostConstruct
    public void PostConstruct(){
        sendMessageCollection.setMessengerService(this);
        telegram.setMessenger(this);
        counterMessages =
                Counter.builder("all received message")
                        .description("Всего сообщений, полученные ботом")
                        .register(meterRegistry);
        counterSendMessage =
                Counter.builder("send message")
                        .description("Всего сообщений, отправленных ботом")
                        .register(meterRegistry);
        counterErrorSendMessage =
                Counter.builder("error send message")
                        .description("Количество ошибок при отправке")
                        .register(meterRegistry);
    }

    public void processTheMessage(Message message){
        counterMessages.increment();
        if (CheckService.isCommand(message.getText())) {
            IntermediateCommand intermediateCommand = Parser.parseStringToIntermediateCommand(message.getText());
            controllersService.execute(message, intermediateCommand);
            resendingService.resendingCommand(message, intermediateCommand);
        }else{
            resendMessageService.execute(message);
            resendingService.resendingMessage(message);
        }
    }

    public void send(Message message){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message.getText());
        sendMessage.enableHtml(true);
        sendMessage.setDisableWebPagePreview(true);
        sendMessage.setChatId(message.getIdChat());
        sendMessage.setMessageThreadId(message.getIdTread());
        try {
            telegram.execute(sendMessage);
            counterSendMessage.increment();
        }
        catch (Exception e){
            logger.error(e.getMessage());
            counterErrorSendMessage.increment();
        }

    }

}
