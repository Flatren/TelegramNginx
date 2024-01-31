package com.nginxtelegrm.NginxTelegramMessage.model.pojo.RuleChatToChat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RuleTime {
    Integer beginTimeMinutes;
    Integer endTimeMinutes;
    List<DayOfWeek> dayOfWeeks;
}
