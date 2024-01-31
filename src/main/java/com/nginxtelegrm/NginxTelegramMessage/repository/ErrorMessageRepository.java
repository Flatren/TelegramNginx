package com.nginxtelegrm.NginxTelegramMessage.repository;

import com.nginxtelegrm.NginxTelegramMessage.model.pojo.Message;
import database.database.tables.ErrorMessageToSend;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;


@Repository
public class ErrorMessageRepository {

    @Autowired
    DSLContext dsl;

    public void insert(Message message, String textError){
        dsl.insertInto(ErrorMessageToSend.ERROR_MESSAGE_TO_SEND)
                .set(ErrorMessageToSend.ERROR_MESSAGE_TO_SEND.DATETIME_ERROR, LocalDateTime.now())
                .set(ErrorMessageToSend.ERROR_MESSAGE_TO_SEND.TEXT_ERROR_SEND, textError)
                .set(ErrorMessageToSend.ERROR_MESSAGE_TO_SEND.ID_MESSAGE, message.getId())
                .execute();
    }
}
