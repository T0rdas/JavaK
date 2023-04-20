package kurswork.dealership.service;

import kurswork.dealership.dto.OrderDto;
import kurswork.dealership.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order saveOrder(OrderDto orderDto);
    Optional<Order> findById(Long id);
    List<OrderDto> findAllOrders();
}

