package com.nginxtelegrm.NginxTelegramMessage.util.parse;

import java.util.ArrayList;

public class ParserUtil {

    public static ArrayList<String> textToArrayString(String text) {
        ArrayList<String> stringParam = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder();

        int textLength = text.length();
        boolean isOpen = true;
        char ch = ' ';
        boolean spec = false;
        for (int i = 0; i < textLength; i++) {

            ch = text.charAt(i);

            if (!spec && ch == '"') {
                isOpen = !isOpen;
            }

            if (isOpen && (ch != ' ' && ch != '\n')) {
                stringBuilder.append(ch);
            }

            if (isOpen && (ch == ' ' || ch == '\n')) {

                if (!stringBuilder.isEmpty()){
                    if (stringBuilder.length()>1 &&
                            stringBuilder.charAt(0) =='"' &&
                            stringBuilder.charAt(stringBuilder.length()-1) =='"'){
                        stringBuilder.deleteCharAt(0);
                        stringBuilder.deleteCharAt(stringBuilder.length()-1);
                    }
                    stringParam.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                }
            }

            if (spec){
                switch (ch) {
                    case '"', 'n', '\\' -> stringBuilder.append(ch);
                }
                spec = false;
                continue;
            }

            if (!isOpen && ch == '\\') {
                spec = true;
            }

            if (!isOpen && !spec) {
                stringBuilder.append(ch);
            }
        }
        if (!stringBuilder.isEmpty()){
            if (stringBuilder.length()>1 &&
                    stringBuilder.charAt(0) =='"' &&
                    stringBuilder.charAt(stringBuilder.length()-1) =='"'){
                stringBuilder.deleteCharAt(0);
                stringBuilder.deleteCharAt(stringBuilder.length()-1);
            }
            stringParam.add(stringBuilder.toString());
        }



        return stringParam;
    }
}
