package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Client;
import com.shop.GoodsShop.Repositories.ClientRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ClientServiceTest {
    @Autowired
    private ClientService clientService;

    @MockBean
    private InitDB initDB;

    @MockBean
    private ClientRepo clientRepo;

    @Test
    public void shouldSaveCategory() {
        Client client = new Client("f@f","123456", "ABC", "DEF", "A");
        clientService.save(client);

        Mockito.verify(clientRepo, Mockito.times(1))
                .save(client);
    }

    @Test
    public void shouldDeleteCategory() {
        Client client = new Client("f@f","123456", "ABC", "DEF", "A");
        clientService.delete(client);

        Mockito.verify(clientRepo, Mockito.times(1))
                .delete(client);
    }

    @Test
    public void shouldDeleteCategoryById() {
        clientService.deleteById(1L);

        Mockito.verify(clientRepo, Mockito.times(1))
                .deleteById(1L);
    }
}
