package ru.gb.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gb.entity.common.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "ORDER_ITEM")
public class OrderItem extends BaseEntity {

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "item_price")
    private BigDecimal itemPrice;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Builder
    public OrderItem(Long id, Integer quantity, BigDecimal itemPrice, BigDecimal totalPrice, Product product, Order order) {
        super(id);
        this.quantity = quantity;
        this.itemPrice = itemPrice;
        this.totalPrice = totalPrice;
        this.product = product;
        this.order = order;
    }
}
