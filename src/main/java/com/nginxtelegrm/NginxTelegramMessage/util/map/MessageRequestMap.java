package com.nginxtelegrm.NginxTelegramMessage.util.map;


import com.nginxtelegrm.NginxTelegramMessage.model.pojo.LinkFileTelegram;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.message.MessageRequest;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MessageRequestMap {

    private static String getTextFromMessage(Message message){
        String messageText = "";

        if (message.getText() != null && !message.getText().isEmpty())
            messageText = message.getText();

        if (messageText.isEmpty() && message.getCaption()!= null)
            messageText = message.getCaption();

        return messageText;
    }

    public static MessageRequest map(Message message) throws TelegramApiException {

        MessageRequest messageRequest = new MessageRequest();

        messageRequest.setIdMessage(message.getMessageId());

        messageRequest.setText(getTextFromMessage(message));

        messageRequest.setIdChat(message.getChatId());

        messageRequest.setIdTread(message.getMessageThreadId()!=null?
                message.getMessageThreadId():0);

        messageRequest.setIdMessage(message.getMessageId());

        messageRequest.setNameChat(message.getChat().getTitle());

        if (message.getDocument()!=null)
        {
            LinkFileTelegram linkFileTelegram = new LinkFileTelegram();
            linkFileTelegram.setIdFile(message.getDocument().getFileId());
            linkFileTelegram.setNameFile(message.getDocument().getFileName());
            messageRequest.setFile(linkFileTelegram);
        }

        if (message.getChat().getIsForum() == null)
            messageRequest.setForum(false);
        else
            messageRequest.setForum(message.getChat().getIsForum());

        messageRequest.setUserId(message.getFrom().getId());
        messageRequest.setUserName(message.getFrom().getUserName());

        return messageRequest;
    }
}
