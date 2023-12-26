package com.nginxtelegrm.NginxTelegramMessage.core.modeles.Intermediate;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class IntermediateRuleTime {
    String begin;
    String end;
    ArrayList<String> dayOfWeeks;
}
