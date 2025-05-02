package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserDto;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<UserDto> getUserByChatId(Long chatId) {
        return userRepository.findUserByChatId(chatId).map(UserDto::from);
    }

    public void addNewUser(Long chatId, String username) {
        User user = User.builder()
                .chatId(chatId)
                .username(username)
                .botState(User.BotState.REGISTERED)
                .build();

        userRepository.save(user);
    }

    @Transactional
    public void setUserBotState(Long chatId, User.BotState newBotState) {
        Optional<User> userOp = userRepository.findUserByChatId(chatId);

        if (userOp.isPresent()) {
            User user = userOp.get();
            user.setBotState(newBotState);

            userRepository.save(user);
        }
    }
}
