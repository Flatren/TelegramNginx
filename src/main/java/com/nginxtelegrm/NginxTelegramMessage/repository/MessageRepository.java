package com.nginxtelegrm.NginxTelegramMessage.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nginxtelegrm.NginxTelegramMessage.enums.MessageStatus;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.Message;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.message.MessageResponse;
import com.nginxtelegrm.NginxTelegramMessage.util.parse.JsonUtil;
import database.database.tables.MessageToSend;
import database.database.tables.records.MessageToSendRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class MessageRepository {

    @Autowired
    DSLContext dsl;

    public void insert(MessageResponse messageResponse){
        dsl.insertInto(MessageToSend.MESSAGE_TO_SEND)
                .set(MessageToSend.MESSAGE_TO_SEND.DATETIME_SEND, LocalDateTime.now())
                .set(MessageToSend.MESSAGE_TO_SEND.STATUS, MessageStatus.NO_IS_SEND.getKey())
                .set(MessageToSend.MESSAGE_TO_SEND.MESSAGE_JSON, JsonUtil.toJson(messageResponse))
                .execute();
    }

    public List<Message> getWhere(MessageStatus messageStatus){
        return dsl.selectFrom(MessageToSend.MESSAGE_TO_SEND)
                .where(MessageToSend.MESSAGE_TO_SEND.STATUS.eq(messageStatus.getKey()))
                .fetch(this::map);
    }

    private Message map(MessageToSendRecord messageToSendRecord) {
        Message message = new Message();
        message.setId(messageToSendRecord.getId());
        message.setStatus(MessageStatus.values()[messageToSendRecord.getStatus()]);
        message.setDataUpdate(messageToSendRecord.getDatetimeSend());
        message.setMessage(JsonUtil.parseJson(messageToSendRecord.getMessageJson(), new TypeReference<MessageResponse>() {}));
        return message;
    }

    public void UpdateStatus(Message message){
        dsl.update(MessageToSend.MESSAGE_TO_SEND)
                .set(MessageToSend.MESSAGE_TO_SEND.STATUS, message.getStatus().getKey())
                .set(MessageToSend.MESSAGE_TO_SEND.MESSAGE_JSON, JsonUtil.toJson(message.getMessage()))
                .where(MessageToSend.MESSAGE_TO_SEND.ID.eq(message.getId()))
                .execute();
    }

    void delete(Message message){
        dsl.deleteFrom(MessageToSend.MESSAGE_TO_SEND)
                .where(MessageToSend.MESSAGE_TO_SEND.ID.eq(message.getId()))
                .execute();
    }
}
