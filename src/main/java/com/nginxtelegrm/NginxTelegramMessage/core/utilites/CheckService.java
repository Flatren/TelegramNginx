package com.nginxtelegrm.NginxTelegramMessage.core.utilites;

import com.nginxtelegrm.NginxTelegramMessage.core.modeles.Intermediate.IntermediateCommand;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.ResultCheckTypeParams;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.rule.InfoCommand;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class CheckService {
    static Pattern patternCommand = Pattern.compile("^/\\w+[\s\n]?.*");
    static Pattern patternInteger = Pattern.compile("^[+-]?\\d+$");
    static Pattern patternDouble = Pattern.compile("^[+-]?\\d+[,.]?\\d*$");

    public static boolean isCommand(String text){
        return patternCommand.matcher(text).find();
    }

    public  static boolean isTrueType(String param, String type){
        return switch (type) {
            case "int" -> patternInteger.matcher(param).find();
            case "double" -> patternDouble.matcher(param).find();
            case "string" -> true;
            default -> false;
        };
    }

    public static ResultCheckTypeParams isTrueTypes(IntermediateCommand intermediateCommand, InfoCommand infoCommand){

        ResultCheckTypeParams resultCheckTypeParams = new ResultCheckTypeParams(true, new ArrayList<>());

        if (intermediateCommand.getParams().size() != infoCommand.getParams().size()){
            resultCheckTypeParams.setStatus(false);
            resultCheckTypeParams.getErrors().add(
                    "Количество параметров не соответствует ожидаемому:"
                    + "\nОжидается: " + infoCommand.getParams().size()
                    + "\nПолучено: " + intermediateCommand.getParams().size()
            ) ;
            return resultCheckTypeParams;
        }

        int countParams = intermediateCommand.getParams().size();

        for (int i = 0; i < countParams; i++){
            if (!isTrueType(intermediateCommand.getParams().get(i), infoCommand.getTypes().get(i))){
                resultCheckTypeParams.setStatus(false);
                resultCheckTypeParams.getErrors().add(
                        "Параметр "
                        + (i + 1)
                        + " имеет неверный тип данных."
                        + " Ожидается " + infoCommand.getTypes().get(i)
                );
            }
        }

        return resultCheckTypeParams;
    }

}
