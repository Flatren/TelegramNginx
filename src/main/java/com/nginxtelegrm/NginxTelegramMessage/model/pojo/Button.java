package com.nginxtelegrm.NginxTelegramMessage.model.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Button {
    Long key = null;
    String application = null;
    String viewText = null;
}
