package com.nginxtelegrm.NginxTelegramMessage.util.map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.message.KeyCallBackRequest;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.message.MessageCallBackRequest;
import com.nginxtelegrm.NginxTelegramMessage.util.parse.JsonUtil;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class MessageCallBackRequestMap {
    public static MessageCallBackRequest map(CallbackQuery callbackQuery) throws TelegramApiException {
        Message message = callbackQuery.getMessage();
        MessageCallBackRequest messageCallBackRequest = new MessageCallBackRequest();
        KeyCallBackRequest keyCallBackRequest = JsonUtil
                .parseJson(callbackQuery.getData(), new TypeReference<KeyCallBackRequest>() {});

        if (keyCallBackRequest == null)
            return null;

        messageCallBackRequest.setIdMessage(message.getMessageId());
        messageCallBackRequest.setKey(keyCallBackRequest);
        messageCallBackRequest.setIdChat(message.getChatId());
        messageCallBackRequest.setUserId(message.getFrom().getId());
        messageCallBackRequest.setUserName(message.getFrom().getUserName());
        messageCallBackRequest.setIdTread(message.getMessageThreadId());

        return messageCallBackRequest;
    }
}
