package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;

    private Long chatId;

    private String userName;

    private User.BotState botState;

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .chatId(user.getChatId())
                .userName(user.getUsername())
                .botState(user.getBotState())
                .build();
    }
}
