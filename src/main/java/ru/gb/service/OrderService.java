package ru.gb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.api.common.enums.OrderStatus;
import ru.gb.api.events.OrderEvent;
import ru.gb.api.order.dto.OrderDto;
import ru.gb.config.JmsConfig;
import ru.gb.dao.CategoryDao;
import ru.gb.dao.ManufacturerDao;
import ru.gb.dao.OrderDao;
import ru.gb.entity.Order;
import ru.gb.web.dto.mapper.OrderMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderDao orderDao;
    private final OrderMapper orderMapper;
    private final CategoryDao categoryDao;
    private final ManufacturerDao manufacturerDao;
    private final JmsTemplate jmsTemplate;

    @Transactional
    public OrderDto save(final OrderDto orderDto) {
        Order order = orderMapper.toOrder(orderDto, manufacturerDao, categoryDao);
        if (order.getId() != null) {
            orderDao.findById(orderDto.getId()).ifPresent(
                    (p) -> order.setVersion(p.getVersion())
            );
        } else {
            order.setStatus(OrderStatus.CREATED);
        }
        OrderDto savedOrderDto = orderMapper.toOrderDto(orderDao.save(order));
        jmsTemplate.convertAndSend(JmsConfig.ORDER_CHANGED, new OrderEvent(savedOrderDto));
        return savedOrderDto;
    }

    @Transactional(readOnly = true)
    public OrderDto findById(Long id) {
        return orderMapper.toOrderDto(orderDao.findById(id).orElse(null));
    }

    public List<OrderDto> findAll() {
        return orderDao.findAll().stream().map(orderMapper::toOrderDto).collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        try {
            orderDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
        }
    }
}
