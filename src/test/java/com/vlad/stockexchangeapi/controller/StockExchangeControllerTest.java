package com.vlad.stockexchangeapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vlad.stockexchangeapi.entity.Stock;
import com.vlad.stockexchangeapi.entity.StockExchange;
import com.vlad.stockexchangeapi.model.StockExchangeRequest;
import com.vlad.stockexchangeapi.service.StockExchangeDetailService;
import com.vlad.stockexchangeapi.service.StockExchangeService;
import com.vlad.stockexchangeapi.service.StockService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class StockExchangeControllerTest {

    private final StockExchangeService stockExchangeService = Mockito.mock(StockExchangeService.class);
    private final StockService stockService = Mockito.mock(StockService.class);
    private final StockExchangeDetailService stockExchangeDetailService = Mockito.mock(StockExchangeDetailService.class);

    private final StockExchangeController stockExchangeController =
            new StockExchangeController(stockExchangeService, stockService, stockExchangeDetailService);

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(stockExchangeController).build();

    @Test
    void addStockToExchange() throws Exception {
        when(stockService.findStockByName("AAPL")).thenReturn(new Stock(1,"AAPL",null, BigDecimal.valueOf(150.0), null));
        when(stockExchangeService.findStockExchangeByName("NASDAQ")).thenReturn(new StockExchange(1,"NASDAQ", null, false));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/stock-exchange")
                        .param("stockName", "AAPL")
                        .param("stockExchangeName", "NASDAQ"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Stock added to Stock Exchange."));
    }

    @Test
    void addStockExchange() throws Exception {
        StockExchangeRequest stockExchangeRequest = new StockExchangeRequest();
        stockExchangeRequest.setName("NASDAQ");

        when(stockExchangeService.addStockExchange(any(StockExchangeRequest.class)))
                .thenReturn(new StockExchange(1,stockExchangeRequest.getName(), null,false));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/stock-exchange/exchange")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stockExchangeRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Stock Exchange added."));
    }


    @Test
    void removeStockFromExchange() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/stock-exchange/{stockExchangeName}/{stockName}", "NASDAQ", "AAPL"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Stock removed from StockExchange."));
    }

    @Test
    void removeStockExchange() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/stock-exchange/{stockExchangeName}", "NASDAQ"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Stock Exchange removed."));
    }

    @Test
    void getAllStocks() throws Exception {
        when(stockExchangeService.getAllStockExchanges()).thenReturn(
                Arrays.asList(new StockExchange(1,"NASDAQ","",true), new StockExchange(2,"NYSE","",false))
        );

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/stock-exchange"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("NASDAQ"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("NYSE"));
    }
}
