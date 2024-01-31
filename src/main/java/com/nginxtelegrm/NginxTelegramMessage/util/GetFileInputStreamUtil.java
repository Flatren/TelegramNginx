package com.nginxtelegrm.NginxTelegramMessage.util;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class GetFileInputStreamUtil {
    public static InputStream getInputStream(String nameFile) throws FileNotFoundException {
        return new FileInputStream(nameFile);
    }
}
