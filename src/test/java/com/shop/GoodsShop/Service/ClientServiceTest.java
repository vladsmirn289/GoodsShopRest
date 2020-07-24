package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.*;
import com.shop.GoodsShop.Repositories.ClientRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class ClientServiceTest {
    @Autowired
    private ClientService clientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private ClientRepo clientRepo;

    @MockBean
    private AuthenticationManager authManager;

    private Client client;

    @BeforeEach
    public void init() {
        this.client = new Client("f@f","123456", "ABC", "DEF", "A");
    }

    @Test
    public void shouldAuthenticateClient() {
        Mockito
                .doReturn(client)
                .when(clientRepo)
                .findByLogin("A");

        clientService.authenticateClient("123456", "A", authManager);

        Mockito
                .verify(clientRepo, Mockito.times(1))
                .findByLogin("A");
        Mockito
                .verify(authManager, Mockito.times(1))
                .authenticate(any());
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
        Pageable pageable = PageRequest.of(0, 10);
        Page<Client> page = new PageImpl<>(Collections.singletonList(client));
        Mockito
                .doReturn(page)
                .when(clientRepo)
                .findAll(pageable);

        Page<Client> clientsPage = clientService.findAll(pageable);
        List<Client> clients = clientsPage.getContent();

        assertThat(clients.size()).isEqualTo(1);
        Mockito
                .verify(clientRepo, Mockito.times(1))
                .findAll(pageable);
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
                , 600D, "123");
        item.setDescription("description...");
        item.setCharacteristics("characteristics...");
        item.setCategory(book);
        ClientItem clientItem = new ClientItem(item, 3);
        ClientItem clientItem1 = new ClientItem(item, 4);

        client.setBasket(new HashSet<>(Arrays.asList(clientItem, clientItem1)));

        Mockito
                .doReturn(client)
                .when(clientRepo)
                .findByLogin("A");

        clientService.deleteBasketItems(new HashSet<>(Collections.singleton(clientItem)), "A");

        assertThat(client.getBasket().size()).isEqualTo(1);
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
