package com.nginxtelegrm.NginxTelegramMessage.core.map;

import com.nginxtelegrm.NginxTelegramMessage.core.exceptions.MapException;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.AddressChat;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.Intermediate.IntermediateRuleResendMessageChats;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.Intermediate.IntermediateRuleTime;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.RuleResendMessageInChats.RuleResend;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.RuleResendMessageInChats.RuleTime;
import lombok.SneakyThrows;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class Map {

    @SneakyThrows
    public static RuleResend map(IntermediateRuleResendMessageChats intermediateRuleResendMessageChats){

        // TODO дописать все проверки
        if (intermediateRuleResendMessageChats.getFrom() == null)
            throw new MapException("Не задан from");
        if (intermediateRuleResendMessageChats.getFrom().size() == 0)
            throw new MapException("Не задан from");
        if (intermediateRuleResendMessageChats.getTo() == null)
            throw new MapException("Не задан to");
        if (intermediateRuleResendMessageChats.getTo().size() == 0)
            throw new MapException("Не задан to");

        RuleResend ruleResend = new RuleResend();
        ruleResend.setInfo(intermediateRuleResendMessageChats.getInfo());
        ruleResend.setKeyWord(intermediateRuleResendMessageChats.getKeyWord());
        ruleResend.setNameRule(intermediateRuleResendMessageChats.getNameRule());

        ruleResend.setFrom(
                intermediateRuleResendMessageChats.getFrom()
                        .stream()
                        .map(AddressChat::new).toList());

        ruleResend.setTo(
                intermediateRuleResendMessageChats.getTo()
                        .stream()
                        .map(AddressChat::new).toList());

        if (intermediateRuleResendMessageChats.getRulesTime() != null)
            ruleResend.setRulesTime(
                    intermediateRuleResendMessageChats.getRulesTime().stream().map(Map::map).toList()
            );
        else
            ruleResend.setRulesTime(List.of());
        return ruleResend;
    }
    @SneakyThrows
    public static RuleResend mapSimple(IntermediateRuleResendMessageChats intermediateRuleResendMessageChats){
        if (intermediateRuleResendMessageChats.getNameRule() == null)
            throw new MapException("Не задано название.");

        RuleResend ruleResend = new RuleResend();
        ruleResend.setInfo(intermediateRuleResendMessageChats.getInfo());
        ruleResend.setKeyWord(intermediateRuleResendMessageChats.getKeyWord());
        ruleResend.setNameRule(intermediateRuleResendMessageChats.getNameRule());

        if (intermediateRuleResendMessageChats.getFrom() != null)
            ruleResend.setFrom(
                intermediateRuleResendMessageChats.getFrom()
                        .stream()
                        .map(AddressChat::new).toList());

        if (intermediateRuleResendMessageChats.getTo() != null)
            ruleResend.setTo(
                intermediateRuleResendMessageChats.getTo()
                        .stream()
                        .map(AddressChat::new).toList());

        if (intermediateRuleResendMessageChats.getRulesTime() != null)
            ruleResend.setRulesTime(
                    intermediateRuleResendMessageChats.getRulesTime().stream().map(Map::map).toList()
            );

        return ruleResend;
    }
    @SneakyThrows
    public static DayOfWeek map(String dayOfWeek){
        return switch (dayOfWeek) {
            case "пн" -> DayOfWeek.of(1);
            case "вт" -> DayOfWeek.of(2);
            case "ср" -> DayOfWeek.of(3);
            case "чт" -> DayOfWeek.of(4);
            case "пт" -> DayOfWeek.of(5);
            case "сб" -> DayOfWeek.of(6);
            case "вс" -> DayOfWeek.of(7);
            default -> throw new MapException("День недели задан неверно: " + dayOfWeek);
        };
    }

    @SneakyThrows
    public static RuleTime map(IntermediateRuleTime intermediateRuleTime){
        // TODO сделать проверку на корректность задаваемого времени
        if (intermediateRuleTime == null)
            return null;
        RuleTime ruleTime = new RuleTime();
        int minutes;
        String[] hourAndMinute;
        if (intermediateRuleTime.getBegin() == null)
            ruleTime.setBeginTimeMinutes(0);
        else{
            hourAndMinute = intermediateRuleTime.getBegin().split(":");
            minutes = Integer.parseInt(hourAndMinute[0]) * 60 + Integer.parseInt(hourAndMinute[1]);
            ruleTime.setBeginTimeMinutes(minutes);
        }
        if (intermediateRuleTime.getEnd() == null)
            ruleTime.setEndTimeMinutes(0);
        else{
            hourAndMinute = intermediateRuleTime.getEnd().split(":");
            minutes = Integer.parseInt(hourAndMinute[0]) * 60 + Integer.parseInt(hourAndMinute[1]);
            ruleTime.setEndTimeMinutes(minutes);
        }
        if (intermediateRuleTime.getDayOfWeeks() == null)
            ruleTime.setDayOfWeeks(List.of());
        else
            ruleTime.setDayOfWeeks(
                            intermediateRuleTime.getDayOfWeeks()
                                    .stream()
                                    .map(Map::map)
                                    .toList()
            );
        return ruleTime;
    }
}
