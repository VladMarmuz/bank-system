package com.banksystem.credit.handler;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@AllArgsConstructor
public class ExceptionBody {
    public ZonedDateTime dateTime;
    public HttpStatus status;
    public String message;
}
