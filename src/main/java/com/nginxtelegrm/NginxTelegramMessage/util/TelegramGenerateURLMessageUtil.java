package com.nginxtelegrm.NginxTelegramMessage.util;

import com.nginxtelegrm.NginxTelegramMessage.model.pojo.message.MessageRequest;

public class TelegramGenerateURLMessageUtil {
    public static String generateUrl(MessageRequest messageRequest){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("https://t.me/c/")
                .append(messageRequest.getIdChat().toString().replace("-100", ""))
                .append("/");

        if (messageRequest.isForum())
            stringBuilder
                    .append((messageRequest.getIdTread()==0 ? 1 : messageRequest.getIdTread()))
                    .append("/");
        stringBuilder.append(messageRequest.getIdMessage());
        return stringBuilder.toString();
    }
}
