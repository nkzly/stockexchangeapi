package com.vlad.stockexchangeapi.service;


import com.vlad.stockexchangeapi.entity.StockExchange;
import com.vlad.stockexchangeapi.exception.ResourceAlreadyExistsException;
import com.vlad.stockexchangeapi.exception.ResourceNotFoundException;
import com.vlad.stockexchangeapi.mapper.StockMapper;
import com.vlad.stockexchangeapi.model.StockExchangeRequest;
import com.vlad.stockexchangeapi.repository.StockExchangeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockExchangeServiceImpl implements StockExchangeService{

    private final StockExchangeRepository stockExchangeRepository;

    @Override
    public StockExchange addStockExchange(StockExchangeRequest request) {

        String stockExchangeName = request.getName();
        String description = request.getDescription();
        // Check if a stock with the same name or description already exists
        if (stockExchangeRepository.existsByNameOrDescription(stockExchangeName, description)) {
            throw new ResourceAlreadyExistsException("Stock exchange with the same name or description already exists.");
        }

        StockExchange stockExchange = StockMapper.INSTANCE.toEntity(request);
        stockExchange.setLiveInMarket(false);

        return stockExchangeRepository.save(stockExchange);
    }

    @Override
    public StockExchange findStockExchangeByName(String stockName) {

        StockExchange stockExchange = stockExchangeRepository.findByName(stockName);
        if (stockExchange == null) {
            throw new ResourceNotFoundException("Stock Exchange not found with name: " + stockName);
        }
        return stockExchange;
    }

}
