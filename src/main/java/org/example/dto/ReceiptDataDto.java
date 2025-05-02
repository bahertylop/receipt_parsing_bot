package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceiptDataDto {
    private LocalDateTime dateTime;
    private List<ItemDto> items;
    private int totalSum;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("🧾 *Чек от*: ").append(dateTime).append("\n\n");
        builder.append("*Список товаров:*\n");

        for (ItemDto item : items) {
            builder.append("• ").append(item.getName()).append("\n")
                    .append("  Кол-во: ").append(item.getQuantity()).append("\n")
                    .append("  Цена: ").append(item.getPrice() / 100.0).append(" ₽\n")
                    .append("  Сумма: ").append(item.getSum() / 100.0).append(" ₽\n\n");
        }

        builder.append("*Итого:* ").append(totalSum / 100.0).append(" ₽");

        return builder.toString();
    }
}
