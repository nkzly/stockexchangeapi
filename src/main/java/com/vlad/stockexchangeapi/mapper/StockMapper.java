package com.vlad.stockexchangeapi.mapper;

import com.vlad.stockexchangeapi.entity.Stock;
import com.vlad.stockexchangeapi.entity.StockExchange;
import com.vlad.stockexchangeapi.entity.StockExchangeDetail;
import com.vlad.stockexchangeapi.model.StockExchangeDetailRequest;
import com.vlad.stockexchangeapi.model.StockExchangeRequest;
import com.vlad.stockexchangeapi.model.StockRequest;
import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper(componentModel = "spring")
public interface StockMapper {
    StockMapper INSTANCE = Mappers.getMapper(StockMapper.class);


    Stock toEntity(StockRequest request);

    StockRequest toRequest(Stock stock);

    StockExchange toEntity(StockExchangeRequest request);

    StockExchangeRequest toRequest(StockExchange stockExchange);

    StockExchangeDetail toEntity(StockExchangeDetailRequest request);

    StockExchangeDetailRequest toRequest(StockExchangeDetail stockExchangeDetail);
}
