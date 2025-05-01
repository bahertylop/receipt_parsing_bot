package org.example.service.message;

import org.example.ReceiptParsingBot;
import org.example.dto.SenderDto;

public interface MessageHandler {

    boolean handleMessage(ReceiptParsingBot bot, SenderDto sender, String text);
}

