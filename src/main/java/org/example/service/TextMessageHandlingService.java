package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.ReceiptParsingBot;
import org.example.dto.SenderDto;
import org.example.dto.UserDto;
import org.example.service.command.CommandService;
import org.example.service.message.MessageService;
import org.example.util.KeyboardConstants;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.example.util.MessagesConstants;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TextMessageHandlingService {

    private final UserService userService;

    private final MessageService messageService;

    private final CommandService commandsService;

    public void onMessageReceived(ReceiptParsingBot bot, Long chatId, Message message) {
        String text = message.getText();
        Integer messageId = message.getMessageId();

        Optional<UserDto> userOp = userService.getUserByChatId(chatId);
        if (userOp.isEmpty()) {
            userService.addNewUser(chatId, message.getFrom().getUserName());
            bot.sendMessage(chatId, MessagesConstants.HELLO_MESSAGE, KeyboardConstants.botButtons());
            return;
        }

        SenderDto sender = new SenderDto(chatId, userOp.get(), messageId);

        if (message.isCommand()) {
            commandsService.handle(bot, sender, message.getText());
            return;
        }

        messageService.handleMessage(bot, sender, text);
    }
}
