package ru.sw.stock_price_monitoring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Данные по торгам акции за определенные день")
@Data
public class StockDataDto {

  @Schema(description = "Дата торгов", example = "2024-08-05")
  private LocalDate date;

  @Schema(description = "Цена открытия", example = "154.21")
  private BigDecimal open;

  @Schema(description = "Цена закрытия", example = "161.02")
  private BigDecimal close;

  @Schema(description = "Максимальная цена", example = "162.96")
  private BigDecimal high;

  @Schema(description = "Минимальная цена", example = "151.61")
  private BigDecimal low;
}

