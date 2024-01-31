package com.nginxtelegrm.NginxTelegramMessage.core.interfaces;



import com.nginxtelegrm.NginxTelegramMessage.model.pojo.InfoFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public interface FSRepository {

    InputStream getInputStreamFile(InfoFile infoFile);

    Boolean exist(String fileName) throws IOException;

    Optional<InfoFile> getInfoFile(String fileName) throws IOException;

    InfoFile createFile(String nameFile, InputStream inputStream) throws IOException;

}
