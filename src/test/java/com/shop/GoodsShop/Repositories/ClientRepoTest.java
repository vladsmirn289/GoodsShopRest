package com.shop.GoodsShop.Repositories;

import com.shop.GoodsShop.Model.Client;
import com.shop.GoodsShop.Service.InitDB;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ClientRepoTest {
    @Autowired
    private ClientRepo clientRepo;

    @MockBean
    private InitDB initDB;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void init() {
        Client client = new Client("i@gmail.com", "12345", "Igor", "Key", "C", "IK");
        clientRepo.save(client);
    }

    @AfterEach
    public void resetSequence() {
        entityManager.getEntityManager()
                .createNativeQuery("alter sequence hibernate_sequence restart 1")
                .executeUpdate();
    }

    @Test
    public void shouldFindClientById() {
        Client client = clientRepo.findById(1L).orElse(null);

        assertThat(client).isNotNull();
        assertThat(client.getEmail()).isEqualTo("i@gmail.com");
        assertThat(client.getPassword()).isEqualTo("12345");
        assertThat(client.getFirstName()).isEqualTo("Igor");
        assertThat(client.getLastName()).isEqualTo("Key");
        assertThat(client.getPatronymic()).isEqualTo("C");
        assertThat(client.getLogin()).isEqualTo("IK");
    }

    @Test
    public void shouldSaveClient() {
        Client client = new Client("l@gmail.com", "45678", "ABC", "DEF", "GHI", "ADG");
        clientRepo.save(client);

        assertThat(clientRepo.findAll().size()).isEqualTo(2);
    }

    @Test
    public void shouldDeleteClient() {
        Client client = clientRepo.findById(1L).orElse(null);
        assertThat(client).isNotNull();
        clientRepo.delete(client);

        assertThat(clientRepo.findAll()).isEmpty();
    }

    @Test
    public void shouldDeleteClientById() {
        clientRepo.deleteById(1L);

        assertThat(clientRepo.findAll()).isEmpty();
    }
}
