package com.vlad.stockexchangeapi.repository;

import com.vlad.stockexchangeapi.entity.StockExchangeDetail;
import com.vlad.stockexchangeapi.entity.StockExchangeEmbededId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockExchangeDetailRepository extends JpaRepository<StockExchangeDetail, StockExchangeEmbededId> {

    void deleteByStockExchangeNameAndStockName(String stockExchangeName, String stockName);

    void deleteByStockExchangeName(String stockExchangeName);

    List<StockExchangeDetail> findByIdExchangeId(long exchangeId);

    List<StockExchangeDetail> findByStockName(String stockName);

    long countByIdExchangeId(long exchangeId);


}
