package com.nginxtelegrm.NginxTelegramMessage.modeles.rule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InfoCommand {
    List<String> params;
    List<String> types;
    Boolean contentFile;
    String description;
}
