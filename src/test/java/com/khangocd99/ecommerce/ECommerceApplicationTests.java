package com.khangocd99.ecommerce;

import com.khangocd99.ecommerce.controllers.UserController;
import com.khangocd99.ecommerce.model.persistence.User;
import com.khangocd99.ecommerce.model.requests.CreateUserRequest;
import com.khangocd99.ecommerce.model.requests.LoginRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ECommerceApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private JacksonTester<CreateUserRequest> createUserRequestJackson;

    @Autowired
    private JacksonTester<LoginRequest> loginRequestJackson;

    @Autowired
    private JacksonTester<User> userJackson;

    @Autowired
    UserController userController;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void user_login_happy_path() throws Exception {
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername("test");
        userRequest.setPassword("somepassword");
        userRequest.setConfirmPassword("somepassword");

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(userRequest.getUsername());
        loginRequest.setPassword(userRequest.getPassword());

        mvc.perform(post(String.valueOf(new URI("/api/user/create")))
                        .content(createUserRequestJackson.write(userRequest).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        mvc.perform(post(new URI("/login"))
                        .content(loginRequestJackson.write(loginRequest).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getHeader("Authorization");
    }
}