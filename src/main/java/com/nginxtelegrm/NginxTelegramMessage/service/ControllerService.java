package com.nginxtelegrm.NginxTelegramMessage.service;

import com.nginxtelegrm.NginxTelegramMessage.core.abstracts.MessengerController;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.IntermediateCommand;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.message.MessageResponse;
import com.nginxtelegrm.NginxTelegramMessage.repository.MessageRepository;
import com.nginxtelegrm.NginxTelegramMessage.util.GenerateHtmlTextUtil;
import com.nginxtelegrm.NginxTelegramMessage.util.MessageResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ControllerService {
    HashMap<String, MessengerController> messengerControllerHashMap;
    @Autowired
    MessageRepository messageRepository;
    Logger logger = LoggerFactory.getLogger(ControllerService.class);

    public ControllerService(@Autowired List<MessengerController> controllers){
        messengerControllerHashMap = new HashMap<>();
        controllers.forEach(controller->{
            controller.getCommands().forEach(
                    command->{
                        logger.info("Добавлена команда: " + command);
                        messengerControllerHashMap.put(command, controller);
                    }
            );
        });
    }

    public void direct(IntermediateCommand intermediateCommand) {
        MessengerController messengerController = messengerControllerHashMap.get(intermediateCommand.getCommand());
        if (messengerController != null)
            if(messengerController.filter(intermediateCommand.getMessageRequest())){
                try {
                    Optional<MessageResponse> messageResponse = messengerController.sendToTheMethod(intermediateCommand);
                    messageResponse.ifPresent(message ->{
                        messageRepository.insert(message);
                    });
                }catch (Exception e){
                    logger.error(e.getMessage());
                    MessageResponse messageResponse = MessageResponseUtil.build(intermediateCommand.getMessageRequest());
                    messageResponse.setText(GenerateHtmlTextUtil.escape(e.getMessage()));
                    messageRepository.insert(messageResponse);
                }
            }
    }
}
