package com.khangocd99.ecommerce.controllers;

import com.khangocd99.ecommerce.model.persistence.User;
import com.khangocd99.ecommerce.model.persistence.UserOrder;
import com.khangocd99.ecommerce.model.persistence.repositories.OrderRepository;
import com.khangocd99.ecommerce.model.persistence.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private static final Logger logger = LogManager.getLogger(OrderController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;


    @PostMapping("/submit/{username}")
    public ResponseEntity<UserOrder> submit(@PathVariable String username) {
        logger.info("Submitting the order for user: " + username);
        User user = userRepository.findByUsername(username);
        if (user == null) {
            logger.error("Cannot find user: " + username);
            return ResponseEntity.notFound().build();
        }
        UserOrder order = UserOrder.createFromCart(user.getCart());
        orderRepository.save(order);
        logger.info("The order has been submitted successfully for user: " + username);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/history/{username}")
    public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username) {
        logger.info("Retrieving history order for user: " + username);
        User user = userRepository.findByUsername(username);
        if (user == null) {
            logger.error("Cannot find user: " + username);
            return ResponseEntity.notFound().build();
        }
        logger.info("History order has been retrieved successfully for user: " + username);
        return ResponseEntity.ok(orderRepository.findByUser(user));
    }
}