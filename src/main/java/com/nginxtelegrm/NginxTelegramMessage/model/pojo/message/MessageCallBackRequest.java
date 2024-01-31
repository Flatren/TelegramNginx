package com.nginxtelegrm.NginxTelegramMessage.model.pojo.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageCallBackRequest extends MessageData {

    Long userId;

    String userName;

    KeyCallBackRequest key;

}
