package ru.sw.stock_price_monitoring.service.request.strategy;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.sw.stock_price_monitoring.dto.mapper.StockDataMapper;
import ru.sw.stock_price_monitoring.feign.PolygonServiceFeign;

@Getter
@RequiredArgsConstructor
public abstract class PolygonRequest {
    private final PolygonServiceFeign polygonClient;
    private final StockDataMapper mapper;
}
