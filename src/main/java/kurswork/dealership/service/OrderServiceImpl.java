package kurswork.dealership.service;

import kurswork.dealership.dto.OrderDto;
import kurswork.dealership.entity.Order;
import kurswork.dealership.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{
    private OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository=orderRepository;
    }
    @Override
    public Order saveOrder(OrderDto orderDto) {
        Order order=new Order();
        order.setName(orderDto.getName());
        order.setAmount(orderDto.getAmount());
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<OrderDto> findAllOrders() {
        List<Order> orders=orderRepository.findAll();
        return orders.stream().map((order)->convertEntityToDto(order)).collect(Collectors.toList());
    }

    private OrderDto convertEntityToDto(Order order){
        OrderDto orderDto = new OrderDto();
        orderDto.setName(order.getName());
        orderDto.setAmount(order.getAmount());
        orderDto.setId(order.getId());
        return orderDto;
    }
}
