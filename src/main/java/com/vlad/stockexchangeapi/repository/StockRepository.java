package com.vlad.stockexchangeapi.repository;

import com.vlad.stockexchangeapi.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findByName(String name);

    boolean existsByNameOrDescription(String name, String description);

    List<Stock> findAllByNameIn(List<String> stockNames);
}