package com.vlad.stockexchangeapi.controller;


import com.vlad.stockexchangeapi.entity.Stock;
import com.vlad.stockexchangeapi.mapper.StockMapper;
import com.vlad.stockexchangeapi.model.StockRequest;
import com.vlad.stockexchangeapi.model.UpdatePriceRequest;
import com.vlad.stockexchangeapi.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/stock")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> addStock(@RequestBody @Valid StockRequest request) {
        Stock createdStock = stockService.addStock(request);
        StockRequest responseDTO = StockMapper.INSTANCE.toRequest(createdStock);
        return ResponseEntity.ok("Stock added.");
    }

    @PutMapping
    public ResponseEntity<String> updateCurrentPrice(@RequestBody @Valid UpdatePriceRequest updatePriceRequest) {
        stockService.updateCurrentPrice(updatePriceRequest.getStockName(), updatePriceRequest.getNewPrice());
        return ResponseEntity.ok("Price updated.");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockService.getAllStocks();
        return ResponseEntity.ok(stocks);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{stockName}")
    public ResponseEntity<String> deleteStockByName(@PathVariable String stockName) {
        stockService.deleteStockByName(stockName);
        //need to delete from excchangedetail table , and change liveonmarket status as count of stock
        return ResponseEntity.ok("Stock removed.");
    }


}
