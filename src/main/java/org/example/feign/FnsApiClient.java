package org.example.feign;

import org.example.dto.response.CheckResponseDto;
import org.example.dto.FNSFeignRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "fnsApiClient", url = "https://proverkacheka.com")
public interface FnsApiClient {

    @PostMapping(value = "/api/v1/check/get", consumes = "application/json")
    CheckResponseDto getReceiptInfo(@RequestBody FNSFeignRequest request);
}
