package com.nginxtelegrm.NginxTelegramMessage.core.repositoryes;

import com.nginxtelegrm.NginxTelegramMessage.core.utilites.Mappers;
import com.nginxtelegrm.NginxTelegramMessage.modeles.Message;
import database.database.tables.SendMessage;
import database.database.tables.records.SendMessageRecord;
import lombok.SneakyThrows;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SendMessagesRepository {

    @Autowired
    private DSLContext dsl;

    @SneakyThrows
    public SendMessageRecord map(Message message){
        SendMessageRecord sendMessageRecord = new SendMessageRecord();
        sendMessageRecord.setMessageJson(Mappers.getStringJson(message));
        sendMessageRecord.setIsSend(false);
        sendMessageRecord.setTextErrorSend("");
        sendMessageRecord.setIsSendingAttempt(false);
        sendMessageRecord.setIdMessageInTelegram(null);
        return sendMessageRecord;
    }

    @SneakyThrows
    public Message map(SendMessageRecord sendMessageRecord){
        Message message = Mappers.getObjectJson(sendMessageRecord.getMessageJson(), Message.class);
        message.setId(sendMessageRecord.getId());
        return message;
    }

    public void insert(Message message){
        dsl.insertInto(SendMessage.SEND_MESSAGE)
                .set(map(message))
                .execute();
    }

    public void setStatusSend(Long idMessage, Long idMessageInMessenger){
        dsl.update(SendMessage.SEND_MESSAGE)
                .set(SendMessage.SEND_MESSAGE.IS_SENDING_ATTEMPT, true)
                .set(SendMessage.SEND_MESSAGE.IS_SEND, true)
                .set(SendMessage.SEND_MESSAGE.ID_MESSAGE_IN_TELEGRAM, idMessageInMessenger)
                .where(SendMessage.SEND_MESSAGE.ID.eq(idMessage))
                .execute();
    }

    public void setStatusErrorSend(Long idMessage, String error){
        dsl.update(SendMessage.SEND_MESSAGE)
                .set(SendMessage.SEND_MESSAGE.IS_SENDING_ATTEMPT, true)
                .set(SendMessage.SEND_MESSAGE.TEXT_ERROR_SEND, error)
                .where(SendMessage.SEND_MESSAGE.ID.eq(idMessage))
                .execute();
    }

    public List<Message> getNoSendNoAttempt(){
       return dsl.selectFrom(SendMessage.SEND_MESSAGE)
               .where(SendMessage.SEND_MESSAGE.IS_SENDING_ATTEMPT.eq(false))
               .fetch(this::map);
    }



}
