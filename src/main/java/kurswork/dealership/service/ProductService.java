package kurswork.dealership.service;

import kurswork.dealership.dto.ProductDto;
import kurswork.dealership.entity.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct(ProductDto productDto);
    Product findByName(String name);
    List<ProductDto> findAllProduct();
}
