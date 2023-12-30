package com.nginxtelegrm.NginxTelegramMessage.core.repositoryes;

import com.nginxtelegrm.NginxTelegramMessage.core.utilites.Mappers;
import com.nginxtelegrm.NginxTelegramMessage.modeles.Message;
import database.database.tables.ReceivedMessage;
import lombok.SneakyThrows;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class ReceivedMessageRepository {
    @Autowired
    private DSLContext dsl;

    @SneakyThrows
    public void insert(Message message){
        dsl.insertInto(ReceivedMessage.RECEIVED_MESSAGE)
                .set(ReceivedMessage.RECEIVED_MESSAGE.MESSAGE_JSON, Mappers.getStringJson(message))
                .set(ReceivedMessage.RECEIVED_MESSAGE.IS_PROCESSED, false)
                .set(ReceivedMessage.RECEIVED_MESSAGE.DATETIME_RECEIVED, LocalDateTime.now())
                .execute();
    }

    public void setTrueIsProcessed(Message message){
        dsl.update(ReceivedMessage.RECEIVED_MESSAGE)
                .set(ReceivedMessage.RECEIVED_MESSAGE.IS_PROCESSED, true)
                .where(ReceivedMessage.RECEIVED_MESSAGE.ID.eq(message.getId()))
                .execute();
    }
}
