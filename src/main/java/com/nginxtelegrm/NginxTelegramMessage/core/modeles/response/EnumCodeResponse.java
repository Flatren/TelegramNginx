package com.nginxtelegrm.NginxTelegramMessage.core.modeles.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EnumCodeResponse {
    ERROR("error"),
    SUCCESS("success");

    @Getter
    private final String description;
}
