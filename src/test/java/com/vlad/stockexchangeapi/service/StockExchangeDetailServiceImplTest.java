package com.vlad.stockexchangeapi.service;

import com.vlad.stockexchangeapi.entity.Stock;
import com.vlad.stockexchangeapi.entity.StockExchange;
import com.vlad.stockexchangeapi.entity.StockExchangeDetail;
import com.vlad.stockexchangeapi.entity.StockExchangeEmbededId;
import com.vlad.stockexchangeapi.exception.ResourceNotFoundException;
import com.vlad.stockexchangeapi.repository.StockExchangeDetailRepository;
import com.vlad.stockexchangeapi.repository.StockExchangeRepository;
import com.vlad.stockexchangeapi.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class StockExchangeDetailServiceImplTest {

    public static final BigDecimal PRICE = BigDecimal.valueOf(150.0);
    @Mock
    private StockRepository stockRepository;

    @Mock
    private StockExchangeRepository stockExchangeRepository;

    @Mock
    private StockExchangeDetailRepository stockExchangeDetailRepository;

    @InjectMocks
    private StockExchangeDetailServiceImpl stockExchangeDetailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addStockToExchange() {
        long stockId = 1L;
        long exchangeId = 1L;
        String stockName = "AAPL";
        String stockExchangeName = "NASDAQ";

        StockExchangeDetail stockExchangeDetail = new StockExchangeDetail();
        stockExchangeDetail.setId(new StockExchangeEmbededId(stockId, exchangeId));
        stockExchangeDetail.setStockName(stockName);
        stockExchangeDetail.setStockExchangeName(stockExchangeName);

        when(stockRepository.findByName(stockName)).thenReturn(new Stock(1,stockName,"", PRICE, null));
        when(stockExchangeRepository.findById(exchangeId)).thenReturn(Optional.of(new StockExchange(exchangeId, stockExchangeName,"",false)));
        when(stockExchangeDetailRepository.existsById(any())).thenReturn(false);

        stockExchangeDetailService.addStockToExchange(stockId, exchangeId, stockName, stockExchangeName);

    }

    @Test
    void getStocksByStockExchange() {
        long exchangeId = 1L;
        StockExchangeDetail detail1 = new StockExchangeDetail(new StockExchangeEmbededId(1L, exchangeId), "AAPL", "NASDAQ", null);
        StockExchangeDetail detail2 = new StockExchangeDetail(new StockExchangeEmbededId(2L, exchangeId), "GOOGL", "NASDAQ", null);

        when(stockExchangeDetailRepository.findByIdExchangeId(exchangeId)).thenReturn(Arrays.asList(detail1, detail2));
        when(stockRepository.findAllByNameIn(Arrays.asList("AAPL", "GOOGL")))
                .thenReturn(Arrays.asList(new Stock(1,"AAPL",null, PRICE, null), new Stock(2, "GOOGL",null, PRICE, null)));

        List<Stock> stocks = stockExchangeDetailService.getStocksByStockExchange(exchangeId);

        assertEquals(2, stocks.size());
    }

    @Test
    void removeStockExchange() {
        String stockExchangeName = "NASDAQ";
        StockExchange stockExchange = new StockExchange(1L, stockExchangeName, null, false);

        when(stockExchangeRepository.findByName(stockExchangeName)).thenReturn(stockExchange);

        stockExchangeDetailService.removeStockExchange(stockExchangeName);

    }

    @Test
    void removeStockExchange_NotFound() {
        String stockExchangeName = "UNKNOWN";

        when(stockExchangeRepository.findByName(stockExchangeName)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class,
                () -> stockExchangeDetailService.removeStockExchange(stockExchangeName));
    }

}
