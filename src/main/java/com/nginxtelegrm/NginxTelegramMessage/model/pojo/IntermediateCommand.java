package com.nginxtelegrm.NginxTelegramMessage.model.pojo;

import com.nginxtelegrm.NginxTelegramMessage.model.pojo.message.MessageRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IntermediateCommand {
    String command;
    List<String> params;
    MessageRequest messageRequest;
}
