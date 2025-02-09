package ru.sw.stock_price_monitoring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sw.stock_price_monitoring.entity.Stock;
import ru.sw.stock_price_monitoring.entity.StockNotFoundData;

import java.time.LocalDate;
import java.util.List;

public interface StockNotFoundDataRepository extends JpaRepository<StockNotFoundData, Long> {
    List<StockNotFoundData> findByStockAndDateIn(Stock stock, List<LocalDate> dates);
}
