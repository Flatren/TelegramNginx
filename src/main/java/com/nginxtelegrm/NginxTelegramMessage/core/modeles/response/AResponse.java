package com.nginxtelegrm.NginxTelegramMessage.core.modeles.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AResponse {
    @JsonProperty("CODE")
    EnumCodeResponse code = EnumCodeResponse.SUCCESS;
    @JsonProperty("TEXT")
    String text;
    @JsonProperty("MESSAGE")
    Message message;
}
