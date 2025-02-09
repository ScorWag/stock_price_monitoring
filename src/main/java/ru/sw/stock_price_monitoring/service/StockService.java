package ru.sw.stock_price_monitoring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sw.stock_price_monitoring.dao.StockRepository;
import ru.sw.stock_price_monitoring.entity.Stock;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;

    public Stock findByTicker(String ticker) {
        return stockRepository.findByTicker(ticker);
    }

    public boolean existsByTicker(String ticker) {
        return stockRepository.existsByTicker(ticker);
    }
}
