package com.nginxtelegrm.NginxTelegramMessage.model.pojo.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeyCallBackRequest {
    String application;
    String value;
}
