package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Category;
import com.shop.GoodsShop.Model.Item;
import com.shop.GoodsShop.Model.OrderedItem;
import com.shop.GoodsShop.Repositories.OrderedItemRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class OrderedItemServiceTest {
    @Autowired
    private OrderedItemService orderedItemService;

    @MockBean
    private InitDB initDB;

    @MockBean
    private OrderedItemRepo orderedItemRepo;

    private OrderedItem orderedItem;

    @BeforeEach
    public void init() {
        Category books = new Category("Books");
        Category book = new Category("Book", books);
        Item item = new Item("item", 30L, 3D
                , 600D, "description..."
                , "characteristics...", "123", book);
        item.setId(1L);
        this.orderedItem = new OrderedItem(item, 3);
    }

    @Test
    public void shouldSaveOrderedItem() {
        orderedItemService.save(orderedItem);

        Mockito.verify(orderedItemRepo, Mockito.times(1))
                .save(orderedItem);
    }

    @Test
    public void shouldDeleteOrderedItem() {
        orderedItemService.delete(orderedItem);

        Mockito.verify(orderedItemRepo, Mockito.times(1))
                .delete(orderedItem);
    }

    @Test
    public void shouldDeleteOrderedItemById() {
        orderedItemService.deleteById(1L);

        Mockito.verify(orderedItemRepo, Mockito.times(1))
                .deleteById(1L);
    }
}
