package com.vlad.stockexchangeapi.entity;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class StockExchangeEmbededId implements Serializable {
    private long stockId;
    private long exchangeId;
}
