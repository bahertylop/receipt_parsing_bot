package org.example.dto.response;

import lombok.Data;

@Data
public class ReceiptItem {
    private int sum;
    private String name;
    private int price;
    private int quantity;
}

