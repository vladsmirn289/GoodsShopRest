package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Client;
import com.shop.GoodsShop.Model.Role;
import com.shop.GoodsShop.Repositories.ClientRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ClientServiceTest {
    @Autowired
    private ClientService clientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private InitDB initDB;

    @MockBean
    private ClientRepo clientRepo;

    private Client client;

    @BeforeEach
    public void init() {
        this.client = new Client("f@f","123456", "ABC", "DEF", "A");
    }

    @Test
    public void shouldFindClientByLogin() {
        Mockito
                .doReturn(client)
                .when(clientRepo)
                .findByLogin("A");

        Client client = clientService.findByLogin("A");

        assertThat(client.getLogin()).isEqualTo("A");
        Mockito
                .verify(clientRepo, Mockito.times(1))
                .findByLogin("A");
    }

    @Test
    public void shouldSaveOrUpdateClient() {
        clientService.save(client);

        assertThat(client.getRoles().size()).isEqualTo(1);
        assertThat(client.getRoles().iterator().next()).isEqualTo(Role.USER);
        assertThat(passwordEncoder.matches("123456", client.getPassword())).isTrue();
        Mockito.verify(clientRepo, Mockito.times(1))
                .save(client);

        Long id = client.getId();

        Mockito
                .doReturn(client)
                .when(clientRepo)
                .findByLogin(client.getLogin());

        client.setPatronymic("Patronymic");
        client.setEmail("g@g");
        clientService.save(client);

        assertThat(client.getId()).isEqualTo(id);
        assertThat(passwordEncoder.matches("123456", client.getPassword())).isTrue();
        Mockito.verify(clientRepo, Mockito.times(2))
                .save(client);
    }

    @Test
    public void shouldDeleteClient() {
        clientService.delete(client);

        Mockito.verify(clientRepo, Mockito.times(1))
                .delete(client);
    }

    @Test
    public void shouldDeleteClientById() {
        clientService.deleteById(1L);

        Mockito.verify(clientRepo, Mockito.times(1))
                .deleteById(1L);
    }
}
