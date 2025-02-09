package ru.sw.stock_price_monitoring.service.request.strategy;

import feign.FeignException;
import org.springframework.stereotype.Component;
import ru.sw.stock_price_monitoring.dto.mapper.StockDataMapper;
import ru.sw.stock_price_monitoring.entity.Stock;
import ru.sw.stock_price_monitoring.entity.StockData;
import ru.sw.stock_price_monitoring.entity.StockNotFoundData;
import ru.sw.stock_price_monitoring.feign.PolygonServiceFeign;
import ru.sw.stock_price_monitoring.feign.dto.CertainDateStockInfoDto;
import ru.sw.stock_price_monitoring.util.DataContainer;
import ru.sw.stock_price_monitoring.util.DateRange;

import java.time.LocalDate;

@Component
public class OpenCloseRequestStrategy extends PolygonRequest implements RequestStockDataStrategy{
    public OpenCloseRequestStrategy(
            PolygonServiceFeign polygonClient,
            StockDataMapper mapper
    ) {
        super(polygonClient, mapper);
    }

    @Override
    public boolean applicability(int i) {
        return i == 1;
    }

    @Override
    public DataContainer getStockData(Stock stock, DateRange dateRange) {
        DataContainer container = new DataContainer();

        CertainDateStockInfoDto response;
        LocalDate requestDate = dateRange.getStartDate();
        try {
            response = getPolygonClient().getCertainDateStockInfo(stock.getTicker(), requestDate.toString());
            StockData sd = getMapper().toStockData(response);
            sd.setStock(stock);
            container.addStockData(sd);
        } catch (FeignException.NotFound e) {
            container.addStockNotFoundData(new StockNotFoundData(stock, requestDate));
        }
        return container;
    }
}
