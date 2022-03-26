package ru.gb.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import ru.gb.api.common.enums.Status;
import ru.gb.entity.common.InfoEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "product")
@EntityListeners(AuditingEntityListener.class)
public class Product extends InfoEntity {

    @Column(name = "title")
    private String title;
    @Column(name = "cost")
    private BigDecimal cost;
    @Column(name = "manufacture_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, fallbackPatterns = {"M/d/yy", "dd.MM.yyyy"})
    private LocalDate manufactureDate;
    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Singular
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<ProductImage> images;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "full_description")
    private String fullDescription;

    @Override
    public String toString() {
        return "Product{" +
                ", title='" + title + '\'' +
                ", cost=" + cost +
                ", manufactureDate=" + manufactureDate +
//                ", manufacturer=" + manufacturer.getName() +
                "}\n";
    }


    @Builder
    public Product(Long id, int version, String createdBy, LocalDateTime createdDate, String lastModifiedBy,
                   LocalDateTime lastModifiedDate, String title, BigDecimal cost, LocalDate manufactureDate,
                   Manufacturer manufacturer, Set<Category> categories, Status status) {
        super(id, version, createdBy, createdDate, lastModifiedBy, lastModifiedDate);
        this.title = title;
        this.cost = cost;
        this.manufactureDate = manufactureDate;
        this.manufacturer = manufacturer;
        this.categories = categories;
        this.status = status;
    }
}
