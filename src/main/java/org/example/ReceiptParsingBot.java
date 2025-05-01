package org.example;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.qr.QrHandler;
import org.example.service.qr.QrService;
import org.example.service.TextMessageHandlingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReceiptParsingBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    private final TextMessageHandlingService textMessageHandlingService;

    private final QrHandler qrHandler;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            Long chatId = message.getChatId();

            User user = message.getFrom();
            if (user.getIsBot()) {
                return;
            }

            textMessageHandlingService.onMessageReceived(this, chatId, message);
        }
        if (update.hasMessage() && update.getMessage().hasPhoto()) {
            qrHandler.handleQr(this, update.getMessage());
        }

//        if (update.hasCallbackQuery()) {
//            CallbackQuery callbackQuery = update.getCallbackQuery();
//            Long chatId = callbackQuery.getFrom().getId();
//
//            callbackHandlingService.onCallbackReceived(this, chatId, callbackQuery);
//        }
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage(chatId.toString(), text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.warn("не удалось отправить сообщение, chatId: {}", chatId, e);
        }
    }

    public void sendMessage(Long chatId, String text, ReplyKeyboard keyboard) {
        SendMessage sendMessage = new SendMessage(chatId.toString(), text);
        sendMessage.setReplyMarkup(keyboard);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.warn("не удалось отправить сообщение, chatId: {}", chatId, e);
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
