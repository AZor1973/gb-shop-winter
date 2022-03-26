package ru.gb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.api.product.dto.ProductDto;
import ru.gb.model.Cart;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductService productService;

    public void addProductToCart(Cart cart, Long productId) {
        if (productId == null) {
            return;
        }
        Map<ProductDto, Integer> products = cart.getProducts();
        ProductDto productDto = productService.findById(productId);
        if (products.containsKey(productDto)) {
            products.compute(productDto, (key, value) -> value + 1);
        } else {
            products.put(productDto, 1);
        }
        System.out.println("Add" + products);
    }

    public void deleteProductFromCart(Cart cart, Long productId) {
        Map<ProductDto, Integer> products = cart.getProducts();
        ProductDto productDto = productService.findById(productId);
        System.out.println("Size " + products.size());
        System.out.println("Present" + products.containsKey(productDto));
        if (products.size() == 0 || !products.containsKey(productDto)) {
            return;
        }
        if (products.get(productDto) > 1){
            products.compute(productDto, (key, value) -> value - 1);
        }else {
            products.remove(productDto);
        }
        System.out.println("Delete" + products);
    }
}
