package com.nginxtelegrm.NginxTelegramMessage.model.pojo.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageData {

    @JsonProperty("idMessage")
    Integer idMessage;

    @JsonProperty("chat")
    Long idChat;

    @JsonProperty("thread")
    Integer idTread;

    @JsonProperty("text")
    String text;
}
