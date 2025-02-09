package ru.sw.stock_price_monitoring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import ru.sw.stock_price_monitoring.validation.CheckDate;
import ru.sw.stock_price_monitoring.validation.CheckTicker;

@Builder
@Data
@CheckDate(
        field = "start",
        fieldMatch = "end"
)
@Schema(description = "Запрос на сохранение данных по акции")
public class StockInfoRequest {

    @Schema(description = "Тикер акции", examples = {"AMZN", "GOOGL", "TCSG"})
    @CheckTicker
    @NotBlank(message = "{validation-not-blank}")
    private String ticker;

    @Schema(description = "Дата начала периода запроса", example = "2024-08-01")
    @NotBlank(message = "{validation-not-blank}")
    @Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}", message = "{validation-date-format}")
    private String start;

    @Schema(description = "Дата конца периода запроса", example = "2024-09-01")
    @NotBlank(message = "{validation-not-blank}")
    @Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}", message = "{validation-date-format}")
    private String end;
}

