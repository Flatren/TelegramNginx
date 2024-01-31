package com.nginxtelegrm.NginxTelegramMessage.service;

import com.nginxtelegrm.NginxTelegramMessage.model.pojo.AddressChat;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.message.MessageRequest;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.message.MessageResponse;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.RuleChatToChat.RuleChatToChat;
import com.nginxtelegrm.NginxTelegramMessage.repository.MessageRepository;
import com.nginxtelegrm.NginxTelegramMessage.repository.RulesChatToChatRepository;
import com.nginxtelegrm.NginxTelegramMessage.util.GenerateHtmlTextUtil;
import com.nginxtelegrm.NginxTelegramMessage.util.TelegramGenerateURLMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class RedirectingTheMessageFromChatToChatService {


    @Autowired
    RulesChatToChatRepository rulesChatToChatRepository;
    @Autowired
    MessageRepository messageRepository;

    private final int holdHours = 1;
    private final HashMap<String, LocalDateTime> longLocalDateTimeHashMap=new HashMap<>();

    public void direct(MessageRequest messageRequest){

        AddressChat addressChat = new AddressChat();

        addressChat.setIdChat(messageRequest.getIdChat());
        addressChat.setIdThread(messageRequest.getIdTread());

        List<RuleChatToChat> ruleResends = rulesChatToChatRepository.get(addressChat, messageRequest.getText());

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
                MessageResponse messageResponse = new MessageResponse();
                messageResponse.setIdChat(to.getIdChat());
                messageResponse.setIdTread(to.getIdThread());
                messageResponse.setText(
                        GenerateHtmlTextUtil.b(
                                GenerateHtmlTextUtil.a(TelegramGenerateURLMessageUtil.generateUrl(messageRequest), messageRequest.getNameChat())
                        )
                                +"\n"
                                + GenerateHtmlTextUtil.escape(ruleResend.getInfo())
                                +"\n\n"
                                + GenerateHtmlTextUtil.escape(messageRequest.getText()));
                messageRepository.insert(messageResponse);
            });
        });
    }
}
