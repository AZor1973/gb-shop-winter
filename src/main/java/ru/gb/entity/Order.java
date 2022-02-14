package ru.gb.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.gb.api.common.enums.OrderStatus;
import ru.gb.entity.common.InfoEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "shop_order")
@EntityListeners(AuditingEntityListener.class)
public class Order extends InfoEntity {

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "FIRSTNAME")
    private String firstname;

    @Column(name = "LASTNAME")
    private String lastname;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "MAIL")
    private String mail;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "STATUS")
    private OrderStatus status = OrderStatus.CREATED;

    @Column(name = "DELIVERY_DATE")
    private LocalDate deliveryDate;

    @ManyToMany
    @JoinTable(
            name = "product_order",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products;


    @Builder
    public Order(Long id, int version, String createdBy, LocalDateTime createdDate, String lastModifiedBy,
                 LocalDateTime lastModifiedDate, String address, String firstname, String lastname, String phone,
                 String mail, OrderStatus status, LocalDate deliveryDate, Set<Product> products) {
        super(id, version, createdBy, createdDate, lastModifiedBy, lastModifiedDate);
        this.address = address;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.mail = mail;
        this.status = status;
        this.deliveryDate = deliveryDate;
        this.products = products;
    }
}
