package com.vlad.stockexchangeapi.service;

import com.vlad.stockexchangeapi.entity.Stock;
import com.vlad.stockexchangeapi.entity.StockExchange;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StockExchangeDetailService {

    void addStockToExchange(long stockId, long exchangeId, String stockName, String stockExchangeName);

    List<Stock> getStocksByStockExchange(Long exchangeId);

    void removeStockFromExchange(String stockExchangeName, String stockName);

    void removeStockExchange(String stockExchangeName);

}
