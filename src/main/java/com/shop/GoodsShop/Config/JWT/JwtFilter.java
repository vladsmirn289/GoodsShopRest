package com.shop.GoodsShop.Config.JWT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

@Service
public class JwtFilter extends GenericFilterBean {
    private final static Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    private final JwtUtils jwtUtils;

    public JwtFilter(@Autowired JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("DoFilter method called");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Cookie cookie = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("jwtToken"))
                .findAny().orElse(null);

        if (cookie == null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        } else {
            String token = cookie.getValue();

            if (token != null && jwtUtils.validateToken(token)) {
                logger.info("Token is not null and token is valid");
            } else {
                logger.warn("Jwt token is null or invalid");
                SecurityContextHolder.getContext().setAuthentication(null);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
