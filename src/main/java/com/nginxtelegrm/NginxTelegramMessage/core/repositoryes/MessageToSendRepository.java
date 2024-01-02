package com.nginxtelegrm.NginxTelegramMessage.core.repositoryes;

import com.nginxtelegrm.NginxTelegramMessage.core.interfaces.repository.IMessageToSendRepository;
import com.nginxtelegrm.NginxTelegramMessage.core.store.StoreMessagesToSendOnChat;
import com.nginxtelegrm.NginxTelegramMessage.core.utilites.Mappers;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.Message;
import database.database.tables.MessageToSend;
import database.database.tables.records.MessageToSendRecord;
import lombok.SneakyThrows;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class MessageToSendRepository implements IMessageToSendRepository {

    private final HashMap<Long, StoreMessagesToSendOnChat> dictChatMessages;
    private final Object lock;
    private final long timeInMsSendMessage;
    @Autowired
    private DSLContext dsl;


    public MessageToSendRepository(@Value("${bot.time_in_second_send_message}") long timeInSecondSendMessage){
        this.timeInMsSendMessage = timeInSecondSendMessage*1000;
        this.dictChatMessages = new HashMap<>();
        this.lock = new Object();
    }

    private void insertDB(Message message){
        Long id = dsl.insertInto(MessageToSend.MESSAGE_TO_SEND)
                    .set(map(message))
                    .returning().fetchOne().getId();
        message.setId(id);
    }

    public void insert(Message message) {
        insert(message, false);
    }

    @SneakyThrows
    public void update(Message message){
        if (message.getIsLogMessage())
            return;
        dsl.update(MessageToSend.MESSAGE_TO_SEND)
                .set(MessageToSend.MESSAGE_TO_SEND.MESSAGE_JSON, Mappers.getStringJson(message))
                .set(MessageToSend.MESSAGE_TO_SEND.IS_SEND, true)
                .set(MessageToSend.MESSAGE_TO_SEND.DATETIME_SEND, LocalDateTime.now())
                .where(MessageToSend.MESSAGE_TO_SEND.ID.eq(message.getId()))
                .execute();
    }

    public void insert(Message message, boolean isFast){
        synchronized (lock){
            if(!message.getIsLogMessage())
                insertDB(message);
            StoreMessagesToSendOnChat storeMessagesToSendOnChat = dictChatMessages.get(message.getIdChat());
            if (storeMessagesToSendOnChat == null){
                storeMessagesToSendOnChat = new StoreMessagesToSendOnChat();
                dictChatMessages.put(message.getIdChat(), storeMessagesToSendOnChat);
            }
            storeMessagesToSendOnChat.addMessage(message, isFast);
        }
    }

    public List<Message> getMessageToSend() {
        List<Message> messages = new ArrayList<>();
        synchronized (lock){
            dictChatMessages.values().forEach(item->{
                        item.getMessageSend(timeInMsSendMessage)
                                .ifPresent(messages::add);
                }
            );
        }
        return messages;
    }

    @SneakyThrows
    private MessageToSendRecord map(Message message){
        MessageToSendRecord messageToSendRecord = new MessageToSendRecord();
        messageToSendRecord.setMessageJson(Mappers.getStringJson(message));
        messageToSendRecord.setDatetimeSend(LocalDateTime.now());
        messageToSendRecord.setIsSend(false);
        return messageToSendRecord;
    }

    @SneakyThrows
    private Message map(MessageToSendRecord messagesRecord){
        Message message = Mappers.getObjectJson(messagesRecord.getMessageJson(), Message.class);
        message.setId(messagesRecord.getId());
        return message;
    }
}
