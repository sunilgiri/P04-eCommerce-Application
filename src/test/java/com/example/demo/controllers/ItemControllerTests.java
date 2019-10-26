package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTests {

    private ItemController itemController;
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp() {

        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);
    }

    @Test
    public void getItemByIdSuccess() {
        when(itemRepository.findById(2l)).thenReturn(java.util.Optional.ofNullable(getItems1().get(0)));
        ResponseEntity<Item> responseEntity = itemController.getItemById(2l);
        assertNotNull(responseEntity.getBody());
        assertEquals("bottle1", responseEntity.getBody().getName());
    }

    @Test
    public void getItemByIdNotFound() {
        when(itemRepository.findById(2l)).thenReturn(java.util.Optional.ofNullable(null));
        ResponseEntity<Item> responseEntity = itemController.getItemById(2l);
        assertEquals(404, responseEntity.getStatusCodeValue());
    }


    @Test
    public void getItemsByNameSuccess() {
        when(itemRepository.findByName("bottle1")).thenReturn(getItems1());
        ResponseEntity<List<Item>> responseEntity = itemController.getItemsByName("bottle1");
        assertNotNull(responseEntity.getBody());
        assertEquals("bottle1", responseEntity.getBody().get(0).getName());
    }

    @Test
    public void getItemsByNameNotFound() {
        when(itemRepository.findByName("bottle1")).thenReturn(null);
        ResponseEntity<List<Item>> responseEntity = itemController.getItemsByName("bottle1");
        assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    public void getItemsSuccess() {

        List<Item> products;
        products = getItems1();
        products.addAll(getItems2());

        when(itemRepository.findAll()).thenReturn(products);

        ResponseEntity<List<Item>> responseEntity = itemController.getItems();

        assertEquals(2, responseEntity.getBody().size());

    }

    private List<Item> getItems1() {
        Item bottle = new Item();
        bottle.setId(2l);
        bottle.setDescription("Glass bottle");
        bottle.setName("bottle1");
        bottle.setPrice(BigDecimal.valueOf(99.99));
        List<Item> list = new ArrayList<>(Arrays.asList(bottle));
        return list;
    }

    private List<Item> getItems2() {

        Item glass = new Item();
        glass.setId(22l);
        glass.setDescription("Cup");
        glass.setName("cup1");
        glass.setPrice(BigDecimal.valueOf(9.9));
        List<Item> list = new ArrayList<>(Arrays.asList(glass));
        return list;

    }


}
