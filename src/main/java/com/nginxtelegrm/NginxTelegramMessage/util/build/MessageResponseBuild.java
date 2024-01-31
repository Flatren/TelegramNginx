package com.nginxtelegrm.NginxTelegramMessage.util.build;

import com.nginxtelegrm.NginxTelegramMessage.enums.PriorityMessageEnum;
import com.nginxtelegrm.NginxTelegramMessage.model.dto.MessageDto;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.AddressChat;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.message.MessageResponse;
import lombok.SneakyThrows;

public class MessageResponseBuild {
    public static MessageResponse logMessage(AddressChat addressChat, String text){
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setText(text);
        messageResponse.setIdChat(addressChat.getIdChat());
        messageResponse.setIdTread(addressChat.getIdThread());
        messageResponse.setPriorityMessage(PriorityMessageEnum.IMPORTANT);
        messageResponse.setIsLogMessage(true);
        return messageResponse;
    }
    @SneakyThrows
    public static MessageResponse buildFromMessageDto(MessageDto messageDto){
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setText(messageDto.getText());
        messageResponse.setButtons(messageDto.getButtons());
        messageResponse.setIdMessage(messageDto.getIdMessage());
        AddressChat addressChat = new AddressChat(messageDto.getAddress());
        messageResponse.setIdChat(addressChat.getIdChat());
        messageResponse.setIdTread(addressChat.getIdThread());
        messageResponse.setType(messageDto.getType());
        return messageResponse;
    }
}
