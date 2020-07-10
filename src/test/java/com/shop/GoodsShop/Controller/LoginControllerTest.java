package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Service.InitDB;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InitDB initDB;

    @Test
    public void showLoginPageTest() throws Exception {
        mockMvc
                .perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("security/loginPage"))
                .andExpect(content().string(not(containsString("Неверный логин или пароль!"))));
    }

    @Test
    public void showLoginPageWithErrorAttributeTest() throws Exception {
        mockMvc
                .perform(get("/login").param("error", ""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("security/loginPage"))
                .andExpect(content().string(containsString("Неверный логин или пароль!")));
    }
}
