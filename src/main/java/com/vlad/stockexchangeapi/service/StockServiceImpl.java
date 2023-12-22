package com.vlad.stockexchangeapi.service;

import com.vlad.stockexchangeapi.entity.Stock;
import com.vlad.stockexchangeapi.exception.ResourceAlreadyExistsException;
import com.vlad.stockexchangeapi.exception.ResourceNotFoundException;
import com.vlad.stockexchangeapi.mapper.StockMapper;
import com.vlad.stockexchangeapi.model.StockRequest;
import com.vlad.stockexchangeapi.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
@RequiredArgsConstructor
@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    @Override
    public Stock addStock(StockRequest request) {

        String stockName = request.getName();
        String description = request.getDescription();

        if (stockRepository.existsByNameOrDescription(stockName, description)) {
            throw new ResourceAlreadyExistsException("Stock with the same name or description already exists.");
        }

        Stock stock = StockMapper.INSTANCE.toEntity(request);

        Date date = new Date();
        stock.setLastUpdate(new Timestamp(date.getTime()));


        return stockRepository.save(stock);
    }

    @Override
    public void deleteStockByName(String stockName) {

        Stock stock = stockRepository.findByName(stockName);
        if (stock != null) {
            stockRepository.delete(stock);
        } else {
            throw new ResourceNotFoundException("Stock not found with name: " + stockName);
        }
    }

    @Override
    @Transactional
    public void updateCurrentPrice(String stockName, BigDecimal newPrice) {
        Stock stock = stockRepository.findByName(stockName);
        if (stock != null) {
            stock.setCurrentPrice(newPrice);
            stock.setLastUpdate(new Timestamp(System.currentTimeMillis()));
            stockRepository.save(stock);
        } else {
            throw new ResourceNotFoundException("Stock not found with name: " + stockName);
        }
    }

    @Override
    public Stock findStockByName(String stockName) {

        Stock stock = stockRepository.findByName(stockName);
        if (stock == null) {
            throw new ResourceNotFoundException("Stock not found with name: " + stockName);
        }
        return stock;
    }

    @Override
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }


}
