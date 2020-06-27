package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Service.ClientService;
import com.shop.GoodsShop.Service.InitDB;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@PropertySource(value = "classpath:application-test.properties")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RegistrationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientService clientService;

    @Autowired
    private RegistrationController registrationController;

    @MockBean
    private InitDB initDB;

    @Test
    public void showRegistrationPageTest() throws Exception {
        mockMvc
                .perform(get("/registration"))
                .andDo(print())
                .andExpect(view().name("registration"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSuccessRegisterNewClient() throws Exception {
        mockMvc
                .perform(post("/registration")
                        .with(csrf())
                        .param("firstName", "test")
                        .param("lastName", "user")
                        .param("login", "tU")
                        .param("email", "g@g")
                        .param("password", "12345")
                        .param("passwordRepeat", "12345"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        assertThat(clientService.findByLogin("tU")).isNotNull();
    }

    @Test
    public void shouldErrorShowWhenRegisterNewClientWithWrongData() throws Exception {
        mockMvc
                .perform(post("/registration")
                        .with(csrf())
                        .param("firstName", "")
                        .param("lastName", "")
                        .param("login", "")
                        .param("email", "g")
                        .param("password", "1234")
                        .param("passwordRepeat", "123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(model().errorCount(5))
                .andExpect(model().attributeHasFieldErrors(
                        "newClient", "firstName",
                        "lastName", "login",
                        "email", "password"))
                .andExpect(model().attributeExists("firstNameError"))
                .andExpect(model().attributeExists("lastNameError"))
                .andExpect(model().attributeExists("loginError"))
                .andExpect(model().attributeExists("emailError"))
                .andExpect(model().attributeExists("passwordError"))
                .andExpect(model().attributeExists("passwordRepeatError"))
                .andExpect(model().attributeDoesNotExist("userExistsError"));
    }

    @Test
    @Sql(value = {
            "classpath:db/H2/after-test.sql",
            "classpath:db/H2/user-test.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {
            "classpath:db/H2/after-test.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldErrorUserExists() throws Exception {
        mockMvc
                .perform(post("/registration")
                        .with(csrf())
                        .param("firstName", "test")
                        .param("lastName", "user")
                        .param("login", "simpleUser")
                        .param("email", "g@g")
                        .param("password", "12345")
                        .param("passwordRepeat", "12345"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(model().attributeExists("userExistsError"));
    }
}
