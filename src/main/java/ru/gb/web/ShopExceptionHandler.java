package ru.gb.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.gb.exceptions.ProductImageNotFoundException;
import ru.gb.exceptions.ProductNotFoundException;

@Slf4j
@ControllerAdvice
public class ShopExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public String handleProductNotFoundException(final ProductNotFoundException exception) {
        log.error("Product not found thrown", exception);
        return "not-found";
    }


    // todo ДЗ - сделать обработку исключения в случае если не найдено изображение
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductImageNotFoundException.class)
    public String handleProductImageNotFoundException(final ProductImageNotFoundException exception) {
        log.error("Product image not found thrown", exception);
        return "not-found";
    }
}
