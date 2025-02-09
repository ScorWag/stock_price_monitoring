package ru.sw.stock_price_monitoring.util;

import lombok.Data;
import ru.sw.stock_price_monitoring.entity.StockData;
import ru.sw.stock_price_monitoring.entity.StockNotFoundData;

import java.util.ArrayList;
import java.util.List;

@Data
public class DataContainer {
    List<StockData> stockDataList = new ArrayList<>();
    List<StockNotFoundData> stockNotFoundDataList = new ArrayList<>();

    public boolean addStockData(StockData stockData) {
        return stockDataList.add(stockData);
    }

    public boolean addStockNotFoundData(StockNotFoundData stockNotFoundData) {
        return stockNotFoundDataList.add(stockNotFoundData);
    }

    public void add(DataContainer dataContainer) {
        stockDataList.addAll(dataContainer.stockDataList);
        stockNotFoundDataList.addAll(dataContainer.stockNotFoundDataList);
    }
}
