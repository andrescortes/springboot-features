package com.debuggeando_ideas.best_travel.api.models.controllers.errorhandler;

import com.debuggeando_ideas.best_travel.api.models.response.BaseErrorResponse;
import com.debuggeando_ideas.best_travel.api.models.response.ErrorResponse;
import com.debuggeando_ideas.best_travel.api.models.response.ErrorsResponse;
import com.debuggeando_ideas.best_travel.util.exceptions.IdNotFoundException;
import com.debuggeando_ideas.best_travel.util.exceptions.UsernameNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestController {

    @ExceptionHandler({IdNotFoundException.class, UsernameNotFoundException.class})
    public BaseErrorResponse handlerIdNotFoundException(RuntimeException ex) {
        return ErrorResponse.builder()
            .error(ex.getMessage())
            .status(HttpStatus.BAD_REQUEST.name())
            .code(HttpStatus.BAD_REQUEST.value())
            .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseErrorResponse handlerMethodArgumentNotValidException(
        MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getAllErrors()
            .forEach(error -> errors.add(error.getDefaultMessage()));
        return ErrorsResponse.builder()
            .errors(errors)
            .status(HttpStatus.BAD_REQUEST.name())
            .code(HttpStatus.BAD_REQUEST.value())
            .build();
    }
}
