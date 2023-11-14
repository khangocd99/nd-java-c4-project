package com.khangocd99.ecommerce.model.persistence.repositories;

import com.khangocd99.ecommerce.model.persistence.Cart;
import com.khangocd99.ecommerce.model.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	Cart findByUser(User user);
}
