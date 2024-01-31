package com.nginxtelegrm.NginxTelegramMessage.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.file.Paths;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InfoFile {
    String nameFile;
    String path;

    public String getFullPath(){
        return Paths.get(path, nameFile).toString();
    }
}
