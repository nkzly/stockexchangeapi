package com.vlad.stockexchangeapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "STOCK_EXCHANGE_DETAIL")
public class StockExchangeDetail {

    @EmbeddedId
    private StockExchangeEmbededId id;
    private String stockName;
    private String stockExchangeName;
    private Timestamp startDate;

}
