package com.nginxtelegrm.NginxTelegramMessage.core.modeles;

import java.util.List;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.rule.Rule;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfigRules {
    List<Rule> rules;
}
