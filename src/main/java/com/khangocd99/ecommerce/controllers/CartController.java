package com.khangocd99.ecommerce.controllers;

import com.khangocd99.ecommerce.model.persistence.Cart;
import com.khangocd99.ecommerce.model.persistence.Item;
import com.khangocd99.ecommerce.model.persistence.User;
import com.khangocd99.ecommerce.model.persistence.repositories.CartRepository;
import com.khangocd99.ecommerce.model.persistence.repositories.ItemRepository;
import com.khangocd99.ecommerce.model.persistence.repositories.UserRepository;
import com.khangocd99.ecommerce.model.requests.ModifyCartRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private static final Logger logger = LogManager.getLogger(CartController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @PostMapping("/addToCart")
    public ResponseEntity<Cart> addTocart(@RequestBody ModifyCartRequest request) {
        logger.info("Adding item to cart for user: " + request.getUsername());
        User user = userRepository.findByUsername(request.getUsername());
        if (user == null) {
            logger.error("Cannot find user: " + request.getUsername());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Optional<Item> item = itemRepository.findById(request.getItemId());
        if (!item.isPresent()) {
            logger.error("Cannot find item: " + request.getItemId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Cart cart = user.getCart();
        IntStream.range(0, request.getQuantity())
                .forEach(i -> cart.addItem(item.get()));
        cartRepository.save(cart);
        logger.info("Item has been added to user's cart successfully: " + request.getUsername());
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/removeFromCart")
    public ResponseEntity<Cart> removeFromcart(@RequestBody ModifyCartRequest request) {
        logger.info("Removing item to cart for user: " + request.getUsername());
        User user = userRepository.findByUsername(request.getUsername());
        if (user == null) {
            logger.error("Cannot find user: " + request.getUsername());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Optional<Item> item = itemRepository.findById(request.getItemId());
        if (!item.isPresent()) {
            logger.error("Cannot find item: " + request.getItemId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Cart cart = user.getCart();
        IntStream.range(0, request.getQuantity())
                .forEach(i -> cart.removeItem(item.get()));
        cartRepository.save(cart);
        logger.info("Item has been removed to user's cart successfully: " + request.getUsername());
        return ResponseEntity.ok(cart);
    }
}