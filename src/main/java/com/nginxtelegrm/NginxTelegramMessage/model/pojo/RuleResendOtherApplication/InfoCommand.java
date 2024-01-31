package com.nginxtelegrm.NginxTelegramMessage.model.pojo.RuleResendOtherApplication;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InfoCommand {
    List<String> params; // Название параметров, которые будут
    List<String> types; // Описание типов, которые дожны быть
    Boolean contentFile; // Необходиммо ли тянуть файлы
    String description; // короткое описание команды
}
