package com.nginxtelegrm.NginxTelegramMessage.core.repositoryes;

import com.nginxtelegrm.NginxTelegramMessage.core.modeles.Bots;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.ConfigRules;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.inspector.TagInspector;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Repository
public class ConfigRepository {

    public ConfigRules getConfig(String nameConfig) throws FileNotFoundException {
        LoaderOptions loaderOptions =  new LoaderOptions();
        TagInspector taginspector =
                tag -> tag.getClassName().equals(ConfigRules.class.getName());
        loaderOptions.setTagInspector(taginspector);
        Yaml yaml = new Yaml(new Constructor(ConfigRules.class, loaderOptions));
        InputStream inputStream = new FileInputStream(nameConfig);
        return yaml.load(inputStream);
    }

    public Bots getBots(String nameConfig) throws FileNotFoundException {
        LoaderOptions loaderOptions =  new LoaderOptions();
        TagInspector taginspector =
                tag -> tag.getClassName().equals(Bots.class.getName());
        loaderOptions.setTagInspector(taginspector);
        Yaml yaml = new Yaml(new Constructor(Bots.class, loaderOptions));
        InputStream inputStream = new FileInputStream(nameConfig);
        return yaml.load(inputStream);
    }
}
