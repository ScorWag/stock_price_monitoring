package ru.sw.stock_price_monitoring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;


@Data
@Builder
@Schema(description = "Ответ с сохраненными данными по акции у пользователя")
public class StockDto {

  @Schema(description = "Id пользователя", example = "87")
  private Long userId;

  @Schema(description = "Сохраненные данные по акциям, сгруппированных по их тикерам")
  private Map<String, List<StockDataDto>> data;
}

