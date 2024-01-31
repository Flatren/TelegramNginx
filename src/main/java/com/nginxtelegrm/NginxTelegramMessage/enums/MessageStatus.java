package com.nginxtelegrm.NginxTelegramMessage.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public enum MessageStatus {
    IS_SEND(0),
    ERROR_SEND(1),
    PROCESS_SEND(2),
    NO_IS_SEND(3);

    @Getter
    private final Integer key;
}
