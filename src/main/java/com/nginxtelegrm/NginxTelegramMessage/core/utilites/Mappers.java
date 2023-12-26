package com.nginxtelegrm.NginxTelegramMessage.core.utilites;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.RuleResendMessageInChats.RuleResend;
import com.nginxtelegrm.NginxTelegramMessage.modeles.ConfigRules;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.inspector.TagInspector;

import java.lang.reflect.Type;

public class Mappers {
    static ObjectMapper objectMapper = new JsonMapper();

    public static String getStringJson(Object ob) throws JsonProcessingException {
        return objectMapper.writeValueAsString(ob);

    }

    public static <T> T getObjectJson(String json, Class<T> type) throws JsonProcessingException {
        return objectMapper.readValue(json, type);
    }

    public static <T> String getStringYaml(T ob) throws JsonProcessingException {
        LoaderOptions loaderOptions =  new LoaderOptions();
        Yaml yaml = new Yaml(new Constructor(ob.getClass(), loaderOptions));
        return yaml.dump(ob);
    }

    public static <T> T getObjectYaml(String yamlText, Class<T> type) {
        LoaderOptions loaderOptions =  new LoaderOptions();
        TagInspector taginspector =
                tag -> tag.getClassName().equals(type.getName());
        loaderOptions.setTagInspector(taginspector);
        Yaml yaml = new Yaml(new Constructor(type, loaderOptions));
        return yaml.load(yamlText);
    }
}
