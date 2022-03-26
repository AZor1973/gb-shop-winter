package ru.gb.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.api.product.dto.ProductDto;
import ru.gb.model.Cart;
import ru.gb.service.CartService;
import ru.gb.service.ProductService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final Cart cart = new Cart();

    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("products", cart.getProducts());
        return "cart/cart";
    }

    @GetMapping("/add/{productId}")
    public String addProductToCart(Model model, @PathVariable(name = "productId") Long id) {
        cartService.addProductToCart(cart, id);
        model.addAttribute("products", cart.getProducts());
        return "cart/cart";
    }

    @GetMapping("/delete")
    public String deleteProductFromCart(@RequestParam(name = "id") Long id) {
        cartService.deleteProductFromCart(cart, id);
        return "redirect:/cart";
    }
}
