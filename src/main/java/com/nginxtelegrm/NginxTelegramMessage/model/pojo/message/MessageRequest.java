package com.nginxtelegrm.NginxTelegramMessage.model.pojo.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.LinkFileTelegram;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MessageRequest extends MessageData {

    @JsonProperty("userId")
    Long userId;

    @JsonProperty("userName")
    String userName;

    @JsonProperty("file")
    LinkFileTelegram file;

    @JsonProperty("nameChat")
    String nameChat;

    @JsonProperty("isForum")
    boolean isForum;
}
