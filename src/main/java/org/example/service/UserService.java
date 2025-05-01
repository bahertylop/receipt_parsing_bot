package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserDto;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

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
}
