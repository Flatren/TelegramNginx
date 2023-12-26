package com.nginxtelegrm.NginxTelegramMessage.core.modeles.RuleResendMessageInChats;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RuleTime {
    Integer beginTimeMinutes;
    Integer endTimeMinutes;
    List<DayOfWeek> dayOfWeeks;
}
