package ru.gb.external.api.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.order.dto.OrderDto;
import ru.gb.api.order.api.OrderGateway;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderGateway orderGateway;

    @GetMapping
    public List<OrderDto> getOrderList() {
        return orderGateway.getOrderList();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable("orderId") Long id) {
        return orderGateway.getOrder(id);
    }

    @PostMapping
    public ResponseEntity<?> handlePost(@Validated @RequestBody OrderDto orderDto) {
        return orderGateway.handlePost(orderDto);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<?> handleUpdate(@PathVariable("orderId") Long id,
                                          @Validated @RequestBody OrderDto orderDto) {
        log.info("Change order with id {}", id);
        return orderGateway.handleUpdate(id, orderDto);
    }

    @DeleteMapping("/{orderId}")
    public void deleteById(@PathVariable("orderId") Long id) {
        orderGateway.deleteById(id);
    }
}
