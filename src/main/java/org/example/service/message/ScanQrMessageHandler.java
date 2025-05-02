package org.example.service.message;

import lombok.RequiredArgsConstructor;
import org.example.ReceiptParsingBot;
import org.example.dto.SenderDto;
import org.example.model.User;
import org.example.service.UserService;
import org.example.util.MessagesConstants;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScanQrMessageHandler implements MessageHandler {

    private final UserService userService;

    @Override
    public boolean handleMessage(ReceiptParsingBot bot, SenderDto sender, String text) {
        if (!text.equals("отправить чек")) {
            return false;
        }

        userService.setUserBotState(sender.getChatId(), User.BotState.QR);
        bot.sendMessage(sender.getChatId(), MessagesConstants.SEND_QR_MESSAGE);
        return true;
    }
}
