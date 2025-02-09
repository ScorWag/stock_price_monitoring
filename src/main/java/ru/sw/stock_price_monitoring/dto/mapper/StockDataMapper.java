package ru.sw.stock_price_monitoring.dto.mapper;

import org.mapstruct.Mapper;
import ru.sw.stock_price_monitoring.dto.StockDataDto;
import ru.sw.stock_price_monitoring.entity.StockData;
import ru.sw.stock_price_monitoring.feign.dto.CertainDateStockInfoDto;
import ru.sw.stock_price_monitoring.feign.dto.DailyInfoDto;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface StockDataMapper {

    Map<String, List<StockDataDto>> toStockDataDtoMap(Map<String, List<StockData>> data);

    List<StockDataDto> toStockDataDtoList(List<StockData> value);

    StockData toStockData(CertainDateStockInfoDto certainDateStockInfoDto);
    
    StockData toStockData(DailyInfoDto dailyInfoDto);

    List<StockData> toStockDataList(List<DailyInfoDto> dailyInfoDto);

    List<DailyInfoDto> toDailyInfoDtoList(List<StockData> stockDataList);
}
