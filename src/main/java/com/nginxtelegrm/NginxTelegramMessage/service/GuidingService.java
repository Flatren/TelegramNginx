package com.nginxtelegrm.NginxTelegramMessage.service;

import com.nginxtelegrm.NginxTelegramMessage.model.pojo.IntermediateCommand;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.message.MessageCallBackRequest;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.message.MessageRequest;
import com.nginxtelegrm.NginxTelegramMessage.util.CommandUtil;
import com.nginxtelegrm.NginxTelegramMessage.util.IntermediateCommandUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/*
* Занимаетья логикой перенаправления сообщений пришедших от телеграмма
*/
@Service
public class GuidingService {

    @Autowired // обрабаывает команды от телеграмма
    ControllerService controllerService;

    @Autowired // перенаправляет сообщения из чата в другой чат
    RedirectingTheMessageFromChatToChatService redirectingTheMessageChatToChatService;

    @Autowired // направляет сообщения в иное приложение, для дальнейшой обработки
    RedirectingTheMessageFromChatToOtherAppService redirectingTheMessageFromChatToOtherApp;

    @Autowired
    RedirectingTheCallBackFromChatToOtherAppService redirectingTheCallBackFromChatToOtherAppService;

    public void direct(MessageRequest messageRequest){

        boolean isCommand = CommandUtil.isCommand(messageRequest.getText());

        if (isCommand) {
            IntermediateCommand intermediateCommand = IntermediateCommandUtil.parse(messageRequest.getText());
            intermediateCommand.setMessageRequest(messageRequest);
            controllerService.direct(intermediateCommand);
            redirectingTheMessageFromChatToOtherApp.direct(intermediateCommand);
        }else{
            redirectingTheMessageChatToChatService.direct(messageRequest);
        }
    }

    @SneakyThrows
    public void direct(MessageCallBackRequest messageRequest){
        if (messageRequest !=null)
            redirectingTheCallBackFromChatToOtherAppService.direct(messageRequest);
    }
}
