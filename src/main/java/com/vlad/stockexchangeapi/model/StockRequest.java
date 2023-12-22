package com.vlad.stockexchangeapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class StockRequest {
    @NotNull( message = "name may not be null")
    @JsonProperty("name")
    private String name;

    @NotNull( message = "description may not be null")
    @JsonProperty("description")
    private String description;

    @NotNull( message = "currentPrice may not be null")
    @JsonProperty("currentPrice")
    private BigDecimal currentPrice;
}
