package com.bughunters.code.passwordmanagerwebapplication.Filters;

import com.bughunters.code.passwordmanagerwebapplication.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Filter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Filter(name = "jwtFilterChain")
@Slf4j
@RequiredArgsConstructor
public class JwtFilterChain extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;
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
        String username = jwtService.getExtractUsername(token);

        if(username != null || SecurityContextHolder.getContext().getAuthentication() == null){

            /*Getting the user details.*/
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            /*Token validation.*/
            if(jwtService.isValid(token, userDetails)){

                UsernamePasswordAuthenticationToken userToken = new
                        UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                userToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(userToken);

                log.info("Configured user context.");
            }
            filterChain.doFilter(request,response);
        }
    }
}
