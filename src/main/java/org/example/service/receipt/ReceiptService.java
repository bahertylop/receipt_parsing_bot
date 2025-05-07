package org.example.service.receipt;

import lombok.RequiredArgsConstructor;
import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.example.ReceiptParsingBot;
import org.example.util.KannyDetectorUtil;
import org.example.util.TelegramFileDownloader;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiptService {

    public String parseReceipt(ReceiptParsingBot bot, Message message) throws TelegramApiException, IOException {
        List<PhotoSize> photos = message.getPhoto();

        PhotoSize photo = photos.get(photos.size() - 1);
        String fileId = photo.getFileId();

        GetFile getFileMethod = new GetFile(fileId);
        File file = bot.execute(getFileMethod);
        String filePath = file.getFilePath();
        String fileUrl = "https://api.telegram.org/file/bot" + bot.getBotToken() + "/" + filePath;


        String extension = filePath.substring(filePath.lastIndexOf('.') + 1);
        String destinationPath = "D:/android_developing/IdiaProjects/receipt_parsing_bot/src/main/resources/photos/photo_" + fileId + "." + extension;

        TelegramFileDownloader.downloadFile(fileUrl, destinationPath);

        Tesseract tesseract = new Tesseract();

        // путь к tessdata
        tesseract.setDatapath("D:\\desseract\\tessdata");

        tesseract.setLanguage("rus");

        KannyDetectorUtil.kannyUtil(destinationPath, fileId, extension);

        String finalFilePath = "D:/android_developing/IdiaProjects/receipt_parsing_bot/src/main/resources/adaptiveThreshold/photo_" + fileId + "." + extension;
        java.io.File downloaded = new java.io.File(finalFilePath);

        InputFile inputFile = new InputFile();
        inputFile.setMedia(new java.io.File(finalFilePath));
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(inputFile);
        sendPhoto.setChatId(message.getChatId());
        bot.execute(sendPhoto);

        try {
//            tesseract.setOcrEngineMode(ITessAPI.TessOcrEngineMode.OEM_LSTM_ONLY);
//            tesseract.setPageSegMode(ITessAPI.TessPageSegMode.PSM_AUTO);
            String result = tesseract.doOCR(downloaded);
            System.out.println("Результат OCR:");
            System.out.println(result);

            return result;
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
            return "Не распарсилось";
        }
    }
}
