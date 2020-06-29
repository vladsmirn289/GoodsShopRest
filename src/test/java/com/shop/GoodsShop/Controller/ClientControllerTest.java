package com.shop.GoodsShop.Controller;

import com.shop.GoodsShop.Model.Client;
import com.shop.GoodsShop.Service.ClientService;
import com.shop.GoodsShop.Service.InitDB;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@PropertySource(value = "classpath:application-test.properties")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Sql(value = {
        "classpath:db/H2/after-test.sql",
        "classpath:db/H2/user-test.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {
        "classpath:db/H2/after-test.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    @SpyBean
    private ClientService clientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private InitDB initDB;

    @Test
    @SuppressWarnings("deprecation")
    public void shouldActivateClient() throws Exception {
        Mockito
                .doNothing()
                .when(clientService)
                .authenticateClient(eq("01112"), eq("userWithCode"), anyObject());

        mockMvc
                .perform(get("/client/activate/123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("util/successConfirmation"));

        Client client = clientService.findByLogin("userWithCode");
        String password = client.getPassword();
        assertThat(client.getConfirmationCode()).isNull();
        assertThat(passwordEncoder.matches("01112", password));

        Mockito
                .verify(clientService, Mockito.times(1))
                    .authenticateClient(eq("01112"), eq("userWithCode"), any());
    }
}
