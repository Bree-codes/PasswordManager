package com.bughunters.code.passwordmanagerwebapplication.Filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Filter;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Filter(name = "jwtFilterChain")
@Slf4j
@RequiredArgsConstructor
public class JwtFilterChain extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        /*Getting the Authorization details from the header*/

        String header = request.getHeader("Authorization");

        if(header == null || !header.startsWith("Bearer ")){
            log.warn("Request does not contain a token!");
            filterChain.doFilter(request, response);
            return;
        }

        /*Extracting the token from the Authorization header.*/
        String token = header.substring(7);

        /*extracting the user details.*/


        /*Getting the user details.*/

        /*Token validation.*/


    }
}
