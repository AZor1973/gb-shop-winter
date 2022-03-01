package ru.gb.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.FileNotFoundException;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProductImageNotFoundException extends FileNotFoundException {
    public ProductImageNotFoundException(String imageName) {
        super("Image " + imageName + " not found");
    }
}
