package com.nginxtelegrm.NginxTelegramMessage.util.parse;

import java.time.DayOfWeek;

public class DayOfWeekUtil {

    public static DayOfWeek parse(String dayOfWeek) throws Exception {
        return switch (dayOfWeek) {
            case "пн" -> DayOfWeek.of(1);
            case "вт" -> DayOfWeek.of(2);
            case "ср" -> DayOfWeek.of(3);
            case "чт" -> DayOfWeek.of(4);
            case "пт" -> DayOfWeek.of(5);
            case "сб" -> DayOfWeek.of(6);
            case "вс" -> DayOfWeek.of(7);
            default -> throw new Exception("День недели задан неверно: " + dayOfWeek);
        };
    }
}
