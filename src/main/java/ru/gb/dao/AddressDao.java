package ru.gb.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.entity.Address;

public interface AddressDao extends JpaRepository<Address, Long> {
}
