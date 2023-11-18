package com.khangocd99.ecommerce.controllers;

import com.khangocd99.ecommerce.model.persistence.User;
import com.khangocd99.ecommerce.model.persistence.repositories.CartRepository;
import com.khangocd99.ecommerce.model.persistence.repositories.UserRepository;
import com.khangocd99.ecommerce.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;

    private final UserRepository userRepo = mock(UserRepository.class);

    private final CartRepository cartRepo = mock(CartRepository.class);

    private final BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp() {
        userController = new UserController();
        ReflectionTestUtils.setField(userController, "userRepository", userRepo);
        ReflectionTestUtils.setField(userController, "cartRepository", cartRepo);
        ReflectionTestUtils.setField(userController, "bCryptPasswordEncoder", encoder);
    }

    @Test
    public void create_user_happy_path() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("test");
        createUserRequest.setPassword("somepassword");
        createUserRequest.setConfirmPassword("somepassword");
        when(encoder.encode(createUserRequest.getPassword())).thenReturn("hashedPassword");
        ResponseEntity<User> response = userController.createUser(createUserRequest);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("hashedPassword", response.getBody().getPassword());
    }

    @Test
    public void create_user_unhappy_path() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("test");
        createUserRequest.setPassword("6chars");
        createUserRequest.setConfirmPassword("6chars");
        ResponseEntity<User> response = userController.createUser(createUserRequest);
        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void find_user_by_id_happy_path() {
        Long id = 1L;
        when(userRepo.findById(id)).thenReturn(Optional.of(new User()));
        ResponseEntity<User> response = userController.findById(id);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void find_user_by_id_unhappy_path_id_not_found() {
        Long id = 1L;
        ResponseEntity<User> response = userController.findById(id);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void find_user_by_username_happy_path() {
        String username = "test";
        when(userRepo.findByUsername(username)).thenReturn(new User());
        ResponseEntity<User> response = userController.findByUserName(username);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void find_user_by_username_unhappy_path_username_not_found() {
        String username = "unknown";
        ResponseEntity<User> response = userController.findByUserName(username);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }
}