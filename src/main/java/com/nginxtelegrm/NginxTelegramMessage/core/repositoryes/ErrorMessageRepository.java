package com.nginxtelegrm.NginxTelegramMessage.core.repositoryes;

import com.nginxtelegrm.NginxTelegramMessage.core.modeles.Message;
import database.database.tables.ErrorMessageToSend;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class ErrorMessageRepository {

    @Autowired
    private DSLContext dsl;

    public void insert(Message message, String error){
        dsl.insertInto(ErrorMessageToSend.ERROR_MESSAGE_TO_SEND)
                .set(ErrorMessageToSend.ERROR_MESSAGE_TO_SEND.ID_MESSAGE, message.getId())
                .set(ErrorMessageToSend.ERROR_MESSAGE_TO_SEND.DATETIME_ERROR, LocalDateTime.now())
                .set(ErrorMessageToSend.ERROR_MESSAGE_TO_SEND.TEXT_ERROR_SEND, error)
                .execute();
    }
}
