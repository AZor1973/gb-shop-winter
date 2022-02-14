package ru.gb.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.entity.Order;

public interface OrderDao extends JpaRepository<Order, Long> {
}
