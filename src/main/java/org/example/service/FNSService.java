package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.CheckResponseDto;
import org.example.dto.FNSFeignRequest;
import org.example.feign.FnsApiClient;
import org.hibernate.annotations.Check;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FNSService {

    private final String fnsToken = "32712.N76Z4EBOFoYaiNJP5";

    private final FnsApiClient apiClient;

    public void getReceiptInfo(String qrInfo) {
        FNSFeignRequest request = FNSFeignRequest.builder()
                .token(fnsToken)
                .qrraw(qrInfo)
                .build();

        try {
            CheckResponseDto response = apiClient.getReceiptInfo(request);
            System.out.println(response);
            return;
        } catch (Exception e) {
            log.error("error: ", e);
        }
    }
}
