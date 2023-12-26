package com.nginxtelegrm.NginxTelegramMessage;

import com.nginxtelegrm.NginxTelegramMessage.core.service.MessengerService;
import com.nginxtelegrm.NginxTelegramMessage.modeles.Message;
import com.nginxtelegrm.NginxTelegramMessage.services.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class Telegram extends TelegramLongPollingBot {

    private final String botUsername;
    Logger logger = LoggerFactory.getLogger(Telegram.class);
    MessengerService messenger = null;

    public void setMessenger(MessengerService messenger) {
        this.messenger = messenger;
    }

    public Telegram(@Value("${bot.token}") String botToken,
                    @Value("${bot.name}") String botUsername) {
        super(botToken);
        this.botUsername = botUsername;
    }


    @Override
    public void onUpdateReceived(Update update) {
        Message message = Map.mapToMessageMessenger(update);
        messenger.processTheMessage(message);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }
}
