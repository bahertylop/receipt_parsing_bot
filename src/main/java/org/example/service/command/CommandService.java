package org.example.service.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.ReceiptParsingBot;
import org.example.dto.SenderDto;
import org.example.service.command.handlers.DefaultCommandHandler;
import org.example.service.command.handlers.StartCommandHandler;
import org.example.service.command.handlers.TestCommandHandler;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommandService {

    private final Map<String, CommandHandler> handlers;

    public void handle(ReceiptParsingBot bot, SenderDto senderDto, String command) {
        String commandName = command.substring(1);

        CommandHandler handler = handlers.get(commandNameToBeanName(commandName));
        if (handler != null) {
            handler.handle(bot, senderDto, command);
        } else {
            log.info("unexpected command: {} chatId: {}", commandName, senderDto.getChatId());
        }
    }

    private String commandNameToBeanName(String commandName) {
        String commandNameUn = StringUtils.uncapitalize(commandName);
        switch (commandNameUn) {
            case "start":
                return StringUtils.uncapitalize(StartCommandHandler.class.getSimpleName());
            case "test":
                return StringUtils.uncapitalize(TestCommandHandler.class.getSimpleName());
            default:
                System.out.println(StringUtils.uncapitalize(DefaultCommandHandler.class.getSimpleName()));
                return StringUtils.uncapitalize(DefaultCommandHandler.class.getSimpleName());
        }
    }
}
