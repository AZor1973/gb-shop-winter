package ru.gb.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gb.entity.common.BaseEntity;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "PRODUCT_IMAGE")
public class ProductImage  extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "path")
    private String path;

    @Builder
    public ProductImage(Long id, Product product, String path) {
        super(id);
        this.product = product;
        this.path = path;
    }
}
