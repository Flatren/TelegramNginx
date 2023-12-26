package com.nginxtelegrm.NginxTelegramMessage.modeles.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class AResponse {
    @JsonProperty("CODE")
    public EnumCodeResponse code = EnumCodeResponse.SUCCESS;
    @JsonProperty("MESSAGE")
    public String message = "";
}
