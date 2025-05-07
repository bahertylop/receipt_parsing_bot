package org.example.service.command.handlers;

import org.example.ReceiptParsingBot;
import org.example.dto.ReceiptDataDto;
import org.example.dto.SenderDto;
import org.example.dto.mapper.ReceiptMapper;
import org.example.dto.response.CheckResponseDto;
import org.example.service.FNSService;
import org.example.service.command.CommandHandler;
import org.example.service.qr.QrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class TestCommandHandler implements CommandHandler {

    @Autowired
    private QrService qrService;

    @Autowired
    private FNSService fnsService;

    @Autowired
    private ReceiptMapper receiptMapper;

//    @Override
//    public void handle(ReceiptParsingBot bot, SenderDto senderDto, String command) {
//        try {
//            File folder = new File(getClass().getClassLoader().getResource("photos").toURI());
//            File[] files = folder.listFiles();
//
//            if (files == null || files.length == 0) {
//                System.out.println("Нет файлов для обработки.");
//                return;
//            }
//
//            int successCount = 0;
//
//            for (File file : files) {
//                try {
//                    System.out.println("Обработка файла: " + file.getName());
//
//                    String parsedInfo = qrService.decodeQRCode3(file);
//                    System.out.println("QR info: " + parsedInfo);
//
//                    CheckResponseDto response = fnsService.getReceiptInfo(parsedInfo);
//                    ReceiptDataDto data = receiptMapper.toDto(response);
//
//                    // Можно отправить через бота, если нужно
//                    // bot.sendMessage(senderDto.getChatId(), data.toString());
//
//                    System.out.println("Успешно: " + data);
//                    successCount++;
//
//                } catch (Exception e) {
//                    System.err.println("Ошибка при обработке файла " + file.getName() + ": " + e.getMessage());
//                }
//            }
//
//            System.out.println("Обработка завершена. Успешно обработано: " + successCount + " из " + files.length);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void handle(ReceiptParsingBot bot, SenderDto senderDto, String command) {
        int count = 0;
        for (int i = 0; i < 3000; i++) {
            try {
                List<String> parsingInfos = List.of(
                        "t=20250502T1952&s=1310.38&fn=7380440700195934&i=15739&fp=3586631954&n=1",
                        "t=20250502T2007&s=296.97&fn=7380440700435184&i=2368&fp=1167254108&n=1",
                        "t=20250502T2008&s=277.00&fn=7380440700435184&i=2369&fp=4099025916&n=1"
                );

                for (String info : parsingInfos) {
                    try {
                        String parsedInfo = info;

                        CheckResponseDto response = fnsService.getReceiptInfo(parsedInfo);
                        ReceiptDataDto data = receiptMapper.toDto(response);

                        System.out.println("Успешно: " + data);
                        count++;

                    } catch (Exception e) {
                        System.err.println("Ошибка при обработке " + info);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Обработка завершена. Успешно обработано: " + count);
        }
    }
}
