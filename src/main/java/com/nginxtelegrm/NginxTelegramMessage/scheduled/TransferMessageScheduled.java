package com.nginxtelegrm.NginxTelegramMessage.scheduled;

import com.nginxtelegrm.NginxTelegramMessage.enums.MessageStatus;
import com.nginxtelegrm.NginxTelegramMessage.repository.DistributedMessageRepository;
import com.nginxtelegrm.NginxTelegramMessage.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TransferMessageScheduled {

    @Autowired
    DistributedMessageRepository distributedMessageRepository;
    @Autowired
    MessageRepository messageRepository;

    @Async
    @Scheduled(cron = "*/1 * * * * *")
    public void schedule(){
        messageRepository.getWhere(MessageStatus.NO_IS_SEND).forEach(
                message -> {
                    message.setStatus(MessageStatus.PROCESS_SEND);
                    messageRepository.UpdateStatus(message);
                    distributedMessageRepository.add(message);
                }
        );
    }
}
