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
public class SenderDto {

    private Long chatId;

    private UserDto user;

    private Integer messageId;
}
