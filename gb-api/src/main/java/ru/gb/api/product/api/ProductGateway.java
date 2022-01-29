package ru.gb.api.product.api;

import org.springframework.cloud.openfeign.FeignClient;;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.product.dto.ProductDto;

import java.util.List;

@FeignClient(url = "http://127.0.0.1:8080/internal/api/v1/product", name = "ProductGateway")
public interface ProductGateway {

    @GetMapping
    List<ProductDto> getProductList();

//    @GetMapping("/info")
//    public List<ProductManufacturerDto> getInfoProductList();

    @GetMapping("/{productId}")
    ResponseEntity<?> getProduct(@PathVariable("productId") Long id);

    @PostMapping
    ResponseEntity<?> handlePost(@Validated @RequestBody ProductDto productDto);

    @PutMapping("/{productId}")
    ResponseEntity<?> handleUpdate(@PathVariable("productId") Long id,
                                          @Validated @RequestBody ProductDto productDto);

    @DeleteMapping("/{productId}")
    void deleteById(@PathVariable("productId") Long id);
}
