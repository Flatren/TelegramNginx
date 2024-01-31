package com.nginxtelegrm.NginxTelegramMessage.util.parse;

import java.util.regex.Pattern;

public class ConvertUtil {

    static Pattern patternInteger = Pattern.compile("^[+-]?\\d+$");

    static Pattern patternDouble = Pattern.compile("^[+-]?\\d+[,.]?\\d*$");

    public  static boolean isTrueType(String param, String type){
        return switch (type) {
            case "int" -> patternInteger.matcher(param).find();
            case "double" -> patternDouble.matcher(param).find();
            case "string" -> true;
            default -> false;
        };
    }
}
