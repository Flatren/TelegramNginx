package com.nginxtelegrm.NginxTelegramMessage.core.service;

import com.nginxtelegrm.NginxTelegramMessage.core.collection.SendMessageCollection;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.AddressChat;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.RuleResendMessageInChats.RuleResend;
import com.nginxtelegrm.NginxTelegramMessage.core.repositoryes.RuleResendMessageRepository;
import com.nginxtelegrm.NginxTelegramMessage.core.utilites.GenerateHtmlText;
import com.nginxtelegrm.NginxTelegramMessage.modeles.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class ResendMessageService {
    private int holdHours = 1;
    @Autowired
    private RuleResendMessageRepository ruleResendMessageRepository;
    @Autowired
    private SendMessageCollection sendMessageCollection;

    HashMap<String, LocalDateTime> longLocalDateTimeHashMap=new HashMap<>();

    private String generateUrl(Message message){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("https://t.me/c/")
                .append(message.getIdChat().toString().replace("-100", ""))
                .append("/");

        if (message.isForum())
            stringBuilder
                    .append((message.getIdTread()==0 ? 1 : message.getIdTread()))
                    .append("/");
        stringBuilder.append(message.getIdMessage());
        return stringBuilder.toString();
    }

    public void execute(Message message){
        AddressChat addressChat = new AddressChat();

        addressChat.setIdChat(message.getIdChat());
        addressChat.setIdThread(message.getIdTread());

        List<RuleResend> ruleResends = ruleResendMessageRepository.select(addressChat, message.getText());



        ruleResends.forEach(ruleResend -> {
            if (ruleResend.getKeyWord() == null)
            {
               LocalDateTime localDateTime = longLocalDateTimeHashMap.get(ruleResend.getNameRule());
               if (localDateTime != null) {
                   if (LocalDateTime.now().isBefore(localDateTime))
                       return;
                   else
                       longLocalDateTimeHashMap.put(ruleResend.getNameRule(), LocalDateTime.now().plusHours(holdHours));
               }
               else{
                   longLocalDateTimeHashMap.put(ruleResend.getNameRule(), LocalDateTime.now().plusHours(holdHours));
               }

            }
            ruleResend.getTo().forEach(to->{
                Message sendMessage = new Message(to.getIdChat(), to.getIdThread());
                sendMessage.setText(
                                    GenerateHtmlText.b(
                                        GenerateHtmlText.a(generateUrl(message), message.getNameChat())
                                    )
                                    +"\n"
                                    + GenerateHtmlText.escape(ruleResend.getInfo())
                                    +"\n\n"
                                    + GenerateHtmlText.escape(message.getText()));
                sendMessageCollection.addMessage(sendMessage, false);
            });
        });


    }

}
