package com.nginxtelegrm.NginxTelegramMessage.util;

import java.util.regex.Pattern;

public class CommandUtil {

    static Pattern patternCommand = Pattern.compile("^/\\w+[\s\n]?.*");

    public static boolean isCommand(String text){
        return patternCommand.matcher(text).find();
    }


}
