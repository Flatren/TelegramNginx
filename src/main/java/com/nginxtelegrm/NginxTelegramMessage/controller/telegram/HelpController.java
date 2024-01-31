package com.nginxtelegrm.NginxTelegramMessage.controller.telegram;

import com.nginxtelegrm.NginxTelegramMessage.controller.telegram.role.CommonRole;
import com.nginxtelegrm.NginxTelegramMessage.core.abstracts.MessengerController;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.IntermediateCommand;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.message.MessageResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class HelpController extends MessengerController {

    @Override
    public List<String> getCommands(){
        return List.of("/help", "/info");
    }

    public HelpController(CommonRole commonRole) {
        super(commonRole);
    }

    private Optional<MessageResponse> help(IntermediateCommand intermediateCommand){
        MessageResponse messageResponse = new MessageResponse();

        messageResponse.setIdChat(intermediateCommand.getMessageRequest().getIdChat());
        messageResponse.setIdTread(intermediateCommand.getMessageRequest().getIdTread());

        messageResponse.setText("Привет, я не помогу!!!");

        return Optional.of(messageResponse);
    }

    private Optional<MessageResponse> info(IntermediateCommand intermediateCommand){
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setIdChat(intermediateCommand.getMessageRequest().getIdChat());
        messageResponse.setIdTread(intermediateCommand.getMessageRequest().getIdTread());
        messageResponse.setText(
                "Id chat: " + intermediateCommand.getMessageRequest().getIdChat() +
                "\n" +
                "Id thread: " + intermediateCommand.getMessageRequest().getIdTread());
        return Optional.of(messageResponse);
    }

    @Override
    public Optional<MessageResponse> sendToTheMethod(IntermediateCommand intermediateCommand){
        return switch (intermediateCommand.getCommand()){
            case "/help" -> help(intermediateCommand);
            case "/info" -> info(intermediateCommand);
            default -> Optional.empty();
        };
    }
}
