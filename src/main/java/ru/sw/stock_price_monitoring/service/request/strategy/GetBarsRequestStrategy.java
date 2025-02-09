package ru.sw.stock_price_monitoring.service.request.strategy;

import org.springframework.stereotype.Component;
import ru.sw.stock_price_monitoring.dto.mapper.StockDataMapper;
import ru.sw.stock_price_monitoring.entity.Stock;
import ru.sw.stock_price_monitoring.entity.StockData;
import ru.sw.stock_price_monitoring.entity.StockNotFoundData;
import ru.sw.stock_price_monitoring.feign.PolygonServiceFeign;
import ru.sw.stock_price_monitoring.feign.dto.BarsDto;
import ru.sw.stock_price_monitoring.service.StockNotFoundDataService;
import ru.sw.stock_price_monitoring.util.DataContainer;
import ru.sw.stock_price_monitoring.util.DateRange;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class GetBarsRequestStrategy extends PolygonRequest implements RequestStockDataStrategy {
    public final StockNotFoundDataService stockNotFoundDataService;

    public GetBarsRequestStrategy(
            PolygonServiceFeign polygonClient,
            StockDataMapper mapper, StockNotFoundDataService stockNotFoundDataService
    ) {
        super(polygonClient, mapper);
        this.stockNotFoundDataService = stockNotFoundDataService;
    }

    @Override
    public boolean applicability(int i) {
        return i > 1;
    }

    @Override
    public DataContainer getStockData(Stock stock, DateRange dateRange) {
        DataContainer container = new DataContainer();
        BarsDto barsDto = getPolygonClient().getBars(
                stock.getTicker(),
                dateRange.getStartDate().toString(),
                dateRange.getEndDate().toString());


        List<StockData> resultStockDataList = new ArrayList<>();
        List<StockData> resultRequest = getMapper().toStockDataList(barsDto.getResults());

        if (resultRequest != null && !resultRequest.isEmpty()) {
            resultStockDataList.addAll(resultRequest);

            resultStockDataList.forEach(stockData -> stockData.setStock(stock));
            container.setStockDataList(resultStockDataList);
        }

        container.setStockNotFoundDataList(findStockNotFoundData(
                dateRange.getDateRange(),
                resultStockDataList.stream().map(StockData::getDate).toList(),
                stock));


        return container;
    }

    private List<StockNotFoundData> findStockNotFoundData(
            Set<LocalDate> requestDates,
            List<LocalDate> stockDataList,
            Stock stock) {

        List<LocalDate> resultDates = requestDates.stream()
                .filter(requestDate -> !stockDataList.contains(requestDate))
                .toList();

        return resultDates.stream()
                .map(date -> new StockNotFoundData(stock, date))
                .toList();
    }
}
