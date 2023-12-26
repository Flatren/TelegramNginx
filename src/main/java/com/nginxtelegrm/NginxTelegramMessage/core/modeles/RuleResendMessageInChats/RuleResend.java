package com.nginxtelegrm.NginxTelegramMessage.core.modeles.RuleResendMessageInChats;

import com.nginxtelegrm.NginxTelegramMessage.core.modeles.AddressChat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RuleResend {
    String nameRule;
    List<AddressChat> from;
    List<AddressChat> to;
    String info;
    String keyWord;
    List<RuleTime> rulesTime;
}
