package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
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

public class OrderControllerTests {


    private OrderController orderController;
    private UserRepository userRepository = mock(UserRepository.class);
    private OrderRepository orderRepository = mock(OrderRepository.class);

    @Before
    public void setUp() {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository", userRepository);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);
    }

    @Test
    public void orderSubmitSuccessful() {

        User usr = getUser();
        usr.getCart().addItem(getItems1().get(0));
        usr.getCart().addItem(getItems2().get(0));
        when(userRepository.findByUsername("Sivan")).thenReturn(usr);

        UserOrder order = orderController.submit("Sivan").getBody();

        assertEquals(2, order.getItems().size());
        assertEquals(BigDecimal.valueOf(109.89), order.getTotal());
    }

    @Test
    public void orderSubmitUserNotFound() {
        when(userRepository.findByUsername("userNme")).thenReturn(null);
        ResponseEntity responseEntity = orderController.submit("userNme");
        assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    public void orderHistorySuccessful() {

        User usr = getUser();
        usr.getCart().addItem(getItems1().get(0));
        usr.getCart().addItem(getItems2().get(0));
        when(userRepository.findByUsername("Sivan")).thenReturn(usr);
        List<UserOrder> userOrders = new ArrayList<>();
        userOrders.add(UserOrder.createFromCart(usr.getCart()));
        userOrders.add(UserOrder.createFromCart(usr.getCart()));
        when(orderRepository.findByUser(usr)).thenReturn(userOrders);
        List<UserOrder> order = orderController.getOrdersForUser("Sivan").getBody();

        assertNotNull(order);
        assertEquals(2, order.size());
    }

    @Test
    public void orderHistoryUserNotFound() {
        when(userRepository.findByUsername("userNme")).thenReturn(null);
        ResponseEntity responseEntity = orderController.getOrdersForUser("userNme");
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