package com.vlad.stockexchangeapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vlad.stockexchangeapi.entity.Stock;
import com.vlad.stockexchangeapi.model.StockRequest;
import com.vlad.stockexchangeapi.model.UpdatePriceRequest;
import com.vlad.stockexchangeapi.service.StockService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class StockControllerTest {

    public static final BigDecimal CURRENT_PRICE = BigDecimal.valueOf(150.0);
    private final StockService stockService = Mockito.mock(StockService.class);
    private final StockController stockController = new StockController(stockService);
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(stockController).build();

    @Test
    void addStock() throws Exception {
        StockRequest stockRequest = new StockRequest();
        stockRequest.setName("AAPL");
        stockRequest.setCurrentPrice(CURRENT_PRICE);

        when(stockService.addStock(any(StockRequest.class)))
                .thenReturn(new Stock(1,stockRequest.getName(), "APPLE", stockRequest.getCurrentPrice(), null));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stockRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Stock added."));
    }

    @Test
    void updateCurrentPrice() throws Exception {
        UpdatePriceRequest updatePriceRequest = new UpdatePriceRequest();
        updatePriceRequest.setStockName("AAPL");
        updatePriceRequest.setNewPrice(CURRENT_PRICE);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePriceRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Price updated."));
    }

    @Test
    void deleteStockByName() throws Exception {
        String stockName = "AAPL";

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/stock/{stockName}", stockName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Stock removed."));
    }
}
