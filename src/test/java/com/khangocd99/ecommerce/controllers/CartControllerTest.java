package com.khangocd99.ecommerce.controllers;

import com.khangocd99.ecommerce.model.persistence.Cart;
import com.khangocd99.ecommerce.model.persistence.Item;
import com.khangocd99.ecommerce.model.persistence.User;
import com.khangocd99.ecommerce.model.persistence.repositories.CartRepository;
import com.khangocd99.ecommerce.model.persistence.repositories.ItemRepository;
import com.khangocd99.ecommerce.model.persistence.repositories.UserRepository;
import com.khangocd99.ecommerce.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {

    private CartController cartController;

    private final UserRepository userRepo = mock(UserRepository.class);

    private final CartRepository cartRepo = mock(CartRepository.class);

    private final ItemRepository itemRepo = mock(ItemRepository.class);

    @Before
    public void setUp() {
        cartController = new CartController();
        ReflectionTestUtils.setField(cartController, "userRepository", userRepo);
        ReflectionTestUtils.setField(cartController, "cartRepository", cartRepo);
        ReflectionTestUtils.setField(cartController, "itemRepository", itemRepo);

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

        when(userRepo.findByUsername(user.getUsername())).thenReturn(user);
        when(itemRepo.findById(item.getId())).thenReturn(Optional.of(item));
    }

    @Test
    public void add_to_cart_happy_path() {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("test");
        modifyCartRequest.setItemId(1);
        modifyCartRequest.setQuantity(1);
        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void add_to_cart_unhappy_path_username_not_found() {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("unknown");
        modifyCartRequest.setItemId(1);
        modifyCartRequest.setQuantity(1);
        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void add_to_cart_unhappy_path_item_not_found() {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("test");
        modifyCartRequest.setItemId(3);
        modifyCartRequest.setQuantity(1);
        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }
}