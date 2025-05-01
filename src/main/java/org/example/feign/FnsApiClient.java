package org.example.feign;

import org.example.dto.CheckResponseDto;
import org.example.dto.FNSFeignRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "fnsApiClient", url = "https://proverkacheka.com")
public interface FnsApiClient {

    @PostMapping(value = "/api/v1/check/get", consumes = "application/json")
    CheckResponseDto getReceiptInfo(@RequestBody FNSFeignRequest request);
}
