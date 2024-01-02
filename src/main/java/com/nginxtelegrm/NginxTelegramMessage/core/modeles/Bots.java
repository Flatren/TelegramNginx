package com.nginxtelegrm.NginxTelegramMessage.core.modeles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bots {
    HashMap<String, String> bots;
}
