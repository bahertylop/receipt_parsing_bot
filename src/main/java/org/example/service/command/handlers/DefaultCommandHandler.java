package org.example.service.command.handlers;

import org.example.ReceiptParsingBot;
import org.example.dto.SenderDto;
import org.example.service.command.CommandHandler;
import org.example.util.MessagesConstants;
import org.springframework.stereotype.Component;

@Component
public class DefaultCommandHandler implements CommandHandler {

    @Override
    public void handle(ReceiptParsingBot bot, SenderDto senderDto, String command) {
        bot.sendMessage(senderDto.getChatId(), MessagesConstants.COMMAND_NOT_HANDLED_MESSAGE);
    }
}
