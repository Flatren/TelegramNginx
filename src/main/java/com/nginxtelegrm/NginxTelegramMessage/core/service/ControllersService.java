package com.nginxtelegrm.NginxTelegramMessage.core.service;

import com.nginxtelegrm.NginxTelegramMessage.core.controller.MessengerController;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.Intermediate.IntermediateCommand;
import com.nginxtelegrm.NginxTelegramMessage.modeles.Message;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

@Service
public class ControllersService {
    @Autowired
    private MeterRegistry meterRegistry;

    Dictionary<String, MessengerController> dictKeyControllers;
    Logger logger = LoggerFactory.getLogger(ControllersService.class);

    private Counter counterExecuteCommand;
    @PostConstruct
    public void PostConstruct(){

        counterExecuteCommand =
                Counter.builder("count execute command")
                        .description("Количество выполненных команд")
                        .register(meterRegistry);

    }
    public ControllersService(@Autowired List<MessengerController> messengerControllers) {
        dictKeyControllers = new Hashtable<>();
        for (MessengerController messengerController: messengerControllers) {
            logger.info("Добавлена команда: " + messengerController.getCOMMAND_PREFIX());
            dictKeyControllers.put(messengerController.getCOMMAND_PREFIX(), messengerController);
        }
    }

    public void execute(Message message, IntermediateCommand intermediateCommand) {
        MessengerController telegramController = dictKeyControllers.get(intermediateCommand.getCommand());
        if (telegramController != null)
        {
            telegramController.executeTry(message, intermediateCommand);
            counterExecuteCommand.increment();
        }
    }

}
