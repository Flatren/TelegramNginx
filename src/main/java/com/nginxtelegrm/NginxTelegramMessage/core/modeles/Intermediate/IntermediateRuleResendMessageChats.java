package com.nginxtelegrm.NginxTelegramMessage.core.modeles.Intermediate;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class IntermediateRuleResendMessageChats {
    String nameRule;
    ArrayList<String> from;
    ArrayList<String> to;
    String info;
    String keyWord;
    ArrayList<IntermediateRuleTime> rulesTime;
}
