package com.nginxtelegrm.NginxTelegramMessage.util;

import com.nginxtelegrm.NginxTelegramMessage.enums.PriorityMessageEnum;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.message.MessageRequest;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.message.MessageResponse;

public class MessageResponseUtil {
    public static MessageResponse build(MessageRequest messageRequest){
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setIdChat(messageRequest.getIdChat());
        messageResponse.setIdTread(messageRequest.getIdTread());
        messageResponse.setPriorityMessage(PriorityMessageEnum.IMPORTANT);
        return messageResponse;
    }
}
