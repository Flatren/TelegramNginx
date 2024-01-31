package com.nginxtelegrm.NginxTelegramMessage.util;

import com.nginxtelegrm.NginxTelegramMessage.model.pojo.IntermediateCommand;
import com.nginxtelegrm.NginxTelegramMessage.util.parse.ParserUtil;

import java.util.List;

public class IntermediateCommandUtil {

   public static IntermediateCommand parse(String text){

       IntermediateCommand intermediateCommand = new IntermediateCommand();

       String[] splitCommandAndParams = text.split("[ \n]", 2);

       intermediateCommand.setCommand(splitCommandAndParams[0]);

       if (splitCommandAndParams.length == 2) {
           intermediateCommand.setParams(ParserUtil.textToArrayString(splitCommandAndParams[1]));
       }else
           intermediateCommand.setParams(List.of());

       return intermediateCommand;
   }
}
