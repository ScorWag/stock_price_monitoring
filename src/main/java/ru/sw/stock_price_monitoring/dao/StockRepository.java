package ru.sw.stock_price_monitoring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sw.stock_price_monitoring.entity.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
    boolean existsByTicker(String ticker);
    Stock findByTicker(String ticker);
}
