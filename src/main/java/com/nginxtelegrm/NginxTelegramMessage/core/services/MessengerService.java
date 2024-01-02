package com.nginxtelegrm.NginxTelegramMessage.core.services;
import com.nginxtelegrm.NginxTelegramMessage.core.enums.TypeError;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.Intermediate.IntermediateCommand;
import com.nginxtelegrm.NginxTelegramMessage.core.repositoryes.ErrorMessageRepository;
import com.nginxtelegrm.NginxTelegramMessage.core.repositoryes.MessageToSendRepository;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.Message;
import com.nginxtelegrm.NginxTelegramMessage.core.utilites.CheckService;
import com.nginxtelegrm.NginxTelegramMessage.core.utilites.Parser;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessengerService {
    @Autowired
    private MeterRegistry meterRegistry;
    @Autowired
    MessageToSendRepository messageToSendRepository;
    @Autowired
    ResendingService resendingService;
    @Autowired
    ControllersService controllersService;
    @Autowired
    ResendMessageService resendMessageService;
    @Autowired
    LoggerMessengerService loggerMessengerService;

    Logger logger = LoggerFactory.getLogger(MessengerService.class);

    @Autowired
    ErrorMessageRepository errorMessageRepository;

    private Double counterMessagesTemp=0.0;
    private Double counterSendMessageTemp=0.0;
    private Double counterErrorSendMessageTemp=0.0;

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

    public List<Message> getListSendMessage(){
        return messageToSendRepository.getMessageToSend();
    }

    public void processTheMessage(Message message){

        counterMessages.increment();
        if (CheckService.isCommand(message.getText())) {
            IntermediateCommand intermediateCommand = Parser.parseStringToIntermediateCommand(message.getText());
            controllersService.execute(message, intermediateCommand);
            resendingService.resendingCommand(message, intermediateCommand);
        }else{
            resendMessageService.execute(message);
            resendingService.resendingMessage(message);
        }

    }

    // Каждый час предоставляет статистику в мессенджер
    @Scheduled(fixedDelay = 1000*60*60)
    public void showStatistic(){
        loggerMessengerService.INFO(
                "Отпрвлено удачно: " + (counterSendMessage.count()-counterMessagesTemp) +
                "\nОшибок при отпрвлении: " + (counterErrorSendMessage.count()-counterSendMessageTemp) +
                "\nПопыток отпрвить всего: " + (counterMessages.count()-counterErrorSendMessageTemp)
        );
        counterMessagesTemp = counterMessages.count();
        counterSendMessageTemp = counterSendMessage.count();
        counterErrorSendMessageTemp = counterErrorSendMessage.count();
    }

    //Сообщить о том, что произошла ошибка
    public void ReportError(Message message, String report, TypeError typeError){
        counterErrorSendMessage.increment();
        if (typeError == TypeError.INTERNET)
            messageToSendRepository.insert(message, true);
        errorMessageRepository.insert(message, report);
        if (!message.getIsLogMessage())
            loggerMessengerService.ERROR(report);
    }

    //Подтвердить, что сообщение отправлено
    public void confirmSendingTheMessage(Message message){
        counterSendMessage.increment();
        messageToSendRepository.update(message);
    }
}
