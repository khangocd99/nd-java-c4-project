package com.khangocd99.ecommerce.model.persistence.repositories;

import java.util.List;

import com.khangocd99.ecommerce.model.persistence.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	List<Item> findByName(String name);

}
