package com.nginxtelegrm.NginxTelegramMessage.model.pojo;

import com.nginxtelegrm.NginxTelegramMessage.enums.MessageStatus;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.message.MessageResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Message {

    MessageStatus status;

    Long id; // ID в бд

    MessageResponse message;

    String nameOtherApplication;

    LocalDateTime dataUpdate;
}
