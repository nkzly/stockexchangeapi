package com.vlad.stockexchangeapi.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "STOCK")
@SequenceGenerator(name = "stock_sequence", sequenceName = "stock_seq", allocationSize = 1)
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_sequence")
    private long stockId;
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String description;
    private BigDecimal currentPrice;
    private Timestamp lastUpdate;

}
