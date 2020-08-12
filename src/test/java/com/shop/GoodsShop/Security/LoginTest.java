package com.shop.GoodsShop.Security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@PropertySource(value = "classpath:application.properties")
@Sql(value = {
        "classpath:db/H2/after-test.sql",
        "classpath:db/H2/user-test.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class LoginTest {
    @Autowired
    private MockMvc mockMvc;

    @Value("${jwt.user.long.term}")
    private String userToken;

    @Test
    public void notAuthenticatedTest() throws Exception {
        mockMvc
                .perform(get("/basket"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void badCredentialsTest() throws Exception {
        mockMvc
                .perform(post("/login").cookie(new Cookie("jwtToken", userToken))
                        .with(csrf())
                        .param("username", "Andrey"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error=true"));
    }

    @Test
    @Sql(value = {
            "classpath:db/H2/after-test.sql",
            "classpath:db/H2/user-test.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void correctCredentialsTestWithSessionAttribute() throws Exception {
        mockMvc
                .perform(post("/login").cookie(new Cookie("jwtToken", userToken))
                        .with(csrf())
                        .param("username", "simpleUser")
                        .param("password", "12345")
                        .sessionAttr("url_prior_login", "http://localhost/basket"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/basket"));
    }

    @Test
    @Sql(value = {
            "classpath:db/H2/after-test.sql",
            "classpath:db/H2/user-test.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void correctCredentialsTestWithoutSessionAttribute() throws Exception {
        mockMvc
                .perform(post("/login").cookie(new Cookie("jwtToken", userToken))
                        .with(csrf())
                        .param("username", "simpleUser")
                        .param("password", "12345"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void logoutTest() throws Exception {
        mockMvc
                .perform(logout())
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?logout"));
    }
}
