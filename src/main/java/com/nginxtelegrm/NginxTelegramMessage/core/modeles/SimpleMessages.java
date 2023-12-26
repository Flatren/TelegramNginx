package com.nginxtelegrm.NginxTelegramMessage.core.modeles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleMessages {
    String to;
    Boolean contentFiles;
}
