package kurswork.dealership;

import com.fasterxml.jackson.databind.ObjectMapper;
import kurswork.dealership.controller.ProductController;
import kurswork.dealership.dto.ProductDto;
import kurswork.dealership.repository.ProductRepository;
import kurswork.dealership.repository.OrderRepository;
import kurswork.dealership.service.ProductService;
import kurswork.dealership.service.OrderService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductTestController {
    @MockBean
    private OrderService orderService;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductService productService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private FilterChainProxy filterChainProxy;
    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    @Test
    @WithAnonymousUser
    public void whenAnonymousAccessDealer_thenIsUnauthorized() throws Exception {
        mvc.perform(get("/dealer"))
                .andExpect(status().isUnauthorized());
    }
    @Test
    @WithAnonymousUser
    public void whenAnonymousAccessWorker_thenIsUnauthorized() throws Exception {
        mvc.perform(get("/worker"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void whenUserAccessOrder_thenViewMenu() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/dealer");
        ResultActions result = mvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("dealer"));
    }

    @Test
    public void testNew(){
        ProductController productC=new ProductController(productService,productRepository,orderService,orderRepository);
        BindingResult result=new BeanPropertyBindingResult(productC,"objectName");
        String res=productC.new_product(new ProductDto(6L,"Ручки","30",0L),result,null);
        assertEquals(res,"redirect:/admin/products");
    }
}

