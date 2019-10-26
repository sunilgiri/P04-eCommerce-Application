package com.example.demo.service;

import java.util.Collections;


import com.example.demo.model.persistence.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository secureRepo;

    public UserDetailsServiceImpl(UserRepository secureRepo) {
        this.secureRepo = secureRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        com.example.demo.model.persistence.User applicationUser = secureRepo.findByUsername(name);

        if(applicationUser == null) {
            throw  new UsernameNotFoundException(name);
        }

        return new User(applicationUser.getUsername(), applicationUser.getPassword(), Collections.emptyList());

    }

}
