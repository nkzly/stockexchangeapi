package com.vlad.stockexchangeapi.service;

import com.vlad.stockexchangeapi.entity.Stock;
import com.vlad.stockexchangeapi.model.StockRequest;

import java.math.BigDecimal;
import java.util.List;

public interface StockService {

    Stock addStock(StockRequest request);
    void deleteStockByName(String stockName);
    Stock findStockByName(String stockName);
    List<Stock> getAllStocks();
    void updateCurrentPrice(String stockName, BigDecimal newPrice);
}
