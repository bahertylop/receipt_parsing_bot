package org.example.dto.response;

import lombok.Data;

@Data
public class ManualReceipt {
    private String fn;
    private String fd;
    private String fp;
    private String check_time;
    private String type;
    private String sum;
}
