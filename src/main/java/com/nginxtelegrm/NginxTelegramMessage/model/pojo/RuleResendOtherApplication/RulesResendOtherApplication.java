package com.nginxtelegrm.NginxTelegramMessage.model.pojo.RuleResendOtherApplication;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RulesResendOtherApplication {
    String name;
    String toAddress;
    String addressSurvey;
    String addressCallBack;
    List<RuleResendOtherApplication> rules;
}
