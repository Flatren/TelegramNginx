package com.nginxtelegrm.NginxTelegramMessage.repositoryes;

import com.nginxtelegrm.NginxTelegramMessage.modeles.Bots;
import com.nginxtelegrm.NginxTelegramMessage.modeles.ConfigRules;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.inspector.TagInspector;

import java.io.File;
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
        InputStream inputStream = new FileInputStream(new File(nameConfig));
        return yaml.load(inputStream);
    }

    public Bots getBots(String nameConfig) throws FileNotFoundException {
        LoaderOptions loaderOptions =  new LoaderOptions();
        TagInspector taginspector =
                tag -> tag.getClassName().equals(Bots.class.getName());
        loaderOptions.setTagInspector(taginspector);
        Yaml yaml = new Yaml(new Constructor(Bots.class, loaderOptions));
        InputStream inputStream = new FileInputStream(new File(nameConfig));
        return yaml.load(inputStream);
    }
}
