package com.nginxtelegrm.NginxTelegramMessage.util;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

public class GenerateHtmlTextUtil {

    public static String i(String text){
        return "<i>" + text + "</i>";
    }

    public static String escape(String text){
        return escapeHtml4(text);
    }

    public static String b(String text){
        return "<b>" + text + "</b>";
    }

    public static String a(String url, String text){
        return "<a href=\""+url+"\">" + text + "</a>";
    }
}
