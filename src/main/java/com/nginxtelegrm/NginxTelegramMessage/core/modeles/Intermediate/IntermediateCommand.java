package com.nginxtelegrm.NginxTelegramMessage.core.modeles.Intermediate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Dictionary;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IntermediateCommand {
    String command;
    List<String> params;
}
