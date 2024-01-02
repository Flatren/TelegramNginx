package com.nginxtelegrm.NginxTelegramMessage;

import com.nginxtelegrm.NginxTelegramMessage.core.enums.TypeError;
import com.nginxtelegrm.NginxTelegramMessage.core.services.MessengerService;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.Message;
import com.nginxtelegrm.NginxTelegramMessage.core.utilites.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class Telegram extends TelegramLongPollingBot{

    private final String botUsername;
    Logger logger = LoggerFactory.getLogger(Telegram.class);

    @Autowired
    MessengerService messengerService;

    public Telegram(@Value("${bot.token}") String botToken,
                    @Value("${bot.name}") String botUsername) {
        super(botToken);
        this.botUsername = botUsername;
    }


    @Override
    public void onUpdateReceived(Update update) {
        messengerService.processTheMessage(Map.mapToMessageMessenger(update));
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }



    @Scheduled(fixedDelay = 1000)
    public void sendMessage(){
        //Берем сообщения на отправку

        List<Message> messageListToSend = messengerService.getListSendMessage();
        //отправляем
        messageListToSend.forEach(item->{
            //Проверяем ошибки
            try{
                org.telegram.telegrambots.meta.api.objects.Message message =
                        execute(Map.mapSendMessage(item));
                item.setIdMessage(message.getMessageId());
                messengerService.confirmSendingTheMessage(item);
            }
            catch (Exception e){
                //Говорим что была ошибка
                messengerService.ReportError(item, e.getMessage(), TypeError.INTERNET);
                logger.error(e.getMessage());
            }
        });
    }
}
