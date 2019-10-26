package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTests {

    UserController userController;
    UserRepository userRepository = mock(UserRepository.class);
    CartRepository cartRepository = mock(CartRepository.class);
    private BCryptPasswordEncoder passwordEncoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp() {
        userController = new UserController();
        TestUtils.injectObjects(userController,"userRepository",userRepository);
        TestUtils.injectObjects(userController,"cartRepository",cartRepository);
        TestUtils.injectObjects(userController,"bCryptPasswordEncoder",passwordEncoder);
    }

    @Test
    public void createUserSuccess(){

        when(passwordEncoder.encode("passuser1")).thenReturn("%&&&&&CCBCB");
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("user1");
        createUserRequest.setPassword("passuser1");
        createUserRequest.setConfirmPassword("passuser1");

        final ResponseEntity<User> response = userController.createUser(createUserRequest);

        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());
        User usr = response.getBody();
        assertNotNull(usr);
        assertEquals(0,usr.getId());
        assertEquals("user1",usr.getUsername());
        assertEquals("%&&&&&CCBCB",usr.getPassword());
    }
}
