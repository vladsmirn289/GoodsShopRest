package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.*;
import com.shop.GoodsShop.Repositories.ClientRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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
    public void shouldFindClientById() {
        Mockito
                .doReturn(Optional.of(client))
                .when(clientRepo)
                .findById(1L);

        Client client = clientService.findById(1L);

        assertThat(client.getLogin()).isEqualTo("A");
        Mockito
                .verify(clientRepo, Mockito.times(1))
                .findById(1L);
    }

    @Test
    public void shouldReturnNullWhenFindClientByIncorrectId() {
        Client client = clientService.findById(1L);

        assertThat(client).isNull();
        Mockito
                .verify(clientRepo, Mockito.times(1))
                .findById(1L);
    }

    @Test
    public void shouldFindAllClients() {
        Mockito
                .doReturn(Collections.singletonList(client))
                .when(clientRepo)
                .findAll();

        List<Client> clients = clientService.findAll();

        assertThat(clients.size()).isEqualTo(1);
        Mockito
                .verify(clientRepo, Mockito.times(1))
                .findAll();
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
    public void shouldFindClientByConfirmationCode() {
        client.setConfirmationCode("123");
        Mockito
                .doReturn(client)
                .when(clientRepo)
                .findByConfirmationCode("123");

        Client founded = clientService.findByConfirmationCode("123");

        assertThat(founded).isNotNull();
        assertThat(founded.getConfirmationCode()).isEqualTo("123");

        Mockito.verify(clientRepo, Mockito.times(1))
                .findByConfirmationCode("123");
    }

    @Test
    public void shouldSaveOrUpdateClient() {
        clientService.save(client);

        assertThat(client.getRoles().size()).isEqualTo(1);
        assertThat(client.getRoles().iterator().next()).isEqualTo(Role.USER);
        assertThat(client.getPassword()).isEqualTo("123456");
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
        assertThat(client.getPassword()).isEqualTo("123456");
        Mockito.verify(clientRepo, Mockito.times(2))
                .save(client);
    }

    @Test
    public void shouldEncodePasswordWhenConfirmationCodeIsNotNull() {
        client.setConfirmationCode("123");
        clientService.save(client);

        Mockito
                .doReturn(client)
                .when(clientRepo)
                .findByLogin("A");

        clientService.save(client);

        assertThat(client.getConfirmationCode()).isNull();
        assertThat(passwordEncoder.matches("123456", client.getPassword()));
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

    @Test
    public void shouldDeleteBasketItems() {
        Category books = new Category("Books");
        Category book = new Category("Book", books);
        Item item = new Item("item", 30L, 3D
                , 600D, "description..."
                , "characteristics...", "123", book);
        ClientItem clientItem = new ClientItem(item, 3);

        client.setBasket(new HashSet<>(Collections.singleton(clientItem)));

        Mockito
                .doReturn(client)
                .when(clientRepo)
                .findByLogin("A");

        clientService.deleteBasketItems(new HashSet<>(Collections.singleton(clientItem)), "A");

        assertThat(client.getBasket().size()).isEqualTo(0);
        Mockito
                .verify(clientRepo, Mockito.times(2))
                .findByLogin("A");
        Mockito
                .verify(clientRepo, Mockito.times(1))
                .save(client);
    }

    @Test
    public void shouldReturnNullWhenLoadClientByUsernameWithNotEmptyConfirmationCode() {
        client.setConfirmationCode("123");
        Mockito
                .doReturn(client)
                .when(clientRepo)
                .findByLogin("A");

        UserDetails userDetails = clientService.loadUserByUsername("A");

        assertThat(userDetails).isNull();
    }

    @Test
    public void shouldReturnUserDetailsWhenLoadClientByUsernameWithNullConfirmationCode() {
        client.setConfirmationCode(null);
        Mockito
                .doReturn(client)
                .when(clientRepo)
                .findByLogin("A");

        UserDetails userDetails = clientService.loadUserByUsername("A");

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo("A");
    }
}
