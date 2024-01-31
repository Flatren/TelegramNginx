package com.nginxtelegrm.NginxTelegramMessage.model.pojo.RuleResendOtherApplication;

import com.nginxtelegrm.NginxTelegramMessage.model.pojo.AddressChat;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class RuleResendOtherApplication {

    List<AddressChat> chats; // чаты, откуда принимать сообщения

    HashMap<String, InfoCommand> infoCommands; // название команды по типу "/help" и описание данной команды
}
