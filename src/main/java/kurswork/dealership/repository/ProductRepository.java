package kurswork.dealership.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kurswork.dealership.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Product findByName(String name);
}
