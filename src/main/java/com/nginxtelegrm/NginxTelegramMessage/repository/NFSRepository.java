package com.nginxtelegrm.NginxTelegramMessage.repository;

import com.nginxtelegrm.NginxTelegramMessage.core.interfaces.FSRepository;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.InfoFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class NFSRepository implements FSRepository {

    @Override
    public InputStream getInputStreamFile(InfoFile infoFile) {
        return null;
    }

    @Override
    public Boolean exist(String fileName) throws IOException {
        return null;
    }

    @Override
    public Optional<InfoFile> getInfoFile(String fileName) throws IOException {
        return Optional.empty();
    }

    @Override
    public InfoFile createFile(String nameFile, InputStream inputStream) throws IOException {
        return null;
    }
}
