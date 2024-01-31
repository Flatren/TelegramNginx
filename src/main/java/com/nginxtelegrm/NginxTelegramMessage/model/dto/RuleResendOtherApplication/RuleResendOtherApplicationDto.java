package com.nginxtelegrm.NginxTelegramMessage.model.dto.RuleResendOtherApplication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
public class RuleResendOtherApplicationDto {
    ArrayList<String> chats; // чаты, откуда принимать сообщения формат: (id_chat:id_thread)
    HashMap<String, InfoCommandDto> infoCommands; // название команды по типу "/help" и описание данной команды
}
