package com.example.demo.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static com.example.demo.constants.SecurityConstants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);

        if(header == null || !header.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request,response);
            return;
        }

        UsernamePasswordAuthenticationToken usPsToken = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(usPsToken);
        filterChain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if(token!=null) {

            String principal = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX,""))
                    .getSubject();

            if(principal!=null) {
                return new UsernamePasswordAuthenticationToken(principal,null,new ArrayList<>());

            }
            return null;
        }

        return  null;
    }
}