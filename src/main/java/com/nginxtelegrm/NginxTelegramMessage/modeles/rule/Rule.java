package com.nginxtelegrm.NginxTelegramMessage.modeles.rule;

import com.nginxtelegrm.NginxTelegramMessage.core.modeles.AddressChat;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.SimpleMessages;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rule {
    ArrayList<AddressChat> chats;
    ArrayList<Long> whiteListUsers;
    String toAddress;
    SimpleMessages simpleMessages;
    HashMap<String, InfoCommand> infoCommands;
}
