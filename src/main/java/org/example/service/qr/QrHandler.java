package org.example.service.qr;

import lombok.RequiredArgsConstructor;
import org.example.ReceiptParsingBot;
import org.example.dto.SenderDto;
import org.example.dto.UserDto;
import org.example.model.User;
import org.example.service.FNSService;
import org.example.service.UserService;
import org.example.util.MessagesConstants;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QrHandler {

    private final QrService qrService;

    private final UserService userService;

    private final FNSService fnsService;

    public void handleQr(ReceiptParsingBot bot, Message message) {
        Optional<UserDto> userOp = userService.getUserByChatId(message.getChatId());

        if (userOp.isEmpty()) {
            bot.sendMessage(message.getChatId(), MessagesConstants.USER_NOT_FOUND_MESSAGE);
            return;
        }

        List<PhotoSize> photos = message.getPhoto();

        PhotoSize photo = photos.get(photos.size() - 1);
        String fileId = photo.getFileId();

        // Можно получить file_path через Telegram API
        GetFile getFileMethod = new GetFile(fileId);
        try {
            File file = bot.execute(getFileMethod);
            String filePath = file.getFilePath();

            java.io.File downloaded = bot.downloadFile(filePath);

            System.out.println("Фото успешно получено: " + downloaded.getAbsolutePath());
            String parsedInfo = qrService.decodeQRCode(downloaded);
            fnsService.getReceiptInfo(parsedInfo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
