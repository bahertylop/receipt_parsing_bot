package org.example.service.command.handlers;

import org.example.ReceiptParsingBot;
import org.example.dto.SenderDto;
import org.example.service.command.CommandHandler;
import org.example.util.KeyboardConstants;
import org.example.util.MessagesConstants;
import org.springframework.stereotype.Component;

@Component
public class StartCommandHandler implements CommandHandler {
    @Override
    public void handle(ReceiptParsingBot bot, SenderDto senderDto, String command) {
        bot.sendMessage(senderDto.getChatId(), MessagesConstants.HELLO_MESSAGE, KeyboardConstants.botButtons());
    }
}
