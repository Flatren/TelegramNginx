package com.nginxtelegrm.NginxTelegramMessage.repository;

import com.nginxtelegrm.NginxTelegramMessage.core.interfaces.FSRepository;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.InfoFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Optional;


public class LocalFSRepository implements FSRepository {

    @Value("${local.path}")
    private String path;

    Long numberFolder = 0L;

    Object lock = new Object();

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

    private String getNewFolder(){
        synchronized (lock){
            return (++numberFolder).toString();
        }
    }

    @Override
    public InfoFile createFile(String nameFile, InputStream inputStream) throws IOException {
        InfoFile infoFile = new InfoFile();

        infoFile.setNameFile(nameFile);
        infoFile.setPath("");

        File file = new File(Paths.get(path, nameFile).toString());

        if(file.exists())
        {
            String nameNewFolder = getNewFolder();
            file = new File(Paths.get(path, nameNewFolder).toString());
            if (file.mkdir()){
                infoFile.setPath(Paths.get(nameNewFolder).toString());
                file = new File(Paths.get(path, infoFile.getFullPath()).toString());
            }
            else {
                throw new IOException("Не удалось создать папку.");
            }
        }

        FileOutputStream fileOutputStream = new FileOutputStream(file.getPath());
        fileOutputStream.write(inputStream.readAllBytes());
        fileOutputStream.close();

        return infoFile;
    }
}
