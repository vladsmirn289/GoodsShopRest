package com.shop.GoodsShop.Config.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private final static Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String secret;

    public boolean validateToken(String token) {
        logger.info("Trying to validate token");

        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                logger.warn("Token is expired");
                return false;
            }

            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            logger.info("Jwt token is invalid");
            return false;
        }
    }
}
