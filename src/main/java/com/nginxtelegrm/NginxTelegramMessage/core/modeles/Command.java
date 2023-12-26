package com.nginxtelegrm.NginxTelegramMessage.core.modeles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Command {

    String command;
    HashMap<String, String> params;

}
