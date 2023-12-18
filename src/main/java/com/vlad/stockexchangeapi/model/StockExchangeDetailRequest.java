package com.vlad.stockexchangeapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockExchangeDetailRequest {


    @JsonProperty("stockName")
    private String stockName;
    @JsonProperty("stockExchangeName")
    private String stockExchangeName;
}
