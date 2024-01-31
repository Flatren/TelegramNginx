package com.nginxtelegrm.NginxTelegramMessage.model.pojo.message;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nginxtelegrm.NginxTelegramMessage.enums.PriorityMessageEnum;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.Button;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.InfoFile;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MessageResponse extends MessageData {

    @JsonProperty("priorityMessage")
    PriorityMessageEnum priorityMessage = PriorityMessageEnum.USUAL;

    @JsonIgnore
    Boolean isLogMessage=false;

    @JsonProperty("buttons")
    List<Button> buttons;

    @JsonProperty("files")
    List<InfoFile> infoFiles;

    @JsonProperty("type")
    String type = "send";
}
