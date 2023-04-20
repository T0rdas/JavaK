package kurswork.dealership;

import com.fasterxml.jackson.databind.ObjectMapper;
import kurswork.dealership.controller.AuthController;
import kurswork.dealership.dto.UserDto;
import kurswork.dealership.entity.Role;
import kurswork.dealership.entity.User;
import kurswork.dealership.repository.RoleRepository;
import kurswork.dealership.repository.UserRepository;
import kurswork.dealership.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.PropertyEditorRegistry;
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
import org.springframework.validation.*;
import org.springframework.web.context.WebApplicationContext;


import java.beans.PropertyEditor;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthTestController {
    @MockBean
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleRepository roleRepository;
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
    public void whenAnonymousAccessLogin_thenOk() throws Exception {
        mvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void whenAnonymousAccessRestrictedEndpoint_thenIsUnauthorized() throws Exception {
        mvc.perform(get("/home"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser()
    public void whenUserAccessRestrictedEndpoint_thenOk() throws Exception {
        mvc.perform(get("/home"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void whenUserAccessHome_thenViewHome() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/home");
        ResultActions result = mvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("home"));
    }

    @Test
    public void testRegister(){
        AuthController auth=new AuthController(userService,roleRepository,userRepository);
        BindingResult result=new BeanPropertyBindingResult(auth,"objectName");
        String res=auth.registration(new UserDto(null,"Лиза","Иванова","ivanova@mail.ru","pass",null,null),result,null);
        assertEquals(res,"redirect:/login");
    }

    @Test
    public void testEdit(){
        AuthController auth=new AuthController(userService,roleRepository,userRepository);
        List<Role> roles=new ArrayList<>();
        User user=new User(5L,"Лиза Иванова","ivanova@mail.ru",
                "pass", roles,"USER");
        String result=auth.userEdit(user,"ADMIN");
        assertEquals(result,"redirect:/admin/users");
    }
}
