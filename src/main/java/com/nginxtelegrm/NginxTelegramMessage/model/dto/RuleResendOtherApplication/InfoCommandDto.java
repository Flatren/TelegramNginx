package com.nginxtelegrm.NginxTelegramMessage.model.dto.RuleResendOtherApplication;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InfoCommandDto {
    List<String> params; // Название параметров, которые будут
    List<String> types; // Описание типов, которые дожны быть
    Boolean contentFile; // Необходиммо ли тянуть файлы
    String description; // короткое описание команды
}
