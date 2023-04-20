package kurswork.dealership.controller;

import jakarta.validation.Valid;
import kurswork.dealership.dto.OrderDto;
import kurswork.dealership.dto.ProductDto;
import kurswork.dealership.entity.Order;
import kurswork.dealership.entity.Product;
import kurswork.dealership.repository.OrderRepository;
import kurswork.dealership.repository.ProductRepository;
import kurswork.dealership.service.OrderService;
import kurswork.dealership.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {
    private ProductRepository productRepository;
    private ProductService productService;
    private OrderRepository orderRepository;
    private OrderService orderService;
    public ProductController(ProductService productService,ProductRepository productRepository,
                             OrderService orderService,OrderRepository orderRepository){
        this.productRepository=productRepository;
        this.orderRepository=orderRepository;
        this.productService=productService;
        this.orderService=orderService;
    }

    @GetMapping("/dealer")
    public String dealer(Model model){
        List<ProductDto> products=productService.findAllProduct();
        model.addAttribute("products",products);
        return "dealer";
    }

    @GetMapping("/worker")
    public String worker(Model model){
        List<OrderDto> orders=orderService.findAllOrders();
        model.addAttribute("orders",orders);
        return "worker";
    }

    @GetMapping("/admin/products")
    public String products(Model model){
        List<ProductDto> products=productService.findAllProduct();
        model.addAttribute("products",products);
        return "products";
    }
    @GetMapping("/worker/storage")
    public String storage(Model model){
        List<ProductDto> products=productService.findAllProduct();
        model.addAttribute("products",products);
        return "storage";
    }
    @GetMapping("/admin/products/new")
    public String new_product(Model model){
        ProductDto product=new ProductDto();
        model.addAttribute("product",product);
        return "new_product";
    }

    @PostMapping("/dealer/save")
    public String order(@RequestParam String[] amount){
        List<ProductDto> products=productService.findAllProduct();
        for(int i=0;i< products.size();i++){
            if (!amount[i].equals("0")){
                String product_name=products.get(i).getName();
                Product product=productRepository.findByName(product_name);
                OrderDto order=new OrderDto();
                order.setName(product.getName());
                order.setAmount(Long.valueOf(amount[i]));
                orderService.saveOrder(order);
            }
        }
        return "redirect:/home";
    }

    @PostMapping("/worker/{order}")
    public String admisson(@PathVariable Order order){
        String product_name=order.getName();
        Product product=productRepository.findByName(product_name);
        if (product.getAmount()<order.getAmount()){
            return "redirect:/worker";
        }
        product.setAmount(product.getAmount()-order.getAmount());
        orderRepository.delete(order);
        return "redirect:/worker";
    }

    @PostMapping("/worker/storage/{product}")
    public String edit_amount(@PathVariable Product product,@RequestParam String amount){
        product.setAmount(Long.valueOf(amount));
        productRepository.save(product);
        return "redirect:/worker/storage";
    }

    @PostMapping("/admin/products/{product}")
    public String delete_product(@PathVariable Product product){
        productRepository.delete(product);
        return "redirect:/admin/products";
    }

    @PostMapping("/admin/products/new/save")
    public String new_product(@Valid @ModelAttribute("product") ProductDto product,
                              BindingResult result,
                              Model model){
        Product ex=productService.findByName(product.getName());
        if(ex!=null){
            result.rejectValue("name", null, "There is already a product with that name");
        }
        if (result.hasErrors()) {
            model.addAttribute("product", product);
        }
        product.setAmount(0L);
        productService.saveProduct(product);
        return "redirect:/admin/products";
    }
}
