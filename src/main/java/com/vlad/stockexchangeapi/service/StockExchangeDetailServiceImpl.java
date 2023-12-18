package com.vlad.stockexchangeapi.service;

import com.vlad.stockexchangeapi.entity.Stock;
import com.vlad.stockexchangeapi.entity.StockExchange;
import com.vlad.stockexchangeapi.entity.StockExchangeDetail;
import com.vlad.stockexchangeapi.entity.StockExchangeEmbededId;
import com.vlad.stockexchangeapi.exception.ResourceAlreadyExistsException;
import com.vlad.stockexchangeapi.exception.ResourceNotFoundException;
import com.vlad.stockexchangeapi.repository.StockExchangeDetailRepository;
import com.vlad.stockexchangeapi.repository.StockExchangeRepository;
import com.vlad.stockexchangeapi.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockExchangeDetailServiceImpl implements StockExchangeDetailService {

    private final StockRepository stockRepository;
    private final StockExchangeRepository stockExchangeRepository;
    private final StockExchangeDetailRepository stockExchangeDetailRepository;

    @Autowired
    public StockExchangeDetailServiceImpl(
            StockExchangeDetailRepository stockExchangeDetailRepository,
            StockExchangeRepository stockExchangeRepository,
            StockRepository stockRepository) {
        this.stockExchangeDetailRepository = stockExchangeDetailRepository;
        this.stockExchangeRepository = stockExchangeRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    @Transactional
    public void addStockToExchange(long stockId, long exchangeId, String stockName, String stockExchangeName) {
        StockExchangeEmbededId id = new StockExchangeEmbededId(stockId, exchangeId);

        if (stockExchangeDetailRepository.existsById(id)) {
            throw new ResourceAlreadyExistsException("Stock already in stock exchange");
        }

        StockExchangeDetail stockExchangeDetail = new StockExchangeDetail();
        stockExchangeDetail.setId(id);
        stockExchangeDetail.setStockName(stockName);
        stockExchangeDetail.setStockExchangeName(stockExchangeName);
        stockExchangeDetail.setStartDate(new Timestamp(System.currentTimeMillis()));

        stockExchangeDetailRepository.save(stockExchangeDetail);

        long stockCount = stockExchangeDetailRepository.countByIdExchangeId(exchangeId);

        stockExchangeRepository.findById(exchangeId).ifPresent(stockExchange -> {
            boolean isLiveInMarket = stockCount >= 5;
            stockExchange.setLiveInMarket(isLiveInMarket);
            stockExchangeRepository.save(stockExchange);
        });
    }

    @Override
    public List<Stock> getStocksByStockExchange(Long exchangeId) {
        List<String> stockNames = stockExchangeDetailRepository.findByIdExchangeId(exchangeId)
                .stream()
                .map(StockExchangeDetail::getStockName)
                .collect(Collectors.toList());

        return stockRepository.findAllByNameIn(stockNames);
    }

    @Override
    @Transactional
    public void removeStockFromExchange(String stockExchangeName, String stockName) {
        StockExchange stockExchange = stockExchangeRepository.findByName(stockExchangeName);
        Stock stock = stockRepository.findByName(stockName);

        if (stockExchange == null) {
            throw new ResourceNotFoundException("Stock Exchange not found: " + stockExchangeName);
        }

        if (stock == null) {
            throw new ResourceNotFoundException("Stock not found: " + stockName);
        }

        List<Stock> stockExchangeDetails = getStocksByStockExchange(stockExchange.getExchangeId());

        if (stockExchangeDetails.isEmpty()) {
            throw new ResourceNotFoundException("The " + stockName + " stock is not in the " + stockExchangeName);
        }

        stockExchangeDetailRepository.deleteByStockExchangeNameAndStockName(stockExchangeName, stockName);

        long stockCount = stockExchangeDetails.size() - 1;

        if (stockExchange.getLiveInMarket() && stockCount < 5) {
            stockExchange.setLiveInMarket(false);
            stockExchangeRepository.save(stockExchange);
        }
    }

    @Override
    @Transactional
    public void removeStockExchange(String stockExchangeName) {
        StockExchange stockExchange = stockExchangeRepository.findByName(stockExchangeName);

        if (stockExchange != null) {
            stockExchangeRepository.delete(stockExchange);
            stockExchangeDetailRepository.deleteByStockExchangeName(stockExchangeName);
        } else {
            throw new ResourceNotFoundException("Stock Exchange not found: " + stockExchangeName);
        }
    }

    @Override
    public List<StockExchange> getAllStockExchanges() {
        return stockExchangeRepository.findAll();
    }
}
