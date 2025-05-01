package org.example.service.command;

import org.example.ReceiptParsingBot;
import org.example.dto.SenderDto;

public interface CommandHandler {

    void handle(ReceiptParsingBot bot, SenderDto senderDto, String command);
}
