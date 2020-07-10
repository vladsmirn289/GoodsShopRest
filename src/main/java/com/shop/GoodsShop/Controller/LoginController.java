package com.shop.GoodsShop.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping
    public String login(HttpServletRequest request) {
        logger.info("Showing login page");

        if (request.getSession().getAttribute("url_prior_login") == null) {
            String referer = request.getHeader("Referer");
            logger.info("Add attribute url_prior_login to current session");
            request.getSession().setAttribute("url_prior_login", referer);
        }

        return "security/loginPage";
    }
}
