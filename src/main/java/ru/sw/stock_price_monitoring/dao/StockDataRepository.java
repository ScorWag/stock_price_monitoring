package ru.sw.stock_price_monitoring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.sw.stock_price_monitoring.entity.StockData;

public interface StockDataRepository extends JpaRepository<StockData, Long>, JpaSpecificationExecutor<StockData> {

}
