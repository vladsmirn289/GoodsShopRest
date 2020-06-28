package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Category;
import com.shop.GoodsShop.Model.ClientItem;
import com.shop.GoodsShop.Model.Item;
import com.shop.GoodsShop.Repositories.ClientItemRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ClientItemServiceTest {
    @Autowired
    private ClientItemService clientItemService;

    @MockBean
    private InitDB initDB;

    @MockBean
    private ClientItemRepo clientItemRepo;

    private ClientItem clientItem;

    @BeforeEach
    public void init() {
        Category books = new Category("Books");
        Category book = new Category("Book", books);
        Item item = new Item("item", 30L, 3D
                , 600D, "description..."
                , "characteristics...", "123", book);
        item.setId(1L);
        this.clientItem = new ClientItem(item, 3);
    }

    @Test
    public void shouldSaveClientItem() {
        clientItemService.save(clientItem);

        Mockito.verify(clientItemRepo, Mockito.times(1))
                .save(clientItem);
    }

    @Test
    public void shouldDeleteClientItem() {
        clientItemService.delete(clientItem);

        Mockito.verify(clientItemRepo, Mockito.times(1))
                .delete(clientItem);
    }

    @Test
    public void shouldDeleteClientItemById() {
        clientItemService.deleteById(1L);

        Mockito.verify(clientItemRepo, Mockito.times(1))
                .deleteById(1L);
    }
}
