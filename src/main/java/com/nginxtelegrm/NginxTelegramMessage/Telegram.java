package com.nginxtelegrm.NginxTelegramMessage;


import com.nginxtelegrm.NginxTelegramMessage.enums.MessageType;
import com.nginxtelegrm.NginxTelegramMessage.service.GuidingService;
import com.nginxtelegrm.NginxTelegramMessage.util.map.MessageCallBackRequestMap;
import com.nginxtelegrm.NginxTelegramMessage.util.map.MessageRequestMap;
import com.nginxtelegrm.NginxTelegramMessage.util.telegramUpdate.TypeTelegramMessageIdentify;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
public class Telegram extends TelegramLongPollingBot{

    private final String botUsername;
    Logger logger = LoggerFactory.getLogger(Telegram.class);

    @Autowired
    GuidingService guidingService;

    public Telegram(@Value("${bot.token}") String botToken,
                    @Value("${bot.name}") String botUsername) {
        super(botToken);
        this.botUsername = botUsername;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        MessageType messageType = TypeTelegramMessageIdentify.identify(update);
        switch (messageType) {
            case MESSAGE -> {
                guidingService.direct(MessageRequestMap.map(update.getMessage()));
            }
            case CALLBACK -> {
                guidingService.direct(MessageCallBackRequestMap.map(update.getCallbackQuery()));
            }
        }

    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }
}
