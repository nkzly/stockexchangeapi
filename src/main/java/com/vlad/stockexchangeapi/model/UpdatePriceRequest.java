package com.vlad.stockexchangeapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class UpdatePriceRequest {
    @NotNull( message = "Name may not be null")
    private String stockName;

    @NotNull( message = "Name may not be null")
    private BigDecimal newPrice;

}