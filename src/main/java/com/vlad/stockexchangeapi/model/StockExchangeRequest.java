package com.vlad.stockexchangeapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StockExchangeRequest {
    @NotNull( message = "name may not be null")
    @JsonProperty("name")
    private String name;

    @NotNull( message = "description may not be null")
    @JsonProperty("description")
    private String description;
}
