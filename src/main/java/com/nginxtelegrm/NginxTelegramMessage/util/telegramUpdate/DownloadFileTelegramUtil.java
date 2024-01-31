package com.nginxtelegrm.NginxTelegramMessage.util.telegramUpdate;

import com.nginxtelegrm.NginxTelegramMessage.Telegram;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.LinkFileTelegram;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

public class DownloadFileTelegramUtil {
    public static LinkFileTelegram download(Telegram telegram, String idFile, String nameFile) throws TelegramApiException {
        GetFile getFile = new GetFile(idFile);
        String filePath = telegram.execute(getFile).getFilePath();
        LinkFileTelegram infoFileTelegram = new LinkFileTelegram();
        infoFileTelegram.setNameFile(nameFile);
        File file = telegram.downloadFile(filePath);
        infoFileTelegram.setIdFile(file.getPath());
        return infoFileTelegram;
    }
}
