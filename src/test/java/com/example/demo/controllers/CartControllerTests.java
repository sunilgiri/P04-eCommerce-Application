package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
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

public class CartControllerTests {

    private CartController cartController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp() {
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);

    }

    @Test
    public void addTocartSuccessful() {

        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setUsername("Sivan");
        cartRequest.setItemId(2);
        cartRequest.setQuantity(2);
        when(userRepository.findByUsername("Sivan")).thenReturn(getUser());
        when(itemRepository.findById(2l)).thenReturn(java.util.Optional.ofNullable(getItems1().get(0)));

        Cart cart = cartController.addTocart(cartRequest).getBody();

        assertNotNull(cart);
        assertEquals(9, cart.getId().longValue());
        assertNotNull(cart.getItems());
        assertEquals(2, cart.getItems().get(0).getId().longValue());

    }

    @Test
    public void addTocartUserNotFound() {

        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setUsername("userNme");
        when(userRepository.findByUsername("userNme")).thenReturn(null);

        ResponseEntity responseEntity = cartController.addTocart(cartRequest);
        assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    public void addTocartItemNotFound() {

        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setUsername("Sivan");
        cartRequest.setItemId(2);
        cartRequest.setQuantity(2);
        when(userRepository.findByUsername("Sivan")).thenReturn(getUser());
        when(itemRepository.findById(2l)).thenReturn(java.util.Optional.ofNullable(null));

        ResponseEntity responseEntity = cartController.addTocart(cartRequest);
        assertEquals(404, responseEntity.getStatusCodeValue());
    }


    @Test
    public void removeFromCartSuccessful() {
        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setUsername("Sivan");
        cartRequest.setItemId(22);
        cartRequest.setQuantity(2);
        User usr = getUser();
        usr.getCart().addItem(getItems1().get(0));
        usr.getCart().addItem(getItems2().get(0));
        when(userRepository.findByUsername("Sivan")).thenReturn(usr);
        when(itemRepository.findById(22l)).thenReturn(java.util.Optional.ofNullable(getItems2().get(0)));
        Cart cart = cartController.removeFromcart(cartRequest).getBody();
        assertNotNull(cart);
        assertEquals(9, cart.getId().longValue());
        assertNotNull(cart.getItems());
        assertEquals(1, cart.getItems().size());

    }

    @Test
    public void removeFromCartUserNotFound() {
        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setUsername("user2");
        cartRequest.setItemId(22);
        cartRequest.setQuantity(2);
        when(userRepository.findByUsername("user2")).thenReturn(null);
        when(itemRepository.findById(22l)).thenReturn(java.util.Optional.ofNullable(getItems2().get(0)));
        Cart cart = cartController.removeFromcart(cartRequest).getBody();
        ResponseEntity responseEntity = cartController.removeFromcart(cartRequest);
        assertEquals(404, responseEntity.getStatusCodeValue());

    }

    @Test
    public void removeFromCartItemNotFound() {
        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setUsername("Sivan");
        cartRequest.setItemId(22);
        cartRequest.setQuantity(2);
        User usr = getUser();
        usr.getCart().addItem(getItems1().get(0));
        usr.getCart().addItem(getItems2().get(0));
        when(userRepository.findByUsername("Sivan")).thenReturn(usr);
        when(itemRepository.findById(22l)).thenReturn(java.util.Optional.ofNullable(null));
        Cart cart = cartController.removeFromcart(cartRequest).getBody();
        ResponseEntity responseEntity = cartController.removeFromcart(cartRequest);
        assertEquals(404, responseEntity.getStatusCodeValue());

    }


    private User getUser() {

        User user = new User();
        user.setUsername("Sivan");
        user.setId(24);
        user.setPassword("userpassword");
        Cart cart = new Cart();
        cart.setId(9l);
        user.setCart(cart);

        return user;
    }

    private Cart getCart() {
        Cart cart = new Cart();
        cart.setId(9l);
        cart.addItem(getItems1().get(0));
        cart.addItem(getItems2().get(0));
        return cart;
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
