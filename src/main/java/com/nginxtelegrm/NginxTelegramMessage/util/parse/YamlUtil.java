package com.nginxtelegrm.NginxTelegramMessage.util.parse;

import com.fasterxml.jackson.core.type.TypeReference;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public final class YamlUtil {

    public static <T> String dump(T ob){
        Yaml yaml = new Yaml(new Constructor(ob.getClass(), new LoaderOptions()));
        return yaml.dump(ob);
    }

    public static <T> T load(String textConfig, TypeReference<T> typeReference) {
        Yaml yaml = new Yaml(new Constructor((Class<?>) typeReference.getType(), new LoaderOptions()));
        return yaml.load(textConfig);
    }
}
