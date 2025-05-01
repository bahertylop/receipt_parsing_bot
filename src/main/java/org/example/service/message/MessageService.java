package org.example.service.message;

import lombok.RequiredArgsConstructor;
import org.example.ReceiptParsingBot;
import org.example.dto.SenderDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final List<MessageHandler> messageHandlers;

    public void handleMessage(ReceiptParsingBot bot, SenderDto sender, String text) {
        for (MessageHandler handler : messageHandlers) {
            if (handler.handleMessage(bot, sender, text)) {
                return;
            }
        }
    }
}