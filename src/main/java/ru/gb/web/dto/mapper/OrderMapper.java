package ru.gb.web.dto.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import ru.gb.api.order.dto.OrderDto;
import ru.gb.dao.CategoryDao;
import ru.gb.dao.ManufacturerDao;
import ru.gb.entity.Order;

@Mapper(uses = {ProductMapper.class, ManufacturerMapper.class})
public interface OrderMapper {

    Order toOrder(OrderDto  orderDto, @Context ManufacturerDao manufacturerDao, @Context CategoryDao categoryDao);

    OrderDto toOrderDto(Order order);
}
