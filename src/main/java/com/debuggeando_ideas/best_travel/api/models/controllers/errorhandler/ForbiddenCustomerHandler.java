package com.debuggeando_ideas.best_travel.api.models.controllers.errorhandler;

import com.debuggeando_ideas.best_travel.api.models.response.BaseErrorResponse;
import com.debuggeando_ideas.best_travel.api.models.response.ErrorResponse;
import com.debuggeando_ideas.best_travel.util.exceptions.ForbiddenCustomerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenCustomerHandler {


    @ExceptionHandler(ForbiddenCustomerException.class)
    public BaseErrorResponse handlerForbiddenCustomerException(ForbiddenCustomerException ex) {
        return ErrorResponse.builder()
            .error(ex.getMessage())
            .status(HttpStatus.FORBIDDEN.name())
            .code(HttpStatus.FORBIDDEN.value())
            .build();
    }
}