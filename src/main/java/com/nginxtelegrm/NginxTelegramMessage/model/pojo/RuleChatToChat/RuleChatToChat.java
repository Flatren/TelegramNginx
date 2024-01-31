package com.nginxtelegrm.NginxTelegramMessage.model.pojo.RuleChatToChat;

import com.nginxtelegrm.NginxTelegramMessage.model.pojo.AddressChat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RuleChatToChat {
    String nameRule;
    List<AddressChat> from;
    List<AddressChat> to;
    String info;
    String keyWord;
    List<RuleTime> rulesTime;
}
