package com.nginxtelegrm.NginxTelegramMessage.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RuleChatToChatDto {
    String nameRule;
    ArrayList<String> from;
    ArrayList<String> to;
    String info;
    String keyWord;
    ArrayList<RuleTimeDto> rulesTime;
}
