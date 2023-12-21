package com.vlad.stockexchangeapi.service;

import com.vlad.stockexchangeapi.entity.Stock;
import com.vlad.stockexchangeapi.entity.StockExchange;
import com.vlad.stockexchangeapi.model.StockExchangeRequest;
import com.vlad.stockexchangeapi.model.StockRequest;

import java.math.BigDecimal;
import java.util.List;

public interface StockExchangeService {
    StockExchange addStockExchange(StockExchangeRequest request);
    StockExchange findStockExchangeByName(String stockName);

    List<StockExchange> getAllStockExchanges();

}
