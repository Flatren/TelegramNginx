package com.nginxtelegrm.NginxTelegramMessage.model.dto.RuleResendOtherApplication;

import com.nginxtelegrm.NginxTelegramMessage.model.dto.RuleResendOtherApplication.RuleResendOtherApplicationDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RulesResendOtherApplicationDto {
    String name;
    String toAddress;
    String addressSurvey;
    String addressCallBack;
    List<RuleResendOtherApplicationDto> rules;
}
