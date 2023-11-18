package com.khangocd99.ecommerce.controllers;

import com.khangocd99.ecommerce.model.persistence.Item;
import com.khangocd99.ecommerce.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    private ItemController itemController;

    private final ItemRepository itemRepo = mock(ItemRepository.class);

    @Before
    public void setUp() {
        itemController = new ItemController();
        ReflectionTestUtils.setField(itemController, "itemRepository", itemRepo);
    }

    @Test
    public void get_items_happy_path() {
        when(itemRepo.findAll()).thenReturn(new ArrayList<Item>());
        ResponseEntity<List<Item>> response = itemController.getItems();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void get_item_by_id_happy_path() {
        Long id = 1L;
        when(itemRepo.findById(id)).thenReturn(Optional.of(new Item()));
        ResponseEntity<Item> response = itemController.getItemById(id);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void get_item_by_id_unhappy_path_id_not_found() {
        Long id = 1L;
        ResponseEntity<Item> response = itemController.getItemById(id);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void get_items_by_name_happy_path() {
        String name = "Test Widget";
        List<Item> items = new ArrayList<>();
        items.add(new Item());
        when(itemRepo.findByName(name)).thenReturn(items);
        ResponseEntity<List<Item>> response = itemController.getItemsByName(name);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void get_items_by_name_unhappy_path_item_name_not_found() {
        String name = "Invalid Name";
        ResponseEntity<List<Item>> response = itemController.getItemsByName(name);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }
}