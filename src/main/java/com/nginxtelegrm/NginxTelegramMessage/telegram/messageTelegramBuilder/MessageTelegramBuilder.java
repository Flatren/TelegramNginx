package com.nginxtelegrm.NginxTelegramMessage.telegram.messageTelegramBuilder;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class MessageTelegramBuilder {

    public static SendMessage getSendMessage(Message message){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setMessageThreadId(message.getMessageThreadId());
        sendMessage.enableHtml(true);
        sendMessage.setDisableWebPagePreview(true);
        return sendMessage;
    }
    public static SendMessage getSendMessage(Long idChat, Integer idThread){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(idChat);
        sendMessage.setMessageThreadId(idThread);
        sendMessage.enableHtml(true);
        sendMessage.setDisableWebPagePreview(true);
        return sendMessage;
    }
    public static void addButtons(SendMessage sendMessage, List<String> buttons){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow keyboardButtons = new KeyboardRow();
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        if (buttons.size() == 0) {
            sendMessage.setReplyMarkup(ReplyKeyboardMarkup.builder().clearKeyboard().build());
            return;
        }
        int i = 0;
        for (String button: buttons) {
            if (i == 3)
            {
                i = 0;
                keyboardRows.add(keyboardButtons);
                keyboardButtons = new KeyboardRow();
            }
            keyboardButtons.add(button);
            i++;
        }
        if (keyboardButtons.size()>0)
            keyboardRows.add(keyboardButtons);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
    }
}
