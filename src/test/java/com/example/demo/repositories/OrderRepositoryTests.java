package com.example.demo.repositories;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
@DataJpaTest
@RunWith(SpringRunner.class)
public class OrderRepositoryTests {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserRepository userRepository;


    @Before
    public void setUp() {
        Cart cart = getCart();
        Item it1 = itemRepository.save(getItems1().get(0));
        Item it2 = itemRepository.save(getItems2().get(0));
        cart.setItems(new ArrayList<Item>(Arrays.asList(it1, it2)));
        cartRepository.save(cart);

        User usr = getUser();
        usr.setCart(cart);
        userRepository.save(usr);
    }


    @Test
    public void findByUsername() {
        User usr = userRepository.findByUsername("Sivan");
        List<UserOrder> order = orderRepository.findByUser(usr);
        assertNotNull(order);
    }

    private User getUser() {
        User user = new User();
        user.setUsername("Sivan");
        user.setPassword("passuser1");
        return user;
    }


    private Cart getCart() {
        Cart cart = new Cart();
        return cart;
    }


    private List<Item> getItems1() {
        Item bottle = new Item();
        bottle.setDescription("Glass bottle");
        bottle.setName("bottle1");
        bottle.setPrice(BigDecimal.valueOf(99.99));
        List<Item> list = new ArrayList<>(Arrays.asList(bottle));
        return list;
    }

    private List<Item> getItems2() {

        Item glass = new Item();
        glass.setDescription("Cup");
        glass.setName("cup1");
        glass.setPrice(BigDecimal.valueOf(9.9));
        List<Item> list = new ArrayList<>(Arrays.asList(glass));
        return list;

    }


}
