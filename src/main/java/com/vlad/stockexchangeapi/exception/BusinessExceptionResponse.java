package com.vlad.stockexchangeapi.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BusinessExceptionResponse {
    private int status;
    private String message;

}