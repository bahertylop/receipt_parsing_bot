package org.example.dto.response;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ReceiptData {
    private JsonReceipt json;
    private String html;
}
