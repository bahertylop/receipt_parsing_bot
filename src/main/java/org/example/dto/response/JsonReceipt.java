package org.example.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class JsonReceipt {
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
