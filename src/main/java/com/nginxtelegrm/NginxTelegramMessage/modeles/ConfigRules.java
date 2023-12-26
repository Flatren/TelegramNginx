package com.nginxtelegrm.NginxTelegramMessage.modeles;

import java.util.List;
import com.nginxtelegrm.NginxTelegramMessage.modeles.rule.Rule;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfigRules {
    List<Rule> rules;
}
