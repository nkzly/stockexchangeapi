package com.vlad.stockexchangeapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdatePriceRequest {
    private String stockName;
    private BigDecimal newPrice;

}