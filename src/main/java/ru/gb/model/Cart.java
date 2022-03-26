package ru.gb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.gb.api.product.dto.ProductDto;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Getter
@Setter
@AllArgsConstructor
public class Cart {
    private Integer id;
    private Map<ProductDto, Integer> products;

    public Cart (){
        id = new Random().nextInt(1000);
        products = new ConcurrentHashMap<>();
    }
}
