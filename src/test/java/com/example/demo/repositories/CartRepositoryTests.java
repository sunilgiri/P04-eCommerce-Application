package com.example.demo.repositories;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CartRepositoryTests {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;


    @Before
    public void setUp() {

        Cart cart = new Cart();
        User user = getUser();
        cartRepository.save(cart);
        user.setCart(cart);
        userRepository.save(user);
    }

    @Test
    public void findByUser() {
        User usr = userRepository.findByUsername("Krishnan");
        //Cart cart = cartRepository.findByUser(usr);
        //assertNotNull(cart);
        //assertEquals(Long.valueOf(1l),cart.getId());
    }

    private User getUser() {
        User user = new User();
        user.setUsername("Krishnan");
        user.setPassword("passuser1");
        return user;
    }
}
