package com.example.demo.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.LoginUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.demo.constants.SecurityConstants.*;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    Logger logger = LoggerFactory.getLogger(UsernamePasswordAuthenticationFilter.class);

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            LoginUserRequest applicationUser = new ObjectMapper().readValue(req.getInputStream(), LoginUserRequest.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(applicationUser.getUsername(),
                    applicationUser.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            logger.error("Exception occured because of {}",e.getCause());
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) throws IOException, ServletException {
        String keyToken = JWT.create()
                .withSubject(((User) authentication.getPrincipal()).getUsername()).withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).
                        sign(Algorithm.HMAC512(SECRET.getBytes()));
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + keyToken);
    }

}