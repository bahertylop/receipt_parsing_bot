package org.example.dto.mapper;

import org.example.dto.response.CheckResponseDto;
import org.example.dto.ItemDto;
import org.example.dto.ReceiptDataDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReceiptMapper {

    public ReceiptDataDto toDto(CheckResponseDto receipt) {
        List<ItemDto> items = receipt.getData().getJson().getItems().stream()
                .map(i -> new ItemDto(i.getName(), i.getPrice(), i.getQuantity(), i.getSum()))
                .collect(Collectors.toList());

        return ReceiptDataDto.builder()
                .dateTime(receipt.getData().getJson().getDateTime())
                .totalSum(receipt.getData().getJson().getTotalSum())
                .items(items)
                .build();
    }
}

