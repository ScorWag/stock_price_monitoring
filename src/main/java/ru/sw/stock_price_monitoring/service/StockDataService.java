package ru.sw.stock_price_monitoring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.sw.stock_price_monitoring.dao.StockDataRepository;
import ru.sw.stock_price_monitoring.dao.StockRepository;
import ru.sw.stock_price_monitoring.entity.Stock;
import ru.sw.stock_price_monitoring.entity.StockData;
import ru.sw.stock_price_monitoring.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.sw.stock_price_monitoring.dao.specification.StockDataSpecification.*;

@RequiredArgsConstructor
@Service
public class StockDataService {
    private final StockDataRepository stockDataRepository;
    private final StockRepository stockRepository;

    public Map<String, List<StockData>> getStockDataByUserAndTicker(User user, String ticker) {
        Specification<StockData> spec = byUser(user);
        List<StockData> stockDataList;

        if (stockRepository.existsByTicker(ticker)) {
            spec = spec.and(byStock(stockRepository.findByTicker(ticker)));
        }

        stockDataList = stockDataRepository.findAll(spec);

        return stockDataList.stream()
                .collect(Collectors.groupingBy(sd -> sd.getStock().getTicker()));
    }

    public List<StockData> getStockDataByUserAndStock(User user, Stock stock) {
        Specification<StockData> spec = byUser(user).and(byStock(stock));
        return stockDataRepository.findAll(spec);
    }

    public List<StockData> getStockDataByStockAndDateRange(Stock stock, LocalDate from, LocalDate to) {
        Specification<StockData> spec = byStock(stock)
                .and(greaterThanOrEqualTo(from))
                .and(lessThanOrEqualTo(to));
        return stockDataRepository.findAll(spec);
    }

    public List<StockData> saveAll(List<StockData> stockDataList) {
        return stockDataRepository.saveAll(stockDataList);
    }

}
