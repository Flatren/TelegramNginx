package com.nginxtelegrm.NginxTelegramMessage.core.service;
import com.nginxtelegrm.NginxTelegramMessage.core.enums.TypeError;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.Intermediate.IntermediateCommand;
import com.nginxtelegrm.NginxTelegramMessage.core.repositoryes.MessageSendRepository;
import com.nginxtelegrm.NginxTelegramMessage.core.repositoryes.ReceivedMessageRepository;
import com.nginxtelegrm.NginxTelegramMessage.modeles.Message;
import com.nginxtelegrm.NginxTelegramMessage.services.CheckService;
import com.nginxtelegrm.NginxTelegramMessage.services.Parser;
import com.nginxtelegrm.NginxTelegramMessage.services.ResendingService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessengerService {
    @Autowired
    private MeterRegistry meterRegistry;
    @Autowired
    MessageSendRepository messageSendRepository;
    @Autowired
    ResendingService resendingService;
    @Autowired
    ControllersService controllersService;
    @Autowired
    ResendMessageService resendMessageService;

    Logger logger = LoggerFactory.getLogger(MessengerService.class);

    @Autowired
    ReceivedMessageRepository receivedMessageRepository;
    @Autowired
    MessageToSendService messageToSendService;

    private Counter counterMessages;
    private Counter counterSendMessage;
    private Counter counterErrorSendMessage;

    @PostConstruct
    public void PostConstruct(){
        counterMessages =
                Counter.builder("all received message")
                        .description("Всего сообщений, полученные ботом")
                        .register(meterRegistry);
        counterSendMessage =
                Counter.builder("send message")
                        .description("Всего сообщений, отправленных ботом")
                        .register(meterRegistry);
        counterErrorSendMessage =
                Counter.builder("error send message")
                        .description("Количество ошибок при отправке")
                        .register(meterRegistry);
    }

    public void readAllMessageUnseen(){
        // TODO
        //Запускаем раз во сколько то времени
        //Берем все сообщения из бд
        //запускаем processTheMessage для каждого сообщения
        //обрабатываем ошибки
        //меняем статусы
    }

    public List<Message> getListSendMessage(){
        return messageSendRepository.getMessageToSend();
    }

    public void processTheMessage(Message message){
        receivedMessageRepository.insert(message);
        counterMessages.increment();
        if (CheckService.isCommand(message.getText())) {
            IntermediateCommand intermediateCommand = Parser.parseStringToIntermediateCommand(message.getText());
            controllersService.execute(message, intermediateCommand);
            resendingService.resendingCommand(message, intermediateCommand);
        }else{
            resendMessageService.execute(message);
            resendingService.resendingMessage(message);
        }
        receivedMessageRepository.setTrueIsProcessed(message);
    }

    //Сообщить о том, что произошла ошибка
    public void ReportError(Message message, String report, TypeError typeError){
        // TODO
    }

    //Подтвердить, что сообщение отправлено
    public void confirmSendingTheMessage(Message message, Integer idMessage){
        // TODO
    }
}
