package com.shop.GoodsShop.Config;

import com.shop.GoodsShop.DTO.AuthRequest;
import com.shop.GoodsShop.DTO.AuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final static Logger logger = LoggerFactory.getLogger(CustomAuthenticationFilter.class);

    private final RestTemplate restTemplate;

    public CustomAuthenticationFilter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        logger.info("Attempt to authenticate");

        String username = super.obtainUsername(request);
        String password = super.obtainPassword(request);
        Authentication auth = super.attemptAuthentication(request, response);

        logger.info("Getting jwt token");
        AuthRequest authRequest = new AuthRequest(username, password);
        AuthResponse authResponse = restTemplate.exchange(
                "http://localhost/api/authentication",
                HttpMethod.POST,
                new HttpEntity<>(authRequest),
                AuthResponse.class).getBody();

        if (authResponse != null && authResponse.getJwtToken() != null) {
            logger.info("Successful");
        }

        Cookie jwtCookie = new Cookie("jwtToken", authResponse.getJwtToken());
        response.addCookie(jwtCookie);

        return auth;
    }
}
