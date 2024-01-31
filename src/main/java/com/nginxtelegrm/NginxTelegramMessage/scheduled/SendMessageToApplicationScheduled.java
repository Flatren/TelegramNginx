package com.nginxtelegrm.NginxTelegramMessage.scheduled;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nginxtelegrm.NginxTelegramMessage.Telegram;
import com.nginxtelegrm.NginxTelegramMessage.core.interfaces.FSRepository;
import com.nginxtelegrm.NginxTelegramMessage.model.dto.MessageDto;
import com.nginxtelegrm.NginxTelegramMessage.model.param.messageApplication.OutMessageAppParam;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.InfoFile;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.message.MessageResponse;
import com.nginxtelegrm.NginxTelegramMessage.repository.MessageApplicationRepository;
import com.nginxtelegrm.NginxTelegramMessage.repository.MessageRepository;
import com.nginxtelegrm.NginxTelegramMessage.util.GetFileInputStreamUtil;
import com.nginxtelegrm.NginxTelegramMessage.util.build.MessageResponseBuild;
import com.nginxtelegrm.NginxTelegramMessage.util.http.HttpRequestUtil;
import com.nginxtelegrm.NginxTelegramMessage.util.parse.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.File;

@Component
public class SendMessageToApplicationScheduled {
    @Autowired
    Telegram telegram;
    @Autowired
    MessageApplicationRepository messageApplicationRepository;
    @Autowired
    FSRepository fsRepository;
    @Autowired
    MessageRepository messageRepository;
    private static final Logger LOG = LoggerFactory.getLogger(SendMessageToApplicationScheduled.class);

    void oneAction(OutMessageAppParam outParam){
        try{
            if (outParam.getLinkFileTelegram() != null) {
                GetFile getFile = new GetFile(outParam.getLinkFileTelegram().getIdFile());
                File file = telegram.execute(getFile);
                InfoFile infoFile = fsRepository.createFile(
                        outParam.getLinkFileTelegram().getNameFile(),
                        GetFileInputStreamUtil.getInputStream(file.getFilePath()));
                outParam.setFile(JsonUtil.toJson(infoFile));
            }
            MessageDto msg = HttpRequestUtil
                    .sendRequest( outParam.getUrl().toString(), outParam.getParams(),
                            new TypeReference<MessageDto>() {});
            if (msg != null){
                MessageResponse messageResponse = MessageResponseBuild.buildFromMessageDto(msg);
                messageRepository.insert(messageResponse);
            }
        }catch (Exception e){
            LOG.error(e.getMessage());
        }
    }

    @Async
    @Scheduled(cron = "*/1 * * * * *")
    void schedule() {
        OutMessageAppParam outParam = messageApplicationRepository.get();
        while (outParam != null) {
            oneAction(outParam);
            outParam = messageApplicationRepository.get();
        }

    }
}
