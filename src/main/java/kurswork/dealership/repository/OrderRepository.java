package kurswork.dealership.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kurswork.dealership.entity.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
