package com.vlad.stockexchangeapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StockExchangeRequest {
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
}
