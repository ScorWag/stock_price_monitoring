package ru.sw.stock_price_monitoring.controller.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import ru.sw.stock_price_monitoring.dto.StockDto;
import ru.sw.stock_price_monitoring.dto.StockInfoRequest;
import ru.sw.stock_price_monitoring.dto.error.ValidationErrorDto;

public interface StockControllerDocumentation {
    @Operation(summary = "Получение сохранненых данных по акции у пользователя по ее тикеру.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StockDto.class)
                    )
            )
    })
    ResponseEntity<StockDto> getUserStockInfo(
            @Parameter(name = "ticker", description = "Тикер акции", example = "AMZN") String ticker
    );

    @Operation(summary = "Сохранение данных пользователю по акции за период.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ValidationErrorDto.class)
            ))
    })
    ResponseEntity<Void> saveStockInfoRequest(StockInfoRequest stockInfoRequest);
}
