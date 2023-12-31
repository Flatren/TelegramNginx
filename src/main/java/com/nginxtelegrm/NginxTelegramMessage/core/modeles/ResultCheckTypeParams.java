package com.nginxtelegrm.NginxTelegramMessage.core.modeles;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResultCheckTypeParams {
    boolean status;
    ArrayList<String> errors;
}
