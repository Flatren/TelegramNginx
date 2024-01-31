package com.nginxtelegrm.NginxTelegramMessage.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.Button;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageDto {
    @JsonProperty("address")
    String address; // chat:thread
    @JsonProperty("text")
    String text;
    @JsonProperty("buttons")
    List<Button> buttons;
    @JsonProperty("files")
    List<String> files;
    @JsonProperty("type")
    String type="send"; // send | edit
    @JsonProperty("idMessage")
    Integer idMessage;
}
