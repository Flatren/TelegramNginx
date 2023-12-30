package com.nginxtelegrm.NginxTelegramMessage.core.repositoryes;

import com.nginxtelegrm.NginxTelegramMessage.core.utilites.Mappers;
import com.nginxtelegrm.NginxTelegramMessage.modeles.Message;
import database.database.tables.Messages;
import database.database.tables.records.MessagesRecord;
import lombok.SneakyThrows;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class MessageRepository {

    @Autowired
    private DSLContext dsl;

    @SneakyThrows
    private MessagesRecord map(Message message){
        MessagesRecord messagesRecord = new MessagesRecord();
        messagesRecord.setMessageJson(Mappers.getStringJson(message));
        messagesRecord.setDatetimeInsert(LocalDateTime.now());
        messagesRecord.setIsNoTrySend(false);
        return messagesRecord;
    }

    @SneakyThrows
    private Message map(MessagesRecord messagesRecord){
        Message message = Mappers.getObjectJson(messagesRecord.getMessageJson(), Message.class);
        message.setId(messagesRecord.getId());
        return message;
    }

    public void insert(Message message){
        Long id = dsl.insertInto(Messages.MESSAGES)
                .set(map(message))
                .returningResult(Messages.MESSAGES.ID).fetch().get(0).value1();
        message.setId(id);
    }

    public Optional<Message> getMessage(Long id) {
        return dsl.selectFrom(Messages.MESSAGES)
                .where(Messages.MESSAGES.ID.eq(id))
                .fetch(this::map).stream().findFirst();
    }
}
