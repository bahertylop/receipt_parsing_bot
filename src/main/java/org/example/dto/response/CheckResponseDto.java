package org.example.dto.response;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CheckResponseDto {
    private int code;
    private int first;
    private ReceiptData data;
    private ReceiptRequest request;
}
