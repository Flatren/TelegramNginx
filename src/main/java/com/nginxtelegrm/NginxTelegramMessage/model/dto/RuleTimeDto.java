package com.nginxtelegrm.NginxTelegramMessage.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RuleTimeDto {
    String begin;
    String end;
    ArrayList<String> dayOfWeeks;
}
