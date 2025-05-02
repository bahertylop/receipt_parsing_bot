package org.example.dto.response;

import lombok.Data;

@Data
public class ReceiptRequest {
    private String qrurl;
    private String qrfile;
    private String qrraw;
    private ManualReceipt manual;
}

