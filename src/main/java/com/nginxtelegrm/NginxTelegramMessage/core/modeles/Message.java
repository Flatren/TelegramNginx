package com.nginxtelegrm.NginxTelegramMessage.core.modeles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    public Message(Long idChat,Integer idTread){
        this.idChat = idChat;
        this.idTread = idTread;
    }

    @JsonIgnore
    org.telegram.telegrambots.meta.api.objects.Message message;
    @JsonIgnore
    Boolean isLogMessage=false;
    @JsonIgnore
    Long id;
    @JsonProperty("idMessage")
    Integer idMessage;
    @JsonProperty("chat")
    Long idChat;
    @JsonProperty("thread")
    Integer idTread;
    @JsonProperty("text")
    String text;
    @JsonProperty("userId")
    Long userId;
    @JsonProperty("userName")
    String userName;
    @JsonProperty("files")
    List<String> files;
    @JsonProperty("buttons")
    List<String> buttons;
    @JsonProperty("nameChat")
    String nameChat;
    @JsonProperty("isForum")
    boolean isForum;
}
