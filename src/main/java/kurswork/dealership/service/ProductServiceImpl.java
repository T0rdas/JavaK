package kurswork.dealership.service;

import kurswork.dealership.dto.ProductDto;
import kurswork.dealership.entity.Product;
import kurswork.dealership.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository=productRepository;
    }
    @Override
    public Product saveProduct(ProductDto productDto) {
        Product product=new Product();
        product.setName(productDto.getName());
        product.setCost(productDto.getCost());
        product.setAmount(productDto.getAmount());
        return productRepository.save(product);
    }

    @Override
    public Product findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<ProductDto> findAllProduct() {
        List<Product> products=productRepository.findAll();
        return products.stream().map((product)->convertEntityToDto(product)).collect(Collectors.toList());
    }

    private ProductDto convertEntityToDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setCost(product.getCost());
        productDto.setId(product.getId());
        productDto.setAmount(product.getAmount());
        return productDto;
    }
}
