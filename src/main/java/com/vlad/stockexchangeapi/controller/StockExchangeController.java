package com.vlad.stockexchangeapi.controller;

import com.vlad.stockexchangeapi.entity.Stock;
import com.vlad.stockexchangeapi.entity.StockExchange;
import com.vlad.stockexchangeapi.model.StockExchangeRequest;
import com.vlad.stockexchangeapi.service.StockExchangeDetailService;
import com.vlad.stockexchangeapi.service.StockExchangeService;
import com.vlad.stockexchangeapi.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/stock-exchange")
public class StockExchangeController {

    private final StockExchangeService stockExchangeService;
    private final StockService stockService;
    private final StockExchangeDetailService stockExchangeDetailService;

    public StockExchangeController(StockExchangeService stockExchangeService, StockService stockService, StockExchangeDetailService stockExchangeDetailService) {
        this.stockExchangeService = stockExchangeService;
        this.stockService = stockService;
        this.stockExchangeDetailService = stockExchangeDetailService;
    }

    @PostMapping
    public ResponseEntity<String> addStockToExchange(@RequestParam String stockName, @RequestParam String stockExchangeName) {
        Stock stock = stockService.findStockByName(stockName);
        StockExchange stockExchange = stockExchangeService.findStockExchangeByName(stockExchangeName);

        stockExchangeDetailService.addStockToExchange(stock.getStockId(), stockExchange.getExchangeId(), stockName, stockExchangeName);
        return ResponseEntity.ok("Stock added to Stock Exchange.");

    }

    @PostMapping(value = "/exchange", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> addStockExchange(@RequestBody StockExchangeRequest request) {
        StockExchange createdStockExchange = stockExchangeService.addStockExchange(request);
        return ResponseEntity.ok("Stock Exchange added.");
    }

    @GetMapping("/{stockExchangeName}")
    public ResponseEntity<List<Stock>> getStocksByStockExchange(@PathVariable String stockExchangeName) {
        StockExchange stockExchange = stockExchangeService.findStockExchangeByName(stockExchangeName);

        if (stockExchange == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

        List<Stock> stocks = stockExchangeDetailService.getStocksByStockExchange(stockExchange.getExchangeId());
        return ResponseEntity.ok(stocks);
    }

    @DeleteMapping("/{stockExchangeName}/{stockName}")
    public ResponseEntity<String> removeStockFromExchange(@PathVariable String stockExchangeName, @PathVariable String stockName) {
        stockExchangeDetailService.removeStockFromExchange(stockExchangeName, stockName);
        return ResponseEntity.ok("Stock removed from StockExchange.");
    }

    @DeleteMapping("/{stockExchangeName}")
    public ResponseEntity<String> removeStockExchange(@PathVariable String stockExchangeName) {
        stockExchangeDetailService.removeStockExchange(stockExchangeName);
        return ResponseEntity.ok("Stock Exchange removed.");
    }

    @GetMapping
    public ResponseEntity<List<StockExchange>> getAllStocks() {
        List<StockExchange> stocks = stockExchangeService.getAllStockExchanges();
        return ResponseEntity.ok(stocks);
    }

}
