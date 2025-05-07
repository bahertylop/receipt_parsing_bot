package org.example.service.qr;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ReceiptParsingBot;
import org.example.dto.response.CheckResponseDto;
import org.example.dto.ReceiptDataDto;
import org.example.dto.UserDto;
import org.example.dto.mapper.ReceiptMapper;
import org.example.model.User;
import org.example.service.FNSService;
import org.example.service.UserService;
import org.example.service.receipt.ReceiptService;
import org.example.util.MessagesConstants;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import boofcv.abst.fiducial.QrCodeDetector;
import boofcv.factory.fiducial.FactoryFiducial;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.struct.image.GrayU8;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class QrHandler {

    private final QrService qrService;

    private final UserService userService;

    private final FNSService fnsService;

    private final ReceiptMapper receiptMapper;

    private final ReceiptClient client;

    private final ReceiptService receiptService;

    public void handleQr(ReceiptParsingBot bot, Message message) {
        Optional<UserDto> userOp = userService.getUserByChatId(message.getChatId());

        if (userOp.isEmpty()) {
            bot.sendMessage(message.getChatId(), MessagesConstants.USER_NOT_FOUND_MESSAGE);
            return;
        }

        if (userOp.get().getBotState().equals(User.BotState.QR)) {
            String result = null;
            try {
                result = receiptService.parseReceipt(bot, message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            bot.sendMessage(message.getChatId(), result);

//            List<PhotoSize> photos = message.getPhoto();
//
//            PhotoSize photo = photos.get(photos.size() - 1);
//            String fileId = photo.getFileId();
//
//            // Можно получить file_path через Telegram API
//            GetFile getFileMethod = new GetFile(fileId);
//            try {
//                File file = bot.execute(getFileMethod);
//                String filePath = file.getFilePath();
//
//                java.io.File downloaded = bot.downloadFile(filePath);
//                client.parse(downloaded);
//                System.out.println("Фото успешно получено: " + downloaded.getAbsolutePath());
//                String parsedInfo = qrService.decodeQRCode3(downloaded);
//                System.out.println("qr info: " + parsedInfo);
//                CheckResponseDto response = fnsService.getReceiptInfo(parsedInfo);
//                ReceiptDataDto data = receiptMapper.toDto(response);
//                bot.sendMessage(message.getChatId(), data.toString());

                // обработка файлов
//                String result = receiptService.parseReceipt(bot, message);
//                bot.sendMessage(message.getChatId(), result);

//            } catch (Exception e) {
//                log.error("error: ", e);
//                bot.sendMessage(message.getChatId(), MessagesConstants.PARSING_ERROR_MESSAGE);
//            }
        } else {
            bot.sendMessage(message.getChatId(), MessagesConstants.ANOTHER_BOT_STATE_MESSAGE);
        }
    }


}
