package com.nginxtelegrm.NginxTelegramMessage.core.utilites;

import com.nginxtelegrm.NginxTelegramMessage.core.modeles.Intermediate.IntermediateCommand;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.Intermediate.IntermediateRuleResendMessageChats;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.ConfigRules;
import com.nginxtelegrm.NginxTelegramMessage.core.exceptions.TypeException;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.inspector.TagInspector;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class Parser {


    public static ArrayList<Object> stringToParams(String stringParams, ArrayList<Type> typeParams) throws TypeException {
        ArrayList<String> arrayListParams = textToArrayString(stringParams);
        ArrayList<Object> resultTypeParams = new ArrayList<>();
        if (arrayListParams.size() != typeParams.size()){
            throw new TypeException("Количество параметров не соотвествет ожидаемому." +
                    "\nОжидается: "+typeParams.size()+"\nПолучено: "+arrayListParams.size());
        }
        int len = typeParams.size();
        for (int indexParam=0; indexParam < len; indexParam++){
            Type temp = typeParams.get(indexParam);
            if (Integer.class.equals(temp)) {
                resultTypeParams.add(Integer.parseInt(arrayListParams.get(indexParam)));
            } else if (String.class.equals(temp)) {
                resultTypeParams.add(arrayListParams.get(indexParam));
            } else if (Double.class.equals(temp)) {
                resultTypeParams.add(Double.parseDouble(arrayListParams.get(indexParam)));
            }
        }
        return resultTypeParams;
    }

    public static ArrayList<Object> parseToParams(IntermediateCommand intermediateCommand,
                                         ArrayList<Type> typeParams) throws TypeException {

        ArrayList<Object> resultTypeParams = new ArrayList<>();
        List<String> arrayListParams = intermediateCommand.getParams();
        if (arrayListParams.size() != typeParams.size()){
            throw new TypeException(
                    "Количество параметров не соотвествет ожидаемому." +
                    "\nОжидается: "+typeParams.size()+
                    "\nПолучено: "+arrayListParams.size());
        }
        int len = typeParams.size();

        for (int indexParam=0; indexParam < len; indexParam++){

            Type temp = typeParams.get(indexParam);

            if (Integer.class.equals(temp)) {
                resultTypeParams.add(Integer.parseInt(arrayListParams.get(indexParam)));
            } else if (String.class.equals(temp)) {
                resultTypeParams.add(arrayListParams.get(indexParam));
            } else if (Double.class.equals(temp)) {
                resultTypeParams.add(Double.parseDouble(arrayListParams.get(indexParam)));
            }
        }
        return resultTypeParams;
    }

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

    public static IntermediateCommand parseStringToIntermediateCommand(String text){

        IntermediateCommand intermediateCommand = new IntermediateCommand();

        String[] splitCommandAndParams = text.split("[ \n]", 2);

        intermediateCommand.setCommand(splitCommandAndParams[0]);
        if (splitCommandAndParams.length == 2) {
            intermediateCommand.setParams(textToArrayString(splitCommandAndParams[1]));
        }else
            intermediateCommand.setParams(List.of());

        return intermediateCommand;
    }

    public static IntermediateRuleResendMessageChats parseIntermediateRuleResendMessageChats(String text){
        Yaml yaml = new Yaml(new Constructor(IntermediateRuleResendMessageChats.class, new LoaderOptions()));
        return yaml.load(text);
    }
    public static String dumpYaml(Object ob, Type type){
        LoaderOptions loaderOptions =  new LoaderOptions();
        TagInspector taginspector =
                tag -> tag.getClassName().equals(type.getClass().getName());
        loaderOptions.setTagInspector(taginspector);
        Yaml yaml = new Yaml(new Constructor(type.getClass(), loaderOptions));
        return yaml.dump(ob);
    }
    public static ConfigRules parseToConfigRules(String textConfig) {
        LoaderOptions loaderOptions =  new LoaderOptions();
        TagInspector taginspector =
                tag -> tag.getClassName().equals(ConfigRules.class.getName());
        loaderOptions.setTagInspector(taginspector);
        Yaml yaml = new Yaml(new Constructor(ConfigRules.class, loaderOptions));
        return yaml.load(textConfig);
    }

}
