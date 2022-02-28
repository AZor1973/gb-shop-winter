package ru.gb.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.gb.entity.common.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "ADDRESS")
public class Address extends BaseEntity {

    @Column(name = "address")
    private String address;

    @Builder
    public Address(Long id, String address) {
        super(id);
        this.address = address;
    }
}
