package com.nginxtelegrm.NginxTelegramMessage.scheduled;

import com.nginxtelegrm.NginxTelegramMessage.Telegram;
import com.nginxtelegrm.NginxTelegramMessage.enums.MessageStatus;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.Message;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.message.KeyCallBackRequest;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.message.MessageResponse;
import com.nginxtelegrm.NginxTelegramMessage.repository.DistributedMessageRepository;
import com.nginxtelegrm.NginxTelegramMessage.repository.ErrorMessageRepository;
import com.nginxtelegrm.NginxTelegramMessage.repository.MessageRepository;
import com.nginxtelegrm.NginxTelegramMessage.util.parse.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Component
public class SendMessageToTelegramScheduled {
    @Autowired
    Telegram telegram;
    @Autowired
    DistributedMessageRepository distributedMessageRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    ErrorMessageRepository errorMessageRepository;

    Logger logger = LoggerFactory.getLogger(SendMessageToTelegramScheduled.class);

    private SendMessage mapSendMessage(Message message){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message.getMessage().getText());
        sendMessage.enableHtml(true);
        sendMessage.setDisableWebPagePreview(true);
        sendMessage.setChatId(message.getMessage().getIdChat());
        sendMessage.setMessageThreadId(message.getMessage().getIdTread());
        if (message.getMessage().getButtons()!=null && !message.getMessage().getButtons().isEmpty())
        {
            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            message.getMessage().getButtons().forEach(
                    button->{
                        KeyCallBackRequest keyCallBackRequest = new KeyCallBackRequest();
                        keyCallBackRequest.setApplication(button.getApplication());
                        keyCallBackRequest.setValue(button.getKey().toString());
                        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                        inlineKeyboardButton.setCallbackData(JsonUtil.toJson(keyCallBackRequest));
                        inlineKeyboardButton.setText(button.getViewText());
                        rowInline.add(inlineKeyboardButton);
                    }
            );
            List<InlineKeyboardButton> rowInlineTemp = new ArrayList<>();
            for (int i = 0; i < rowInline.size(); i++) {

                if (i % 3 == 0){
                    rowsInline.add(rowInlineTemp);
                    rowInlineTemp = new ArrayList<>();
                }
                rowInlineTemp.add(rowInline.get(i));
            }
            if (!rowInlineTemp.isEmpty())
                rowsInline.add(rowInlineTemp);
            markupInline.setKeyboard(rowsInline);
            sendMessage.setReplyMarkup(markupInline);
        }
        return sendMessage;
    }

    private EditMessageText mapEditMessageText(Message message){
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setText(message.getMessage().getText());
        editMessageText.enableHtml(true);
        editMessageText.setDisableWebPagePreview(true);
        editMessageText.setChatId(message.getMessage().getIdChat());
        editMessageText.setMessageId(message.getMessage().getIdMessage());

        if (message.getMessage().getButtons()!=null && !message.getMessage().getButtons().isEmpty())
        {
            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            message.getMessage().getButtons().forEach(
                    button->{
                        KeyCallBackRequest keyCallBackRequest = new KeyCallBackRequest();
                        keyCallBackRequest.setApplication(button.getApplication());
                        keyCallBackRequest.setValue(button.getKey().toString());
                        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                        inlineKeyboardButton.setCallbackData(JsonUtil.toJson(keyCallBackRequest));
                        inlineKeyboardButton.setText(button.getViewText());
                        rowInline.add(inlineKeyboardButton);
                    }
            );

            List<InlineKeyboardButton> rowInlineTemp = new ArrayList<>();
            for (int i = 0; i < rowInline.size(); i++) {

                if (i % 3 == 0){
                    rowsInline.add(rowInlineTemp);
                    rowInlineTemp = new ArrayList<>();
                }
                rowInlineTemp.add(rowInline.get(i));
            }
            if (!rowInlineTemp.isEmpty())
                rowsInline.add(rowInlineTemp);
            markupInline.setKeyboard(rowsInline);
            editMessageText.setReplyMarkup(markupInline);
        }

        return editMessageText;
    }

    @Async
    @Scheduled(cron = "*/1 * * * * *")
    public void schedule(){
        distributedMessageRepository.get().forEach(message -> {
            try{
                org.telegram.telegrambots.meta.api.objects.Message messageTelegram = null;

                if (message.getMessage().getType().equals("send")) {
                    messageTelegram = telegram.execute(mapSendMessage(message));
                    message.getMessage().setIdMessage(messageTelegram.getMessageId());
                }
                if (message.getMessage().getType().equals("edit"))
                    telegram.execute(mapEditMessageText(message));

                message.setStatus(MessageStatus.IS_SEND);
            }
            catch (Exception e){
                message.setStatus(MessageStatus.ERROR_SEND);
                errorMessageRepository.insert(message, e.getMessage());
                logger.error(e.getMessage());
            }
            messageRepository.UpdateStatus(message);
        });
    }
}
