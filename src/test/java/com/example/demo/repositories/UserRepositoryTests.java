package com.example.demo.repositories;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    UserRepository userRepository;


    @Before
    public void setUp() {
        userRepository.save(getUser());
    }


    @Test
    public void findByUsername() {
        User user = userRepository.findByUsername("Sivan");
        assertNotNull(user);
    }

    private User getUser() {
        User user = new User();
        user.setUsername("Sivan");
        user.setPassword("passuser1");
        return user;
    }

}
