package com.khangocd99.ecommerce.controllers;

import com.khangocd99.ecommerce.model.persistence.Cart;
import com.khangocd99.ecommerce.model.persistence.Item;
import com.khangocd99.ecommerce.model.persistence.User;
import com.khangocd99.ecommerce.model.persistence.UserOrder;
import com.khangocd99.ecommerce.model.persistence.repositories.OrderRepository;
import com.khangocd99.ecommerce.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    private OrderController orderController;

    private final UserRepository userRepo = mock(UserRepository.class);

    private final OrderRepository orderRepo = mock(OrderRepository.class);

    @Before
    public void setUp() {
        orderController = new OrderController();
        ReflectionTestUtils.setField(orderController, "userRepository", userRepo);
        ReflectionTestUtils.setField(orderController, "orderRepository", orderRepo);

        User user = new User();

        Cart cart = new Cart();
        user.setId(1);
        user.setUsername("test");
        user.setPassword("somepassword");
        user.setCart(cart);

        Item item = new Item();
        item.setId(1L);
        item.setName("Round Widget");
        item.setPrice(BigDecimal.valueOf(2.99));
        item.setDescription("A widget that is round");

        List<Item> items = new ArrayList<>();
        items.add(item);
        cart.setItems(items);

        when(userRepo.findByUsername(user.getUsername())).thenReturn(user);
        when(orderRepo.findByUser(user)).thenReturn(new ArrayList<>());
    }

    @Test
    public void submit_order_happy_path() {
        ResponseEntity<UserOrder> response = orderController.submit("test");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void submit_order_unhappy_path_username_not_found() {
        ResponseEntity<UserOrder> response = orderController.submit("unknown");
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void get_history_happy_path() {
        ResponseEntity<UserOrder> submitOrderResponse = orderController.submit("test");
        assertNotNull(submitOrderResponse);
        assertEquals(200, submitOrderResponse.getStatusCodeValue());
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("test");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }
    @Test
    public void get_history_unhappy_path_username_not_found() {
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("unknown");
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }
}