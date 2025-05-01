package org.example.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CheckResponseDto {
    private int code;
    private int first;
    private ReceiptData data;
    private ReceiptRequest request;
}

@Data
class ReceiptData {
    private JsonReceipt json;
    private String html;
}

@Data
class JsonReceipt {
    private String user;
    private List<ReceiptItem> items;
    private int nds18;
    private String userInn;
    private LocalDateTime dateTime;
    private String kktRegId;
    private String operator;
    private int totalSum;
    private long fiscalSign;
    private int receiptCode;
    private int shiftNumber;
    private int cashTotalSum;
    private int ecashTotalSum;
    private int taxationType;
    private int operationType;
    private int requestNumber;
    private String fiscalDriveNumber;
    private String retailPlaceAddress;
    private int fiscalDocumentNumber;
    private String region;
}

@Data
class ReceiptItem {
    private int sum;
    private String name;
    private int price;
    private int quantity;
}

@Data
class ReceiptRequest {
    private String qrurl;
    private String qrfile;
    private String qrraw;
    private ManualReceipt manual;
}

@Data
class ManualReceipt {
    private String fn;
    private String fd;
    private String fp;
    private String check_time;
    private String type;
    private String sum;
}
