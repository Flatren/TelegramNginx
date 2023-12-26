package com.nginxtelegrm.NginxTelegramMessage.modeles.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EnumCodeResponse {
    ERROR("error"),
    SUCCESS("success");

    @Getter
    private final String description;
}
