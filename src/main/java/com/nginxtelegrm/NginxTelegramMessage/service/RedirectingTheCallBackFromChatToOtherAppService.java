package com.nginxtelegrm.NginxTelegramMessage.service;

import com.nginxtelegrm.NginxTelegramMessage.model.param.messageApplication.OutMessageAppParam;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.AddressChat;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.RuleResendOtherApplication.RulesResendOtherApplication;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.message.MessageCallBackRequest;
import com.nginxtelegrm.NginxTelegramMessage.repository.MessageApplicationRepository;
import com.nginxtelegrm.NginxTelegramMessage.repository.MessageRepository;
import com.nginxtelegrm.NginxTelegramMessage.repository.RulesResendOtherApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Service
public class RedirectingTheCallBackFromChatToOtherAppService {

    @Autowired
    RulesResendOtherApplicationRepository rulesResendOtherApplicationRepository;
    @Autowired
    MessageApplicationRepository messageApplicationRepository;

    public void direct(MessageCallBackRequest messageRequest) throws MalformedURLException {

        RulesResendOtherApplication rules =
                rulesResendOtherApplicationRepository.get(messageRequest.getKey().getApplication());

        if (rules == null)
            return;

        OutMessageAppParam outMessageAppParam = new OutMessageAppParam();

        outMessageAppParam.setIdMessage(messageRequest.getIdMessage().toString());

        outMessageAppParam.setChat(new AddressChat(messageRequest.getIdChat(), messageRequest.getIdTread()).ToString());

        outMessageAppParam.setIdUser(messageRequest.getUserId().toString());

        outMessageAppParam.setUrl(new URL(rules.getAddressCallBack()));

        outMessageAppParam.setParam("key", messageRequest.getKey().getValue());

        messageApplicationRepository.insert(outMessageAppParam);

    }

}
